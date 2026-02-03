package com.example.githubuseractivity.service;

import com.example.githubuseractivity.model.GitHubEvent;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GitHubService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public List <GitHubEvent> getUserEvents (String username) {
        String url = "https://api.github.com/users/" + username + "/events";

        try {
            ResponseEntity <String> response = restTemplate.getForEntity (url, String.class);
            String json = response.getBody ();

            return objectMapper.readValue (json, new TypeReference <List <GitHubEvent>>() {});
        } catch (Exception e) {
            return null;
        }
    }
}