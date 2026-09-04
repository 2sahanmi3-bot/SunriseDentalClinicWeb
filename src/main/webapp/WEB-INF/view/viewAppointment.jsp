<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

<c:set var="activePage"
       value="appointments"/>

<div class="app-layout">

<jsp:include page="/WEB-INF/view/includes/sidebar.jsp"/>

<div class="app-main">

<div class="page-container">

    <header class="top-bar">

        <div>
            <h1>Sunrise Dental Clinic</h1>

            <p>
                Staff Appointment Management
            </p>
        </div>

        <nav class="main-nav">

            <a href="${pageContext.request.contextPath}/dashboard">
                Dashboard
            </a>

            <a href="${pageContext.request.contextPath}/appointment"
               class="active-nav"
               aria-current="page">
                Appointments
            </a>

            <a href="${pageContext.request.contextPath}/patient">
                Patients
            </a>

            <a href="${pageContext.request.contextPath}/billing">
                Billing
            </a>

            <a href="${pageContext.request.contextPath}/reports">
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

    </header>

    <main class="content-card">

        <h2>
            Appointment Details
        </h2>

        <c:if test="${not empty errorMessage}">

        <div class="error-message">
            <c:out value="${errorMessage}"/>
        </div>

        </c:if>

        <c:if test="${not empty successMessage}">

            <div class="success-message">
                <c:out value="${successMessage}"/>
            </div>

        </c:if>

        <c:if test="${not empty appointment}">

        <div class="appointment-details">

            <p>
                <strong>Appointment Number:</strong>
                <c:out value="${appointment.appointmentNumber}"/>
            </p>

            <p>
                <strong>Patient Name:</strong>
                <c:out value="${appointment.patientName}"/>
            </p>

            <p>
                <strong>Address:</strong>
                <c:out value="${appointment.address}"/>
            </p>

            <p>
                <strong>Contact Number:</strong>
                <c:out value="${appointment.contactNumber}"/>
            </p>

            <p>
                <strong>Email:</strong>
                <c:out value="${appointmentPatient.email}"/>
            </p>

            <p>
                <strong>Dentist:</strong>
                <c:out value="${appointment.dentistName}"/>
            </p>

            <p>
                <strong>Treatment Type:</strong>
                <c:out value="${appointment.treatmentType}"/>
            </p>

            <p>
                <strong>Appointment Date:</strong>
                <c:out value="${appointment.appointmentDate}"/>
            </p>

            <p>
                <strong>Appointment Time:</strong>
                <c:out value="${appointment.appointmentTime}"/>
            </p>

            <p>
                <strong>Status:</strong>
                <c:out value="${appointment.status}"/>
            </p>

        </div>

        </c:if>

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

</div>

</div>

</body>
</html>
