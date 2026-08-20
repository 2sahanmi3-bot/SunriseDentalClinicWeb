package com.sunrisedental.dao;

import com.sunrisedental.model.Appointment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class AppointmentDAOTest {

    private AppointmentDAO appointmentDAO;

    @BeforeEach
    void setUp() {
        appointmentDAO = new AppointmentDAO();
    }

    @Test
    void shouldSaveAppointmentSuccessfully()
            throws SQLException {

        // Arrange
        Appointment appointment =
                new Appointment(
                        1,
                        "APT001"
                );

        Connection connection =
                mock(Connection.class);

        PreparedStatement statement =
                mock(PreparedStatement.class);

        when(connection.prepareStatement(anyString()))
                .thenReturn(statement);

        when(statement.executeUpdate())
                .thenReturn(1);

        try (MockedStatic<DBConnectionFactory> mocked =
                     mockStatic(DBConnectionFactory.class)) {

            mocked.when(DBConnectionFactory::getConnection)
                    .thenReturn(connection);

            // Act
            boolean result =
                    appointmentDAO.saveAppointment(appointment);

            // Assert
            assertTrue(result);

            verify(statement)
                    .executeUpdate();
        }
    }
}