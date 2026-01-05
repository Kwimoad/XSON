package org.dto;

import java.sql.Date;
import java.util.List;

public class User {

    private final int id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private Gender gender;
    private Account account;
    private Archives archives;

    /**
     * Constructor to create an user with a specific ID and archives and account
     *
     * @param id
     * @param firstName
     * @param lastName
     * @param dateOfBirth
     * @param gender
     * @param account
     * @param archives
     */
    public User(int id, String firstName, String lastName, Date dateOfBirth, Gender gender, Account account, Archives archives) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.account = account;
        this.archives = archives;
    }

    /**
     * Constructor to create an user with a specific ID and account and without archives
     *
     * @param id
     * @param firstName
     * @param lastName
     * @param dateOfBirth
     * @param gender
     * @param account
     */
    public User(int id, String firstName, String lastName, Date dateOfBirth, Gender gender, Account account) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.account = account;
    }

    /**
     * Constructor to create an user with a specific ID and without account and without archives
     *
     * @param id
     * @param firstName
     * @param lastName
     * @param gender
     * @param dateOfBirth
     */
    public User(int id, String firstName, String lastName, Gender gender, Date dateOfBirth) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    /**
     * Constructor to create an user without ID and without account and without archives
     * The ID is set to -1 (undefined).
     *
     * @param firstName
     * @param lastName
     * @param gender
     * @param dateOfBirth
     */
    public User(String firstName, String lastName, Gender gender, Date dateOfBirth) {
        this.id = -1;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    /**
     *
     * @return user ID
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return user first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return user last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return user date of birth
     */
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     *
     * @param dateOfBirth
     */
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     *
     * @return Gender (HOME, FEMALE)
     */
    public Gender getGender() {
        return gender;
    }

    /**
     *
     * @param gender
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     *
     * @return account
     */
    public Account getAccount() {
        return account;
    }

    /**
     *
     * @param account
     */
    public void setAccount(Account account) {
        this.account = account;
    }
}
