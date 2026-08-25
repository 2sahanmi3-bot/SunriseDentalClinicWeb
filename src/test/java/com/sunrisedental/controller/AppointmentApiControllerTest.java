package com.sunrisedental.controller;

import com.sunrisedental.service.AppointmentService;
import com.sunrisedental.model.Appointment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.io.StringWriter;
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
                        "Nimal Perera",
                        "Colombo",
                        "0771234567",
                        "Dr Silva",
                        "Cleaning",
                        "2026-08-25",
                        "10:30"
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
                        "application/json"
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
    }
}