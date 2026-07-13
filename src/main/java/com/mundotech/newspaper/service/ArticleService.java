package com.mundotech.newspaper.service;

import java.util.List;

import com.mundotech.newspaper.dto.request.ArticleDto;
import com.mundotech.newspaper.dto.response.ArticleInfoDto;
import com.mundotech.newspaper.entity.Article;

public interface ArticleService {
    public ArticleInfoDto createArticle(ArticleDto article, int userId);

    public List<ArticleInfoDto> getAllArticles();

    public ArticleInfoDto getArticleById(int id);

    public Article getArticleEntityById(int id);

    public List<ArticleInfoDto> getArticlesByUserId(int userId);

    public ArticleInfoDto updateArticle(Integer id,Integer userLoginId, ArticleDto article);
}