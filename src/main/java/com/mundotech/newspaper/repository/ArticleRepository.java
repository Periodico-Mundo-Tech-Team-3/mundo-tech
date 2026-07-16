package com.mundotech.newspaper.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mundotech.newspaper.entity.Article;
import com.mundotech.newspaper.entity.ArticleStatus;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

    List<Article> findByUserId(Integer userId);

    List<Article> findByStatus(ArticleStatus status);

    List<Article> findByStatusAndUserId(ArticleStatus status, Integer userId);
}