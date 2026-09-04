<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <title>
        Sunrise Dental Clinic - Register Appointment
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

        <div class="page-header">

            <h1>Appointment Management</h1>

            <p>
                Register and manage patient appointments.
            </p>

        </div>

        <div class="search-section">

            <h2>Search Appointment</h2>

            <p class="page-description">
                Enter the appointment number to view the appointment details.
            </p>

            <form method="get"
                  action="${pageContext.request.contextPath}/appointment"
                  class="search-form">

                <input type="hidden"
                       name="action"
                       value="search">

                <div class="form-group search-input">

                    <label for="searchAppointmentNumber">
                        Appointment Number
                    </label>

                    <input type="text"
                           id="searchAppointmentNumber"
                           name="appointmentNumber"
                           maxlength="20"
                           placeholder="e.g. APT001"
                           required>

                </div>

                <button type="submit"
                        class="primary-button">

                    Search Appointment

                </button>

            </form>

        </div>

        <hr class="section-divider">

        <h2>
            Register New Appointment
        </h2>

        <p class="page-description">
            Enter the patient and appointment details below.
        </p>

        <c:if test="${not empty errorMessage}">

        <div class="error-message">
            <c:out value="${errorMessage}"/>
        </div>

        </c:if>

        <form method="post"
              action="${pageContext.request.contextPath}/appointment">

            <input type="hidden"
                   name="action"
                   value="register">

            <c:if test="${not empty selectedPatient}">

                <input type="hidden"
                       name="patientId"
                       value="${selectedPatient.patientId}">

            </c:if>

            <div class="form-grid">

                <div class="form-group">

                    <label for="appointmentNumber">
                        Appointment Number
                    </label>

                    <input type="text"
                           id="appointmentNumber"
                           name="appointmentNumber"
                           maxlength="20"
                           placeholder="e.g. APT001"
                           required>

                </div>

                <div class="form-group">

                    <label for="patientName">
                        Patient Name
                    </label>

                    <input type="text"
                           id="patientName"
                           name="patientName"
                           maxlength="100"
                           value="<c:out value='${selectedPatient.patientName}'/>"
                           required>

                </div>

                <div class="form-group full-width">

                    <label for="address">
                        Address
                    </label>

                    <input type="text"
                           id="address"
                           name="address"
                           maxlength="255"
                           value="<c:out value='${selectedPatient.address}'/>"
                           required>

                </div>

                <div class="form-group">

                    <label for="contactNumber">
                        Contact Number
                    </label>

                    <input type="text"
                           id="contactNumber"
                           name="contactNumber"
                           placeholder="0771234567"
                           maxlength="10"
                           pattern="0[0-9]{9}"
                           inputmode="numeric"
                           title="Enter a 10-digit contact number starting with 0"
                           value="<c:out value='${selectedPatient.contactNumber}'/>"
                           required>

                </div>

                <div class="form-group">

                    <label for="email">
                        Email
                    </label>

                    <input type="email"
                           id="email"
                           name="email"
                           maxlength="100"
                           value="<c:out value='${selectedPatient.email}'/>"
                           required>

                </div>

                <div class="form-group">

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

                                <c:if test="${not empty dentist.specialization}">
                                    -
                                    <c:out value="${dentist.specialization}"/>
                                </c:if>

                            </option>

                        </c:forEach>

                    </select>

                </div>

                <div class="form-group">

                    <label for="treatmentType">
                        Treatment Type
                    </label>

                    <select id="treatmentType"
                            name="treatmentType"
                            required>

                        <option value="">
                            Select a treatment
                        </option>

                        <c:forEach var="treatment"
                                   items="${treatments}">

                            <option value="${treatment.treatmentName}">
                                <c:out value="${treatment.treatmentName}"/>
                            </option>

                        </c:forEach>

                    </select>

                </div>

                <div class="form-group">

                    <label for="appointmentDate">
                        Appointment Date
                    </label>

                    <input type="date"
                           id="appointmentDate"
                           name="appointmentDate"
                           required>

                </div>

                <div class="form-group">

                    <label for="appointmentTime">
                        Appointment Time
                    </label>

                    <input type="time"
                           id="appointmentTime"
                           name="appointmentTime"
                           required>

                </div>

            </div>

            <div class="form-actions">

                <button type="submit"
                        class="primary-button">

                    Register Appointment

                </button>

            </div>

        </form>

    </main>

</div>

</div>

</div>

</body>
</html>
