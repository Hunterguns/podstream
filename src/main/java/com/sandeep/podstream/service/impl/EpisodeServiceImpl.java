package com.sandeep.podstream.service.impl;

import com.sandeep.podstream.core.entity.EpisodeEntity;
import com.sandeep.podstream.model.Episode;
import com.sandeep.podstream.model.requests.EpisodeRequest;
import com.sandeep.podstream.repository.EpisodeRepository;
import com.sandeep.podstream.service.EpisodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class EpisodeServiceImpl implements EpisodeService {

    private final EpisodeRepository episodeRepository;

    @Override
    public Episode createEpisode(EpisodeRequest episodeRequest) {
        return null;
    }

    @Override
    public List<EpisodeEntity> getAllEpisodeByPodcastId(UUID podcastId){
        return episodeRepository.findAllByPodcastId(podcastId);
    }
}
