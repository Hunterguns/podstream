package com.sandeep.podstream.utils;

import lombok.extern.slf4j.Slf4j;
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
    private String key = "1234567812345678";
    private String initVector = "1234567812345678";
    private String algo = "AES/CBC/PKCS5PADDING";

    public String encrypt(String value){
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), AES_ALGORITHM);

            Cipher cipher = Cipher.getInstance(algo);
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
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), AES_ALGORITHM);

            Cipher cipher = Cipher.getInstance(algo);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return decrypted.toString();
        }catch (Exception e) {
            log.error("Error while decrypting data: ", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
