import { createDoctorCard } from "./components/doctorCard.js";
import { openModal } from "./components/modals.js";
import { getDoctors, filterDoctors } from "./services/doctorServices.js";
import { patientLogin, patientSignup } from "./services/patientServices.js";

document.addEventListener("DOMContentLoaded", () => {
  loadDoctorCards();
  setupModalTriggers();
  setupFilterListeners();
});

async function loadDoctorCards() {
  try {
    const doctors = await getDoctors();
    renderDoctorCards(doctors);
  } catch {
    renderNoDoctorsMessage();
  }
}

function setupModalTriggers() {
  document.getElementById("patientSignup")?.addEventListener("click", () => openModal("patientSignup"));
  document.getElementById("patientLogin")?.addEventListener("click", () => openModal("patientLogin"));
}

function setupFilterListeners() {
  document.getElementById("searchBar")?.addEventListener("input", filterDoctorsOnChange);
  document.getElementById("filterTime")?.addEventListener("change", filterDoctorsOnChange);
  document.getElementById("filterSpecialty")?.addEventListener("change", filterDoctorsOnChange);
}

async function filterDoctorsOnChange() {
  const name = document.getElementById("searchBar")?.value || "";
  const time = document.getElementById("filterTime")?.value || "";
  const specialty = document.getElementById("filterSpecialty")?.value || "";
  try {
    const doctors = await filterDoctors(name, time, specialty);
    if (doctors.length > 0) {
      renderDoctorCards(doctors);
    } else {
      renderNoDoctorsMessage();
    }
  } catch {
    renderNoDoctorsMessage();
  }
}

function renderDoctorCards(doctors) {
  const contentDiv = document.getElementById("content");
  contentDiv.innerHTML = "";
  doctors.forEach(doctor => {
    const card = createDoctorCard(doctor);
    contentDiv.appendChild(card);
  });
}

function renderNoDoctorsMessage() {
  const contentDiv = document.getElementById("content");
  contentDiv.innerHTML = "<p>No doctors found with the given filters.</p>";
}

window.signupPatient = async function () {
  // Implement signup form data collection and call patientSignup()
  alert("Signup functionality not implemented yet.");
};

window.loginPatient = async function () {
  // Implement login form data collection and call patientLogin()
  alert("Login functionality not implemented yet.");
};
