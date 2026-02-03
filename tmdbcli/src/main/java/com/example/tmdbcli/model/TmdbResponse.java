package com.example.tmdbcli.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TmdbResponse {
    private int page;
    private List <Movie> results;
    private int totalPages;
    private int totalResults;
}
