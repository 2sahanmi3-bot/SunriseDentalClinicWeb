package com.sunrisedental.controller;

import com.sunrisedental.model.Appointment;
import com.sunrisedental.model.Dentist;
import com.sunrisedental.model.Patient;
import com.sunrisedental.model.Treatment;

import com.sunrisedental.service.AppointmentService;
import com.sunrisedental.service.DentistService;
import com.sunrisedental.service.PatientService;
import com.sunrisedental.service.TreatmentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

class AppointmentControllerTest {

    private AppointmentService appointmentService;
    private PatientService patientService;
    private DentistService dentistService;
    private TreatmentService treatmentService;

    private AppointmentController controller;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;

    private Dentist dentist;

    @BeforeEach
    void setUp()
            throws Exception {

        appointmentService =
                mock(AppointmentService.class);

        patientService =
                mock(PatientService.class);

        dentistService =
                mock(DentistService.class);

        treatmentService =
                mock(TreatmentService.class);

        request =
                mock(HttpServletRequest.class);

        response =
                mock(HttpServletResponse.class);

        dispatcher =
                mock(RequestDispatcher.class);

        dentist =
                new Dentist(
                        2,
                        "Dr Silva",
                        "General Dentistry",
                        "0770000000",
                        true
                );

        when(
                dentistService.getDentist(
                        2
                )
        ).thenReturn(
                Optional.of(
                        dentist
                )
        );

        when(request.getParameter("treatmentType"))
                .thenReturn("Cleaning");

        when(
                treatmentService.findByTreatmentName(
                        "Cleaning"
                )
        ).thenReturn(
                Optional.of(
                        new Treatment(
                                1,
                                "Cleaning",
                                5000.00,
                                1500.00,
                                true
                        )
                )
        );

        when(
                treatmentService.getActiveTreatments()
        ).thenReturn(
                List.of(
                        new Treatment(
                                1,
                                "Cleaning",
                                5000.00,
                                1500.00
                        )
                )
        );

        when(
                patientService.findOrCreatePatient(
                        any(),
                        any(),
                        any(),
                        any()
                )
        ).thenReturn(
                new Patient(
                        3,
                        "Nimal Perera",
                        "Colombo",
                        "0771234567",
                        "nimal@example.com"
                )
        );

        controller =
                new AppointmentController(
                        appointmentService,
                        patientService,
                        dentistService,
                        treatmentService
                );
    }

    @Test
    void postRegisterShouldRegisterAppointmentAndRedirect()
            throws Exception {

        when(request.getParameter("action"))
                .thenReturn("register");

        when(request.getParameter("appointmentNumber"))
                .thenReturn("APT001");

        when(request.getParameter("dentistId"))
                .thenReturn("2");

        when(appointmentService.registerAppointment(
                any(Appointment.class)))
                .thenReturn(true);

        controller.doPost(
                request,
                response
        );

        verify(appointmentService)
                .registerAppointment(
                        argThat(appointment ->
                                "APT001".equals(
                                        appointment.getAppointmentNumber()
                                )
                                        && Integer.valueOf(2).equals(
                                        appointment.getDentistId()
                                )
                                        && Integer.valueOf(3).equals(
                                        appointment.getPatientId()
                                )
                                        && "Dr Silva".equals(
                                        appointment.getDentistName()
                                )
                        )
                );

        verify(response)
                .sendRedirect(
                        "appointment?action=search&appointmentNumber=APT001"
                );
    }

    @Test
    void postRegisterShouldShowErrorWhenRegistrationFails()
            throws Exception {

        when(request.getParameter("action"))
                .thenReturn("register");

        when(request.getParameter("appointmentNumber"))
                .thenReturn("APT001");

        when(request.getParameter("dentistId"))
                .thenReturn("2");

        when(appointmentService.registerAppointment(
                any(Appointment.class)))
                .thenReturn(false);

        when(request.getRequestDispatcher(
                "WEB-INF/view/addAppointment.jsp"))
                .thenReturn(dispatcher);

        controller.doPost(
                request,
                response
        );

        verify(request)
                .setAttribute(
                        "errorMessage",
                        "Appointment could not be registered"
                );

        verify(dentistService)
                .getActiveDentists();

        verify(treatmentService)
                .getActiveTreatments();

        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }

    @Test
    void getSearchShouldLoadAppointmentAndForwardToDetailsPage()
            throws Exception {

        Appointment appointment =
                new Appointment(
                        1,
                        "APT001"
                );

        when(request.getParameter("action"))
                .thenReturn("search");

        when(request.getParameter("appointmentNumber"))
                .thenReturn("APT001");

        when(appointmentService.findByAppointmentNumber("APT001"))
                .thenReturn(Optional.of(appointment));

        when(request.getRequestDispatcher(
                "WEB-INF/view/viewAppointment.jsp"))
                .thenReturn(dispatcher);

        controller.doGet(
                request,
                response
        );

        verify(appointmentService)
                .findByAppointmentNumber("APT001");

        verify(request)
                .setAttribute(
                        "appointment",
                        appointment
                );

        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }

    @Test
    void getSearchShouldShowMessageWhenAppointmentNotFound()
            throws Exception {

        when(request.getParameter("action"))
                .thenReturn("search");

        when(request.getParameter("appointmentNumber"))
                .thenReturn("APT999");

        when(appointmentService.findByAppointmentNumber("APT999"))
                .thenReturn(Optional.empty());

        when(request.getRequestDispatcher(
                "WEB-INF/view/viewAppointment.jsp"))
                .thenReturn(dispatcher);

        controller.doGet(
                request,
                response
        );

        verify(appointmentService)
                .findByAppointmentNumber("APT999");

        verify(request)
                .setAttribute(
                        "errorMessage",
                        "Appointment not found"
                );

        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }

    @Test
    void postRegisterShouldPassFullAppointmentDetailsToService()
            throws Exception {

        when(request.getParameter("action"))
                .thenReturn("register");

        when(request.getParameter("appointmentNumber"))
                .thenReturn("APT005");

        when(request.getParameter("patientName"))
                .thenReturn("Nimal Perera");

        when(request.getParameter("address"))
                .thenReturn("Colombo");

        when(request.getParameter("contactNumber"))
                .thenReturn("0771234567");

        when(request.getParameter("dentistId"))
                .thenReturn("2");

        when(request.getParameter("treatmentType"))
                .thenReturn("Cleaning");

        when(request.getParameter("appointmentDate"))
                .thenReturn("2026-08-27");

        when(request.getParameter("appointmentTime"))
                .thenReturn("09:30");

        when(appointmentService.registerAppointment(
                any(Appointment.class)))
                .thenReturn(true);

        controller.doPost(
                request,
                response
        );

        verify(appointmentService)
                .registerAppointment(
                        argThat(appointment ->
                                "APT005".equals(
                                        appointment.getAppointmentNumber()
                                )
                                        && "Nimal Perera".equals(
                                        appointment.getPatientName()
                                )
                                        && "Colombo".equals(
                                        appointment.getAddress()
                                )
                                        && "0771234567".equals(
                                        appointment.getContactNumber()
                                )
                                        && Integer.valueOf(3).equals(
                                        appointment.getPatientId()
                                )
                                        && Integer.valueOf(2).equals(
                                        appointment.getDentistId()
                                )
                                        && "Dr Silva".equals(
                                        appointment.getDentistName()
                                )
                                        && "Cleaning".equals(
                                        appointment.getTreatmentType()
                                )
                                        && "2026-08-27".equals(
                                        appointment.getAppointmentDate()
                                )
                                        && "09:30".equals(
                                        appointment.getAppointmentTime()
                                )
                                        && "SCHEDULED".equals(
                                        appointment.getStatus()
                                )
                        )
                );
    }

    @Test
    void postRegisterShouldTrimAppointmentNumber()
            throws Exception {

        when(request.getParameter("action"))
                .thenReturn("register");

        when(request.getParameter("appointmentNumber"))
                .thenReturn("  APT100  ");

        when(request.getParameter("dentistId"))
                .thenReturn("2");

        when(appointmentService.registerAppointment(
                any(Appointment.class)))
                .thenReturn(true);

        controller.doPost(
                request,
                response
        );

        verify(appointmentService)
                .registerAppointment(
                        argThat(appointment ->
                                "APT100".equals(
                                        appointment.getAppointmentNumber()
                                )
                        )
                );

        verify(response)
                .sendRedirect(
                        "appointment?action=search&appointmentNumber=APT100"
                );
    }

    @Test
    void getSearchShouldTrimAppointmentNumber()
            throws Exception {

        Appointment appointment =
                new Appointment(
                        1,
                        "APT100"
                );

        when(request.getParameter("action"))
                .thenReturn("search");

        when(request.getParameter("appointmentNumber"))
                .thenReturn("  APT100  ");

        when(appointmentService.findByAppointmentNumber(
                anyString()))
                .thenReturn(
                        Optional.of(appointment)
                );

        when(request.getRequestDispatcher(
                "WEB-INF/view/viewAppointment.jsp"))
                .thenReturn(dispatcher);

        controller.doGet(
                request,
                response
        );

        verify(appointmentService)
                .findByAppointmentNumber(
                        "APT100"
                );

        verify(request)
                .setAttribute(
                        "appointment",
                        appointment
                );

        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }
}
