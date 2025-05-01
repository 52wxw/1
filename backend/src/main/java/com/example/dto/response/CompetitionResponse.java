package com.example.dto.response;

import com.example.entity.Competition;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record CompetitionResponse(
    Long id,
    String name,
    String description,
    LocalDateTime startTime,
    LocalDateTime endTime,
    String rules,
    UserResponse organizer,
    List<UserResponse> participants,
    List<TeamResponse> teams,
    Integer participantCount,
    Integer teamCount,
    Boolean isParticipating,
    Boolean isOrganizer
) {
    // 从Competition实体转换
    public CompetitionResponse(Competition competition, Long currentUserId) {
        this(
            competition.getId(),
            competition.getName(),
            competition.getDescription(),
            competition.getStartTime(),
            competition.getEndTime(),
            competition.getRules(),
            new UserResponse(competition.getOrganizer()),
            competition.getParticipants().stream()
                .map(UserResponse::new)
                .collect(Collectors.toList()),
            competition.getTeams().stream()
                .map(team -> new TeamResponse(team, currentUserId))
                .collect(Collectors.toList()),
            competition.getParticipants().size(),
            competition.getTeams().size(),
            competition.getParticipants().stream()
                .anyMatch(user -> user.getId().equals(currentUserId)),
            competition.getOrganizer().getId().equals(currentUserId)
        );
    }
}
