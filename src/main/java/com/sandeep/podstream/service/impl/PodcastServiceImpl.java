package com.sandeep.podstream.service.impl;

import com.sandeep.podstream.core.entity.EpisodeEntity;
import com.sandeep.podstream.core.entity.PodcastEntity;
import com.sandeep.podstream.model.Podcast;
import com.sandeep.podstream.model.requests.PodcastRequest;
import com.sandeep.podstream.repository.EpisodeRepository;
import com.sandeep.podstream.repository.PodcastRepository;
import com.sandeep.podstream.service.EpisodeService;
import com.sandeep.podstream.service.PodcastService;
import graphql.com.google.common.base.Strings;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class PodcastServiceImpl implements PodcastService {

    private final EpisodeService episodeService;
    private final PodcastRepository podcastRepository;
    private final EpisodeRepository episodeRepository;
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    @Transactional
    public Podcast createPodcast(PodcastRequest podcastRequest) throws Exception {
        Optional<PodcastEntity> existingPodcast = podcastRepository.findByCreatorIdAndTitle(podcastRequest.getCreatorId(), podcastRequest.getTitle());
        if (existingPodcast.isPresent()) {
            log.error("Podcast with title " + podcastRequest.getTitle() + "was already created by the user.");
            throw new Exception("Podcast with the provided title already exists");
        }
        PodcastEntity podcastEntity = PodcastRequest.toPodcastEntity.apply(podcastRequest);
        PodcastEntity savedPodcast = podcastRepository.save(podcastEntity);
        return PodcastEntity.toPodcast.apply(savedPodcast);
    }

    @Override
    public List<Podcast> filterPodcast(PodcastRequest podcastRequest) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PodcastEntity> criteriaQuery = criteriaBuilder.createQuery(PodcastEntity.class);
        Root<PodcastEntity> podcastEntityRoot = criteriaQuery.from(PodcastEntity.class);
        List<Predicate> predicates = new ArrayList<>();
        if(!Strings.isNullOrEmpty(podcastRequest.getTitle())){
            predicates.add(criteriaBuilder.equal(podcastEntityRoot.get("title"), podcastRequest.getTitle()));
        }
        if(podcastRequest.getCreatorId()!=null){
            predicates.add(criteriaBuilder.equal(podcastEntityRoot.get("creatorId"), podcastRequest.getCreatorId()));
        }
        if(!Strings.isNullOrEmpty(podcastRequest.getLanguage())){
            predicates.add(criteriaBuilder.equal(podcastEntityRoot.get("language"), podcastRequest.getLanguage()));
        }

        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        List<PodcastEntity> resultList = entityManager.createQuery(criteriaQuery).getResultList();
        return resultList.stream().map(PodcastEntity.toPodcast::apply).toList();
    }

    @Override
    public Podcast getPodcastById(UUID id) {
        Optional<PodcastEntity> podcastEntity = podcastRepository.findById(id);
        return PodcastEntity.toPodcast.apply(podcastEntity.get());
    }

    @Override
    public Boolean updatePodcast(PodcastRequest podcastRequest) {
        Optional<PodcastEntity> OptionalPodcastEntity = podcastRepository.findById(podcastRequest.getId());
        PodcastEntity podcastEntity = OptionalPodcastEntity.get();
        PodcastEntity updatedPodcastEntity = PodcastEntity.builder()
                .id(podcastEntity.getId())
                .creatorId(podcastEntity.getCreatorId())
                .title(Strings.isNullOrEmpty(podcastRequest.getTitle()) ? podcastEntity.getTitle() : podcastRequest.getTitle())
                .language(Strings.isNullOrEmpty(podcastRequest.getLanguage()) ? podcastEntity.getLanguage() : podcastRequest.getLanguage())
                .isExplicit(Objects.isNull(podcastRequest.isExplicit()) ? podcastEntity.isExplicit() : podcastRequest.isExplicit())
                .description(Strings.isNullOrEmpty(podcastRequest.getDescription()) ? podcastEntity.getDescription() : podcastRequest.getDescription())
                .coverImageUrl(Strings.isNullOrEmpty(podcastRequest.getCoverImageUrl()) ? podcastEntity.getCoverImageUrl() : podcastRequest.getCoverImageUrl())
                .createdAt(podcastEntity.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();

        podcastRepository.save(updatedPodcastEntity);
        log.info("Successfully updated podcast with id: " + podcastRequest.getId());
        return true;
    }

    @Override
    @Transactional
    public Boolean deletePodcastById(UUID podcastId) throws Exception {
        //create this im episodeservice
        Optional<PodcastEntity> podcastEntity = podcastRepository.findById(podcastId);
        if(podcastEntity.isEmpty()){
            throw new Exception("Podcast doesn't exists");
        }
        List<EpisodeEntity> episodeEntities = episodeService.getAllEpisodeByPodcastId(podcastId);
        episodeRepository.deleteAll(episodeEntities);
        podcastRepository.delete(podcastEntity.get());
        return true;
    }
}
