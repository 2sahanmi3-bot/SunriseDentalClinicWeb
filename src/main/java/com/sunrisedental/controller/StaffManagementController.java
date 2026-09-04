package com.sunrisedental.controller;

import com.sunrisedental.dao.UserDAO;
import com.sunrisedental.service.StaffManagementService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/staff")
public class StaffManagementController extends HttpServlet {

    private StaffManagementService staffManagementService;

    public StaffManagementController() {

        this(
                new StaffManagementService(
                        new UserDAO()
                )
        );
    }

    public StaffManagementController(
            StaffManagementService staffManagementService) {

        this.staffManagementService =
                staffManagementService;
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            request.setAttribute(
                    "users",
                    staffManagementService.getAllUsers()
            );

            request.getRequestDispatcher(
                    "/WEB-INF/view/manageStaff.jsp"
            ).forward(
                    request,
                    response
            );

        } catch (SQLException e) {

            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action =
                request.getParameter(
                        "action"
                );

        if ("updateRole".equals(action)) {

            try {

                int userId =
                        Integer.parseInt(
                                request.getParameter(
                                        "userId"
                                )
                        );

                String role =
                        request.getParameter(
                                "role"
                        );

                staffManagementService
                        .changeUserRole(
                                userId,
                                role
                        );

                request.setAttribute(
                        "successMessage",
                        "User role updated successfully"
                );

                request.setAttribute(
                        "users",
                        staffManagementService
                                .getAllUsers()
                );

                request.getRequestDispatcher(
                        "/WEB-INF/view/manageStaff.jsp"
                ).forward(
                        request,
                        response
                );

                return;

            } catch (IllegalArgumentException e) {

                request.setAttribute(
                        "errorMessage",
                        e.getMessage()
                );

                try {

                    request.setAttribute(
                            "users",
                            staffManagementService
                                    .getAllUsers()
                    );

                } catch (SQLException sqlException) {

                    throw new ServletException(
                            sqlException
                    );
                }

                request.getRequestDispatcher(
                        "/WEB-INF/view/manageStaff.jsp"
                ).forward(
                        request,
                        response
                );

                return;

            } catch (SQLException e) {

                throw new ServletException(e);
            }
        }

        String username =
                request.getParameter(
                        "username"
                );

        String password =
                request.getParameter(
                        "password"
                );

        String role =
                request.getParameter(
                        "role"
                );

        try {

            try {

                boolean created =
                        staffManagementService
                                .createStaffUser(
                                        username,
                                        password,
                                        role
                                );

                if (created) {

                    request.setAttribute(
                            "successMessage",
                            "Staff account created successfully"
                    );
                }

            } catch (IllegalArgumentException e) {

                request.setAttribute(
                        "errorMessage",
                        e.getMessage()
                );
            }

            request.setAttribute(
                    "users",
                    staffManagementService.getAllUsers()
            );

            RequestDispatcher dispatcher =
                    request.getRequestDispatcher(
                            "/WEB-INF/view/manageStaff.jsp"
                    );

            dispatcher.forward(
                    request,
                    response
            );

        } catch (SQLException e) {

            throw new ServletException(e);
        }
    }
}