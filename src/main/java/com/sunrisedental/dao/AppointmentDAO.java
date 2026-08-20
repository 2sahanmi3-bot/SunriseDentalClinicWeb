package com.sunrisedental.dao;

import com.sunrisedental.model.Appointment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class AppointmentDAO {

    public boolean saveAppointment(Appointment appointment)
            throws SQLException {

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection =
                    DBConnectionFactory.getConnection();

            statement =
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

        } finally {

            // Close the statement after the save attempt is finished.
            if (statement != null) {
                statement.close();
            }

            // Close the connection after the save attempt is finished.
            if (connection != null) {
                connection.close();
            }
        }
    }

    public Optional<Appointment> findByAppointmentNumber(
            String appointmentNumber)
            throws SQLException {

        Connection connection =
                DBConnectionFactory.getConnection();

        PreparedStatement statement =
                connection.prepareStatement(
                        "SELECT appointment_id, appointment_number " +
                                "FROM appointments " +
                                "WHERE appointment_number = ?"
                );

        statement.setString(
                1,
                appointmentNumber
        );

        ResultSet resultSet =
                statement.executeQuery();

        if (resultSet.next()) {

            Appointment appointment =
                    new Appointment(
                            resultSet.getInt("appointment_id"),
                            resultSet.getString("appointment_number")
                    );

            return Optional.of(appointment);
        }

        return Optional.empty();
    }
}