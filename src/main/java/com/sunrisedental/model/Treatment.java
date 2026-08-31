package com.sunrisedental.model;

public class Treatment {

    private int treatmentId;
    private String treatmentName;
    private double treatmentCharge;
    private double consultationFee;

    public Treatment(
            int treatmentId,
            String treatmentName,
            double treatmentCharge,
            double consultationFee) {

        this.treatmentId =
                treatmentId;

        this.treatmentName =
                treatmentName;

        this.treatmentCharge =
                treatmentCharge;

        this.consultationFee =
                consultationFee;
    }

    public int getTreatmentId() {
        return treatmentId;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public double getTreatmentCharge() {
        return treatmentCharge;
    }

    public double getConsultationFee() {
        return consultationFee;
    }
}