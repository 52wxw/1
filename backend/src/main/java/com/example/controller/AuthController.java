package com.example.controller;

import com.example.common.Result;
import com.example.entity.User;
import com.example.service.UserService;
import com.example.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");

        User user = userService.login(username, password);
        if (user != null) {
            // 修正：传递正确的参数（Long, String, Integer）
            String token = JwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole()); 
            Map<String, Object> result = new HashMap<>();
            result.put("user", user);
            result.put("token", token);
            result.put("role", user.getRole());
            return Result.success(result);
        } else {
            return Result.error("用户名或密码错误");
        }
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        User newUser = userService.register(user);
        if (newUser != null) {
            return Result.success(newUser);
        } else {
            return Result.error("注册失败，用户名已存在");
        }
    }

    @PostMapping("/logout")
    public Result logout(@RequestHeader("Authorization") String token) {
        return Result.success();
    }
}
