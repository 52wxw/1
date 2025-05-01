package com.example.service;

import com.example.dto.request.UserRequest;
import com.example.dto.response.UserResponse;
import com.example.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    UserResponse createUser(UserRequest request);

    Optional<User> getUserById(Long id);

    UserResponse getUserResponseById(Long id);

    Optional<User> getUserByUsername(String username);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(Long id, UserRequest request);

    void deleteUser(Long id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
