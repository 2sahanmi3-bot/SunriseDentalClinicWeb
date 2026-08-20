package com.sunrisedental.model;

public class Appointment {

    private int appointmentId;
    private String appointmentNumber;

    public Appointment(
            int appointmentId,
            String appointmentNumber) {

        this.appointmentId = appointmentId;
        this.appointmentNumber = appointmentNumber;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public String getAppointmentNumber() {
        return appointmentNumber;
    }
}