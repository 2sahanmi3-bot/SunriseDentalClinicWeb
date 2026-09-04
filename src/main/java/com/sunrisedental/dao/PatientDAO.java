package com.sunrisedental.dao;

import com.sunrisedental.model.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PatientDAO {

    public boolean savePatient(
            Patient patient)
            throws SQLException {

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection =
                    DBConnectionFactory.getConnection();

            statement =
                    connection.prepareStatement(
                            "INSERT INTO patients " +
                                    "(patient_name, address, contact_number, email) " +
                                    "VALUES (?, ?, ?, ?)"
                    );

            statement.setString(
                    1,
                    patient.getPatientName()
            );

            statement.setString(
                    2,
                    patient.getAddress()
            );

            statement.setString(
                    3,
                    patient.getContactNumber()
            );

            statement.setString(
                    4,
                    patient.getEmail()
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

    public List<Patient> findByContactNumber(
            String contactNumber)
            throws SQLException {

        List<Patient> patients =
                new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            connection =
                    DBConnectionFactory.getConnection();

            statement =
                    connection.prepareStatement(
                            "SELECT patient_id, patient_name, address, " +
                                    "contact_number, email " +
                                    "FROM patients " +
                                    "WHERE contact_number = ? " +
                                    "ORDER BY patient_name"
                    );

            statement.setString(
                    1,
                    contactNumber
            );

            resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                patients.add(
                        new Patient(
                                resultSet.getInt(
                                        "patient_id"
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
                                        "email"
                                )
                        )
                );
            }

            return patients;

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

    public Optional<Patient> findById(
            int patientId)
            throws SQLException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            connection =
                    DBConnectionFactory.getConnection();

            statement =
                    connection.prepareStatement(
                            "SELECT patient_id, patient_name, address, " +
                                    "contact_number, email " +
                                    "FROM patients " +
                                    "WHERE patient_id = ?"
                    );

            statement.setInt(
                    1,
                    patientId
            );

            resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                return Optional.of(
                        new Patient(
                                resultSet.getInt("patient_id"),
                                resultSet.getString("patient_name"),
                                resultSet.getString("address"),
                                resultSet.getString("contact_number"),
                                resultSet.getString("email")
                        )
                );
            }

            return Optional.empty();

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

    public boolean updatePatient(
            Patient patient)
            throws SQLException {

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection =
                    DBConnectionFactory.getConnection();

            statement =
                    connection.prepareStatement(
                            "UPDATE patients " +
                                    "SET patient_name = ?, " +
                                    "address = ?, " +
                                    "contact_number = ?, " +
                                    "email = ? " +
                                    "WHERE patient_id = ?"
                    );

            statement.setString(
                    1,
                    patient.getPatientName()
            );

            statement.setString(
                    2,
                    patient.getAddress()
            );

            statement.setString(
                    3,
                    patient.getContactNumber()
            );

            statement.setString(
                    4,
                    patient.getEmail()
            );

            statement.setInt(
                    5,
                    patient.getPatientId()
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
}