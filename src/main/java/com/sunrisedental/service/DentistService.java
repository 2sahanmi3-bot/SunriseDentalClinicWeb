package com.sunrisedental.service;

import com.sunrisedental.dao.DentistDAO;
import com.sunrisedental.model.Dentist;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DentistService {

    private DentistDAO dentistDAO;

    public DentistService(
            DentistDAO dentistDAO) {

        this.dentistDAO =
                dentistDAO;
    }

    public boolean addDentist(
            String dentistName,
            String specialization,
            String contactNumber)
            throws SQLException {

        if (dentistName == null
                || dentistName.isBlank()) {

            throw new IllegalArgumentException(
                    "Dentist name is required"
            );
        }

        dentistName =
                dentistName.trim();

        if (specialization != null) {

            specialization =
                    specialization.trim();

            if (specialization.isBlank()) {
                specialization = null;
            }
        }

        if (contactNumber != null) {

            contactNumber =
                    contactNumber.trim();

            if (contactNumber.isBlank()) {

                contactNumber = null;

            } else if (!contactNumber.matches(
                    "0\\d{9}"
            )) {

                throw new IllegalArgumentException(
                        "Invalid contact number"
                );
            }
        }

        Dentist dentist =
                new Dentist(
                        0,
                        dentistName,
                        specialization,
                        contactNumber,
                        true
                );

        return dentistDAO.saveDentist(
                dentist
        );
    }

    public List<Dentist> getAllDentists()
            throws SQLException {

        return dentistDAO
                .findAllDentists();
    }

    public List<Dentist> getActiveDentists()
            throws SQLException {

        return dentistDAO
                .findActiveDentists();
    }

    public Optional<Dentist> getDentist(
            int dentistId)
            throws SQLException {

        return dentistDAO.findById(
                dentistId
        );
    }

    public boolean updateDentist(
            int dentistId,
            String dentistName,
            String specialization,
            String contactNumber)
            throws SQLException {

        if (dentistName == null
                || dentistName.isBlank()) {

            throw new IllegalArgumentException(
                    "Dentist name is required"
            );
        }

        dentistName =
                dentistName.trim();

        if (specialization != null) {

            specialization =
                    specialization.trim();

            if (specialization.isBlank()) {
                specialization = null;
            }
        }

        if (contactNumber != null) {

            contactNumber =
                    contactNumber.trim();

            if (contactNumber.isBlank()) {

                contactNumber = null;

            } else if (!contactNumber.matches(
                    "0\\d{9}"
            )) {

                throw new IllegalArgumentException(
                        "Invalid contact number"
                );
            }
        }

        Optional<Dentist> existingDentist =
                dentistDAO.findById(
                        dentistId
                );

        if (existingDentist.isEmpty()) {

            throw new IllegalArgumentException(
                    "Dentist not found"
            );
        }

        Dentist dentist =
                new Dentist(
                        dentistId,
                        dentistName,
                        specialization,
                        contactNumber,
                        existingDentist
                                .get()
                                .isActive()
                );

        return dentistDAO.updateDentist(
                dentist
        );
    }

    public boolean changeDentistStatus(
            int dentistId,
            boolean active)
            throws SQLException {

        return dentistDAO
                .updateDentistStatus(
                        dentistId,
                        active
                );
    }
}