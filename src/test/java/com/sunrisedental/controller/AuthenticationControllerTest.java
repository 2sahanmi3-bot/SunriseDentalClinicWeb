package com.sunrisedental.controller;

import com.sunrisedental.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
}