package com.mundotech.newspaper.dto.request;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ArticleDto(

    @NotBlank
    @Size(max = 100, message = "El título no puede pasar de los 100 caracteres")
    String title,

    @NotBlank
    @Size(max = 10000, message = "El contenido no puede pasar de los 10.000 caracteres")
    String content,

    @NotNull
    Date publishDate,

    String image

) {}