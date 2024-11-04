package com.sandeep.podstream.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static com.sandeep.podstream.constants.ApplicationConstants.AES_ALGORITHM;

@Component
@Slf4j
public class EncryptionUtils {
    @Value("${security.encryption.key}")
    private String encryptionKey;

    @Value("${security.encryption.initVector}")
    private String encryptionInitVector;

    @Value("${security.encryption.algo}")
    private String encryptionAlgo;

    public String encrypt(String value){
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(encryptionInitVector.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec secretKeySpec = new SecretKeySpec(encryptionKey.getBytes(StandardCharsets.UTF_8), AES_ALGORITHM);

            Cipher cipher = Cipher.getInstance(encryptionAlgo);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            log.error("Error while encrypting data: ", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String decrypt(String encryptedData){
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(encryptionInitVector.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec secretKeySpec = new SecretKeySpec(encryptionKey.getBytes(StandardCharsets.UTF_8), AES_ALGORITHM);

            Cipher cipher = Cipher.getInstance(encryptionAlgo);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decrypted, StandardCharsets.UTF_8);
        }catch (Exception e) {
            log.error("Error while decrypting data: ", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
