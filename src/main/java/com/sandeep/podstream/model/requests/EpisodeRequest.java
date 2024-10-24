package com.sandeep.podstream.model.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sandeep.podstream.core.entity.EpisodeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;
import java.util.function.Function;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class EpisodeRequest {
    UUID id;
    UUID podcastId;
    String title;
    String description;
    String audioFileUrl;
    String duration;
    LocalDate publishDate;
    int episodeNumber;
    int seasonNumber;
//    File audioFile;
//    String fileName;
//    String mimeType;

    public static final Function<EpisodeRequest, EpisodeEntity> toEpisodeEntity = episodeRequest ->
            EpisodeEntity.builder()
                    .podcastId(episodeRequest.getPodcastId())
                    .title(episodeRequest.getTitle())
                    .description(episodeRequest.getDescription())
                    .audioFileUrl(episodeRequest.getAudioFileUrl())
                    .duration(Duration.parse(episodeRequest.getDuration()).toSeconds())
                    .publishDate(episodeRequest.getPublishDate())
                    .episodeNumber(episodeRequest.getEpisodeNumber())
                    .seasonNumber(episodeRequest.getSeasonNumber())
                    .build();
}
