<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>Reports - Sunrise Dental Clinic</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

    <style>
        @media print {
            header,
            .report-options,
            .print-button {
                display: none;
            }
        }
    </style>

</head>

<body>

<header class="top-bar">

    <div class="container">

        <h1>Sunrise Dental Clinic</h1>

        <nav>

            <a href="${pageContext.request.contextPath}/dashboard">
                Dashboard
            </a>

            <a href="${pageContext.request.contextPath}/patient">
                Patients
            </a>

            <a href="${pageContext.request.contextPath}/appointment">
                Appointments
            </a>

            <a href="${pageContext.request.contextPath}/billing">
                Billing
            </a>

            <a href="${pageContext.request.contextPath}/reports"
               class="active">
                Reports
            </a>

            <c:if test="${sessionScope.staffRole == 'ADMIN'}">

                <a href="${pageContext.request.contextPath}/admin/staff">
                    User Management
                </a>

                <a href="${pageContext.request.contextPath}/admin/dentists">
                    Dentist Management
                </a>

                <a href="${pageContext.request.contextPath}/admin/treatments">
                    Treatment Management
                </a>

            </c:if>

            <a href="${pageContext.request.contextPath}/help">
                Help
            </a>

            <a href="${pageContext.request.contextPath}/auth?action=logout">
                Logout
            </a>

        </nav>

    </div>

</header>


<main class="container">

    <h2>Reports</h2>


    <c:if test="${not empty errorMessage}">

        <div class="error-message">
            <c:out value="${errorMessage}"/>
        </div>

    </c:if>


    <section class="card report-options">

        <h3>Daily Appointment Report</h3>

        <form method="get"
              action="${pageContext.request.contextPath}/reports">

            <input type="hidden"
                   name="action"
                   value="daily">

            <label for="dailyDate">
                Date
            </label>

            <input type="date"
                   id="dailyDate"
                   name="date"
                   required>

            <button type="submit">
                View Report
            </button>

        </form>

    </section>


    <section class="card report-options">

        <h3>Dentist Schedule Report</h3>

        <form method="get"
              action="${pageContext.request.contextPath}/reports">

            <input type="hidden"
                   name="action"
                   value="dentist">

            <label for="dentistId">
                Dentist
            </label>

            <select id="dentistId"
                    name="dentistId"
                    required>

                <option value="">
                    Select a dentist
                </option>

                <c:forEach var="dentist"
                           items="${dentists}">

                    <option value="${dentist.dentistId}">
                        <c:out value="${dentist.dentistName}"/>
                    </option>

                </c:forEach>

            </select>

            <label for="dentistDate">
                Date
            </label>

            <input type="date"
                   id="dentistDate"
                   name="date"
                   required>

            <button type="submit">
                View Schedule
            </button>

        </form>

    </section>


    <section class="card report-options">

        <h3>Treatment Summary Report</h3>

        <a href="${pageContext.request.contextPath}/reports?action=treatments">
            View Treatment Summary
        </a>

    </section>


    <c:if test="${not empty reportTitle}">

        <section class="card">

            <h2>
                <c:out value="${reportTitle}"/>
            </h2>


            <c:if test="${not empty selectedDate}">

                <p>
                    <strong>Date:</strong>
                    <c:out value="${selectedDate}"/>
                </p>

            </c:if>


            <c:if test="${not empty appointments}">

                <table>

                    <thead>

                    <tr>
                        <th>Time</th>
                        <th>Appointment</th>
                        <th>Patient</th>
                        <th>Dentist</th>
                        <th>Treatment</th>
                        <th>Status</th>
                    </tr>

                    </thead>

                    <tbody>

                    <c:forEach var="appointment"
                               items="${appointments}">

                        <tr>

                            <td>
                                <c:out value="${appointment.appointmentTime}"/>
                            </td>

                            <td>
                                <c:out value="${appointment.appointmentNumber}"/>
                            </td>

                            <td>
                                <c:out value="${appointment.patientName}"/>
                            </td>

                            <td>
                                <c:out value="${appointment.dentistName}"/>
                            </td>

                            <td>
                                <c:out value="${appointment.treatmentType}"/>
                            </td>

                            <td>
                                <c:out value="${appointment.status}"/>
                            </td>

                        </tr>

                    </c:forEach>

                    </tbody>

                </table>

            </c:if>


            <c:if test="${not empty treatmentSummary}">

                <table>

                    <thead>

                    <tr>
                        <th>Treatment</th>
                        <th>Total Appointments</th>
                        <th>Completed</th>
                        <th>Cancelled</th>
                    </tr>

                    </thead>

                    <tbody>

                    <c:forEach var="row"
                               items="${treatmentSummary}">

                        <tr>

                            <td>
                                <c:out value="${row.treatmentType}"/>
                            </td>

                            <td>
                                <c:out value="${row.totalAppointments}"/>
                            </td>

                            <td>
                                <c:out value="${row.completedAppointments}"/>
                            </td>

                            <td>
                                <c:out value="${row.cancelledAppointments}"/>
                            </td>

                        </tr>

                    </c:forEach>

                    </tbody>

                </table>

            </c:if>


            <c:if test="${empty appointments && empty treatmentSummary}">

                <p>
                    No report data found.
                </p>

            </c:if>


            <button type="button"
                    class="print-button"
                    onclick="window.print();">

                Print Report

            </button>

        </section>

    </c:if>

</main>

</body>

</html>
