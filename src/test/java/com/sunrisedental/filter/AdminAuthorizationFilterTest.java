package com.sunrisedental.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

class AdminAuthorizationFilterTest {

    private AdminAuthorizationFilter filter;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private FilterChain chain;

    @BeforeEach
    void setUp() {

        filter =
                new AdminAuthorizationFilter();

        request =
                mock(HttpServletRequest.class);

        response =
                mock(HttpServletResponse.class);

        session =
                mock(HttpSession.class);

        chain =
                mock(FilterChain.class);
    }

    @Test
    void staffUserShouldBeBlockedFromAdminArea()
            throws Exception {

        when(request.getSession(false))
                .thenReturn(session);

        when(session.getAttribute("staffUser"))
                .thenReturn("reception1");

        when(session.getAttribute("staffRole"))
                .thenReturn("STAFF");

        filter.doFilter(
                request,
                response,
                chain
        );

        verify(response)
                .sendError(
                        HttpServletResponse.SC_FORBIDDEN
                );

        verify(chain, never())
                .doFilter(
                        request,
                        response
                );
    }

    @Test
    void adminUserShouldAccessAdminArea()
            throws Exception {

        when(request.getSession(false))
                .thenReturn(session);

        when(session.getAttribute("staffUser"))
                .thenReturn("admin");

        when(session.getAttribute("staffRole"))
                .thenReturn("ADMIN");

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
                .sendError(
                        HttpServletResponse.SC_FORBIDDEN
                );
    }

    @Test
    void userWithoutSessionShouldReturnToLogin()
            throws Exception {

        when(request.getSession(false))
                .thenReturn(null);

        when(request.getContextPath())
                .thenReturn(
                        "/sunrise-dental-clinic"
                );

        filter.doFilter(
                request,
                response,
                chain
        );

        verify(response)
                .sendRedirect(
                        "/sunrise-dental-clinic/login.jsp"
                );

        verify(chain, never())
                .doFilter(
                        request,
                        response
                );
    }
}