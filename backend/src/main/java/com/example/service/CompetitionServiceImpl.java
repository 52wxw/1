package com.example.service;

import com.example.dto.request.CompetitionRequest;
import com.example.dto.response.CompetitionResponse;
import com.example.entity.Competition;
import com.example.entity.Team;
import com.example.entity.User;
import com.example.repository.CompetitionRepository;
import com.example.repository.TeamRepository;
import com.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CompetitionServiceImpl implements CompetitionService {

    private final CompetitionRepository competitionRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    public CompetitionServiceImpl(
        CompetitionRepository competitionRepository,
        UserRepository userRepository,
        TeamRepository teamRepository
    ) {
        this.competitionRepository = competitionRepository;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public CompetitionResponse createCompetition(CompetitionRequest request, User organizer) {
        if (existsByName(request.name())) {
            throw new IllegalArgumentException("竞赛名称已存在");
        }

        if (request.startTime().isAfter(request.endTime())) {
            throw new IllegalArgumentException("开始时间不能晚于结束时间");
        }

        Competition competition = Competition.builder()
            .name(request.name())
            .description(request.description())
            .startTime(request.startTime())
            .endTime(request.endTime())
            .rules(request.rules())
            .organizer(organizer)
            .maxParticipants(request.maxParticipants())
            .build();

        Competition savedCompetition = competitionRepository.save(competition);
        return new CompetitionResponse(savedCompetition, null);
    }

    @Override
    public Optional<Competition> getCompetitionById(Long id) {
        return competitionRepository.findById(id);
    }

    @Override
    public CompetitionResponse getCompetitionResponseById(Long id, Long currentUserId) {
        Competition competition = getCompetitionById(id)
            .orElseThrow(() -> new IllegalArgumentException("竞赛不存在"));
        return new CompetitionResponse(competition, currentUserId);
    }

    @Override
    public Optional<Competition> getCompetitionByName(String name) {
        return competitionRepository.findByName(name);
    }

    @Override
    public List<CompetitionResponse> getAllCompetitions(Long currentUserId) {
        return competitionRepository.findAll().stream()
            .map(competition -> new CompetitionResponse(competition, currentUserId))
            .collect(Collectors.toList());
    }

    @Override
    public List<CompetitionResponse> getCompetitionsOrganizedByUser(User organizer, Long currentUserId) {
        return competitionRepository.findByOrganizer(organizer).stream()
            .map(competition -> new CompetitionResponse(competition, currentUserId))
            .collect(Collectors.toList());
    }

    @Override
    public List<CompetitionResponse> getCompetitionsUserParticipated(User participant, Long currentUserId) {
        return competitionRepository.findByParticipantsContaining(participant).stream()
            .map(competition -> new CompetitionResponse(competition, currentUserId))
            .collect(Collectors.toList());
    }

    @Override
    public List<CompetitionResponse> getCompetitionsByTeam(Team team, Long currentUserId) {
        return competitionRepository.findByTeamsContaining(team).stream()
            .map(competition -> new CompetitionResponse(competition, currentUserId))
            .collect(Collectors.toList());
    }

    @Override
    public List<CompetitionResponse> getUpcomingCompetitions(Long currentUserId) {
        return competitionRepository.findByStartTimeAfter(LocalDateTime.now()).stream()
            .map(competition -> new CompetitionResponse(competition, currentUserId))
            .collect(Collectors.toList());
    }

    @Override
    public List<CompetitionResponse> getPastCompetitions(Long currentUserId) {
        return competitionRepository.findByEndTimeBefore(LocalDateTime.now()).stream()
            .map(competition -> new CompetitionResponse(competition, currentUserId))
            .collect(Collectors.toList());
    }

    @Override
    public List<CompetitionResponse> getActiveCompetitions(Long currentUserId) {
        return competitionRepository.findByIsActive(true).stream()
            .map(competition -> new CompetitionResponse(competition, currentUserId))
            .collect(Collectors.toList());
    }

    @Override
    public CompetitionResponse updateCompetition(Long id, CompetitionRequest request, Long currentUserId) {
        Competition competition = getCompetitionById(id)
            .orElseThrow(() -> new IllegalArgumentException("竞赛不存在"));

        if (!competition.getOrganizer().getId().equals(currentUserId)) {
            throw new IllegalStateException("只有竞赛组织者可以更新竞赛信息");
        }

        if (request.name() != null && !request.name().equals(competition.getName())) {
            if (existsByName(request.name())) {
                throw new IllegalArgumentException("竞赛名称已存在");
            }
            competition.setName(request.name());
        }

        if (request.description() != null) {
            competition.setDescription(request.description());
        }

        if (request.startTime() != null) {
            if (request.startTime().isAfter(request.endTime() != null ? request.endTime() : competition.getEndTime())) {
                throw new IllegalArgumentException("开始时间不能晚于结束时间");
            }
            competition.setStartTime(request.startTime());
        }

        if (request.endTime() != null) {
            if (request.endTime().isBefore(request.startTime() != null ? request.startTime() : competition.getStartTime())) {
                throw new IllegalArgumentException("结束时间不能早于开始时间");
            }
            competition.setEndTime(request.endTime());
        }

        if (request.rules() != null) {
            competition.setRules(request.rules());
        }

        if (request.maxParticipants() != null) {
            competition.setMaxParticipants(request.maxParticipants());
        }

        Competition updatedCompetition = competitionRepository.save(competition);
        return new CompetitionResponse(updatedCompetition, currentUserId);
    }

    @Override
    public void addParticipant(Long competitionId, Long participantId, Long currentUserId) {
        Competition competition = getCompetitionById(competitionId)
            .orElseThrow(() -> new IllegalArgumentException("竞赛不存在"));

        if (!competition.getOrganizer().getId().equals(currentUserId)) {
            throw new IllegalStateException("只有竞赛组织者可以添加参与者");
        }

        User participant = userRepository.findById(participantId)
            .orElseThrow(() -> new IllegalArgumentException("用户不存在"));

        if (competition.getParticipants().size() >= competition.getMaxParticipants()) {
            throw new IllegalStateException("竞赛已达到最大参与者数量");
        }

        competition.addParticipant(participant);
        competitionRepository.save(competition);
    }

    @Override
    public void removeParticipant(Long competitionId, Long participantId, Long currentUserId) {
        Competition competition = getCompetitionById(competitionId)
            .orElseThrow(() -> new IllegalArgumentException("竞赛不存在"));

        if (!competition.getOrganizer().getId().equals(currentUserId) && !participantId.equals(currentUserId)) {
            throw new IllegalStateException("只有竞赛组织者或参与者本人可以移除参与者");
        }

        User participant = userRepository.findById(participantId)
            .orElseThrow(() -> new IllegalArgumentException("用户不存在"));

        competition.removeParticipant(participant);
        competitionRepository.save(competition);
    }

    @Override
    public void addTeam(Long competitionId, Long teamId, Long currentUserId) {
        Competition competition = getCompetitionById(competitionId)
            .orElseThrow(() -> new IllegalArgumentException("竞赛不存在"));

        if (!competition.getOrganizer().getId().equals(currentUserId)) {
            throw new IllegalStateException("只有竞赛组织者可以添加团队");
        }

        Team team = teamRepository.findById(teamId)
            .orElseThrow(() -> new IllegalArgumentException("团队不存在"));

        if (competition.getTeams().contains(team)) {
            throw new IllegalArgumentException("团队已加入此竞赛");
        }

        competition.addTeam(team);
        competitionRepository.save(competition);
    }

    @Override
    public void removeTeam(Long competitionId, Long teamId, Long currentUserId) {
        Competition competition = getCompetitionById(competitionId)
            .orElseThrow(() -> new IllegalArgumentException("竞赛不存在"));

        if (!competition.getOrganizer().getId().equals(currentUserId)) {
            throw new IllegalStateException("只有竞赛组织者可以移除团队");
        }

        Team team = teamRepository.findById(teamId)
            .orElseThrow(() -> new IllegalArgumentException("团队不存在"));

        competition.removeTeam(team);
        competitionRepository.save(competition);
    }

    @Override
    public void deactivateCompetition(Long id, Long currentUserId) {
        Competition competition = getCompetitionById(id)
            .orElseThrow(() -> new IllegalArgumentException("竞赛不存在"));

        if (!competition.getOrganizer().getId().equals(currentUserId)) {
            throw new IllegalStateException("只有竞赛组织者可以停用竞赛");
        }

        competition.setActive(false);
        competitionRepository.save(competition);
    }

    @Override
    public boolean existsByName(String name) {
        return competitionRepository.existsByName(name);
    }
}
