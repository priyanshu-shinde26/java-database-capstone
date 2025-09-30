package com.project.back_end.service;

import com.project.back_end.dto.Login;
import com.project.back_end.model.Admin;
import com.project.back_end.model.Appointment;
import com.project.back_end.model.Doctor;
import com.project.back_end.model.Patient;
import com.project.back_end.repository.AdminRepository;
import com.project.back_end.repository.DoctorRepository;
import com.project.back_end.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service
public class Service {

    @Autowired private TokenService tokenService;
    @Autowired private AdminRepository adminRepository;
    @Autowired private DoctorRepository doctorRepository;
    @Autowired private PatientRepository patientRepository;
    @Autowired private DoctorService doctorService;
    @Autowired private PatientService patientService;

    public ResponseEntity<Map<String, String>> validateToken(String token, String user) {
        Map<String, String> response = new HashMap<>();
        if (!tokenService.validateToken(token, user)) {
            response.put("message", "Invalid or expired token");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Map<String, String>> validateAdmin(Admin receivedAdmin) {
        Admin admin = adminRepository.findByUsername(receivedAdmin.getUsername());
        Map<String, String> response = new HashMap<>();
        if (admin != null && admin.getPassword().equals(receivedAdmin.getPassword())) {
            response.put("token", tokenService.generateToken(admin.getUsername(), "admin"));
            return ResponseEntity.ok(response);
        }
        response.put("message", "Invalid credentials");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    public Map<String, Object> filterDoctor(String name, String specialty, String time) {
        // Delegate filtering logic to DoctorService
        return doctorService.filterDoctorsByNameSpecilityandTime(name, specialty, time);
    }

    public int validateAppointment(Appointment appointment) {
        Doctor doctor = doctorRepository.findById(appointment.getDoctor().getId()).orElse(null);
        if (doctor == null) return -1; // Doctor not found

        List<String> availableSlots = doctorService.getDoctorAvailability(doctor.getId(), appointment.getAppointmentTime().toLocalDate());
        String requestedTime = appointment.getAppointmentTime().toLocalTime().toString();

        return availableSlots.stream().anyMatch(slot -> slot.startsWith(requestedTime)) ? 1 : 0;
    }

    public boolean validatePatient(Patient patient) {
        return patientRepository.findByEmailOrPhone(patient.getEmail(), patient.getPhone()) == null;
    }

    public ResponseEntity<Map<String, String>> validatePatientLogin(Login login) {
        Patient patient = patientRepository.findByEmail(login.getIdentifier());
        Map<String, String> response = new HashMap<>();
        if (patient != null && patient.getPassword().equals(login.getPassword())) {
            response.put("token", tokenService.generateToken(patient.getEmail(), "patient"));
            return ResponseEntity.ok(response);
        }
        response.put("message", "Invalid credentials");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<Map<String, Object>> filterPatient(String condition, String name, String token) {
        String email = tokenService.extractIdentifier(token);
        Patient patient = patientRepository.findByEmail(email);
        if (patient == null) {
            return new ResponseEntity<>(Map.of("message", "Patient not found"), HttpStatus.NOT_FOUND);
        }

        if (condition != null && name != null) {
            return patientService.filterByDoctorAndCondition(condition, name, patient.getId());
        } else if (condition != null) {
            return patientService.filterByCondition(condition, patient.getId());
        } else if (name != null) {
            return patientService.filterByDoctor(name, patient.getId());
        }
        return patientService.getPatientAppointment(patient.getId(), token);
    }
}
