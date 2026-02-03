package com.example.personalblog.controller;

import com.example.personalblog.model.Article;
import com.example.personalblog.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
    private ArticleService articleService;

    public AdminController (ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/admin")
    public String admin () {
        return "admin";
    }

    @PostMapping("/admin/login")
    public String login (@RequestParam String username, @RequestParam String password, Model model) {
        if (username.equals ("admin") && password.equals ("admin123")) {
            return "redirect:/admin/dashboard";
        } else {
            model.addAttribute ("error", "Incorrect username or password");
            return "admin";
        }
    }

    @GetMapping("/admin/dashboard")
    public String dashboard (Model model) {
        model.addAttribute ("articles", articleService.getAllArticles ());

        return "dashboard";
    }

    @GetMapping("/admin/edit/{fileName}")
    public String edit (@PathVariable String fileName, Model model) {
        Article article = articleService.getArticleByFileName (fileName);

        model.addAttribute ("fileName", fileName);
        model.addAttribute ("title", article.getTitle ());
        model.addAttribute ("date", article.getDate ());
        model.addAttribute ("content", article.getContent ());

        return "editArticle";
    }

    @GetMapping("/admin/add")
    public String add () {
        return "addArticle";
    }

    @PostMapping("/admin/delete/{fileName}")
    public String delete (@PathVariable String fileName) {
        articleService.deleteArticle (fileName);

        return "redirect:/admin/dashboard";
    }

    @PostMapping("/admin/save")
    public String delete (@RequestParam(required = false) String fileName, @RequestParam String title, @RequestParam String date, @RequestParam String content) {
        Article article = new Article ();

        String newFileName = title.toLowerCase ().
                replaceAll ("[^a-z0-9]+", "-").
                replaceAll ("^-|-$", "");

        if (fileName != null && !fileName.isBlank () && !fileName.equals (newFileName)) {
            articleService.deleteArticle (fileName);
        }

        article.setFileName (newFileName);
        article.setTitle(title);
        article.setDate(date);
        article.setContent(content);

        articleService.saveArticle (article);

        return "redirect:/admin/dashboard";
    }
}
