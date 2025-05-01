package com.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rankings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ranking extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Long score;

    @Column(nullable = false)
    private Long solvedTasks;

    @Column(nullable = false)
    private LocalDateTime lastUpdate;
}
