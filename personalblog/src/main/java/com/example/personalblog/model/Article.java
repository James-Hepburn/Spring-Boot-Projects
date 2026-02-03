package com.example.personalblog.model;

import jakarta.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Article {
    private String title;
    private String date;
    private String content;
    private String fileName;

    public Article () {

    }

    public Article (String title, String date, String content) {
        this.title = title;
        this.date = date;
        this.content = content;
        this.fileName = getFileName ();
    }

    public void setTitle (String title) {
        this.title = title;
    }
    public void setDate (String date) {
        this.date = date;
    }
    public void setContent (String content) {
        this.content = content;
    }
    public void setFileName (String fileName) {
        this.fileName = fileName;
    }

    public String getTitle () {
        return this.title;
    }
    public String getDate () {
        return this.date;
    }
    public String getContent () {
        return this.content;
    }
    public String getFileName () {
        return this.title.toLowerCase ().
                replaceAll ("[^a-z0-9]+", "-").
                replaceAll ("^-|-$", "");
    }
}