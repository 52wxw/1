package com.example.utils;

import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordEncoder {

    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";

    public static String encode(String password) {
        try {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);

            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] hash = factory.generateSecret(spec).getEncoded();

            byte[] hashWithSalt = new byte[salt.length + hash.length];
            System.arraycopy(salt, 0, hashWithSalt, 0, salt.length);
            System.arraycopy(hash, 0, hashWithSalt, salt.length, hash.length);

            return Base64.getEncoder().encodeToString(hashWithSalt);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean matches(String password, String encodedPassword) {
        try {
            byte[] hashWithSalt = Base64.getDecoder().decode(encodedPassword);
            byte[] salt = new byte[16];
            System.arraycopy(hashWithSalt, 0, salt, 0, salt.length);

            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] hash = factory.generateSecret(spec).getEncoded();

            if (hash.length + salt.length != hashWithSalt.length) {
                return false;
            }

            for (int i = 0; i < hash.length; i++) {
                if (hash[i] != hashWithSalt[i + salt.length]) {
                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}    