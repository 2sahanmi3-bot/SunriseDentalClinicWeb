package com.sunrisedental.service;

import com.sunrisedental.dao.TreatmentDAO;
import com.sunrisedental.model.Treatment;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TreatmentService {

    private TreatmentDAO treatmentDAO;

    public TreatmentService(
            TreatmentDAO treatmentDAO) {

        this.treatmentDAO =
                treatmentDAO;
    }

    public Optional<Treatment> findByTreatmentName(
            String treatmentName)
            throws SQLException {

        return treatmentDAO.findByTreatmentName(
                treatmentName
        );
    }

    public boolean addTreatment(
            String treatmentName,
            String treatmentCharge,
            String consultationFee)
            throws SQLException {

        String normalizedName =
                validateTreatmentName(
                        treatmentName
                );

        double parsedTreatmentCharge =
                parseCharge(
                        treatmentCharge,
                        "Treatment charge cannot be negative"
                );

        double parsedConsultationFee =
                parseCharge(
                        consultationFee,
                        "Consultation fee cannot be negative"
                );

        if (treatmentDAO.findByTreatmentName(
                normalizedName
        ).isPresent()) {

            throw new IllegalArgumentException(
                    "Treatment already exists"
            );
        }

        Treatment treatment =
                new Treatment(
                        0,
                        normalizedName,
                        parsedTreatmentCharge,
                        parsedConsultationFee,
                        true
                );

        return treatmentDAO.saveTreatment(
                treatment
        );
    }

    public List<Treatment> getAllTreatments()
            throws SQLException {

        return treatmentDAO.findAllTreatments();
    }

    public List<Treatment> getActiveTreatments()
            throws SQLException {

        return treatmentDAO.findActiveTreatments();
    }

    public Optional<Treatment> getTreatment(
            int treatmentId)
            throws SQLException {

        return treatmentDAO.findById(
                treatmentId
        );
    }

    public boolean updateTreatment(
            int treatmentId,
            String treatmentName,
            String treatmentCharge,
            String consultationFee)
            throws SQLException {

        if (treatmentId <= 0) {

            throw new IllegalArgumentException(
                    "Treatment not found"
            );
        }

        String normalizedName =
                validateTreatmentName(
                        treatmentName
                );

        double parsedTreatmentCharge =
                parseCharge(
                        treatmentCharge,
                        "Treatment charge cannot be negative"
                );

        double parsedConsultationFee =
                parseCharge(
                        consultationFee,
                        "Consultation fee cannot be negative"
                );

        Optional<Treatment> existingTreatment =
                treatmentDAO.findById(
                        treatmentId
                );

        if (existingTreatment.isEmpty()) {

            throw new IllegalArgumentException(
                    "Treatment not found"
            );
        }

        Optional<Treatment> duplicateTreatment =
                treatmentDAO.findByTreatmentName(
                        normalizedName
                );

        if (duplicateTreatment.isPresent()
                && duplicateTreatment.get().getTreatmentId()
                != treatmentId) {

            throw new IllegalArgumentException(
                    "Treatment already exists"
            );
        }

        Treatment treatment =
                new Treatment(
                        treatmentId,
                        normalizedName,
                        parsedTreatmentCharge,
                        parsedConsultationFee,
                        existingTreatment
                                .get()
                                .isActive()
                );

        return treatmentDAO.updateTreatment(
                treatment
        );
    }

    public boolean changeTreatmentStatus(
            int treatmentId,
            boolean active)
            throws SQLException {

        if (treatmentId <= 0) {

            throw new IllegalArgumentException(
                    "Treatment not found"
            );
        }

        if (treatmentDAO.findById(
                treatmentId
        ).isEmpty()) {

            throw new IllegalArgumentException(
                    "Treatment not found"
            );
        }

        return treatmentDAO.updateTreatmentStatus(
                treatmentId,
                active
        );
    }

    private String validateTreatmentName(
            String treatmentName) {

        if (treatmentName == null
                || treatmentName.isBlank()) {

            throw new IllegalArgumentException(
                    "Treatment name is required"
            );
        }

        return treatmentName.trim();
    }

    private double parseCharge(
            String value,
            String negativeMessage) {

        if (value == null
                || value.isBlank()) {

            throw new IllegalArgumentException(
                    negativeMessage
            );
        }

        double parsedValue;

        try {

            parsedValue =
                    Double.parseDouble(
                            value.trim()
                    );

        } catch (NumberFormatException e) {

            throw new IllegalArgumentException(
                    negativeMessage
            );
        }

        if (parsedValue < 0) {

            throw new IllegalArgumentException(
                    negativeMessage
            );
        }

        return parsedValue;
    }
}
