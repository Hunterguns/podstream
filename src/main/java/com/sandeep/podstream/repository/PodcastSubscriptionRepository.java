package com.sandeep.podstream.repository;

import com.sandeep.podstream.core.entity.PodcastSubscriptionEntity;
import com.sandeep.podstream.core.entity.RatingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PodcastSubscriptionRepository extends JpaRepository<PodcastSubscriptionEntity, UUID> {
}
