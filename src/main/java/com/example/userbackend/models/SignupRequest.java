package com.example.userbackend.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class SignupRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
