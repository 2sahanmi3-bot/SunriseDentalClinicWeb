package com.sunrisedental.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionFactory {

    private static final String URL =
            "jdbc:mysql://localhost:3306/sunrise_dental";

    private static final String USERNAME =
            System.getenv("SUNRISE_DB_USERNAME");

    private static final String PASSWORD =
            System.getenv("SUNRISE_DB_PASSWORD");


    public static Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(
                URL,
                USERNAME,
                PASSWORD
        );
    }
}