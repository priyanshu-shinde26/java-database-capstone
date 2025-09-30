package com.project.back_end.controller;

import com.project.back_end.model.Appointment;
import com.project.back_end.service.AppointmentService;
import com.project.back_end.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired private AppointmentService appointmentService;
    @Autowired private Service service;

    @GetMapping("/{date}/{patientName}/{token}")
    public ResponseEntity<?> getAppointments(@PathVariable LocalDate date, @PathVariable String patientName, @PathVariable String token) {
        if (service.validateToken(token, "doctor").getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(Map.of("message", "Unauthorized"), HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(appointmentService.getAppointment("null".equals(patientName) ? null : patientName, date, token));
    }

    @PostMapping("/{token}")
    public ResponseEntity<Map<String, String>> bookAppointment(@Valid @RequestBody Appointment appointment, @PathVariable String token) {
        if (service.validateToken(token, "patient").getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(Map.of("message", "Unauthorized"), HttpStatus.UNAUTHORIZED);
        }
        int result = service.validateAppointment(appointment);
        if (result == 1) {
            return (appointmentService.bookAppointment(appointment) == 1)
                    ? new ResponseEntity<>(Map.of("message", "Appointment booked successfully"), HttpStatus.CREATED)
                    : new ResponseEntity<>(Map.of("message", "Booking failed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(Map.of("message", result == 0 ? "Slot not available" : "Doctor not found"), HttpStatus.CONFLICT);
    }

    @PutMapping("/{token}")
    public ResponseEntity<Map<String, String>> updateAppointment(@Valid @RequestBody Appointment appointment, @PathVariable String token) {
        if (service.validateToken(token, "patient").getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(Map.of("message", "Unauthorized"), HttpStatus.UNAUTHORIZED);
        }
        return appointmentService.updateAppointment(appointment);
    }

    @DeleteMapping("/{id}/{token}")
    public ResponseEntity<Map<String, String>> cancelAppointment(@PathVariable Long id, @PathVariable String token) {
        if (service.validateToken(token, "patient").getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(Map.of("message", "Unauthorized"), HttpStatus.UNAUTHORIZED);
        }
        return appointmentService.cancelAppointment(id, token);
    }
}
