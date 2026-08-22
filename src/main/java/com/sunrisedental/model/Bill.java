package com.sunrisedental.model;

public class Bill {

    private String appointmentNumber;
    private String patientName;
    private String treatmentType;
    private double treatmentCharge;
    private double consultationFee;
    private double totalAmount;

    public Bill(
            String appointmentNumber,
            String patientName,
            String treatmentType,
            double treatmentCharge,
            double consultationFee,
            double totalAmount) {

        this.appointmentNumber = appointmentNumber;
        this.patientName = patientName;
        this.treatmentType = treatmentType;
        this.treatmentCharge = treatmentCharge;
        this.consultationFee = consultationFee;
        this.totalAmount = totalAmount;
    }

    public String getAppointmentNumber() {
        return appointmentNumber;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getTreatmentType() {
        return treatmentType;
    }

    public double getTreatmentCharge() {
        return treatmentCharge;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}