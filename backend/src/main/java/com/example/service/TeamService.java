package com.example.service;

import com.example.dto.request.TeamRequest;
import com.example.dto.response.TeamResponse;
import com.example.entity.Team;
import com.example.entity.User;

import java.util.List;
import java.util.Optional;

public interface TeamService {

    TeamResponse createTeam(TeamRequest request, User leader);

    Optional<Team> getTeamById(Long id);

    TeamResponse getTeamResponseById(Long id, Long currentUserId);

    Optional<Team> getTeamByName(String name);

    List<TeamResponse> getAllTeams(Long currentUserId);

    List<TeamResponse> getTeamsLedByUser(User leader, Long currentUserId);

    List<TeamResponse> getTeamsUserJoined(User member, Long currentUserId);

    TeamResponse updateTeam(Long id, TeamRequest request, Long currentUserId);

    void addMember(Long teamId, Long memberId, Long currentUserId);

    void removeMember(Long teamId, Long memberId, Long currentUserId);

    void deleteTeam(Long id, Long currentUserId);

    boolean existsByName(String name);
}
