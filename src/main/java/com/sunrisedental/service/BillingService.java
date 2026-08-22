package com.sunrisedental.service;

import com.sunrisedental.model.Appointment;
import com.sunrisedental.model.Bill;

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

    public Bill createBill(
            Appointment appointment,
            double treatmentCharge,
            double consultationFee) {

        return null;
    }
}
