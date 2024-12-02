package com.sandeep.podstream.controller;

import com.sandeep.podstream.model.Episode;
import com.sandeep.podstream.model.requests.EpisodeRequest;
import com.sandeep.podstream.service.EpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class EpisodeController {

    private final EpisodeService episodeService;

    @MutationMapping(value = "createEpisode")
    public Episode createEpisode(@Argument EpisodeRequest episodeRequest) {
        Episode episode = episodeService.createEpisode(episodeRequest);
        return episode;
    }
}
