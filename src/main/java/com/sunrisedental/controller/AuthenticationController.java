package com.sunrisedental.controller;

import com.sunrisedental.dao.UserDAO;
import com.sunrisedental.model.User;
import com.sunrisedental.service.AuthenticationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet("/auth")
public class AuthenticationController extends HttpServlet {

    private AuthenticationService authenticationService;

    public AuthenticationController() {

        this(
                new AuthenticationService(
                        new UserDAO()
                )
        );
    }

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

            Optional<User> authenticatedUser =
                    authenticationService.authenticateUser(
                            username,
                            password
                    );

            if (authenticatedUser.isPresent()) {

                HttpSession session =
                        request.getSession();

                User user =
                        authenticatedUser.get();

                session.setAttribute(
                        "staffUser",
                        user.getUsername()
                );

                session.setAttribute(
                        "staffRole",
                        user.getRole()
                );

                session.setAttribute(
                        "loginMessage",
                        "Login successful. Welcome, "
                                + user.getUsername()
                                + "."
                );

                response.sendRedirect(
                        "dashboard"
                );

            } else {

                request.setAttribute(
                        "errorMessage",
                        "Invalid username or password"
                );

                RequestDispatcher dispatcher =
                        request.getRequestDispatcher(
                                "login.jsp"
                        );

                dispatcher.forward(
                        request,
                        response
                );
            }

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action =
                request.getParameter("action");

        if ("logout".equals(action)) {

            HttpSession session =
                    request.getSession(false);

            if (session != null) {
                session.invalidate();
            }

            response.sendRedirect(
                    "login.jsp"
            );
        }
    }
}