package com.example.repository;

import com.example.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    // 查找角色（返回 Optional）
    Optional<Role> findByName(Role.ERole name);
    
    // 检查角色是否存在（返回 boolean）
    boolean existsByName(Role.ERole name);
}
