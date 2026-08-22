package com.sunrisedental.controller;

import com.sunrisedental.service.AppointmentService;
import com.sunrisedental.service.BillingService;
import org.junit.jupiter.api.BeforeEach;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.mock;

class BillingControllerTest {

    private AppointmentService appointmentService;
    private BillingService billingService;
    private BillingController controller;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() {

        appointmentService =
                mock(AppointmentService.class);

        billingService =
                mock(BillingService.class);

        controller =
                new BillingController(
                        appointmentService,
                        billingService
                );

        request =
                mock(HttpServletRequest.class);

        response =
                mock(HttpServletResponse.class);

        dispatcher =
                mock(RequestDispatcher.class);
    }
}