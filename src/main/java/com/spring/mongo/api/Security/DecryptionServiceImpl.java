package com.spring.mongo.api.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;
import java.util.Base64;

public class DecryptionServiceImpl {

    private static final String SECRET_KEY = "8clF4dyq1kll58gvFVGtdwHCjmiPjojO";
    private static final String SALT = "678025308de70905";
    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int KEY_SIZE = 256;
    private static final int IV_SIZE = 12;
    private static final int TAG_BIT_LENGTH = 128;

    public String decrypt(String encryptedText) throws Exception {
        byte[] decodedEncryptedText = Base64.getDecoder().decode(encryptedText);

        byte[] iv = new byte[IV_SIZE];
        System.arraycopy(decodedEncryptedText, 0, iv, 0, iv.length);

        byte[] cipherText = new byte[decodedEncryptedText.length - IV_SIZE];
        System.arraycopy(decodedEncryptedText, IV_SIZE, cipherText, 0, cipherText.length);

        SecretKeySpec secretKey = getSecretKey();

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(TAG_BIT_LENGTH, iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);

        byte[] decryptedText = cipher.doFinal(cipherText);
        return new String(decryptedText);
    }

    private SecretKeySpec getSecretKey() throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, KEY_SIZE);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }
}