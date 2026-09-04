package com.sunrisedental.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.FilterChain;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class AuthenticationFilterTest {

    private AuthenticationFilter filter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain chain;
    private HttpSession session;

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

        session =
                mock(HttpSession.class);
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

    @Test
    void sessionWithoutStaffUserShouldRedirectToLogin()
            throws Exception {

        // A session alone does not prove the user has logged in.
        when(request.getSession(false))
                .thenReturn(session);

        when(session.getAttribute("staffUser"))
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

    @Test
    void authenticatedRequestShouldContinueFilterChain()
            throws Exception {

        // A valid staff session should be allowed to continue.
        when(request.getSession(false))
                .thenReturn(session);

        when(session.getAttribute("staffUser"))
                .thenReturn("admin");

        filter.doFilter(
                request,
                response,
                chain
        );

        verify(chain)
                .doFilter(
                        request,
                        response
                );

        verify(response, never())
                .sendRedirect(
                "login.jsp"
        );
    }

    @Test
    void filterShouldProtectAppointmentApi() {

        WebFilter webFilter =
                AuthenticationFilter.class.getAnnotation(
                        WebFilter.class
                );

        assertTrue(
                java.util.Arrays.asList(
                        webFilter.urlPatterns()
                ).contains(
                        "/api/appointments"
                )
        );
    }
}
