package com.sandeep.podstream.repository;

import com.sandeep.podstream.core.entity.PodcastEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PodcastRepository extends JpaRepository<PodcastEntity, UUID> {
    Optional<PodcastEntity> findByCreatorIdAndTitle(UUID creatorId, String title);
}
