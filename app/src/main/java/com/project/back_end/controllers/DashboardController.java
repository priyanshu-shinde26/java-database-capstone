
package com.project.back_end.controller;

import com.project.back_end.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Map;

@Controller
public class DashboardController {

    @Autowired
    private TokenService tokenService; // Assuming you have a TokenService for validation

    /**
     * Handles GET requests for the admin dashboard.
     * Validates the token and renders the view if valid, otherwise redirects.
     *
     * @param token The token from the URL path.
     * @return The view name or a redirect instruction.
     */
    @GetMapping("/adminDashboard/{token}")
    public String adminDashboard(@PathVariable String token) {
        // Validate the token for the 'admin' role
        Map<String, Object> validationResult = tokenService.validateToken(token, "admin");

        if (validationResult.isEmpty()) {
            // If the token is valid (result map is empty), return the dashboard view
            return "admin/adminDashboard";
        } else {
            // If the token is invalid, redirect to the homepage
            return "redirect:/";
        }
    }

    /**
     * Handles GET requests for the doctor dashboard.
     * Validates the token and renders the view if valid, otherwise redirects.
     *
     * @param token The token from the URL path.
     * @return The view name or a redirect instruction.
     */
    @GetMapping("/doctorDashboard/{token}")
    public String doctorDashboard(@PathVariable String token) {
        // Validate the token for the 'doctor' role
        Map<String, Object> validationResult = tokenService.validateToken(token, "doctor");

        if (validationResult.isEmpty()) {
            // If the token is valid, return the doctor dashboard view
            return "doctor/doctorDashboard";
        } else {
            // If the token is invalid, redirect to the homepage
            return "redirect:/";
        }
    }
}
