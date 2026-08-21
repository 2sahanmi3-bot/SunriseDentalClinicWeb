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

    public Appointment(
            int appointmentId,
            String appointmentNumber,
            String patientName,
            String address,
            String contactNumber,
            String dentistName,
            String treatmentType,
            String appointmentDate,
            String appointmentTime) {

        this.appointmentId = appointmentId;
        this.appointmentNumber = appointmentNumber;

        // The remaining appointment details are not stored yet.
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public String getAppointmentNumber() {
        return appointmentNumber;
    }

    public String getPatientName() {
        return null;
    }

    public String getAddress() {
        return null;
    }

    public String getContactNumber() {
        return null;
    }

    public String getDentistName() {
        return null;
    }

    public String getTreatmentType() {
        return null;
    }

    public String getAppointmentDate() {
        return null;
    }

    public String getAppointmentTime() {
        return null;
    }
}
