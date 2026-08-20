package com.sunrisedental.service;

import com.sunrisedental.dao.AppointmentDAO;
import com.sunrisedental.model.Appointment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;
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
}