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

        return authenticateUser(
                username,
                password
        ).isPresent();
    }

    public Optional<User> authenticateUser(
            String username,
            String password)
            throws SQLException {

        // Reject empty login details before checking the database.
        if (username == null
                || username.isBlank()
                || password == null
                || password.isBlank()) {

            return Optional.empty();
        }

        Optional<User> user =
                userDAO.findByUsername(
                        username
                );

        if (user.isEmpty()) {
            return Optional.empty();
        }

        // Inactive accounts are not allowed to log in.
        if (!user.get().isActive()) {
            return Optional.empty();
        }

        if (!user.get()
                .getPassword()
                .equals(password)) {

            return Optional.empty();
        }

        return user;
    }
}