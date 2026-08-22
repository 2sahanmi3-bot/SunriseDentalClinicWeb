package com.sunrisedental.controller;

import com.sunrisedental.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

class AuthenticationControllerTest {

    private AuthenticationService authenticationService;
    private AuthenticationController controller;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

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

        dispatcher =
                mock(RequestDispatcher.class);
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
    @Test
    void loginShouldReturnToLoginPageForInvalidCredentials()
            throws Exception {

        String username = "admin";
        String password = "wrong123";

        when(request.getParameter("username"))
                .thenReturn(username);

        when(request.getParameter("password"))
                .thenReturn(password);

        when(authenticationService.authenticate(
                username,
                password))
                .thenReturn(false);

        when(request.getRequestDispatcher(
                "login.jsp"))
                .thenReturn(dispatcher);

        controller.doPost(
                request,
                response
        );

        verify(authenticationService)
                .authenticate(
                        username,
                        password
                );

        verify(request)
                .setAttribute(
                        "errorMessage",
                        "Invalid username or password"
                );

        verify(dispatcher)
                .forward(
                        request,
                        response
                );

        verify(request, never())
                .getSession();
    }

}

