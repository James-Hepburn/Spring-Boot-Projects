package com.example.numberguessinggame.cli;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.Random;

@Component
public class CommandRunner implements CommandLineRunner {
    private Scanner input = new Scanner (System.in);
    private Random random = new Random ();

    @Override
    public void run (String... args) {
        int answer = 1 + random.nextInt (100);
        int chances = 0;

        System.out.println ("Welcome to the Number Guessing Game!");
        System.out.println ("I'm thinking of a number between 1 and 100.");

        System.out.println ("\nPlease select the difficulty level:");
        System.out.println ("1. Easy (10 chances)");
        System.out.println ("2. Medium (5 chances)");
        System.out.println ("3. Hard (3 chances)");

        System.out.print ("\nEnter an option: ");
        int option = input.nextInt ();

        if (option == 1) {
            chances = 10;
        } else if (option == 2) {
            chances = 5;
        } else if (option == 3) {
            chances = 3;
        } else {
            System.out.println ("Invalid option.");
            System.exit (0);
        }

        System.out.println ("\nGreat! Let's start!");

        for (int i = 1; i <= chances; i++) {
            System.out.print ("\nEnter a guess: ");
            int guess = input.nextInt ();

            if (guess < answer) {
                System.out.println ("Incorrect! The number is greater than " + guess + ".");
            } else if (guess > answer) {
                System.out.println ("Incorrect! The number is less than " + guess + ".");
            } else {
                System.out.println ("Congratulations! You guessed the correct number in " + i + " attempts.");
                break;
            }

            if (chances == i) {
                System.out.println ("Sorry! The correct number was " + answer + ".");
            }
        }
    }
}