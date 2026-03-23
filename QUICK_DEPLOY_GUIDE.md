# 🚀 FashionApp - Deployment & Startup Guide

## Table of Contents
1. [Quick Start](#quick-start)
2. [Local Development](#local-development)
3. [Docker Deployment](#docker-deployment)
4. [Verification & Testing](#verification--testing)

---

## Quick Start

### In 30 Seconds (Windows)
```batch
REM 1. Build
mvnw.cmd spring-boot:run

REM Then open browser to:
REM http://localhost:8080/swagger-ui.html
```

### In 30 Seconds (Linux/Mac)
```bash
# 1. Build and run
./mvnw spring-boot:run

# Then open browser to:
# http://localhost:8080/swagger-ui.html
```

---

## Local Development

### Prerequisites
```bash
# Check Java version
java -version
# Expected: Java 21.x or higher

# Check Maven
mvn -version
# Expected: Maven 3.6 or higher

# Check PostgreSQL
psql --version
# Expected: PostgreSQL 12 or higher
```

### Setup Steps

#### Step 1: Clone & Navigate
```bash
cd C:\Users\Administrator\IdeaProjects\fashionapp
```

#### Step 2: Ensure Database Exists
```bash
psql -U postgres -c "CREATE DATABASE fashiondb;"
```

#### Step 3: Configure Database (if needed)
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/fashiondb
spring.datasource.username=postgres
spring.datasource.password=Emmanuel
```

#### Step 4: Build Project
```bash
# Windows
mvnw.cmd clean compile

# Linux/Mac
./mvnw clean compile
```

#### Step 5: Run Application
```bash
# Windows
mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

#### Step 6: Access Application
- Open browser: http://localhost:8080/swagger-ui.html
- View API docs: http://localhost:8080/api-docs

---

## Docker Deployment

### Option 1: Docker Compose (Recommended for Local)

#### Prerequisites
```bash
docker --version      # Docker 20.10+
docker-compose --version  # Docker Compose 1.29+
```

#### Commands

**Start Everything**
```bash
docker-compose up -d
```

**View Logs**
```bash
docker-compose logs -f fashionapp
```

**View Specific Service Logs**
```bash
# Application logs
docker-compose logs -f fashionapp

# Database logs
docker-compose logs -f postgres
```

**Stop Everything**
```bash
docker-compose down
```

**Clean Everything (including data)**
```bash
docker-compose down -v
```

**Verify Services**
```bash
docker-compose ps
```

**Restart Services**
```bash
docker-compose restart fashionapp
```

**Access Application**
- Swagger UI: http://localhost:8080/swagger-ui.html
- API Docs: http://localhost:8080/api-docs
- Database: localhost:5432

---

### Option 2: Manual Docker (For Production)

#### Build Image
```bash
docker build -t fashionapp:v1.0.0 .
```

#### Run Container
```bash
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://YOUR_DB_HOST:5432/fashiondb \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=YourPassword \
  fashionapp:v1.0.0
```

#### Push to Registry
```bash
# Example: Docker Hub
docker tag fashionapp:v1.0.0 yourusername/fashionapp:v1.0.0
docker push yourusername/fashionapp:v1.0.0
```

---

## Maven Commands Reference

### Build Commands
```bash
# Clean build
mvnw.cmd clean compile

# Build with tests
mvnw.cmd clean compile test

# Package as JAR
mvnw.cmd clean package

# Package without tests
mvnw.cmd clean package -DskipTests

# Build and run immediately
mvnw.cmd clean compile spring-boot:run
```

### Testing Commands
```bash
# Run all tests
mvnw.cmd test

# Run specific test class
mvnw.cmd test -Dtest=ApplicationTests

# Run with detailed output
mvnw.cmd test -X

# Skip tests during build
mvnw.cmd package -DskipTests
```

### Development Commands
```bash
# Run application (auto-reload on file changes)
mvnw.cmd spring-boot:run

# Check dependencies
mvnw.cmd dependency:tree

# Update dependencies
mvnw.cmd versions:display-dependency-updates
```

---

## Verification & Testing

### Health Check

#### Browser
Open: http://localhost:8080/swagger-ui.html
Expected: Interactive Swagger UI loads

#### Command Line (Windows PowerShell)
```powershell
curl http://localhost:8080/api/clothes
```

#### Command Line (Linux/Mac)
```bash
curl http://localhost:8080/api/clothes
```

Expected Response:
```json
{
  "content": [],
  "pageable": {...},
  "totalElements": 0,
  "totalPages": 0,
  "first": true,
  "last": true
}
```

### Test API Endpoints

#### Create a Cloth
```bash
curl -X POST http://localhost:8080/api/clothes \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test Shirt",
    "category": "SHIRT",
    "price": 29.99,
    "stock": 50
  }'
```

#### Get All Clothes
```bash
curl http://localhost:8080/api/clothes
```

#### Search by Name
```bash
curl "http://localhost:8080/api/clothes/search/name?searchTerm=shirt"
```

#### Get by ID
```bash
curl http://localhost:8080/api/clothes/1
```

### Run Test Suite
```bash
# Windows
mvnw.cmd test

# Linux/Mac
./mvnw test

# Expected output: 10/10 tests passing
```

---

## Troubleshooting

### Port Already in Use (8080)
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Linux/Mac
lsof -ti:8080 | xargs kill -9
```

### Database Connection Error
```bash
# Check if PostgreSQL is running
psql -U postgres -c "SELECT 1"

# If error, start PostgreSQL
# Windows: net start PostgreSQL
# Mac: brew services start postgresql
# Linux: sudo systemctl start postgresql
```

### Docker Issues
```bash
# View all containers
docker ps -a

# View logs
docker logs <container_id>

# Remove stopped containers
docker container prune

# Remove unused images
docker image prune
```

### Build Failures
```bash
# Clean cache and rebuild
mvnw.cmd clean compile

# Force download dependencies
mvnw.cmd dependency:purge-local-repository clean compile

# Check Java version
java -version
# Must be Java 21+
```

---

## Environment Variables

### Development (.env.example)
```
SPRING_PROFILES_ACTIVE=dev
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/fashiondb
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=Emmanuel
LOGGING_LEVEL_COM_FASHIONAPP=DEBUG
```

### Production
```
SPRING_PROFILES_ACTIVE=prod
SPRING_DATASOURCE_URL=jdbc:postgresql://prod-host:5432/fashiondb
SPRING_DATASOURCE_USERNAME=prod_user
SPRING_DATASOURCE_PASSWORD=secure_password_here
LOGGING_LEVEL_COM_FASHIONAPP=INFO
SERVER_PORT=8080
```

---

## Performance Tips

### Local Development
```bash
# Run with debug logging
LOGGING_LEVEL_COM_FASHIONAPP=DEBUG mvnw.cmd spring-boot:run

# Run without tests for faster builds
mvnw.cmd clean package -DskipTests
```

### Docker Optimization
```bash
# Use smaller base image
# Already optimized: eclipse-temurin:21-jre-alpine

# Limit container resources
docker run -p 8080:8080 \
  -m 1g \
  --cpus="1.0" \
  fashionapp:v1.0.0
```

---

## Monitoring & Logs

### View Application Logs
```bash
# Docker Compose
docker-compose logs -f fashionapp

# Specific number of lines
docker-compose logs -f --tail 100 fashionapp

# Docker standalone
docker logs -f <container_id>

# Local (from console where app is running)
# Logs appear in stdout
```

### Log Locations
```
Local Development: Console (stdout)
Docker: docker logs <container_id>
File-based: Configure in application.properties
```

### View Database Logs
```bash
# PostgreSQL in Docker
docker-compose logs -f postgres

# Local PostgreSQL
# Platform-specific (check PostgreSQL installation)
```

---

## Production Checklist

- [ ] Update database credentials (not hardcoded)
- [ ] Set LOGGING_LEVEL to INFO (not DEBUG)
- [ ] Configure HTTPS/SSL
- [ ] Update application.properties for production
- [ ] Run full test suite
- [ ] Create database backups
- [ ] Configure monitoring
- [ ] Set up auto-restart on failure
- [ ] Enable health checks
- [ ] Plan rollback strategy

---

## Support Commands

### Get Help
```bash
# Maven help
mvnw.cmd help

# Spring Boot help
mvnw.cmd spring-boot:help

# Docker help
docker help
docker-compose help
```

### Report Issues
```bash
# Collect diagnostics
mvnw.cmd dependency:tree > dependencies.txt
java -version > java_version.txt
docker version > docker_version.txt

# Include in issue report
```

---

## Summary

### Fastest Way to Start
```bash
docker-compose up -d
# Then open http://localhost:8080/swagger-ui.html
```

### For Development
```bash
./mvnw spring-boot:run
# Then open http://localhost:8080/swagger-ui.html
```

### For Production
```bash
docker build -t fashionapp:v1.0 .
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=<prod_db_url> \
  -e SPRING_DATASOURCE_USERNAME=<username> \
  -e SPRING_DATASOURCE_PASSWORD=<password> \
  fashionapp:v1.0
```

---

**FashionApp - Ready to Deploy! 🚀**

For detailed information, see:
- README.md - Full documentation
- DEVELOPMENT.md - Developer guide
- API_EXAMPLES.md - API testing

