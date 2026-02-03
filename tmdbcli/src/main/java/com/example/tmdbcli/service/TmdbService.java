package com.example.tmdbcli.service;

import com.example.tmdbcli.model.Movie;
import com.example.tmdbcli.model.TmdbResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class TmdbService {
    @Value("${tmdb.api.key}")
    private String apiKey;

    private String baseUrl = "https://api.themoviedb.org/3/movie";
    private RestTemplate restTemplate = new RestTemplate ();

    public List <Movie> getNowPlayingMovies () {
        String url = String.format ("%s/now_playing?api_key=%s&language=en-US&page=1", this.baseUrl, this.apiKey);

        try {
            TmdbResponse response = this.restTemplate.getForObject (url, TmdbResponse.class);
            return (response != null) ? response.getResults () : Collections.emptyList ();
        } catch (Exception e) {
            System.err.println ("Error fetching now playing movies: " + e.getMessage ());
            return Collections.emptyList ();
        }
    }

    public List <Movie> getPopularMovies () {
        String url = String.format ("%s/popular?api_key=%s&language=en-US&page=1", this.baseUrl, this.apiKey);

        try {
            TmdbResponse response = this.restTemplate.getForObject (url, TmdbResponse.class);
            return (response != null) ? response.getResults () : Collections.emptyList ();
        } catch (Exception e) {
            System.err.println ("Error fetching popular movies: " + e.getMessage ());
            return Collections.emptyList ();
        }
    }

    public List <Movie> getTopRatedMovies () {
        String url = String.format ("%s/top_rated?api_key=%s&language=en-US&page=1", this.baseUrl, this.apiKey);

        try {
            TmdbResponse response = this.restTemplate.getForObject (url, TmdbResponse.class);
            return (response != null) ? response.getResults () : Collections.emptyList ();
        } catch (Exception e) {
            System.err.println ("Error fetching top rated movies: " + e.getMessage ());
            return Collections.emptyList ();
        }
    }

    public List <Movie> getUpcomingMovies () {
        String url = String.format ("%s/upcoming?api_key=%s&language=en-US&page=1", this.baseUrl, this.apiKey);

        try {
            TmdbResponse response = this.restTemplate.getForObject (url, TmdbResponse.class);
            return (response != null) ? response.getResults () : Collections.emptyList ();
        } catch (Exception e) {
            System.err.println ("Error fetching upcoming movies: " + e.getMessage ());
            return Collections.emptyList ();
        }
    }
}
