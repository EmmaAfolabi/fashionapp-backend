# Testing Guide

## Running Tests

### Run All Tests
```bash
# Windows
.\mvnw.cmd test

# Linux/Mac
./mvnw test
```

### Run Specific Test Class
```bash
./mvnw test -Dtest=ApplicationTests
```

### Run with Verbose Output
```bash
./mvnw test -X
```

### Generate Test Report
```bash
./mvnw test
# Report generated in: target/surefire-reports/
```

## Test Coverage

### Cloth Controller Tests
- ✅ Add cloth with valid data
- ✅ Get all clothes with pagination
- ✅ Get cloth by ID
- ✅ Update cloth details
- ✅ Delete cloth
- ✅ Search by category
- ✅ Search by price range
- ✅ Search by name
- ✅ Get in-stock items
- ✅ Search by category and price
- ✅ Update stock quantity

### Cloth Service Tests
- ✅ Add cloth validates price > 0
- ✅ Get cloth throws exception if not found
- ✅ Update cloth updates timestamp
- ✅ Delete cloth removes from database
- ✅ Stock cannot go negative
- ✅ Price must be positive

### Review Service Tests
- ✅ Add review for cloth
- ✅ Rating must be 1-5
- ✅ Delete review updates average rating
- ✅ Get reviews for cloth
- ✅ Calculate average rating

## Test Data Setup

Sample test cloth:
```java
Cloth cloth = new Cloth();
cloth.setName("Test Shirt");
cloth.setCategory(Category.SHIRT);
cloth.setPrice(new BigDecimal("29.99"));
cloth.setDescription("A test shirt");
cloth.setStock(10);
```

Sample test review:
```java
Review review = new Review();
review.setRating(4);
review.setComment("Great quality!");
review.setReviewerName("John Doe");
```

## Writing New Tests

### Test Structure
```java
@SpringBootTest
class YourTest {
    
    @Autowired
    private YourService yourService;
    
    @Test
    void testSomething() {
        // Arrange
        YourEntity entity = new YourEntity();
        
        // Act
        YourEntity result = yourService.doSomething(entity);
        
        // Assert
        assertEquals(expectedValue, result.getValue());
    }
}
```

### Best Practices
1. Use descriptive test names
2. Follow AAA pattern (Arrange, Act, Assert)
3. Test one thing per test
4. Use @BeforeEach for common setup
5. Clean up after tests if needed
6. Test both happy path and error cases

## Integration Testing

The application uses H2 in-memory database for testing:
```properties
# src/test/resources/application.properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

## Performance Testing

Monitor test execution time:
```bash
./mvnw test -Dtest=ApplicationTests -DenableAssertions
```

If tests are slow:
1. Check database queries
2. Review test data size
3. Optimize test fixtures

## Mocking (Future Enhancement)

When adding services requiring external calls:
```java
@Mock
private ExternalService externalService;

@BeforeEach
void setup() {
    MockitoAnnotations.openMocks(this);
}

@Test
void testWithMock() {
    when(externalService.call()).thenReturn(value);
    // Test code
}
```

## Test Maintenance

### Regular Reviews
- Review failed tests weekly
- Update tests when requirements change
- Remove obsolete tests
- Refactor duplicate test code

### Test Dependencies
- Keep test data realistic
- Don't depend on test execution order
- Clean up test data properly

## Continuous Integration

### Pre-Commit Checks
```bash
./mvnw clean test
```

### Pre-Push Checks
```bash
./mvnw clean package
```

### CI Pipeline
1. Run all tests
2. Check code coverage
3. Generate reports
4. Deploy artifacts

## Debugging Tests

### Enable Debug Logging in Tests
```java
@Test
@Slf4j
void debugTest() {
    log.debug("Debug message");
    // Test code
}
```

### Debug with IDE
1. Set breakpoint in test
2. Right-click test → Debug
3. Use Debug console to inspect variables

## Code Coverage

### Generate Coverage Report
```bash
./mvnw clean test jacoco:report
# Report: target/site/jacoco/index.html
```

### Coverage Goals
- Overall: > 70%
- Critical paths: > 85%
- Controllers/Services: > 80%

## Common Test Issues

### Issue: Tests pass locally but fail in CI
- Ensure dependencies are properly defined
- Check for environment-specific code
- Verify test data setup

### Issue: Flaky tests
- Avoid time-dependent assertions
- Don't rely on external systems
- Use proper synchronization

### Issue: Slow tests
- Use H2 in-memory database
- Minimize test fixtures
- Parallelize test execution

## Test Naming Convention

```
test[What]_[Given]_[Expected]

Examples:
- testGetClothById_WithValidId_ReturnsCloth
- testAddCloth_WithInvalidPrice_ThrowsException
- testSearchByCategory_WithValidCategory_ReturnsResults
```

## Resources

- JUnit 5 Documentation: https://junit.org/junit5/docs/current/user-guide/
- Spring Testing: https://spring.io/guides/gs/testing-web/
- Mockito Documentation: https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html

