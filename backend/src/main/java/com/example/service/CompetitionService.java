package com.example.service;

import com.example.dto.request.CompetitionRequest;
import com.example.dto.response.CompetitionResponse;
import com.example.entity.Competition;
import com.example.entity.Team;
import com.example.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CompetitionService {

    CompetitionResponse createCompetition(CompetitionRequest request, User organizer);

    Optional<Competition> getCompetitionById(Long id);

    CompetitionResponse getCompetitionResponseById(Long id, Long currentUserId);

    Optional<Competition> getCompetitionByName(String name);

    List<CompetitionResponse> getAllCompetitions(Long currentUserId);

    List<CompetitionResponse> getCompetitionsOrganizedByUser(User organizer, Long currentUserId);

    List<CompetitionResponse> getCompetitionsUserParticipated(User participant, Long currentUserId);

    List<CompetitionResponse> getCompetitionsByTeam(Team team, Long currentUserId);

    List<CompetitionResponse> getUpcomingCompetitions(Long currentUserId);

    List<CompetitionResponse> getPastCompetitions(Long currentUserId);

    List<CompetitionResponse> getActiveCompetitions(Long currentUserId);

    CompetitionResponse updateCompetition(Long id, CompetitionRequest request, Long currentUserId);

    void addParticipant(Long competitionId, Long participantId, Long currentUserId);

    void removeParticipant(Long competitionId, Long participantId, Long currentUserId);

    void addTeam(Long competitionId, Long teamId, Long currentUserId);

    void removeTeam(Long competitionId, Long teamId, Long currentUserId);

    void deactivateCompetition(Long id, Long currentUserId);

    boolean existsByName(String name);
}
