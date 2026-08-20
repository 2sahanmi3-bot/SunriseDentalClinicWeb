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

        String appointmentNumber =
                appointment.getAppointmentNumber();

        // Do not register an appointment without a valid number.
        if (appointmentNumber == null
                || appointmentNumber.isBlank()) {
            return false;
        }

        Optional<Appointment> existingAppointment =
                appointmentDAO.findByAppointmentNumber(
                        appointmentNumber
                );

        // Save the appointment only when its number is available.
        if (existingAppointment.isPresent()) {
            return false;
        }

        return appointmentDAO.saveAppointment(appointment);
    }

    public Optional<Appointment> findByAppointmentNumber(
            String appointmentNumber)
            throws SQLException {

        return Optional.empty();
    }
}
