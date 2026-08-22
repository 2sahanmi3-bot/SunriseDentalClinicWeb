package com.sunrisedental.controller;

import com.sunrisedental.service.AuthenticationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class AuthenticationController extends HttpServlet {

    private AuthenticationService authenticationService;

    public AuthenticationController(
            AuthenticationService authenticationService) {

        this.authenticationService =
                authenticationService;
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String username =
                request.getParameter("username");

        String password =
                request.getParameter("password");

        try {

            boolean authenticated =
                    authenticationService.authenticate(
                            username,
                            password
                    );

            if (authenticated) {

                HttpSession session =
                        request.getSession();

                session.setAttribute(
                        "staffUser",
                        username
                );

                response.sendRedirect(
                        "appointment"
                );
            }

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
