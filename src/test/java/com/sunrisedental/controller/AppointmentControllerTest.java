package com.sunrisedental.controller;

import com.sunrisedental.service.AppointmentService;
import org.junit.jupiter.api.BeforeEach;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.mock;

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
}