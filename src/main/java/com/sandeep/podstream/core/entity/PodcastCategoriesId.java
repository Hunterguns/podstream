package com.sandeep.podstream.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@EqualsAndHashCode(callSuper = false)
public class PodcastCategoriesId implements Serializable {
    @Column(name = "podcast_id")
    private UUID podcastId;
    @Column(name = "category_id")
    private UUID categoryId;
}
