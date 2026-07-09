package com.mundotech.newspaper.mapper;

import org.springframework.stereotype.Component;

import com.mundotech.newspaper.dto.request.ArticleDto;
import com.mundotech.newspaper.entity.Article;

@Component
public class ArticleMapper {

    public Article toArticleEntity(ArticleDto articleDto){
        
        if (articleDto == null) return null;

        Article article = new Article();
        article.setTitle(articleDto.title());
        article.setContent(articleDto.content());
        article.setPublishDate(articleDto.publishDate());

        return article;
    }
}
