package com.sunrisedental.service;

import com.sunrisedental.dao.UserDAO;
import com.sunrisedental.model.User;

import java.sql.SQLException;

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