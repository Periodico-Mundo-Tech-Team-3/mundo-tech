# 📰 Mundo Tech — Newspaper API

**Mundo Tech** es una API REST desarrollada con **Spring Boot 3** y **Java 25** como proyecto educativo para aprender los fundamentos de Spring Boot, arquitectura en capas, JPA y relaciones.

Gestiona **Usuarios**, **Roles** (relación Many-to-Many) y **Artículos** (relación Many-to-One con Usuario), y está diseñada para crecer con un flujo de revisión editorial.

## 📊 Diagrama de entidades

```mermaid
erDiagram
  Article {
        int id PK
        varchar title
        text content
        date publishDate
        varchar status
        int user_id FK
    }

    User {
        int id PK
        varchar name
        varchar email
        varchar password
    }

    Role {
        int id PK
        varchar role
    }

  

    user_roles {
        int user_id FK
        int role_id FK
    }

    User ||--o{ Article : escribe
    User ||--o{ user_roles : tiene
    Role ||--o{ user_roles : pertenece
    
```

**User** y **Role** se relacionan Many-to-Many mediante la tabla intermedia `user_roles`.
**User** y **Article** se relacionan One-to-Many: un usuario puede tener varios artículos.

Además, `Article` incluye un campo `status` de tipo `EstadoDocumento` que puede ser `DRAFT`, `IN_REVIEW` o `PUBLISHED`.

## 🛠️ Tech Stack

- Java 25
- Spring Boot 3.5.16
- Maven 3.9.16 (Wrapper incluido)
- Spring Data JPA / Hibernate
- PostgreSQL (producción) / H2 (testing)
- Jakarta Validation
- Lombok
- spring-dotenv

## 📂 Estructura del proyecto

```
src/main/java/com/mundotech/newspaper/
├── NewspaperApplication.java
├── controller/
│   ├── ArticleController.java     # /api/v1/articles
│   ├── RoleController.java        # /api/v1/roles
│   └── UserController.java        # /api/v1/users
├── entity/
│   ├── Article.java
│   ├── Role.java
│   └── User.java
├── repository/
│   ├── ArticleRepository.java
│   ├── RoleRepository.java
│   └── UserRepository.java
└── service/
    ├── ArticleService.java
    ├── ArticleServiceImpl.java
    ├── RoleService.java
    ├── RoleServiceImpl.java
    ├── UserService.java
    └── UserServiceImpl.java
```

## 📋Prerrequisitos

- Java 25+
- Maven 3.9+ (o usa el wrapper `./mvnw`)

## 🚀 Ejecutar el proyecto

```bash
# Copiar y configurar variables de entorno
cp .env.example .env

# Ejecutar con Maven Wrapper
./mvnw spring-boot:run
```

## 🔌 API Endpoints

| Método | Ruta                         | Descripción                     |
|--------|------------------------------|---------------------------------|
| POST   | /api/v1/users?rolesIds=      | Crear usuario con roles         |
| GET    | /api/v1/users                | Obtener todos los usuarios      |
| POST   | /api/v1/roles                | Crear un rol                    |
| GET    | /api/v1/roles                | Obtener todos los roles         |
| POST   | /api/v1/articles/{userId}    | Crear artículo asociado a un usuario |

## 🗄️ Configuración de BD

Por defecto usa **PostgreSQL**. Las credenciales se configuran en `.env`:

```
DB_URL=jdbc:postgresql://localhost:5432/mundotech
DB_USER=postgres
DB_PASSWORD=postgres
```

Para usar **H2** en desarrollo, descomenta la configuración en `application.properties`.

## 🗺️ Próximos pasos (roadmap)

### Fase 2 — CRUD de artículos (completar)

| Método | Ruta                          | Descripción                        |
|--------|-------------------------------|------------------------------------|
| GET    | /api/v1/articles              | Listar todos                       |
| GET    | /api/v1/articles/{id}         | Obtener por ID                     |
| GET    | /api/v1/articles?author={id}  | Buscar por autor                   |
| PUT    | /api/v1/articles/{id}         | Autor actualiza su artículo        |
| DELETE | /api/v1/articles/{id}         | Autor elimina su artículo          |
| DELETE | /api/v1/users/{id}            | Elimina usuario y artículos (cascada) |

### Fase 3 — Flujo de revisión
- `PATCH /api/v1/articles/{id}/submit` → autor envía a revisión (IN_REVIEW)
- `PATCH /api/v1/articles/{id}/approve` → manager aprueba (PUBLISHED)
- Endpoints para listar por estado: `/articles/status/draft`, `/in-review`, `/published`

### Fase 4 — Validaciones y calidad
- `@ControllerAdvice` para manejo global de excepciones
- DTOs request/response
- Tests con JUnit 5 y colección de Postman

## 👩‍💻 Equipo de Desarrollo

| Nombre | Rol | GitHub |
|---|---|---|
| Damaris Castro | Developer | [@damcb1](https://github.com/damcb1) |
| Ivanna Caraccio | Developer | [@IvannaRCA](https://github.com/IvannaRCA) |
| Rosa Maria Naharro| Developer & Scrum Master | [@rosana50factoria](https://github.com/orgs/Cinephile-Team-2/people/rosana50factoria) |
| Andrea Tapia | Developer & Product Owner  | [@atapiamallea](https://github.com/atapiamallea) |

---

