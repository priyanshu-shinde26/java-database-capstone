import { openModal } from "./components/modals.js";
import { getDoctors, filterDoctors, saveDoctor } from "./services/doctorServices.js";
import { createDoctorCard } from "./components/doctorCard.js";

document.addEventListener("DOMContentLoaded", () => {
  loadDoctorCards();

  const addDocBtn = document.getElementById('addDocBtn');
  if (addDocBtn) addDocBtn.addEventListener('click', () => openModal('addDoctor'));

  document.getElementById("searchBar")?.addEventListener("input", filterDoctorsOnChange);
  document.getElementById("filterTime")?.addEventListener("change", filterDoctorsOnChange);
  document.getElementById("filterSpecialty")?.addEventListener("change", filterDoctorsOnChange);
});

async function loadDoctorCards() {
  try {
    const doctors = await getDoctors();
    renderDoctorCards(doctors);
  } catch (err) {
    showError("Failed to load doctors.");
  }
}

async function filterDoctorsOnChange() {
  const name = document.getElementById("searchBar")?.value.trim() ?? "";
  const time = document.getElementById("filterTime")?.value ?? "";
  const specialty = document.getElementById("filterSpecialty")?.value ?? "";
  try {
    const doctors = await filterDoctors(name, time, specialty);
    if (doctors.length > 0) {
      renderDoctorCards(doctors);
    } else {
      displayNoDoctorsFound();
    }
  } catch {
    showError("Error filtering doctors.");
  }
}

function renderDoctorCards(doctors) {
  const contentDiv = document.getElementById("content");
  contentDiv.innerHTML = '';
  doctors.forEach(doctor => {
    const card = createDoctorCard(doctor);
    contentDiv.appendChild(card);
  });
}

function displayNoDoctorsFound() {
  const contentDiv = document.getElementById("content");
  contentDiv.innerHTML = "<p>No doctors found with the given filters.</p>";
}

function showError(message) {
  const contentDiv = document.getElementById("content");
  contentDiv.innerHTML = `<p class="error">${message}</p>`;
}

window.adminAddDoctor = async function () {
  const token = localStorage.getItem("token");
  if (!token) {
    alert("Please log in.");
    return;
  }

  const doctorData = collectDoctorFormData();
  if (!doctorData) return;

  try {
    const result = await saveDoctor(doctorData, token);
    if (result.success) {
      alert("Doctor added successfully.");
      loadDoctorCards();
      closeModal();
    } else {
      alert("Failed to add doctor: " + result.message);
    }
  } catch {
    alert("Error saving doctor.");
  }
};

function collectDoctorFormData() {
  // Implement form field collection and validation here
  return {
    name: document.getElementById("doctorName").value,
    specialty: document.getElementById("doctorSpecialty").value,
    email: document.getElementById("doctorEmail").value,
    password: document.getElementById("doctorPassword").value,
    phone: document.getElementById("doctorPhone").value,
    availability: [] // Collect availability checkboxes as array
  };
}

function closeModal() {
  const modal = document.getElementById("modal");
  if (modal) modal.style.display = "none";
}
