package com.example.githubuseractivity.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties (ignoreUnknown = true)
public class Payload {
    private String ref;
    private String refType;
    private String action;
    private List <Commit> commits;

    public String getRef () {
        return ref;
    }

    public String getRefType () {
        return refType;
    }

    public String getAction () {
        return action;
    }

    public List <Commit> getCommits () {
        return commits;
    }
}
