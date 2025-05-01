package com.example.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import com.example.utils.JwtUtil;

import java.io.IOException;
import java.util.Collections;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 修正：Spring Security 6.x 禁用 CSRF 的正确方式
            .csrf(csrf -> csrf.disable()) 
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter(), BasicAuthenticationFilter.class);

        return http.build();
    }

    private Filter jwtAuthenticationFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(
                HttpServletRequest request,
                HttpServletResponse response,
                FilterChain filterChain
            ) throws ServletException, IOException {
                String tokenHeader = request.getHeader("Authorization");
                if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
                    String token = tokenHeader.substring(7);
                    if (JwtUtil.validateToken(token)) {
                        var claims = JwtUtil.parseToken(token);
                        String username = claims.getSubject();
                        Integer role = (Integer) claims.get("role");

                        Authentication authentication = new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            Collections.singletonList(() -> "ROLE_" + role)
                        );
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
                filterChain.doFilter(request, response);
            }
        };
    }
}
