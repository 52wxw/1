package com.example.dto.response;

import com.example.entity.Role;
import com.example.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String avatarUrl;
    private String bio;
    private List<String> roles;
    private Boolean isCurrentUser;

    // 新增构造器：从 User 实体和当前用户 ID 构建
    public UserResponse(User user, Long currentUserId) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.avatarUrl = user.getAvatarUrl();
        this.bio = user.getBio();
        this.roles = user.getRoles().stream()
                .map(Role::name)
                .collect(Collectors.toList());
        this.isCurrentUser = user.getId().equals(currentUserId);
    }
}
