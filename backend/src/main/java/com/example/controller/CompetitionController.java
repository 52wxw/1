package com.example.controller;

import com.example.common.Result;
import com.example.entity.Competition;
import com.example.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/competition")
public class CompetitionController {

    @Autowired
    private CompetitionService competitionService;

    @GetMapping("/list")
    public Result listCompetitions() {
        List<Competition> competitions = competitionService.listCompetitions();
        return Result.success(competitions);
    }

    @GetMapping("/{id}")
    public Result getCompetition(@PathVariable Long id) {
        Competition competition = competitionService.getCompetitionById(id);
        if (competition != null) {
            return Result.success(competition);
        } else {
            return Result.error("竞赛不存在");
        }
    }

    @PostMapping("/participate")
    public Result participateCompetition(@RequestParam Long competitionId, @RequestParam Long userId) {
        boolean success = competitionService.participateCompetition(competitionId, userId);
        if (success) {
            return Result.success();
        } else {
            return Result.error("参与失败");
        }
    }
}    