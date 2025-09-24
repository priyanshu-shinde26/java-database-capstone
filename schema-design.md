## MySQL Database Design

### Table: patients
- id: INT, Primary Key, AUTO_INCREMENT, NOT NULL
- name: VARCHAR(100), NOT NULL
- email: VARCHAR(100), UNIQUE, NOT NULL
- password: VARCHAR(255), NOT NULL   // Store hashed password
- phone: VARCHAR(20), NOT NULL
- created_at: DATETIME, DEFAULT CURRENT_TIMESTAMP

### Table: doctors
- id: INT, Primary Key, AUTO_INCREMENT, NOT NULL
- name: VARCHAR(100), NOT NULL
- email: VARCHAR(100), UNIQUE, NOT NULL
- password: VARCHAR(255), NOT NULL
- phone: VARCHAR(20), NOT NULL
- specialization: VARCHAR(100)
- available_from: TIME
- available_to: TIME
- created_at: DATETIME, DEFAULT CURRENT_TIMESTAMP

### Table: admins
- id: INT, Primary Key, AUTO_INCREMENT, NOT NULL
- name: VARCHAR(100), NOT NULL
- email: VARCHAR(100), UNIQUE, NOT NULL
- password: VARCHAR(255), NOT NULL

### Table: appointments
- id: INT, Primary Key, AUTO_INCREMENT, NOT NULL
- doctor_id: INT, Foreign Key -> doctors(id), NOT NULL
- patient_id: INT, Foreign Key -> patients(id), NOT NULL
- appointment_time: DATETIME, NOT NULL
- status: INT (0 = Scheduled, 1 = Completed, 2 = Cancelled)
- created_at: DATETIME, DEFAULT CURRENT_TIMESTAMP

// Note: Set ON DELETE CASCADE for foreign keys if you want related appointments deleted automatically.

## MongoDB Collection Design

### Collection: prescriptions

- Each prescription is linked to an appointment but allows flexibility for additional fields or embedded medication arrays.  
- Consider separate collections for feedback, chat logs, or file uploads if needed.
