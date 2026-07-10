package com.mundotech.newspaper.service;

import java.util.List;

import com.mundotech.newspaper.entity.Article;

public interface ArticleService {
    public Article createArticle(Article article, int userId);

    public List<Article> getAllArticles();

    public Article getArticleById(int id);

    public List<Article> getArticlesByUserId(int userId);
}