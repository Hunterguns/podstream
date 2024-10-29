package com.sandeep.podstream.repository;

import com.sandeep.podstream.core.entity.PlaybackHistoryEntity;
import com.sandeep.podstream.core.entity.RatingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlaybackHistoryRepository extends JpaRepository<PlaybackHistoryEntity, UUID> {
}
