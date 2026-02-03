package com.example.personalblog.service;

import com.example.personalblog.model.Article;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ArticleService {
    private Path fileDirectory;
    private ObjectMapper objectMapper;

    public ArticleService () {
        this.fileDirectory = Paths.get ("src/articles");
        this.objectMapper = new ObjectMapper ();
    }

    public List <Article> getAllArticles () {
        List <Article> articles = new ArrayList <> ();

        try {
            Files.walk (this.fileDirectory, 1)
                    .filter (Files::isRegularFile)
                    .forEach (path ->{
                        try {
                            Article article = objectMapper.readValue (path.toFile (), Article.class);
                            articles.add (article);
                        } catch (IOException e) {
                            e.printStackTrace ();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace ();
        }

        return articles;
    }

    public Article getArticleByFileName (String fileName) {
        AtomicReference <Article> result = new AtomicReference <>();

        try {
            Files.walk (this.fileDirectory, 1)
                    .filter (Files::isRegularFile)
                    .forEach (path ->{
                        try {
                            Article article = objectMapper.readValue (path.toFile (), Article.class);

                            if (article.getFileName ().equals (fileName)) {
                                result.set (article);
                            }
                        } catch (IOException e) {
                            e.printStackTrace ();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace ();
        }

        return result.get ();
    }

    public void saveArticle (Article article) {
        File file = this.fileDirectory.resolve (article.getFileName () + ".json").toFile ();

        try {
            this.objectMapper.writeValue (file, article);
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    public void deleteArticle (String fileName) {
        try {
            Files.deleteIfExists (this.fileDirectory.resolve (fileName + ".json"));
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }
}
