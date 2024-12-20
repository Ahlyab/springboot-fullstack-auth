package com.example.userbackend.models;

import com.example.userbackend.entities.User;
import lombok.*;

@Getter
@Builder
public class LoginResponse {
    private final String accessToken;
    private final ResponseUserObject user;
    private String message;
    private int status;
}
