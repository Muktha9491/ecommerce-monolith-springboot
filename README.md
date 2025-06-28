#E-commerce Spring Boot Application

This is a monolithic e-commerce web application built with Java, Spring Boot, JPA, MySQL, and secured using JWT authentication. The application supports product browsing, user registration, and order placement.

## 🚀 Features

- ✅ RESTful API using Spring Boot
- ✅ Product, Order, and User management
- ✅ MySQL database integration
- ✅ JWT-based user authentication
- ✅ Swagger UI for API documentation
- ✅ Unit testing using JUnit
- ✅ Docker-ready for easy deployment

---

## 🧱 Tech Stack

| Layer         | Technology                          |
|---------------|-------------------------------------|
| Language      | Java 21                             |
| Framework     | Spring Boot 3.2.5                   |
| Security      | Spring Security + JWT               |
| Database      | MySQL                               |
| ORM           | Spring Data JPA                     |
| API Docs      | Springdoc OpenAPI (Swagger)         |
| Build Tool    | Maven                               |
| Dev Tools     | Lombok, Spring Boot DevTools        |
| Testing       | JUnit, Spring Boot Starter Test     |
| Deployment    | Docker (optional)                   |

---

## ⚙️ Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/ecommerce-monolith-springboot.git
cd ecommerce-monolith-springboot
````

### 2. Configure Database

Update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
spring.datasource.username=root
spring.datasource.password=your_password
```

> Create the database `ecommerce_db` in MySQL manually or via script.

### 3. Build the Project

```bash
mvn clean install
```

### 4. Run the Application

```bash
mvn spring-boot:run
```

Application will start at:
📍 `http://localhost:8080`

---

## 🔐 API Authentication

* Authentication is done via JWT tokens.
* Secure endpoints require Authorization header:

```
Authorization: Bearer <your_jwt_token>
```

---

## 📄 Swagger API Docs

After starting the app, access API documentation:

```bash
http://localhost:8080/swagger-ui.html
```

---

## 🧪 Running Tests

```bash
mvn test
```

---

## 🐳 Docker Support (Optional)

### Build Docker Image

```bash
docker build -t ecommerce-app .
```

### Run the Container

```bash
docker run -p 8080:8080 ecommerce-app
```

---

## 📁 Project Structure

```
ecommerce-monolith-springboot/
├── main/
│   ├── java/com/ecommerce/
│   │   ├── controller/
│   │   ├── model/
│   │   ├── repository/
│   │   ├── security/
│   │   └── ProductServiceApplication.java
│   └── resources/
│       ├── application.properties
├── test/
├── pom.xml
└── README.md
