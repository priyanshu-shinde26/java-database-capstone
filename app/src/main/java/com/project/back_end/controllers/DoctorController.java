package com.project.back_end.controller;

import com.project.back_end.dto.Login;
import com.project.back_end.model.Doctor;
import com.project.back_end.service.DoctorService;
import com.project.back_end.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("${api.path}doctor")
public class DoctorController {

    @Autowired private DoctorService doctorService;
    @Autowired private Service service;

    @GetMapping("/availability/{user}/{doctorId}/{date}/{token}")
    public ResponseEntity<?> getAvailability(@PathVariable String user, @PathVariable Long doctorId, @PathVariable LocalDate date, @PathVariable String token) {
        if (service.validateToken(token, user).getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(Map.of("message", "Unauthorized"), org.springframework.http.HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(Map.of("availability", doctorService.getDoctorAvailability(doctorId, date)));
    }

    // Implement other endpoints following a similar pattern of validation and delegation
}
