<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>
        Treatment Management - Sunrise Dental Clinic
    </title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

</head>

<body>

<c:set var="activePage"
       value="treatments"/>

<div class="app-layout">

<jsp:include page="/WEB-INF/view/includes/sidebar.jsp"/>

<div class="app-main">

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

            <a href="${pageContext.request.contextPath}/admin/dentists">
                Dentist Management
            </a>

            <a href="${pageContext.request.contextPath}/admin/treatments"
               class="active">
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

    <div class="page-header">

        <h1>Treatment Management</h1>

        <p>
            Manage treatment charges, consultation fees and availability.
        </p>

    </div>


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

        <c:when test="${not empty selectedTreatment}">

            <section class="card">

                <h3>Edit Treatment</h3>

                <form method="post"
                      action="${pageContext.request.contextPath}/admin/treatments">

                    <input type="hidden"
                           name="action"
                           value="update">

                    <input type="hidden"
                           name="treatmentId"
                           value="${selectedTreatment.treatmentId}">

                    <div class="form-group">

                        <label for="editTreatmentName">
                            Treatment Name
                        </label>

                        <input type="text"
                               id="editTreatmentName"
                               name="treatmentName"
                               maxlength="100"
                               value="<c:out value='${selectedTreatment.treatmentName}'/>"
                               required>

                    </div>


                    <div class="form-group">

                        <label for="editTreatmentCharge">
                            Treatment Charge
                        </label>

                        <input type="number"
                               id="editTreatmentCharge"
                               name="treatmentCharge"
                               min="0"
                               step="0.01"
                               value="${selectedTreatment.treatmentCharge}"
                               required>

                    </div>


                    <div class="form-group">

                        <label for="editConsultationFee">
                            Consultation Fee
                        </label>

                        <input type="number"
                               id="editConsultationFee"
                               name="consultationFee"
                               min="0"
                               step="0.01"
                               value="${selectedTreatment.consultationFee}"
                               required>

                    </div>


                    <button type="submit"
                            class="primary-button">
                        Save Changes
                    </button>

                    <a href="${pageContext.request.contextPath}/admin/treatments"
                       class="secondary-button">
                        Cancel
                    </a>

                </form>

            </section>

        </c:when>


        <c:otherwise>

            <section class="card">

                <h3>Add Treatment</h3>

                <form method="post"
                      action="${pageContext.request.contextPath}/admin/treatments">

                    <div class="form-group">

                        <label for="treatmentName">
                            Treatment Name
                        </label>

                        <input type="text"
                               id="treatmentName"
                               name="treatmentName"
                               maxlength="100"
                               required>

                    </div>


                    <div class="form-group">

                        <label for="treatmentCharge">
                            Treatment Charge
                        </label>

                        <input type="number"
                               id="treatmentCharge"
                               name="treatmentCharge"
                               min="0"
                               step="0.01"
                               required>

                    </div>


                    <div class="form-group">

                        <label for="consultationFee">
                            Consultation Fee
                        </label>

                        <input type="number"
                               id="consultationFee"
                               name="consultationFee"
                               min="0"
                               step="0.01"
                               required>

                    </div>


                    <button type="submit"
                            class="primary-button">
                        Add Treatment
                    </button>

                </form>

            </section>

        </c:otherwise>

    </c:choose>


    <section class="card">

        <h3>Treatments</h3>

        <c:choose>

            <c:when test="${empty treatments}">

                <p>
                    No treatments found.
                </p>

            </c:when>

            <c:otherwise>

                <div class="table-wrapper">

                <table>

                    <thead>

                    <tr>
                        <th>ID</th>
                        <th>Treatment</th>
                        <th>Treatment Charge</th>
                        <th>Consultation Fee</th>
                        <th>Status</th>
                        <th>Edit</th>
                        <th>Action</th>
                    </tr>

                    </thead>

                    <tbody>

                    <c:forEach var="treatment"
                               items="${treatments}">

                        <tr>

                            <td>
                                <c:out value="${treatment.treatmentId}"/>
                            </td>

                            <td>
                                <c:out value="${treatment.treatmentName}"/>
                            </td>

                            <td>
                                Rs.
                                <c:out value="${treatment.treatmentCharge}"/>
                            </td>

                            <td>
                                Rs.
                                <c:out value="${treatment.consultationFee}"/>
                            </td>

                            <td>

                                <c:choose>

                                    <c:when test="${treatment.active}">
                                        <span class="status-badge status-active">
                                            Active
                                        </span>
                                    </c:when>

                                    <c:otherwise>
                                        <span class="status-badge status-inactive">
                                            Inactive
                                        </span>
                                    </c:otherwise>

                                </c:choose>

                            </td>

                            <td>

                                <a href="${pageContext.request.contextPath}/admin/treatments?action=edit&treatmentId=${treatment.treatmentId}">
                                    Edit
                                </a>

                            </td>

                            <td>

                                <form method="post"
                                      action="${pageContext.request.contextPath}/admin/treatments">

                                    <input type="hidden"
                                           name="action"
                                           value="updateStatus">

                                    <input type="hidden"
                                           name="treatmentId"
                                           value="${treatment.treatmentId}">

                                    <c:choose>

                                        <c:when test="${treatment.active}">

                                            <input type="hidden"
                                                   name="active"
                                                   value="false">

                                            <button type="submit"
                                                    class="danger-button">
                                                Deactivate
                                            </button>

                                        </c:when>

                                        <c:otherwise>

                                            <input type="hidden"
                                                   name="active"
                                                   value="true">

                                            <button type="submit"
                                                    class="secondary-button">
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

                </div>

            </c:otherwise>

        </c:choose>

    </section>

</main>

</div>

</div>

</body>

</html>
