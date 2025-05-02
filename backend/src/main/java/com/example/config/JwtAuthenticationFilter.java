package com.example.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

// 注意：使用 javax.servlet 还是 jakarta.servlet 取决于你的 Spring Boot 版本
// 如果你使用的是 Spring Boot 3.x，应该使用 jakarta.servlet
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Base64; // 添加这一行
import org.springframework.stereotype.Component; // 添加这一行

@Component

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        username = extractUsername(jwt);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new java.util.Date());
    }

    private Key getSignInKey() {
        // 使用 Java 标准库的 Base64 解码器替代 JJWT 的 Decoders.BASE64_URL
        // 处理 URL 安全的 Base64 编码（替换 - 和 _ 字符）
        String standardBase64 = jwtSecret.replace('-', '+').replace('_', '/');
        // 计算填充字符 '=' 的数量
        int padding = 4 - (standardBase64.length() % 4);
        if (padding < 4) {
            standardBase64 = standardBase64 + "=".repeat(padding);
        }
        // 使用 Java 标准库解码
        byte[] keyBytes = Base64.getDecoder().decode(standardBase64);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
