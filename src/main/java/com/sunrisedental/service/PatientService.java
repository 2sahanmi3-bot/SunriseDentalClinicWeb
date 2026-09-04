package com.sunrisedental.service;

import com.sunrisedental.dao.PatientDAO;
import com.sunrisedental.model.Patient;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PatientService {

    private PatientDAO patientDAO;

    public PatientService(
            PatientDAO patientDAO) {

        this.patientDAO =
                patientDAO;
    }

    public boolean registerPatient(
            String patientName,
            String address,
            String contactNumber,
            String email)
            throws SQLException {

        if (patientName == null
                || patientName.isBlank()
                || address == null
                || address.isBlank()
                || contactNumber == null
                || contactNumber.isBlank()) {

            throw new IllegalArgumentException(
                    "Please complete all required patient fields"
            );
        }

        patientName =
                patientName.trim();

        address =
                address.trim();

        contactNumber =
                contactNumber.trim();

        if (!contactNumber.matches(
                "0\\d{9}"
        )) {

            throw new IllegalArgumentException(
                    "Invalid contact number"
            );
        }

        if (email != null) {

            email =
                    email.trim();

            if (email.isBlank()) {

                email =
                        null;

            } else if (!email.matches(
                    "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$"
            )) {

                throw new IllegalArgumentException(
                        "Invalid email address"
                );
            }
        }

        Patient patient =
                new Patient(
                        0,
                        patientName,
                        address,
                        contactNumber,
                        email
                );

        return patientDAO.savePatient(
                patient
        );
    }

    public Patient findOrCreatePatient(
            String patientName,
            String address,
            String contactNumber,
            String email)
            throws SQLException {

        patientName =
                validatePatientName(
                        patientName
                );

        address =
                validateAddress(
                        address
                );

        contactNumber =
                validateContactNumber(
                        contactNumber
                );

        email =
                validateRequiredEmail(
                        email
                );

        Optional<Patient> existingPatient =
                patientDAO.findByNameAndContact(
                        patientName,
                        contactNumber
                );

        if (existingPatient.isPresent()) {

            Patient patient =
                    new Patient(
                            existingPatient
                                    .get()
                                    .getPatientId(),
                            patientName,
                            address,
                            contactNumber,
                            email
                    );

            patientDAO.updatePatient(
                    patient
            );

            return patient;
        }

        Patient patient =
                new Patient(
                        0,
                        patientName,
                        address,
                        contactNumber,
                        email
                );

        int patientId =
                patientDAO.savePatientAndReturnId(
                        patient
                );

        return new Patient(
                patientId,
                patientName,
                address,
                contactNumber,
                email
        );
    }

    public List<Patient> searchByContactNumber(
            String contactNumber)
            throws SQLException {

        if (contactNumber == null
                || !contactNumber.trim()
                .matches("0\\d{9}")) {

            throw new IllegalArgumentException(
                    "Invalid contact number"
            );
        }

        return patientDAO
                .findByContactNumber(
                        contactNumber.trim()
                );
    }

    public Optional<Patient> getPatient(
            int patientId)
            throws SQLException {

        return patientDAO.findById(
                patientId
        );
    }

    public boolean updatePatient(
            int patientId,
            String patientName,
            String address,
            String contactNumber,
            String email)
            throws SQLException {

        if (patientName == null
                || patientName.isBlank()
                || address == null
                || address.isBlank()
                || contactNumber == null
                || contactNumber.isBlank()) {

            throw new IllegalArgumentException(
                    "Please complete all required patient fields"
            );
        }

        patientName =
                patientName.trim();

        address =
                address.trim();

        contactNumber =
                contactNumber.trim();

        if (!contactNumber.matches(
                "0\\d{9}"
        )) {

            throw new IllegalArgumentException(
                    "Invalid contact number"
            );
        }

        if (email != null) {

            email =
                    email.trim();

            if (email.isBlank()) {

                email =
                        null;

            } else if (!email.matches(
                    "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$"
            )) {

                throw new IllegalArgumentException(
                        "Invalid email address"
                );
            }
        }

        Patient patient =
                new Patient(
                        patientId,
                        patientName,
                        address,
                        contactNumber,
                        email
                );

        return patientDAO.updatePatient(
                patient
        );
    }

    private String validatePatientName(
            String patientName) {

        if (patientName == null
                || patientName.isBlank()) {

            throw new IllegalArgumentException(
                    "Patient name is required"
            );
        }

        return patientName.trim();
    }

    private String validateAddress(
            String address) {

        if (address == null
                || address.isBlank()) {

            throw new IllegalArgumentException(
                    "Address is required"
            );
        }

        return address.trim();
    }

    private String validateContactNumber(
            String contactNumber) {

        if (contactNumber == null
                || !contactNumber.trim()
                .matches("0\\d{9}")) {

            throw new IllegalArgumentException(
                    "Invalid contact number"
            );
        }

        return contactNumber.trim();
    }

    private String validateEmail(
            String email) {

        if (email != null) {

            email =
                    email.trim();

            if (email.isBlank()) {

                email =
                        null;

            } else if (!email.matches(
                    "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$"
            )) {

                throw new IllegalArgumentException(
                        "Invalid email address"
                );
            }
        }

        return email;
    }

    private String validateRequiredEmail(
            String email) {

        if (email == null
                || email.isBlank()) {

            throw new IllegalArgumentException(
                    "Invalid email address"
            );
        }

        return validateEmail(
                email
        );
    }
}
