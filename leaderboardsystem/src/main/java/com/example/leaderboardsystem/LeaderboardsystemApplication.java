package com.example.leaderboardsystem;

import com.example.leaderboardsystem.model.User;
import com.example.leaderboardsystem.service.ScoreService;
import com.example.leaderboardsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Set;

@SpringBootApplication
public class LeaderboardsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeaderboardsystemApplication.class, args);
	}

    @Bean
    CommandLineRunner init (UserService userService, ScoreService scoreService) {
        return args -> {
            String[] usernames = {"Alice", "Bob", "Charlie", "Diana", "Eve", "Frank", "Grace", "Hank", "Ivy", "Jack"};
            for (String name : usernames) {
                if (!userService.existsByUsername(name)) {
                    User user = userService.register(name, name.toLowerCase() + "@example.com", "password");
                    scoreService.submitScore(user, "Tetris", (int) (Math.random() * 10000));
                    if (Math.random() > 0.5) scoreService.submitScore(user, "Pac-Man", (int) (Math.random() * 5000));
                    if (Math.random() > 0.5) scoreService.submitScore(user, "Space Invaders", (int) (Math.random() * 8000));
                }
            }

            scoreService.getAllScores().forEach(score -> {
                scoreService.getLeaderboardService().updateLeaderboard(score.getGame(), score.getUser(), score.getScore());
            });
        };
    }
}
