package com.sunrisedental.dao;

import com.sunrisedental.model.Appointment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AppointmentDAO {

    public boolean saveAppointment(
            Appointment appointment)
            throws SQLException {

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection =
                    DBConnectionFactory.getConnection();

            statement =
                    connection.prepareStatement(
                            "INSERT INTO appointments " +
                                    "(appointment_id, appointment_number, " +
                                    "patient_name, address, contact_number, " +
                                    "dentist_name, treatment_type, " +
                                    "appointment_date, appointment_time, " +
                                    "patient_id, dentist_id, status) " +
                                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
                    );

            statement.setInt(
                    1,
                    appointment.getAppointmentId()
            );

            statement.setString(
                    2,
                    appointment.getAppointmentNumber()
            );

            statement.setString(
                    3,
                    appointment.getPatientName()
            );

            statement.setString(
                    4,
                    appointment.getAddress()
            );

            statement.setString(
                    5,
                    appointment.getContactNumber()
            );

            statement.setString(
                    6,
                    appointment.getDentistName()
            );

            statement.setString(
                    7,
                    appointment.getTreatmentType()
            );

            statement.setString(
                    8,
                    appointment.getAppointmentDate()
            );

            statement.setString(
                    9,
                    appointment.getAppointmentTime()
            );

            if (appointment.getPatientId() == null) {

                statement.setNull(
                        10,
                        Types.INTEGER
                );

            } else {

                statement.setInt(
                        10,
                        appointment.getPatientId()
                );
            }

            if (appointment.getDentistId() == null) {

                statement.setNull(
                        11,
                        Types.INTEGER
                );

            } else {

                statement.setInt(
                        11,
                        appointment.getDentistId()
                );
            }

            statement.setString(
                    12,
                    appointment.getStatus()
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

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            connection =
                    DBConnectionFactory.getConnection();

            statement =
                    connection.prepareStatement(
                            "SELECT appointment_id, appointment_number, " +
                                    "patient_id, dentist_id, patient_name, " +
                                    "address, contact_number, dentist_name, " +
                                    "treatment_type, appointment_date, " +
                                    "appointment_time, status " +
                                    "FROM appointments " +
                                    "WHERE appointment_number = ?"
                    );

            statement.setString(
                    1,
                    appointmentNumber
            );

            resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                Integer patientId =
                        (Integer) resultSet.getObject(
                                "patient_id"
                        );

                Integer dentistId =
                        (Integer) resultSet.getObject(
                                "dentist_id"
                        );

                Appointment appointment =
                        new Appointment(
                                resultSet.getInt(
                                        "appointment_id"
                                ),
                                resultSet.getString(
                                        "appointment_number"
                                ),
                                patientId,
                                dentistId,
                                resultSet.getString(
                                        "patient_name"
                                ),
                                resultSet.getString(
                                        "address"
                                ),
                                resultSet.getString(
                                        "contact_number"
                                ),
                                resultSet.getString(
                                        "dentist_name"
                                ),
                                resultSet.getString(
                                        "treatment_type"
                                ),
                                resultSet.getString(
                                        "appointment_date"
                                ),
                                resultSet.getString(
                                        "appointment_time"
                                ),
                                resultSet.getString(
                                        "status"
                                )
                        );

                return Optional.of(
                        appointment
                );
            }

            return Optional.empty();

        } finally {

            // Close the result after the search attempt is finished.
            if (resultSet != null) {
                resultSet.close();
            }

            // Close the statement after the search attempt is finished.
            if (statement != null) {
                statement.close();
            }

            // Close the connection after the search attempt is finished.
            if (connection != null) {
                connection.close();
            }
        }
    }

    public List<Appointment> findByPatientId(
            int patientId)
            throws SQLException {

        List<Appointment> appointments =
                new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            connection =
                    DBConnectionFactory.getConnection();

            statement =
                    connection.prepareStatement(
                            "SELECT appointment_id, appointment_number, " +
                                    "patient_id, dentist_id, patient_name, " +
                                    "address, contact_number, dentist_name, " +
                                    "treatment_type, appointment_date, " +
                                    "appointment_time, status " +
                                    "FROM appointments " +
                                    "WHERE patient_id = ? " +
                                    "ORDER BY appointment_date DESC, " +
                                    "appointment_time DESC"
                    );

            statement.setInt(
                    1,
                    patientId
            );

            resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                appointments.add(
                        new Appointment(
                                resultSet.getInt(
                                        "appointment_id"
                                ),
                                resultSet.getString(
                                        "appointment_number"
                                ),
                                (Integer) resultSet.getObject(
                                        "patient_id"
                                ),
                                (Integer) resultSet.getObject(
                                        "dentist_id"
                                ),
                                resultSet.getString(
                                        "patient_name"
                                ),
                                resultSet.getString(
                                        "address"
                                ),
                                resultSet.getString(
                                        "contact_number"
                                ),
                                resultSet.getString(
                                        "dentist_name"
                                ),
                                resultSet.getString(
                                        "treatment_type"
                                ),
                                resultSet.getString(
                                        "appointment_date"
                                ),
                                resultSet.getString(
                                        "appointment_time"
                                ),
                                resultSet.getString(
                                        "status"
                                )
                        )
                );
            }

            return appointments;

        } finally {

            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
    }

    public boolean updateAppointmentStatus(
            String appointmentNumber,
            String status)
            throws SQLException {

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection =
                    DBConnectionFactory.getConnection();

            statement =
                    connection.prepareStatement(
                            "UPDATE appointments " +
                                    "SET status = ? " +
                                    "WHERE appointment_number = ?"
                    );

            statement.setString(
                    1,
                    status
            );

            statement.setString(
                    2,
                    appointmentNumber
            );

            return statement.executeUpdate() > 0;

        } finally {

            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
    }

    public boolean existsDentistBooking(
            int dentistId,
            String appointmentDate,
            String appointmentTime)
            throws SQLException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            connection =
                    DBConnectionFactory.getConnection();

            statement =
                    connection.prepareStatement(
                            "SELECT appointment_id " +
                                    "FROM appointments " +
                                    "WHERE dentist_id = ? " +
                                    "AND appointment_date = ? " +
                                    "AND appointment_time = ? " +
                                    "AND status = 'SCHEDULED' " +
                                    "LIMIT 1"
                    );

            statement.setInt(
                    1,
                    dentistId
            );

            statement.setString(
                    2,
                    appointmentDate
            );

            statement.setString(
                    3,
                    appointmentTime
            );

            resultSet =
                    statement.executeQuery();

            return resultSet.next();

        } finally {

            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
    }
}
