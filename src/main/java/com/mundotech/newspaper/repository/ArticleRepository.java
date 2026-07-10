package com.mundotech.newspaper.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mundotech.newspaper.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

    List<Article> findByUserId(Integer userId);
}