package com.sunrisedental.service;

import com.sunrisedental.dao.AppointmentDAO;
import com.sunrisedental.model.Appointment;

import java.sql.SQLException;
import java.util.Optional;

public class AppointmentService {

    private AppointmentDAO appointmentDAO;

    public AppointmentService(AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }

    public boolean registerAppointment(Appointment appointment)
            throws SQLException {

        Optional<Appointment> existingAppointment =
                appointmentDAO.findByAppointmentNumber(
                        appointment.getAppointmentNumber()
                );

        // Save the appointment only when its number is available.
        if (existingAppointment.isPresent()) {
            return false;
        }

        return appointmentDAO.saveAppointment(appointment);
    }
}