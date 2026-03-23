# FashionApp - Full-Scale Fashion Clothing Management System

A comprehensive Spring Boot 4.0.3 application for managing fashion clothing inventory, reviews, and advanced search capabilities with PostgreSQL.

## 🚀 Features

### Core Functionality
- **Full CRUD Operations** - Create, read, update, delete clothing items
- **Advanced Search & Filtering** - Search by name, category, price range, and combined filters
- **Pagination & Sorting** - Efficient data retrieval with customizable pagination and sorting
- **Stock Management** - Track inventory levels and prevent negative stock
- **Review System** - User reviews and ratings (1-5 stars) with automatic average calculation
- **Global Exception Handling** - Centralized error responses with structured error messages
- **API Documentation** - Interactive Swagger UI with OpenAPI specification

### Technical Features
- **RESTful API** - Standard HTTP methods with proper status codes (201, 200, 400, 404, 500)
- **Database Integration** - PostgreSQL with JPA/Hibernate ORM
- **Comprehensive Logging** - SLF4J with DEBUG level for application logic and SQL logging
- **Input Validation** - Service-layer validation with custom exceptions
- **Pagination Support** - Spring Data JPA pagination for all list endpoints
- **Custom Query Methods** - Complex searches using `@Query` annotations

## 📋 Requirements

- **Java**: 21
- **Maven**: 3.6+
- **PostgreSQL**: 12+
- **Spring Boot**: 4.0.3

## 🛠️ Installation & Setup

### 1. Database Configuration

Create PostgreSQL database:
```bash
psql -U postgres
CREATE DATABASE fashiondb;
```

Update database credentials in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/fashiondb
spring.datasource.username=postgres
spring.datasource.password=YourPassword
```

### 2. Build the Application

```bash
# Windows
.\mvnw.cmd clean compile

# Linux/Mac
./mvnw clean compile
```

### 3. Run the Application

```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

Server will start on `http://localhost:8080`

### 4. Run Tests

```bash
# Windows
.\mvnw.cmd test

# Linux/Mac
./mvnw test
```

## 📚 API Endpoints

### Cloth Management

#### Create Cloth
```http
POST /api/clothes
Content-Type: application/json

{
  "name": "Blue Cotton Shirt",
  "category": "SHIRT",
  "price": 29.99,
  "description": "Premium cotton shirt",
  "imageUrl": "https://example.com/image.jpg",
  "stock": 50
}
```

#### Get All Clothes (with pagination)
```http
GET /api/clothes?page=0&size=20&sort=name,asc
```

#### Get Cloth by ID
```http
GET /api/clothes/{id}
```

#### Update Cloth
```http
PUT /api/clothes/{id}
Content-Type: application/json

{
  "price": 34.99,
  "stock": 45,
  "description": "Updated description"
}
```

#### Delete Cloth
```http
DELETE /api/clothes/{id}
```

### Search & Filtering

#### Search by Category
```http
GET /api/clothes/search/category?category=SHIRT&page=0&size=20
```

#### Search by Price Range
```http
GET /api/clothes/search/price-range?minPrice=20&maxPrice=100&page=0&size=20
```

#### Search by Name
```http
GET /api/clothes/search/name?searchTerm=shirt&page=0&size=20
```

#### Get In-Stock Items
```http
GET /api/clothes/stock/in-stock?page=0&size=20
```

#### Search by Category and Price
```http
GET /api/clothes/search/category-price?category=SHIRT&minPrice=20&maxPrice=50&page=0&size=20
```

### Stock Management

#### Update Stock
```http
PATCH /api/clothes/{clothId}/stock?quantity=5
```

### Review Management

#### Add Review for Cloth
```http
POST /api/reviews/cloth/{clothId}
Content-Type: application/json

{
  "rating": 4,
  "comment": "Great quality shirt!",
  "reviewerName": "John Doe"
}
```

#### Get Reviews for Cloth
```http
GET /api/reviews/cloth/{clothId}?page=0&size=20
```

#### Get Average Rating
```http
GET /api/reviews/cloth/{clothId}/rating
```

#### Get Review Count
```http
GET /api/reviews/cloth/{clothId}/count
```

#### Delete Review
```http
DELETE /api/reviews/{reviewId}
```

## 📊 Data Models

### Cloth Entity
```
{
  "id": 1,
  "name": "Blue Shirt",
  "category": "SHIRT",
  "price": 29.99,
  "description": "Premium cotton shirt",
  "imageUrl": "https://example.com/image.jpg",
  "stock": 50,
  "rating": 4,
  "createdAt": "2026-04-03T12:00:00",
  "updatedAt": "2026-04-03T14:30:00"
}
```

### Review Entity
```
{
  "id": 1,
  "cloth": { /* Cloth object */ },
  "rating": 5,
  "comment": "Excellent quality!",
  "reviewerName": "Jane Smith",
  "createdAt": "2026-04-03T13:15:00"
}
```

### Category Enum
- TRADITIONAL
- ENGLISH
- SHIRT
- TROUSER
- SHOES

## 🔍 Swagger Documentation

Access interactive API documentation:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs

## 📝 Configuration

Key configuration in `application.properties`:

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/fashiondb
spring.jpa.hibernate.ddl-auto=create-drop

# Logging
logging.level.com.fashionapp=DEBUG
logging.level.org.hibernate.SQL=DEBUG

# Swagger
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.enabled=true
```

## 🧪 Testing

The application includes comprehensive unit and integration tests:

```bash
# Run all tests
./mvnw test

# Run specific test class
./mvnw test -Dtest=ApplicationTests

# Run with verbose output
./mvnw test -X
```

**Test Coverage:**
- ✅ Create cloth operations
- ✅ Read and retrieve operations
- ✅ Update functionality
- ✅ Delete operations
- ✅ Search by category
- ✅ Price range filtering
- ✅ Name search
- ✅ Stock management
- ✅ Invalid price validation
- ✅ Exception handling

## 🏗️ Project Structure

```
fashionapp/
├── src/main/java/com/fashionapp/
│   ├── Application.java              # Main entry point
│   ├── controller/
│   │   ├── ClothController.java       # Cloth REST endpoints
│   │   └── ReviewController.java      # Review REST endpoints
│   ├── model/
│   │   ├── Cloth.java                 # Cloth JPA entity
│   │   ├── Review.java                # Review JPA entity
│   │   └── Category.java              # Category enum
│   ├── service/
│   │   ├── ClothService.java          # Cloth business logic
│   │   └── ReviewService.java         # Review business logic
│   ├── repository/
│   │   ├── ClothRepository.java       # Cloth data access
│   │   └── ReviewRepository.java      # Review data access
│   └── exception/
│       ├── GlobalExceptionHandler.java # Centralized error handling
│       ├── ResourceNotFoundException.java
│       └── InvalidPriceException.java
├── src/main/resources/
│   └── application.properties          # Configuration
├── src/test/java/com/fashionapp/
│   └── ApplicationTests.java           # Integration tests
├── pom.xml                             # Maven configuration
└── AGENTS.md                           # AI agent guidelines
```

## 🔑 Key Design Patterns

1. **Layered Architecture** - Separation of concerns (Controller → Service → Repository → Entity)
2. **Dependency Injection** - Spring @Autowired for loose coupling
3. **Custom Exceptions** - `ResourceNotFoundException`, `InvalidPriceException`
4. **Repository Pattern** - JPA repositories with custom query methods
5. **Validation** - Service-layer validation with meaningful error messages
6. **Pagination** - Spring Data Page for efficient large dataset retrieval
7. **Logging** - SLF4J for structured application logging
8. **API Documentation** - Swagger annotations for self-documenting REST APIs

## 🐛 Error Handling

Centralized error responses with the following format:

```json
{
  "timestamp": "2026-04-03T12:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Cloth not found with ID: 999",
  "path": "/api/clothes/999"
}
```

## 📈 Future Enhancements

- Authentication and authorization (JWT)
- Advanced analytics and reporting
- File upload for product images
- Email notifications for stock alerts
- Caching (Redis) for performance
- Batch operations
- API versioning
- Rate limiting
- Full text search optimization

## 📄 License

This project is part of the fashionapp system.

## 👥 Support

For issues or questions, please refer to the AGENTS.md file for coding conventions and architecture guidelines.

