package com.sunrisedental.controller;

import com.sunrisedental.dao.PatientDAO;
import com.sunrisedental.model.Patient;
import com.sunrisedental.service.PatientService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@WebServlet("/patient")
public class PatientController extends HttpServlet {

    private PatientService patientService;

    public PatientController() {

        this(
                new PatientService(
                        new PatientDAO()
                )
        );
    }

    public PatientController(
            PatientService patientService) {

        this.patientService =
                patientService;
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

            if ("search".equals(action)) {

                String contactNumber =
                        request.getParameter(
                                "contactNumber"
                        );

                List<Patient> patients =
                        patientService
                                .searchByContactNumber(
                                        contactNumber
                                );

                if (patients.isEmpty()) {

                    request.setAttribute(
                            "errorMessage",
                            "No patients found"
                    );

                } else {

                    request.setAttribute(
                            "patients",
                            patients
                    );
                }

            } else if ("view".equals(action)
                    || "edit".equals(action)) {

                int patientId =
                        Integer.parseInt(
                                request.getParameter(
                                        "patientId"
                                )
                        );

                Optional<Patient> patient =
                        patientService.getPatient(
                                patientId
                        );

                if (patient.isPresent()) {

                    request.setAttribute(
                            "selectedPatient",
                            patient.get()
                    );

                    if ("edit".equals(action)) {

                        request.setAttribute(
                                "editMode",
                                true
                        );
                    }

                } else {

                    request.setAttribute(
                            "errorMessage",
                            "Patient not found"
                    );
                }
            }

        } catch (IllegalArgumentException e) {

            request.setAttribute(
                    "errorMessage",
                    e.getMessage()
            );

        } catch (SQLException e) {

            throw new ServletException(e);
        }

        request.getRequestDispatcher(
                "/WEB-INF/view/managePatients.jsp"
        ).forward(
                request,
                response
        );
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

                int patientId =
                        Integer.parseInt(
                                request.getParameter(
                                        "patientId"
                                )
                        );

                boolean updated =
                        patientService.updatePatient(
                                patientId,
                                request.getParameter(
                                        "patientName"
                                ),
                                request.getParameter(
                                        "address"
                                ),
                                request.getParameter(
                                        "contactNumber"
                                ),
                                request.getParameter(
                                        "email"
                                )
                        );

                if (updated) {

                    request.setAttribute(
                            "successMessage",
                            "Patient details updated successfully"
                    );

                    Optional<Patient> patient =
                            patientService.getPatient(
                                    patientId
                            );

                    patient.ifPresent(value ->
                            request.setAttribute(
                                    "selectedPatient",
                                    value
                            )
                    );
                }

            } else {

                boolean registered =
                        patientService.registerPatient(
                                request.getParameter(
                                        "patientName"
                                ),
                                request.getParameter(
                                        "address"
                                ),
                                request.getParameter(
                                        "contactNumber"
                                ),
                                request.getParameter(
                                        "email"
                                )
                        );

                if (registered) {

                    request.setAttribute(
                            "successMessage",
                            "Patient registered successfully"
                    );
                }
            }

        } catch (IllegalArgumentException e) {

            request.setAttribute(
                    "errorMessage",
                    e.getMessage()
            );

        } catch (SQLException e) {

            throw new ServletException(e);
        }

        request.getRequestDispatcher(
                "/WEB-INF/view/managePatients.jsp"
        ).forward(
                request,
                response
        );
    }
}