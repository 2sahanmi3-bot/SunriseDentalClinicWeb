<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt"
           uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <title>
        Sunrise Dental Clinic - Receipt
    </title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

<div class="page-container">

    <header class="top-bar no-print">

        <div>
            <h1>Sunrise Dental Clinic</h1>
            <p>Patient Receipt</p>
        </div>

        <nav class="main-nav">

            <a href="${pageContext.request.contextPath}/appointment">
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

    <main class="receipt-card">

        <div class="receipt-header">

            <h2>Sunrise Dental Clinic</h2>

            <p>
                Patient Billing Receipt
            </p>

        </div>

        <div class="receipt-section">

            <div class="receipt-row">

                <span>Appointment Number</span>

                <strong>
                    ${bill.appointmentNumber}
                </strong>

            </div>

            <div class="receipt-row">

                <span>Patient Name</span>

                <strong>
                    ${bill.patientName}
                </strong>

            </div>

            <div class="receipt-row">

                <span>Treatment</span>

                <strong>
                    ${bill.treatmentType}
                </strong>

            </div>

        </div>

        <div class="receipt-section">

            <div class="receipt-row">

                <span>Treatment Charge</span>

                <strong>
                    Rs.
                    <fmt:formatNumber
                            value="${bill.treatmentCharge}"
                            minFractionDigits="2"
                            maxFractionDigits="2"/>
                </strong>

            </div>

            <div class="receipt-row">

                <span>Consultation Fee</span>

                <strong>
                    Rs.
                    <fmt:formatNumber
                            value="${bill.consultationFee}"
                            minFractionDigits="2"
                            maxFractionDigits="2"/>
                </strong>

            </div>

        </div>

        <div class="receipt-total">

            <span>Total Amount</span>

            <strong>
                Rs.
                <fmt:formatNumber
                        value="${bill.totalAmount}"
                        minFractionDigits="2"
                        maxFractionDigits="2"/>
            </strong>

        </div>

        <div class="receipt-footer">

            <p>
                Thank you for visiting Sunrise Dental Clinic.
            </p>

        </div>

        <div class="receipt-actions no-print">

            <button type="button"
                    class="primary-button"
                    onclick="window.print()">

                Print Receipt

            </button>

            <a href="${pageContext.request.contextPath}/billing"
               class="secondary-button">

                Back to Billing

            </a>

        </div>

    </main>

</div>

</body>
</html>