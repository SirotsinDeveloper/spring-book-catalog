# Book Catalog API

REST API for managing a book catalog.

## Features

- Add a book
- Get book by id
- List books with pagination
- Search books by title
- Delete a book

## Tech stack

- Java 17
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- PostgreSQL
- Lombok
- Gradle

## API

### Create book

`POST /books`

Request body (`RequestBookDto`):

```json
{
  "title": "Clean Code",
  "author": "Robert Martin",
  "publishedYear": 2008
}
```

Response 201 (`ResponseBookDto`):

```json
{
  "id": 1,
  "title": "Clean Code",
  "author": "Robert Martin",
  "publishedYear": 2008
}
```

### Get book by id

`GET /books/{id}`

### List / search

`GET /books?page=0&size=5&sort=title,asc`

`GET /books?title=java&page=0&size=5`

### Delete

`DELETE /books/{id}` returns `204 No Content`

## Validation

`POST /books` uses bean validation:
- `title`, `author`: `@NotBlank`
- `publishedYear`: `@Min`, `@Max`

## Error handling

Global handler (`@RestControllerAdvice`) returns `ErrorResponseDto`:

```json
{
  "timestamp": "2026-03-27T20:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Book not found",
  "path": "/books/1"
}
```

## Run (local profile)

Create `src/main/resources/application-local.yaml` (this file contains secrets and is ignored by git):

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/postgres
    username: postgres
    password: your_password
    driver-class-name: org.postgresql.Driver
```

Run with profile:

PowerShell:

```
$env:SPRING_PROFILES_ACTIVE="local"
./gradlew bootRun
```

## Roadmap

- Docker
    - Add `Dockerfile` for the app
    - Add `docker-compose.yml` for app + PostgreSQL
    - Use environment variables for config/secrets
- Flyway migrations
    - Add Flyway and versioned SQL migrations (`V1__...sql`, etc.)
    - Remove manual schema changes and keep DB schema in migrations
- Tests
    - Unit tests for service/mapper
    - Controller tests (`@WebMvcTest`)
    - Integration tests with PostgreSQL (preferably Testcontainers)
- Swagger / OpenAPI
    - Add Swagger UI (OpenAPI documentation) for all endpoints
- Auth + roles
    - Add Spring Security (authentication)
    - Add role-based access control (RBAC) for endpoints (e.g. `USER`, `ADMIN`)
    - Protect write endpoints (create/delete/update) and keep read endpoints public or role-based

## Run with Docker (app + database)

Requirements:
- Docker Desktop (or Docker Engine) with `docker compose`

Start:

```
docker compose up --build
```

App will be available at:
- `http://localhost:8080`

Stop:

```
docker compose down
```

Remove DB volume (optional):

```
docker compose down -v
```
