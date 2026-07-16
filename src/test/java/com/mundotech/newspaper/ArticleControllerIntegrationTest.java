package com.mundotech.newspaper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.transaction.annotation.Transactional;

import com.mundotech.newspaper.entity.Article;
import com.mundotech.newspaper.entity.ArticleStatus;
import com.mundotech.newspaper.entity.Role;
import com.mundotech.newspaper.entity.User;
import com.mundotech.newspaper.repository.ArticleRepository;
import com.mundotech.newspaper.repository.RoleRepository;
import com.mundotech.newspaper.repository.UserRepository;

import jakarta.servlet.ServletException;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class ArticleControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private RoleRepository roleRepository;

    private Role authorRole;
    private Role managerRole;
    private User author;
    private User manager;
    private User anotherAuthor;

    @BeforeEach
    void setup(){

        System.out.println("Working dir: " + System.getProperty("user.dir"));
        System.out.println("DB_URL leída: " + System.getenv("DB_URL"));
        System.out.println("Property spring.datasource.url: " + System.getProperty("spring.datasource.url"));


        authorRole = roleRepository.findById(1)
            .orElseGet(() -> {
                Role role = new Role();
                role.setName("author");
                return roleRepository.save(role);
            });

        managerRole = roleRepository.findById(2)
            .orElseGet(() -> {
                Role role = new Role();
                role.setName("manager");
                return roleRepository.save(role);
            });

        author = new User();
        author.setName("Ana Test");
        author.setEmail("ana.test@mundotech.com");
        author.setPassword("1234");
        author.setRoles(Set.of(authorRole));
        author = userRepository.save(author);

        manager = new User();
        manager.setName("Manager Test");
        manager.setEmail("manager.test@mundotech.com");
        manager.setPassword("1234");
        manager.setRoles(Set.of(managerRole));
        manager = userRepository.save(manager);

        anotherAuthor = new User();
        anotherAuthor.setName("Otro Autor Test");
        anotherAuthor.setEmail("otro.autor@mundotech.com");
        anotherAuthor.setPassword("1234");
        anotherAuthor.setRoles(Set.of(authorRole));
        anotherAuthor = userRepository.save(anotherAuthor);
    }

    private Article crearArticulo(User author, ArticleStatus status) {
        Article article = new Article();
        article.setTitle("Artículo de prueba");
        article.setContent("Contenido de prueba");
        article.setPublishDate(new Date());
        article.setStatus(status);
        article.setUser(author);
        return articleRepository.save(article);
    }

    @Test
    public void submitArticle_shouldReturn200AndChangeStatus_whenUserIsAuthorAndStateIsDRAFT() throws Exception {
        Article article = crearArticulo(author, ArticleStatus.DRAFT);

        mockMvc.perform(get("/api/v1/articles/{id}/submit", article.getId())
                    .param("userId", author.getId().toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("IN_REVIEW"));
    }

    @Test
    public void submitArticle_shouldReturn500_whenArticleIsNotDraft() throws Exception {
        Article article = crearArticulo(author, ArticleStatus.PUBLISHED);

        Exception exception = assertThrows(ServletException.class, () ->
        mockMvc.perform(get("/api/v1/articles/{id}/submit", article.getId())
                .param("userId", author.getId().toString()))
        );

        assertTrue(exception.getCause() instanceof IllegalStateException);
        assertTrue(exception.getCause().getMessage().contains("debe estar en estado DRAFT"));
    }

    @Test
    public void submitArticle_shouldReturn500_whenUserIsOnlyManager() throws Exception {
        Article article = crearArticulo(author, ArticleStatus.DRAFT);

        Exception exception = assertThrows(ServletException.class, () ->
        mockMvc.perform(get("/api/v1/articles/{id}/submit", article.getId())
                .param("userId", manager.getId().toString()))
        );

        assertTrue(exception.getCause() instanceof IllegalAccessError);
    }

    @Test
    public void submitArticle_shouldReturn500_whenUserIsAnotherAuthor() throws Exception {
        Article article = crearArticulo(author, ArticleStatus.DRAFT);

        Exception exception = assertThrows(ServletException.class, () ->
        mockMvc.perform(get("/api/v1/articles/{id}/submit", article.getId())
                .param("userId", anotherAuthor.getId().toString()))
        );

        assertTrue(exception.getCause() instanceof IllegalAccessError);
    }
}
