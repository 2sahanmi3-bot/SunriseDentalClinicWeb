package com.sunrisedental.service;

import com.sunrisedental.dao.TreatmentDAO;
import com.sunrisedental.model.Treatment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TreatmentServiceTest {

    private TreatmentDAO treatmentDAO;
    private TreatmentService treatmentService;

    @BeforeEach
    void setUp() {

        treatmentDAO =
                mock(TreatmentDAO.class);

        treatmentService =
                new TreatmentService(
                        treatmentDAO
                );
    }

    @Test
    void shouldFindTreatmentByName()
            throws Exception {

        Treatment treatment =
                new Treatment(
                        1,
                        "Cleaning",
                        5000.00,
                        1500.00
                );

        when(
                treatmentDAO.findByTreatmentName(
                        "Cleaning"
                )
        ).thenReturn(
                Optional.of(treatment)
        );

        Optional<Treatment> result =
                treatmentService.findByTreatmentName(
                        "Cleaning"
                );

        assertTrue(
                result.isPresent()
        );

        assertEquals(
                "Cleaning",
                result.get().getTreatmentName()
        );

        assertEquals(
                5000.00,
                result.get().getTreatmentCharge()
        );

        assertEquals(
                1500.00,
                result.get().getConsultationFee()
        );

        verify(treatmentDAO)
                .findByTreatmentName(
                        "Cleaning"
                );
    }
}

