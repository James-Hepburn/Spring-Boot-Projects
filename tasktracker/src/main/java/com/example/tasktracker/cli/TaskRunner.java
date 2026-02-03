package com.example.tasktracker.cli;

import com.example.tasktracker.model.Task;
import com.example.tasktracker.repository.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class TaskRunner implements CommandLineRunner {
    private Scanner input = new Scanner (System.in);
    private TaskRepository repo;

    public TaskRunner (TaskRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run (String... args) {
        System.out.println ("Task Runner CLI");

        while (true) {
            System.out.println ("\n1. Add a task");
            System.out.println ("2. Delete a task");
            System.out.println ("3. Update a task");
            System.out.println ("4. List all tasks");
            System.out.println ("5. List all tasks marked todo");
            System.out.println ("6. List all tasks marked in-progress");
            System.out.println ("7. List all tasks marked done");
            System.out.println ("8. Quit application");

            System.out.print ("\nPlease select an option: ");
            int option = input.nextInt ();

            if (option == 1) {
                input.nextLine ();

                System.out.print ("Enter the task: ");
                String description = input.nextLine ();

                Optional <Task> taskOptional = repo.findByDescription (description);

                if (taskOptional.isPresent ()) {
                    System.out.println ("This task already exists.");
                } else {
                    Task task = new Task (description);
                    repo.save (task);
                    System.out.println ("Task successfully added.");
                }
            }

            else if (option == 2) {
                input.nextLine ();

                System.out.print ("Enter the task: ");
                String description = input.nextLine ();

                Optional <Task> taskOptional = repo.findByDescription (description);

                if (taskOptional.isPresent ()) {
                    Task task = taskOptional.get ();
                    repo.delete (task);
                    System.out.println ("Task successfully deleted.");
                } else {
                    System.out.println ("No task exists with that description.");
                }
            }

            else if (option == 3) {
                input.nextLine ();

                System.out.print ("Enter the task: ");
                String description = input.nextLine ();

                Optional <Task> taskOptional = repo.findByDescription (description);

                if (taskOptional.isPresent ()) {
                    Task task = taskOptional.get ();

                    System.out.println ("\n1. Update the status");
                    System.out.println ("2. Update the description");
                    System.out.print ("\nPlease select an option: ");
                    int suboption = input.nextInt ();

                    if (suboption == 1) {
                        System.out.println ("\n1. Todo");
                        System.out.println ("2. Done");
                        System.out.println ("3. In-Progress");
                        System.out.print ("\nPlease select an option: ");
                        int statusoption = input.nextInt ();

                        if (statusoption == 1) {
                            task.setStatus ("Todo");
                            repo.save (task);
                            System.out.println ("Task status successfully updated to todo.");
                        } else if (statusoption == 2) {
                            task.setStatus ("Done");
                            repo.save (task);
                            System.out.println ("Task status successfully updated to done.");
                        } else if (statusoption == 3) {
                            task.setStatus ("In-Progress");
                            repo.save (task);
                            System.out.println ("Task status successfully updated to in-progress.");
                        } else {
                            System.out.println ("Invalid input.");
                        }
                    } else if (suboption == 2) {
                        input.nextLine ();

                        System.out.print ("\nPlease enter the new description: ");
                        String newdescription = input.nextLine ();

                        Optional <Task> taskOptionalDescription = repo.findByDescription (newdescription);

                        if (taskOptionalDescription.isPresent ()) {
                            System.out.println ("This task already exists.");
                        } else {
                            task.setDescription (newdescription);
                            repo.save (task);
                            System.out.println ("Task description successfully updated.");
                        }
                    } else {
                        System.out.println ("Invalid input.");
                    }
                } else {
                    System.out.println ("No task exists with that description.");
                }
            }

            else if (option == 4) {
                List <Task> tasks = repo.findAll ();

                System.out.println ("Here are all tasks:");

                for (int i = 0; i < tasks.size (); i++) {
                    System.out.println (tasks.get (i));
                }
            }

            else if (option == 5) {
                List <Task> tasks = repo.findAll ();

                System.out.println ("Here are all tasks marked todo:");

                for (int i = 0; i < tasks.size (); i++) {
                    if (tasks.get (i).getStatus ().equals ("Todo")) {
                        System.out.println (tasks.get (i));
                    }
                }
            }

            else if (option == 6) {
                List <Task> tasks = repo.findAll ();

                System.out.println ("Here are all tasks marked in-progress:");

                for (int i = 0; i < tasks.size (); i++) {
                    if (tasks.get (i).getStatus ().equals ("In-Progress")) {
                        System.out.println (tasks.get (i));
                    }
                }
            }

            else if (option == 7) {
                List <Task> tasks = repo.findAll ();

                System.out.println ("Here are all tasks marked done:");

                for (int i = 0; i < tasks.size (); i++) {
                    if (tasks.get (i).getStatus ().equals ("Done")) {
                        System.out.println (tasks.get (i));
                    }
                }
            }

            else if (option == 8) {
                System.out.println ("Thank you for using the CLI Task Tracker.");
                break;
            }

            else {
                System.out.println ("Invalid input.");
            }
        }

        System.exit (0);
    }
}
