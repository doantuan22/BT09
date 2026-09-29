package com.example.bai_tap_09_bai_tap.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginDTO {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
