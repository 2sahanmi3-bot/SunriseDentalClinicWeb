package com.sunrisedental.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

class AuthenticationFilterTest {

    private AuthenticationFilter filter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain chain;

    @BeforeEach
    void setUp() {

        filter =
                new AuthenticationFilter();

        request =
                mock(HttpServletRequest.class);

        response =
                mock(HttpServletResponse.class);

        chain =
                mock(FilterChain.class);
    }

    @Test
    void unauthenticatedRequestShouldRedirectToLogin()
            throws Exception {

        // No existing session means the user is not authenticated.
        when(request.getSession(false))
                .thenReturn(null);

        filter.doFilter(
                request,
                response,
                chain
        );

        verify(response)
                .sendRedirect(
                        "login.jsp"
                );

        verify(chain, never())
                .doFilter(
                        request,
                        response
                );
    }
}