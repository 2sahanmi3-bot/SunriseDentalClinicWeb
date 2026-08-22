package com.sunrisedental.controller;

import com.sunrisedental.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

class AuthenticationControllerTest {

    private AuthenticationService authenticationService;
    private AuthenticationController controller;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    @BeforeEach
    void setUp() {

        authenticationService =
                mock(AuthenticationService.class);

        controller =
                new AuthenticationController(
                        authenticationService
                );

        request =
                mock(HttpServletRequest.class);

        response =
                mock(HttpServletResponse.class);

        session =
                mock(HttpSession.class);
    }

    @Test
    void loginShouldCreateSessionAndRedirectForValidCredentials()
            throws Exception {

        String username = "admin";
        String password = "admin123";

        when(request.getParameter("username"))
                .thenReturn(username);

        when(request.getParameter("password"))
                .thenReturn(password);

        when(authenticationService.authenticate(
                username,
                password))
                .thenReturn(true);

        when(request.getSession())
                .thenReturn(session);

        controller.doPost(
                request,
                response
        );

        verify(authenticationService)
                .authenticate(
                        username,
                        password
                );

        verify(session)
                .setAttribute(
                        "staffUser",
                        username
                );

        verify(response)
                .sendRedirect("appointment");
    }
}