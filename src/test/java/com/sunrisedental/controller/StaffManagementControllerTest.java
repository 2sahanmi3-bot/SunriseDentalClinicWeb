package com.sunrisedental.controller;

import com.sunrisedental.service.StaffManagementService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

class StaffManagementControllerTest {

    private StaffManagementService staffManagementService;
    private StaffManagementController controller;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() {

        staffManagementService =
                mock(StaffManagementService.class);

        controller =
                new StaffManagementController(
                        staffManagementService
                );

        request =
                mock(HttpServletRequest.class);

        response =
                mock(HttpServletResponse.class);

        dispatcher =
                mock(RequestDispatcher.class);
    }

    @Test
    void postShouldCreateStaffAccount()
            throws Exception {

        when(request.getParameter("username"))
                .thenReturn("staff01");

        when(request.getParameter("password"))
                .thenReturn("staff123");

        when(request.getParameter("role"))
                .thenReturn("STAFF");

        when(
                staffManagementService
                        .createStaffUser(
                                "staff01",
                                "staff123",
                                "STAFF"
                        )
        ).thenReturn(
                true
        );

        when(
                request.getRequestDispatcher(
                        "/WEB-INF/view/manageStaff.jsp"
                )
        ).thenReturn(
                dispatcher
        );

        controller.doPost(
                request,
                response
        );

        verify(
                staffManagementService
        ).createStaffUser(
                "staff01",
                "staff123",
                "STAFF"
        );

        verify(request)
                .setAttribute(
                        "successMessage",
                        "Staff account created successfully"
                );

        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }
}