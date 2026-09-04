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

    <c:if test="${not empty sessionScope.loginMessage}">

        <div class="success-message">

                ${sessionScope.loginMessage}

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
            Quick Actions
        </h2>

        <a href="${pageContext.request.contextPath}/patient">
            Patient Management
        </a>

        <a href="${pageContext.request.contextPath}/appointment">
            Appointments
        </a>

        <a href="${pageContext.request.contextPath}/billing">
            Billing
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

    </section>


    <c:if test="${sessionScope.staffRole == 'ADMIN'}">

        <section class="card">

            <h2>
                Administration
            </h2>

            <p>
                Manage authorized clinic users and administrative functions.
            </p>

            <a href="${pageContext.request.contextPath}/admin/staff">
                Manage Users
            </a>

            <a href="${pageContext.request.contextPath}/admin/dentists">
                Manage Dentists
            </a>

            <a href="${pageContext.request.contextPath}/admin/treatments">
                Manage Treatments
            </a>

        </section>

    </c:if>

</main>

</body>
</html>
