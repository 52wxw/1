package com.example.controller;

import com.example.dto.request.TeamRequest;
import com.example.dto.response.ApiResponse;
import com.example.dto.response.TeamResponse;
import com.example.entity.Team;
import com.example.service.TeamService;
import com.example.security.CurrentUser;
import com.example.security.UserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TeamResponse> createTeam(
            @Valid @RequestBody TeamRequest request,
            @CurrentUser UserPrincipal currentUser
    ) {
        Team team = teamService.createTeam(request, currentUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new TeamResponse(team));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TeamResponse> getTeamById(@PathVariable Long id) {
        Team team = teamService.getTeamById(id);
        return ResponseEntity.ok(new TeamResponse(team));
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<TeamResponse>> getTeamsByUser(
            @CurrentUser UserPrincipal currentUser
    ) {
        List<Team> teams = teamService.getTeamsByUser(currentUser.getId());
        List<TeamResponse> responses = teams.stream()
                .map(TeamResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/{teamId}/members/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TeamResponse> addMember(
            @PathVariable Long teamId,
            @PathVariable Long userId,
            @CurrentUser UserPrincipal currentUser
    ) {
        // 只有队长或管理员可以添加成员
        Team team = teamService.addMember(teamId, userId, currentUser.getId());
        return ResponseEntity.ok(new TeamResponse(team));
    }

    @DeleteMapping("/{teamId}/members/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TeamResponse> removeMember(
            @PathVariable Long teamId,
            @PathVariable Long userId,
            @CurrentUser UserPrincipal currentUser
    ) {
        // 只有队长或管理员可以移除成员
        Team team = teamService.removeMember(teamId, userId, currentUser.getId());
        return ResponseEntity.ok(new TeamResponse(team));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TeamResponse> updateTeam(
            @PathVariable Long id,
            @Valid @RequestBody TeamRequest request,
            @CurrentUser UserPrincipal currentUser
    ) {
        // 只有队长或管理员可以更新团队
        Team team = teamService.updateTeam(id, request, currentUser.getId());
        return ResponseEntity.ok(new TeamResponse(team));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<?>> deleteTeam(
            @PathVariable Long id,
            @CurrentUser UserPrincipal currentUser
    ) {
        // 只有队长或管理员可以删除团队
        teamService.deleteTeam(id, currentUser.getId());
        return ResponseEntity.ok(new ApiResponse<>(true, "团队删除成功"));
    }
}
