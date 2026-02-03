package com.example.tmdbcli.runner;

import com.example.tmdbcli.model.Movie;
import com.example.tmdbcli.service.TmdbService;
import lombok.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class MovieCommandLineRunner implements CommandLineRunner {
    private Scanner input = new Scanner (System.in);
    private TmdbService tmdbService;

    public MovieCommandLineRunner (TmdbService tmdbService) {
        this.tmdbService = tmdbService;
    }

    @Override
    public void run (String... args) {
        System.out.println ("TMDB CLI Tool!\n");

        System.out.println ("1. Now playing movies");
        System.out.println ("2. Popular movies");
        System.out.println ("3. Top rated movies ");
        System.out.println ("4. Upcoming movies");

        System.out.print ("Enter an option: ");
        int option = input.nextInt ();

        if (option == 1) {
            displayMovies (this.tmdbService.getNowPlayingMovies ());
        } else if (option == 2) {
            displayMovies (this.tmdbService.getPopularMovies ());
        } else if (option == 3) {
            displayMovies (this.tmdbService.getTopRatedMovies ());
        } else if (option == 4) {
            displayMovies (this.tmdbService.getUpcomingMovies ());
        } else {
            System.out.println ("\nInvalid option.");
        }
    }

    private void displayMovies (List <Movie> movies) {
        System.out.println ("\nHere are the movies:");

        for (Movie m : movies) {
            System.out.println (m.getTitle () + ":\n    " + m.getOverview ());
        }
    }
}
