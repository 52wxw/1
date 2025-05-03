package com.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false, unique = true)
    private ERole name; // 内部枚举

    // 定义内部枚举（包含所有角色）
    public enum ERole {
	     ROLE_ADMIN,
    ROLE_MODERATOR,
    ROLE_USER
    }

    // 构造方法
    public Role() {}
    public Role(ERole name) { this.name = name; }

    // Getters/Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public ERole getName() { return name; }
    public void setName(ERole name) { this.name = name; }
}
