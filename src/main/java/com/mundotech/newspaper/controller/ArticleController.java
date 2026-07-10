package com.mundotech.newspaper.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mundotech.newspaper.dto.request.ArticleDto;
import com.mundotech.newspaper.dto.response.ArticleInfoDto;
import com.mundotech.newspaper.entity.Article;
import com.mundotech.newspaper.mapper.ArticleMapper;
import com.mundotech.newspaper.service.ArticleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleMapper articleMapper;

    public ArticleController(ArticleService articleservice, ArticleMapper articleMapper){
        this.articleService = articleservice;
        this.articleMapper = articleMapper;
    }
    
    @PostMapping("/{userId}")
    ResponseEntity<ArticleInfoDto> createArticle(@Valid @RequestBody ArticleDto articleDto, @PathVariable int userId) {
        Article article = articleMapper.toArticleEntity(articleDto);
        ArticleInfoDto response = articleMapper.toArticleInfoDto(articleService.createArticle(article, userId));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ArticleInfoDto>> getAllArticles(){
        List<ArticleInfoDto> responses = articleMapper.toArticleResponseList(articleService.getAllArticles());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleInfoDto> getArticleById(@PathVariable int id){
        ArticleInfoDto response = articleMapper.toArticleInfoDto(articleService.getArticleById(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    
    //@GetMapping("/author/{userId}")
    //public ResponseEntity<List<ArticleInfoDto>> getArticlesByUserId(@PathVariable int userId) {
    @GetMapping("/author")
    public ResponseEntity<List<ArticleInfoDto>> getArticlesByUserId(@RequestParam int authorId) {
    List<ArticleInfoDto> responses =
            articleMapper.toArticleResponseList(articleService.getArticlesByUserId(authorId));
    return new ResponseEntity<>(responses, HttpStatus.OK);
}

}