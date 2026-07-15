 package com.mundotech.newspaper.dto.response;

 import java.time.Instant;

 import com.mundotech.newspaper.entity.ArticleStatus;

public record ArticleInfoDto(
    Integer id,
    String title,
    String content,
    Instant publishDate,
    ArticleStatus status,
    AuthorInfoDto author,
    String image
) {

    // public record AuthorInfoDto(
    //     Integer id,
    //     String name
    // ) {}
}