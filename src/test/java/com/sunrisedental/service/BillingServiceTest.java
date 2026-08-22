package com.sunrisedental.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.sunrisedental.model.Appointment;
import com.sunrisedental.model.Bill;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class BillingServiceTest {

    private BillingService billingService;

    @BeforeEach
    void setUp() {
        billingService =
                new BillingService();
    }

    @Test
    void calculateTotalShouldAddTreatmentAndConsultationCharges() {

        double treatmentCharge = 5000.00;
        double consultationFee = 1500.00;

        double total =
                billingService.calculateTotal(
                        treatmentCharge,
                        consultationFee
                );

        assertEquals(
                6500.00,
                total
        );
    }

    @Test
    void calculateTotalShouldRejectNegativeCharges() {

        double treatmentCharge = -500.00;
        double consultationFee = 1500.00;

        assertThrows(
                IllegalArgumentException.class,
                () -> billingService.calculateTotal(
                        treatmentCharge,
                        consultationFee
                )
        );
    }

    @Test
    void createBillShouldUseAppointmentAndCharges() {

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

        double treatmentCharge = 5000.00;
        double consultationFee = 1500.00;

        Bill bill =
                billingService.createBill(
                        appointment,
                        treatmentCharge,
                        consultationFee
                );

        assertNotNull(bill);

        assertEquals(
                "APT001",
                bill.getAppointmentNumber()
        );

        assertEquals(
                "Nimal Perera",
                bill.getPatientName()
        );

        assertEquals(
                "Cleaning",
                bill.getTreatmentType()
        );

        assertEquals(
                5000.00,
                bill.getTreatmentCharge()
        );

        assertEquals(
                1500.00,
                bill.getConsultationFee()
        );

        assertEquals(
                6500.00,
                bill.getTotalAmount()
        );
    }
}

