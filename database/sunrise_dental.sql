CREATE DATABASE sunrise_dental
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE sunrise_dental;

-- USERS
-- Stores authorised staff login credentials.

CREATE TABLE users (

                       user_id INT
                           NOT NULL
                           AUTO_INCREMENT,

                       username VARCHAR(50)
                           NOT NULL,

                       password VARCHAR(255)
                           NOT NULL,

                       role VARCHAR(20)
                           NOT NULL,

                       active BOOLEAN
                           NOT NULL
                           DEFAULT TRUE,

                       PRIMARY KEY (user_id),

                       CONSTRAINT uq_users_username
                           UNIQUE (username),

                       CONSTRAINT chk_users_role
                           CHECK (role IN ('ADMIN', 'STAFF'))
)
    ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

-- PATIENTS
-- Stores patient records managed by clinic staff.

CREATE TABLE patients (

                          patient_id INT
                              NOT NULL
                              AUTO_INCREMENT,

                          patient_name VARCHAR(100)
                              NOT NULL,

                          address VARCHAR(255)
                              NOT NULL,

                          contact_number VARCHAR(10)
                              NOT NULL,

                          email VARCHAR(100),

                          PRIMARY KEY (patient_id)
)
    ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

-- DENTISTS
-- Stores dentists available for clinic appointments.

CREATE TABLE dentists (

                          dentist_id INT
                              NOT NULL
                              AUTO_INCREMENT,

                          dentist_name VARCHAR(100)
                              NOT NULL,

                          specialization VARCHAR(100),

                          contact_number VARCHAR(10),

                          active BOOLEAN
                              NOT NULL
                              DEFAULT TRUE,

                          PRIMARY KEY (dentist_id)
)
    ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

-- TREATMENTS
-- Stores treatment types and billing charges.

-- Charges below are sample values used for development
-- and system demonstration.

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

-- APPOINTMENTS
-- Stores patient and appointment details.

-- Column names remain aligned with Appointment.java
-- and AppointmentDAO.java.

CREATE TABLE appointments (

                              appointment_id INT
                                  NOT NULL
                                  AUTO_INCREMENT,

                              appointment_number VARCHAR(20)
                                  NOT NULL,

                              patient_id INT
                                  NULL,

                              dentist_id INT
                                  NULL,

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

                              status VARCHAR(20)
                                  NOT NULL
                                  DEFAULT 'SCHEDULED',

                              PRIMARY KEY (appointment_id),

                              CONSTRAINT uq_appointments_number
                                  UNIQUE (appointment_number),

                              CONSTRAINT fk_appointments_treatment
                                  FOREIGN KEY (treatment_type)
                                      REFERENCES treatments (treatment_name)
                                      ON UPDATE CASCADE
                                      ON DELETE RESTRICT,

                              CONSTRAINT fk_appointments_patient
                                  FOREIGN KEY (patient_id)
                                      REFERENCES patients (patient_id),

                              CONSTRAINT fk_appointments_dentist
                                  FOREIGN KEY (dentist_id)
                                      REFERENCES dentists (dentist_id),

                              CONSTRAINT chk_appointment_status
                                  CHECK (
                                      status IN (
                                                 'SCHEDULED',
                                                 'COMPLETED',
                                                 'CANCELLED'
                                          )
                                      )
)
    ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

-- INITIAL AUTHORISED STAFF ACCOUNT
-- Development/demo account.

INSERT INTO users (
    username,
    password,
    role
)
VALUES (
           'admin',
           'admin123',
           'ADMIN'
       );

-- INITIAL TREATMENT DATA

-- These are development/demo assumptions.
-- Cleaning values match the billing data already used
-- in the existing unit tests.

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



-- VERIFICATION QUERIES

SHOW TABLES;

DESCRIBE users;

DESCRIBE patients;

DESCRIBE dentists;

DESCRIBE treatments;

DESCRIBE appointments;

SELECT * FROM users;

SELECT * FROM patients;

SELECT * FROM dentists;

SELECT * FROM treatments;

SELECT * FROM appointments;