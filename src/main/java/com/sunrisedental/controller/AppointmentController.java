package com.sunrisedental.controller;

import com.sunrisedental.model.Appointment;
import com.sunrisedental.service.AppointmentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

import java.io.IOException;
import java.sql.SQLException;

import com.sunrisedental.dao.AppointmentDAO;

import javax.servlet.annotation.WebServlet;

@WebServlet("/appointment")
public class AppointmentController extends HttpServlet {

    private AppointmentService appointmentService;

    public AppointmentController() {

        this(
                new AppointmentService(
                        new AppointmentDAO()
                )
        );
    }

    public AppointmentController(
            AppointmentService appointmentService) {

        this.appointmentService = appointmentService;
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action =
                request.getParameter("action");

        if ("register".equals(action)) {

            String appointmentNumber =
                    request.getParameter("appointmentNumber");

            String patientName =
                    request.getParameter("patientName");

            String address =
                    request.getParameter("address");

            String contactNumber =
                    request.getParameter("contactNumber");

            String dentistName =
                    request.getParameter("dentistName");

            String treatmentType =
                    request.getParameter("treatmentType");

            String appointmentDate =
                    request.getParameter("appointmentDate");

            String appointmentTime =
                    request.getParameter("appointmentTime");

            Appointment appointment =
                    new Appointment(
                            0,
                            appointmentNumber,
                            patientName,
                            address,
                            contactNumber,
                            dentistName,
                            treatmentType,
                            appointmentDate,
                            appointmentTime
                    );

            try {

                boolean registered =
                        appointmentService.registerAppointment(
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
                request.getParameter("action");

        // Show the appointment form after staff login.
        if (action == null) {

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
                    request.getParameter("appointmentNumber");

            try {

                Optional<Appointment> appointment =
                        appointmentService.findByAppointmentNumber(
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