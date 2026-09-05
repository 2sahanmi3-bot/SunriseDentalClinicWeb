<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Sunrise Dental Clinic - Login</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>

<body class="clinic-login-page">

<main class="clinic-login-shell">

    <section class="clinic-login-hero">

        <div class="clinic-hero-pattern"></div>

        <div class="clinic-brand-block">

            <div class="clinic-brand-mark">
                <i class="fa-solid fa-tooth"></i>
            </div>

            <p class="clinic-eyebrow">
                Internal Clinic System
            </p>

            <h1>
                Sunrise Dental Clinic
            </h1>

            <p class="clinic-tagline">
                Healthy Smiles.
                <br>
                Brighter Tomorrows.
            </p>

            <p class="clinic-hero-copy">
                Simple and reliable clinic management for your team.
                Manage patients, appointments, treatments and billing
                from one secure workspace.
            </p>

        </div>

        <div class="clinic-benefits">

            <div class="clinic-benefit">
                <span>
                    <i class="fa-solid fa-shield-halved"></i>
                </span>

                <div>
                    <strong>Secure</strong>
                    <p>Protected staff access and role-based controls.</p>
                </div>
            </div>

            <div class="clinic-benefit">
                <span>
                    <i class="fa-solid fa-bolt"></i>
                </span>

                <div>
                    <strong>Simple</strong>
                    <p>Clear workflows for daily clinic operations.</p>
                </div>
            </div>

            <div class="clinic-benefit">
                <span>
                    <i class="fa-solid fa-heart-pulse"></i>
                </span>

                <div>
                    <strong>Clinic Focused</strong>
                    <p>Built around patients, appointments and care.</p>
                </div>
            </div>

        </div>

    </section>


    <section class="clinic-login-panel">

        <div class="clinic-login-card">

            <div class="clinic-card-heading">
                <h2>Sign In</h2>

                <p>
                    Sign in to access the clinic management system.
                </p>
            </div>

            <c:if test="${not empty errorMessage}">

                <div class="clinic-login-error">
                    <i class="fa-solid fa-circle-exclamation"></i>

                    <span>
                        <c:out value="${errorMessage}"/>
                    </span>
                </div>

            </c:if>

            <form method="post"
                  action="${pageContext.request.contextPath}/auth"
                  class="clinic-login-form">

                <div class="clinic-field">
                    <label for="username">
                        Username
                    </label>

                    <div class="clinic-input">
                        <i class="fa-regular fa-user"></i>

                        <input type="text"
                               id="username"
                               name="username"
                               maxlength="50"
                               autocomplete="username"
                               placeholder="Enter your username"
                               required>
                    </div>
                </div>

                <div class="clinic-field">
                    <label for="password">
                        Password
                    </label>

                    <div class="clinic-input">
                        <i class="fa-solid fa-lock"></i>

                        <input type="password"
                               id="password"
                               name="password"
                               autocomplete="current-password"
                               placeholder="Enter your password"
                               required>

                        <button type="button"
                                class="clinic-password-toggle"
                                id="passwordToggle"
                                aria-label="Show password">
                            <i class="fa-regular fa-eye"></i>
                        </button>
                    </div>
                </div>

                <button type="submit"
                        class="clinic-login-button">
                    <span>Sign In</span>
                    <i class="fa-solid fa-arrow-right"></i>
                </button>

            </form>

        </div>

    </section>

</main>

<script>
    const passwordInput =
        document.getElementById("password");

    const passwordToggle =
        document.getElementById("passwordToggle");

    if (passwordInput && passwordToggle) {

        passwordToggle.addEventListener("click", function () {

            const icon =
                passwordToggle.querySelector("i");

            if (passwordInput.type === "password") {

                passwordInput.type = "text";
                passwordToggle.setAttribute("aria-label", "Hide password");
                icon.classList.remove("fa-eye");
                icon.classList.add("fa-eye-slash");

            } else {

                passwordInput.type = "password";
                passwordToggle.setAttribute("aria-label", "Show password");
                icon.classList.remove("fa-eye-slash");
                icon.classList.add("fa-eye");
            }
        });
    }
</script>

</body>
</html>
