package com.sunrisedental.controller;

import com.sunrisedental.model.Appointment;
import com.sunrisedental.service.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

class AppointmentControllerTest {

    private AppointmentService appointmentService;
    private AppointmentController controller;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;

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

        dispatcher =
                mock(RequestDispatcher.class);
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

    @Test
    void postRegisterShouldShowErrorWhenRegistrationFails()
            throws Exception {

        when(request.getParameter("action"))
                .thenReturn("register");

        when(request.getParameter("appointmentNumber"))
                .thenReturn("APT001");

        when(appointmentService.registerAppointment(
                any(Appointment.class)))
                .thenReturn(false);

        when(request.getRequestDispatcher(
                "WEB-INF/view/addAppointment.jsp"))
                .thenReturn(dispatcher);

        controller.doPost(
                request,
                response
        );

        verify(request)
                .setAttribute(
                        "errorMessage",
                        "Appointment could not be registered"
                );

        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }

    @Test
    void getSearchShouldLoadAppointmentAndForwardToDetailsPage()
            throws Exception {

        Appointment appointment =
                new Appointment(
                        1,
                        "APT001"
                );

        when(request.getParameter("action"))
                .thenReturn("search");

        when(request.getParameter("appointmentNumber"))
                .thenReturn("APT001");

        when(appointmentService.findByAppointmentNumber("APT001"))
                .thenReturn(Optional.of(appointment));

        when(request.getRequestDispatcher(
                "WEB-INF/view/viewAppointment.jsp"))
                .thenReturn(dispatcher);

        controller.doGet(
                request,
                response
        );

        verify(appointmentService)
                .findByAppointmentNumber("APT001");

        verify(request)
                .setAttribute(
                        "appointment",
                        appointment
                );

        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }
}
