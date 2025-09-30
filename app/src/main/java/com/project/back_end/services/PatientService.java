package com.project.back_end.service;

import com.project.back_end.dto.AppointmentDTO;
import com.project.back_end.model.Patient;
import com.project.back_end.repository.AppointmentRepository;
import com.project.back_end.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private TokenService tokenService;

    public int createPatient(Patient patient) {
        try {
            patientRepository.save(patient);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    public ResponseEntity<Map<String, Object>> getPatientAppointment(Long id, String token) {
        String emailFromToken = tokenService.decodeToken(token).get("email").toString();
        Patient patient = patientRepository.findById(id).orElse(null);
        Map<String, Object> response = new HashMap<>();

        if (patient == null || !patient.getEmail().equals(emailFromToken)) {
            response.put("message", "Unauthorized access");
            return ResponseEntity.status(401).body(response);
        }

        List<AppointmentDTO> dtos = appointmentRepository.findByPatientId(id).stream()
                .map(app -> new AppointmentDTO(
                        app.getId(), app.getDoctor().getId(), app.getDoctor().getName(),
                        app.getPatient().getId(), app.getPatient().getName(), app.getPatient().getEmail(),
                        app.getPatient().getPhone(), app.getPatient().getAddress(), app.getAppointmentTime(), app.getStatus()))
                .collect(Collectors.toList());

        response.put("appointments", dtos);
        return ResponseEntity.ok(response);
    }

    // Implement all filter methods similarly...
    public ResponseEntity<Map<String, Object>> filterByCondition(String condition, Long id) {
        int status = "past".equalsIgnoreCase(condition) ? 1 : 0;
        List<AppointmentDTO> dtos = appointmentRepository.findByPatient_IdAndStatusOrderByAppointmentTimeAsc(id, status)
                .stream().map(app -> new AppointmentDTO(/* mapping fields */))
                .collect(Collectors.toList());
        Map<String, Object> response = new HashMap<>();
        response.put("appointments", dtos);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Map<String, Object>> getPatientDetails(String token) {
        String email = tokenService.decodeToken(token).get("email").toString();
        Patient patient = patientRepository.findByEmail(email);
        Map<String, Object> response = new HashMap<>();
        response.put("patient", patient);
        return ResponseEntity.ok(response);
    }

    // ... add all other filter method implementations
}
