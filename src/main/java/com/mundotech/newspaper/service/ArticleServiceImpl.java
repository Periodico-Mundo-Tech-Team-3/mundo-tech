package com.mundotech.newspaper.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mundotech.newspaper.dto.request.ArticleDto;
import com.mundotech.newspaper.dto.response.ArticleInfoDto;
import com.mundotech.newspaper.dto.response.FileUploadResponseDto;
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
    private final FileUploadService fileUploadService;
    
    public ArticleServiceImpl(ArticleRepository articleRepository, UserService userService, ArticleMapper articleMapper, FileUploadService fileUploadService) { 
        this.articleRepository = articleRepository;
        this.userService = userService;
        this.articleMapper = articleMapper;
        this.fileUploadService = fileUploadService;
    }

    @Override
    public ArticleInfoDto createArticle(ArticleDto article, int userId, MultipartFile file) {
        if(!userService.hasRole(userService.getUserEntityById(userId), "author")){
            throw new IllegalArgumentException("El usuario no tiene el rol AUTHOR y no puede crear artículos.");
        }

        //los ficheros se suben a la carpeta uploads y se guarda la ruta en la base de datos
        FileUploadResponseDto fichero = fileUploadService.upload(file);

        Article newArticle = articleMapper.toArticleEntity(article);

        newArticle.setStatus(ArticleStatus.DRAFT);
        newArticle.setUser(userService.getUserEntityById(userId));

        //estamos devolviendo ruta relativa, si quieres devolver ruta absoluta, habría que concatenar con la ruta base del servidor
        //newArticle.setRutaArchivo(fichero.getFileName());
        newArticle.setRutaArchivo(fichero.getPath());
        newArticle.setTipoContenido(fichero.getContentType());
        newArticle.setTamano(fichero.getSize());
        newArticle.setImage(fichero.getFileName());

        //En este caso no necesitarías el FileUploadService para guardar el archivo en disco.
        //esto seria para guardar el archivo en la base de datos, pero no es recomendable por el tamaño de los archivos
        //newArticle.setArchivo(file.getBytes());

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

    @Override
    public ArticleInfoDto submitArticle(Integer articleId, Integer userId) {
        Article article = getArticleEntityById(articleId);
        
        validateStatus(article, article.getStatus());
        validateIsAuthor(article, userId);

        article.setStatus(ArticleStatus.IN_REVIEW);
        return articleMapper.toArticleInfoDto(articleRepository.save(article));
    }

    public ArticleInfoDto publishArticle(Integer articleId, Integer userId) {
        Article article = getArticleEntityById(articleId);
        
        validateStatus(article, article.getStatus());
        validateIsManager(userId);

        article.setStatus(ArticleStatus.PUBLISHED);
        return articleMapper.toArticleInfoDto(articleRepository.save(article));
    }

    public ArticleInfoDto rejectArticle(Integer articleId, Integer userId) {
        Article article = getArticleEntityById(articleId);
        
        validateStatus(article, article.getStatus());
        validateIsManager(userId);

        article.setStatus(ArticleStatus.DRAFT);
        return articleMapper.toArticleInfoDto(articleRepository.save(article));
    }

    private void validateStatus(Article article, ArticleStatus expectedStatus) {
        if (article.getStatus() != expectedStatus) {
            throw new IllegalStateException(
                "El artículo debe estar en estado " + expectedStatus +
                " para esta acción. Estado actual: " + article.getStatus()
            );
        }
    }

    private void validateIsAuthor(Article article, Integer userId) {
        if (!article.getUser().getId().equals(userId)) {
            throw new IllegalAccessError(
                "Solo el autor del artículo puede realizar esta acción."
            );
        }
    }

    private void validateIsManager(Integer userId) {
        if (!userService.hasRole(userService.getUserEntityById(userId), "manager")) {
            throw new IllegalAccessError(
                "Solo un manager puede realizar esta acción."
            );
        }
    }
}


