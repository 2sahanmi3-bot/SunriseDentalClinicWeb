<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">
    <title>Sunrise Dental Clinic - Login</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

<div class="login-page">

    <section class="login-intro">

        <h1>
            Sunrise Dental Clinic
        </h1>

        <p>
            Simple and reliable clinic management for your team.
            Manage patients, appointments, treatments and billing securely
            from one professional system.
        </p>

        <div class="login-highlights">
            <span>Secure</span>
            <span>Simple</span>
            <span>Clinic focused</span>
        </div>

    </section>

    <div class="login-card">

        <h1>Sign In</h1>

        <p class="subtitle">
            Sign in to access the clinic management system.
        </p>

        <c:if test="${not empty errorMessage}">

        <div class="error-message">
            <c:out value="${errorMessage}"/>
        </div>

        </c:if>

        <form method="post"
              action="${pageContext.request.contextPath}/auth">

            <div class="form-group">

                <label for="username">
                    Username
                </label>

                <input type="text"
                       id="username"
                       name="username"
                       maxlength="50"
                       autocomplete="username"
                       required>

            </div>

            <div class="form-group">

                <label for="password">
                    Password
                </label>

                <input type="password"
                       id="password"
                       name="password"
                       autocomplete="current-password"
                       required>

            </div>

            <button type="submit"
                    class="primary-button">

                Sign In

            </button>

        </form>

    </div>

</div>

</body>
</html>
