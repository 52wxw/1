package com.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teams")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_id", nullable = false)
    private User leader;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "team_members",
        joinColumns = @JoinColumn(name = "team_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> members = new ArrayList<>();

    @ManyToMany(mappedBy = "teams")
    private List<Competition> competitions = new ArrayList<>();

    @Column(nullable = false)
    private Boolean isActive;

    @Column(nullable = false)
    private java.time.LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = java.time.LocalDateTime.now();
        isActive = true;
    }

    // 添加成员
    public void addMember(User user) {
        this.members.add(user);
    }

    // 移除成员
    public void removeMember(User user) {
        this.members.remove(user);
    }
}
