package com.example.repository;

import com.example.entity.Competition;
import com.example.entity.User;
import com.example.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long> {

    Optional<Competition> findByName(String name);

    List<Competition> findByOrganizer(User organizer);

    List<Competition> findByParticipantsContaining(User participant);

    List<Competition> findByTeamsContaining(Team team);

    List<Competition> findByStartTimeAfter(LocalDateTime startTime);

    List<Competition> findByEndTimeBefore(LocalDateTime endTime);

    List<Competition> findByIsActive(Boolean isActive);

    boolean existsByName(String name);
}
