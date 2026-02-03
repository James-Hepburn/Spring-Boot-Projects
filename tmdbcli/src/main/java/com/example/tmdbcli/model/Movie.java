package com.example.tmdbcli.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    private String title;
    private String overview;
}
