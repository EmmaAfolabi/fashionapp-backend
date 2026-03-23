@echo off
REM FashionApp Quick Commands (Windows)
REM Run any of these commands from the project root directory

echo.
echo ============================================
echo FashionApp - Quick Command Reference
echo ============================================
echo.

echo Available commands:
echo.
echo 1. Clean Build
echo    Command: mvnw.cmd clean compile
echo    Description: Rebuild all classes
echo.
echo 2. Build Package
echo    Command: mvnw.cmd clean package -DskipTests
echo    Description: Create executable JAR at target/fashionapp-0.0.1-SNAPSHOT.jar
echo.
echo 3. Run Application
echo    Command: mvnw.cmd spring-boot:run
echo    Description: Start Spring Boot app on localhost:8080
echo.
echo 4. Run Tests
echo    Command: mvnw.cmd test
echo    Description: Execute all 10 test cases
echo.
echo 5. Quick Start (Build + Run)
echo    Command: mvnw.cmd clean compile spring-boot:run
echo    Description: Build and immediately run the application
echo.
echo 6. Docker - Build Image
echo    Command: docker build -t fashionapp:latest .
echo    Description: Create Docker image
echo.
echo 7. Docker - Full Stack
echo    Command: docker-compose up -d
echo    Description: Start PostgreSQL + Application with Docker Compose
echo.
echo 8. Docker - View Logs
echo    Command: docker-compose logs -f fashionapp
echo    Description: Stream application logs from Docker container
echo.
echo 9. Docker - Stop All
echo    Command: docker-compose down
echo    Description: Stop and remove all containers
echo.
echo 10. Access Swagger UI
echo     URL: http://localhost:8080/swagger-ui.html
echo     Description: Interactive API documentation
echo.
echo 11. View API Documentation
echo     URL: http://localhost:8080/api-docs
echo     Description: OpenAPI specification
echo.
echo 12. Test with cURL
echo     Command: curl http://localhost:8080/api/clothes
echo     Description: Get all clothes
echo.
echo ============================================
echo Documentation Files:
echo ============================================
echo - README.md              : Main documentation
echo - DEVELOPMENT.md         : Development setup guide
echo - API_EXAMPLES.md        : 17 detailed API examples
echo - BUILD_SUMMARY.md       : Complete build report
echo - AGENTS.md              : AI agent guidelines
echo.
echo ============================================

