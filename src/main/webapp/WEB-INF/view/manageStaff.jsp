<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Management - Sunrise Dental Clinic</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

<header class="top-bar">
    <div class="container">
        <h1>Sunrise Dental Clinic</h1>

        <nav>
            <a href="${pageContext.request.contextPath}/appointment">
                Appointments
            </a>

            <a href="${pageContext.request.contextPath}/billing">
                Billing
            </a>

            <a href="${pageContext.request.contextPath}/admin/staff"
               class="active">
                User Management
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

    <section class="card">

        <h2>User Management</h2>

        <p>
            Add a new authorized staff account for the clinic.
        </p>

        <% if (request.getAttribute("successMessage") != null) { %>

        <div class="success-message">
            <%= request.getAttribute("successMessage") %>
        </div>

        <% } %>

        <% if (request.getAttribute("errorMessage") != null) { %>

        <div class="error-message">
            <%= request.getAttribute("errorMessage") %>
        </div>

        <% } %>

        <form method="post"
              action="${pageContext.request.contextPath}/admin/staff">

            <div class="form-group">
                <label for="username">
                    Username
                </label>

                <input type="text"
                       id="username"
                       name="username"
                       maxlength="50"
                       required>
            </div>

            <div class="form-group">
                <label for="password">
                    Password
                </label>

                <input type="password"
                       id="password"
                       name="password"
                       maxlength="100"
                       required>
            </div>

            <div class="form-group">
                <label for="role">
                    Role
                </label>

                <select id="role"
                        name="role"
                        required>

                    <option value="">
                        Select a role
                    </option>

                    <option value="STAFF">
                        Staff
                    </option>

                    <option value="ADMIN">
                        Admin
                    </option>

                </select>
            </div>

            <button type="submit"
                    class="primary-button">
                Create Account
            </button>

        </form>

    </section>

    <section class="card">

        <h2>Authorized User Accounts</h2>

        <c:choose>

            <c:when test="${empty users}">

                <p>
                    No user accounts found.
                </p>

            </c:when>

            <c:otherwise>

                <table>

                    <thead>
                    <tr>
                        <th>User ID</th>
                        <th>Username</th>
                        <th>Role</th>
                        <th>Change Role</th>
                    </tr>
                    </thead>

                    <tbody>

                    <c:forEach var="user"
                               items="${users}">

                        <tr>
                            <td>
                                    ${user.userId}
                            </td>

                            <td>
                                    ${user.username}
                            </td>

                            <td>
                                    ${user.role}
                            </td>

                            <td>

                                <form method="post"
                                      action="${pageContext.request.contextPath}/admin/staff">

                                    <input type="hidden"
                                           name="action"
                                           value="updateRole">

                                    <input type="hidden"
                                           name="userId"
                                           value="${user.userId}">

                                    <select name="role"
                                            required>

                                        <option value="STAFF"
                                            ${user.role == 'STAFF' ? 'selected' : ''}>
                                            Staff
                                        </option>

                                        <option value="ADMIN"
                                            ${user.role == 'ADMIN' ? 'selected' : ''}>
                                            Admin
                                        </option>

                                    </select>

                                    <button type="submit">
                                        Update
                                    </button>

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