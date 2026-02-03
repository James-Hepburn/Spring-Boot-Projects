package com.example.githubuseractivity.cli;

import com.example.githubuseractivity.model.GitHubEvent;
import com.example.githubuseractivity.service.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class CommandRunner implements CommandLineRunner {
    @Autowired
    private GitHubService gitHubService;

    private Scanner input = new Scanner (System.in);

    @Override
    public void run (String... args) {
        System.out.println ("GitHub User Activity");

        while (true) {
            System.out.println ("\n1. Search user");
            System.out.println ("2. Quit program");

            System.out.print ("Enter an option: ");
            int option = input.nextInt ();

            if (option == 1) {
                input.nextLine ();

                System.out.print ("Enter the username: ");
                String username = input.nextLine ();

                List <GitHubEvent> events = gitHubService.getUserEvents (username);

                if (events == null) {
                    System.out.println ("Invalid username.");
                } else {
                    System.out.println ("\nHere is the user's activity:");

                    for (GitHubEvent e : events) {
                        String type = e.getType ();
                        String repo = e.getRepo ().getName ();

                        if (type.equals ("PushEvent")) {
                            int commits = 0;

                            if (e.getPayload () != null && e.getPayload ().getCommits () != null) {
                                commits = e.getPayload ().getCommits ().size ();
                            }

                            System.out.println ("- Pushed " + commits + " commits to " + repo);
                        } else if (type.equals ("CreateEvent")) {
                            String refType = "";
                            String ref = "";

                            if (e.getPayload () != null) {
                                refType = e.getPayload ().getRefType () == null ? "" : e.getPayload ().getRefType ();
                                ref = e.getPayload ().getRef () == null ? "" : e.getPayload ().getRef ();
                            }

                            if ("repository".equals (refType)) {
                                System.out.println ("- Created repository " + repo);
                            } else if ("branch".equals (refType)) {
                                System.out.println ("- Created branch " + ref + " in " + repo);
                            } else if (!refType.isEmpty ()){
                                System.out.println ("- Created " + refType + " in " + repo);
                            } else {
                                System.out.println ("- Created an item in " + repo);
                            }
                        } else if (type.equals ("IssuesEvent")) {
                            String action = e.getPayload () == null ? "performed an action on" : e.getPayload ().getAction ();
                            System.out.println ("- " + action + " an issue in " + repo);
                        } else if (type.equals ("WatchEvent")) {
                            System.out.println ("- Starred " + repo);
                        } else if (type.equals ("ForkEvent")) {
                            System.out.println ("- Forked " + repo);
                        } else if (type.equals ("PullRequestEvent")) {
                            String action = e.getPayload () == null ? "performed an action on" : e.getPayload ().getAction ();
                            System.out.println ("- " + action + " a pull request in " + repo);
                        } else {
                            System.out.println ("- " + type + " event in " + repo);
                        }
                    }
                }
            } else if (option == 2) {
                System.out.println ("Thank you for using the program.");
                break;
            } else {
                System.out.println ("Invalid option.");
            }
        }

        System.exit (0);
    }
}