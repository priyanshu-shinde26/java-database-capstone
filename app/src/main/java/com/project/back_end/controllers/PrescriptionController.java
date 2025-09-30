package com.project.back_end.controller;

import com.project.back_end.model.Prescription;
import com.project.back_end.service.PrescriptionService;
import com.project.back_end.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("${api.path}prescription")
public class PrescriptionController {

    @Autowired private PrescriptionService prescriptionService;
    @Autowired private Service service;

    @PostMapping("/{token}")
    public ResponseEntity<Map<String, String>> savePrescription(@Valid @RequestBody Prescription prescription, @PathVariable String token) {
        if (service.validateToken(token, "doctor").getStatusCode() != org.springframework.http.HttpStatus.OK) {
            return new ResponseEntity<>(Map.of("message", "Unauthorized"), org.springframework.http.HttpStatus.UNAUTHORIZED);
        }
        return prescriptionService.savePrescription(prescription);
    }

    @GetMapping("/{appointmentId}/{token}")
    public ResponseEntity<Map<String, Object>> getPrescription(@PathVariable Long appointmentId, @PathVariable String token) {
        if (service.validateToken(token, "doctor").getStatusCode() != org.springframework.http.HttpStatus.OK) {
            return new ResponseEntity<>(Map.of("message", "Unauthorized"), org.springframework.http.HttpStatus.UNAUTHORIZED);
        }
        return prescriptionService.getPrescription(appointmentId);
    }
}
