package com.sunrisedental.controller;

import com.sunrisedental.service.AppointmentService;
import com.sunrisedental.service.BillingService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
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

    }
}