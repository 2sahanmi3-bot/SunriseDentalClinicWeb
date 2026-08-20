package com.sunrisedental.dao;

import org.junit.jupiter.api.BeforeEach;

class AppointmentDAOTest {

    private AppointmentDAO appointmentDAO;

    @BeforeEach
    void setUp() {
        appointmentDAO = new AppointmentDAO();
    }
}