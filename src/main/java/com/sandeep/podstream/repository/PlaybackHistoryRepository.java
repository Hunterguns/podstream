package com.sandeep.podstream.repository;

import com.sandeep.podstream.core.entity.PlaybackHistoryEntity;
import com.sandeep.podstream.core.entity.RatingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlaybackHistoryRepository extends JpaRepository<PlaybackHistoryEntity, UUID> {
}
