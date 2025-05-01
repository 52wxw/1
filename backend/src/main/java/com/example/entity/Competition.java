package com.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "competitions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Competition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(columnDefinition = "TEXT")
    private String rules;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id", nullable = false)
    private User organizer;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "competition_participants",
        joinColumns = @JoinColumn(name = "competition_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> participants = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "competition_teams",
        joinColumns = @JoinColumn(name = "competition_id"),
        inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private List<Team> teams = new ArrayList<>();

    @Column(nullable = false)
    private Integer maxParticipants;

    @Column(nullable = false)
    private Boolean isActive;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        isActive = true;
    }

    // 添加参与者
    public void addParticipant(User user) {
        this.participants.add(user);
    }

    // 移除参与者
    public void removeParticipant(User user) {
        this.participants.remove(user);
    }

    // 添加团队
    public void addTeam(Team team) {
        this.teams.add(team);
    }

    // 移除团队
    public void removeTeam(Team team) {
        this.teams.remove(team);
    }
}
