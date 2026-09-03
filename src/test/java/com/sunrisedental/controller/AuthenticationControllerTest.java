package com.sunrisedental.controller;

import com.sunrisedental.model.User;
import com.sunrisedental.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;

import org.junit.jupiter.api.Test;

import java.util.Optional;

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

        User user =
                new User(
                        1,
                        "admin",
                        "admin123",
                        "ADMIN"
                );

        when(request.getParameter("username"))
                .thenReturn(username);

        when(request.getParameter("password"))
                .thenReturn(password);

        when(authenticationService.authenticateUser(
                username,
                password))
                .thenReturn(
                        Optional.of(user)
                );

        when(request.getSession())
                .thenReturn(session);

        controller.doPost(
                request,
                response
        );

        verify(authenticationService)
                .authenticateUser(
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

        when(authenticationService.authenticateUser(
                username,
                password))
                .thenReturn(
                        Optional.empty()
                );

        when(request.getRequestDispatcher(
                "login.jsp"))
                .thenReturn(dispatcher);

        controller.doPost(
                request,
                response
        );

        verify(authenticationService)
                .authenticateUser(
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

    @Test
    void logoutShouldInvalidateSessionAndRedirectToLogin()
            throws Exception {

        when(request.getParameter("action"))
                .thenReturn("logout");

        when(request.getSession(false))
                .thenReturn(session);

        controller.doGet(
                request,
                response
        );

        verify(session)
                .invalidate();

        verify(response)
                .sendRedirect("login.jsp");
    }

    @Test
    void loginShouldStoreUserRoleInSession()
            throws Exception {

        User user =
                new User(
                        1,
                        "admin",
                        "admin123",
                        "ADMIN"
                );

        when(request.getParameter("username"))
                .thenReturn("admin");

        when(request.getParameter("password"))
                .thenReturn("admin123");

        // Keep the current controller on its successful login path for RED.
        when(authenticationService.authenticate(
                "admin",
                "admin123"))
                .thenReturn(true);

        when(authenticationService.authenticateUser(
                "admin",
                "admin123"))
                .thenReturn(
                        Optional.of(user)
                );

        when(request.getSession())
                .thenReturn(session);

        controller.doPost(
                request,
                response
        );

        verify(session)
                .setAttribute(
                        "staffUser",
                        "admin"
                );

        verify(session)
                .setAttribute(
                        "staffRole",
                        "ADMIN"
                );

        verify(response)
                .sendRedirect(
                        "appointment"
                );
    }
}