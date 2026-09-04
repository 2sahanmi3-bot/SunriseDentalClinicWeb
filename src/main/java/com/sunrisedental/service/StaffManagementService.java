package com.sunrisedental.service;

import com.sunrisedental.dao.UserDAO;
import com.sunrisedental.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class StaffManagementService {

    private UserDAO userDAO;

    public StaffManagementService(
            UserDAO userDAO) {

        this.userDAO =
                userDAO;
    }

    public boolean createStaffUser(
            String username,
            String password,
            String role)
            throws SQLException {

        Optional<User> existingUser =
                userDAO.findByUsername(
                        username
                );

        if (existingUser.isPresent()) {

            throw new IllegalArgumentException(
                    "Username already exists"
            );
        }

        // Only the two supported staff roles can be saved.
        if (!"ADMIN".equals(role)
                && !"STAFF".equals(role)) {

            throw new IllegalArgumentException(
                    "Invalid user role"
            );
        }

        User user =
                new User(
                        0,
                        username,
                        password,
                        role
                );

        return userDAO.saveUser(
                user
        );
    }

    public List<User> getAllUsers()
            throws SQLException {

        return userDAO.findAllUsers();
    }

    public boolean changeUserRole(
            int userId,
            String role)
            throws SQLException {

        if (!"ADMIN".equals(role)
                && !"STAFF".equals(role)) {

            throw new IllegalArgumentException(
                    "Invalid user role"
            );
        }

        return userDAO.updateUserRole(
                userId,
                role
        );
    }
}