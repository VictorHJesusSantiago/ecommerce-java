# E-Commerce Java Platform

A full-stack e-commerce platform built with Spring Boot 3.2, featuring comprehensive product catalog, order management, payment processing, and admin capabilities.

## Tech Stack

- **Backend**: Java 17, Spring Boot 3.2, Spring Security, Spring Data JPA
- **Database**: PostgreSQL 16, Redis 7, H2 (dev)
- **Message Queue**: RabbitMQ, Apache Kafka
- **API Documentation**: OpenAPI 3.0 (Swagger)
- **Build Tool**: Maven
- **Containerization**: Docker, Docker Compose

## Features

- Product catalog with categories, brands, variants, and images
- User management with JWT authentication and OAuth2
- Shopping cart and wishlist
- Order management with status tracking
- Payment processing (Stripe, PayPal)
- Inventory management with warehouse tracking
- Marketing tools (coupons, discounts, promotions)
- Reviews and ratings
- Notification system (email, SMS, push)
- CMS for pages, menus, and settings
- Analytics and reporting
- Internationalization (i18n)
- Caching with Redis
- Message queue integration
- Audit logging

## Quick Start

```bash
# Development
./scripts/start.sh dev

# Docker
./scripts/start.sh docker

# Tests
./scripts/start.sh test
```

## API Documentation

Access Swagger UI at: http://localhost:8080/swagger-ui.html
