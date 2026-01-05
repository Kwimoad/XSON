package org.controller;

import org.dto.Account;
import org.dto.User;
import org.Models.UserRepository;
import org.service.AuthenticationService;
import org.utils.ValidationUtils;

import java.sql.SQLException;

/**
 * This class manages user authentication.
 */
public class AuthenticationController {

    private AuthenticationService authService = new AuthenticationService();

    /**
     * Authenticate a user using email and password.
     *
     * @param email user email
     * @param password user password
     * @return User object if login is successful, null otherwise
     */
    public User login(String email, String password) {
        if(ValidationUtils.isEmail(email) && ValidationUtils.isNotEmpty(password)){
            Account account = authService.authenticate(email, password);
            UserRepository userRepository = new UserRepository();
            try {
                User user = userRepository.findByFk(account.getId());
                user.setAccount(account);
                return user;
            } catch (SQLException e) {
                return null;
            }
        }
        return null;
    }

}
