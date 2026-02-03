package com.example.leaderboardsystem.repository;

import com.example.leaderboardsystem.model.Score;
import com.example.leaderboardsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository <Score, Long> {
    List <Score> findByUser (User user);
    List <Score> findByGameOrderByScoreDesc (String game);
    List <Score> findTop10ByGameOrderByScoreDesc (String game);
}