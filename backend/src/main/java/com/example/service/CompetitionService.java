package com.example.service;

import com.example.entity.Competition;

import java.util.List;

public interface CompetitionService {

    List<Competition> listCompetitions();

    Competition getCompetitionById(Long id);

    boolean participateCompetition(Long competitionId, Long userId);
}    