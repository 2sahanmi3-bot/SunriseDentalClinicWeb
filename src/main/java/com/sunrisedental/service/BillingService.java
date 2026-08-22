package com.sunrisedental.service;

public class BillingService {

    public double calculateTotal(
            double treatmentCharge,
            double consultationFee) {

        // Charges must not be negative.
        if (treatmentCharge < 0
                || consultationFee < 0) {
            throw new IllegalArgumentException(
                    "Charges cannot be negative"
            );
        }

        return treatmentCharge
                + consultationFee;
    }
}
