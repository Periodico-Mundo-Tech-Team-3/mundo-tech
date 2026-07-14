package com.mundotech.newspaper.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mundotech.newspaper.dto.request.ArticleDto;
import com.mundotech.newspaper.dto.response.ArticleInfoDto;
import com.mundotech.newspaper.entity.ArticleStatus;
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
    ResponseEntity<ArticleInfoDto> createArticle(@Valid @RequestBody ArticleDto articleDto, @PathVariable int userId) {
        ArticleInfoDto articleInfoDto = articleService.createArticle(articleDto, userId);
        return new ResponseEntity<>(articleInfoDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ArticleInfoDto>> getAllArticles(){
        List<ArticleInfoDto> listArticleInfoDto = articleService.getAllArticles();
        return new ResponseEntity<>(listArticleInfoDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleInfoDto> getArticleById(@PathVariable int id){
        ArticleInfoDto articleInfoDto = articleService.getArticleById(id);
        return new ResponseEntity<>(articleInfoDto, HttpStatus.OK);
    }

    
    @GetMapping("/author")
    public ResponseEntity<List<ArticleInfoDto>> getArticlesByUserId(@RequestParam int authorId) {
        List<ArticleInfoDto> listArticleInfoDto = articleService.getArticlesByUserId(authorId);
        return new ResponseEntity<>(listArticleInfoDto, HttpStatus.OK);
    }

    @PutMapping("/{id}/{userLoginId}")
    public ResponseEntity<ArticleInfoDto> updateArticle(@PathVariable Integer id,@PathVariable Integer userLoginId, @Valid @RequestBody ArticleDto article){
        ArticleInfoDto articleInfoDto = articleService.updateArticle(id,userLoginId, article);

        return new ResponseEntity<>(articleInfoDto, HttpStatus.OK);
    }

    @GetMapping("/status")
    public ResponseEntity<List<ArticleInfoDto>> getArticlesByStatus(
        @RequestParam ArticleStatus status,
        @RequestParam(required = false) Integer userId) {

        List<ArticleInfoDto> articles = articleService.getArticlesByStatus(status, userId);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }
}