package com.mundotech.newspaper.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
     
    @PostMapping(value = "/{userId}" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<ArticleInfoDto> createArticle(@RequestPart("article") @Valid ArticleDto articleDto,@RequestPart(value = "file", required = false) MultipartFile file, @PathVariable int userId) {
        ArticleInfoDto articleInfoDto = articleService.createArticle(articleDto, userId,file);
        return new ResponseEntity<>(articleInfoDto, HttpStatus.CREATED);
    }

    @PutMapping(value ="/{id}/{userLoginId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ArticleInfoDto> updateArticle(@PathVariable Integer id,@PathVariable Integer userLoginId,  @RequestPart("article") @Valid ArticleDto article,@RequestPart(value = "file", required = false) MultipartFile file) {
        ArticleInfoDto articleInfoDto = articleService.updateArticle(id,userLoginId, article,file);

        return new ResponseEntity<>(articleInfoDto, HttpStatus.OK);
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



    @GetMapping("/status")
    public ResponseEntity<List<ArticleInfoDto>> getArticlesByStatus(
        @RequestParam ArticleStatus status,
        @RequestParam(required = false) Integer userId) {

        List<ArticleInfoDto> articles = articleService.getArticlesByStatus(status, userId);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/{userLoginId}")
    public ResponseEntity<Void> deleteArticle(@PathVariable int id,@PathVariable Integer userLoginId) {
        articleService.deleteArticleById(id, userLoginId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/submit")
    public ResponseEntity<ArticleInfoDto> submitArticle(
        @PathVariable Integer id,
        @RequestParam Integer userId) {

        ArticleInfoDto article = articleService.submitArticle(id, userId);
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @GetMapping("/{id}/publish")
    public ResponseEntity<ArticleInfoDto> publishArticle(
        @PathVariable Integer id,
        @RequestParam Integer userId) {

        ArticleInfoDto article = articleService.publishArticle(id, userId);
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @GetMapping("/{id}/reject")
    public ResponseEntity<ArticleInfoDto> rejectArticle(
        @PathVariable Integer id,
        @RequestParam Integer userId) {

        ArticleInfoDto article = articleService.rejectArticle(id, userId);
        return new ResponseEntity<>(article, HttpStatus.OK);
    }
}