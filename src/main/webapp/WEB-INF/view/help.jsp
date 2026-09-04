<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">

    <title>
        Help - Sunrise Dental Clinic
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

            <a href="${pageContext.request.contextPath}/dashboard">
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

            <a href="${pageContext.request.contextPath}/help"
               class="active">
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

        <h2>
            Sunrise Dental Clinic Help
        </h2>

        <p>
            This page provides guidance for using the clinic management system.
        </p>

    </section>


    <section class="card">

        <h3>
            1. Getting Started
        </h3>

        <p>
            Staff must log in with their username and password. The dashboard
            shows clinic statistics and quick links to the main system areas.
            ADMIN users have additional management functions, while STAFF users
            use operational functions such as Patients, Appointments, Billing
            and Reports.
        </p>

    </section>


    <section class="card">

        <h3>
            2. Patient Management
        </h3>

        <p>
            Open Patient Management to register, search, view and edit patient
            records. Patient registration includes name, address, contact number
            and email. From patient details, use Book Appointment to create a
            linked appointment for that patient.
        </p>

        <p>
            One patient can have multiple appointments over time. The patient
            details page also shows appointment and treatment history.
        </p>

    </section>


    <section class="card">

        <h3>
            3. Appointment Management
        </h3>

        <p>
            Use Appointment Management to register new appointments and search
            existing appointments by appointment number. During registration,
            enter or confirm patient information, email, dentist, treatment,
            date and time.
        </p>

        <p>
            Appointment numbers must be unique. Only active dentists and active
            treatments should be selectable. A dentist cannot be booked for the
            same date and time while another appointment is still SCHEDULED.
        </p>

    </section>


    <section class="card">

        <h3>
            4. Appointment Status
        </h3>

        <p>
            Appointments use the statuses SCHEDULED, COMPLETED and CANCELLED.
            A scheduled appointment can be marked as completed or cancelled.
            Completed and cancelled appointments cannot be changed back through
            the normal system.
        </p>

        <p>
            Cancelling an appointment releases the dentist's time slot for a
            future booking.
        </p>

    </section>


    <section class="card">

        <h3>
            5. Billing and Receipt
        </h3>

        <p>
            Open Billing and search for the appointment. Generate the bill,
            verify the appointment and patient details, then use the Print
            button to print or save the receipt.
        </p>

        <p>
            The bill uses the treatment charge and consultation fee configured
            for the selected treatment.
        </p>

    </section>


    <section class="card">

        <h3>
            6. Reports
        </h3>

        <p>
            Reports provide operational summaries using real appointment data.
            The available reports are the Daily Appointment Report, Dentist
            Schedule Report and Treatment Summary Report.
        </p>

        <p>
            Daily and dentist schedule reports can be filtered by date. The
            Treatment Summary Report shows appointment totals by treatment,
            including completed and cancelled appointments. Use Print Report
            when a printed or saved copy is needed.
        </p>

    </section>


    <section class="card">

        <h3>
            7. Email Notifications
        </h3>

        <p>
            When a patient has a valid email address, the system can send an
            appointment confirmation after successful booking. A cancellation
            notification can also be sent when an appointment is cancelled.
        </p>

        <p>
            Appointment and database changes remain valid even if email delivery
            fails.
        </p>

    </section>


    <c:if test="${sessionScope.staffRole == 'ADMIN'}">

        <section class="card">

            <h3>
                8. ADMIN-only Management
            </h3>

            <p>
                User Management allows administrators to create ADMIN and STAFF
                accounts, change roles where allowed, and activate or deactivate
                user accounts.
            </p>

            <p>
                Dentist Management allows administrators to add and edit
                dentists, and activate or deactivate dentist records.
            </p>

            <p>
                Treatment Management allows administrators to add and edit
                treatments, update treatment charges and consultation fees, and
                activate or deactivate treatments.
            </p>

        </section>

    </c:if>


    <section class="card">

        <h3>
            9. Common Validation / Troubleshooting
        </h3>

        <ul>
            <li>Complete all required fields before submitting a form.</li>
            <li>Use a valid contact number.</li>
            <li>Use a valid email address.</li>
            <li>Use a unique appointment number.</li>
            <li>Choose an available dentist, date and time.</li>
            <li>If a page does not update after a successful operation, return to the dashboard or refresh once.</li>
            <li>Contact the system administrator for account or access problems.</li>
        </ul>

    </section>


    <section class="card">

        <h3>
            10. Logout / Security
        </h3>

        <p>
            Always use Logout when finished. Do not share account passwords.
            ADMIN functions are restricted to authorized administrators.
        </p>

    </section>

</main>

</body>
</html>
