package com.sandeep.podstream.core.config;

import jakarta.persistence.AttributeConverter;

import java.time.Duration;

public class DurationConverter implements AttributeConverter<String, Long> {
    @Override
    public Long convertToDatabaseColumn(String s) {
        return Duration.parse(s).toSeconds();
    }

    @Override
    public String convertToEntityAttribute(Long i) {
        return String.valueOf(Duration.ofSeconds(i).toHours());
    }
}
