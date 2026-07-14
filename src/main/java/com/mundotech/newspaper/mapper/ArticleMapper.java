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
            //¿hay que enviar la ruta del archivo, tamaño y tipo de contenido en el DTO de respuesta?
            //si es así, habría que añadir esos campos en el constructor de ArticleInfoDto y en la clase ArticleInfoDto
            //article.getRutaArchivo()
            //article.getTamano(),
            //article.getTipoContenido(),
            new AuthorInfoDto(
                article.getUser().getId(),
                article.getUser().getName()
            ),
                article.getImage()
        );
    }

    public List<ArticleInfoDto> toArticleInfoDtoList(List<Article> articles){
        return articles.stream()
            .map(this::toArticleInfoDto)
            .toList();
    }
}
