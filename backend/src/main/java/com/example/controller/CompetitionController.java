package com.example.controller;

import com.example.dto.request.CompetitionRequest;
import com.example.dto.response.ApiResponse;
import com.example.dto.response.CompetitionResponse;
import com.example.entity.Competition;
import com.example.service.CompetitionService;
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
@RequestMapping("/api/competitions")
public class CompetitionController {

    private final CompetitionService competitionService;

    public CompetitionController(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CompetitionResponse> createCompetition(
            @Valid @RequestBody CompetitionRequest request,
            @CurrentUser UserPrincipal currentUser
    ) {
        Competition competition = competitionService.createCompetition(request, currentUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CompetitionResponse(competition));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CompetitionResponse> getCompetitionById(@PathVariable Long id) {
        Competition competition = competitionService.getCompetitionById(id);
        return ResponseEntity.ok(new CompetitionResponse(competition));
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<CompetitionResponse>> getAllCompetitions() {
        List<Competition> competitions = competitionService.getAllCompetitions();
        List<CompetitionResponse> responses = competitions.stream()
                .map(CompetitionResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/{competitionId}/participants/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CompetitionResponse> addParticipant(
            @PathVariable Long competitionId,
            @PathVariable Long userId,
            @CurrentUser UserPrincipal currentUser
    ) {
        Competition competition = competitionService.addParticipant(
                competitionId, userId, currentUser.getId()
        );
        return ResponseEntity.ok(new CompetitionResponse(competition));
    }

    @PostMapping("/{competitionId}/teams/{teamId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CompetitionResponse> addTeam(
            @PathVariable Long competitionId,
            @PathVariable Long teamId,
            @CurrentUser UserPrincipal currentUser
    ) {
        Competition competition = competitionService.addTeam(
                competitionId, teamId, currentUser.getId()
        );
        return ResponseEntity.ok(new CompetitionResponse(competition));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CompetitionResponse> updateCompetition(
            @PathVariable Long id,
            @Valid @RequestBody CompetitionRequest request,
            @CurrentUser UserPrincipal currentUser
    ) {
        Competition competition = competitionService.updateCompetition(
                id, request, currentUser.getId()
        );
        return ResponseEntity.ok(new CompetitionResponse(competition));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> deleteCompetition(@PathVariable Long id) {
        competitionService.deleteCompetition(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "竞赛删除成功"));
    }
}
