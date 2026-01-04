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

    public User(int id, String firstName, String lastName, Date dateOfBirth, Gender gender, Account account, Archives archives) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.account = account;
        this.archives = archives;
    }

    public User(int id, String firstName, String lastName, Date dateOfBirth, Gender gender, Account account) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.account = account;
    }

    public User(int id, String firstName, String lastName, Gender gender, Date dateOfBirth) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public User(String firstName, String lastName, Gender gender, Date dateOfBirth) {
        this.id = -1;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
