package com.sunrisedental.controller;

import com.sunrisedental.dao.AppointmentDAO;
import com.sunrisedental.service.AppointmentService;
import com.sunrisedental.model.Appointment;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api/appointments")
public class AppointmentApiController extends HttpServlet {

    private AppointmentService appointmentService;

    public AppointmentApiController() {

        this(
                new AppointmentService(
                        new AppointmentDAO()
                )
        );
    }

    public AppointmentApiController(
            AppointmentService appointmentService) {

        this.appointmentService =
                appointmentService;
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String appointmentNumber =
                request.getParameter("appointmentNumber");

        try {

            Optional<Appointment> appointment =
                    appointmentService.findByAppointmentNumber(
                            appointmentNumber
                    );

            if (appointment.isPresent()) {

                Appointment foundAppointment =
                        appointment.get();

                response.setContentType(
                        "application/json"
                );

                PrintWriter writer =
                        response.getWriter();

                writer.print(
                        "{"
                                + "\"appointmentNumber\":\""
                                + foundAppointment.getAppointmentNumber()
                                + "\","
                                + "\"patientName\":\""
                                + foundAppointment.getPatientName()
                                + "\","
                                + "\"address\":\""
                                + foundAppointment.getAddress()
                                + "\","
                                + "\"contactNumber\":\""
                                + foundAppointment.getContactNumber()
                                + "\","
                                + "\"dentistName\":\""
                                + foundAppointment.getDentistName()
                                + "\","
                                + "\"treatmentType\":\""
                                + foundAppointment.getTreatmentType()
                                + "\","
                                + "\"appointmentDate\":\""
                                + foundAppointment.getAppointmentDate()
                                + "\","
                                + "\"appointmentTime\":\""
                                + foundAppointment.getAppointmentTime()
                                + "\""
                                + "}"
                );

            } else {

                response.setStatus(
                        HttpServletResponse.SC_NOT_FOUND
                );

                response.setContentType(
                        "application/json"
                );

                PrintWriter writer =
                        response.getWriter();

                writer.print(
                        "{\"error\":\"Appointment not found\"}"
                );
            }

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}