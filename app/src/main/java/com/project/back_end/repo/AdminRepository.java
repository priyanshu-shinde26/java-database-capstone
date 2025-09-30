package com.project.back_end.repository;

import com.project.back_end.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    /**
     * Finds an admin by their username.
     * @param username The username to search for.
     * @return The Admin entity if found, otherwise null.
     */
    Admin findByUsername(String username);
}
