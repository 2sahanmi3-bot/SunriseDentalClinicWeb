-- Sunrise Dental Clinic Database
-- Database structure aligned with the tested DAO classes.

CREATE DATABASE IF NOT EXISTS sunrise_dental;

USE sunrise_dental;


-- Staff users used for system authentication.
CREATE TABLE IF NOT EXISTS users (

                                     user_id INT AUTO_INCREMENT PRIMARY KEY,

                                     username VARCHAR(50)
    NOT NULL
    UNIQUE,

    password VARCHAR(255)
    NOT NULL
    );


-- Stores registered dental appointments.
CREATE TABLE IF NOT EXISTS appointments (

                                            appointment_id INT
                                            AUTO_INCREMENT
                                            PRIMARY KEY,

                                            appointment_number VARCHAR(20)
    NOT NULL
    UNIQUE,

    patient_name VARCHAR(100)
    NOT NULL,

    address VARCHAR(255)
    NOT NULL,

    contact_number VARCHAR(20)
    NOT NULL,

    dentist_name VARCHAR(100)
    NOT NULL,

    treatment_type VARCHAR(100)
    NOT NULL,

    appointment_date DATE
    NOT NULL,

    appointment_time TIME
    NOT NULL
    );


-- Initial authorised staff account for system testing.
INSERT IGNORE INTO users (
    username,
    password
)
VALUES (
    'admin',
    'admin123'
);