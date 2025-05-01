package com.example.repository;

import com.example.model.Ranking;
import com.example.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RankingRepository extends JpaRepository<Ranking, Long> {

    Optional<Ranking> findByUser(User user);

    @Query("SELECT r FROM Ranking r ORDER BY r.score DESC, r.solvedTasks DESC, r.lastUpdate ASC")
    List<Ranking> findTop20ByOrderByScoreDescSolvedTasksDescLastUpdateAsc();

    @Query("SELECT r FROM Ranking r WHERE r.user.username = :username")
    Optional<Ranking> findByUsername(@Param("username") String username);
}
