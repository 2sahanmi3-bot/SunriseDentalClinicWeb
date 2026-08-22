package com.sunrisedental.service;

public class BillingService {

    public double calculateTotal(
            double treatmentCharge,
            double consultationFee) {

        return treatmentCharge
                + consultationFee;
    }
}
