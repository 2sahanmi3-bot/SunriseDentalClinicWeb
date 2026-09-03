package com.sunrisedental.service;

import com.sunrisedental.dao.UserDAO;
import com.sunrisedental.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
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

    @Test
    void authenticateShouldReturnFalseForInvalidPassword()
            throws SQLException {

        // Use a valid username with the wrong password.
        String username = "admin";
        String password = "wrong123";

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

        assertFalse(result);

        verify(userDAO)
                .findByUsername(username);
    }

    @Test
    void authenticateShouldRejectBlankCredentials()
            throws SQLException {

        // Use empty login details to represent invalid staff input.
        String username = "";
        String password = "";

        boolean result =
                authenticationService.authenticate(
                        username,
                        password
                );

        assertFalse(result);

        verify(userDAO, never())
                .findByUsername(anyString());
    }

    @Test
    void authenticateShouldReturnFalseWhenUsernameDoesNotExist()
            throws SQLException {

        // Use a username that is not registered as a staff user.
        String username = "unknown";
        String password = "admin123";

        when(userDAO.findByUsername(username))
                .thenReturn(Optional.empty());

        boolean result =
                authenticationService.authenticate(
                        username,
                        password
                );

        assertFalse(result);

        verify(userDAO)
                .findByUsername(username);
    }

    @Test
    void shouldReturnAuthenticatedUserWithRole()
            throws Exception {

        User user =
                new User(
                        1,
                        "admin",
                        "admin123",
                        "ADMIN"
                );

        when(
                userDAO.findByUsername(
                        "admin"
                )
        ).thenReturn(
                Optional.of(user)
        );

        Optional<User> result =
                authenticationService.authenticateUser(
                        "admin",
                        "admin123"
                );

        assertTrue(
                result.isPresent()
        );

        assertEquals(
                "admin",
                result.get().getUsername()
        );

        assertEquals(
                "ADMIN",
                result.get().getRole()
        );

        verify(
                userDAO
        ).findByUsername(
                "admin"
        );
    }
}