package com.example.service;

import com.example.entity.User;

import java.util.List;

public interface UserService {

    User login(String username, String password);

    User register(User user);

    User getUserById(Long userId);

    User updateUser(User user);

    List<User> listUsers();

    boolean banUser(Long userId);
}
