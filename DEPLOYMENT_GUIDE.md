# Deployment & Production Guide

## Pre-Deployment Checklist

- [ ] All tests pass (`mvn test`)
- [ ] No compilation warnings (`mvn clean compile`)
- [ ] Code review completed
- [ ] Database backups configured
- [ ] Environment variables configured
- [ ] CORS settings verified for frontend domain
- [ ] Swagger UI disabled in production
- [ ] Logging levels appropriate for environment

## Environment Setup

### Development
```bash
mvn spring-boot:run
# Uses application-dev.properties
# DDL: create-drop (recreates on startup)
# Debug logging enabled
```

### Production
```bash
export DB_PASSWORD=<your-secure-password>
export SPRING_PROFILES_ACTIVE=prod
java -jar fashionapp-0.0.1-SNAPSHOT.jar
```

## Database Configuration

### PostgreSQL Setup
```sql
CREATE DATABASE fashiondb;
CREATE USER fashionapp WITH PASSWORD 'secure_password';
GRANT ALL PRIVILEGES ON DATABASE fashiondb TO fashionapp;
```

### Connection Pooling (HikariCP - Default)
- Max pool size: 10
- Min idle: 2
- Connection timeout: 30s
- Idle timeout: 10min

### DDL Strategy
- **Development**: `create-drop` (recreates schema on restart)
- **Production**: `validate` (checks schema without changes)

## Building for Production

### Step 1: Clean Build
```bash
mvn clean compile
```

### Step 2: Run All Tests
```bash
mvn test
```

### Step 3: Package Application
```bash
mvn clean package
```

This generates: `target/fashionapp-0.0.1-SNAPSHOT.jar`

### Step 4: Verify JAR
```bash
jar -tf target/fashionapp-0.0.1-SNAPSHOT.jar | grep application.properties
```

## Docker Deployment

### Build Docker Image
```bash
docker build -t fashionapp:1.0.0 .
```

### Run Container
```bash
docker run -p 8080:8080 \
  -e DB_PASSWORD=secure_password \
  -e SPRING_PROFILES_ACTIVE=prod \
  fashionapp:1.0.0
```

### Docker Compose
```bash
docker-compose up -d
```

## Performance Optimization

### Caching (Future)
- Implement Redis for frequently accessed clothing data
- Cache average ratings calculation

### Database Optimization
- Add indexes on frequently queried columns:
  ```sql
  CREATE INDEX idx_category ON clothes(category);
  CREATE INDEX idx_price ON clothes(price);
  CREATE INDEX idx_stock ON clothes(stock);
  ```

### JPA Optimization
- Use `@Query` with fetch joins to prevent N+1 queries
- Lazy load collections where appropriate

## Monitoring & Logging

### Log Levels in Production
```properties
logging.level.root=WARN
logging.level.com.fashionapp=INFO
logging.level.org.springframework=WARN
logging.level.org.hibernate=WARN
```

### Key Metrics to Monitor
- Request response time
- Error rate (5xx responses)
- Database connection pool usage
- JVM memory usage
- SQL query performance

## Security Checklist

- [ ] Remove debug logging in production
- [ ] Disable Swagger UI in production
  ```properties
  springdoc.swagger-ui.enabled=false
  ```
- [ ] Use environment variables for database credentials
- [ ] Enable HTTPS/TLS
- [ ] Configure CORS for specific frontend domain only
- [ ] Implement rate limiting
- [ ] Add authentication/authorization (JWT)
- [ ] Validate all input data
- [ ] Use parameterized queries (already in use)

## Backup & Recovery

### Database Backup
```bash
pg_dump -U postgres fashiondb > backup_$(date +%Y%m%d).sql
```

### Restore from Backup
```bash
psql -U postgres fashiondb < backup_20240409.sql
```

## Troubleshooting

### Port Already in Use
```bash
# Find process on port 8080
lsof -i :8080
# Kill process
kill -9 <PID>
```

### Database Connection Issues
1. Verify PostgreSQL is running
2. Check database credentials
3. Verify network connectivity
4. Check firewall rules

### Memory Issues
```bash
java -Xmx1024m -Xms512m -jar fashionapp-0.0.1-SNAPSHOT.jar
```

### Slow Queries
- Enable query logging: `spring.jpa.show-sql=true`
- Check PostgreSQL query plan: `EXPLAIN ANALYZE`
- Review connection pool settings

## Continuous Integration/Deployment

### GitHub Actions Pipeline (Recommended)
1. Push to main branch
2. Run tests
3. Build Docker image
4. Push to registry
5. Deploy to production

### Manual Deployment Steps
1. Clone repository
2. Run `mvn clean package`
3. Stop old application
4. Deploy new JAR
5. Restart application
6. Verify health checks

## Health Check Endpoint

Add health check endpoint (future):
```
GET /api/health
```

Expected response:
```json
{
  "status": "UP",
  "database": "UP",
  "components": {
    "db": {
      "status": "UP"
    }
  }
}
```

## Rollback Procedure

1. Keep previous JAR versions
2. Document each deployment
3. In case of issues, restart with previous JAR
4. Verify all functionality works
5. Investigate issue and redeploy

## Support & Maintenance

- Monitor application logs daily
- Review slow query logs weekly
- Update dependencies monthly
- Full security audit quarterly

