# H2_DB_CRUD

A CRUD REST API built with Spring Boot that uses an in-memory H2 database for persisting Employee records. This project demonstrates common RESTful endpoints for create, read, update, delete employees, CSV import and a collection of unit and controller tests.

## 🚀 Features
- Create, retrieve, update, and delete Employee entities
- Partial update (salary) endpoint
- H2 in-memory database (developer friendly)
- CSV upload endpoint to bulk import employees (API v2)
- OpenAPI/Swagger UI documentation
- Layered architecture: Controllers, Services, Repositories, DTOs, Global Exception Handling
- Unit & Controller tests using JUnit 5, Mockito, and MockMvc

## 🧰 Tech Stack
- Java 17+ (or compatible)
- Spring Boot
- Spring MVC / Spring Data JPA
- H2 in-memory database
- ModelMapper
- OpenAPI/Swagger (springdoc)
- JUnit 5, Mockito
- Maven (mvn / mvnw)

## 📝 Prerequisites
- Java JDK 17+
- Maven
- (Optional) An IDE such as IntelliJ IDEA

## 🔧 Run locally

1. Clone the repository and navigate into the project directory:

```bash
git clone <your-repo-url>
cd H2_DB_CRUD
```

2. Run the application using the Maven wrapper:

```bash
./mvnw spring-boot:run
# Windows PowerShell
./mvnw.cmd spring-boot:run
```

3. The REST API will start on port `8081` (as configured in `application.properties`). Verify:

- Swagger UI: http://localhost:8081/docs
- H2 Console:  http://localhost:8081/h2-console  (JDBC URL: `jdbc:h2:mem:employeedb`)

## 🧩 API Endpoints

Base URL: http://localhost:8081/api/emp

1. Create Employee (POST)
   - POST /api/emp/create
   - Body (JSON):
     ```json
     {
       "name": "Amit Singh",
       "email": "amit@gmail.com",
       "contactNo": "8954763214",
       "department": "Technical",
       "salary": 7000
     }
     ```

2. Get All Employees (GET)
   - GET /api/emp/get

3. Get Employee by ID (GET)
   - GET /api/emp/get/{id}

4. Delete Employee (DELETE)
   - DELETE /api/emp/{id}

5. Update Employee (PUT)
   - PUT /api/emp/update
   - Body (JSON) – supply full entity with `id`:
     ```json
     {
       "id": 1,
       "name": "Amit Singh",
       "email": "amit@gmail.com",
       "contactNo": "8954763214",
       "department": "Technical",
       "salary": 15000
     }
     ```

6. Partial Update – Update Salary (PATCH)
   - PATCH /api/emp/
   - Body (JSON):
     ```json
     {
       "id": 2,
       "salary": 8500
     }
     ```

7. CSV Upload (API v2)
   - POST /api/emp/v2/upload (multipart/form-data)
   - Field: `csvFile`
   - GET /api/emp/v2/getAll – returns list of employees

## 🧪 Tests

Run tests using Maven:

```bash
./mvnw test
# Windows PowerShell
./mvnw.cmd test
```

Project includes:
- Unit and MockMvc controller tests located in `src/test/java/...`

## 🛡️ Exception Handling
- Validation errors (e.g., `@Valid`) return HTTP 400 with a JSON map where keys are field names and values are messages.
- Resource not found (e.g., not found by ID) throws `ResourceNotFoundException` and returns HTTP 404 with the message.

## 📂 H2 Console details
- Path: `/h2-console`
- JDBC URL: `jdbc:h2:mem:employeedb`
- User: `sa` (no password)

## 📦 Packaging

Create a shaded jar using Maven:

```bash
./mvnw clean package
# Windows PowerShell
./mvnw.cmd clean package
```

Run the JAR:

```bash
java -jar target/H2_DB_CRUD-0.0.1-SNAPSHOT.jar
```

## 🧭 Contributing
- Fork the repo and open a Pull Request with your changes.
- Please add unit tests for any new behaviors and run `mvn test`.

## 👤 Author
- Mayank Jaiswal

---
