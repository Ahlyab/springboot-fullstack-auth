package com.example.userbackend.services;

import com.example.userbackend.entities.User;
import com.example.userbackend.models.LoginResponse;
import com.example.userbackend.models.ResponseUserObject;
import com.example.userbackend.models.SignupRequest;
import com.example.userbackend.models.SignupResponse;
import com.example.userbackend.repositories.UserRepository;
import com.example.userbackend.security.JwtIssuer;
import com.example.userbackend.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    public LoginResponse attemptLogin(String email, String password) {

        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );


        SecurityContextHolder.getContext().setAuthentication(authentication);
        var principal = (UserPrincipal) authentication.getPrincipal();
        var roles = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        String token = jwtIssuer.issue(principal.getUserId(), principal.getEmail(), roles);
        Optional<User> user = userRepository.findByEmail(email);

        if(user.isEmpty()) {
            return LoginResponse.builder().build();

        }
        return LoginResponse.builder().accessToken(token)
                    .status(200)
                    .message("user logged in")
                    .user(ResponseUserObject.builder()
                            .firstName(user.get().getFirstName())
                            .email(email)
                            .lastName(user.get().getLastName())
                            .role(user.get().getRole())
                            .id(user.get().getId())
                            .build())
                    .build();
    }


    public SignupResponse attemptSignup(SignupRequest request) {
        try{
            User user = modelMapper.map(request, User.class);
            user.setPassword(passwordEncoder.encode(request.getPassword()));

            if(userRepository.findByEmail(request.getEmail()).isPresent()) {
                return SignupResponse.builder().status(500).message("Email Already used").build();

            }


            User res = userRepository.save(user);
            return SignupResponse.builder().message("User Signed Up Successfully").status(201)
                    .user(ResponseUserObject.builder()
                            .id(res.getId())
                            .role(res.getRole())
                            .lastName(res.getLastName())
                            .firstName(res.getFirstName())
                            .email(res.getEmail())
                            .build()).build();
        }catch (Exception ex) {
            return SignupResponse.builder().status(500).message(ex.getMessage()).build();
        }

    }

}
