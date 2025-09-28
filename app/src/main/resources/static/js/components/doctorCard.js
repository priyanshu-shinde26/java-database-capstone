// doctorCard.js
// Usage: import { createDoctorCard } from './doctorCard.js';

export function createDoctorCard(doctor) {
  const card = document.createElement("div");
  card.classList.add("doctor-card");

  // Doctor info section
  const infoDiv = document.createElement("div");
  infoDiv.classList.add("doctor-info");

  const nameEl = document.createElement("h3");
  nameEl.textContent = doctor.name;

  const specializationEl = document.createElement("p");
  specializationEl.textContent = "Specialty: " + doctor.specialty;

  const emailEl = document.createElement("p");
  emailEl.textContent = "Email: " + doctor.email;

  const availabilityEl = document.createElement("p");
  availabilityEl.textContent = "Availability: " + (Array.isArray(doctor.availability) ? doctor.availability.join(", ") : doctor.availability);

  infoDiv.appendChild(nameEl);
  infoDiv.appendChild(specializationEl);
  infoDiv.appendChild(emailEl);
  infoDiv.appendChild(availabilityEl);

  // Button actions section
  const actionsDiv = document.createElement("div");
  actionsDiv.classList.add("card-actions");

  const role = localStorage.getItem("userRole");

  if (role === "admin") {
    const removeBtn = document.createElement("button");
    removeBtn.textContent = "Delete";
    removeBtn.classList.add("remove-btn");
    removeBtn.addEventListener("click", async () => {
      if (confirm("Are you sure you want to delete this doctor?")) {
        const token = localStorage.getItem("token");
        try {
          // Assumes deleteDoctor API is imported from /js/services/doctorServices.js
          await deleteDoctor(doctor.id, token);
          card.remove();
        } catch (e) {
          alert("Deletion failed.");
        }
      }
    });
    actionsDiv.appendChild(removeBtn);
  } else if (role === "patient") {
    const bookBtn = document.createElement("button");
    bookBtn.textContent = "Book Now";
    bookBtn.classList.add("booking-btn");
    bookBtn.addEventListener("click", () => {
      alert("Patient needs to login first.");
    });
    actionsDiv.appendChild(bookBtn);
  } else if (role === "loggedPatient") {
    const bookBtn = document.createElement("button");
    bookBtn.textContent = "Book Now";
    bookBtn.classList.add("booking-btn");
    bookBtn.addEventListener("click", async (e) => {
      const token = localStorage.getItem("token");
      // Assumes getPatientData, showBookingOverlay are imported
      const patientData = await getPatientData(token);
      showBookingOverlay(e, doctor, patientData);
    });
    actionsDiv.appendChild(bookBtn);
  }

  card.appendChild(infoDiv);
  card.appendChild(actionsDiv);

  return card;
}
