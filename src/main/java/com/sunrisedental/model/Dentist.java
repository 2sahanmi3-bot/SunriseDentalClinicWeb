package com.sunrisedental.model;

public class Dentist {

    private int dentistId;
    private String dentistName;
    private String specialization;
    private String contactNumber;
    private boolean active;

    public Dentist(
            int dentistId,
            String dentistName,
            String specialization,
            String contactNumber,
            boolean active) {

        this.dentistId = dentistId;
        this.dentistName = dentistName;
        this.specialization = specialization;
        this.contactNumber = contactNumber;
        this.active = active;
    }

    public int getDentistId() {
        return dentistId;
    }

    public String getDentistName() {
        return dentistName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public boolean isActive() {
        return active;
    }
}