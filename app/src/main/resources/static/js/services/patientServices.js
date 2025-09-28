import { API_BASE_URL } from "../config/config.js";
const PATIENT_API = API_BASE_URL + '/patient';

export async function patientSignup(data) {
  try {
    const response = await fetch(PATIENT_API + '/signup', {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data)
    });
    if (!response.ok) {
      return { success: false, message: "Signup failed" };
    }
    return await response.json();
  } catch (err) {
    return { success: false, message: err.message };
  }
}

export async function patientLogin(data) {
  try {
    const response = await fetch(PATIENT_API + '/login', {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data)
    });
    return response;
  } catch (err) {
    return null;
  }
}

export async function getPatientData(token) {
  try {
    const response = await fetch(PATIENT_API + '/profile', {
      headers: { "Authorization": `Bearer ${token}` }
    });
    if (!response.ok) return null;
    return await response.json();
  } catch (err) {
    return null;
  }
}

export async function getPatientAppointments(id, token, user = 'patient') {
  try {
    const response = await fetch(`${PATIENT_API}/${user}/${id}/appointments`, {
      headers: { "Authorization": `Bearer ${token}` }
    });
    if (!response.ok) return [];
    const data = await response.json();
    return data.appointments || [];
  } catch (err) {
    return [];
  }
}

export async function filterAppointments(condition, name, token) {
  try {
    const url = `${PATIENT_API}/appointments/filter?condition=${encodeURIComponent(condition)}&name=${encodeURIComponent(name)}`;
    const response = await fetch(url, {
      headers: { "Authorization": `Bearer ${token}` }
    });
    if (!response.ok) return [];
    return await response.json();
  } catch (err) {
    return [];
  }
}
