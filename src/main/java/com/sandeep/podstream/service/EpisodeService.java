package com.sandeep.podstream.service;

import com.sandeep.podstream.core.entity.EpisodeEntity;
import com.sandeep.podstream.model.Episode;
import com.sandeep.podstream.model.requests.EpisodeRequest;

import java.util.List;
import java.util.UUID;

public interface EpisodeService {
    Episode createEpisode(EpisodeRequest episodeRequest);

    List<EpisodeEntity> getAllEpisodeByPodcastId(UUID podcastId);
}
