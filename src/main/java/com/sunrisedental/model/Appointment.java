package com.sunrisedental.model;

public class Appointment {

    private int appointmentId;
    private String appointmentNumber;
    private Integer patientId;
    private String patientName;
    private String address;
    private String contactNumber;
    private String dentistName;
    private String treatmentType;
    private String appointmentDate;
    private String appointmentTime;
    private String status;

    public Appointment(
            int appointmentId,
            String appointmentNumber) {

        this.appointmentId =
                appointmentId;

        this.appointmentNumber =
                appointmentNumber;

        this.patientId =
                null;

        this.status =
                "SCHEDULED";
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

        this(
                appointmentId,
                appointmentNumber,
                null,
                patientName,
                address,
                contactNumber,
                dentistName,
                treatmentType,
                appointmentDate,
                appointmentTime,
                "SCHEDULED"
        );
    }

    public Appointment(
            int appointmentId,
            String appointmentNumber,
            Integer patientId,
            String patientName,
            String address,
            String contactNumber,
            String dentistName,
            String treatmentType,
            String appointmentDate,
            String appointmentTime,
            String status) {

        this.appointmentId =
                appointmentId;

        this.appointmentNumber =
                appointmentNumber;

        this.patientId =
                patientId;

        this.patientName =
                patientName;

        this.address =
                address;

        this.contactNumber =
                contactNumber;

        this.dentistName =
                dentistName;

        this.treatmentType =
                treatmentType;

        this.appointmentDate =
                appointmentDate;

        this.appointmentTime =
                appointmentTime;

        this.status =
                status;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public String getAppointmentNumber() {
        return appointmentNumber;
    }

    public Integer getPatientId() {
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

    public String getDentistName() {
        return dentistName;
    }

    public String getTreatmentType() {
        return treatmentType;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public String getStatus() {
        return status;
    }
}