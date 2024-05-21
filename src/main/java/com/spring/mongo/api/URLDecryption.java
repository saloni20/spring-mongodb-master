package com.spring.mongo.api;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.util.Base64;

public class URLDecryption {
    private static final String AES_KEY = "yourtesecretkeys"; // 16, 24, or 32 bytes
    private static final String ENCRT_SAT = "TESTAUTESTAUUTED"; // 16, 24, or 32 bytes

    public static String decryptURL(String encryptedURL) throws Exception {
//        String decodedURL = URLDecoder.decode(encryptedURL, "UTF-8");
//        decodedURL = decodedURL.replace('-', '+').replace('_', '/');
//        byte[] encryptedBytes = Base64.getDecoder().decode(decodedURL);
//        SecretKeySpec secretKeySpec = new SecretKeySpec(AES_KEY.getBytes(), "AES");
//        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); // Use ECB mode
//        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
//        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
//        return new String(decryptedBytes);

        String decodedURL = URLDecoder.decode(encryptedURL, "UTF-8");
        decodedURL = decodedURL.replace('-', '+').replace('_', '/');
        byte[] encryptedBytes = Base64.getDecoder().decode(decodedURL);
        byte[] keyBytes = AES_KEY.getBytes("UTF-8");
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        byte[] ivBytes = encryptionSalt.getBytes(StandardCharsets.UTF_8);
//        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
//        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, "UTF-8");

    }

    public static void main(String[] args) {
        try {
            // Sample encrypted URL received from client
            String encryptedURL = "encryptedurlstty"; // Replace with actual encrypted URL

            // Decrypt the URL
            String decryptedURL = decryptURL(encryptedURL);

            System.out.println("Decrypted URL: " + decryptedURL);

            // Search across endpoints based on decrypted URL parameters...
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}