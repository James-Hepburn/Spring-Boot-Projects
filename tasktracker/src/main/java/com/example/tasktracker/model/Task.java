package com.example.tasktracker.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Task {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Column (unique = true)
    private String description;

    public Task () {}

    public Task (String description) {
        this.description = description;
        this.status = "Todo";
        this.createdAt = LocalDateTime.now ();
        this.updatedAt = LocalDateTime.now ();
    }

    public String toString () {
        return "Task: " + this.description + " - Status: " + this.status
                + " - Created At: " + this.createdAt.toString ()
                + " - Updated At: " + this.updatedAt.toString ();
    }

    public void setDescription (String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now ();
    }

    public void setStatus (String status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now ();
    }

    public String getStatus () {
        return this.status;
    }
}
