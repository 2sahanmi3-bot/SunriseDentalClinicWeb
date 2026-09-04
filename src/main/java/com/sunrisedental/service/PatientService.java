package com.sunrisedental.service;

import com.sunrisedental.dao.PatientDAO;
import com.sunrisedental.model.Patient;

import java.sql.SQLException;
import java.util.List;

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
                email = null;
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
}