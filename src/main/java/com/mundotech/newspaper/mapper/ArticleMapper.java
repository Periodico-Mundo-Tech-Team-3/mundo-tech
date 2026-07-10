package com.mundotech.newspaper.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mundotech.newspaper.dto.request.ArticleDto;
import com.mundotech.newspaper.dto.response.ArticleInfoDto;
import com.mundotech.newspaper.entity.Article;

import com.mundotech.newspaper.dto.response.AuthorInfoDto;

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

    public ArticleInfoDto toArticleInfoDto(Article article){
        if (article == null) return null;

        return new ArticleInfoDto(
            article.getId(),
            article.getTitle(),
            article.getContent(),
            article.getPublishDate().toInstant(),
            article.getStatus(),
            new AuthorInfoDto(
                article.getUser().getId(),
                article.getUser().getName()
            )
        );
    }

    public List<ArticleInfoDto> toArticleResponseList(List<Article> articles){
        return articles.stream()
            .map(this::toArticleInfoDto)
            .toList();
    }
}
