package com.example.userbackend.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseUserObject {
    private String email;
    private String role;
    private String firstName;
    private String lastName;
    private String id;
}
