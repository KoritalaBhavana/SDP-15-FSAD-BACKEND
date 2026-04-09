# Travel Nest Pro Backend

Travel Nest Pro Backend is a Spring Boot REST API for the Travel Nest Pro platform. It powers authentication, role-based dashboards, bookings, homestays, attractions, dining requests, guide bookings, wishlist data, reviews, notifications, file uploads, and admin approval workflows.

## Tech Stack

- Java 21
- Spring Boot 3.4
- Spring Web
- Spring Data JPA
- Spring Security
- JWT authentication
- MySQL
- Maven
- Swagger / OpenAPI

## Core Features

- JWT-based authentication and authorization
- Role support for admin, tourist, host, guide, and chef
- MySQL persistence with Spring Data JPA
- Host approval workflow controlled by admin
- Homestay CRUD APIs
- Booking, dining booking, and guide booking APIs
- Wishlist and itinerary APIs
- Reviews and reply support
- File upload support
- Mail support hooks
- Logging with AOP and application log files
- Swagger UI with JWT `Authorize` support

## Project Structure

- `src/main/java/com/travelnestpro/controller` REST controllers
- `src/main/java/com/travelnestpro/service` business logic
- `src/main/java/com/travelnestpro/repository` JPA repositories
- `src/main/java/com/travelnestpro/entity` database entities
- `src/main/java/com/travelnestpro/config` security, CORS, Swagger, seeding, and app config
- `src/main/resources/application.properties` runtime configuration
- `src/main/resources/schema.sql` schema reference

## Default Runtime

- Port: `6060`
- Swagger UI: `http://localhost:6060/swagger-ui/index.html`
- API docs: `http://localhost:6060/api-docs`
- Base API URL: `http://localhost:6060/api`

## Database

Default database name:

- `travelnextpro`

Datasource is configured to auto-create the database if MySQL permissions allow it.

## Environment Variables

The backend reads these values from environment variables, with local fallbacks where configured:

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
- `MAIL_SMTP_AUTH`
- `MAIL_SMTP_STARTTLS`
- `MAIL_FROM`
- `MAIL_SUPPORT_TO`

## Local Setup

1. Make sure MySQL is running.

2. Start the backend from the project folder:

```powershell
cd e:\travel-nest-pro-main\travel-nest-pro-backend
$env:DB_USERNAME="root"
$env:DB_PASSWORD="your_mysql_password"
$env:JAVA_HOME="C:\Program Files\Java\jdk-25.0.2"
$env:Path="$env:JAVA_HOME\bin;$env:Path"
.\mvnw.cmd spring-boot:run
```

3. Open:

```text
http://localhost:6060/swagger-ui/index.html
```

## Demo Accounts

Seeded accounts:

- `admin@travelnest.com` / `admin123`
- `tourist@travelnest.com` / `tourist123`
- `host@travelnest.com` / `host123`
- `guide@travelnest.com` / `guide123`
- `chef@travelnest.com` / `chef123`

## Useful Endpoints

- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET /api/homestays`
- `POST /api/bookings`
- `POST /api/dining-bookings`
- `POST /api/reviews`
- `GET /api/users/stats`

## Swagger Authentication

1. Call `POST /api/auth/login`
2. Copy the returned JWT token
3. Click `Authorize` in Swagger
4. Paste:

```text
Bearer YOUR_TOKEN
```

Then you can test protected APIs directly from Swagger UI.
