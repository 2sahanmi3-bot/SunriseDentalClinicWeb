package com.sunrisedental.dao;

import com.sunrisedental.model.Treatment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class TreatmentDAO {

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
                                    "treatment_charge, consultation_fee " +
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

                Treatment treatment =
                        new Treatment(
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
                                )
                        );

                return Optional.of(
                        treatment
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
}