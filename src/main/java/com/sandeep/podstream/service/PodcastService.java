package com.sandeep.podstream.service;

import com.sandeep.podstream.model.Podcast;
import com.sandeep.podstream.model.requests.PodcastRequest;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

public interface PodcastService {
    Podcast createPodcast(PodcastRequest podcastRequest) throws Exception;

    List<Podcast> filterPodcast(PodcastRequest podcastRequest);

    Podcast getPodcastById(UUID id);

    Boolean updatePodcast(PodcastRequest podcastRequest);

    @Transactional
    Boolean deletePodcastById(UUID podcastId) throws Exception;
}
