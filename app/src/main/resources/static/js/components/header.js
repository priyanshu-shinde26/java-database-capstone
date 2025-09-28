// header.js

function renderHeader() {
  const headerDiv = document.getElementById("header");
  if (!headerDiv) return;

  // Check if it's the homepage ("/" or "/index.html"); no role-based header on landing
  if (window.location.pathname.endsWith("/index.html") || window.location.pathname === "/") {
    localStorage.removeItem("userRole");
    localStorage.removeItem("token");
    headerDiv.innerHTML = `
      <header class="header">
        <img src="./assets/images/logo/logo.png" alt="Clinic Logo" class="logo" />
        <nav><span>Smart Clinic Management System</span></nav>
      </header>
    `;
    return;
  }

  // Get current role and token from localStorage
  const role = localStorage.getItem("userRole");
  const token = localStorage.getItem("token");
  let headerContent = `
    <header class="header">
      <img src="./assets/images/logo/logo.png" alt="Clinic Logo" class="logo" />
      <nav>
  `;

  // Handle invalid session for secure roles
  if ((role === "loggedPatient" || role === "admin" || role === "doctor") && !token) {
    localStorage.removeItem("userRole");
    alert("Session expired or invalid login. Please log in again.");
    window.location.href = "/";
    return;
  }

  // Role-based navigation/content
  if (role === "admin") {
    headerContent += `
      <button id="addDocBtn" class="adminBtn" onclick="openModal('addDoctor')">Add Doctor</button>
      <a href="#" id="logoutBtn">Logout</a>
    `;
  } else if (role === "doctor") {
    headerContent += `
      <a href="/doctor/doctorDashboard.html" id="homeBtn">Home</a>
      <a href="#" id="logoutBtn">Logout</a>
    `;
  } else if (role === "patient") {
    headerContent += `
      <button id="loginBtn">Login</button>
      <button id="signupBtn">Sign Up</button>
    `;
  } else if (role === "loggedPatient") {
    headerContent += `
      <a href="/pages/patientDashboard.html" id="homeBtn">Home</a>
      <a href="/pages/appointments.html" id="appointmentsBtn">Appointments</a>
      <a href="#" id="logoutPatientBtn">Logout</a>
    `;
  } else {
    // Fallback: default branding only
    headerContent += `<span>Smart Clinic Management System</span>`;
  }

  headerContent += `
      </nav>
    </header>
  `;

  headerDiv.innerHTML = headerContent;
  attachHeaderButtonListeners();
}

// Attach event listeners for dynamically-added buttons
function attachHeaderButtonListeners() {
  const logoutBtn = document.getElementById("logoutBtn");
  if (logoutBtn) {
    logoutBtn.addEventListener("click", logout);
  }
  const logoutPatientBtn = document.getElementById("logoutPatientBtn");
  if (logoutPatientBtn) {
    logoutPatientBtn.addEventListener("click", logoutPatient);
  }
  const loginBtn = document.getElementById("loginBtn");
  if (loginBtn) {
    loginBtn.addEventListener("click", () => window.location.href = "/login.html");
  }
  const signupBtn = document.getElementById("signupBtn");
  if (signupBtn) {
    signupBtn.addEventListener("click", () => window.location.href = "/signup.html");
  }
  // AddDocBtn's onclick handled via inline attribute
}

// Logout for admins and doctors
function logout() {
  localStorage.removeItem("token");
  localStorage.removeItem("userRole");
  window.location.href = "/";
}

// Logout for patients, resets to generic patient
function logoutPatient() {
  localStorage.removeItem("token");
  localStorage.setItem("userRole", "patient");
  window.location.href = "/pages/patientDashboard.html";
}

// Render header on page load
document.addEventListener("DOMContentLoaded", renderHeader);
