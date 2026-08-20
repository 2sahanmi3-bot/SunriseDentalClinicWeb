package com.sunrisedental.service;

import com.sunrisedental.dao.AppointmentDAO;
import com.sunrisedental.model.Appointment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

class AppointmentServiceTest {

    private AppointmentDAO appointmentDAO;
    private AppointmentService appointmentService;

    @BeforeEach
    void setUp() {
        appointmentDAO = mock(AppointmentDAO.class);
        appointmentService = new AppointmentService(appointmentDAO);
    }

    @Test
    void registerAppointmentShouldSaveAppointment()
            throws SQLException {

        Appointment appointment =
                new Appointment(
                        1,
                        "APT001"
                );

        when(appointmentDAO.saveAppointment(appointment))
                .thenReturn(true);

        boolean result =
                appointmentService.registerAppointment(appointment);

        assertTrue(result);

        verify(appointmentDAO)
                .saveAppointment(appointment);
    }

    @Test
    void registerAppointmentShouldRejectDuplicateNumber()
            throws SQLException {

        // Use an appointment number that is already registered.
        Appointment appointment =
                new Appointment(
                        2,
                        "APT001"
                );

        Appointment existingAppointment =
                new Appointment(
                        1,
                        "APT001"
                );

        when(appointmentDAO.findByAppointmentNumber("APT001"))
                .thenReturn(Optional.of(existingAppointment));

        // Try to register another appointment with the same number.
        boolean result =
                appointmentService.registerAppointment(appointment);

        // A duplicate should not be saved.
        assertFalse(result);

        verify(appointmentDAO)
                .findByAppointmentNumber("APT001");

        verify(appointmentDAO, never())
                .saveAppointment(appointment);
    }
}