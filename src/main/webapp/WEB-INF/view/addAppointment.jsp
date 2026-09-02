<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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

<div class="page-container">

    <header class="top-bar">

        <div>
            <h1>Sunrise Dental Clinic</h1>

            <p>
                Staff Appointment Management
            </p>
        </div>

        <nav class="main-nav">

            <a href="${pageContext.request.contextPath}/appointment">
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
            Register New Appointment
        </h2>

        <p class="page-description">
            Enter the patient and appointment details below.
        </p>

        <%-- Show validation errors returned by the controller. --%>
        <%
            String errorMessage =
                    (String) request.getAttribute(
                            "errorMessage"
                    );

            if (errorMessage != null) {
        %>

        <div class="error-message">
            <%= errorMessage %>
        </div>

        <%
            }
        %>

        <form method="post"
              action="${pageContext.request.contextPath}/appointment">

            <input type="hidden"
                   name="action"
                   value="register">

            <div class="form-grid">

                <div class="form-group">

                    <label for="appointmentNumber">
                        Appointment Number
                    </label>

                    <input type="text"
                           id="appointmentNumber"
                           name="appointmentNumber"
                           required>

                </div>

                <div class="form-group">

                    <label for="patientName">
                        Patient Name
                    </label>

                    <input type="text"
                           id="patientName"
                           name="patientName"
                           required>

                </div>

                <div class="form-group full-width">

                    <label for="address">
                        Address
                    </label>

                    <input type="text"
                           id="address"
                           name="address"
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
                           required>

                </div>

                <div class="form-group">

                    <label for="dentistName">
                        Dentist
                    </label>

                    <input type="text"
                           id="dentistName"
                           name="dentistName"
                           required>

                </div>

                <div class="form-group">

                    <label for="treatmentType">
                        Treatment Type
                    </label>

                    <select id="treatmentType"
                            name="treatmentType"
                            required>

                        <option value="">
                            Select Treatment
                        </option>

                        <option value="Cleaning">
                            Cleaning
                        </option>

                        <option value="Filling">
                            Filling
                        </option>

                        <option value="Extraction">
                            Extraction
                        </option>

                        <option value="Root Canal">
                            Root Canal
                        </option>

                        <option value="Crown">
                            Crown
                        </option>

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

</body>
</html>