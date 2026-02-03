package com.example.bloggingplatformapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String category;

    @ElementCollection
    private List <String> tags;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Post () {

    }

    public Post (String title, String content, String category, List <String> tags) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.tags = tags;
    }

    @PrePersist
    protected void onCreate () {
        createdAt = LocalDateTime.now ();
        updatedAt = LocalDateTime.now ();
    }

    @PreUpdate
    protected void onUpdate () {
        updatedAt = LocalDateTime.now ();
    }

    public String toString () {
        return "{\n" +
                "id: " + this.id + ",\n" +
                "title: " + this.title + ",\n" +
                "content: " + this.content + ",\n" +
                "category: " + this.category + ",\n" +
                "tags: " + this.tags + ",\n" +
                "createdAt: " + this.createdAt + ",\n" +
                "updatedAt: " + this.updatedAt + "\n" +
                "}";
    }
}
