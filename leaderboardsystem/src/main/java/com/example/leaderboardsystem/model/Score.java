package com.example.leaderboardsystem.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String game;
    private int score;

    @CreationTimestamp
    private LocalDateTime submittedAt;

    public Score (User user, String game, int score) {
        this.user = user;
        this.game = game;
        this.score = score;
    }
}