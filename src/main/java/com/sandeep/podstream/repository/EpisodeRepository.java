package com.sandeep.podstream.repository;

import com.sandeep.podstream.core.entity.EpisodeEntity;
import com.sandeep.podstream.core.entity.RatingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EpisodeRepository extends JpaRepository<EpisodeEntity, UUID> {
    List<EpisodeEntity> findAllByPodcastId(UUID podcastId);
}
