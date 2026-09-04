package com.sunrisedental.controller;

import com.sunrisedental.dao.DentistDAO;
import com.sunrisedental.dao.ReportDAO;

import com.sunrisedental.service.DentistService;
import com.sunrisedental.service.ReportService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/reports")
public class ReportController extends HttpServlet {

    private ReportService reportService;
    private DentistService dentistService;

    public ReportController() {

        this(
                new ReportService(
                        new ReportDAO()
                ),
                new DentistService(
                        new DentistDAO()
                )
        );
    }

    public ReportController(
            ReportService reportService,
            DentistService dentistService) {

        this.reportService =
                reportService;

        this.dentistService =
                dentistService;
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action =
                request.getParameter(
                        "action"
                );

        try {

            request.setAttribute(
                    "dentists",
                    dentistService.getAllDentists()
            );

            if ("daily".equals(action)) {

                String date =
                        request.getParameter(
                                "date"
                        );

                request.setAttribute(
                        "reportTitle",
                        "Daily Appointment Report"
                );

                request.setAttribute(
                        "appointments",
                        reportService
                                .getDailyAppointments(
                                        date
                                )
                );

                request.setAttribute(
                        "selectedDate",
                        date
                );

            } else if ("dentist".equals(action)) {

                int dentistId =
                        Integer.parseInt(
                                request.getParameter(
                                        "dentistId"
                                )
                        );

                String date =
                        request.getParameter(
                                "date"
                        );

                request.setAttribute(
                        "reportTitle",
                        "Dentist Schedule Report"
                );

                request.setAttribute(
                        "appointments",
                        reportService
                                .getDentistSchedule(
                                        dentistId,
                                        date
                                )
                );

                request.setAttribute(
                        "selectedDate",
                        date
                );

                request.setAttribute(
                        "selectedDentistId",
                        dentistId
                );

            } else if ("treatments".equals(action)) {

                request.setAttribute(
                        "reportTitle",
                        "Treatment Summary Report"
                );

                request.setAttribute(
                        "treatmentSummary",
                        reportService
                                .getTreatmentSummary()
                );
            }

        } catch (IllegalArgumentException e) {

            request.setAttribute(
                    "errorMessage",
                    e.getMessage()
            );

        } catch (SQLException e) {

            throw new ServletException(e);
        }

        request.getRequestDispatcher(
                "/WEB-INF/view/reports.jsp"
        ).forward(
                request,
                response
        );
    }
}
