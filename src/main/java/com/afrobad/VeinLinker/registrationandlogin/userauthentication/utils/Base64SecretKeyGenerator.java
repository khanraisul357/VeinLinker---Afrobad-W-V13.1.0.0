package com.afrobad.VeinLinker.registrationandlogin.userauthentication.utils;

import java.security.SecureRandom;
import java.util.Base64;

// Generates a random 256-bit secret and converts it to Base64
public class Base64SecretKeyGenerator {

    public static void main(String[] args) {

        // 32 bytes = 256 bits
        byte[] key = new byte[32];

        // Generate cryptographically secure random bytes
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(key);

        // Convert random bytes to Base64
        String base64Secret = Base64.getEncoder()
                .encodeToString(key);

        // Print the secret
        System.out.println("Generated JWT Secret:");
        System.out.println(base64Secret);
    }
}