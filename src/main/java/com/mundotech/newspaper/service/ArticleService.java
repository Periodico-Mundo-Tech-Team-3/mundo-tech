package com.mundotech.newspaper.service;

import com.mundotech.newspaper.entity.Article;

public interface ArticleService {
    public Article createArticle(Article article, int userId);
}