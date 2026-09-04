<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>
        Dentist Management - Sunrise Dental Clinic
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

            <a href="${pageContext.request.contextPath}/patient">
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

            <a href="${pageContext.request.contextPath}/admin/staff">
                User Management
            </a>

            <a href="${pageContext.request.contextPath}/admin/dentists"
               class="active">
                Dentist Management
            </a>

            <a href="${pageContext.request.contextPath}/admin/treatments">
                Treatment Management
            </a>

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

    <h2>Dentist Management</h2>


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


    <c:choose>

        <c:when test="${not empty selectedDentist}">

            <section class="card">

                <h3>Edit Dentist</h3>

                <form method="post"
                      action="${pageContext.request.contextPath}/admin/dentists">

                    <input type="hidden"
                           name="action"
                           value="update">

                    <input type="hidden"
                           name="dentistId"
                           value="${selectedDentist.dentistId}">

                    <div class="form-group">

                        <label for="editDentistName">
                            Dentist Name
                        </label>

                        <input type="text"
                               id="editDentistName"
                               name="dentistName"
                               maxlength="100"
                               value="<c:out value='${selectedDentist.dentistName}'/>"
                               required>

                    </div>


                    <div class="form-group">

                        <label for="editSpecialization">
                            Specialization
                        </label>

                        <input type="text"
                               id="editSpecialization"
                               name="specialization"
                               maxlength="100"
                               value="<c:out value='${selectedDentist.specialization}'/>">

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
                               value="<c:out value='${selectedDentist.contactNumber}'/>">

                    </div>


                    <button type="submit">
                        Save Changes
                    </button>

                    <a href="${pageContext.request.contextPath}/admin/dentists">
                        Cancel
                    </a>

                </form>

            </section>

        </c:when>


        <c:otherwise>

            <section class="card">

                <h3>Add Dentist</h3>

                <form method="post"
                      action="${pageContext.request.contextPath}/admin/dentists">

                    <div class="form-group">

                        <label for="dentistName">
                            Dentist Name
                        </label>

                        <input type="text"
                               id="dentistName"
                               name="dentistName"
                               maxlength="100"
                               required>

                    </div>


                    <div class="form-group">

                        <label for="specialization">
                            Specialization
                        </label>

                        <input type="text"
                               id="specialization"
                               name="specialization"
                               maxlength="100">

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
                               inputmode="numeric">

                    </div>


                    <button type="submit">
                        Add Dentist
                    </button>

                </form>

            </section>

        </c:otherwise>

    </c:choose>


    <section class="card">

        <h3>Dentists</h3>

        <c:choose>

            <c:when test="${empty dentists}">

                <p>
                    No dentists found.
                </p>

            </c:when>

            <c:otherwise>

                <table>

                    <thead>

                    <tr>
                        <th>ID</th>
                        <th>Dentist</th>
                        <th>Specialization</th>
                        <th>Contact</th>
                        <th>Status</th>
                        <th>Edit</th>
                        <th>Account</th>
                    </tr>

                    </thead>

                    <tbody>

                    <c:forEach var="dentist"
                               items="${dentists}">

                        <tr>

                            <td>
                                <c:out value="${dentist.dentistId}"/>
                            </td>

                            <td>
                                <c:out value="${dentist.dentistName}"/>
                            </td>

                            <td>
                                <c:out value="${dentist.specialization}"/>
                            </td>

                            <td>
                                <c:out value="${dentist.contactNumber}"/>
                            </td>

                            <td>

                                <c:choose>

                                    <c:when test="${dentist.active}">
                                        Active
                                    </c:when>

                                    <c:otherwise>
                                        Inactive
                                    </c:otherwise>

                                </c:choose>

                            </td>

                            <td>

                                <a href="${pageContext.request.contextPath}/admin/dentists?action=edit&dentistId=${dentist.dentistId}">
                                    Edit
                                </a>

                            </td>

                            <td>

                                <form method="post"
                                      action="${pageContext.request.contextPath}/admin/dentists">

                                    <input type="hidden"
                                           name="action"
                                           value="updateStatus">

                                    <input type="hidden"
                                           name="dentistId"
                                           value="${dentist.dentistId}">

                                    <c:choose>

                                        <c:when test="${dentist.active}">

                                            <input type="hidden"
                                                   name="active"
                                                   value="false">

                                            <button type="submit">
                                                Deactivate
                                            </button>

                                        </c:when>

                                        <c:otherwise>

                                            <input type="hidden"
                                                   name="active"
                                                   value="true">

                                            <button type="submit">
                                                Activate
                                            </button>

                                        </c:otherwise>

                                    </c:choose>

                                </form>

                            </td>

                        </tr>

                    </c:forEach>

                    </tbody>

                </table>

            </c:otherwise>

        </c:choose>

    </section>

</main>

</body>

</html>
