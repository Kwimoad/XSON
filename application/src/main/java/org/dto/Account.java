package org.dto;

/**
 * The Account class represents a user account.
 * Each account has a unique ID, an email, and a password.
 */
public class Account {

    private final int id;
    private String email;
    private String password;

    /**
     * Constructor to create an account with a specific ID.
     *
     * @param id
     * @param email
     * @param password
     */
    public Account(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    /**
     * Constructor to create an account without a specific ID.
     * The ID is set to -1 (undefined).
     *
     * @param email
     * @param password
     */
    public Account(String email, String password) {
        this.id = -1;
        this.email = email;
        this.password = password;
    }

    // setters getters

    /**
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
