package com.mundotech.newspaper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mundotech.newspaper.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
}