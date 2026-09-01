package com.sunrisedental.controller;

import com.sunrisedental.service.AppointmentService;
import com.sunrisedental.service.BillingService;
import com.sunrisedental.service.TreatmentService;
import org.junit.jupiter.api.BeforeEach;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunrisedental.model.Appointment;
import com.sunrisedental.model.Treatment;
import com.sunrisedental.model.Bill;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;

class BillingControllerTest {

    private AppointmentService appointmentService;
    private BillingService billingService;
    private TreatmentService treatmentService;
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

        treatmentService =
                mock(TreatmentService.class);

        controller =
                new BillingController(
                        appointmentService,
                        billingService,
                        treatmentService
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

    @Test
    void generateBillShouldShowErrorWhenAppointmentNotFound()
            throws Exception {

        String appointmentNumber = "APT999";

        when(request.getParameter("appointmentNumber"))
                .thenReturn(appointmentNumber);

        when(request.getParameter("treatmentCharge"))
                .thenReturn("5000.00");

        when(request.getParameter("consultationFee"))
                .thenReturn("1500.00");

        when(appointmentService.findByAppointmentNumber(
                appointmentNumber))
                .thenReturn(Optional.empty());

        when(request.getRequestDispatcher(
                "bill.jsp"))
                .thenReturn(dispatcher);

        controller.doPost(
                request,
                response
        );

        verify(appointmentService)
                .findByAppointmentNumber(
                        appointmentNumber
                );

        verify(request)
                .setAttribute(
                        "errorMessage",
                        "Appointment not found"
                );

        verify(dispatcher)
                .forward(
                        request,
                        response
                );

        verify(billingService, never())
                .createBill(
                        any(Appointment.class),
                        anyDouble(),
                        anyDouble()
                );
    }

    @Test
    void receiptRequestShouldForwardToReceiptPage()
            throws Exception {

        when(request.getParameter("action"))
                .thenReturn("receipt");

        when(request.getRequestDispatcher(
                "receipt.jsp"))
                .thenReturn(dispatcher);

        controller.doGet(
                request,
                response
        );

        verify(request)
                .getRequestDispatcher(
                        "receipt.jsp"
                );

        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }

    @Test
    void generateBillShouldShowErrorForNonNumericCharge()
            throws Exception {

        when(request.getParameter("appointmentNumber"))
                .thenReturn("APT001");

        when(request.getParameter("treatmentCharge"))
                .thenReturn("abc");

        when(request.getParameter("consultationFee"))
                .thenReturn("1500.00");

        when(request.getRequestDispatcher(
                "bill.jsp"))
                .thenReturn(dispatcher);

        controller.doPost(
                request,
                response
        );

        verify(request)
                .setAttribute(
                        "errorMessage",
                        "Please enter valid billing charges"
                );

        verify(dispatcher)
                .forward(
                        request,
                        response
                );

        verifyNoInteractions(
                appointmentService,
                billingService
        );
    }

    @Test
    void generateBillShouldShowErrorForNegativeCharge()
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

        when(request.getParameter("appointmentNumber"))
                .thenReturn(appointmentNumber);

        when(request.getParameter("treatmentCharge"))
                .thenReturn("-500.00");

        when(request.getParameter("consultationFee"))
                .thenReturn("1500.00");

        when(appointmentService.findByAppointmentNumber(
                appointmentNumber))
                .thenReturn(Optional.of(appointment));

        when(billingService.createBill(
                appointment,
                -500.00,
                1500.00))
                .thenThrow(
                        new IllegalArgumentException(
                                "Charges cannot be negative"
                        )
                );

        when(request.getRequestDispatcher(
                "bill.jsp"))
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
                        -500.00,
                        1500.00
                );

        verify(request)
                .setAttribute(
                        "errorMessage",
                        "Charges cannot be negative"
                );

        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }

    @Test
    void shouldUseStoredTreatmentCharges()
            throws Exception {

        Appointment appointment =
                new Appointment(
                        1,
                        "APT001",
                        "Nimal Perera",
                        "Colombo",
                        "0771234567",
                        "Dr Silva",
                        "Cleaning",
                        "2026-09-01",
                        "10:30"
                );

        Treatment treatment =
                new Treatment(
                        1,
                        "Cleaning",
                        5000.00,
                        1500.00
                );

        Bill bill =
                mock(Bill.class);

        when(
                request.getParameter(
                        "appointmentNumber"
                )
        ).thenReturn(
                "APT001"
        );

        // Different values to check stored treatment prices are used
        when(
                request.getParameter(
                        "treatmentCharge"
                )
        ).thenReturn(
                "9000"
        );

        when(
                request.getParameter(
                        "consultationFee"
                )
        ).thenReturn(
                "3000"
        );

        when(
                appointmentService
                        .findByAppointmentNumber(
                                "APT001"
                        )
        ).thenReturn(
                Optional.of(appointment)
        );

        when(
                treatmentService
                        .findByTreatmentName(
                                "Cleaning"
                        )
        ).thenReturn(
                Optional.of(treatment)
        );

        when(
                billingService.createBill(
                        appointment,
                        5000.00,
                        1500.00
                )
        ).thenReturn(
                bill
        );

        when(
                request.getRequestDispatcher(
                        "receipt.jsp"
                )
        ).thenReturn(
                dispatcher
        );

        controller.doPost(
                request,
                response
        );

        verify(
                treatmentService
        ).findByTreatmentName(
                "Cleaning"
        );

        verify(
                billingService
        ).createBill(
                appointment,
                5000.00,
                1500.00
        );

        verify(
                request
        ).setAttribute(
                "bill",
                bill
        );

        verify(
                dispatcher
        ).forward(
                request,
                response
        );
    }

    @Test
    void shouldGenerateBillWithoutManualChargeInput()
            throws Exception {

        Appointment appointment =
                new Appointment(
                        1,
                        "APT001",
                        "Nimal Perera",
                        "Colombo",
                        "0771234567",
                        "Dr Silva",
                        "Cleaning",
                        "2026-09-01",
                        "10:30"
                );

        Treatment treatment =
                new Treatment(
                        1,
                        "Cleaning",
                        5000.00,
                        1500.00
                );

        Bill bill =
                mock(Bill.class);

        when(
                request.getParameter(
                        "appointmentNumber"
                )
        ).thenReturn(
                "APT001"
        );

        // No treatmentCharge or consultationFee request parameters are supplied

        when(
                appointmentService
                        .findByAppointmentNumber(
                                "APT001"
                        )
        ).thenReturn(
                Optional.of(appointment)
        );

        when(
                treatmentService
                        .findByTreatmentName(
                                "Cleaning"
                        )
        ).thenReturn(
                Optional.of(treatment)
        );

        when(
                billingService.createBill(
                        appointment,
                        5000.00,
                        1500.00
                )
        ).thenReturn(
                bill
        );

        when(
                request.getRequestDispatcher(
                        "receipt.jsp"
                )
        ).thenReturn(
                dispatcher
        );

        controller.doPost(
                request,
                response
        );

        verify(
                treatmentService
        ).findByTreatmentName(
                "Cleaning"
        );

        verify(
                billingService
        ).createBill(
                appointment,
                5000.00,
                1500.00
        );

        verify(
                request
        ).setAttribute(
                "bill",
                bill
        );

        verify(
                dispatcher
        ).forward(
                request,
                response
        );
    }

    @Test
    void generateBillShouldShowErrorWhenTreatmentNotFound()
            throws Exception {

        Appointment appointment =
                new Appointment(
                        1,
                        "APT001",
                        "Nimal Perera",
                        "Colombo",
                        "0771234567",
                        "Dr Silva",
                        "Unknown Treatment",
                        "2026-09-01",
                        "10:30"
                );

        when(
                request.getParameter(
                        "appointmentNumber"
                )
        ).thenReturn(
                "APT001"
        );

        // No prices are sent because they should come from the stored treatment.
        when(
                appointmentService
                        .findByAppointmentNumber(
                                "APT001"
                        )
        ).thenReturn(
                Optional.of(appointment)
        );

        when(
                treatmentService
                        .findByTreatmentName(
                                "Unknown Treatment"
                        )
        ).thenReturn(
                Optional.empty()
        );

        when(
                request.getRequestDispatcher(
                        "bill.jsp"
                )
        ).thenReturn(
                dispatcher
        );

        controller.doPost(
                request,
                response
        );

        verify(
                treatmentService
        ).findByTreatmentName(
                "Unknown Treatment"
        );

        verify(
                request
        ).setAttribute(
                "errorMessage",
                "Treatment not found"
        );

        verify(
                dispatcher
        ).forward(
                request,
                response
        );

        verify(
                billingService,
                never()
        ).createBill(
                any(Appointment.class),
                anyDouble(),
                anyDouble()
        );
    }
}

