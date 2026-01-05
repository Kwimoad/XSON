package org.service;

import org.dto.Account;
import org.Models.AccountRepository;
import org.security.PasswordUtils;

import java.sql.SQLException;

/**
 * AuthenticationService handles user login by checking email and password.
 */
public class AuthenticationService {

    private AccountRepository accountRepository = new AccountRepository();

    /**
     * Authenticates a user by email and password.
     * @param email The email provided by the user
     * @param password The plain text password provided by the user
     * @return The Account if authentication is successful, or null if failed
     */
    public Account authenticate(String email, String password) {
        Account account = null;
        try {
            account = accountRepository.find(email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (account == null) return null;
        if(PasswordUtils.verify(password, account.getPassword())){
            return account;
        }
        return null;
    }

}
