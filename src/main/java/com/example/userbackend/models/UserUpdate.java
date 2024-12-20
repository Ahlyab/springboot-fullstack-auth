package com.example.userbackend.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserUpdate {
    private String firstName;
    private String lastName;
    private String email;
}
