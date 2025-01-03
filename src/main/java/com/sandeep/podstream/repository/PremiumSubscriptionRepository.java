package com.sandeep.podstream.repository;

import com.sandeep.podstream.core.entity.PremiumSubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PremiumSubscriptionRepository extends JpaRepository<PremiumSubscriptionEntity, UUID> {
}
