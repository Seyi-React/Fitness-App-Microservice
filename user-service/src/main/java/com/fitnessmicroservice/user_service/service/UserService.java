package com.fitnessmicroservice.user_service.service;

import java.util.List;

import com.fitnessmicroservice.user_service.dtos.RegisterUserDto;
import com.fitnessmicroservice.user_service.models.User;

public interface UserService {
    User createUser(RegisterUserDto user);
    User getUserById(Integer userId);
    void deleteUser(Integer userId);
    User updateUser(Integer userId, RegisterUserDto user);
    List<User> getAllUsers();
}
