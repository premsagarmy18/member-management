# member-management

# Member Management Service

## Overview
This is a Spring Boot–based backend application for managing members.  
The application exposes REST APIs secured with JWT authentication and enforces role-based access control (ADMIN / USER).

The project includes **integration tests** using Testcontainers to validate real application behavior with a PostgreSQL database.

## Tech Stack
- Java 17
- Spring Boot
- Spring Security (JWT)
- Spring Data JPA
- PostgreSQL
- Gradle
- Testcontainers
- JUnit 5
- MockMvc
## Features
- Member CRUD operations
- JWT-based authentication
- Role-based authorization (ADMIN / USER)
- Pagination support for member listing
- Integration testing with real database
- Clean separation of main and test configurations
## Project Structure
src
├── main
│ ├── java
│ │ └── com/surest/member
│ │ ├── controllers
│ │ ├── service
│ │ ├── repository
│ │ ├── entity
│ │ └── security
│ └── resources
│ └── application.yml
└── test
├── java
│ └── com/surest/member
│ ├── controllers
│ └── util
└── resources
└── application-test.yml

> This is a backend-only application. No frontend or assets folder is required.

## Security
- Authentication is handled using JWT tokens.
- Passwords are stored in encoded (hashed) format.
- Role-based access control:
    - **ADMIN**: Can create members
    - **USER**: Restricted from admin-only operations

## Integration Testing

### Testing Approach
- Integration tests are written using `@SpringBootTest` and `MockMvc`
- PostgreSQL is provided using **Testcontainers**
- JWT tokens are generated using the same `JwtUtil` used in production
- Redis and DevTools are disabled for the test profile
- Tests validate:
    - Authentication and authorization
    - Role-based access control
    - Pagination behavior
    - Negative scenarios (unauthorized / forbidden access)

### Run Tests
```bash
./gradlew test

