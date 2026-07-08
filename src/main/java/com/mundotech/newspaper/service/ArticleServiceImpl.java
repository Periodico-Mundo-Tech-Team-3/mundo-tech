package com.mundotech.newspaper.service;

import org.springframework.stereotype.Service;

import com.mundotech.newspaper.entity.Article;
import com.mundotech.newspaper.repository.ArticleRepository;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserService userService;
    
    public ArticleServiceImpl(ArticleRepository articleRepository, UserService userService) {
        this.articleRepository = articleRepository;
        this.userService = userService;
    }

    @Override
    public Article createArticle(Article article, int userId) {
        article.setUser(userService.getUserById(userId));
        return articleRepository.save(article);
    }
}