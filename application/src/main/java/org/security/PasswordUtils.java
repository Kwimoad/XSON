package org.security;

/**
 * PasswordUtils class provides methods to hash passwords and verify them.
 * This is used to securely store passwords in the database.
 */
public class PasswordUtils {

    /**
     * Hashes a password using BCrypt algorithm.
     * @param password The plain text password
     * @return The hashed password
     */
    public static String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(10));
    }

    /**
     * Verifies a plain password against a hashed password.
     * @param password The plain text password
     * @param hash The hashed password from the database
     * @return true if the password matches the hash, false otherwise
     */
    public static boolean verify(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }

}
