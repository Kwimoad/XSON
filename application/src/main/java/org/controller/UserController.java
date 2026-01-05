package org.controller;

import org.dto.User;
import org.Models.UserRepository;

import java.sql.SQLException;

/**
 * This class manages user data operations.
 */
public class UserController {

    private UserRepository userRepository = new UserRepository();

    /**
     * Update user information in the database.
     *
     * @param user the user to update
     * @return true if the update was successful, false otherwise
     */
    public boolean updateUser(User user){
        try {
            return userRepository.update(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
