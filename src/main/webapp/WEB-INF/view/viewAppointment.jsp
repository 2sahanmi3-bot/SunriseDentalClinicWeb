<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.sunrisedental.model.Appointment" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <title>
        Sunrise Dental Clinic - Appointment Details
    </title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

<div class="page-container">

    <header class="top-bar">

        <div>
            <h1>Sunrise Dental Clinic</h1>

            <p>
                Staff Appointment Management
            </p>
        </div>

        <nav class="main-nav">

            <a href="${pageContext.request.contextPath}/appointment"
               class="active-nav"
               aria-current="page">
                Appointments
            </a>

            <a href="${pageContext.request.contextPath}/billing">
                Billing
            </a>

            <a href="${pageContext.request.contextPath}/help">
                Help
            </a>

            <a href="${pageContext.request.contextPath}/auth?action=logout">
                Logout
            </a>

        </nav>

    </header>

    <main class="content-card">

        <h2>
            Appointment Details
        </h2>

        <%
            String errorMessage =
                    (String) request.getAttribute(
                            "errorMessage"
                    );

            Appointment appointment =
                    (Appointment) request.getAttribute(
                            "appointment"
                    );
        %>

        <% if (errorMessage != null) { %>

        <div class="error-message">
            <%= errorMessage %>
        </div>

        <% } %>

        <% if (appointment != null) { %>

        <div class="appointment-details">

            <p>
                <strong>Appointment Number:</strong>
                <%= appointment.getAppointmentNumber() %>
            </p>

            <p>
                <strong>Patient Name:</strong>
                <%= appointment.getPatientName() %>
            </p>

            <p>
                <strong>Address:</strong>
                <%= appointment.getAddress() %>
            </p>

            <p>
                <strong>Contact Number:</strong>
                <%= appointment.getContactNumber() %>
            </p>

            <p>
                <strong>Dentist:</strong>
                <%= appointment.getDentistName() %>
            </p>

            <p>
                <strong>Treatment Type:</strong>
                <%= appointment.getTreatmentType() %>
            </p>

            <p>
                <strong>Appointment Date:</strong>
                <%= appointment.getAppointmentDate() %>
            </p>

            <p>
                <strong>Appointment Time:</strong>
                <%= appointment.getAppointmentTime() %>
            </p>

        </div>

        <% } %>

        <div class="form-actions">

            <a href="${pageContext.request.contextPath}/appointment"
               class="primary-button">

                Register Another Appointment

            </a>

        </div>

    </main>

</div>

</body>
</html>