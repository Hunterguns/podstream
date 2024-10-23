package com.sandeep.podstream.core.config;

import com.sandeep.podstream.utils.EncryptionUtils;
import jakarta.persistence.AttributeConverter;
import org.springframework.beans.factory.annotation.Autowired;

public class Encrypt implements AttributeConverter<String, String> {

    @Autowired
    EncryptionUtils encryptionUtils;

    @Override
    public String convertToDatabaseColumn(String s) {
        return encryptionUtils.encrypt(s);
    }

    @Override
    public String convertToEntityAttribute(String s) {
        return encryptionUtils.decrypt(s);
    }
}
