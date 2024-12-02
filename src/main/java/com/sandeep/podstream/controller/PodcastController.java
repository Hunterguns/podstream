package com.sandeep.podstream.controller;

import com.sandeep.podstream.core.entity.EpisodeEntity;
import com.sandeep.podstream.model.Episode;
import com.sandeep.podstream.model.Podcast;
import com.sandeep.podstream.model.requests.PodcastRequest;
import com.sandeep.podstream.service.EpisodeService;
import com.sandeep.podstream.service.PodcastService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class PodcastController {
    private final PodcastService podcastService;
    private final EpisodeService episodeService;

    @MutationMapping(value = "createPodcast")
    public Podcast createPodcast(@Argument PodcastRequest podcastRequest) throws Exception {
        return podcastService.createPodcast(podcastRequest);
    }

    @QueryMapping(value = "filterPodcast")
    public List<Podcast> filterPodcast(@Argument PodcastRequest podcastRequest) {
        return podcastService.filterPodcast(podcastRequest);
    }

    @MutationMapping(value = "deletePodcast")
    public String deletePodcast(@Argument UUID podcastId) throws Exception {
        Boolean status = podcastService.deletePodcastById(podcastId);
        if (status) {
            return "Podcast and related episodes deleted successfully";
        } else {
            return "Unable to delete podcast and related episodes. Something went wrong";
        }
    }

    @QueryMapping(value = "getEpisodes")
    public List<Episode> episodes(@Argument UUID podcastId) {
        List<EpisodeEntity> episodeEntities = episodeService.getAllEpisodeByPodcastId(podcastId);
        return episodeEntities.stream().map(EpisodeEntity.toEpisode::apply).toList();
    }
}
