package com.sunrisedental.service;

import com.sunrisedental.dao.DashboardDAO;
import com.sunrisedental.model.DashboardStats;

import java.sql.SQLException;

public class DashboardService {

    private DashboardDAO dashboardDAO;

    public DashboardService(
            DashboardDAO dashboardDAO) {

        this.dashboardDAO =
                dashboardDAO;
    }

    public DashboardStats getDashboardStats()
            throws SQLException {

        return dashboardDAO.getDashboardStats();
    }
}
