package org.controller;

import org.dto.Account;
import org.Models.AccountRepository;
import org.security.PasswordUtils;

import java.sql.SQLException;

public class AccountController {

    private AccountRepository accountRepository = new AccountRepository();

    public Account getAccount(int id){
        try {
            return accountRepository.findById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
