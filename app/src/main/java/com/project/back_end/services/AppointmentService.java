package com.project.back_end.service;

import com.project.back_end.dto.AppointmentDTO;
import com.project.back_end.model.Appointment;
import com.project.back_end.model.Doctor;
import com.project.back_end.repository.AppointmentRepository;
import com.project.back_end.repository.DoctorRepository;
import com.project.back_end.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private TokenService tokenService;

    public int bookAppointment(Appointment appointment) {
        try {
            appointmentRepository.save(appointment);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    public ResponseEntity<Map<String, String>> updateAppointment(Appointment appointment) {
        // Validation logic would be here
        appointmentRepository.save(appointment);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Appointment updated successfully");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Map<String, String>> cancelAppointment(long id, String token) {
        // Authorization and cancellation logic here
        appointmentRepository.deleteById(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Appointment canceled successfully");
        return ResponseEntity.ok(response);
    }

    public Map<String, Object> getAppointment(String pname, LocalDate date, String token) {
        String doctorEmail = tokenService.decodeToken(token).get("email").toString();
        Doctor doctor = doctorRepository.findByEmail(doctorEmail);
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        List<Appointment> appointments;
        if (pname == null || pname.trim().isEmpty()) {
            appointments = appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(doctor.getId(), startOfDay, endOfDay);
        } else {
            appointments = appointmentRepository.findByDoctorIdAndPatient_NameContainingIgnoreCaseAndAppointmentTimeBetween(doctor.getId(), pname, startOfDay, endOfDay);
        }

        List<AppointmentDTO> appointmentDTOs = appointments.stream().map(app ->
                new AppointmentDTO(
                        app.getId(), app.getDoctor().getId(), app.getDoctor().getName(),
                        app.getPatient().getId(), app.getPatient().getName(), app.getPatient().getEmail(),
                        app.getPatient().getPhone(), app.getPatient().getAddress(), app.getAppointmentTime(), app.getStatus())
        ).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("appointments", appointmentDTOs);
        return response;
    }
}
