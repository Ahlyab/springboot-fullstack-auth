package com.example.userbackend.controllers;

import com.example.userbackend.entities.User;
import com.example.userbackend.models.LoginRequest;
import com.example.userbackend.models.LoginResponse;
import com.example.userbackend.models.SignupRequest;
import com.example.userbackend.models.SignupResponse;
import com.example.userbackend.repositories.UserRepository;
import com.example.userbackend.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public SignupResponse signupUser(@RequestBody  SignupRequest request) {
        return authService.attemptSignup(request);
    }

    @PostMapping("/login")
    public LoginResponse loginUser(@RequestBody LoginRequest request) {
        return authService.attemptLogin(request.getEmail(), request.getPassword());
    }
}
