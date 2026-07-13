package com.mundotech.newspaper.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mundotech.newspaper.entity.Article;
import com.mundotech.newspaper.repository.ArticleRepository;
import com.mundotech.newspaper.entity.ArticleStatus;

import com.mundotech.newspaper.entity.User;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserService userService;
    
    public ArticleServiceImpl(ArticleRepository articleRepository, UserService userService) {
        this.articleRepository = articleRepository;
        this.userService = userService;
    }

    @Override
    public Article createArticle(Article article, int userId) {
        if(!userService.getUserById(userId).getRoles().stream().anyMatch(role -> role.getName().equalsIgnoreCase("author"))){
            throw new IllegalArgumentException("El usuario no tiene el rol AUTHOR y no puede crear artículos.");
        }

        article.setStatus(ArticleStatus.DRAFT);
        article.setUser(userService.getUserById(userId));
        return articleRepository.save(article);
        
    }

    @Override
    public List<Article> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        if(articles.isEmpty()){
            throw new RuntimeException("No existen articulos");
        }
        return articles;
    }

    @Override
    public Article getArticleById(int id) {
         Optional<Article> article=articleRepository.findById(id);
        if(article.isEmpty()){
            throw new RuntimeException("No existe ese articulo");
        }
        return article.get();
    }

    @Override
    public List<Article> getArticlesByUserId(int userId) {
        User user = userService.getUserById(userId);
         if(user==null){
            throw new RuntimeException("No existe ese usuario");
        }
        return articleRepository.findByUserId(userId);
    }

    @Override
    public Article updateArticle(Integer id,Integer userLoginId, Article article) {
        //para validar la autoria del articulo debo pasar el userid logeado como parámetro
        Article articleAct=getArticleById(id);
        // La validación del solicitante es el autor
    if (articleAct.getUser() == null || articleAct.getUser().getId() != userLoginId) {
        throw new RuntimeException("No puedes editar un artículo que no es tuyo.");
    }
        articleAct.setTitle(article.getTitle());
        articleAct.setContent(article.getContent());
        return articleRepository.save(articleAct);  
    }
}

