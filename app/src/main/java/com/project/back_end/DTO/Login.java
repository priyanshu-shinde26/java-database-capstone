package com.project.back_end.dto;

import jakarta.validation.constraints.NotBlank;

public class Login {

    @NotBlank(message = "Identifier cannot be blank")
    private String identifier;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    // Default constructor
    public Login() {
    }

    // Getters and Setters
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
