package com.sunrisedental.model;

public class TreatmentReportRow {

    private String treatmentType;
    private int totalAppointments;
    private int completedAppointments;
    private int cancelledAppointments;

    public TreatmentReportRow(
            String treatmentType,
            int totalAppointments,
            int completedAppointments,
            int cancelledAppointments) {

        this.treatmentType =
                treatmentType;

        this.totalAppointments =
                totalAppointments;

        this.completedAppointments =
                completedAppointments;

        this.cancelledAppointments =
                cancelledAppointments;
    }

    public String getTreatmentType() {
        return treatmentType;
    }

    public int getTotalAppointments() {
        return totalAppointments;
    }

    public int getCompletedAppointments() {
        return completedAppointments;
    }

    public int getCancelledAppointments() {
        return cancelledAppointments;
    }
}
