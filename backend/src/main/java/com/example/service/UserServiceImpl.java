package com.example.service;

import com.example.dto.request.UserRequest;
import com.example.dto.response.UserResponse;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse createUser(UserRequest request) {
        if (existsByUsername(request.username())) {
            throw new IllegalArgumentException("用户名已存在");
        }
        if (existsByEmail(request.email())) {
            throw new IllegalArgumentException("邮箱已存在");
        }

        User user = User.builder()
            .username(request.username())
            .email(request.email())
            .password(passwordEncoder.encode(request.password()))
            .avatarUrl(request.avatarUrl())
            .bio(request.bio())
            .roles(Collections.singleton(Role.USER))
            .build();

        User savedUser = userRepository.save(user);
        return new UserResponse(savedUser);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public UserResponse getUserResponseById(Long id) {
        return getUserById(id)
            .map(UserResponse::new)
            .orElseThrow(() -> new IllegalArgumentException("用户不存在"));
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
            .map(UserResponse::new)
            .collect(Collectors.toList());
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest request) {
        User user = getUserById(id)
            .orElseThrow(() -> new IllegalArgumentException("用户不存在"));

        if (request.username() != null && !request.username().equals(user.getUsername())) {
            if (existsByUsername(request.username())) {
                throw new IllegalArgumentException("用户名已存在");
            }
            user.setUsername(request.username());
        }

        if (request.email() != null && !request.email().equals(user.getEmail())) {
            if (existsByEmail(request.email())) {
                throw new IllegalArgumentException("邮箱已存在");
            }
            user.setEmail(request.email());
        }

        if (request.password() != null) {
            user.setPassword(passwordEncoder.encode(request.password()));
        }

        if (request.avatarUrl() != null) {
            user.setAvatarUrl(request.avatarUrl());
        }

        if (request.bio() != null) {
            user.setBio(request.bio());
        }

        User updatedUser = userRepository.save(user);
        return new UserResponse(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("用户不存在");
        }
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("用户不存在: " + username));
    }
}
