package com.sandeep.podstream.repository;

import com.sandeep.podstream.core.entity.PodcastEntity;
import com.sandeep.podstream.core.entity.RatingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PodcastRepository extends JpaRepository<PodcastEntity, UUID> {
    Optional<PodcastEntity> findByCreatorIdAndTitle(UUID creatorId, String title);
}
