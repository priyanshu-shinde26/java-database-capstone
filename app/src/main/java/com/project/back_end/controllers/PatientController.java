package com.project.back_end.controller;

import com.project.back_end.dto.Login;
import com.project.back_end.model.Patient;
import com.project.back_end.service.PatientService;
import com.project.back_end.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired private PatientService patientService;
    @Autowired private Service service;

    @GetMapping("/{token}")
    public ResponseEntity<?> getPatientDetails(@PathVariable String token) {
        if (service.validateToken(token, "patient").getStatusCode() != org.springframework.http.HttpStatus.OK) {
            return new ResponseEntity<>(Map.of("message", "Unauthorized"), org.springframework.http.HttpStatus.UNAUTHORIZED);
        }
        return patientService.getPatientDetails(token);
    }

    // Implement other endpoints like patient creation, login, and appointment filtering
}
