# E-Commerce API Documentation

## Authentication

All authenticated endpoints require a Bearer token in the Authorization header.

### Register
```http
POST /api/v1/auth/register
Content-Type: application/json

{
  "email": "user@example.com",
  "username": "username",
  "password": "Password123!",
  "firstName": "John",
  "lastName": "Doe"
}
```

### Login
```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "Password123!"
}
```

## Products

### Get All Products
```http
GET /api/v1/products?page=0&size=20&sortBy=createdAt&sortDir=desc
```

### Get Product by ID
```http
GET /api/v1/products/{id}
```

### Search Products
```http
GET /api/v1/products/search?query=keyword&page=0&size=20
```

## Orders

### Create Order
```http
POST /api/v1/orders
Authorization: Bearer {token}
Content-Type: application/json

{
  "shippingAddressId": 1,
  "shippingMethodId": 1,
  "paymentMethod": "CREDIT_CARD",
  "paymentToken": "tok_xxx"
}
```

## Cart

### Add to Cart
```http
POST /api/v1/cart/items
Content-Type: application/json

{
  "productId": 1,
  "quantity": 2
}
```

## Error Codes

| Code | Description |
|------|-------------|
| 400 | Bad Request |
| 401 | Unauthorized |
| 403 | Forbidden |
| 404 | Not Found |
| 409 | Conflict |
| 422 | Validation Error |
| 500 | Internal Server Error |
