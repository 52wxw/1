package com.example.service;

import com.example.dto.request.TeamRequest;
import com.example.dto.response.TeamResponse;
import com.example.entity.Team;
import com.example.entity.User;
import com.example.repository.TeamRepository;
import com.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public TeamServiceImpl(TeamRepository teamRepository, UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TeamResponse createTeam(TeamRequest request, User leader) {
        if (existsByName(request.name())) {
            throw new IllegalArgumentException("团队名称已存在");
        }

        Team team = Team.builder()
            .name(request.name())
            .description(request.description())
            .leader(leader)
            .build();

        Team savedTeam = teamRepository.save(team);
        return new TeamResponse(savedTeam, null); // null表示当前用户ID，创建时不需要校验
    }

    @Override
    public Optional<Team> getTeamById(Long id) {
        return teamRepository.findById(id);
    }

    @Override
    public TeamResponse getTeamResponseById(Long id, Long currentUserId) {
        Team team = getTeamById(id)
            .orElseThrow(() -> new IllegalArgumentException("团队不存在"));
        return new TeamResponse(team, currentUserId);
    }

    @Override
    public Optional<Team> getTeamByName(String name) {
        return teamRepository.findByName(name);
    }

    @Override
    public List<TeamResponse> getAllTeams(Long currentUserId) {
        return teamRepository.findAll().stream()
            .map(team -> new TeamResponse(team, currentUserId))
            .collect(Collectors.toList());
    }

    @Override
    public List<TeamResponse> getTeamsLedByUser(User leader, Long currentUserId) {
        return teamRepository.findByLeader(leader).stream()
            .map(team -> new TeamResponse(team, currentUserId))
            .collect(Collectors.toList());
    }

    @Override
    public List<TeamResponse> getTeamsUserJoined(User member, Long currentUserId) {
        return teamRepository.findByMembersContaining(member).stream()
            .map(team -> new TeamResponse(team, currentUserId))
            .collect(Collectors.toList());
    }

    @Override
    public TeamResponse updateTeam(Long id, TeamRequest request, Long currentUserId) {
        Team team = getTeamById(id)
            .orElseThrow(() -> new IllegalArgumentException("团队不存在"));

        if (!team.getLeader().getId().equals(currentUserId)) {
            throw new IllegalStateException("只有团队领导可以更新团队信息");
        }

        if (request.name() != null && !request.name().equals(team.getName())) {
            if (existsByName(request.name())) {
                throw new IllegalArgumentException("团队名称已存在");
            }
            team.setName(request.name());
        }

        if (request.description() != null) {
            team.setDescription(request.description());
        }

        Team updatedTeam = teamRepository.save(team);
        return new TeamResponse(updatedTeam, currentUserId);
    }

    @Override
    public void addMember(Long teamId, Long memberId, Long currentUserId) {
        Team team = getTeamById(teamId)
            .orElseThrow(() -> new IllegalArgumentException("团队不存在"));

        if (!team.getLeader().getId().equals(currentUserId)) {
            throw new IllegalStateException("只有团队领导可以添加成员");
        }

        User member = userRepository.findById(memberId)
            .orElseThrow(() -> new IllegalArgumentException("用户不存在"));

        team.addMember(member);
        teamRepository.save(team);
    }

    @Override
    public void removeMember(Long teamId, Long memberId, Long currentUserId) {
        Team team = getTeamById(teamId)
            .orElseThrow(() -> new IllegalArgumentException("团队不存在"));

        if (!team.getLeader().getId().equals(currentUserId) && !memberId.equals(currentUserId)) {
            throw new IllegalStateException("只有团队领导或成员本人可以移除成员");
        }

        User member = userRepository.findById(memberId)
            .orElseThrow(() -> new IllegalArgumentException("用户不存在"));

        team.removeMember(member);
        teamRepository.save(team);
    }

    @Override
    public void deleteTeam(Long id, Long currentUserId) {
        Team team = getTeamById(id)
            .orElseThrow(() -> new IllegalArgumentException("团队不存在"));

        if (!team.getLeader().getId().equals(currentUserId)) {
            throw new IllegalStateException("只有团队领导可以删除团队");
        }

        teamRepository.delete(team);
    }

    @Override
    public boolean existsByName(String name) {
        return teamRepository.existsByName(name);
    }
}
