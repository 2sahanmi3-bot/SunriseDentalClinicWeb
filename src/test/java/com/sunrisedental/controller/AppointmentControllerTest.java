package com.sunrisedental.controller;

import com.sunrisedental.model.Appointment;
import com.sunrisedental.service.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

class AppointmentControllerTest {

    private AppointmentService appointmentService;
    private AppointmentController controller;

    private HttpServletRequest request;
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {

        appointmentService =
                mock(AppointmentService.class);

        controller =
                new AppointmentController(
                        appointmentService
                );

        request =
                mock(HttpServletRequest.class);

        response =
                mock(HttpServletResponse.class);
    }

    @Test
    void postRegisterShouldRegisterAppointmentAndRedirect()
            throws Exception {

        when(request.getParameter("action"))
                .thenReturn("register");

        when(request.getParameter("appointmentNumber"))
                .thenReturn("APT001");

        when(appointmentService.registerAppointment(
                any(Appointment.class)))
                .thenReturn(true);

        controller.doPost(
                request,
                response
        );

        verify(appointmentService)
                .registerAppointment(
                        argThat(appointment ->
                                "APT001".equals(
                                        appointment.getAppointmentNumber()
                                )
                        )
                );

        verify(response)
                .sendRedirect(
                        "appointment?action=search&appointmentNumber=APT001"
                );
    }
}