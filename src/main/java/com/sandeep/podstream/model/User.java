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
public class User {
    public UUID id;
    public String username;
    public String email;
    public String userType;
    public String password;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

}
