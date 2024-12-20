package com.example.userbackend.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.example.userbackend.dto.UserDTO;
import com.example.userbackend.entities.User;
import com.example.userbackend.exceptions.RecordNotFoundException;
import com.example.userbackend.models.UserUpdate;
import com.example.userbackend.security.UserPrincipal;
import com.example.userbackend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;
//    private final UserPrincipal userPrincipal;

    @GetMapping("/{id}")
    public Optional<UserDTO> getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PutMapping("/update")
    public UserDTO updateUserProfile(Authentication authentication, @RequestBody UserUpdate userUpdate) {

        UserPrincipal  userPrincipal = (UserPrincipal) authentication.getPrincipal();
        System.out.println(userPrincipal.getUserId());
        System.out.println(userPrincipal.getEmail());


        try{
            UserDTO user =  userService.getUserById(userPrincipal.getUserId()).orElseThrow( () -> new RecordNotFoundException("User Id Invalid"));
            if(userUpdate.getFirstName() != null) {
                user.setFirstName(userUpdate.getFirstName());
            }

            if(userUpdate.getLastName() != null) {
                user.setLastName(userUpdate.getLastName());
            }

            if(userUpdate.getEmail() != null) {
                user.setEmail(userUpdate.getEmail());
            }

            Optional<User> result= userService.findByEmail(userUpdate.getEmail());
            result.ifPresent(value -> user.setPassword(value.getPassword()));
            return userService.update(user);
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
