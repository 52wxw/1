package com.example.service;

import com.example.model.Ranking;

import java.util.List;

public interface RankingService {

    void updateRanking(String username);

    List<Ranking> getTopRankings(int limit);

    Ranking getRankingByUser(String username);

    void broadcastRankings();
}