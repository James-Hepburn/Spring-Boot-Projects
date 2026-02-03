package com.example.leaderboardsystem.service;

import com.example.leaderboardsystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LeaderboardService {
    @Autowired
    private RedisTemplate <String, String> redisTemplate;

    private String LEADERBOARD_KEY_PREFIX = "leaderboard:";

    public void updateLeaderboard (String game, User user, int score) {
        String key = this.LEADERBOARD_KEY_PREFIX + game;
        ZSetOperations <String, String> zSetOps = this.redisTemplate.opsForZSet ();
        zSetOps.add (key, user.getUsername (), (double) score);
    }

    public Long getUserRank (String game, User user) {
        String key = this.LEADERBOARD_KEY_PREFIX + game;
        ZSetOperations <String, String> zSetOps = this.redisTemplate.opsForZSet ();
        Long rank = zSetOps.reverseRank (key, user.getUsername ());
        return (rank != null) ? rank + 1 : null;
    }

    public List <Map <String, Object>> getTopPlayers (String game) {
        String key = this.LEADERBOARD_KEY_PREFIX + game;
        ZSetOperations <String, String> zSetOps = this.redisTemplate.opsForZSet ();
        Set <ZSetOperations.TypedTuple <String>> topSet = zSetOps.reverseRangeWithScores (key, 0, 9);

        List <Map <String, Object>> topPlayers = new ArrayList <>();

        if (topSet != null) {
            for (ZSetOperations.TypedTuple <String> tuple : topSet) {
                Map <String, Object> player = new HashMap <>();
                player.put ("username", tuple.getValue ());
                player.put ("score", tuple.getScore ().intValue ());
                topPlayers.add (player);
            }
        }

        return topPlayers;
    }

    public Integer getUserScore (String game, User user) {
        String key = this.LEADERBOARD_KEY_PREFIX + game;
        ZSetOperations <String, String> zSetOps = this.redisTemplate.opsForZSet ();
        Double score = zSetOps.score (key, user.getUsername ());
        return (score != null) ? score.intValue () : null;
    }

    public Set <String> getAllGames() {
        return redisTemplate.keys (LEADERBOARD_KEY_PREFIX + "*");
    }
}