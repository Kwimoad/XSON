package org.security;

public class PasswordUtils {

    public static String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(10));
    }

    public static boolean verify(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }

}
