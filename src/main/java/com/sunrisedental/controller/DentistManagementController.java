package com.sunrisedental.controller;

import com.sunrisedental.dao.DentistDAO;
import com.sunrisedental.model.Dentist;
import com.sunrisedental.service.DentistService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet("/admin/dentists")
public class DentistManagementController extends HttpServlet {

    private DentistService dentistService;

    public DentistManagementController() {

        this(
                new DentistService(
                        new DentistDAO()
                )
        );
    }

    public DentistManagementController(
            DentistService dentistService) {

        this.dentistService =
                dentistService;
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action =
                request.getParameter(
                        "action"
                );

        try {

            if ("edit".equals(action)) {

                int dentistId =
                        Integer.parseInt(
                                request.getParameter(
                                        "dentistId"
                                )
                        );

                Optional<Dentist> dentist =
                        dentistService.getDentist(
                                dentistId
                        );

                if (dentist.isPresent()) {

                    request.setAttribute(
                            "selectedDentist",
                            dentist.get()
                    );

                } else {

                    request.setAttribute(
                            "errorMessage",
                            "Dentist not found"
                    );
                }
            }

            loadDentists(
                    request,
                    response
            );

        } catch (NumberFormatException e) {

            request.setAttribute(
                    "errorMessage",
                    "Invalid dentist"
            );

            try {

                loadDentists(
                        request,
                        response
                );

            } catch (SQLException sqlException) {

                throw new ServletException(
                        sqlException
                );
            }

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

        try {

            if ("update".equals(action)) {

                int dentistId =
                        Integer.parseInt(
                                request.getParameter(
                                        "dentistId"
                                )
                        );

                dentistService.updateDentist(
                        dentistId,
                        request.getParameter(
                                "dentistName"
                        ),
                        request.getParameter(
                                "specialization"
                        ),
                        request.getParameter(
                                "contactNumber"
                        )
                );

                request.setAttribute(
                        "successMessage",
                        "Dentist details updated successfully"
                );

            } else if ("updateStatus".equals(action)) {

                int dentistId =
                        Integer.parseInt(
                                request.getParameter(
                                        "dentistId"
                                )
                        );

                boolean active =
                        Boolean.parseBoolean(
                                request.getParameter(
                                        "active"
                                )
                        );

                dentistService.changeDentistStatus(
                        dentistId,
                        active
                );

                request.setAttribute(
                        "successMessage",
                        active
                                ? "Dentist activated successfully"
                                : "Dentist deactivated successfully"
                );

            } else {

                dentistService.addDentist(
                        request.getParameter(
                                "dentistName"
                        ),
                        request.getParameter(
                                "specialization"
                        ),
                        request.getParameter(
                                "contactNumber"
                        )
                );

                request.setAttribute(
                        "successMessage",
                        "Dentist added successfully"
                );
            }

            loadDentists(
                    request,
                    response
            );

        } catch (IllegalArgumentException e) {

            request.setAttribute(
                    "errorMessage",
                    e.getMessage()
            );

            try {

                loadDentists(
                        request,
                        response
                );

            } catch (SQLException sqlException) {

                throw new ServletException(
                        sqlException
                );
            }

        } catch (SQLException e) {

            throw new ServletException(e);
        }
    }

    private void loadDentists(
            HttpServletRequest request,
            HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        request.setAttribute(
                "dentists",
                dentistService.getAllDentists()
        );

        request.getRequestDispatcher(
                "/WEB-INF/view/manageDentists.jsp"
        ).forward(
                request,
                response
        );
    }
}