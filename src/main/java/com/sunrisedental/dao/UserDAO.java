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

        Connection connection =
                DBConnectionFactory.getConnection();

        PreparedStatement statement =
                connection.prepareStatement(
                        "SELECT user_id, username, password " +
                                "FROM users " +
                                "WHERE username = ?"
                );

        statement.setString(
                1,
                username
        );

        ResultSet resultSet =
                statement.executeQuery();

        if (resultSet.next()) {

            User user =
                    new User(
                            resultSet.getInt("user_id"),
                            resultSet.getString("username"),
                            resultSet.getString("password")
                    );

            return Optional.of(user);
        }

        return Optional.empty();
    }
}