package com.example.controller;

import com.example.dto.request.LoginRequest;
import com.example.dto.request.RegisterRequest;
import com.example.dto.response.ApiResponse;
import com.example.dto.response.JwtAuthenticationResponse;
import com.example.entity.User;
import com.example.security.UserPrincipal;
import com.example.service.UserService;
import com.example.config.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider tokenProvider;

    public AuthController(AuthenticationManager authenticationManager, 
                          UserService userService, 
                          JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        // 验证用户名密码
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        // 设置认证到上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 生成JWT令牌
        String jwt = tokenProvider.generateToken(authentication);
        
        // 返回响应
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        // 检查用户名是否已存在
        if (userService.existsByUsername(registerRequest.getUsername())) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "用户名已存在"), 
                    HttpStatus.BAD_REQUEST
            );
        }

        // 创建用户
        User user = userService.registerUser(registerRequest);

        // 构建创建成功的响应
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(user.getUsername()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "用户注册成功"));
    }
}
