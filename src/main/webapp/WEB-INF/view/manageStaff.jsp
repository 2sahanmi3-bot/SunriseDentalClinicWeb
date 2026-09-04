<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Staff - Sunrise Dental Clinic</title>

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
                Staff Management
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

        <h2>Create Staff Account</h2>

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

</main>

</body>
</html>