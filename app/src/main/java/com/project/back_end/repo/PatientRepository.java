package com.project.back_end.repository;

import com.project.back_end.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Patient findByEmail(String email);

    Patient findByEmailOrPhone(String email, String phone);
}
