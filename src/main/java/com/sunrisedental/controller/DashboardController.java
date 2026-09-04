package com.sunrisedental.controller;

import com.sunrisedental.dao.DashboardDAO;
import com.sunrisedental.service.DashboardService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/dashboard")
public class DashboardController extends HttpServlet {

    private DashboardService dashboardService;

    public DashboardController() {

        this(
                new DashboardService(
                        new DashboardDAO()
                )
        );
    }

    public DashboardController(
            DashboardService dashboardService) {

        this.dashboardService =
                dashboardService;
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            request.setAttribute(
                    "stats",
                    dashboardService.getDashboardStats()
            );

            request.getRequestDispatcher(
                    "/WEB-INF/view/dashboard.jsp"
            ).forward(
                    request,
                    response
            );

        } catch (SQLException e) {

            throw new ServletException(e);
        }
    }
}
