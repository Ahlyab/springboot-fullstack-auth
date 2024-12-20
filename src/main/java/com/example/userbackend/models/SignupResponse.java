package com.example.userbackend.models;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class SignupResponse {
    private String message;
    private int status;
    private ResponseUserObject user;
}
