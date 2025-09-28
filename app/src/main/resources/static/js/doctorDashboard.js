import { getAllAppointments } from "./services/appointmentRecordService.js";
import { createPatientRow } from "./components/patientRows.js";

const token = localStorage.getItem("token");
let selectedDate = new Date().toISOString().split('T')[0];
let patientName = null;

document.addEventListener("DOMContentLoaded", () => {
  setupEventListeners();
  loadAppointments();
});

function setupEventListeners() {
  const searchBar = document.getElementById("searchBar");
  searchBar?.addEventListener("input", (e) => {
    patientName = e.target.value.trim() || null;
    loadAppointments();
  });

  const todayButton = document.getElementById("todayAppointmentsBtn");
  todayButton?.addEventListener("click", () => {
    selectedDate = new Date().toISOString().split('T')[0];
    document.getElementById("dateFilter").value = selectedDate;
    loadAppointments();
  });

  const dateFilter = document.getElementById("dateFilter");
  dateFilter?.addEventListener("change", (e) => {
    selectedDate = e.target.value;
    loadAppointments();
  });

  if (dateFilter) dateFilter.value = selectedDate;
}

async function loadAppointments() {
  const tbody = document.querySelector("#patientTable tbody");
  if (!tbody) return;
  tbody.innerHTML = "";

  try {
    const appointments = await getAllAppointments(selectedDate, patientName, token);
    if (!appointments || appointments.length === 0) {
      tbody.innerHTML = `<tr><td colspan="5" style="text-align:center;">No Appointments found for selected criteria.</td></tr>`;
      return;
    }
    appointments.forEach(app => {
      const row = createPatientRow(app);
      tbody.appendChild(row);
    });
  } catch {
    tbody.innerHTML = `<tr><td colspan="5" style="text-align:center;">Error loading appointments.</td></tr>`;
  }
}
