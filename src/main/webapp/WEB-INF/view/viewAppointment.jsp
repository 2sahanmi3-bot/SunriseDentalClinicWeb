<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.sunrisedental.model.Appointment" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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

            <c:if test="${sessionScope.staffRole == 'ADMIN'}">
                <a href="${pageContext.request.contextPath}/admin/staff">
                    Staff Management
                </a>
            </c:if>

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

        <c:if test="${not empty successMessage}">

            <div class="success-message">
                <c:out value="${successMessage}"/>
            </div>

        </c:if>

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
                <strong>Email:</strong>
                <c:out value="${appointmentPatient.email}"/>
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

            <p>
                <strong>Status:</strong>
                <c:out value="${appointment.status}"/>
            </p>

        </div>

        <% } %>

        <c:if test="${appointment.status == 'SCHEDULED'}">

            <div class="appointment-actions">

                <form method="post"
                      action="${pageContext.request.contextPath}/appointment">

                    <input type="hidden"
                           name="action"
                           value="updateStatus">

                    <input type="hidden"
                           name="appointmentNumber"
                           value="${appointment.appointmentNumber}">

                    <input type="hidden"
                           name="status"
                           value="COMPLETED">

                    <button type="submit">
                        Mark Completed
                    </button>

                </form>


                <form method="post"
                      action="${pageContext.request.contextPath}/appointment">

                    <input type="hidden"
                           name="action"
                           value="updateStatus">

                    <input type="hidden"
                           name="appointmentNumber"
                           value="${appointment.appointmentNumber}">

                    <input type="hidden"
                           name="status"
                           value="CANCELLED">

                    <button type="submit"
                            onclick="return confirm('Cancel this appointment?');">
                        Cancel Appointment
                    </button>

                </form>

            </div>

        </c:if>

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
