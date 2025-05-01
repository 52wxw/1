package com.example.service;

import com.example.entity.Team;

import java.util.List;

public interface TeamService {

    List<Team> listTeams();

    Team createTeam(Team team);

    boolean joinTeam(Long teamId, Long userId);
}    