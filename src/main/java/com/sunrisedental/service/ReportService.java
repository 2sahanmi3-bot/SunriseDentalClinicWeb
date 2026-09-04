package com.sunrisedental.service;

import com.sunrisedental.dao.ReportDAO;
import com.sunrisedental.model.Appointment;
import com.sunrisedental.model.TreatmentReportRow;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import java.util.List;

public class ReportService {

    private ReportDAO reportDAO;

    public ReportService(
            ReportDAO reportDAO) {

        this.reportDAO =
                reportDAO;
    }

    public List<Appointment> getDailyAppointments(
            String date)
            throws SQLException {

        validateDate(
                date
        );

        return reportDAO
                .findAppointmentsByDate(
                        date.trim()
                );
    }

    public List<Appointment> getDentistSchedule(
            int dentistId,
            String date)
            throws SQLException {

        if (dentistId <= 0) {

            throw new IllegalArgumentException(
                    "Please select a dentist"
            );
        }

        validateDate(
                date
        );

        return reportDAO
                .findDentistSchedule(
                        dentistId,
                        date.trim()
                );
    }

    public List<TreatmentReportRow> getTreatmentSummary()
            throws SQLException {

        return reportDAO
                .getTreatmentSummary();
    }

    private void validateDate(
            String date) {

        if (date == null
                || date.isBlank()) {

            throw new IllegalArgumentException(
                    "Please select a date"
            );
        }

        try {

            LocalDate.parse(
                    date.trim()
            );

        } catch (DateTimeParseException e) {

            throw new IllegalArgumentException(
                    "Invalid report date"
            );
        }
    }
}
