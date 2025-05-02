package com.example.config;

import com.example.entity.Role;
import com.example.entity.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class AdminInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initAdminUser() {
        return args -> {
            // 检查管理员用户是否存在
            if (!userRepository.findByUsername("admin").isPresent()) {
                // 创建管理员用户实例
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setEmail("admin@example.com");
                admin.setEnabled(true);

                // 创建管理员角色并关联（直接创建新角色，依赖级联保存）
                Role adminRole = new Role(Role.ERole.ROLE_ADMIN);
                Set<Role> roles = new HashSet<>();
                roles.add(adminRole);
                admin.setRoles(roles);

                // 保存用户（级联保存角色和关联关系）
                userRepository.save(admin);

                System.out.println("✓ 管理员账户创建成功，用户名：admin");
            }
        };
    }
}
