# ⚙️ ActivityHub — Backend

> **SDP-15 | Full Stack Application Development (FSAD)**
> Spring Boot REST API powering the ActivityHub travel and homestay platform, documented with Swagger/OpenAPI.

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?style=flat-square&logo=springboot)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-ED8B00?style=flat-square&logo=openjdk)](https://openjdk.org/)
[![Swagger](https://img.shields.io/badge/Swagger-OpenAPI%203-85EA2D?style=flat-square&logo=swagger)](https://swagger.io/)
[![MySQL](https://img.shields.io/badge/MySQL-8.x-4479A1?style=flat-square&logo=mysql)](https://www.mysql.com/)

---

## 📖 Table of Contents

- [Project Overview](#project-overview)
- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Database Schema](#database-schema)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Swagger Documentation](#swagger-documentation)
- [Security & Authentication](#security--authentication)
- [Environment Variables](#environment-variables)
- [Deployment](#deployment)
- [Team](#team)

---

## 📌 Project Overview

This is the **backend REST API** for ActivityHub — a travel platform that connects tourists, homestay hosts, local guides, and administrators. It is built with **Spring Boot 3** and uses **Spring Security with JWT** for role-based authentication.

The API serves the React frontend and is fully documented via **Swagger UI (OpenAPI 3)**.

### User Roles Supported

| Role | Access Level |
|------|-------------|
| `TOURIST` | Browse, book, review homestays and attractions |
| `HOST` | List and manage properties, handle bookings |
| `GUIDE` | Manage itineraries, attractions, and bookings |
| `ADMIN` | Full platform access — users, listings, analytics, interviews |

---

## 🏗️ Architecture

```
React Frontend (Render)
        │
        │ HTTPS / REST (JSON)
        ▼
Spring Boot Application (Port 8080)
        │
        ├── Controller Layer   → REST endpoints, request/response mapping
        ├── Service Layer      → Business logic
        ├── Repository Layer   → JPA/Hibernate data access
        └── Security Layer     → JWT auth, role-based access
                │
                ▼
        MySQL Database
```

### Design Patterns Used
- **Layered Architecture**: Controller → Service → Repository
- **DTO Pattern**: Request/Response DTOs separate from entity models
- **Repository Pattern**: Spring Data JPA repositories per entity
- **JWT Stateless Auth**: No server-side session; token validated per request

---

## 🛠️ Tech Stack

| Category | Technology |
|----------|-----------|
| Language | Java 17 |
| Framework | Spring Boot 3.x |
| Security | Spring Security + JWT (JJWT) |
| ORM | Spring Data JPA / Hibernate |
| Database | MySQL 8.x |
| API Docs | SpringDoc OpenAPI 3 (Swagger UI) |
| Build Tool | Maven |
| Email/OTP | Spring Mail / Twilio (optional) |
| File Storage | Cloudinary (image upload integration) |
| Deployment | Render / Railway / AWS EC2 |

---

## 📁 Project Structure

```
SDP-15-FSAD-BACKEND/
├── src/
│   └── main/
│       ├── java/com/activityhub/
│       │   ├── ActivityHubApplication.java    # Main entry point
│       │   │
│       │   ├── config/
│       │   │   ├── SecurityConfig.java         # Spring Security + CORS
│       │   │   ├── JwtConfig.java
│       │   │   └── SwaggerConfig.java          # OpenAPI 3 config
│       │   │
│       │   ├── controller/
│       │   │   ├── AuthController.java         # /api/auth
│       │   │   ├── UserController.java         # /api/users
│       │   │   ├── HomestayController.java     # /api/homestays
│       │   │   ├── BookingController.java      # /api/bookings
│       │   │   ├── PaymentController.java      # /api/payments
│       │   │   ├── AttractionController.java   # /api/attractions
│       │   │   ├── GuideController.java        # /api/guides
│       │   │   ├── ItineraryController.java    # /api/itineraries
│       │   │   ├── ReviewController.java       # /api/reviews
│       │   │   ├── MessageController.java      # /api/messages
│       │   │   └── AdminController.java        # /api/admin
│       │   │
│       │   ├── service/
│       │   │   ├── AuthService.java
│       │   │   ├── UserService.java
│       │   │   ├── HomestayService.java
│       │   │   ├── BookingService.java
│       │   │   ├── PaymentService.java
│       │   │   ├── AttractionService.java
│       │   │   ├── GuideService.java
│       │   │   ├── ItineraryService.java
│       │   │   ├── ReviewService.java
│       │   │   ├── MessageService.java
│       │   │   └── AdminService.java
│       │   │
│       │   ├── repository/
│       │   │   ├── UserRepository.java
│       │   │   ├── HomestayRepository.java
│       │   │   ├── BookingRepository.java
│       │   │   ├── PaymentRepository.java
│       │   │   ├── AttractionRepository.java
│       │   │   ├── ItineraryRepository.java
│       │   │   ├── ReviewRepository.java
│       │   │   └── MessageRepository.java
│       │   │
│       │   ├── model/                          # JPA Entity classes
│       │   │   ├── User.java
│       │   │   ├── Homestay.java
│       │   │   ├── Booking.java
│       │   │   ├── Payment.java
│       │   │   ├── Attraction.java
│       │   │   ├── Itinerary.java
│       │   │   ├── Review.java
│       │   │   ├── Message.java
│       │   │   └── enums/
│       │   │       ├── Role.java               # TOURIST, HOST, GUIDE, ADMIN
│       │   │       ├── BookingStatus.java       # PENDING, CONFIRMED, CANCELLED
│       │   │       └── PaymentStatus.java       # PENDING, SUCCESS, FAILED
│       │   │
│       │   ├── dto/                            # Request / Response DTOs
│       │   │   ├── request/
│       │   │   │   ├── LoginRequest.java
│       │   │   │   ├── RegisterRequest.java
│       │   │   │   ├── BookingRequest.java
│       │   │   │   ├── PaymentRequest.java
│       │   │   │   └── ...
│       │   │   └── response/
│       │   │       ├── AuthResponse.java
│       │   │       ├── HomestayResponse.java
│       │   │       ├── BookingResponse.java
│       │   │       └── ...
│       │   │
│       │   ├── security/
│       │   │   ├── JwtTokenProvider.java       # Token generation & validation
│       │   │   ├── JwtAuthFilter.java          # Per-request JWT filter
│       │   │   └── CustomUserDetailsService.java
│       │   │
│       │   └── exception/
│       │       ├── GlobalExceptionHandler.java # @ControllerAdvice
│       │       ├── ResourceNotFoundException.java
│       │       └── UnauthorizedException.java
│       │
│       └── resources/
│           ├── application.properties          # Main config
│           ├── application-dev.properties      # Dev overrides
│           └── application-prod.properties     # Prod overrides
│
├── pom.xml
└── README.md
```

---

## 🗄️ Database Schema

### Key Entities and Relationships

```
users (id, name, email, phone, password_hash, role, profile_image, status, created_at)
  │
  ├──< homestays (id, host_id→users, title, description, location, price_per_night,
  │              amenities, images, status, created_at)
  │         │
  │         └──< bookings (id, homestay_id, tourist_id→users, check_in, check_out,
  │                        guests, total_amount, status, special_requests, created_at)
  │                   │
  │                   └──< payments (id, booking_id, amount, method, status,
  │                                  transaction_id, created_at)
  │
  ├──< attractions (id, guide_id→users, name, description, location, images,
  │                entry_fee, best_time, rating, created_at)
  │
  ├──< itineraries (id, guide_id→users, title, description, days_plan,
  │                 is_public, created_at)
  │
  ├──< reviews (id, user_id→users, target_id, target_type [HOMESTAY|GUIDE|ATTRACTION],
  │             rating, comment, reply, created_at)
  │
  └──< messages (id, sender_id→users, receiver_id→users, booking_id→bookings,
                 content, is_read, created_at)

interviews (id, user_id→users, scheduled_at, interviewer_notes, status [PENDING|APPOINTED|REJECTED])
```

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- MySQL 8.x running locally or on cloud
- (Optional) Postman or any REST client for testing

### Installation

```bash
# 1. Clone the repository
git clone https://github.com/KoritalaBhavana/SDP-15-FSAD-BACKEND.git
cd SDP-15-FSAD-BACKEND

# 2. Create the MySQL database
mysql -u root -p
CREATE DATABASE activityhub_db;
EXIT;

# 3. Configure environment variables (see Environment Variables section)
# Edit src/main/resources/application.properties

# 4. Build and run
mvn clean install
mvn spring-boot:run
```

The server starts at `http://localhost:8080`

### Running Tests

```bash
mvn test
```

---

## 📡 API Endpoints

All endpoints are prefixed with `/api`. Protected endpoints require the `Authorization: Bearer <token>` header.

### 🔐 Auth — `/api/auth`

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | `/api/auth/register` | Register a new user (Tourist/Host/Guide) | Public |
| POST | `/api/auth/login` | Login and receive JWT token | Public |
| POST | `/api/auth/forgot-password` | Send OTP to email/phone | Public |
| POST | `/api/auth/reset-password` | Reset password with OTP | Public |
| POST | `/api/auth/logout` | Logout (client-side token removal) | Any |

**Register Request Body:**
```json
{
  "name": "Koritala Bhavana",
  "email": "bhavana@example.com",
  "phone": "9876543210",
  "password": "securepassword",
  "role": "TOURIST"
}
```

**Login Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "userId": 1,
  "name": "Koritala Bhavana",
  "role": "TOURIST",
  "email": "bhavana@example.com"
}
```

---

### 👤 Users — `/api/users`

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/api/users/{id}` | Get user profile | Any |
| PUT | `/api/users/{id}` | Update user profile (name, phone, image) | Owner |
| DELETE | `/api/users/{id}` | Delete user account | Owner/Admin |
| GET | `/api/users/me` | Get current authenticated user | Any |

---

### 🏡 Homestays — `/api/homestays`

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/api/homestays` | List all approved homestays | Public |
| GET | `/api/homestays/{id}` | Get single homestay details | Public |
| GET | `/api/homestays/search?location=&minPrice=&maxPrice=` | Search and filter | Public |
| POST | `/api/homestays` | Create new homestay listing | HOST |
| PUT | `/api/homestays/{id}` | Update homestay details | HOST (owner) |
| DELETE | `/api/homestays/{id}` | Delete a homestay | HOST/ADMIN |
| GET | `/api/homestays/host/{hostId}` | Get all homestays by a host | HOST |
| PATCH | `/api/homestays/{id}/status` | Approve/reject listing | ADMIN |

**Create Homestay Request Body:**
```json
{
  "title": "Mountain Dew Cottage",
  "description": "A cosy hillside retreat with mountain views.",
  "location": "Manali, Himachal Pradesh",
  "pricePerNight": 1800,
  "amenities": ["WiFi", "Parking", "Breakfast"],
  "images": ["https://cloudinary.com/image1.jpg"],
  "maxGuests": 4,
  "houseRules": "No smoking. Check-in after 2 PM."
}
```

---

### 📅 Bookings — `/api/bookings`

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | `/api/bookings` | Create a new booking | TOURIST |
| GET | `/api/bookings/{id}` | Get booking details | Owner/Host |
| GET | `/api/bookings/tourist/{touristId}` | Get all bookings for a tourist | TOURIST |
| GET | `/api/bookings/host/{hostId}` | Get all bookings for a host | HOST |
| PATCH | `/api/bookings/{id}/status` | Accept / Reject booking | HOST |
| PATCH | `/api/bookings/{id}/cancel` | Cancel a booking | TOURIST |

**Booking Request Body:**
```json
{
  "homestayId": 5,
  "checkIn": "2025-12-20",
  "checkOut": "2025-12-25",
  "guests": 2,
  "specialRequests": "Early check-in preferred"
}
```

**Booking Status Values:** `PENDING`, `CONFIRMED`, `REJECTED`, `CANCELLED`, `COMPLETED`

---

### 💳 Payments — `/api/payments`

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | `/api/payments/initiate` | Initiate payment for booking | TOURIST |
| GET | `/api/payments/{id}` | Get payment status | Owner |
| POST | `/api/payments/verify` | Verify and confirm payment | TOURIST |
| GET | `/api/payments/booking/{bookingId}` | Get payment by booking | Owner |

**Payment Request Body:**
```json
{
  "bookingId": 12,
  "amount": 9000,
  "method": "UPI",
  "couponCode": "STAY20"
}
```

---

### ⭐ Attractions — `/api/attractions`

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/api/attractions` | List all attractions | Public |
| GET | `/api/attractions/{id}` | Get attraction details | Public |
| GET | `/api/attractions/nearby?location=` | Get nearby attractions | Public |
| POST | `/api/attractions` | Add new attraction (with image) | GUIDE |
| PUT | `/api/attractions/{id}` | Update attraction | GUIDE (owner) |
| DELETE | `/api/attractions/{id}` | Delete attraction | GUIDE/ADMIN |

---

### 🧭 Guides — `/api/guides`

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/api/guides` | List all guides | Public |
| GET | `/api/guides/{id}` | Get guide profile | Public |
| GET | `/api/guides/{id}/itineraries` | Get itineraries by guide | Public |
| PUT | `/api/guides/{id}` | Update guide profile | GUIDE (owner) |

---

### 🗺️ Itineraries — `/api/itineraries`

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/api/itineraries` | List public itineraries | Public |
| GET | `/api/itineraries/{id}` | Get itinerary details | Public |
| POST | `/api/itineraries` | Create new itinerary | GUIDE |
| PUT | `/api/itineraries/{id}` | Edit existing itinerary | GUIDE (owner) |
| DELETE | `/api/itineraries/{id}` | Delete itinerary | GUIDE/ADMIN |

---

### ⭐ Reviews — `/api/reviews`

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/api/reviews?targetId=&targetType=` | Get reviews for homestay/guide/attraction | Public |
| POST | `/api/reviews` | Submit a review | TOURIST |
| PUT | `/api/reviews/{id}/reply` | Host/Guide reply to review | HOST/GUIDE |
| DELETE | `/api/reviews/{id}` | Delete review | Owner/ADMIN |

**Reply Request Body:**
```json
{
  "reply": "Thank you for the kind words! Looking forward to hosting you again."
}
```

---

### 💬 Messages — `/api/messages`

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/api/messages/conversation/{userId}` | Get conversation with user | Any |
| POST | `/api/messages` | Send a message | Any |
| PATCH | `/api/messages/{id}/read` | Mark message as read | Receiver |
| GET | `/api/messages/unread-count` | Get unread message count | Any |

---

### 👨‍💼 Admin — `/api/admin`

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/api/admin/stats` | Platform-wide stats (users, bookings, etc.) | ADMIN |
| GET | `/api/admin/users` | List all users with filters | ADMIN |
| PATCH | `/api/admin/users/{id}/status` | Approve/Reject/Suspend user | ADMIN |
| GET | `/api/admin/interviews` | List pending interviews | ADMIN |
| POST | `/api/admin/interviews` | Schedule interview for a user | ADMIN |
| PATCH | `/api/admin/interviews/{id}` | Update interview status (APPOINTED/REJECTED) | ADMIN |
| GET | `/api/admin/homestays/pending` | List homestays awaiting approval | ADMIN |
| PATCH | `/api/admin/homestays/{id}/approve` | Approve a homestay | ADMIN |
| DELETE | `/api/admin/content/{id}` | Remove inappropriate content | ADMIN |
| GET | `/api/admin/analytics` | Detailed analytics data | ADMIN |

**Stats Response Example:**
```json
{
  "totalTourists": 142,
  "totalHosts": 38,
  "totalGuides": 21,
  "activeBookings": 56,
  "totalHomestays": 94,
  "totalRevenue": 580000,
  "pendingInterviews": 7
}
```

---

## 📚 Swagger Documentation

Once the backend is running, access the interactive API documentation at:

```
http://localhost:8080/swagger-ui/index.html
```

Or the raw OpenAPI JSON spec at:
```
http://localhost:8080/v3/api-docs
```

Swagger is configured in `SwaggerConfig.java` using **SpringDoc OpenAPI 3**:

```java
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI activityHubOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("ActivityHub API")
                .description("REST API for the ActivityHub Travel & Homestay Platform — SDP-15 FSAD")
                .version("1.0.0"))
            .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
            .components(new Components()
                .addSecuritySchemes("BearerAuth",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")));
    }
}
```

To test protected endpoints in Swagger UI:
1. Call `POST /api/auth/login` and copy the `token` from the response
2. Click **Authorize** (🔒) at the top right
3. Paste the token as `Bearer <your_token>`
4. All subsequent requests will include the Authorization header

---

## 🔐 Security & Authentication

### JWT Flow

```
1. User POSTs credentials to /api/auth/login
2. Server validates credentials, returns JWT token (expires in 24h)
3. Client stores token in localStorage
4. Every subsequent request includes: Authorization: Bearer <token>
5. JwtAuthFilter validates token on each request
6. Spring Security enforces role-based access per endpoint
```

### Role-Based Access Control (RBAC)

```java
@PreAuthorize("hasRole('HOST')")
public ResponseEntity<?> createHomestay(...) { ... }

@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<?> getAdminStats(...) { ... }

@PreAuthorize("hasAnyRole('TOURIST', 'ADMIN')")
public ResponseEntity<?> cancelBooking(...) { ... }
```

### CORS Configuration

The backend allows requests from the deployed frontend URL:
```java
config.addAllowedOrigin("https://fsad-frontend-jmme.onrender.com");
config.addAllowedOrigin("http://localhost:5173"); // dev
```

---

## 🔑 Environment Variables

Configure in `src/main/resources/application.properties`:

```properties
# Server
server.port=8080

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/activityhub_db
spring.datasource.username=root
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# JWT
jwt.secret=your_very_long_secure_secret_key_here
jwt.expiration=86400000

# Mail (OTP / Notifications)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_email@gmail.com
spring.mail.password=your_app_password

# Cloudinary (Image Upload)
cloudinary.cloud-name=your_cloud_name
cloudinary.api-key=your_api_key
cloudinary.api-secret=your_api_secret

# Swagger
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
```

> ⚠️ Do not commit sensitive credentials. Use environment variables or a secrets manager in production.

---

## ☁️ Deployment

### Deploy on Render (Web Service)

1. Push code to GitHub
2. Render Dashboard → **New Web Service** → Connect repo
3. Runtime: **Java**
4. Build command: `mvn clean install -DskipTests`
5. Start command: `java -jar target/activityhub-backend-1.0.0.jar`
6. Add all environment variables
7. Deploy

### Deploy on Railway

```bash
railway login
railway init
railway up
```

### Deploy on AWS EC2

```bash
# On EC2 instance (Amazon Linux / Ubuntu)
sudo apt update && sudo apt install -y openjdk-17-jre mysql-server
# Upload jar
scp target/activityhub-backend.jar ec2-user@<EC2-IP>:~/
# Run
java -jar activityhub-backend.jar --spring.profiles.active=prod
```

---

## 👥 Team

**SDP Group 15 — FSAD**

| Member | Role |
|--------|------|
| Koritala Bhavana | Team Lead / Full Stack Developer |
| Sahithi | Backend Developer |
| Saniya | Backend Developer |

---

## 📄 License

Developed as part of the **Full Stack Application Development (FSAD)** course — SDP Group 15. For academic use only.
