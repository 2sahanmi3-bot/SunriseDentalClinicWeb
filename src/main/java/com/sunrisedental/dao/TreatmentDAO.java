package com.sunrisedental.dao;

import com.sunrisedental.model.Treatment;

import java.sql.SQLException;
import java.util.Optional;

public class TreatmentDAO {

    public Optional<Treatment> findByTreatmentName(
            String treatmentName)
            throws SQLException {

        throw new UnsupportedOperationException(
                "Not implemented"
        );
    }
}