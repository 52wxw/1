package com.example.repository;

import com.example.entity.Team;
import com.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByName(String name);

    List<Team> findByLeader(User leader);

    List<Team> findByMembersContaining(User member);

    boolean existsByName(String name);
}
