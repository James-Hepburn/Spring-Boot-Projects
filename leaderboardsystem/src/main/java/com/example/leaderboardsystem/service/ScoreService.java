package com.example.leaderboardsystem.service;

import com.example.leaderboardsystem.model.Score;
import com.example.leaderboardsystem.model.User;
import com.example.leaderboardsystem.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreService {
    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private LeaderboardService leaderboardService;

    public Score submitScore (User user, String game, int score) {
        Score scoreObject = new Score (user, game, score);
        this.leaderboardService.updateLeaderboard (game, user, score);
        return this.scoreRepository.save (scoreObject);
    }

    public List <Score> getScoresByUser (User user) {
        return this.scoreRepository.findByUser (user);
    }

    public List <Score> getScoresByGame (String game) {
        return this.scoreRepository.findByGameOrderByScoreDesc (game);
    }

    public List <Score> getTopScores (String game) {
        return this.scoreRepository.findTop10ByGameOrderByScoreDesc (game);
    }

    public List <Score> getAllScores () {
        return this.scoreRepository.findAll ();
    }

    public LeaderboardService getLeaderboardService() {
        return this.leaderboardService;
    }
}