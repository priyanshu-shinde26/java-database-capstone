package com.project.back_end.dto; // Make sure this package name matches your project structure

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * A Data Transfer Object (DTO) for representing appointment data.
 * This class is used to transfer structured data between the service layer and the client,
 * decoupling the API from the internal database model.
 */
public class AppointmentDTO {

    private Long id;
    private Long doctorId;
    private String doctorName;
    private Long patientId;
    private String patientName;
    private String patientEmail;
    private String patientPhone;
    private String patientAddress;
    private LocalDateTime appointmentTime;
    private int status;

    // Derived fields, computed for frontend convenience
    private LocalDate appointmentDate;
    private LocalTime appointmentTimeOnly;
    private LocalDateTime endTime;

    /**
     * Constructs an AppointmentDTO and automatically computes derived date/time fields.
     *
     * @param id The unique identifier of the appointment.
     * @param doctorId The ID of the assigned doctor.
     * @param doctorName The name of the assigned doctor.
     * @param patientId The ID of the patient.
     * @param patientName The name of the patient.
     * @param patientEmail The email of the patient.
     * @param patientPhone The phone number of the patient.
     * @param patientAddress The address of the patient.
     * @param appointmentTime The scheduled date and time of the appointment.
     * @param status The status of the appointment (e.g., 0 for scheduled, 1 for completed).
     */
    public AppointmentDTO(Long id, Long doctorId, String doctorName, Long patientId, String patientName,
                          String patientEmail, String patientPhone, String patientAddress,
                          LocalDateTime appointmentTime, int status) {
        this.id = id;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.patientId = patientId;
        this.patientName = patientName;
        this.patientEmail = patientEmail;
        this.patientPhone = patientPhone;
        this.patientAddress = patientAddress;
        this.appointmentTime = appointmentTime;
        this.status = status;

        // Automatically compute derived fields upon object creation
        this.appointmentDate = appointmentTime.toLocalDate();
        this.appointmentTimeOnly = appointmentTime.toLocalTime();
        this.endTime = appointmentTime.plusHours(1);
    }

    // Standard Getter Methods

    public Long getId() {
        return id;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public Long getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }

    public int getStatus() {
        return status;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public LocalTime getAppointmentTimeOnly() {
        return appointmentTimeOnly;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
