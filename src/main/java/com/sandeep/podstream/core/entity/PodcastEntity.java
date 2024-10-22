package com.sandeep.podstream.core.entity;

import io.quarkus.panache.common.Parameters;
import jakarta.persistence.*;
import lombok.*;
import org.sandeep.model.Podcast;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Function;

@Entity
@Table(name = "podcast")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
public class PodcastEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(name = "creator_id")
    private UUID creatorId;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "cover_image_url")
    private String coverImageUrl;
    @Column(name = "language")
    private String language;
    @Column(name = "is_explicit")
    private boolean isExplicit;
    @Column(name = "created_at")
    LocalDateTime createdAt;
    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    public static final Function<PodcastEntity, Podcast> toPodcast = podcastEntity ->
            Podcast.builder()
                    .id(podcastEntity.getId())
                    .creatorId(podcastEntity.getCreatorId())
                    .title(podcastEntity.getTitle())
                    .description(podcastEntity.getDescription())
                    .coverImageUrl(podcastEntity.getCoverImageUrl())
                    .language(podcastEntity.getLanguage())
                    .isExplicit(podcastEntity.isExplicit())
                    .createdAt(podcastEntity.getCreatedAt())
                    .updatedAt(podcastEntity.getUpdatedAt())
                    .build();

    public static PodcastEntity findByCreatorIdAndTitle(UUID creatorId, String title) {
        return find("creatorId = :creatorId and title = :title", Parameters.with("creatorId", creatorId).and("title", title)).firstResult();
    }
}
