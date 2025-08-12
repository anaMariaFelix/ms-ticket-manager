# 📌Microservice Project — Ticket Manager with Java + Spring Boot & AWS

## 📝 Project Description

The project consists of an independent microservice — ms-ticket-manager — organized in a monorepo to facilitate development and maintenance.

- **Microservice Ticket(Port 8081):** Manages ticket purchases and cancellations, ensuring security via JWT, communication with Event Manager through OpenFeign, and persistence with MongoDB Atlas via Spring Data MongoDB.

---

## 📂 Project Structure

The project uses a monorepo to facilitate the management and development. 
Its internal structure was developed following the Spring Boot layering pattern, with a clear separation of responsibilities.

The main folder structure is organized as follows:

```
ms-ticket-manager/                 # Microservice responsible for ticket management (port 8081)
├── src/main/java
│   └── com/anamariafelix/ms_ticket_manager
│       ├── client/                # OpenFeign interfaces for consuming external APIs (ms-event)
│       ├── config/                # Global Settings (Security and Swagger)
│       ├── controller/            # REST controllers that expose endpoints to the end client
│       ├── dto/                   # Data Transfer Objects (Create and Response DTOs)
│       ├── enums/                 # Enumerations used in the system (e.g., Status, types, etc.)
│       ├── exception/             # Custom exception classes
│       ├── jwt/                   # JWT authentication implementation (provider, filter, UserDetailsService)
│       ├── mapper/                # Converters between entities and DTOs
│       ├── model/                 # Domain entities (e.g. Event, User)
│       ├── repository/            # Data access interfaces (Spring Data JPA/Mongo)
│       ├── service/               # Services with business logic
│
└── pom.xml                        # Maven configuration file
```
---
## ✨ Features

### Microservice Ticket
- [x] User registration and login with JWTn
- [x] Integration with ms-event via OpenFeign
- [x] Query, create, purchase, update and delete tickets
- [x] Persistence in the MongoDB Atlas database
- [x] Unit test coverage with `Coverage`
- [x] Documented using Swagger/OpenAPI
- [x] Depoly on AWS EC2 instances
---

## 🛠️ Technologies Used

- **Language:** Java 17
- **Framework:** Spring Boot 3
- **Communication:** Spring Web, OpenFeign
- **Security:** Spring Security, JWT
- **Database:** MongoDB Atlas
- **Persistence:** Spring Data MongoDB 
- **Documentation:** SpringDoc (Swagger)
- **Build:** Maven
- **Layered architecture:** following Spring best practices
---

## 🧩 Tool and Dependency Details

### 🔗 OpenFeign

- **In ms-ticket-manager:**  `EventClient` consumes ms-event-manager to fetch event information, with timeouts, fallback and logging configured.


### 🔒 Spring Security & JWT

**Authentication Flow:**
1. User sends credentials to `POST api/v1/auth`.
2. The JWT token is generated if authentication is valid.
3. The token is returned to the client.

**Authorization Flow:**
1. Client sends the token in the header `Authorization: Bearer <token>`.
2. A filter validates the token and populates the security context.
---

## 🚀 How to Run the Project

### Prerequisites
- Git
- JDK 17+
- Maven 3.9+

### Step by Step

```
# Clone the project 
git clone https://github.com/anaMariaFelix/ms-ticket-manager

# start
cd ms-ticket-manager
mvn spring-boot:run
```

### 🌐 Swagger Documentation

- **Microservice Ticket:**  
  -[http://localhost:8080/swagger-ms-ticket-manager.html](http://localhost:8081/swagger-ui/index.html)
  
  -[http://localhost:8080/swagger-ms-ticket-manager.html](http://localhost:8081/docs-ms-ticket-manager)
---
