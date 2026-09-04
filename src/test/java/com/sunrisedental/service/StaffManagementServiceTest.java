package com.sunrisedental.service;

import com.sunrisedental.dao.UserDAO;
import com.sunrisedental.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

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
                                        && "staff123".equals(
                                        user.getPassword()
                                )
                                        && "STAFF".equals(
                                        user.getRole()
                                )
                        )
                );
    }
}