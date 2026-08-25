package com.sunrisedental.controller;

import com.sunrisedental.service.AppointmentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AppointmentApiController extends HttpServlet {

    private AppointmentService appointmentService;

    public AppointmentApiController(
            AppointmentService appointmentService) {

        this.appointmentService =
                appointmentService;
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

    }
}