<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>
        Patient Management - Sunrise Dental Clinic
    </title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

</head>

<body>

<header class="top-bar">

    <div class="container">

        <h1>Sunrise Dental Clinic</h1>

        <nav>

            <a href="${pageContext.request.contextPath}/dashboard">
                Dashboard
            </a>

            <a href="${pageContext.request.contextPath}/patient"
               class="active">
                Patients
            </a>

            <a href="${pageContext.request.contextPath}/appointment">
                Appointments
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

    </div>

</header>


<main class="container">

    <h2>Patient Management</h2>


    <c:if test="${not empty successMessage}">

        <div class="success-message">
            <c:out value="${successMessage}"/>
        </div>

    </c:if>


    <c:if test="${not empty errorMessage}">

        <div class="error-message">
            <c:out value="${errorMessage}"/>
        </div>

    </c:if>


    <section class="card">

        <h3>Register Patient</h3>

        <form method="post"
              action="${pageContext.request.contextPath}/patient">

            <div class="form-group">

                <label for="patientName">
                    Patient Name
                </label>

                <input type="text"
                       id="patientName"
                       name="patientName"
                       maxlength="100"
                       required>

            </div>


            <div class="form-group">

                <label for="address">
                    Address
                </label>

                <textarea id="address"
                          name="address"
                          maxlength="255"
                          required></textarea>

            </div>


            <div class="form-group">

                <label for="contactNumber">
                    Contact Number
                </label>

                <input type="text"
                       id="contactNumber"
                       name="contactNumber"
                       maxlength="10"
                       pattern="0[0-9]{9}"
                       inputmode="numeric"
                       required>

            </div>


            <div class="form-group">

                <label for="email">
                    Email
                </label>

                <input type="email"
                       id="email"
                       name="email"
                       maxlength="100">

            </div>


            <button type="submit">
                Register Patient
            </button>

        </form>

    </section>


    <section class="card">

        <h3>Search Patient</h3>

        <form method="get"
              action="${pageContext.request.contextPath}/patient">

            <input type="hidden"
                   name="action"
                   value="search">

            <div class="form-group">

                <label for="searchContactNumber">
                    Contact Number
                </label>

                <input type="text"
                       id="searchContactNumber"
                       name="contactNumber"
                       maxlength="10"
                       pattern="0[0-9]{9}"
                       inputmode="numeric"
                       required>

            </div>

            <button type="submit">
                Search
            </button>

        </form>

    </section>


    <c:if test="${not empty patients}">

        <section class="card">

            <h3>Search Results</h3>

            <table>

                <thead>

                <tr>
                    <th>Patient ID</th>
                    <th>Name</th>
                    <th>Contact</th>
                    <th>Email</th>
                    <th>Actions</th>
                </tr>

                </thead>

                <tbody>

                <c:forEach var="patient"
                           items="${patients}">

                    <tr>

                        <td>
                            <c:out value="${patient.patientId}"/>
                        </td>

                        <td>
                            <c:out value="${patient.patientName}"/>
                        </td>

                        <td>
                            <c:out value="${patient.contactNumber}"/>
                        </td>

                        <td>
                            <c:out value="${patient.email}"/>
                        </td>

                        <td>

                            <a href="${pageContext.request.contextPath}/patient?action=view&patientId=${patient.patientId}">
                                View
                            </a>

                            <a href="${pageContext.request.contextPath}/patient?action=edit&patientId=${patient.patientId}">
                                Edit
                            </a>

                        </td>

                    </tr>

                </c:forEach>

                </tbody>

            </table>

        </section>

    </c:if>


    <c:if test="${not empty selectedPatient && empty editMode}">

        <section class="card">

            <h3>Patient Details</h3>

            <p>
                <strong>Patient ID:</strong>
                <c:out value="${selectedPatient.patientId}"/>
            </p>

            <p>
                <strong>Name:</strong>
                <c:out value="${selectedPatient.patientName}"/>
            </p>

            <p>
                <strong>Address:</strong>
                <c:out value="${selectedPatient.address}"/>
            </p>

            <p>
                <strong>Contact Number:</strong>
                <c:out value="${selectedPatient.contactNumber}"/>
            </p>

            <p>
                <strong>Email:</strong>
                <c:out value="${selectedPatient.email}"/>
            </p>

            <a href="${pageContext.request.contextPath}/patient?action=edit&patientId=${selectedPatient.patientId}">
                Edit Patient
            </a>

            <a href="${pageContext.request.contextPath}/appointment?patientId=${selectedPatient.patientId}">
                Book Appointment
            </a>


            <h3>Appointment and Treatment History</h3>

            <c:choose>

                <c:when test="${empty appointments}">

                    <p>
                        No appointments found for this patient.
                    </p>

                </c:when>

                <c:otherwise>

                    <table>

                        <thead>

                        <tr>
                            <th>Appointment</th>
                            <th>Treatment</th>
                            <th>Dentist</th>
                            <th>Date</th>
                            <th>Time</th>
                            <th>Status</th>
                            <th>Billing</th>
                        </tr>

                        </thead>

                        <tbody>

                        <c:forEach var="appointment"
                                   items="${appointments}">

                            <tr>

                                <td>
                                    <c:out value="${appointment.appointmentNumber}"/>
                                </td>

                                <td>
                                    <c:out value="${appointment.treatmentType}"/>
                                </td>

                                <td>
                                    <c:out value="${appointment.dentistName}"/>
                                </td>

                                <td>
                                    <c:out value="${appointment.appointmentDate}"/>
                                </td>

                                <td>
                                    <c:out value="${appointment.appointmentTime}"/>
                                </td>

                                <td>
                                    <c:out value="${appointment.status}"/>
                                </td>

                                <td>

                                    <a href="${pageContext.request.contextPath}/billing?appointmentNumber=${appointment.appointmentNumber}">
                                        Bill
                                    </a>

                                </td>

                            </tr>

                        </c:forEach>

                        </tbody>

                    </table>

                </c:otherwise>

            </c:choose>

        </section>

    </c:if>


    <c:if test="${not empty selectedPatient && editMode}">

        <section class="card">

            <h3>Edit Patient</h3>

            <form method="post"
                  action="${pageContext.request.contextPath}/patient">

                <input type="hidden"
                       name="action"
                       value="update">

                <input type="hidden"
                       name="patientId"
                       value="${selectedPatient.patientId}">

                <div class="form-group">

                    <label for="editPatientName">
                        Patient Name
                    </label>

                    <input type="text"
                           id="editPatientName"
                           name="patientName"
                           maxlength="100"
                           value="<c:out value='${selectedPatient.patientName}'/>"
                           required>

                </div>

                <div class="form-group">

                    <label for="editAddress">
                        Address
                    </label>

                    <textarea id="editAddress"
                              name="address"
                              maxlength="255"
                              required><c:out value="${selectedPatient.address}"/></textarea>

                </div>

                <div class="form-group">

                    <label for="editContactNumber">
                        Contact Number
                    </label>

                    <input type="text"
                           id="editContactNumber"
                           name="contactNumber"
                           maxlength="10"
                           pattern="0[0-9]{9}"
                           inputmode="numeric"
                           value="<c:out value='${selectedPatient.contactNumber}'/>"
                           required>

                </div>

                <div class="form-group">

                    <label for="editEmail">
                        Email
                    </label>

                    <input type="email"
                           id="editEmail"
                           name="email"
                           maxlength="100"
                           value="<c:out value='${selectedPatient.email}'/>">

                </div>

                <button type="submit">
                    Save Changes
                </button>

                <a href="${pageContext.request.contextPath}/patient?action=view&patientId=${selectedPatient.patientId}">
                    Cancel
                </a>

            </form>

        </section>

    </c:if>

</main>

</body>
</html>
