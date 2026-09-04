package com.sunrisedental.controller;

import com.sunrisedental.dao.TreatmentDAO;
import com.sunrisedental.model.Treatment;
import com.sunrisedental.service.TreatmentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet("/admin/treatments")
public class TreatmentManagementController extends HttpServlet {

    private TreatmentService treatmentService;

    public TreatmentManagementController() {

        this(
                new TreatmentService(
                        new TreatmentDAO()
                )
        );
    }

    public TreatmentManagementController(
            TreatmentService treatmentService) {

        this.treatmentService =
                treatmentService;
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

                int treatmentId =
                        Integer.parseInt(
                                request.getParameter(
                                        "treatmentId"
                                )
                        );

                Optional<Treatment> treatment =
                        treatmentService.getTreatment(
                                treatmentId
                        );

                if (treatment.isPresent()) {

                    request.setAttribute(
                            "selectedTreatment",
                            treatment.get()
                    );

                } else {

                    request.setAttribute(
                            "errorMessage",
                            "Treatment not found"
                    );
                }
            }

            loadTreatments(
                    request,
                    response
            );

        } catch (NumberFormatException e) {

            request.setAttribute(
                    "errorMessage",
                    "Treatment not found"
            );

            try {

                loadTreatments(
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

                int treatmentId =
                        parseTreatmentId(
                                request
                        );

                treatmentService.updateTreatment(
                        treatmentId,
                        request.getParameter(
                                "treatmentName"
                        ),
                        request.getParameter(
                                "treatmentCharge"
                        ),
                        request.getParameter(
                                "consultationFee"
                        )
                );

                request.setAttribute(
                        "successMessage",
                        "Treatment details updated successfully"
                );

            } else if ("updateStatus".equals(action)) {

                int treatmentId =
                        parseTreatmentId(
                                request
                        );

                boolean active =
                        Boolean.parseBoolean(
                                request.getParameter(
                                        "active"
                                )
                        );

                treatmentService.changeTreatmentStatus(
                        treatmentId,
                        active
                );

                request.setAttribute(
                        "successMessage",
                        active
                                ? "Treatment activated successfully"
                                : "Treatment deactivated successfully"
                );

            } else {

                treatmentService.addTreatment(
                        request.getParameter(
                                "treatmentName"
                        ),
                        request.getParameter(
                                "treatmentCharge"
                        ),
                        request.getParameter(
                                "consultationFee"
                        )
                );

                request.setAttribute(
                        "successMessage",
                        "Treatment added successfully"
                );
            }

            loadTreatments(
                    request,
                    response
            );

        } catch (IllegalArgumentException e) {

            request.setAttribute(
                    "errorMessage",
                    e.getMessage()
            );

            try {

                loadTreatments(
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

    private int parseTreatmentId(
            HttpServletRequest request) {

        String treatmentIdParameter =
                request.getParameter(
                        "treatmentId"
                );

        if (treatmentIdParameter == null
                || treatmentIdParameter.isBlank()) {

            throw new IllegalArgumentException(
                    "Treatment not found"
            );
        }

        try {

            return Integer.parseInt(
                    treatmentIdParameter
            );

        } catch (NumberFormatException e) {

            throw new IllegalArgumentException(
                    "Treatment not found"
            );
        }
    }

    private void loadTreatments(
            HttpServletRequest request,
            HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        request.setAttribute(
                "treatments",
                treatmentService.getAllTreatments()
        );

        request.getRequestDispatcher(
                "/WEB-INF/view/manageTreatments.jsp"
        ).forward(
                request,
                response
        );
    }
}
