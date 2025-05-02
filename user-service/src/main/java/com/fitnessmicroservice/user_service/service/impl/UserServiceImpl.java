package com.fitnessmicroservice.user_service.service.impl;

import java.util.List;

import com.fitnessmicroservice.user_service.config.JwtService;
import com.fitnessmicroservice.user_service.dtos.AuthenticationRequest;
import com.fitnessmicroservice.user_service.dtos.AuthenticationResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fitnessmicroservice.user_service.dtos.RegisterUserDto;
import com.fitnessmicroservice.user_service.exceptions.AuthenticationException;
import com.fitnessmicroservice.user_service.exceptions.ResourceNotFoundException;
import com.fitnessmicroservice.user_service.models.User;
import com.fitnessmicroservice.user_service.repository.UserRepository;
import com.fitnessmicroservice.user_service.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public User createUser(RegisterUserDto user) {
        if(userRepository.existsByEmail(user.getEmail())){
            throw new IllegalArgumentException("Email already exists");
        } 
        User newUser = User.builder()
            .name(user.getName())
            .email(user.getEmail())
            .password(passwordEncoder.encode(user.getPassword()))
            .build();

        return userRepository.save(newUser);
    }

    @Override
    public User getUserById(Integer userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User with ID not found"));
    }

    @Override 
    public void deleteUser(Integer userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User with ID not found"));

        userRepository.delete(user);
    }

    @Override
    public User updateUser(Integer userId, RegisterUserDto user) {
        User existingUser = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User with ID not found"));

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());

        return userRepository.save(existingUser);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            // Authenticate the user
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
                )
            );

            // Retrieve user from repository
            User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

            // Generate JWT token
            String jwtToken = jwtService.generateToken(user);

            // Return user and token
            return AuthenticationResponse.builder()
                .token(jwtToken)
                .message("Authentication successful")
                .username(user.getEmail())
                .build();

        } catch (BadCredentialsException e) {
            // Handle incorrect email or password
            throw new AuthenticationException("Invalid email or password");
        }
    }
}
