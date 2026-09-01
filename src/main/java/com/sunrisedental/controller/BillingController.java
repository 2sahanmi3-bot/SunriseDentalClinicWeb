package com.sunrisedental.controller;

import com.sunrisedental.dao.AppointmentDAO;
import com.sunrisedental.dao.TreatmentDAO;
import com.sunrisedental.service.AppointmentService;
import com.sunrisedental.service.BillingService;
import com.sunrisedental.service.TreatmentService;
import com.sunrisedental.model.Appointment;
import com.sunrisedental.model.Treatment;
import com.sunrisedental.model.Bill;

import java.sql.SQLException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/billing")
public class BillingController extends HttpServlet {

    private AppointmentService appointmentService;
    private BillingService billingService;
    private TreatmentService treatmentService;

    public BillingController() {

        this(
                new AppointmentService(
                        new AppointmentDAO()
                ),
                new BillingService(),
                new TreatmentService(
                        new TreatmentDAO()
                )
        );
    }

    public BillingController(
            AppointmentService appointmentService,
            BillingService billingService,
            TreatmentService treatmentService) {

        this.appointmentService =
                appointmentService;

        this.billingService =
                billingService;

        this.treatmentService =
                treatmentService;
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String appointmentNumber =
                request.getParameter("appointmentNumber");

        String treatmentChargeValue =
                request.getParameter(
                        "treatmentCharge"
                );

        String consultationFeeValue =
                request.getParameter(
                        "consultationFee"
                );

        double treatmentCharge = 0;
        double consultationFee = 0;

        // The form may not send charges because billing can use the stored treatment price.
        if (treatmentChargeValue != null
                && consultationFeeValue != null) {

            try {

                treatmentCharge =
                        Double.parseDouble(
                                treatmentChargeValue
                        );

                consultationFee =
                        Double.parseDouble(
                                consultationFeeValue
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
        }

        try {

            Optional<Appointment> appointment =
                    appointmentService.findByAppointmentNumber(
                            appointmentNumber
                    );

            if (appointment.isPresent()) {

                Optional<Treatment> treatment =
                        treatmentService.findByTreatmentName(
                                appointment.get()
                                        .getTreatmentType()
                        );

                double finalTreatmentCharge =
                        treatmentCharge;

                double finalConsultationFee =
                        consultationFee;

                if (treatment.isPresent()) {

                    finalTreatmentCharge =
                            treatment.get()
                                    .getTreatmentCharge();

                    finalConsultationFee =
                            treatment.get()
                                    .getConsultationFee();
                }

                Bill bill =
                        billingService.createBill(
                                appointment.get(),
                                finalTreatmentCharge,
                                finalConsultationFee
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