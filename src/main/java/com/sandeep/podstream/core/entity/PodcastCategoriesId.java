package com.sandeep.podstream.core.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@EqualsAndHashCode(callSuper = false)
public class PodcastCategoriesId implements Serializable {
    private UUID podcastId;
    private UUID categoryId;
}
