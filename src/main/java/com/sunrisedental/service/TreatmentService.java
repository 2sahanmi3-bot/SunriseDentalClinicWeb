package com.sunrisedental.service;

import com.sunrisedental.dao.TreatmentDAO;
import com.sunrisedental.model.Treatment;

import java.sql.SQLException;
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

        throw new UnsupportedOperationException(
                "Not implemented"
        );
    }
}