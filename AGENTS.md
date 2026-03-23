# FashionApp AI Agent Guidelines

## Architecture Overview
- **Layered Architecture**: REST controllers → services (business logic/validation) → repositories (data access) → JPA entities
- **Main Controllers**: 
  - `ClothController` (/api/clothes) - Full CRUD, search, filtering, pagination, stock management
  - `ReviewController` (/api/reviews) - Review management with automatic rating calculation
- **Service Layer**: `ClothService` (inventory, search, stock) and `ReviewService` (ratings, feedback)
- **Entities**: 
  - `Cloth` (id, name, category, price, description, imageUrl, stock, rating, createdAt, updatedAt)
  - `Review` (id, cloth, rating 1-5, comment, reviewerName, createdAt)
  - `Category` enum (TRADITIONAL, ENGLISH, SHIRT, TROUSER, SHOES)

## Key Features Implemented
- **Full CRUD Operations**: Create, read, update, delete clothes
- **Advanced Search**: By name, category, price range, combined filters
- **Pagination & Sorting**: Page-based results with customizable sorting
- **Stock Management**: Track inventory, prevent negative stock
- **Review System**: Rate clothes (1-5), calculate average ratings, review pagination
- **API Documentation**: Swagger UI at `/swagger-ui.html`, OpenAPI at `/api-docs`
- **Global Exception Handling**: Centralized error responses with timestamps and status codes
- **Comprehensive Logging**: SLF4J with DEBUG level for app, structured SQL logging

## Coding Patterns
- **Dependency Injection**: Field injection with `@Autowired` for services/repositories
- **Validation**: Service layer validation with custom exceptions (`InvalidPriceException`, `ResourceNotFoundException`)
- **Timestamp Management**: Set `createdAt` and `updatedAt` manually (e.g., `cloth.setCreatedAt(LocalDateTime.now())`)
- **Getters/Setters**: Implement manually; Lombok present but not used for entity mapping
- **Swagger Documentation**: Add `@Operation`, `@Tag`, `@Parameter` annotations to controller methods
- **Logging**: Use `LoggerFactory.getLogger(ClassName.class)` in services for key operations

## Repository Patterns
- **Custom Queries**: Use `@Query` for complex searches (price ranges, text search)
- **Pagination Support**: Repository methods return `Page<Entity>` for paginated results
- **Named Methods**: Simple queries use derived method names (e.g., `findByName()`, `findByCategory()`)

## Database & Configuration
- **Database**: PostgreSQL (localhost:5432/fashiondb, user: postgres, password: Emmanuel)
- **DDL**: `ddl-auto=create-drop` for development (recreates schema on restart)
- **Connection Pool**: Default HikariCP configuration
- **Logging**: Enable SQL logging via `spring.jpa.show-sql=true`; detailed SQL with `hibernate.format_sql=true`

## Development Workflows
- **Build**: `.\mvnw.cmd clean compile` (Windows) or `./mvnw clean compile` (Unix)
- **Run**: `.\mvnw.cmd spring-boot:run` starts server on port 8080; Swagger at http://localhost:8080/swagger-ui.html
- **Test**: `.\mvnw.cmd test` runs comprehensive JUnit tests covering CRUD, search, stock, validation
- **Clean Build**: `.\mvnw.cmd clean compile` to rebuild classes and resolve issues

## Key Files & Responsibilities
- `pom.xml`: Spring Boot 4.0.3, Java 21, PostgreSQL driver, SpringDoc OpenAPI (Swagger), testing dependencies
- `application.properties`: Database, JPA, server, logging, Swagger configuration
- `com.fashionapp.model/`: `Cloth`, `Review`, `Category` entities (no Lombok annotations)
- `com.fashionapp.controller/`: `ClothController`, `ReviewController` with Swagger annotations
- `com.fashionapp.service/`: `ClothService`, `ReviewService` with business logic and logging
- `com.fashionapp.repository/`: JPA repositories with custom query methods
- `com.fashionapp.exception/`: `ResourceNotFoundException`, `InvalidPriceException`, `GlobalExceptionHandler`

## Conventions
- **Package Structure**: `com.fashionapp.{model,controller,service,repository,exception}`
- **API Paths**: `/api/clothes` for cloth management, `/api/reviews` for review management
- **HTTP Status**: 201 for creation, 200 for success, 400 for validation errors, 404 for not found, 500 for server errors
- **Error Format**: JSON with timestamp, status, error name, message, and path
- **Response Format**: Entities returned directly; List responses use `Page<Entity>` for pagination</content>
<parameter name="filePath">C:\Users\Administrator\IdeaProjects\fashionapp\AGENTS.md
