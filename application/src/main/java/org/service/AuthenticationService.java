package org.service;

import org.dto.Account;
import org.Models.AccountRepository;
import org.security.PasswordUtils;

import java.sql.SQLException;

public class AuthenticationService {

    private AccountRepository accountRepository = new AccountRepository();

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
