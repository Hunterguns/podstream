package com.sandeep.podstream.core.entity;

import com.sandeep.podstream.model.Episode;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Entity
@Table(name = "episode")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
public class EpisodeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(name = "podcast_id")
    private UUID podcastId;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "audio_file_url")
    private String audioFileUrl;
    @Column(name = "duration")
    private long duration;
    @Column(name = "publish_date")
    private LocalDate publishDate;
    @Column(name = "episode_number")
    private int episodeNumber;
    @Column(name = "season_number")
    private int seasonNumber;


    public static final Function<EpisodeEntity, Episode> toEpisode = episodeEntity -> Episode.builder()
            .id(episodeEntity.getId())
            .podcastId(episodeEntity.getPodcastId())
            .title(episodeEntity.getTitle())
            .description(episodeEntity.getDescription())
            .audioFileUrl(episodeEntity.audioFileUrl)
            .duration(episodeEntity.getDuration())
            .publishDate(episodeEntity.getPublishDate())
            .episodeNumber(episodeEntity.getEpisodeNumber())
            .seasonNumber(episodeEntity.getSeasonNumber())
            .build();
}
