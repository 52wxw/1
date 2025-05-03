package com.example.service;

import com.example.entity.User;
import com.example.dto.UserRegistrationDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(UserRegistrationDto registrationDto);
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    User updateUser(Long id, User userDetails);
    Optional<User> getUserByUsername(String username);
    void deleteUser(Long id);
}    
