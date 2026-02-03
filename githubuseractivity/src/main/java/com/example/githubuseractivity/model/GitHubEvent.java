package com.example.githubuseractivity.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties (ignoreUnknown = true)
public class GitHubEvent {
    private String id;
    private String type;
    private Repo repo;
    private Payload payload;
    private String created_at;

    public String getType () {
        return type;
    }

    public Repo getRepo () {
        return repo;
    }

    public Payload getPayload () {
        return payload;
    }
}
