package org.controller;

import org.dto.Account;
import org.Models.AccountRepository;
import org.security.PasswordUtils;

import java.sql.SQLException;

/**
 * This class manages user account operations.
 */
public class AccountController {

    private AccountRepository accountRepository = new AccountRepository();

    /**
     * Get an account by its ID.
     *
     * @param id account identifier
     * @return the corresponding account
     */
    public Account getAccount(int id){
        try {
            return accountRepository.findById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Change the password of an account.
     *
     * @param account the account to update
     * @param oldPassword current password
     * @param newPassword new password
     * @return true if the password is changed, false otherwise
     */
    public boolean changePassword(Account account,String oldPassword,String newPassword){
        System.out.println(account.getPassword());
        if(!PasswordUtils.verify(oldPassword,account.getPassword())){
            return false;
        }
        String password = PasswordUtils.hash(newPassword);
        account.setPassword(password);
        try {
            return accountRepository.update(account);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
