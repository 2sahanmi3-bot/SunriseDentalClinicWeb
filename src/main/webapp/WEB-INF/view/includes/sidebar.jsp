<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<aside class="app-sidebar sidebar screen-only-navigation">

    <div class="sidebar-brand">

        <div class="brand-mark">
            <i class="fa-solid fa-tooth"></i>
        </div>

        <div>
            <h1>
                Sunrise Dental Clinic
            </h1>

            <p>
                Clinic Management
            </p>
        </div>
    </div>

    <nav class="sidebar-nav">

        <a href="${pageContext.request.contextPath}/dashboard"
           class="sidebar-link ${activePage == 'dashboard' ? 'active' : ''}">
            <i class="fa-solid fa-house"></i>
            <span>Dashboard</span>
        </a>

        <a href="${pageContext.request.contextPath}/patient"
           class="sidebar-link ${activePage == 'patients' ? 'active' : ''}">
            <i class="fa-regular fa-user"></i>
            <span>Patients</span>
        </a>

        <a href="${pageContext.request.contextPath}/appointment"
           class="sidebar-link ${activePage == 'appointments' ? 'active' : ''}">
            <i class="fa-regular fa-calendar"></i>
            <span>Appointments</span>
        </a>

        <a href="${pageContext.request.contextPath}/billing"
           class="sidebar-link ${activePage == 'billing' ? 'active' : ''}">
            <i class="fa-regular fa-credit-card"></i>
            <span>Billing</span>
        </a>

        <a href="${pageContext.request.contextPath}/reports"
           class="sidebar-link ${activePage == 'reports' ? 'active' : ''}">
            <i class="fa-solid fa-chart-column"></i>
            <span>Reports</span>
        </a>

        <a href="${pageContext.request.contextPath}/help"
           class="sidebar-link ${activePage == 'help' ? 'active' : ''}">
            <i class="fa-regular fa-circle-question"></i>
            <span>Help</span>
        </a>

        <c:if test="${sessionScope.staffRole == 'ADMIN'}">

            <div class="sidebar-section-title">
                Administration
            </div>

            <a href="${pageContext.request.contextPath}/admin/staff"
               class="sidebar-link ${activePage == 'users' ? 'active' : ''}">
                <i class="fa-solid fa-users-gear"></i>
                <span>User Management</span>
            </a>

            <a href="${pageContext.request.contextPath}/admin/dentists"
               class="sidebar-link ${activePage == 'dentists' ? 'active' : ''}">
                <i class="fa-solid fa-user-doctor"></i>
                <span>Dentist Management</span>
            </a>

            <a href="${pageContext.request.contextPath}/admin/treatments"
               class="sidebar-link ${activePage == 'treatments' ? 'active' : ''}">
                <i class="fa-solid fa-tooth"></i>
                <span>Treatment Management</span>
            </a>

        </c:if>

    </nav>

    <div class="sidebar-footer">
        <p>
            Signed in as
            <br>
            <strong>
                <c:out value="${sessionScope.staffUser}"/>
            </strong>
        </p>

        <p>
            <c:choose>
                <c:when test="${sessionScope.staffRole == 'ADMIN'}">
                    Administrator
                </c:when>
                <c:otherwise>
                    Staff
                </c:otherwise>
            </c:choose>
        </p>

        <a href="${pageContext.request.contextPath}/auth?action=logout"
           class="logout-link">
            <i class="fa-solid fa-arrow-right-from-bracket"></i>
            Logout
        </a>
    </div>

</aside>
