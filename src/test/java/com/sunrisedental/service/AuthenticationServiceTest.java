package com.sunrisedental.service;

import com.sunrisedental.dao.UserDAO;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

class AuthenticationServiceTest {

    private UserDAO userDAO;
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        userDAO = mock(UserDAO.class);
        authenticationService =
                new AuthenticationService(userDAO);
    }
}