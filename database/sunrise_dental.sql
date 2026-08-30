

CREATE DATABASE sunrise_dental
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE sunrise_dental;


-- =========================================================
-- USERS
-- Stores authorised staff login credentials.
-- =========================================================

CREATE TABLE users (

                       user_id INT
                           NOT NULL
                           AUTO_INCREMENT,

                       username VARCHAR(50)
                           NOT NULL,

                       password VARCHAR(255)
                           NOT NULL,

                       PRIMARY KEY (user_id),

                       CONSTRAINT uq_users_username
                           UNIQUE (username)
)
    ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- =========================================================
-- TREATMENTS
-- Stores treatment types and billing charges.
--
-- Charges below are sample values used for development
-- and system demonstration.
-- =========================================================

CREATE TABLE treatments (

                            treatment_id INT
                                NOT NULL
                                AUTO_INCREMENT,

                            treatment_name VARCHAR(100)
                                NOT NULL,

                            treatment_charge DECIMAL(10, 2)
                                NOT NULL,

                            consultation_fee DECIMAL(10, 2)
                                NOT NULL,

                            PRIMARY KEY (treatment_id),

                            CONSTRAINT uq_treatments_name
                                UNIQUE (treatment_name),

                            CONSTRAINT chk_treatment_charge
                                CHECK (treatment_charge >= 0),

                            CONSTRAINT chk_consultation_fee
                                CHECK (consultation_fee >= 0)
)
    ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- =========================================================
-- APPOINTMENTS
-- Stores patient and appointment details.
--
-- Column names remain aligned with Appointment.java
-- and AppointmentDAO.java.
-- =========================================================

CREATE TABLE appointments (

                              appointment_id INT
                                  NOT NULL
                                  AUTO_INCREMENT,

                              appointment_number VARCHAR(20)
                                  NOT NULL,

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
                                  NOT NULL,

                              PRIMARY KEY (appointment_id),

                              CONSTRAINT uq_appointments_number
                                  UNIQUE (appointment_number),

                              CONSTRAINT fk_appointments_treatment
                                  FOREIGN KEY (treatment_type)
                                      REFERENCES treatments (treatment_name)
                                      ON UPDATE CASCADE
                                      ON DELETE RESTRICT
)
    ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- =========================================================
-- INITIAL AUTHORISED STAFF ACCOUNT
-- Development/demo account.
-- =========================================================

INSERT INTO users (
    username,
    password
)
VALUES (
           'admin',
           'admin123'
       );


-- =========================================================
-- INITIAL TREATMENT DATA
--
-- These are development/demo assumptions.
-- Cleaning values match the billing data already used
-- in the existing unit tests.
-- =========================================================

INSERT INTO treatments (
    treatment_name,
    treatment_charge,
    consultation_fee
)
VALUES
    ('Cleaning',   5000.00, 1500.00),
    ('Filling',    7000.00, 1500.00),
    ('Extraction', 8000.00, 1500.00),
    ('Root Canal', 15000.00, 1500.00),
    ('Crown',      20000.00, 1500.00);


-- =========================================================
-- VERIFICATION QUERIES
-- =========================================================

SHOW TABLES;

DESCRIBE users;

DESCRIBE treatments;

DESCRIBE appointments;

SELECT * FROM users;

SELECT * FROM treatments;

SELECT * FROM appointments;