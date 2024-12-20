package com.example.userbackend.services;


import com.example.userbackend.dto.UserDTO;
import com.example.userbackend.entities.User;
import com.example.userbackend.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    final UserRepository userRepository;
    final ModelMapper modelMapper;

    final BCryptPasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO createNewUser(UserDTO userDTO) {

        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return modelMapper.map(userRepository.save(user), UserDTO.class);
    }

    public Optional<UserDTO> getUserById(String id) {
        Optional<User> user =  userRepository.findById(id);
        if(user.isPresent()) {
            UserDTO res = modelMapper.map(user.get(), UserDTO.class);
            res.setPassword(null);
            return Optional.of(res);
        }
        return null;

    }

    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    public UserDTO update (UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        return modelMapper.map(userRepository.save(user), UserDTO.class);
    }

    public Optional<User> findByEmail(String email) {
        // Todo: move this to database
        return userRepository.findByEmail(email);
    }
}
