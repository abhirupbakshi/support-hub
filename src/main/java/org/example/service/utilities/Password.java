package org.example.service.utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <h3>Class Password</h3>
 * This is an utility class which is used to operations on passwords.
 */
public class Password {

    /**
     * Encrypts a password with SHA-256 algorithm.
     * @param password The password. Throws IllegalArgumentException if password is null.
     * @return The encrypted password.
     */
    public static byte[] encrypt(String password) {

        if(password == null)
            throw new IllegalArgumentException("password argument cannot be null");

        try {
            return MessageDigest.getInstance("SHA-256").digest(password.getBytes());
        } catch (NoSuchAlgorithmException ignored) { }

        return null;
    }
}
