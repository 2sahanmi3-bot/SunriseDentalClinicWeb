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
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    void findByAppointmentNumberShouldReturnEmptyWhenNotFound()
            throws SQLException {

        // Use an appointment number that has no matching database result.
        String appointmentNumber = "APT999";

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

            // Search for an appointment that does not exist.
            Optional<Appointment> result =
                    appointmentDAO.findByAppointmentNumber(
                            appointmentNumber
                    );

            // No appointment should be returned.
            assertTrue(result.isEmpty());

            verify(statement)
                    .setString(1, appointmentNumber);

            verify(statement)
                    .executeQuery();
        }
    }

    @Test
    void saveAppointmentShouldCloseResourcesWhenSaveFails()
            throws SQLException {

        // Use a normal appointment but make the database save fail.
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
                    () -> appointmentDAO.saveAppointment(appointment)
            );

            // Resources should be closed even when the save fails.
            verify(statement)
                    .close();

            verify(connection)
                    .close();
        }
    }

    @Test
    void findByAppointmentNumberShouldCloseResourcesWhenSearchFails()
            throws SQLException {

        // Use a normal search but make reading the result fail.
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
                    () -> appointmentDAO.findByAppointmentNumber(
                            appointmentNumber
                    )
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

    @Test
    void saveAppointmentShouldStoreFullAppointmentDetails()
            throws SQLException {

        // Use all details required when registering an appointment.
        Appointment appointment =
                new Appointment(
                        2,
                        "APT002",
                        "Nimal Perera",
                        "Colombo",
                        "0771234567",
                        "Dr Silva",
                        "Cleaning",
                        "2026-08-25",
                        "10:30"
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

            boolean result =
                    appointmentDAO.saveAppointment(appointment);

            assertTrue(result);

            verify(statement)
                    .setInt(
                            1,
                            appointment.getAppointmentId()
                    );

            verify(statement)
                    .setString(
                            2,
                            appointment.getAppointmentNumber()
                    );

            verify(statement)
                    .setString(
                            3,
                            appointment.getPatientName()
                    );

            verify(statement)
                    .setString(
                            4,
                            appointment.getAddress()
                    );

            verify(statement)
                    .setString(
                            5,
                            appointment.getContactNumber()
                    );

            verify(statement)
                    .setString(
                            6,
                            appointment.getDentistName()
                    );

            verify(statement)
                    .setString(
                            7,
                            appointment.getTreatmentType()
                    );

            verify(statement)
                    .setString(
                            8,
                            appointment.getAppointmentDate()
                    );

            verify(statement)
                    .setString(
                            9,
                            appointment.getAppointmentTime()
                    );

            verify(statement)
                    .executeUpdate();
        }
    }

    @Test
    void findByAppointmentNumberShouldReturnFullAppointmentDetails()
            throws SQLException {

        // Use a complete database result for an existing appointment.
        String appointmentNumber = "APT002";

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
                .thenReturn(2);

        when(resultSet.getString("appointment_number"))
                .thenReturn("APT002");

        when(resultSet.getString("patient_name"))
                .thenReturn("Nimal Perera");

        when(resultSet.getString("address"))
                .thenReturn("Colombo");

        when(resultSet.getString("contact_number"))
                .thenReturn("0771234567");

        when(resultSet.getString("dentist_name"))
                .thenReturn("Dr Silva");

        when(resultSet.getString("treatment_type"))
                .thenReturn("Cleaning");

        when(resultSet.getString("appointment_date"))
                .thenReturn("2026-08-25");

        when(resultSet.getString("appointment_time"))
                .thenReturn("10:30");

        try (MockedStatic<DBConnectionFactory> mocked =
                     mockStatic(DBConnectionFactory.class)) {

            mocked.when(DBConnectionFactory::getConnection)
                    .thenReturn(connection);

            Optional<Appointment> result =
                    appointmentDAO.findByAppointmentNumber(
                            appointmentNumber
                    );

            assertTrue(result.isPresent());

            Appointment appointment =
                    result.get();

            assertEquals(
                    "Nimal Perera",
                    appointment.getPatientName()
            );

            assertEquals(
                    "Colombo",
                    appointment.getAddress()
            );

            assertEquals(
                    "0771234567",
                    appointment.getContactNumber()
            );

            assertEquals(
                    "Dr Silva",
                    appointment.getDentistName()
            );

            assertEquals(
                    "Cleaning",
                    appointment.getTreatmentType()
            );

            assertEquals(
                    "2026-08-25",
                    appointment.getAppointmentDate()
            );

            assertEquals(
                    "10:30",
                    appointment.getAppointmentTime()
            );
        }
    }
}