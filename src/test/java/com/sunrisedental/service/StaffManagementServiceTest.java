package com.sunrisedental.service;

import com.sunrisedental.dao.UserDAO;
import com.sunrisedental.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import java.util.Optional;

class StaffManagementServiceTest {

    private UserDAO userDAO;
    private StaffManagementService staffManagementService;

    @BeforeEach
    void setUp() {

        userDAO =
                mock(UserDAO.class);

        staffManagementService =
                new StaffManagementService(
                        userDAO
                );
    }

    @Test
    void shouldCreateValidStaffUser()
            throws Exception {

        when(
                userDAO.saveUser(
                        any(User.class)
                )
        ).thenReturn(
                true
        );

        boolean result =
                staffManagementService.createStaffUser(
                        "staff01",
                        "staff123",
                        "STAFF"
                );

        assertTrue(
                result
        );

        verify(userDAO)
                .saveUser(
                        argThat(user ->
                                "staff01".equals(
                                        user.getUsername()
                                )
                                        && BCrypt.checkpw(
                                        "staff123",
                                        user.getPassword()
                                )
                                        && "STAFF".equals(
                                        user.getRole()
                                )
                        )
                );
    }

    @Test
    void shouldRejectDuplicateUsername()
            throws Exception {

        User existingUser =
                new User(
                        2,
                        "staff01",
                        "staff123",
                        "STAFF"
                );

        when(
                userDAO.findByUsername(
                        "staff01"
                )
        ).thenReturn(
                Optional.of(existingUser)
        );

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () ->
                                staffManagementService
                                        .createStaffUser(
                                                "staff01",
                                                "newpass123",
                                                "STAFF"
                                        )
                );

        assertEquals(
                "Username already exists",
                exception.getMessage()
        );

        verify(userDAO)
                .findByUsername(
                        "staff01"
                );

        verify(
                userDAO,
                never()
        ).saveUser(
                any(User.class)
        );
    }

    @Test
    void shouldRejectInvalidRole()
            throws Exception {

        when(
                userDAO.findByUsername(
                        "staff02"
                )
        ).thenReturn(
                Optional.empty()
        );

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () ->
                                staffManagementService
                                        .createStaffUser(
                                                "staff02",
                                                "staff123",
                                                "MANAGER"
                                        )
                );

        assertEquals(
                "Invalid user role",
                exception.getMessage()
        );

        verify(
                userDAO,
                never()
        ).saveUser(
                any(User.class)
        );
    }
}
