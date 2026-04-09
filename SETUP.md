# TravelNestPro Setup

## Prerequisites

- Git installed and available in `PATH`
- Java 21+ or Java 25 with `JAVA_HOME` set
- Maven wrapper available through `mvnw` / `mvnw.cmd`
- MySQL running locally on port `3306`
- Node.js 18+ and npm
- VS Code or Eclipse/STS for editing and running the project

## Database

Create the database if it does not exist:

```sql
CREATE DATABASE IF NOT EXISTS travelnextpro;
```

Default backend connection:

- Database: `travelnextpro`
- Username: `root`
- Password: `root`
- Port: `6060`

Override credentials with environment variables:

```powershell
$env:DB_USERNAME="root"
$env:DB_PASSWORD="your_mysql_password"
```

## Backend Run

From [`travel-nest-pro-backend`](/e:/travel-nest-pro-main/travel-nest-pro-backend):

```powershell
$env:JAVA_HOME="C:\Program Files\Java\jdk-25.0.2"
$env:Path="$env:JAVA_HOME\bin;$env:Path"
.\mvnw.cmd spring-boot:run
```

Swagger UI:

- `http://localhost:6060/swagger-ui/index.html`

## Frontend Run

From repo root:

```powershell
npm install
npm run dev
```

Optional frontend API override in `.env`:

```env
VITE_API_BASE_URL=http://localhost:8080/api
VITE_API_BASE_URL=http://localhost:6060/api
```

## Seeded Accounts

- Admin: `admin@travelnest.com` / `admin123`
- Tourist: `tourist@travelnest.com` / `tourist123`
- Host: `host@travelnest.com` / `host123`
- Guide: `guide@travelnest.com` / `guide123`
- Chef: `chef@travelnest.com` / `chef123`

## Implemented Project Topics

- Spring Boot REST API with layered controller/service/repository structure
- Spring Data JPA with MySQL integration
- JWT-based login/register flow with Spring Security
- CORS setup for React + Spring Boot integration
- Global exception handling
- Swagger / OpenAPI integration
- File upload endpoint with multipart handling
- Support email service hook
- Logging with SLF4J and AOP-based request/service tracing
- React frontend wired to backend auth and homestay APIs with local fallback

## Current Gaps

These topics are only partially covered or still need a dedicated implementation pass:

- Full role-restricted authorization for every business action
- Refresh tokens / production-grade auth hardening
- DTO mapping across every entity
- JPA relationships with `@OneToMany`, `@ManyToOne`, and `@ManyToMany`
- Real payment gateway integration
- Full Redux/Context consolidation
- Deployment automation
- SSO/OAuth beyond the current Google token verification path
