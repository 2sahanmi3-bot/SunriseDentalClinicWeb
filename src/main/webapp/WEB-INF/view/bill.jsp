<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <title>
        Sunrise Dental Clinic - Billing
    </title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

<div class="page-container">

    <header class="top-bar">

        <div>
            <h1>Sunrise Dental Clinic</h1>
            <p>Staff Billing</p>
        </div>

        <nav class="main-nav">

            <a href="${pageContext.request.contextPath}/dashboard">
                Dashboard
            </a>

            <a href="${pageContext.request.contextPath}/appointment">
                Appointments
            </a>

            <a href="${pageContext.request.contextPath}/patient">
                Patients
            </a>

            <a href="${pageContext.request.contextPath}/billing"
               class="active-nav"
               aria-current="page">
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

    <main class="content-card billing-card">

        <h2>Generate Patient Bill</h2>

        <p class="page-description">
            Enter the appointment number to generate the bill.
            Charges are taken from the stored treatment details.
        </p>

        <c:if test="${not empty errorMessage}">

        <div class="error-message">
            <c:out value="${errorMessage}"/>
        </div>

        </c:if>

        <form method="post"
              action="${pageContext.request.contextPath}/billing">

            <div class="form-group">

                <label for="appointmentNumber">
                    Appointment Number
                </label>

                <input type="text"
                       id="appointmentNumber"
                       name="appointmentNumber"
                       maxlength="20"
                       placeholder="e.g. APT001"
                       value="<c:out value='${param.appointmentNumber}'/>"
                       required>

            </div>

            <div class="billing-note">

                <strong>Billing information</strong>

                <p>
                    Treatment charge and consultation fee
                    are loaded automatically from the
                    treatment stored for the appointment.
                </p>

            </div>

            <div class="form-actions">

                <button type="submit"
                        class="primary-button">

                    Generate Bill

                </button>

            </div>

        </form>

    </main>

</div>

</body>
</html>
