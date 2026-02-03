package com.example.personalblog.controller;

import com.example.personalblog.model.Article;
import com.example.personalblog.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GuestController {
    private ArticleService articleService;

    public GuestController (ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping ("/")
    public String guest (Model model) {
        model.addAttribute ("articles", articleService.getAllArticles ());

        return "index";
    }

    @GetMapping("/article/{fileName}")
    public String viewArticle (@PathVariable String fileName, Model model) {
        Article article = articleService.getArticleByFileName (fileName);

        model.addAttribute ("fileName", fileName);
        model.addAttribute ("title", article.getTitle ());
        model.addAttribute ("date", article.getDate ());
        model.addAttribute ("content", article.getContent ());

        return "viewArticle";
    }
}