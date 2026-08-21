package com.sunrisedental.controller;

import com.sunrisedental.model.Appointment;
import com.sunrisedental.service.AppointmentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class AppointmentController extends HttpServlet {

    private AppointmentService appointmentService;

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

            Appointment appointment =
                    new Appointment(
                            0,
                            appointmentNumber
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
                }

            } catch (SQLException e) {
                throw new ServletException(e);
            }
        }
    }
}
