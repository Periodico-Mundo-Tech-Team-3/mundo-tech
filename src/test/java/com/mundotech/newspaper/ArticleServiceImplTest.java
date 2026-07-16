package com.mundotech.newspaper;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mundotech.newspaper.mapper.ArticleMapper;
import com.mundotech.newspaper.repository.ArticleRepository;
import com.mundotech.newspaper.service.ArticleServiceImpl;
import com.mundotech.newspaper.service.FileUploadService;
import com.mundotech.newspaper.service.UserService;

import com.mundotech.newspaper.entity.User;
import com.mundotech.newspaper.dto.request.ArticleDto;


@ExtendWith(MockitoExtension.class)
class ArticleServiceImplTest {

    @Mock ArticleRepository articleRepository;
    @Mock UserService userService;
    @Mock ArticleMapper articleMapper;
    @Mock FileUploadService fileUploadService;

    @InjectMocks ArticleServiceImpl service;

    @Test
    void getArticleById_throwsWhenNotFound() {
        when(articleRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.getArticleById(99));
    }

    @Test
    void createArticle_throwsWhenUserNotAuthor() {
        User user = new User(); user.setId(1);
        when(userService.getUserEntityById(1)).thenReturn(user);
        when(userService.hasRole(user, "author")).thenReturn(false);

        assertThrows(IllegalArgumentException.class,
            () -> service.createArticle(mock(ArticleDto.class), 1, null));
    }
}