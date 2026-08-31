package com.sunrisedental.dao;

import com.sunrisedental.model.Treatment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TreatmentDAOTest {

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    @BeforeEach
    void setUp() {

        connection =
                mock(Connection.class);

        statement =
                mock(PreparedStatement.class);

        resultSet =
                mock(ResultSet.class);
    }

    @Test
    void shouldFindTreatmentByName()
            throws Exception {

        when(
                connection.prepareStatement(
                        anyString()
                )
        ).thenReturn(
                statement
        );

        when(
                statement.executeQuery()
        ).thenReturn(
                resultSet
        );

        when(
                resultSet.next()
        ).thenReturn(
                true
        );

        when(
                resultSet.getInt(
                        "treatment_id"
                )
        ).thenReturn(
                1
        );

        when(
                resultSet.getString(
                        "treatment_name"
                )
        ).thenReturn(
                "Cleaning"
        );

        when(
                resultSet.getDouble(
                        "treatment_charge"
                )
        ).thenReturn(
                5000.00
        );

        when(
                resultSet.getDouble(
                        "consultation_fee"
                )
        ).thenReturn(
                1500.00
        );

        try (
                MockedStatic<DBConnectionFactory> mocked =
                        mockStatic(
                                DBConnectionFactory.class
                        )
        ) {

            mocked.when(
                    DBConnectionFactory::getConnection
            ).thenReturn(
                    connection
            );

            TreatmentDAO treatmentDAO =
                    new TreatmentDAO();

            Optional<Treatment> result =
                    treatmentDAO.findByTreatmentName(
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

            verify(statement).setString(
                    1,
                    "Cleaning"
            );

            verify(resultSet).close();
            verify(statement).close();
            verify(connection).close();
        }
    }
}