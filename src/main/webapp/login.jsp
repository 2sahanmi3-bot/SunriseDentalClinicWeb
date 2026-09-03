<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sunrise Dental Clinic - Login</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

<div class="login-page">

    <div class="login-card">

        <h1>Sunrise Dental Clinic</h1>

        <p class="subtitle">
            Staff Login
        </p>

        <%-- Show login errors returned by the controller. --%>
        <%
            String errorMessage =
                    (String) request.getAttribute(
                            "errorMessage"
                    );

            if (errorMessage != null) {
        %>

        <div class="error-message">
            <%= errorMessage %>
        </div>

        <%
            }
        %>

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

                Login

            </button>

        </form>

    </div>

</div>

</body>
</html>