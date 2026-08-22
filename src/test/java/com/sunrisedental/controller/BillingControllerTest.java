package com.sunrisedental.controller;

import com.sunrisedental.service.AppointmentService;
import com.sunrisedental.service.BillingService;
import org.junit.jupiter.api.BeforeEach;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.mock;

import com.sunrisedental.model.Appointment;
import com.sunrisedental.model.Bill;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

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

    @Test
    void generateBillShouldForwardReceiptForExistingAppointment()
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

        Bill bill =
                new Bill(
                        "APT001",
                        "Nimal Perera",
                        "Cleaning",
                        5000.00,
                        1500.00,
                        6500.00
                );

        when(request.getParameter("appointmentNumber"))
                .thenReturn(appointmentNumber);

        when(request.getParameter("treatmentCharge"))
                .thenReturn("5000.00");

        when(request.getParameter("consultationFee"))
                .thenReturn("1500.00");

        when(appointmentService.findByAppointmentNumber(
                appointmentNumber))
                .thenReturn(Optional.of(appointment));

        when(billingService.createBill(
                appointment,
                5000.00,
                1500.00))
                .thenReturn(bill);

        when(request.getRequestDispatcher(
                "receipt.jsp"))
                .thenReturn(dispatcher);

        controller.doPost(
                request,
                response
        );

        verify(appointmentService)
                .findByAppointmentNumber(
                        appointmentNumber
                );

        verify(billingService)
                .createBill(
                        appointment,
                        5000.00,
                        1500.00
                );

        verify(request)
                .setAttribute(
                        "bill",
                        bill
                );

        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }
}