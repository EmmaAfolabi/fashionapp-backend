# FashionApp API Examples

## Base URL
```
http://localhost:8080
```

---

## 📦 CLOTH ENDPOINTS

### 1. Create a New Cloth
```http
POST /api/clothes HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
  "name": "Premium Cotton Blue Shirt",
  "category": "SHIRT",
  "price": 49.99,
  "description": "High-quality cotton shirt perfect for casual wear",
  "imageUrl": "https://example.com/images/blue-shirt.jpg",
  "stock": 100
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "name": "Premium Cotton Blue Shirt",
  "category": "SHIRT",
  "price": 49.99,
  "description": "High-quality cotton shirt perfect for casual wear",
  "imageUrl": "https://example.com/images/blue-shirt.jpg",
  "stock": 100,
  "rating": 0,
  "createdAt": "2026-04-03T12:00:00",
  "updatedAt": "2026-04-03T12:00:00"
}
```

---

### 2. Get All Clothes (Paginated)
```http
GET /api/clothes?page=0&size=20&sort=name,asc HTTP/1.1
Host: localhost:8080
```

**Response (200 OK):**
```json
{
  "content": [
    {
      "id": 1,
      "name": "Premium Cotton Blue Shirt",
      "category": "SHIRT",
      "price": 49.99,
      "stock": 100,
      "rating": 0,
      "createdAt": "2026-04-03T12:00:00",
      "updatedAt": "2026-04-03T12:00:00"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 20,
    "sort": ["name: ASC"]
  },
  "totalElements": 1,
  "totalPages": 1,
  "first": true,
  "last": true
}
```

---

### 3. Get Cloth by ID
```http
GET /api/clothes/1 HTTP/1.1
Host: localhost:8080
```

**Response (200 OK):**
```json
{
  "id": 1,
  "name": "Premium Cotton Blue Shirt",
  "category": "SHIRT",
  "price": 49.99,
  "description": "High-quality cotton shirt",
  "imageUrl": "https://example.com/images/blue-shirt.jpg",
  "stock": 100,
  "rating": 0,
  "createdAt": "2026-04-03T12:00:00",
  "updatedAt": "2026-04-03T12:00:00"
}
```

---

### 4. Update Cloth
```http
PUT /api/clothes/1 HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
  "price": 59.99,
  "stock": 85,
  "description": "Updated description"
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "name": "Premium Cotton Blue Shirt",
  "category": "SHIRT",
  "price": 59.99,
  "description": "Updated description",
  "stock": 85,
  "rating": 0,
  "updatedAt": "2026-04-03T14:30:00"
}
```

---

### 5. Delete Cloth
```http
DELETE /api/clothes/1 HTTP/1.1
Host: localhost:8080
```

**Response (200 OK):**
```
Cloth deleted successfully
```

---

## 🔍 SEARCH ENDPOINTS

### 6. Search by Category
```http
GET /api/clothes/search/category?category=SHIRT&page=0&size=20 HTTP/1.1
Host: localhost:8080
```

---

### 7. Search by Price Range
```http
GET /api/clothes/search/price-range?minPrice=30&maxPrice=100&page=0&size=20 HTTP/1.1
Host: localhost:8080
```

---

### 8. Search by Name
```http
GET /api/clothes/search/name?searchTerm=shirt&page=0&size=20 HTTP/1.1
Host: localhost:8080
```

---

### 9. Get In-Stock Items
```http
GET /api/clothes/stock/in-stock?page=0&size=20 HTTP/1.1
Host: localhost:8080
```

---

### 10. Search by Category and Price
```http
GET /api/clothes/search/category-price?category=SHIRT&minPrice=40&maxPrice=80&page=0&size=20 HTTP/1.1
Host: localhost:8080
```

---

## 📊 STOCK MANAGEMENT

### 11. Update Stock
```http
PATCH /api/clothes/1/stock?quantity=10 HTTP/1.1
Host: localhost:8080
```

**Note:** Pass positive or negative quantity to add/reduce stock

**Response (200 OK):**
```
Stock updated successfully
```

---

## ⭐ REVIEW ENDPOINTS

### 12. Add Review for Cloth
```http
POST /api/reviews/cloth/1 HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
  "rating": 5,
  "comment": "Excellent quality! Very comfortable to wear.",
  "reviewerName": "John Doe"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "cloth": { "id": 1, "name": "Premium Cotton Blue Shirt" },
  "rating": 5,
  "comment": "Excellent quality! Very comfortable to wear.",
  "reviewerName": "John Doe",
  "createdAt": "2026-04-03T13:00:00"
}
```

---

### 13. Get Reviews for Cloth
```http
GET /api/reviews/cloth/1?page=0&size=20 HTTP/1.1
Host: localhost:8080
```

---

### 14. Get Review by ID
```http
GET /api/reviews/1 HTTP/1.1
Host: localhost:8080
```

---

### 15. Get Average Rating
```http
GET /api/reviews/cloth/1/rating HTTP/1.1
Host: localhost:8080
```

**Response (200 OK):**
```json
{
  "clothId": 1,
  "averageRating": 4.5,
  "reviewCount": 10
}
```

---

### 16. Get Review Count
```http
GET /api/reviews/cloth/1/count HTTP/1.1
Host: localhost:8080
```

**Response (200 OK):**
```json
{
  "clothId": 1,
  "reviewCount": 10
}
```

---

### 17. Delete Review
```http
DELETE /api/reviews/1 HTTP/1.1
Host: localhost:8080
```

**Response (200 OK):**
```
Review deleted successfully
```

---

## ❌ ERROR RESPONSES

### Invalid Price Exception (400)
```json
{
  "timestamp": "2026-04-03T12:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Price must be greater than zero",
  "path": "/api/clothes"
}
```

### Resource Not Found (404)
```json
{
  "timestamp": "2026-04-03T12:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Cloth not found with ID: 999",
  "path": "/api/clothes/999"
}
```

### Invalid Rating (400)
```json
{
  "timestamp": "2026-04-03T12:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Rating must be between 1 and 5",
  "path": "/api/reviews/cloth/1"
}
```

---

## 📝 Testing with cURL

```bash
# Create cloth
curl -X POST http://localhost:8080/api/clothes \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test Shirt",
    "category": "SHIRT",
    "price": 29.99,
    "stock": 50
  }'

# Get all clothes
curl http://localhost:8080/api/clothes

# Search by name
curl "http://localhost:8080/api/clothes/search/name?searchTerm=shirt"

# Add review
curl -X POST http://localhost:8080/api/reviews/cloth/1 \
  -H "Content-Type: application/json" \
  -d '{
    "rating": 4,
    "comment": "Great!",
    "reviewerName": "Jane"
  }'

# Get average rating
curl http://localhost:8080/api/reviews/cloth/1/rating
```

---

## 📚 Category Values
- TRADITIONAL
- ENGLISH
- SHIRT
- TROUSER
- SHOES

---

## 🔗 Documentation Links
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs

