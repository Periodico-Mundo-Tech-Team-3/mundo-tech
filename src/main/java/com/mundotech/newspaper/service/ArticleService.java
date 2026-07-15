package com.mundotech.newspaper.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.mundotech.newspaper.dto.request.ArticleDto;
import com.mundotech.newspaper.dto.response.ArticleInfoDto;
import com.mundotech.newspaper.entity.Article;
import com.mundotech.newspaper.entity.ArticleStatus;

public interface ArticleService {
    public ArticleInfoDto createArticle(ArticleDto article, int userId, MultipartFile file);

    public List<ArticleInfoDto> getAllArticles();

    public ArticleInfoDto getArticleById(int id);

    public Article getArticleEntityById(int id);

    public List<ArticleInfoDto> getArticlesByUserId(int userId);

    public ArticleInfoDto updateArticle(Integer id,Integer userLoginId, ArticleDto article);

    public List<ArticleInfoDto> getArticlesByStatus(ArticleStatus status, Integer id);

    public ArticleInfoDto submitArticle(Integer articleId, Integer userId);

    public ArticleInfoDto publishArticle(Integer articleId, Integer userId);
    
    public ArticleInfoDto rejectArticle(Integer articleId, Integer userId);
}