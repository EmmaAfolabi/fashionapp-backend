# FashionApp Development Guide

## Quick Start

### Prerequisites
- Java 21+
- Maven 3.6+
- PostgreSQL 12+
- Git

### Local Development Setup

1. **Clone and Navigate**
   ```bash
   cd fashionapp
   ```

2. **Configure Database**
   ```bash
   # Update credentials in src/main/resources/application.properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/fashiondb
   spring.datasource.username=postgres
   spring.datasource.password=Emmanuel
   ```

3. **Build Project**
   ```bash
   ./mvnw clean compile
   ```

4. **Run Tests**
   ```bash
   ./mvnw test
   ```

5. **Start Application**
   ```bash
   ./mvnw spring-boot:run
   ```

6. **Access Swagger UI**
   - Navigate to: http://localhost:8080/swagger-ui.html
   - View API docs: http://localhost:8080/api-docs

## Docker Deployment

### Build and Run with Docker Compose

```bash
# Start all services (PostgreSQL + Application)
docker-compose up -d

# View logs
docker-compose logs -f fashionapp

# Stop services
docker-compose down

# Clean volumes
docker-compose down -v
```

### Build Docker Image Only

```bash
# Build image
docker build -t fashionapp:latest .

# Run image
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/fashiondb \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=Emmanuel \
  fashionapp:latest
```

## Development Workflows

### Adding a New Feature

1. **Create Model** → `src/main/java/com/fashionapp/model/`
2. **Create Repository** → `src/main/java/com/fashionapp/repository/`
3. **Create Service** → `src/main/java/com/fashionapp/service/` (with logging)
4. **Create Controller** → `src/main/java/com/fashionapp/controller/` (with Swagger annotations)
5. **Add Tests** → `src/test/java/com/fashionapp/`
6. **Update AGENTS.md** → Document the new patterns

### Code Style

- Use field injection with `@Autowired`
- Implement all getters/setters manually (no Lombok `@Data`)
- Add logging in service layer: `LoggerFactory.getLogger(ClassName.class)`
- Set timestamps manually: `LocalDateTime.now()`
- Use custom exceptions: `ResourceNotFoundException`, `InvalidPriceException`
- Add Swagger annotations to all controller methods
- Return `Page<Entity>` for list endpoints (pagination support)

### Validation Rules

- Prices must be > 0 (throw `InvalidPriceException`)
- Stock cannot be negative
- Review ratings must be 1-5
- All entities require unique names within their domain

## Maven Commands Reference

```bash
# Clean build
./mvnw clean compile

# Package as JAR
./mvnw clean package

# Run application
./mvnw spring-boot:run

# Run tests
./mvnw test

# Run specific test
./mvnw test -Dtest=ApplicationTests

# Skip tests during build
./mvnw clean package -DskipTests

# Check dependencies
./mvnw dependency:tree

# Update project info
./mvnw project-info-reports:index
```

## Database Migrations

The application uses `ddl-auto=create-drop` for development (schema is recreated on restart).

For production, update to:
```properties
spring.jpa.hibernate.ddl-auto=update
# or use Flyway/Liquibase for versioned migrations
```

## Logging Configuration

### Current Setup
- **Root Level**: INFO
- **Application** (`com.fashionapp`): DEBUG
- **Hibernate SQL**: DEBUG
- **Hibernate Bindings**: TRACE

### Modify in `application.properties`
```properties
logging.level.com.fashionapp=DEBUG
logging.level.org.springframework.web=INFO
logging.level.org.hibernate.SQL=DEBUG
```

## Performance Optimization Tips

1. **Enable Query Caching**
   ```properties
   spring.jpa.properties.hibernate.cache.use_second_level_cache=true
   ```

2. **Add Database Indexes** - Consider adding on frequently queried columns:
   - `clothes.category`
   - `clothes.price`
   - `reviews.cloth_id`

3. **Use Pagination** - Always paginate list endpoints (default: size=20)

4. **Consider Caching Layer** - Redis for:
   - Average ratings
   - Category listings
   - Popular searches

## Troubleshooting

### Database Connection Issues
```bash
# Check PostgreSQL is running
psql -U postgres -c "SELECT 1"

# Verify connection parameters in application.properties
```

### Port Already in Use
```bash
# Kill process on port 8080 (Windows)
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Linux/Mac
lsof -ti:8080 | xargs kill -9
```

### Tests Fail with "Cloth not found"
- Tests use H2 in-memory database in test profile
- Ensure `application-test.properties` exists for test-specific configuration

### Swagger UI Not Loading
- Check http://localhost:8080/api-docs returns JSON
- Verify `springdoc-openapi-starter-webmvc-ui` dependency in pom.xml

## CI/CD Integration

### GitHub Actions Example
```yaml
name: Build and Test

on: [push, pull_request]

jobs:
  build:
    runs-on: ubuntu-latest
    
    services:
      postgres:
        image: postgres:15-alpine
        env:
          POSTGRES_DB: fashiondb
          POSTGRES_PASSWORD: Emmanuel
    
    steps:
      - uses: actions/checkout@v3
      - name: Set up Java
        uses: actions/setup-java@v3
        with:
          java-version: '21'
      - name: Build and test
        run: ./mvnw clean verify
```

## Contributing Guidelines

1. Fork the repository
2. Create feature branch: `git checkout -b feature/amazing-feature`
3. Commit changes: `git commit -m 'Add amazing feature'`
4. Push to branch: `git push origin feature/amazing-feature`
5. Open Pull Request
6. Ensure all tests pass: `./mvnw test`

## Production Deployment Checklist

- [ ] Update `ddl-auto=update` (or use migrations)
- [ ] Configure environment variables (no hardcoded credentials)
- [ ] Set logging level to INFO (not DEBUG)
- [ ] Enable HTTPS/SSL
- [ ] Configure database backups
- [ ] Set up monitoring and alerting
- [ ] Enable caching (Redis/Memcached)
- [ ] Review security settings
- [ ] Load test the application
- [ ] Plan rollback strategy

