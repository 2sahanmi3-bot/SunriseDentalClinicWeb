package com.sunrisedental.dao;

import com.sunrisedental.model.Appointment;
import com.sunrisedental.model.TreatmentReportRow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class ReportDAO {

    public List<Appointment> findAppointmentsByDate(
            String appointmentDate)
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
                                    "WHERE appointment_date = ? " +
                                    "ORDER BY appointment_time"
                    );

            statement.setString(
                    1,
                    appointmentDate
            );

            resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                appointments.add(
                        mapAppointment(
                                resultSet
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

    public List<Appointment> findDentistSchedule(
            int dentistId,
            String appointmentDate)
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
                                    "WHERE dentist_id = ? " +
                                    "AND appointment_date = ? " +
                                    "ORDER BY appointment_time"
                    );

            statement.setInt(
                    1,
                    dentistId
            );

            statement.setString(
                    2,
                    appointmentDate
            );

            resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                appointments.add(
                        mapAppointment(
                                resultSet
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

    public List<TreatmentReportRow> getTreatmentSummary()
            throws SQLException {

        List<TreatmentReportRow> summary =
                new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            connection =
                    DBConnectionFactory.getConnection();

            statement =
                    connection.prepareStatement(
                            "SELECT treatment_type, " +
                                    "COUNT(*) AS total_appointments, " +
                                    "SUM(CASE WHEN status = 'COMPLETED' " +
                                    "THEN 1 ELSE 0 END) AS completed_appointments, " +
                                    "SUM(CASE WHEN status = 'CANCELLED' " +
                                    "THEN 1 ELSE 0 END) AS cancelled_appointments " +
                                    "FROM appointments " +
                                    "GROUP BY treatment_type " +
                                    "ORDER BY total_appointments DESC, treatment_type"
                    );

            resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                summary.add(
                        new TreatmentReportRow(
                                resultSet.getString(
                                        "treatment_type"
                                ),
                                resultSet.getInt(
                                        "total_appointments"
                                ),
                                resultSet.getInt(
                                        "completed_appointments"
                                ),
                                resultSet.getInt(
                                        "cancelled_appointments"
                                )
                        )
                );
            }

            return summary;

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

    private Appointment mapAppointment(
            ResultSet resultSet)
            throws SQLException {

        return new Appointment(
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
        );
    }
}
