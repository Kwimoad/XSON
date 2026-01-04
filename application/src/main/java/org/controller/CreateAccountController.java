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

public class CreateAccountController {

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

    public Archives createArchives(){
        ArchivesRepository archivesRepository = new ArchivesRepository();
        try {
            return archivesRepository.add();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean createArchivesUsers(User user, Archives archives){
        ArchivesUsersRepository archivesUsersRepository = new ArchivesUsersRepository();
        try {
            return archivesUsersRepository.add(user, archives);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
