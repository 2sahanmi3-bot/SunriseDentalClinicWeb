package com.sunrisedental.controller;

import com.sunrisedental.service.AppointmentService;
import com.sunrisedental.model.Appointment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

class AppointmentApiControllerTest {

    private AppointmentService appointmentService;
    private AppointmentApiController controller;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {

        appointmentService =
                mock(AppointmentService.class);

        controller =
                new AppointmentApiController(
                        appointmentService
                );

        request =
                mock(HttpServletRequest.class);

        response =
                mock(HttpServletResponse.class);
    }

    @Test
    void appointmentApiShouldReturnJsonForExistingAppointment()
            throws Exception {

        String appointmentNumber = "APT001";

        Appointment appointment =
                new Appointment(
                        1,
                        "APT001",
                        null,
                        "Nimal Perera",
                        "Colombo",
                        "0771234567",
                "Dr Silva",
                "Cleaning",
                "2026-08-25",
                "10:30",
                "SCHEDULED"
        );

        StringWriter responseBody =
                new StringWriter();

        PrintWriter writer =
                new PrintWriter(responseBody);

        when(request.getParameter("appointmentNumber"))
                .thenReturn(appointmentNumber);

        when(appointmentService.findByAppointmentNumber(
                appointmentNumber))
                .thenReturn(Optional.of(appointment));

        when(response.getWriter())
                .thenReturn(writer);

        controller.doGet(
                request,
                response
        );

        writer.flush();

        verify(appointmentService)
                .findByAppointmentNumber(
                        appointmentNumber
                );

        verify(response)
                .setContentType(
                        "application/json;charset=UTF-8"
                );

        assertTrue(
                responseBody.toString()
                        .contains("\"appointmentNumber\":\"APT001\"")
        );

        assertTrue(
                responseBody.toString()
                        .contains("\"patientName\":\"Nimal Perera\"")
        );

        assertTrue(
                responseBody.toString()
                        .contains("\"treatmentType\":\"Cleaning\"")
        );

        assertTrue(
                responseBody.toString()
                        .contains("\"status\":\"SCHEDULED\"")
        );
    }

    @Test
    void appointmentApiShouldReturnNotFoundForMissingAppointment()
            throws Exception {

        String appointmentNumber = "APT999";

        StringWriter responseBody =
                new StringWriter();

        PrintWriter writer =
                new PrintWriter(responseBody);

        when(request.getParameter("appointmentNumber"))
                .thenReturn(appointmentNumber);

        when(appointmentService.findByAppointmentNumber(
                appointmentNumber))
                .thenReturn(Optional.empty());

        when(response.getWriter())
                .thenReturn(writer);

        controller.doGet(
                request,
                response
        );

        writer.flush();

        verify(appointmentService)
                .findByAppointmentNumber(
                        appointmentNumber
                );

        verify(response)
                .setStatus(
                        HttpServletResponse.SC_NOT_FOUND
                );

        verify(response)
                .setContentType(
                        "application/json;charset=UTF-8"
                );

        assertTrue(
                responseBody.toString()
                        .contains("\"error\":\"Appointment not found\"")
        );
    }

    @Test
    void appointmentApiShouldReturnBadRequestForBlankAppointmentNumber()
            throws Exception {

        StringWriter responseBody =
                new StringWriter();

        PrintWriter writer =
                new PrintWriter(responseBody);

        when(request.getParameter("appointmentNumber"))
                .thenReturn(" ");

        when(response.getWriter())
                .thenReturn(writer);

        controller.doGet(
                request,
                response
        );

        writer.flush();

        verify(appointmentService, never())
                .findByAppointmentNumber(
                        anyString()
                );

        verify(response)
                .setStatus(
                        HttpServletResponse.SC_BAD_REQUEST
                );

        verify(response)
                .setContentType(
                        "application/json;charset=UTF-8"
                );

        assertTrue(
                responseBody.toString()
                        .contains("\"error\":\"Appointment number is required\"")
        );
    }

    @Test
    void appointmentApiShouldReturnGenericServerError()
            throws Exception {

        String appointmentNumber = "APT001";

        StringWriter responseBody =
                new StringWriter();

        PrintWriter writer =
                new PrintWriter(responseBody);

        when(request.getParameter("appointmentNumber"))
                .thenReturn(appointmentNumber);

        when(appointmentService.findByAppointmentNumber(
                appointmentNumber))
                .thenThrow(
                        new SQLException(
                                "database unavailable"
                        )
                );

        when(response.getWriter())
                .thenReturn(writer);

        controller.doGet(
                request,
                response
        );

        writer.flush();

        verify(response)
                .setStatus(
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR
                );

        assertTrue(
                responseBody.toString()
                        .contains("\"error\":\"Unable to retrieve appointment\"")
        );

        assertTrue(
                !responseBody.toString()
                        .contains("database unavailable")
        );
    }

    @Test
    void appointmentApiShouldEscapeJsonValues()
            throws Exception {

        String appointmentNumber = "APT001";

        Appointment appointment =
                new Appointment(
                        1,
                        "APT001",
                        null,
                        "Nimal \"Test\"",
                        "Line 1\nLine 2",
                        "0771234567",
                        "Dr \\ Silva",
                        "Cleaning",
                        "2026-08-25",
                        "10:30",
                        "SCHEDULED"
                );

        StringWriter responseBody =
                new StringWriter();

        PrintWriter writer =
                new PrintWriter(responseBody);

        when(request.getParameter("appointmentNumber"))
                .thenReturn(appointmentNumber);

        when(appointmentService.findByAppointmentNumber(
                appointmentNumber))
                .thenReturn(Optional.of(appointment));

        when(response.getWriter())
                .thenReturn(writer);

        controller.doGet(
                request,
                response
        );

        writer.flush();

        assertTrue(
                responseBody.toString()
                        .contains("Nimal \\\"Test\\\"")
        );

        assertTrue(
                responseBody.toString()
                        .contains("Line 1\\nLine 2")
        );

        assertTrue(
                responseBody.toString()
                        .contains("Dr \\\\ Silva")
        );
    }
}
