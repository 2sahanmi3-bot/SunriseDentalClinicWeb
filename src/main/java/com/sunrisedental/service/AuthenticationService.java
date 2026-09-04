package com.sunrisedental.service;

import com.sunrisedental.dao.UserDAO;
import com.sunrisedental.model.User;
import org.mindrot.jbcrypt.BCrypt;

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

        User authenticatedUser =
                user.get();

        // Inactive accounts are not allowed to log in.
        if (!authenticatedUser.isActive()) {
            return Optional.empty();
        }

        String storedPassword =
                authenticatedUser.getPassword();

        boolean passwordMatches;

        if (storedPassword != null
                && storedPassword.startsWith("$2")) {

            passwordMatches =
                    BCrypt.checkpw(
                            password,
                            storedPassword
                    );

        } else {

            // Existing plaintext accounts are upgraded after one successful login.
            passwordMatches =
                    storedPassword != null
                            && storedPassword.equals(
                                    password
                            );

            if (passwordMatches) {

                String hashedPassword =
                        BCrypt.hashpw(
                                password,
                                BCrypt.gensalt()
                        );

                userDAO.updatePassword(
                        authenticatedUser.getUserId(),
                        hashedPassword
                );
            }
        }

        if (!passwordMatches) {
            return Optional.empty();
        }

        return Optional.of(
                authenticatedUser
        );
    }
}
