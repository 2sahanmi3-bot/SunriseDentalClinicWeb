package com.sunrisedental.service;

import com.sunrisedental.dao.UserDAO;
import com.sunrisedental.model.User;

import java.sql.SQLException;

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
}