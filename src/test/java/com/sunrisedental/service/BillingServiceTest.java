package com.sunrisedental.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
}

