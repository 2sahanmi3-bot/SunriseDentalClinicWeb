package com.sunrisedental.dao;

import org.junit.jupiter.api.BeforeEach;

class UserDAOTest {

    private UserDAO userDAO;

    @BeforeEach
    void setUp() {
        userDAO = new UserDAO();
    }
}