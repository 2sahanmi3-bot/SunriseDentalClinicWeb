package com.sunrisedental.controller;

import com.sunrisedental.service.AppointmentService;
import com.sunrisedental.service.BillingService;
import com.sunrisedental.model.Appointment;
import com.sunrisedental.model.Bill;

import java.sql.SQLException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class BillingController extends HttpServlet {

    private AppointmentService appointmentService;
    private BillingService billingService;

    public BillingController(
            AppointmentService appointmentService,
            BillingService billingService) {

        this.appointmentService =
                appointmentService;

        this.billingService =
                billingService;
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String appointmentNumber =
                request.getParameter("appointmentNumber");

        double treatmentCharge;
        double consultationFee;

        try {

            treatmentCharge =
                    Double.parseDouble(
                            request.getParameter("treatmentCharge")
                    );

            consultationFee =
                    Double.parseDouble(
                            request.getParameter("consultationFee")
                    );

        } catch (NumberFormatException e) {

            request.setAttribute(
                    "errorMessage",
                    "Please enter valid billing charges"
            );

            RequestDispatcher dispatcher =
                    request.getRequestDispatcher(
                            "bill.jsp"
                    );

            dispatcher.forward(
                    request,
                    response
            );

            return;
        }

        try {

            Optional<Appointment> appointment =
                    appointmentService.findByAppointmentNumber(
                            appointmentNumber
                    );

            if (appointment.isPresent()) {

                Bill bill =
                        billingService.createBill(
                                appointment.get(),
                                treatmentCharge,
                                consultationFee
                        );

                request.setAttribute(
                        "bill",
                        bill
                );

                RequestDispatcher dispatcher =
                        request.getRequestDispatcher(
                                "receipt.jsp"
                        );

                dispatcher.forward(
                        request,
                        response
                );

            } else {

                request.setAttribute(
                        "errorMessage",
                        "Appointment not found"
                );

                RequestDispatcher dispatcher =
                        request.getRequestDispatcher(
                                "bill.jsp"
                        );

                dispatcher.forward(
                        request,
                        response
                );
            }

        } catch (IllegalArgumentException e) {

            request.setAttribute(
                    "errorMessage",
                    e.getMessage()
            );

            RequestDispatcher dispatcher =
                    request.getRequestDispatcher(
                            "bill.jsp"
                    );

            dispatcher.forward(
                    request,
                    response
            );

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action =
                request.getParameter("action");

        if ("receipt".equals(action)) {

            RequestDispatcher dispatcher =
                    request.getRequestDispatcher(
                            "receipt.jsp"
                    );

            dispatcher.forward(
                    request,
                    response
            );
        }
    }
}