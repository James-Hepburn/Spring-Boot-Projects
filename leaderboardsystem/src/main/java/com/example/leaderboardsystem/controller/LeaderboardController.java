package com.example.leaderboardsystem.controller;

import com.example.leaderboardsystem.model.User;
import com.example.leaderboardsystem.service.LeaderboardService;
import com.example.leaderboardsystem.service.ScoreService;
import com.example.leaderboardsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class LeaderboardController {

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private LeaderboardService leaderboardService;

    @Autowired
    private UserService userService;

    @GetMapping("/leaderboard")
    public String showLeaderboard (@RequestParam(required = false) String game, Model model) {
        Set <String> gameKeys = this.leaderboardService.getAllGames ();
        List <String> games = gameKeys.stream ()
                .map (key -> key.replace ("leaderboard:", ""))
                .toList ();

        if (game == null || !games.contains (game)) {
            game = games.isEmpty () ? "Tetris" : games.get (0);
        }

        List <Map <String, Object>> topPlayers = this.leaderboardService.getTopPlayers (game);

        model.addAttribute ("topPlayers", topPlayers);
        model.addAttribute ("game", game);
        model.addAttribute ("games", games);
        return "leaderboard";
    }

    @GetMapping("/submit-score")
    public String showScoreForm () {
        return "submit-score";
    }

    @PostMapping("/submit-score")
    public String submitScore (@AuthenticationPrincipal UserDetails userDetails, @RequestParam String game, @RequestParam int score, Model model) {
        User user = this.userService.findByUsername (userDetails.getUsername ()).orElse (null);

        if (user != null) {
            this.scoreService.submitScore (user, game, score);
        }

        return "redirect:/leaderboard?game=" + game;
    }

    @GetMapping("/my-rank")
    public String myRank (@AuthenticationPrincipal UserDetails userDetails, @RequestParam(defaultValue = "default") String game, Model model) {
        User user = this.userService.findByUsername (userDetails.getUsername ()).orElse( null);

        if (user != null) {
            Long rank = this.leaderboardService.getUserRank (game, user);
            Integer userScore = this.leaderboardService.getUserScore (game, user);
            model.addAttribute ("rank", rank);
            model.addAttribute ("score", userScore);
            model.addAttribute ("game", game);
        }

        return "my-rank";
    }
}