package com.mundotech.newspaper.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        //hay que validar que el usuario tenga Role de author para poder crear un articulo
        return new ResponseEntity<>(articleService.createArticle(article, userId), HttpStatus.CREATED);
    }
}