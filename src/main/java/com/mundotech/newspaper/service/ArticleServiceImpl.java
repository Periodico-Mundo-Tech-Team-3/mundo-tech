package com.mundotech.newspaper.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mundotech.newspaper.dto.request.ArticleDto;
import com.mundotech.newspaper.dto.response.ArticleInfoDto;
import com.mundotech.newspaper.entity.Article;
import com.mundotech.newspaper.repository.ArticleRepository;
import com.mundotech.newspaper.entity.ArticleStatus;

import com.mundotech.newspaper.entity.User;
import com.mundotech.newspaper.mapper.ArticleMapper;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserService userService;
    private final ArticleMapper articleMapper;
    
    public ArticleServiceImpl(ArticleRepository articleRepository, UserService userService, ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.userService = userService;
        this.articleMapper = articleMapper;
    }

    @Override
    public ArticleInfoDto createArticle(ArticleDto article, int userId) {
        if(!userService.hasRole(userService.getUserEntityById(userId), "author")){
            throw new IllegalArgumentException("El usuario no tiene el rol AUTHOR y no puede crear artículos.");
        }

        Article newArticle = articleMapper.toArticleEntity(article);

        newArticle.setStatus(ArticleStatus.DRAFT);
        newArticle.setUser(userService.getUserEntityById(userId));
        return articleMapper.toArticleInfoDto(articleRepository.save(newArticle));
    }

    @Override
    public List<ArticleInfoDto> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        if(articles.isEmpty()){
            throw new RuntimeException("No existen articulos");
        }
        return articleMapper.toArticleInfoDtoList(articles);
    }

    @Override
    public ArticleInfoDto getArticleById(int id) {
         Optional<Article> article=articleRepository.findById(id);
        if(article.isEmpty()){
            throw new RuntimeException("No existe ese articulo");
        }
        return articleMapper.toArticleInfoDto(article.get());
    }

    @Override
    public Article getArticleEntityById(int id) {
         Optional<Article> article=articleRepository.findById(id);
        if(article.isEmpty()){
            throw new RuntimeException("No existe ese articulo");
        }
        return article.get();
    }

    @Override
    public List<ArticleInfoDto> getArticlesByUserId(int userId) {
        User user = userService.getUserEntityById(userId);
         if(user==null){
            throw new RuntimeException("No existe ese usuario");
        }
        return articleMapper.toArticleInfoDtoList(articleRepository.findByUserId(userId));
    }

    @Override
    public ArticleInfoDto updateArticle(Integer id,Integer userLoginId, ArticleDto article) {
        Article articleAct = getArticleEntityById(id);
        if (articleAct.getUser() == null || articleAct.getUser().getId() != userLoginId) {
            throw new RuntimeException("No puedes editar un artículo que no es tuyo.");
        }
        articleAct.setTitle(article.title());
        articleAct.setContent(article.content());
        articleAct.setPublishDate(article.publishDate());
        return articleMapper.toArticleInfoDto(articleRepository.save(articleAct));
    }

    @Override
    public List<ArticleInfoDto> getArticlesByStatus(ArticleStatus status, Integer id) {
        validateGetArticleByStatusAction(status, id);
        List<Article> articles = switch (status) {
            case DRAFT -> articleRepository.findByStatusAndUserId(status, id);
            
            case IN_REVIEW ->
                userService.hasRole(userService.getUserEntityById(id), "manager")
                    ? articleRepository.findByStatus(status)
                    : articleRepository.findByStatusAndUserId(status, id);
            
            case PUBLISHED -> articleRepository.findByStatus(status);
        
            default -> throw new IllegalArgumentException("Estado no soportado: " + status);
        };
        return articleMapper.toArticleInfoDtoList(articles);
    }

    private void validateGetArticleByStatusAction(ArticleStatus status, Integer userId) {
    boolean requireStatus = status == ArticleStatus.DRAFT || status == ArticleStatus.IN_REVIEW;
    if (requireStatus && userId == null) {
        throw new IllegalArgumentException(
            "Debe indicarse un usuario (userId) para consultar los artículos en estado " + status);
        }
    }
}


