<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">

    <title>
        Dashboard - Sunrise Dental Clinic
    </title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

<c:set var="activePage"
       value="dashboard"/>

<div class="app-layout">

<jsp:include page="/WEB-INF/view/includes/sidebar.jsp"/>

<div class="app-main">

<header class="top-bar">

    <div class="container">

        <h1>
            Sunrise Dental Clinic
        </h1>

        <nav>

            <a href="${pageContext.request.contextPath}/dashboard"
               class="active">
                Dashboard
            </a>

            <a href="${pageContext.request.contextPath}/patient">
                Patient Management
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

    <div class="page-header">

        <h1>
            Welcome back, <c:out value="${sessionScope.staffUser}"/>
        </h1>

        <p>
            Here's what's happening at Sunrise Dental Clinic today.
        </p>

    </div>

    <c:if test="${not empty sessionScope.loginMessage}">

        <div class="success-message">

                <c:out value="${sessionScope.loginMessage}"/>

        </div>

        <c:remove var="loginMessage"
                  scope="session"/>

    </c:if>


    <section class="card">

        <h2>
            Welcome, ${sessionScope.staffUser}
        </h2>

        <p>
            Role:
            <c:choose>

                <c:when test="${sessionScope.staffRole == 'ADMIN'}">
                    Administrator
                </c:when>

                <c:otherwise>
                    Staff
                </c:otherwise>

            </c:choose>
        </p>

    </section>


    <section class="card">

        <h2>
            Clinic Statistics
        </h2>

        <div class="dashboard-grid">

            <div class="dashboard-card">

                <h3>
                    Today's Appointments
                </h3>

                <p class="dashboard-number">
                    <c:out value="${stats.todayAppointments}"/>
                </p>

            </div>

            <div class="dashboard-card">

                <h3>
                    Upcoming Appointments
                </h3>

                <p class="dashboard-number">
                    <c:out value="${stats.upcomingAppointments}"/>
                </p>

            </div>

            <div class="dashboard-card">

                <h3>
                    Registered Patients
                </h3>

                <p class="dashboard-number">
                    <c:out value="${stats.totalPatients}"/>
                </p>

            </div>

            <c:if test="${sessionScope.staffRole == 'ADMIN'}">

                <div class="dashboard-card">

                    <h3>
                        Active Dentists
                    </h3>

                    <p class="dashboard-number">
                        <c:out value="${stats.activeDentists}"/>
                    </p>

                </div>

                <div class="dashboard-card">

                    <h3>
                        Active User Accounts
                    </h3>

                    <p class="dashboard-number">
                        <c:out value="${stats.activeUsers}"/>
                    </p>

                </div>

            </c:if>

        </div>

    </section>


    <section class="card">

        <h2>
            Quick Actions
        </h2>

        <div class="quick-actions">

            <a href="${pageContext.request.contextPath}/patient">
                Find Patient
            </a>

            <a href="${pageContext.request.contextPath}/appointment">
                Register Appointment
            </a>

            <a href="${pageContext.request.contextPath}/billing">
                Generate Bill
            </a>

            <a href="${pageContext.request.contextPath}/reports">
                View Reports
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

        </div>

    </section>


</main>

</div>

</div>

</body>
</html>
