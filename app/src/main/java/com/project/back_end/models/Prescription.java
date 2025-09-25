package com.project.back_end.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Document(collection = "prescriptions")
public class Prescription {

    @Id
    private String id;

    @NotNull(message = "Patient name is required")
    @Size(min = 3, max = 100, message = "Patient name must be between 3 and 100 characters")
    private String patientName;

    @NotNull(message = "Appointment ID is required")
    private Long appointmentId;

    @NotNull(message = "Medication name is required")
    @Size(min = 3, max = 100, message = "Medication name must be between 3 and 100 characters")
    private String medication;

    @NotNull(message = "Dosage is required")
    @Size(min = 3, max = 20, message = "Dosage must be between 3 and 20 characters")
    private String dosage;

    @Size(max = 200, message = "Doctor notes cannot exceed 200 characters")
    private String doctorNotes;

    // Default constructor
    public Prescription() {}

    // Parameterized constructor for easy creation
    public Prescription(String patientName, Long appointmentId, String medication, String dosage, String doctorNotes) {
        this.patientName = patientName;
        this.appointmentId = appointmentId;
        this.medication = medication;
        this.dosage = dosage;
        this.doctorNotes = doctorNotes;
    }

    // Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public
