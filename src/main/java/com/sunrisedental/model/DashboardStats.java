package com.sunrisedental.model;

public class DashboardStats {

    private int todayAppointments;
    private int totalPatients;
    private int activeDentists;
    private int activeUsers;
    private int upcomingAppointments;

    public DashboardStats(
            int todayAppointments,
            int totalPatients,
            int activeDentists,
            int activeUsers,
            int upcomingAppointments) {

        this.todayAppointments =
                todayAppointments;

        this.totalPatients =
                totalPatients;

        this.activeDentists =
                activeDentists;

        this.activeUsers =
                activeUsers;

        this.upcomingAppointments =
                upcomingAppointments;
    }

    public int getTodayAppointments() {
        return todayAppointments;
    }

    public int getTotalPatients() {
        return totalPatients;
    }

    public int getActiveDentists() {
        return activeDentists;
    }

    public int getActiveUsers() {
        return activeUsers;
    }

    public int getUpcomingAppointments() {
        return upcomingAppointments;
    }
}
