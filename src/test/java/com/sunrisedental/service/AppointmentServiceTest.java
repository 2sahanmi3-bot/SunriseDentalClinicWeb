package com.sunrisedental.service;

import com.sunrisedental.dao.AppointmentDAO;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

class AppointmentServiceTest {

    private AppointmentDAO appointmentDAO;
    private AppointmentService appointmentService;

    @BeforeEach
    void setUp() {
        appointmentDAO = mock(AppointmentDAO.class);
        appointmentService = new AppointmentService(appointmentDAO);
    }
}