package com.sunrisedental.service;

import com.sunrisedental.dao.AppointmentDAO;
import com.sunrisedental.model.Appointment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
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

        // Use complete details for a valid appointment registration.
        Appointment appointment =
                new Appointment(
                        1,
                        "APT001",
                        "Nimal Perera",
                        "Colombo",
                        "0771234567",
                        "Dr Silva",
                        "Cleaning",
                        "2026-08-25",
                        "10:30"
                );

        when(appointmentDAO.findByAppointmentNumber("APT001"))
                .thenReturn(Optional.empty());

        when(appointmentDAO.saveAppointment(appointment))
                .thenReturn(true);

        boolean result =
                appointmentService.registerAppointment(appointment);

        assertTrue(result);

        verify(appointmentDAO)
                .findByAppointmentNumber("APT001");

        verify(appointmentDAO)
                .saveAppointment(appointment);
    }

    @Test
    void registerAppointmentShouldRejectDuplicateNumber()
            throws SQLException {

        // Use complete details with a number that is already registered.
        Appointment appointment =
                new Appointment(
                        2,
                        "APT001",
                        "Kamal Silva",
                        "Kandy",
                        "0712345678",
                        "Dr Fernando",
                        "Filling",
                        "2026-08-26",
                        "11:00"
                );

        Appointment existingAppointment =
                new Appointment(
                        1,
                        "APT001",
                        "Nimal Perera",
                        "Colombo",
                        "0771234567",
                        "Dr Silva",
                        "Cleaning",
                        "2026-08-25",
                        "10:30"
                );

        when(appointmentDAO.findByAppointmentNumber("APT001"))
                .thenReturn(Optional.of(existingAppointment));

        boolean result =
                appointmentService.registerAppointment(appointment);

        assertFalse(result);

        verify(appointmentDAO)
                .findByAppointmentNumber("APT001");

        verify(appointmentDAO, never())
                .saveAppointment(appointment);
    }

    @Test
    void registerAppointmentShouldRejectBlankAppointmentNumber()
            throws SQLException {

        // Use a blank number to represent invalid appointment details.
        Appointment appointment =
                new Appointment(
                        3,
                        ""
                );

        when(appointmentDAO.findByAppointmentNumber(""))
                .thenReturn(Optional.empty());

        when(appointmentDAO.saveAppointment(appointment))
                .thenReturn(true);

        // Try to register an appointment without a valid number.
        boolean result =
                appointmentService.registerAppointment(appointment);

        // Invalid appointment details should not be saved.
        assertFalse(result);

        verify(appointmentDAO, never())
                .saveAppointment(appointment);
    }

    @Test
    void findByAppointmentNumberShouldReturnAppointment()
            throws SQLException {

        // Use an appointment number that is already registered.
        String appointmentNumber = "APT001";

        Appointment appointment =
                new Appointment(
                        1,
                        "APT001"
                );

        when(appointmentDAO.findByAppointmentNumber(appointmentNumber))
                .thenReturn(Optional.of(appointment));

        // Search for the existing appointment.
        Optional<Appointment> result =
                appointmentService.findByAppointmentNumber(
                        appointmentNumber
                );

        // The matching appointment should be returned.
        assertTrue(result.isPresent());

        assertEquals(
                "APT001",
                result.get().getAppointmentNumber()
        );

        verify(appointmentDAO)
                .findByAppointmentNumber(appointmentNumber);
    }

    @Test
    void findByAppointmentNumberShouldReturnEmptyWhenNotFound()
            throws SQLException {

        // Use an appointment number that is not registered.
        String appointmentNumber = "APT999";

        when(appointmentDAO.findByAppointmentNumber(appointmentNumber))
                .thenReturn(Optional.empty());

        // Search for an appointment that does not exist.
        Optional<Appointment> result =
                appointmentService.findByAppointmentNumber(
                        appointmentNumber
                );

        // No appointment should be returned.
        assertTrue(result.isEmpty());

        verify(appointmentDAO)
                .findByAppointmentNumber(appointmentNumber);
    }

    @Test
    void registerAppointmentShouldRejectMissingRequiredDetails()
            throws SQLException {

        // Leave the patient name empty to represent incomplete registration details.
        Appointment appointment =
                new Appointment(
                        6,
                        "APT006",
                        "",
                        "Colombo",
                        "0771234567",
                        "Dr Silva",
                        "Cleaning",
                        "2026-08-26",
                        "11:00"
                );

        when(appointmentDAO.findByAppointmentNumber("APT006"))
                .thenReturn(Optional.empty());

        when(appointmentDAO.saveAppointment(appointment))
                .thenReturn(true);

        // Try to register an appointment with missing required details.
        boolean result =
                appointmentService.registerAppointment(appointment);

        // Incomplete appointment details should not be saved.
        assertFalse(result);

        verify(appointmentDAO, never())
                .saveAppointment(appointment);
    }

    @Test
    void shouldRejectInvalidContactNumber()
            throws Exception {

        Appointment appointment =
                new Appointment(
                        0,
                        "APT100",
                        "Nimal Perera",
                        "Colombo",
                        "07712ABC67",
                        "Dr Silva",
                        "Cleaning",
                        "2026-09-10",
                        "10:30"
                );

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () ->
                                appointmentService
                                        .registerAppointment(
                                                appointment
                                        )
                );

        assertEquals(
                "Invalid contact number",
                exception.getMessage()
        );

        verifyNoInteractions(
                appointmentDAO
        );
    }

    @Test
    void shouldRejectInvalidAppointmentDate()
            throws Exception {

        Appointment appointment =
                new Appointment(
                        0,
                        "APT101",
                        "Nimal Perera",
                        "Colombo",
                        "0771234567",
                        "Dr Silva",
                        "Cleaning",
                        "2026-02-30",
                        "10:30"
                );

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () ->
                                appointmentService
                                        .registerAppointment(
                                                appointment
                                        )
                );

        assertEquals(
                "Invalid appointment date",
                exception.getMessage()
        );

        verifyNoInteractions(
                appointmentDAO
        );
    }

    @Test
    void shouldRejectInvalidAppointmentTime()
            throws Exception {

        Appointment appointment =
                new Appointment(
                        0,
                        "APT102",
                        "Nimal Perera",
                        "Colombo",
                        "0771234567",
                        "Dr Silva",
                        "Cleaning",
                        "2026-09-15",
                        "25:30"
                );

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () ->
                                appointmentService
                                        .registerAppointment(
                                                appointment
                                        )
                );

        assertEquals(
                "Invalid appointment time",
                exception.getMessage()
        );

        verifyNoInteractions(
                appointmentDAO
        );
    }
}

