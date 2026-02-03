package com.example.bloggingplatformapi.controller;

import com.example.bloggingplatformapi.model.Post;
import com.example.bloggingplatformapi.repository.PostRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private PostRepository repo;

    public PostController (PostRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List <Post> getAllPosts () {
        return this.repo.findAll ();
    }

    @GetMapping("/{id}")
    public Post getPostById (@PathVariable Long id) {
        Post post = repo.findById (id).orElseThrow (() -> new RuntimeException ("Post not found"));
        return post;
    }

    @PostMapping
    public Post createPost (@RequestBody Post post) {
        return this.repo.save (post);
    }

    @PutMapping("/{id}")
    public Post updatePost (@PathVariable Long id, @RequestBody Post post) {
        Post oldPost = getPostById (id);

        oldPost.setTitle (post.getTitle ());
        oldPost.setContent (post.getContent ());
        oldPost.setCategory (post.getCategory ());
        oldPost.setTags (post.getTags ());

        return this.repo.save (oldPost);
    }

    @DeleteMapping("/{id}")
    public void deletePost (@PathVariable Long id) {
        getPostById (id);
        this.repo.deleteById (id);
    }
}
