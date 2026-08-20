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
}