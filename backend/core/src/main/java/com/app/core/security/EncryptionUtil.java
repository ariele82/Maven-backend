package com.app.core.security;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.io.FileInputStream;
import java.util.Properties;

public class EncryptionUtil {
    private static final String ALGORITHM = "AES";
    private static SecretKeySpec secretKey;
    
    static {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("C:/config/encryption.properties"));
            
            byte[] key = props.getProperty("encryption.key").getBytes(StandardCharsets.UTF_8);
            secretKey = new SecretKeySpec(key, ALGORITHM);
        } catch (Exception e) {
            throw new RuntimeException("Encryption config error!", e);
        }
    }
    
    public static String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }
    
    public static String decrypt(String encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decoded = Base64.getDecoder().decode(encryptedData);
        byte[] decrypted = cipher.doFinal(decoded);
        return new String(decrypted);
    }
}
