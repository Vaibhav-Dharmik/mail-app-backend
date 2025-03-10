package com.mail_app_backend.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    private String email;
    private String password;

    // Getters and Setters
}
