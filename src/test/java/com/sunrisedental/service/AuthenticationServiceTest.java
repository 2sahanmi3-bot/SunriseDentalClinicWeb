package com.sunrisedental.service;

import com.sunrisedental.dao.UserDAO;
import com.sunrisedental.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    private UserDAO userDAO;
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        userDAO = mock(UserDAO.class);
        authenticationService =
                new AuthenticationService(userDAO);
    }

    @Test
    void authenticateShouldReturnTrueForValidCredentials()
            throws SQLException {

        // Use credentials that belong to an authorised staff user.
        String username = "admin";
        String password = "admin123";

        User user =
                new User(
                        1,
                        "admin",
                        "admin123"
                );

        when(userDAO.findByUsername(username))
                .thenReturn(Optional.of(user));

        boolean result =
                authenticationService.authenticate(
                        username,
                        password
                );

        assertTrue(result);

        verify(userDAO)
                .findByUsername(username);
    }
}