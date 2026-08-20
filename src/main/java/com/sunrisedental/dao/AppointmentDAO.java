package com.sunrisedental.dao;

import com.sunrisedental.model.Appointment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class AppointmentDAO {

    public boolean saveAppointment(Appointment appointment)
            throws SQLException {

        Connection connection =
                DBConnectionFactory.getConnection();

        PreparedStatement statement =
                connection.prepareStatement(
                        "INSERT INTO appointments " +
                                "(appointment_id, appointment_number) " +
                                "VALUES (?, ?)"
                );

        statement.setInt(
                1,
                appointment.getAppointmentId()
        );

        statement.setString(
                2,
                appointment.getAppointmentNumber()
        );

        return statement.executeUpdate() > 0;
    }
    public Optional<Appointment> findByAppointmentNumber(
            String appointmentNumber)
            throws SQLException {

        return Optional.empty();
    }
}