package com.example.controller;

import com.example.dto.AuthRequest;
import com.example.service.impl.UserDetailsServiceImpl;
import org.springframework.security.core.Authentication; 
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.dto.AuthResponse;
import com.example.dto.LoginRequest;
import com.example.entity.User;
import com.example.repository.UserRepository;
import com.example.service.impl.UserDetailsServiceImpl;
import com.example.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager,
                          UserDetailsServiceImpl userDetailsService,
                          JwtUtil jwtUtil,
                          UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

    // 从 UserDetails 中获取用户名
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();  // 关键修正

    // 生成 JWT 时使用用户名
        String jwt = jwtUtil.generateToken(username);
	User user = userRepository.findByUsername(username).orElse(null);

        return ResponseEntity.ok(new AuthResponse(jwt,user));
    }  


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        // 注册逻辑
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }
}    
