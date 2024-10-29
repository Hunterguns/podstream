package com.sandeep.podstream.repository;

import com.sandeep.podstream.core.entity.PodcastCategoryEntity;
import com.sandeep.podstream.core.entity.RatingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PodcastCategoryRepository extends JpaRepository<PodcastCategoryEntity, UUID> {
}
