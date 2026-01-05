package org.controller;

import org.dto.*;
import org.Models.AccountRepository;
import org.Models.ArchivesRepository;
import org.Models.ArchivesUsersRepository;
import org.Models.UserRepository;
import org.security.PasswordUtils;
import org.utils.ValidationUtils;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * This class manages the creation of a new user account.
 */
public class CreateAccountController {

    /**
     * Create a complete user account (account, user, and archives).
     *
     * @param firstName user's first name
     * @param lastName user's last name
     * @param dateOfBirth user's date of birth (dd/MM/yyyy)
     * @param gender user's gender
     * @param email user's email
     * @param password user's password
     * @return created User object, or null if data is invalid
     */
    public User create(String firstName, String lastName, String dateOfBirth, String gender,
                       String email, String password){

        if(ValidationUtils.isNotEmpty(firstName) && ValidationUtils.isNotEmpty(lastName) &&
                ValidationUtils.isNotEmpty(dateOfBirth) && ValidationUtils.isNotEmpty(gender) &&
                ValidationUtils.isNotEmpty(email) && ValidationUtils.isNotEmpty(password)){
            Account account = createAccount(email,password);
            System.out.println("Account créé: " + account.getId());
            User user = createUser(firstName, lastName, dateOfBirth, gender, account);
            System.out.println("User créé: " + user.getId());
            Archives archives = createArchives();
            System.out.println("Archives créés: " + archives.getId());
            createArchivesUsers(user, archives);
            return user;
        }
        return null;

    }

    /**
     * Create an account with email and password.
     *
     * @param email account email
     * @param password account password
     * @return created Account object
     */
    public Account createAccount(String email, String password){

        AccountRepository accountRepository = new AccountRepository();
        String passwordHashe = PasswordUtils.hash(password);
        Account account = new Account(email, passwordHashe);
        try {
            return accountRepository.add(account);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Create a user linked to an account.
     *
     * @param firstName user's first name
     * @param lastName user's last name
     * @param dateOfBirth user's date of birth
     * @param gender user's gender
     * @param account linked account
     * @return created User object
     */
    public User createUser(String firstName, String lastName, String dateOfBirth, String gender, Account account){
        UserRepository userRepository = new UserRepository();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            java.util.Date utilDate = formatter.parse(dateOfBirth);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            Gender gen = (gender.equals("Male")) ? Gender.MALE : Gender.FEMALE;
            User user = new User(firstName, lastName, gen, sqlDate);
            return userRepository.add(user, account);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Create user archives.
     *
     * @return created Archives object
     */
    public Archives createArchives(){
        ArchivesRepository archivesRepository = new ArchivesRepository();
        try {
            return archivesRepository.add();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Link a user to archives.
     *
     * @param user the user
     * @param archives the archives
     * @return true if the link is created, false otherwise
     */
    public boolean createArchivesUsers(User user, Archives archives){
        ArchivesUsersRepository archivesUsersRepository = new ArchivesUsersRepository();
        try {
            return archivesUsersRepository.add(user, archives);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
