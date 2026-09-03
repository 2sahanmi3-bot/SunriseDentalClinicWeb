package com.sunrisedental.dao;

import com.sunrisedental.model.User;

import java.sql.SQLException;
import java.util.Optional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public Optional<User> findByUsername(String username)
            throws SQLException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            connection =
                    DBConnectionFactory.getConnection();

            statement =
                    connection.prepareStatement(
                            "SELECT user_id, username, password, role " +
                                    "FROM users " +
                                    "WHERE username = ?"
                    );

            statement.setString(
                    1,
                    username
            );

            resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                User user =
                        new User(
                                resultSet.getInt("user_id"),
                                resultSet.getString("username"),
                                resultSet.getString("password"),
                                resultSet.getString("role")
                        );

                return Optional.of(user);
            }

            return Optional.empty();

        } finally {

            // Close the result after the staff search is finished.
            if (resultSet != null) {
                resultSet.close();
            }

            // Close the statement after the staff search is finished.
            if (statement != null) {
                statement.close();
            }

            // Close the connection after the staff search is finished.
            if (connection != null) {
                connection.close();
            }
        }
    }
}