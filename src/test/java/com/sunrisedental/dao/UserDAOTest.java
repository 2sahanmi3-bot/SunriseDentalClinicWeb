package com.sunrisedental.dao;

import com.sunrisedental.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserDAOTest {

    private UserDAO userDAO;

    @BeforeEach
    void setUp() {
        userDAO = new UserDAO();
    }

    @Test
    void findByUsernameShouldReturnStaffUser()
            throws SQLException {

        // Use a username that exists in the database result.
        String username = "admin";

        Connection connection =
                mock(Connection.class);

        PreparedStatement statement =
                mock(PreparedStatement.class);

        ResultSet resultSet =
                mock(ResultSet.class);

        when(connection.prepareStatement(anyString()))
                .thenReturn(statement);

        when(statement.executeQuery())
                .thenReturn(resultSet);

        when(resultSet.next())
                .thenReturn(true);

        when(resultSet.getInt("user_id"))
                .thenReturn(1);

        when(resultSet.getString("username"))
                .thenReturn("admin");

        when(resultSet.getString("password"))
                .thenReturn("admin123");

        try (MockedStatic<DBConnectionFactory> mocked =
                     mockStatic(DBConnectionFactory.class)) {

            mocked.when(DBConnectionFactory::getConnection)
                    .thenReturn(connection);

            Optional<User> result =
                    userDAO.findByUsername(username);

            assertTrue(result.isPresent());

            assertEquals(
                    "admin",
                    result.get().getUsername()
            );

            assertEquals(
                    "admin123",
                    result.get().getPassword()
            );

            verify(statement)
                    .setString(1, username);

            verify(statement)
                    .executeQuery();
        }
    }

    @Test
    void findByUsernameShouldReturnEmptyWhenStaffNotFound()
            throws SQLException {

        // Use a username that has no matching database result.
        String username = "unknown";

        Connection connection =
                mock(Connection.class);

        PreparedStatement statement =
                mock(PreparedStatement.class);

        ResultSet resultSet =
                mock(ResultSet.class);

        when(connection.prepareStatement(anyString()))
                .thenReturn(statement);

        when(statement.executeQuery())
                .thenReturn(resultSet);

        when(resultSet.next())
                .thenReturn(false);

        try (MockedStatic<DBConnectionFactory> mocked =
                     mockStatic(DBConnectionFactory.class)) {

            mocked.when(DBConnectionFactory::getConnection)
                    .thenReturn(connection);

            Optional<User> result =
                    userDAO.findByUsername(username);

            assertTrue(result.isEmpty());

            verify(statement)
                    .setString(1, username);

            verify(statement)
                    .executeQuery();
        }
    }

    @Test
    void findByUsernameShouldCloseResourcesWhenSearchFails()
            throws SQLException {

        // Use a normal search but make reading the result fail.
        String username = "admin";

        Connection connection =
                mock(Connection.class);

        PreparedStatement statement =
                mock(PreparedStatement.class);

        ResultSet resultSet =
                mock(ResultSet.class);

        when(connection.prepareStatement(anyString()))
                .thenReturn(statement);

        when(statement.executeQuery())
                .thenReturn(resultSet);

        when(resultSet.next())
                .thenThrow(
                        new SQLException("Database error")
                );

        try (MockedStatic<DBConnectionFactory> mocked =
                     mockStatic(DBConnectionFactory.class)) {

            mocked.when(DBConnectionFactory::getConnection)
                    .thenReturn(connection);

            // The database error should still be reported.
            assertThrows(
                    SQLException.class,
                    () -> userDAO.findByUsername(username)
            );

            // Resources should be closed after the failed search.
            verify(resultSet)
                    .close();

            verify(statement)
                    .close();

            verify(connection)
                    .close();
        }
    }
}