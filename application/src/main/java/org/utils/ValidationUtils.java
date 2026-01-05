package org.utils;

/**
 * Utility class for common validation methods.
 */
public class ValidationUtils {

    /**
     * Checks if a string is a valid email address.
     * @param email The string to check.
     * @return true if the string is a valid email, false otherwise.
     */
    public static boolean isEmail(String email){
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    /**
     * Checks if a string is not null and not empty after trimming.
     * @param text The string to check.
     * @return true if the string is not empty, false otherwise.
     */
    public static boolean isNotEmpty(String text) {
        return text != null && !text.trim().isEmpty();
    }

    /**
     * Checks if a password is strong.
     * A strong password has at least 8 characters and contains:
     * - At least one uppercase letter
     * - At least one lowercase letter
     * - At least one digit
     * - At least one special character (@#$%^&+=)
     * @param password The password string to check.
     * @return true if the password is strong, false otherwise.
     */
    public static boolean isPasswordStrong(String password) {
        if (password.length() < 8) return false;

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            if (Character.isLowerCase(c)) hasLower = true;
            if (Character.isDigit(c)) hasDigit = true;
            if ("@#$%^&+=".contains(String.valueOf(c))) hasSpecial = true;
        }

        return hasUpper && hasLower && hasDigit && hasSpecial;
    }

}
