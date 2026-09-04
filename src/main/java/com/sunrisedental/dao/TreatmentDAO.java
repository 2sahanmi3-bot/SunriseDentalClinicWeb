package com.sunrisedental.dao;

import com.sunrisedental.model.Treatment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TreatmentDAO {

    public boolean saveTreatment(
            Treatment treatment)
            throws SQLException {

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection =
                    DBConnectionFactory.getConnection();

            statement =
                    connection.prepareStatement(
                            "INSERT INTO treatments " +
                                    "(treatment_name, treatment_charge, " +
                                    "consultation_fee, active) " +
                                    "VALUES (?, ?, ?, ?)"
                    );

            statement.setString(
                    1,
                    treatment.getTreatmentName()
            );

            statement.setDouble(
                    2,
                    treatment.getTreatmentCharge()
            );

            statement.setDouble(
                    3,
                    treatment.getConsultationFee()
            );

            statement.setBoolean(
                    4,
                    treatment.isActive()
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

    public List<Treatment> findAllTreatments()
            throws SQLException {

        List<Treatment> treatments =
                new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            connection =
                    DBConnectionFactory.getConnection();

            statement =
                    connection.prepareStatement(
                            "SELECT treatment_id, treatment_name, " +
                                    "treatment_charge, consultation_fee, active " +
                                    "FROM treatments " +
                                    "ORDER BY treatment_name"
                    );

            resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                treatments.add(
                        mapTreatment(
                                resultSet
                        )
                );
            }

            return treatments;

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

    public List<Treatment> findActiveTreatments()
            throws SQLException {

        List<Treatment> treatments =
                new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            connection =
                    DBConnectionFactory.getConnection();

            statement =
                    connection.prepareStatement(
                            "SELECT treatment_id, treatment_name, " +
                                    "treatment_charge, consultation_fee, active " +
                                    "FROM treatments " +
                                    "WHERE active = TRUE " +
                                    "ORDER BY treatment_name"
                    );

            resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                treatments.add(
                        mapTreatment(
                                resultSet
                        )
                );
            }

            return treatments;

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

    public Optional<Treatment> findByTreatmentName(
            String treatmentName)
            throws SQLException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            connection =
                    DBConnectionFactory.getConnection();

            statement =
                    connection.prepareStatement(
                            "SELECT treatment_id, treatment_name, " +
                                    "treatment_charge, consultation_fee, active " +
                                    "FROM treatments " +
                                    "WHERE treatment_name = ?"
                    );

            statement.setString(
                    1,
                    treatmentName
            );

            resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                return Optional.of(
                        mapTreatment(
                                resultSet
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

    public Optional<Treatment> findById(
            int treatmentId)
            throws SQLException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            connection =
                    DBConnectionFactory.getConnection();

            statement =
                    connection.prepareStatement(
                            "SELECT treatment_id, treatment_name, " +
                                    "treatment_charge, consultation_fee, active " +
                                    "FROM treatments " +
                                    "WHERE treatment_id = ?"
                    );

            statement.setInt(
                    1,
                    treatmentId
            );

            resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                return Optional.of(
                        mapTreatment(
                                resultSet
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

    public boolean updateTreatment(
            Treatment treatment)
            throws SQLException {

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection =
                    DBConnectionFactory.getConnection();

            statement =
                    connection.prepareStatement(
                            "UPDATE treatments " +
                                    "SET treatment_name = ?, " +
                                    "treatment_charge = ?, " +
                                    "consultation_fee = ? " +
                                    "WHERE treatment_id = ?"
                    );

            statement.setString(
                    1,
                    treatment.getTreatmentName()
            );

            statement.setDouble(
                    2,
                    treatment.getTreatmentCharge()
            );

            statement.setDouble(
                    3,
                    treatment.getConsultationFee()
            );

            statement.setInt(
                    4,
                    treatment.getTreatmentId()
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

    public boolean updateTreatmentStatus(
            int treatmentId,
            boolean active)
            throws SQLException {

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection =
                    DBConnectionFactory.getConnection();

            statement =
                    connection.prepareStatement(
                            "UPDATE treatments " +
                                    "SET active = ? " +
                                    "WHERE treatment_id = ?"
                    );

            statement.setBoolean(
                    1,
                    active
            );

            statement.setInt(
                    2,
                    treatmentId
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

    private Treatment mapTreatment(
            ResultSet resultSet)
            throws SQLException {

        return new Treatment(
                resultSet.getInt(
                        "treatment_id"
                ),
                resultSet.getString(
                        "treatment_name"
                ),
                resultSet.getDouble(
                        "treatment_charge"
                ),
                resultSet.getDouble(
                        "consultation_fee"
                ),
                resultSet.getBoolean(
                        "active"
                )
        );
    }
}
