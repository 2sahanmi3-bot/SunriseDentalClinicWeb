package com.sunrisedental.model;

public class Patient {

    private int patientId;
    private String patientName;
    private String address;
    private String contactNumber;
    private String email;

    public Patient(
            int patientId,
            String patientName,
            String address,
            String contactNumber,
            String email) {

        this.patientId =
                patientId;

        this.patientName =
                patientName;

        this.address =
                address;

        this.contactNumber =
                contactNumber;

        this.email =
                email;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getAddress() {
        return address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmail() {
        return email;
    }
}