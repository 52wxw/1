package com.example.controller;

import com.example.common.Result;
import com.example.entity.Team;
import com.example.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/list")
    public Result listTeams() {
        List<Team> teams = teamService.listTeams();
        return Result.success(teams);
    }

    @PostMapping("/create")
    public Result createTeam(@RequestBody Team team) {
        Team newTeam = teamService.createTeam(team);
        if (newTeam != null) {
            return Result.success(newTeam);
        } else {
            return Result.error("创建失败");
        }
    }

    @PostMapping("/join")
    public Result joinTeam(@RequestParam Long teamId, @RequestParam Long userId) {
        boolean success = teamService.joinTeam(teamId, userId);
        if (success) {
            return Result.success();
        } else {
            return Result.error("加入失败");
        }
    }
}    