package com.sandeep.podstream.repository;

import com.sandeep.podstream.core.entity.PodcastSubscriptionEntity;
import com.sandeep.podstream.core.entity.RatingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PodcastSubscriptionRepository extends JpaRepository<PodcastSubscriptionEntity, UUID> {
}
