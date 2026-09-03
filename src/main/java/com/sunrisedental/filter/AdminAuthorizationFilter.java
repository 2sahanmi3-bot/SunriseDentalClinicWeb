package com.sunrisedental.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/admin/*")
public class AdminAuthorizationFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest =
                (HttpServletRequest) request;

        HttpServletResponse httpResponse =
                (HttpServletResponse) response;

        HttpSession session =
                httpRequest.getSession(false);

        // Send the user back to login if there is no active staff session.
        if (session == null
                || session.getAttribute("staffUser") == null) {

            httpResponse.sendRedirect(
                    httpRequest.getContextPath()
                            + "/login.jsp"
            );

            return;
        }

        String role =
                (String) session.getAttribute(
                        "staffRole"
                );

        // Staff users should not be able to open admin pages.
        if (!"ADMIN".equals(role)) {

            httpResponse.sendError(
                    HttpServletResponse.SC_FORBIDDEN
            );

            return;
        }

        chain.doFilter(
                request,
                response
        );
    }
}

