import { API_BASE_URL } from "../config/config.js";
const DOCTOR_API = API_BASE_URL + '/doctor';

export async function getDoctors() {
  try {
    const response = await fetch(DOCTOR_API);
    if (!response.ok) return [];
    const data = await response.json();
    return data.doctors || [];
  } catch (err) {
    return [];
  }
}

export async function deleteDoctor(id, token) {
  try {
    const response = await fetch(`${DOCTOR_API}/${id}`, {
      method: "DELETE",
      headers: { "Authorization": `Bearer ${token}` }
    });
    return await response.json();
  } catch (err) {
    return { success: false, message: err.message };
  }
}

export async function saveDoctor(doctor, token) {
  try {
    const response = await fetch(DOCTOR_API, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`
      },
      body: JSON.stringify(doctor)
    });
    return await response.json();
  } catch (err) {
    return { success: false, message: err.message };
  }
}

export async function filterDoctors(name = '', time = '', specialty = '') {
  try {
    const url = `${DOCTOR_API}/filter?name=${encodeURIComponent(name)}&time=${encodeURIComponent(time)}&specialty=${encodeURIComponent(specialty)}`;
    const response = await fetch(url);
    if (!response.ok) return [];
    const data = await response.json();
    return data.doctors || [];
  } catch (err) {
    return [];
  }
}
