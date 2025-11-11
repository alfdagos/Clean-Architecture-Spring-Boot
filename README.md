# Clean Architecture - Java (Spring Boot)

This project is a Java codebase that applies the principles of Clean Architecture.
It provides a structured and maintainable starting point for building Spring Boot applications.

Main features
- Layered architecture: domain, repository, service, controller (presentation)
- Persistence: JPA + PostgreSQL
- Migrations: Flyway (folder `src/main/resources/db/migration`)
- Authentication: JWT (HS256) with subject=userId and `email` claim
- Password hashing: PBKDF2WithHmacSHA512
- Logging: Logback + Logstash encoder -> Seq (docker-compose exposes port 5341)
- Domain events: simple collection in `BaseEntity` and publishing via ApplicationEventPublisher after save
- Permission system: `Permission` entity + `PermissionService` for authorization checks

Why Clean Architecture
----------------------
Clean Architecture separates responsibilities into clear layers to make the code more maintainable, testable and independent from frameworks or persistence technologies.

Key principles applied in this project:
- Entities (Domain): contain domain logic and data models (folder `domain`).
- Use cases / Services (Application): orchestrate high-level operations (folder `service`).
- Adapters / Infrastructure: technical implementations (JPA repositories, JWT provider, password hashing).
- Presentation: REST controllers that expose API endpoints.

Design choices
--------------
- Layer separation: code is organized in packages `domain`, `repository`, `service`, `controller`, `security`, `permission`, `mapper`, `exception`.
- Dependency direction: services depend on repository interfaces and not on the framework directly (Spring wiring uses constructor injection).
- Domain Events: entities can collect domain events via `BaseEntity`. Services publish these events after persistence using `ApplicationEventPublisher`.
- Password hashing: PBKDF2WithHmacSHA512 configured with 16 bytes salt, 500k iterations and 32 bytes hash.
- Authentication and authorization: JWT with subject=userId and `email` claim. `SecurityConfig` adds a filter that validates the token and sets the principal to the userId; `SecurityUserContext` extracts the userId for services.
- Permissions: `permissions` table and `PermissionService` provide authorization checks.

Running and development
-----------------------
Prerequisites:
- Java 21
- Maven
- Docker (to run docker-compose)

Build the project:

```powershell
mvn -DskipTests clean package
```

Start with Docker:

```powershell
docker compose up --build
```

Main endpoints
- POST /api/users/register -> registers a user
- POST /api/users/login -> returns { token }
- GET /api/todos -> list todos (authentication required)
- POST /api/todos -> create todo (authentication required)
- PUT /api/todos/{id} -> update todo (must be owner or have permission 'todos.manage')

Operational notes and recommendations
-------------------------------------
- JWT secret: DO NOT use the hard-coded value in production. Use a secret manager or environment variables.
- Flyway: if your database already has a baseline, add incremental migrations instead of modifying `V1__init.sql`.
- Tests: add unit and integration tests (e.g., Mockito for services, Testcontainers for DB).
- Domain Events: currently events are published by services after persistence; consider transaction synchronization or repository wrappers to publish automatically after commit.

Directory structure (relevant)

- `src/main/java/it/alf/cleana/domain` - entities and domain events
- `src/main/java/it/alf/cleana/repository` - JPA repositories
- `src/main/java/it/alf/cleana/service` - use cases / services
- `src/main/java/it/alf/cleana/controller` - REST API
- `src/main/java/it/alf/cleana/security` - JWT provider, PBKDF2 hasher
- `src/main/resources/db/migration` - Flyway migrations
- `docker-compose.yml` - compose file for Postgres and Seq

Next steps / offers
- I can also:
  - add tests + Testcontainers,
  - integrate a custom `PermissionEvaluator` and use `@PreAuthorize` for method-level authorization,
  - improve OpenAPI/Swagger with schemas and security definitions,
  - add a central mechanism to publish domain events automatically post-commit.

