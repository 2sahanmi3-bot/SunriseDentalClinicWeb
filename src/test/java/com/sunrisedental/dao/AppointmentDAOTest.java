package com.sunrisedental.dao;

import com.sunrisedental.model.Appointment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    void findByAppointmentNumberShouldReturnAppointment()
            throws SQLException {

        // Use an appointment number that exists in the database result.
        String appointmentNumber = "APT001";

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

        when(resultSet.getInt("appointment_id"))
                .thenReturn(1);

        when(resultSet.getString("appointment_number"))
                .thenReturn("APT001");

        try (MockedStatic<DBConnectionFactory> mocked =
                     mockStatic(DBConnectionFactory.class)) {

            mocked.when(DBConnectionFactory::getConnection)
                    .thenReturn(connection);

            // Search using the appointment number.
            Optional<Appointment> result =
                    appointmentDAO.findByAppointmentNumber(
                            appointmentNumber
                    );

            // Check that the matching appointment is returned.
            assertTrue(result.isPresent());

            assertEquals(
                    1,
                    result.get().getAppointmentId()
            );

            assertEquals(
                    "APT001",
                    result.get().getAppointmentNumber()
            );

            verify(statement)
                    .setString(1, appointmentNumber);

            verify(statement)
                    .executeQuery();
        }
    }
}