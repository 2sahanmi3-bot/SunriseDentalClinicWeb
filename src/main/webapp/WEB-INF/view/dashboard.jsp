<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>
        Dashboard - Sunrise Dental Clinic
    </title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>

<body class="dashboard-page">

<c:set var="activePage"
       value="dashboard"/>

<div class="app-layout dashboard-shell">

    <jsp:include page="/WEB-INF/view/includes/sidebar.jsp"/>

    <main class="dashboard-main">

        <header class="dashboard-topbar">

            <div class="dashboard-topbar-spacer"></div>

            <div class="dashboard-user">

                <div class="user-avatar">
                    <c:choose>
                        <c:when test="${sessionScope.staffRole == 'ADMIN'}">
                            AD
                        </c:when>
                        <c:otherwise>
                            ST
                        </c:otherwise>
                    </c:choose>
                </div>

                <div>
                    <strong>
                        <c:out value="${sessionScope.staffUser}"/>
                    </strong>

                    <span>
                        <c:choose>
                            <c:when test="${sessionScope.staffRole == 'ADMIN'}">
                                Administrator
                            </c:when>
                            <c:otherwise>
                                Staff
                            </c:otherwise>
                        </c:choose>
                    </span>
                </div>

            </div>

        </header>


        <section class="dashboard-content">

            <div class="dashboard-welcome">

                <div>
                    <p class="dashboard-kicker">
                        Clinic Overview
                    </p>

                    <h1>
                        Welcome back,
                        <c:out value="${sessionScope.staffUser}"/>
                    </h1>

                    <p>
                        Here's what's happening at Sunrise Dental Clinic today.
                    </p>
                </div>

                <div class="dashboard-date">
                    <i class="fa-regular fa-calendar"></i>
                    <span id="currentDate"></span>
                </div>

            </div>


            <c:if test="${not empty sessionScope.loginMessage}">

                <div class="dashboard-alert">

                    <div class="alert-check">
                        <i class="fa-solid fa-check"></i>
                    </div>

                    <div>
                        <strong>Login successful!</strong>

                        <p>
                            <c:out value="${sessionScope.loginMessage}"/>
                        </p>
                    </div>

                </div>

                <c:remove var="loginMessage"
                          scope="session"/>

            </c:if>


            <div class="dashboard-stats">

                <div class="stat-card">

                    <div class="stat-icon stat-blue">
                        <i class="fa-regular fa-calendar"></i>
                    </div>

                    <div class="stat-details">
                        <span>Today's Appointments</span>

                        <strong>
                            <c:out value="${stats.todayAppointments}"/>
                        </strong>
                    </div>

                </div>


                <div class="stat-card">

                    <div class="stat-icon stat-orange">
                        <i class="fa-regular fa-clock"></i>
                    </div>

                    <div class="stat-details">
                        <span>Upcoming Appointments</span>

                        <strong>
                            <c:out value="${stats.upcomingAppointments}"/>
                        </strong>
                    </div>

                </div>


                <div class="stat-card">

                    <div class="stat-icon stat-green">
                        <i class="fa-solid fa-user-group"></i>
                    </div>

                    <div class="stat-details">
                        <span>Registered Patients</span>

                        <strong>
                            <c:out value="${stats.totalPatients}"/>
                        </strong>
                    </div>

                </div>


                <c:if test="${sessionScope.staffRole == 'ADMIN'}">

                    <div class="stat-card">

                        <div class="stat-icon stat-purple">
                            <i class="fa-solid fa-user-doctor"></i>
                        </div>

                        <div class="stat-details">
                            <span>Active Dentists</span>

                            <strong>
                                <c:out value="${stats.activeDentists}"/>
                            </strong>
                        </div>

                    </div>


                    <div class="stat-card">

                        <div class="stat-icon stat-blue">
                            <i class="fa-solid fa-users-gear"></i>
                        </div>

                        <div class="stat-details">
                            <span>Active User Accounts</span>

                            <strong>
                                <c:out value="${stats.activeUsers}"/>
                            </strong>
                        </div>

                    </div>

                </c:if>

            </div>


            <section class="quick-actions-card">

                <div class="section-heading">
                    <h2>Quick Actions</h2>

                    <p>
                        Common tasks to manage your clinic efficiently.
                    </p>
                </div>

                <div class="quick-actions-grid">

                    <a href="${pageContext.request.contextPath}/patient"
                       class="quick-action quick-blue">

                        <div class="quick-icon">
                            <i class="fa-regular fa-user"></i>
                        </div>

                        <span>Find Patient</span>

                        <i class="fa-solid fa-chevron-right"></i>

                    </a>


                    <a href="${pageContext.request.contextPath}/appointment"
                       class="quick-action quick-green">

                        <div class="quick-icon">
                            <i class="fa-regular fa-calendar-plus"></i>
                        </div>

                        <span>Register Appointment</span>

                        <i class="fa-solid fa-chevron-right"></i>

                    </a>


                    <a href="${pageContext.request.contextPath}/billing"
                       class="quick-action quick-orange">

                        <div class="quick-icon">
                            <i class="fa-regular fa-file-lines"></i>
                        </div>

                        <span>Generate Bill</span>

                        <i class="fa-solid fa-chevron-right"></i>

                    </a>


                    <a href="${pageContext.request.contextPath}/reports"
                       class="quick-action quick-purple">

                        <div class="quick-icon">
                            <i class="fa-solid fa-chart-column"></i>
                        </div>

                        <span>View Reports</span>

                        <i class="fa-solid fa-chevron-right"></i>

                    </a>


                    <c:if test="${sessionScope.staffRole == 'ADMIN'}">

                        <a href="${pageContext.request.contextPath}/admin/staff"
                           class="quick-action quick-blue">

                            <div class="quick-icon">
                                <i class="fa-solid fa-users"></i>
                            </div>

                            <span>User Management</span>

                            <i class="fa-solid fa-chevron-right"></i>

                        </a>


                        <a href="${pageContext.request.contextPath}/admin/dentists"
                           class="quick-action quick-red">

                            <div class="quick-icon">
                                <i class="fa-solid fa-user-doctor"></i>
                            </div>

                            <span>Dentist Management</span>

                            <i class="fa-solid fa-chevron-right"></i>

                        </a>


                        <a href="${pageContext.request.contextPath}/admin/treatments"
                           class="quick-action quick-green">

                            <div class="quick-icon">
                                <i class="fa-solid fa-tooth"></i>
                            </div>

                            <span>Treatment Management</span>

                            <i class="fa-solid fa-chevron-right"></i>

                        </a>

                    </c:if>

                </div>

            </section>

        </section>

    </main>

</div>

<script>
    const dateElement =
        document.getElementById("currentDate");

    if (dateElement) {

        const today =
            new Date();

        dateElement.textContent =
            today.toLocaleDateString(
                "en-GB",
                {
                    weekday: "long",
                    day: "numeric",
                    month: "long",
                    year: "numeric"
                }
            );
    }
</script>

</body>
</html>
