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

        response.setContentType(
                "application/json;charset=UTF-8"
        );

        if (appointmentNumber == null
                || appointmentNumber.isBlank()) {

            response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST
            );

            writeError(
                    response,
                    "Appointment number is required"
            );

            return;
        }

        try {

            Optional<Appointment> appointment =
                    appointmentService.findByAppointmentNumber(
                            appointmentNumber.trim()
                    );

            if (appointment.isPresent()) {

                Appointment foundAppointment =
                        appointment.get();

                PrintWriter writer =
                        response.getWriter();

                writer.print(
                        "{"
                                + "\"appointmentNumber\":\""
                                + escapeJson(
                                        foundAppointment.getAppointmentNumber()
                                )
                                + "\","
                                + "\"patientName\":\""
                                + escapeJson(
                                        foundAppointment.getPatientName()
                                )
                                + "\","
                                + "\"address\":\""
                                + escapeJson(
                                        foundAppointment.getAddress()
                                )
                                + "\","
                                + "\"contactNumber\":\""
                                + escapeJson(
                                        foundAppointment.getContactNumber()
                                )
                                + "\","
                                + "\"dentistName\":\""
                                + escapeJson(
                                        foundAppointment.getDentistName()
                                )
                                + "\","
                                + "\"treatmentType\":\""
                                + escapeJson(
                                        foundAppointment.getTreatmentType()
                                )
                                + "\","
                                + "\"appointmentDate\":\""
                                + escapeJson(
                                        foundAppointment.getAppointmentDate()
                                )
                                + "\","
                                + "\"appointmentTime\":\""
                                + escapeJson(
                                        foundAppointment.getAppointmentTime()
                                )
                                + "\","
                                + "\"status\":\""
                                + escapeJson(
                                        foundAppointment.getStatus()
                                )
                                + "\""
                                + "}"
                );

            } else {

                response.setStatus(
                        HttpServletResponse.SC_NOT_FOUND
                );

                writeError(
                        response,
                        "Appointment not found"
                );
            }

        } catch (SQLException e) {

            response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );

            writeError(
                    response,
                    "Unable to retrieve appointment"
            );
        }
    }

    private void writeError(
            HttpServletResponse response,
            String message)
            throws IOException {

        PrintWriter writer =
                response.getWriter();

        writer.print(
                "{\"error\":\""
                        + escapeJson(
                                message
                        )
                        + "\"}"
        );
    }

    private String escapeJson(
            String value) {

        if (value == null) {
            return "";
        }

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
