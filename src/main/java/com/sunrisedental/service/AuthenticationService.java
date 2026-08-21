package com.sunrisedental.service;

import com.sunrisedental.dao.UserDAO;

import java.sql.SQLException;

public class AuthenticationService {

    private UserDAO userDAO;

    public AuthenticationService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean authenticate(
            String username,
            String password)
            throws SQLException {

        return false;
    }
}
