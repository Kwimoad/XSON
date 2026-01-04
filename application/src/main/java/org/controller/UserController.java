package org.controller;

import org.dto.User;
import org.Models.UserRepository;

import java.sql.SQLException;

public class UserController {

    private UserRepository userRepository = new UserRepository();

    public boolean updateUser(User user){
        try {
            return userRepository.update(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
