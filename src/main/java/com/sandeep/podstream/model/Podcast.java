package com.sandeep.podstream.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class Podcast {
    UUID id;
    UUID creatorId;
    String title;
    String description;
    String coverImageUrl;
    String language;
    boolean isExplicit;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
