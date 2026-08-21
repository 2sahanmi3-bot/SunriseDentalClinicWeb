package com.sunrisedental.service;

import com.sunrisedental.dao.UserDAO;
import com.sunrisedental.model.User;

import java.sql.SQLException;
import java.util.Optional;

public class AuthenticationService {

    private UserDAO userDAO;

    public AuthenticationService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean authenticate(
            String username,
            String password)
            throws SQLException {

        Optional<User> user =
                userDAO.findByUsername(username);

        if (user.isEmpty()) {
            return false;
        }

        return user.get()
                .getPassword()
                .equals(password);
    }
}