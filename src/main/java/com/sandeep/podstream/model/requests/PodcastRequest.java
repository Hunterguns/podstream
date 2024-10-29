package com.sandeep.podstream.model.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sandeep.podstream.core.entity.PodcastEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Function;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class PodcastRequest {
    UUID id;
    UUID creatorId;
    String title;
    String description;
    String coverImageUrl;
    String language;
    boolean isExplicit;

    public static final Function<PodcastRequest, PodcastEntity> toPodcastEntity = podcastRequest ->
            PodcastEntity.builder()
                    .creatorId(podcastRequest.getCreatorId())
                    .title(podcastRequest.getTitle())
                    .description(podcastRequest.getDescription())
                    .coverImageUrl(podcastRequest.coverImageUrl)
                    .language(podcastRequest.getLanguage())
                    .isExplicit(podcastRequest.isExplicit())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
}
