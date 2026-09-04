package com.sunrisedental.controller;

import com.sunrisedental.dao.AppointmentDAO;
import com.sunrisedental.dao.PatientDAO;

import com.sunrisedental.model.Appointment;
import com.sunrisedental.model.Patient;

import com.sunrisedental.service.AppointmentService;
import com.sunrisedental.service.PatientService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

import java.util.Optional;

@WebServlet("/appointment")
public class AppointmentController extends HttpServlet {

    private AppointmentService appointmentService;
    private PatientService patientService;

    public AppointmentController() {

        this(
                new AppointmentService(
                        new AppointmentDAO()
                ),
                new PatientService(
                        new PatientDAO()
                )
        );
    }

    public AppointmentController(
            AppointmentService appointmentService) {

        this(
                appointmentService,
                new PatientService(
                        new PatientDAO()
                )
        );
    }

    public AppointmentController(
            AppointmentService appointmentService,
            PatientService patientService) {

        this.appointmentService =
                appointmentService;

        this.patientService =
                patientService;
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

        if ("register".equals(action)) {

            String appointmentNumber =
                    request.getParameter(
                            "appointmentNumber"
                    );

            // Remove accidental spaces around the appointment number.
            if (appointmentNumber != null) {

                appointmentNumber =
                        appointmentNumber.trim();
            }

            String patientName =
                    request.getParameter(
                            "patientName"
                    );

            String address =
                    request.getParameter(
                            "address"
                    );

            String contactNumber =
                    request.getParameter(
                            "contactNumber"
                    );

            String dentistName =
                    request.getParameter(
                            "dentistName"
                    );

            String treatmentType =
                    request.getParameter(
                            "treatmentType"
                    );

            String appointmentDate =
                    request.getParameter(
                            "appointmentDate"
                    );

            String appointmentTime =
                    request.getParameter(
                            "appointmentTime"
                    );

            Integer patientId =
                    null;

            String patientIdParameter =
                    request.getParameter(
                            "patientId"
                    );

            if (patientIdParameter != null
                    && !patientIdParameter.isBlank()) {

                patientId =
                        Integer.valueOf(
                                patientIdParameter
                        );
            }

            Appointment appointment =
                    new Appointment(
                            0,
                            appointmentNumber,
                            patientId,
                            patientName,
                            address,
                            contactNumber,
                            dentistName,
                            treatmentType,
                            appointmentDate,
                            appointmentTime,
                            "SCHEDULED"
                    );

            try {

                boolean registered =
                        appointmentService
                                .registerAppointment(
                                        appointment
                                );

                if (registered) {

                    response.sendRedirect(
                            "appointment?action=search&appointmentNumber="
                                    + appointmentNumber
                    );

                } else {

                    // Show a clear message when the appointment cannot be registered.
                    request.setAttribute(
                            "errorMessage",
                            "Appointment could not be registered"
                    );

                    request.getRequestDispatcher(
                            "WEB-INF/view/addAppointment.jsp"
                    ).forward(
                            request,
                            response
                    );
                }

            } catch (SQLException e) {

                throw new ServletException(e);
            }
        }
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

        // Show the appointment form after staff login.
        if (action == null) {

            String patientIdParameter =
                    request.getParameter(
                            "patientId"
                    );

            if (patientIdParameter != null
                    && !patientIdParameter.isBlank()) {

                try {

                    int patientId =
                            Integer.parseInt(
                                    patientIdParameter
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
                    }

                } catch (SQLException e) {

                    throw new ServletException(e);

                } catch (NumberFormatException e) {

                    request.setAttribute(
                            "errorMessage",
                            "Invalid patient"
                    );
                }
            }

            request.getRequestDispatcher(
                    "WEB-INF/view/addAppointment.jsp"
            ).forward(
                    request,
                    response
            );

            return;
        }

        if ("search".equals(action)) {

            String appointmentNumber =
                    request.getParameter(
                            "appointmentNumber"
                    );

            // Remove accidental spaces around the appointment number.
            if (appointmentNumber != null) {

                appointmentNumber =
                        appointmentNumber.trim();
            }

            try {

                Optional<Appointment> appointment =
                        appointmentService
                                .findByAppointmentNumber(
                                        appointmentNumber
                                );

                if (appointment.isPresent()) {

                    request.setAttribute(
                            "appointment",
                            appointment.get()
                    );

                    request.getRequestDispatcher(
                            "WEB-INF/view/viewAppointment.jsp"
                    ).forward(
                            request,
                            response
                    );

                } else {

                    // Show a clear message when no appointment is found.
                    request.setAttribute(
                            "errorMessage",
                            "Appointment not found"
                    );

                    request.getRequestDispatcher(
                            "WEB-INF/view/viewAppointment.jsp"
                    ).forward(
                            request,
                            response
                    );
                }

            } catch (SQLException e) {

                throw new ServletException(e);
            }
        }
    }
}