![CI](https://github.com/Amiri-69/hexlet-spring-blog/actions/workflows/ci.yml/badge.svg)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Amiri-69_hexlet-spring-blog&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Amiri-69_hexlet-spring-blog)

# Hexlet Spring Blog

Учебный REST API проект на Spring Boot для работы с пользователями и публикациями.

## Technologies

- Java 21
- Spring Boot 3.3.5
- Spring Data JPA
- H2 Database
- Gradle
- JUnit 5
- MockMvc
- JaCoCo
- GitHub Actions
- SonarCloud

## Run project

```bash
./gradlew bootRun
```

## Run tests

```bash
./gradlew clean test
```

## Coverage

```bash
./gradlew jacocoTestReport
```

Coverage report:

```text
build/reports/jacoco/test/html/index.html
```

## Users API

### Get all users

GET /api/users

### Get user by id

GET /api/users/{id}

### Create user

POST /api/users

### Update user

PUT /api/users/{id}

### Delete user

DELETE /api/users/{id}

## Posts API

### Get all posts

GET /api/posts

### Get post by id

GET /api/posts/{id}

### Create post

POST /api/posts

### Update post

PUT /api/posts/{id}

### Delete post

DELETE /api/posts/{id}