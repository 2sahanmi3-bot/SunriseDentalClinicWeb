package com.sunrisedental.controller;

import com.sunrisedental.dao.UserDAO;
import com.sunrisedental.service.StaffManagementService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/staff")
public class StaffManagementController extends HttpServlet {

    private StaffManagementService staffManagementService;

    public StaffManagementController() {

        this(
                new StaffManagementService(
                        new UserDAO()
                )
        );
    }

    public StaffManagementController(
            StaffManagementService staffManagementService) {

        this.staffManagementService =
                staffManagementService;
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        throw new UnsupportedOperationException(
                "Not implemented"
        );
    }
}