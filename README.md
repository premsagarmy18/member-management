
# member-management

=======
>>>>>>> 403d0fa4bc70e449530f2ffaa5b9d8b69fb17459
# Member Management Service

## Overview
This is a Spring Boot–based backend application for managing members.  
The application exposes REST APIs secured with JWT authentication and enforces role-based access control (ADMIN / USER).

The project includes **integration tests** using Testcontainers to validate real application behavior with a PostgreSQL database.

<<<<<<< HEAD
=======
---

>>>>>>> 403d0fa4bc70e449530f2ffaa5b9d8b69fb17459
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
<<<<<<< HEAD
=======

---

>>>>>>> 403d0fa4bc70e449530f2ffaa5b9d8b69fb17459
## Features
- Member CRUD operations
- JWT-based authentication
- Role-based authorization (ADMIN / USER)
- Pagination support for member listing
- Integration testing with real database
- Clean separation of main and test configurations
<<<<<<< HEAD
=======

---

>>>>>>> 403d0fa4bc70e449530f2ffaa5b9d8b69fb17459
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

<<<<<<< HEAD
> This is a backend-only application. No frontend or assets folder is required.

=======

> This is a backend-only application. No frontend or assets folder is required.

---

>>>>>>> 403d0fa4bc70e449530f2ffaa5b9d8b69fb17459
## Security
- Authentication is handled using JWT tokens.
- Passwords are stored in encoded (hashed) format.
- Role-based access control:
    - **ADMIN**: Can create members
    - **USER**: Restricted from admin-only operations

<<<<<<< HEAD
=======
---

>>>>>>> 403d0fa4bc70e449530f2ffaa5b9d8b69fb17459
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

