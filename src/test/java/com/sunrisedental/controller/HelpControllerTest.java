package com.sunrisedental.controller;

import org.junit.jupiter.api.BeforeEach;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

class HelpControllerTest {

    private HelpController controller;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() {

        controller =
                new HelpController();

        request =
                mock(HttpServletRequest.class);

        response =
                mock(HttpServletResponse.class);

        dispatcher =
                mock(RequestDispatcher.class);
    }

    @Test
    void helpRequestShouldForwardToHelpPage()
            throws Exception {

        when(request.getRequestDispatcher(
                "help.jsp"))
                .thenReturn(dispatcher);

        controller.doGet(
                request,
                response
        );

        verify(request)
                .getRequestDispatcher(
                        "help.jsp"
                );

        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }
}

