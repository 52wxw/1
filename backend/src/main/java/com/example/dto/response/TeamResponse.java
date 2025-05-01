package com.example.dto.response;

import com.example.entity.Team;

import java.util.List;
import java.util.stream.Collectors;

public record TeamResponse(
    Long id,
    String name,
    String description,
    UserResponse leader,
    List<UserResponse> members,
    Integer memberCount,
    Boolean isUserJoined
) {
    public TeamResponse(Team team, Long currentUserId) {
        this(
            team.getId(),
            team.getName(),
            team.getDescription(),
            new UserResponse(team.getLeader(), currentUserId), // 传入 currentUserId
            team.getMembers().stream()
                .map(user -> new UserResponse(user, currentUserId)) // 修正此处
                .collect(Collectors.toList()),
            team.getMembers().size(),
            team.getMembers().contains(userService.getUserById(currentUserId)) // 需要注入 userService
        );
    }
}
