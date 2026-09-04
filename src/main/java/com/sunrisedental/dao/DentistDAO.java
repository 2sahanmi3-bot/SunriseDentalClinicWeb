package com.sunrisedental.dao;

import com.sunrisedental.model.Dentist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DentistDAO {

    public boolean saveDentist(
            Dentist dentist)
            throws SQLException {

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection =
                    DBConnectionFactory.getConnection();

            statement =
                    connection.prepareStatement(
                            "INSERT INTO dentists " +
                                    "(dentist_name, specialization, " +
                                    "contact_number, active) " +
                                    "VALUES (?, ?, ?, ?)"
                    );

            statement.setString(
                    1,
                    dentist.getDentistName()
            );

            statement.setString(
                    2,
                    dentist.getSpecialization()
            );

            statement.setString(
                    3,
                    dentist.getContactNumber()
            );

            statement.setBoolean(
                    4,
                    dentist.isActive()
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

    public List<Dentist> findAllDentists()
            throws SQLException {

        List<Dentist> dentists =
                new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            connection =
                    DBConnectionFactory.getConnection();

            statement =
                    connection.prepareStatement(
                            "SELECT dentist_id, dentist_name, " +
                                    "specialization, contact_number, active " +
                                    "FROM dentists " +
                                    "ORDER BY dentist_name"
                    );

            resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                dentists.add(
                        mapDentist(
                                resultSet
                        )
                );
            }

            return dentists;

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

    public List<Dentist> findActiveDentists()
            throws SQLException {

        List<Dentist> dentists =
                new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            connection =
                    DBConnectionFactory.getConnection();

            statement =
                    connection.prepareStatement(
                            "SELECT dentist_id, dentist_name, " +
                                    "specialization, contact_number, active " +
                                    "FROM dentists " +
                                    "WHERE active = TRUE " +
                                    "ORDER BY dentist_name"
                    );

            resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                dentists.add(
                        mapDentist(
                                resultSet
                        )
                );
            }

            return dentists;

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

    public Optional<Dentist> findById(
            int dentistId)
            throws SQLException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            connection =
                    DBConnectionFactory.getConnection();

            statement =
                    connection.prepareStatement(
                            "SELECT dentist_id, dentist_name, " +
                                    "specialization, contact_number, active " +
                                    "FROM dentists " +
                                    "WHERE dentist_id = ?"
                    );

            statement.setInt(
                    1,
                    dentistId
            );

            resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                return Optional.of(
                        mapDentist(
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

    public boolean updateDentist(
            Dentist dentist)
            throws SQLException {

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection =
                    DBConnectionFactory.getConnection();

            statement =
                    connection.prepareStatement(
                            "UPDATE dentists " +
                                    "SET dentist_name = ?, " +
                                    "specialization = ?, " +
                                    "contact_number = ? " +
                                    "WHERE dentist_id = ?"
                    );

            statement.setString(
                    1,
                    dentist.getDentistName()
            );

            statement.setString(
                    2,
                    dentist.getSpecialization()
            );

            statement.setString(
                    3,
                    dentist.getContactNumber()
            );

            statement.setInt(
                    4,
                    dentist.getDentistId()
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

    public boolean updateDentistStatus(
            int dentistId,
            boolean active)
            throws SQLException {

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection =
                    DBConnectionFactory.getConnection();

            statement =
                    connection.prepareStatement(
                            "UPDATE dentists " +
                                    "SET active = ? " +
                                    "WHERE dentist_id = ?"
                    );

            statement.setBoolean(
                    1,
                    active
            );

            statement.setInt(
                    2,
                    dentistId
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

    private Dentist mapDentist(
            ResultSet resultSet)
            throws SQLException {

        return new Dentist(
                resultSet.getInt(
                        "dentist_id"
                ),
                resultSet.getString(
                        "dentist_name"
                ),
                resultSet.getString(
                        "specialization"
                ),
                resultSet.getString(
                        "contact_number"
                ),
                resultSet.getBoolean(
                        "active"
                )
        );
    }
}