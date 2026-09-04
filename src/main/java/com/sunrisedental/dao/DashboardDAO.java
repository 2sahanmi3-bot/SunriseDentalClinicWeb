package com.sunrisedental.dao;

import com.sunrisedental.model.DashboardStats;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardDAO {

    public DashboardStats getDashboardStats()
            throws SQLException {

        Connection connection = null;

        try {

            connection =
                    DBConnectionFactory.getConnection();

            int todayAppointments =
                    getCount(
                            connection,
                            "SELECT COUNT(*) " +
                                    "FROM appointments " +
                                    "WHERE appointment_date = CURDATE() " +
                                    "AND status <> 'CANCELLED'"
                    );

            int totalPatients =
                    getCount(
                            connection,
                            "SELECT COUNT(*) " +
                                    "FROM patients"
                    );

            int activeDentists =
                    getCount(
                            connection,
                            "SELECT COUNT(*) " +
                                    "FROM dentists " +
                                    "WHERE active = TRUE"
                    );

            int activeUsers =
                    getCount(
                            connection,
                            "SELECT COUNT(*) " +
                                    "FROM users " +
                                    "WHERE active = TRUE"
                    );

            int upcomingAppointments =
                    getCount(
                            connection,
                            "SELECT COUNT(*) " +
                                    "FROM appointments " +
                                    "WHERE status = 'SCHEDULED' " +
                                    "AND (" +
                                    "appointment_date > CURDATE() " +
                                    "OR (" +
                                    "appointment_date = CURDATE() " +
                                    "AND appointment_time >= CURTIME()" +
                                    ")" +
                                    ")"
                    );

            return new DashboardStats(
                    todayAppointments,
                    totalPatients,
                    activeDentists,
                    activeUsers,
                    upcomingAppointments
            );

        } finally {

            if (connection != null) {
                connection.close();
            }
        }
    }

    private int getCount(
            Connection connection,
            String sql)
            throws SQLException {

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            statement =
                    connection.prepareStatement(
                            sql
                    );

            resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                return resultSet.getInt(
                        1
                );
            }

            return 0;

        } finally {

            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }
        }
    }
}
