# 🎰 Casino Core API
> **Note:** I used AI as a learning assistant to better understand Java, backend development concepts, and best practices. I implemented, debugged, and tested the project myself.

A personal backend project built with **Java** and **Spring Boot** that simulates the core functionality of an online casino wallet system.

The goal of this project is to practice real-world backend development concepts and gradually build a production-like REST API using modern Java technologies and best practices.

---

# 🚀 Features

## 👤 User Management

- Create user
- Update user
- Delete user
- Get all users
- Get user by ID
- Search users by username
- Filter users by country and status

---

## 💰 Wallet Management

- Get wallet by user ID
- Deposit funds
- Withdraw funds
- Place casino bets
- Credit player winnings

Each wallet operation automatically creates a transaction record.

---

## 📜 Transaction History

- Store every wallet operation
- Filter transactions by type
- Pagination support

---

# 🏗 Project Architecture

The application follows the standard Spring Boot layered architecture.

```
Client (Postman / Swagger)

          │

          ▼

     REST Controller

          │

          ▼

     Service Layer

          │

          ▼

   Repository Layer

          │

          ▼

      PostgreSQL
```

---

# 🛠 Tech Stack

| Technology | Purpose |
|------------|---------|
| Java 17 | Programming Language |
| Spring Boot | Backend Framework |
| Spring Data JPA | Database Access |
| Hibernate | ORM |
| PostgreSQL | Database |
| Maven | Dependency Management |
| Lombok | Boilerplate Reduction |
| Bean Validation | Request Validation |
| Swagger / OpenAPI | API Documentation |
| JUnit 5 | Unit Testing |
| Mockito | Mocking Framework |

---

# 📚 API Documentation

The project includes Swagger/OpenAPI documentation.

After running the application locally, the API documentation is available at:

```
http://localhost:8080/swagger-ui/index.html
```

---

# ✅ Implemented Concepts

- REST API
- Layered Architecture
- Dependency Injection
- DTO Pattern
- Entity Relationships
- Repository Pattern
- Bean Validation
- Global Exception Handling
- Custom Exceptions
- Pagination
- Filtering
- Swagger Documentation
- Unit Testing
- Mockito

---

# 🧪 Unit Testing

Current unit tests cover the WalletService business logic.

Implemented test scenarios:

- Deposit
- Withdraw
- Bet
- Win

Testing Frameworks:

- JUnit 5
- Mockito

---

# 📁 Project Structure

```
src
├── controller
├── service
├── repository
├── model
├── dto
├── enums
├── exception
├── config
└── resources
```

---

# 🚧 Roadmap

The following features are planned for future development:

- Negative Unit Tests
- Integration Tests
- Spring Security (JWT Authentication)
- Docker
- Docker Compose
- GitHub Actions (CI/CD)
- Logging
- Monitoring
- Deployment to Cloud

---

# 👨‍💻 Author

Antonio Tsankov

GitHub:
https://github.com/Antoniotsankov