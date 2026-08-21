package com.sunrisedental.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppointmentTest {

    @Test
    void shouldCreateAppointmentWithIdAndNumber() {

        // Arrange
        int appointmentId = 1;
        String appointmentNumber = "APT001";

        // Act
        Appointment appointment =
                new Appointment(
                        appointmentId,
                        appointmentNumber
                );

        // Assert
        assertEquals(
                appointmentId,
                appointment.getAppointmentId()
        );

        assertEquals(
                appointmentNumber,
                appointment.getAppointmentNumber()
        );
    }

    @Test
    void shouldCreateAppointmentWithFullDetails() {

        // Arrange
        int appointmentId = 2;
        String appointmentNumber = "APT002";
        String patientName = "Nimal Perera";
        String address = "Colombo";
        String contactNumber = "0771234567";
        String dentistName = "Dr Silva";
        String treatmentType = "Cleaning";
        String appointmentDate = "2026-08-25";
        String appointmentTime = "10:30";

        // Act
        Appointment appointment =
                new Appointment(
                        appointmentId,
                        appointmentNumber,
                        patientName,
                        address,
                        contactNumber,
                        dentistName,
                        treatmentType,
                        appointmentDate,
                        appointmentTime
                );

        // Assert
        assertEquals(
                patientName,
                appointment.getPatientName()
        );

        assertEquals(
                address,
                appointment.getAddress()
        );

        assertEquals(
                contactNumber,
                appointment.getContactNumber()
        );

        assertEquals(
                dentistName,
                appointment.getDentistName()
        );

        assertEquals(
                treatmentType,
                appointment.getTreatmentType()
        );

        assertEquals(
                appointmentDate,
                appointment.getAppointmentDate()
        );

        assertEquals(
                appointmentTime,
                appointment.getAppointmentTime()
        );
    }
}