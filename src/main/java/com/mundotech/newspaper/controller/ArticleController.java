package com.mundotech.newspaper.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mundotech.newspaper.entity.Article;
import com.mundotech.newspaper.service.ArticleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleservice){
        this.articleService = articleservice;
    }
    
    @PostMapping("/{userId}")
    ResponseEntity<Article> createArticle(@Valid @RequestBody Article article,  @PathVariable int userId) {
        return new ResponseEntity<>(articleService.createArticle(article, userId), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Article>> getAllArticles(){
        return new ResponseEntity<>(articleService.getAllArticles(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable int id){
        return new ResponseEntity<>(articleService.getArticleById(id), HttpStatus.OK);
    }
}