package com.sunrisedental.service;

import com.sunrisedental.model.Appointment;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailService {

    public boolean sendAppointmentConfirmation(
            String recipient,
            Appointment appointment) {

        String subject =
                "Sunrise Dental Clinic - Appointment Confirmation";

        String body =
                "Your appointment has been confirmed.\n\n"
                        + "Appointment Number: "
                        + appointment.getAppointmentNumber()
                        + "\nTreatment: "
                        + appointment.getTreatmentType()
                        + "\nDentist: "
                        + appointment.getDentistName()
                        + "\nDate: "
                        + appointment.getAppointmentDate()
                        + "\nTime: "
                        + appointment.getAppointmentTime()
                        + "\n\nSunrise Dental Clinic";

        return sendEmail(
                recipient,
                subject,
                body
        );
    }

    public boolean sendCancellationEmail(
            String recipient,
            Appointment appointment) {

        String subject =
                "Sunrise Dental Clinic - Appointment Cancelled";

        String body =
                "Your appointment has been cancelled.\n\n"
                        + "Appointment Number: "
                        + appointment.getAppointmentNumber()
                        + "\nDentist: "
                        + appointment.getDentistName()
                        + "\nDate: "
                        + appointment.getAppointmentDate()
                        + "\nTime: "
                        + appointment.getAppointmentTime()
                        + "\n\nSunrise Dental Clinic";

        return sendEmail(
                recipient,
                subject,
                body
        );
    }

    private boolean sendEmail(
            String recipient,
            String subject,
            String body) {

        if (recipient == null
                || recipient.isBlank()) {

            return false;
        }

        String host =
                System.getenv("SUNRISE_SMTP_HOST");

        String port =
                System.getenv("SUNRISE_SMTP_PORT");

        String username =
                System.getenv("SUNRISE_SMTP_USERNAME");

        String password =
                System.getenv("SUNRISE_SMTP_PASSWORD");

        String from =
                System.getenv("SUNRISE_SMTP_FROM");

        if (host == null
                || port == null
                || username == null
                || password == null
                || from == null) {

            return false;
        }

        Properties properties =
                new Properties();

        properties.put(
                "mail.smtp.host",
                host
        );

        properties.put(
                "mail.smtp.port",
                port
        );

        properties.put(
                "mail.smtp.starttls.enable",
                "true"
        );

        properties.put(
                "mail.smtp.auth",
                "true"
        );

        Session session =
                Session.getInstance(
                        properties,
                        new Authenticator() {

                            @Override
                            protected PasswordAuthentication
                            getPasswordAuthentication() {

                                return new PasswordAuthentication(
                                        username,
                                        password
                                );
                            }
                        }
                );

        try {

            Message message =
                    new MimeMessage(
                            session
                    );

            message.setFrom(
                    new InternetAddress(
                            from
                    )
            );

            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(
                            recipient
                    )
            );

            message.setSubject(
                    subject
            );

            message.setText(
                    body
            );

            Transport.send(
                    message
            );

            return true;

        } catch (MessagingException e) {

            return false;
        }
    }
}
