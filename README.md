# ActivityHub Backend

> SDP-15 | Full Stack Application Development (FSAD)
> Spring Boot REST API powering the ActivityHub travel and homestay platform, documented with Swagger and OpenAPI.

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?style=flat-square&logo=springboot)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=flat-square&logo=openjdk)](https://openjdk.org/)
[![Swagger](https://img.shields.io/badge/Swagger-OpenAPI%203-85EA2D?style=flat-square&logo=swagger)](https://swagger.io/)
[![MySQL](https://img.shields.io/badge/MySQL-8.x-4479A1?style=flat-square&logo=mysql)](https://www.mysql.com/)

---

## Table of Contents

- [Project Overview](#project-overview)
- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Database Schema](#database-schema)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Swagger Documentation](#swagger-documentation)
- [Security and Authentication](#security-and-authentication)
- [Environment Variables](#environment-variables)
- [Deployment](#deployment)
- [Team](#team)

---

## Project Overview

This is the backend REST API for ActivityHub, a travel platform that connects tourists, homestay hosts, local guides, chefs, and administrators. It is built with Spring Boot and uses Spring Security with JWT for role-based authentication.

The API serves the React frontend and is documented through Swagger UI.

### User Roles Supported

| Role | Access Level |
|------|--------------|
| `TOURIST` | Browse, book, review homestays and attractions |
| `HOST` | List and manage properties, handle bookings |
| `GUIDE` | Manage itineraries, attractions, and bookings |
| `CHEF` | Manage dining requests and meal bookings |
| `ADMIN` | Full platform access for users, listings, analytics, and approvals |

---

## Architecture

```text
React Frontend
        |
        | HTTPS / REST (JSON)
        v
Spring Boot Application (Port 6060)
        |
        |-- Controller Layer   -> REST endpoints and request mapping
        |-- Service Layer      -> Business logic
        |-- Repository Layer   -> JPA and Hibernate data access
        `-- Security Layer     -> JWT auth and role-based access
                |
                v
           MySQL Database
```

### Design Patterns Used

- Layered Architecture: Controller -> Service -> Repository
- DTO Pattern: Request and response DTOs separate from entities
- Repository Pattern: Spring Data JPA repositories per entity
- Stateless JWT Authentication

---

## Tech Stack

| Category | Technology |
|----------|-----------|
| Language | Java 21 |
| Framework | Spring Boot 3.4 |
| Security | Spring Security + JWT |
| ORM | Spring Data JPA / Hibernate |
| Database | MySQL 8 |
| API Docs | SpringDoc OpenAPI 3 |
| Build Tool | Maven |
| Email | Spring Mail |
| Logging | SLF4J / Logback with AOP |

---

## Project Structure

```text
SDP-15-FSAD-BACKEND/
|-- src/
|   `-- main/
|       |-- java/com/travelnestpro/
|       |   |-- config/
|       |   |-- controller/
|       |   |-- dto/
|       |   |-- entity/
|       |   |-- exception/
|       |   |-- repository/
|       |   |-- security/
|       |   |-- service/
|       |   `-- TravelNestProBackendApplication.java
|       `-- resources/
|           |-- application.properties
|           `-- schema.sql
|-- pom.xml
`-- README.md
```

---

## Database Schema

### Key Entities and Relationships

```text
users
  |-- homestays
  |     `-- bookings
  |-- attractions
  |-- itineraries
  |-- reviews
  |-- messages
  |-- guide_bookings
  `-- dining_bookings
```

Core tables include:

- `users`
- `homestays`
- `bookings`
- `attractions`
- `itineraries`
- `reviews`
- `messages`
- `notifications`
- `wishlists`
- `guide_bookings`
- `dining_bookings`

Database name:

- `travelnextpro`

---

## Getting Started

### Prerequisites

- Java 21 or later
- Maven wrapper included
- MySQL 8 running locally

### Installation

```powershell
git clone https://github.com/KoritalaBhavana/SDP-15-FSAD-BACKEND.git
cd SDP-15-FSAD-BACKEND
```

Set environment variables and run:

```powershell
$env:DB_USERNAME="root"
$env:DB_PASSWORD="your_db_password"
$env:JAVA_HOME="C:\Program Files\Java\jdk-25.0.2"
$env:Path="$env:JAVA_HOME\bin;$env:Path"
.\mvnw.cmd spring-boot:run
```

The server starts at:

- `http://localhost:6060`

### Running Tests

```powershell
.\mvnw.cmd test
```

---

## API Endpoints

All endpoints are prefixed with `/api`. Protected endpoints require:

```text
Authorization: Bearer <token>
```

### Auth - `/api/auth`

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/api/auth/register` | Register a new user | Public |
| POST | `/api/auth/login` | Login and receive JWT token | Public |
| POST | `/api/auth/google` | Google login bridge | Public |

### Users - `/api/users`

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| GET | `/api/users/{id}` | Get user profile | Authenticated |
| PUT | `/api/users/{id}` | Update user profile | Owner |
| GET | `/api/users/stats` | Dashboard stats | Admin |
| PATCH | `/api/users/{id}/status` | Approve or reject user | Admin |

### Homestays - `/api/homestays`

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| GET | `/api/homestays` | List all homestays | Public |
| GET | `/api/homestays/{id}` | Get single homestay | Public |
| POST | `/api/homestays` | Create homestay | Host |
| PUT | `/api/homestays/{id}` | Update homestay | Host |
| DELETE | `/api/homestays/{id}` | Delete homestay | Host or Admin |

### Bookings - `/api/bookings`

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/api/bookings` | Create booking | Tourist |
| GET | `/api/bookings/{id}` | Get booking details | Authenticated |
| PATCH | `/api/bookings/{id}/status` | Update booking status | Host |

### Dining Bookings - `/api/dining-bookings`

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/api/dining-bookings` | Create dining or chef booking | Tourist |
| PUT | `/api/dining-bookings/{id}/status` | Accept or reject request | Chef |

### Attractions - `/api/attractions`

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| GET | `/api/attractions` | List attractions | Public |
| POST | `/api/attractions` | Add attraction | Guide |
| PUT | `/api/attractions/{id}` | Update attraction | Guide |

### Itineraries - `/api/itineraries`

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| GET | `/api/itineraries` | List itineraries | Public |
| POST | `/api/itineraries` | Create itinerary | Guide |
| PUT | `/api/itineraries/{id}` | Update itinerary | Guide |

### Reviews - `/api/reviews`

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/api/reviews` | Submit review | Tourist |
| PUT | `/api/reviews/{id}/reply` | Reply to review | Host, Guide, or Chef |
| GET | `/api/reviews/{targetType}/{targetId}` | Get reviews by target | Public |

### Messages - `/api/messages`

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| GET | `/api/messages/conversation/{userId}` | Get conversation | Authenticated |
| POST | `/api/messages` | Send message | Authenticated |

---

## Swagger Documentation

Once the backend is running, open:

```text
http://localhost:6060/swagger-ui/index.html
```

Raw OpenAPI JSON:

```text
http://localhost:6060/api-docs
```

To test protected APIs in Swagger:

1. Call `POST /api/auth/login`
2. Copy the JWT token from the response
3. Click `Authorize` at the top right
4. Paste `Bearer <your_token>`

---

## Security and Authentication

### JWT Flow

```text
1. User sends credentials to /api/auth/login
2. Server validates credentials and returns JWT
3. Client stores token
4. Client sends Authorization: Bearer <token>
5. JwtAuthenticationFilter validates token
6. Spring Security enforces role-based access
```

### Role-Based Access Control

- Admin manages user approvals and stats
- Host manages properties and booking requests
- Guide manages itineraries and attractions
- Chef manages dining requests and replies
- Tourist books stays, adds wishlist items, and submits reviews

### CORS Configuration

Allowed origins are configurable and currently include local frontend origins such as:

- `http://localhost:5173`
- `http://localhost:3000`
- `http://localhost:8080`

---

## Environment Variables

Configured through environment variables and `application.properties`:

```properties
server.port=6060
spring.datasource.url=jdbc:mysql://localhost:3306/travelnextpro?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD:root}
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.api-docs.path=/api-docs
```

Main variables:

- `DB_USERNAME`
- `DB_PASSWORD`
- `JWT_SECRET`
- `JWT_EXPIRATION_MS`
- `CORS_ALLOWED_ORIGINS`
- `FILE_UPLOAD_DIR`
- `MAIL_HOST`
- `MAIL_PORT`
- `MAIL_USERNAME`
- `MAIL_PASSWORD`
- `MAIL_FROM`
- `MAIL_SUPPORT_TO`

Do not commit sensitive credentials.

---

## Deployment

You can deploy this backend on platforms like Render, Railway, or AWS.

Typical build command:

```bash
./mvnw clean install -DskipTests
```

Typical run command:

```bash
java -jar target/travel-nest-pro-backend-0.0.1-SNAPSHOT.jar
```

---

## Team

SDP Group 15 - FSAD

| Member | Role |
|--------|------|
| Koritala Bhavana | Team Lead / Full Stack Developer |
| Sahithi | Backend Developer |
| Saniya | Backend Developer |

---

## License

Developed as part of the Full Stack Application Development course. Academic use only.
