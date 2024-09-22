
# Rewards System API

## Overview

The **Rewards System API** is designed to manage customer rewards and purchases. It provides endpoints for adding purchases, calculating reward points, and managing customer data.

## Features

- Add a purchase for a customer
- Get total reward points for a customer
- Manage customer details (CRUD operations)
- RESTful API design

---

## Technologies Used

- Java
- Spring Boot
- Hibernate (H2)
- MySQL (or any RDBMS of your choice)
- Swagger (for API documentation)
- Maven (for build and dependency management)

---

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java JDK 17+
- Maven 3.6+
- MySQL or any relational database installed
- Postman (optional, for testing API endpoints)

---

## Setup

1. **Clone the repository:**
   ```bash
   git clone https://github.com/chandan095/rewards.git
   ```

2. **Configure Database:**

   Update the `application.properties` or `application.yml` file in the `src/main/resources` directory with your database connection details.

   Example for `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/testdb
   spring.datasource.username=sa
   spring.datasource.password=password

   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```

3. **Build the project:**
   Navigate to the project directory and run:
   ```bash
   mvn clean install
   ```

4. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the Swagger UI:**
   Once the application is running, navigate to:
   ```
   http://localhost:8080/swagger-ui.html
   ```
   This will provide a UI for testing all API endpoints.

---

## API Endpoints

Here are the key API endpoints provided by the Rewards System API:

- **POST /api/rewards/purchase** - Add a new purchase.
- **GET /api/rewards/points?customerId={id}** - Get total reward points for a customer.
- **POST /api/rewards/customer** - Add a new customer.
- **GET /api/rewards/customer/{id}** - Get customer details by ID.
- **PUT /api/rewards/customer/{id}** - Update customer details.
- **DELETE /api/rewards/customer/{id}** - Delete a customer.
- **GET /api/rewards/customers** - Get a list of all customers.

For a detailed description of the endpoints, please refer to the [API Documentation](docs/api_documentation.md).

---

## Running Tests

To run unit tests for the application, you can use the following Maven command:
```bash
mvn test
```

---


## License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## Contact

For any queries or issues, you can contact the project maintainer:

- **Chandan Kumar Prasad**
- Email: chandankumarprasad1@gmail.com
