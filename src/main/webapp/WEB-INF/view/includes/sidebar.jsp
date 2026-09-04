<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<aside class="sidebar screen-only-navigation">

    <div class="sidebar-brand">
        <div class="brand-mark">
            SD
        </div>

        <div>
            <h1>
                Sunrise Dental
            </h1>

            <p>
                Clinic Management
            </p>
        </div>
    </div>

    <nav class="sidebar-nav">

        <a href="${pageContext.request.contextPath}/dashboard"
           class="${activePage == 'dashboard' ? 'active' : ''}">
            Dashboard
        </a>

        <a href="${pageContext.request.contextPath}/patient"
           class="${activePage == 'patients' ? 'active' : ''}">
            Patients
        </a>

        <a href="${pageContext.request.contextPath}/appointment"
           class="${activePage == 'appointments' ? 'active' : ''}">
            Appointments
        </a>

        <a href="${pageContext.request.contextPath}/billing"
           class="${activePage == 'billing' ? 'active' : ''}">
            Billing
        </a>

        <a href="${pageContext.request.contextPath}/reports"
           class="${activePage == 'reports' ? 'active' : ''}">
            Reports
        </a>

        <a href="${pageContext.request.contextPath}/help"
           class="${activePage == 'help' ? 'active' : ''}">
            Help
        </a>

        <c:if test="${sessionScope.staffRole == 'ADMIN'}">

            <div class="sidebar-section-title">
                Administration
            </div>

            <a href="${pageContext.request.contextPath}/admin/staff"
               class="${activePage == 'users' ? 'active' : ''}">
                User Management
            </a>

            <a href="${pageContext.request.contextPath}/admin/dentists"
               class="${activePage == 'dentists' ? 'active' : ''}">
                Dentist Management
            </a>

            <a href="${pageContext.request.contextPath}/admin/treatments"
               class="${activePage == 'treatments' ? 'active' : ''}">
                Treatment Management
            </a>

        </c:if>

    </nav>

    <div class="sidebar-footer">
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
            Logout
        </a>
    </div>

</aside>
