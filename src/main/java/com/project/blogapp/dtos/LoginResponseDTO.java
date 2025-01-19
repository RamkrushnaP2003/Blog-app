package com.project.blogapp.dtos;

public class LoginResponseDTO {
    private String token;

    // Constructor
    public LoginResponseDTO(String token) {
        this.token = token;
    }

    // Getter and setter
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
