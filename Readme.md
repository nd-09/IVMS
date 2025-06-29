# 📦 Inventory Service
#### Inventory-service is a stateless microservice responsible for managing products, suppliers, and inventory-related operations. It communicates with the auth-service for authentication and user registration, without maintaining session state locally.

# 🧩 Responsibilities
- Register users by delegating to auth-service
- Manage inventory data (products, suppliers, etc.)
- Validate and authorize requests using JWT tokens

# 🏗️ Tech Stack
- Java 17
- Springboot 3.x
- Spring Data JPA (Hibernate)
- Spring Web + WebClient
- MySQL

# 🌀 System Role

```plaintext
                            ┌────────────────────────────┐
                            │        Client APP          │
                            └────────────┬───────────────┘
                                         │
                      Authenticate/Register Users (delegated to auth-service)
                                         │
                                         ▼
                          ┌────────────────────────────┐
                          │        auth-service        │
                          │        (JWT Provider)      │
                          └────────────┬───────────────┘
                                       │
                     [ JWT Token Returned to inventory-service ]
                                       │
                                       ▼
                            ┌────────────────────────────┐
                            │     inventory-service      │
                            │ ───────────────────────────│
                            │   Role-Based Verification  │
                            │   (JWT decoded & checked)  │
                            └────────────┬───────────────┘
                                         │
                             ┌───────────▼────────────┐
                             │   Product Management   │
                             │ (CRUD based on roles)  │
                             └───────────┬────────────┘
                                         │
                                Data Access Layer
                                         │
                                         ▼
                          ┌────────────────────────────┐
                          │        Product DB          │
                          │         (MySQL)            |
                          └────────────────────────────┘
```

# 📁 Package Overview
```plaintext
com.user.imvs
│
├── controller        # API endpoints for inventory and user registration
├── service           # Core business logic
├── helper             # AuthProxy for inter-service communication
├── mappers            # Mapper functions to convert dtos to entities
├── config            # JWT filter,security config and web-client config
├── model            # JPA entities (User, Product, etc.)
├── repository        # Spring Data JPA repositories
├── dto               # DTOs for request/response handling
└── exception         # Custom exceptions and global handlers
```
# 📤 API Endpoints
- Registers a user by saving user in DB and delegating to auth-service
#### 🔸 POST /api/v1/users
##### REQUEST BODY:
```json
{
  "username": "user",
  "email": "user@123",
  "password": "password123",
  "role": "ADMIN_INVENTORY"
}
```
##### RESPONSE:
```json
{
  "id": 5,
  "username": "user",
  "email": "user@123",
  "password": "$2a$10$5cBxu4Bq67DHcL5QKkOwx...",
  "role": "ADMIN_INVENTORY",
  "token": "eyJhbGciOiJIUzI1NiIsInR..."
}
```
#### 🔸 POST /api/v1/auth/login
- Authenticates a user extract claims and returns a JWT token.

REQUEST BODY:
```json
{
  "username": "user",
  "password": "password123"
}
```
RESPONSE:
```json
{
  "username": "user",
  "role": "ADMIN_INVENTORY",
  "token": "eyJhbGciOiJIUzI1NiIsInR..."
}
```
#### 🔸 GET /api/v1/users
- Returns all users in the inventory system. Admin-only access.
##### Headers:
```plaintext
Authorization: Bearer <JWT-TOKEN>
```
##### RESPONSE:
```json
[
  {
    "id": 1,
    "username": "admin1",
    "email": "admin1@example.com",
    "role": "ADMIN_INVENTORY"
  },
  {
    "id": 2,
    "username": "user",
    "email": "user@example.com",
    "role": "USER_INVENTORY"
  }
]
```
#### 🔸 DELETE /api/v1/inventory/users/{id}
- Deletes a user by their ID.
##### Headers:
```plaintext
Authorization: Bearer <JWT-TOKEN>
```
##### RESPONSE:
```plaintext
204 No Content
```


#### POST /api/v1/products
- Adds a new product. Admin-only access (based on JWT role).
##### Headers:
```plaintext
Authorization: Bearer <JWT-TOKEN>
```
##### REQUEST BODY:
```json
{
    "name": "Sample Product",
    "description": "A test product",
    "price": 29.99,
    "stockQuantity": 100,
    "category": {
        "name": "Sample Product"
    }
}
```
##### RESPONSE:
```json
{
    "id": 1,
    "name": "Sample Product",
    "description": "A test product",
    "price": 29.99,
    "stockQuantity": 100,
    "category": {
        "name": "Sample Product"
    }
}
```
#### 🔸 GET /api/v1/products/{id}
- Fetch a specific product by ID. JWT token required.

##### Headers:
```plaintext
Authorization: Bearer <JWT-TOKEN>
```
##### RESPONSE:
```json
{
    "id": 1,
    "name": "Sample Product",
    "description": "A test product",
    "price": 29.99,
    "stockQuantity": 100,
    "category": {
        "name": "Sample Product"
    }
}
```
#### 🔸 GET /api/v1/products
- Fetch all products.

##### RESPONSE:
```json
[
      {
        "name": "Sample Product",
        "description": "A test product",
        "price": 29.99,
        "stockQuantity": 500,
        "category": {
          "name": "Sample Product"
        }
      },
      {
        "name": "Sample Product 2",
        "description": "A test product 2",
        "price": 50.99,
        "stockQuantity": 100,
        "category": {
          "name": "Sample Product 2"
        }
      }
    ]

```
#### 🔸 PUT /api/v1/products/{id}
- Update product details. Admin-only access.
##### Headers:
```plaintext
Authorization: Bearer <JWT-TOKEN>
```
##### REQUEST BODY:
```json
{
    "id": 1,
    "name": "Sample Product",
    "description": "A test product",
    "price": 29.99,
    "stockQuantity": 50,
    "category": {
        "name": "Sample Product"
    }
}
```
##### RESPONSE:
```json
{
    "id": 1,
    "name": "Sample Product",
    "description": "A test product",
    "price": 29.99,
    "stockQuantity": 50,
    "category": {
        "name": "Sample Product"
    }
}
```
#### 🔸 DELETE /api/v1/inventory/products/{id}
- Delete a product by ID. Admin-only access.
##### Headers:
```plaintext
Authorization: Bearer <JWT-TOKEN>
```
##### RESPONSE:
```json
{
  "message": "Product with ID 1 deleted successfully."
}
```
#### 🔸 GET /api/v1/products/stats
- Fetch inventory stats. Admin-only access.

##### Headers:
```plaintext
Authorization: Bearer <JWT-TOKEN>
```
##### RESPONSE:
```json
{
    "totalProducts": 100,
    "recentlyAdded": {
        "name": "Sample Product",
        "description": "A test product",
        "price": 29.99,
        "stockQuantity": 100,
        "category": {
             "name": "Sample Product"
            }
          },
    "totalInventoryValuation": 89000.00,
    "lowStockProducts": [
      {
        "name": "Sample Product",
        "description": "A test product",
        "price": 29.99,
        "stockQuantity": 5,
        "category": {
          "name": "Sample Product"
        }
      },
      {
        "name": "Sample Product 2",
        "description": "A test product 2",
        "price": 50.99,
        "stockQuantity": 2,
        "category": {
          "name": "Sample Product 2"
        }
      }
    ]
}
```
# 🔐 Security
- No sessions or state is stored.
- Every incoming request (after login/register) must include a valid JWT token.
- Tokens are locally validated using a JWT filter with shared secret.

# ⚙️ Configuration
##### Update application.properties:
```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/inventory_db
spring.datasource.username=root
spring.datasource.password=your_password

# JWT
jwt.secret=your_jwt_secret_key

# Auth service URL
auth.service.url=http://localhost:8081/api/v1/auth
```
# 🚀 Running Locally
```bash
git clone https://github.com/nd-09/IVMS.git
cd IVMS
./mvnw spring-boot:run
```
##### Ensure that:
- MySQL is running with correct DB credentials
- auth-service is up and running on configured port (e.g., 8081)

# 🛠️ Future Scope
- Supplier/warehouse modules
- Service discovery (Eureka) integration
- Load balancing via Gateway
- Advanced inventory analytics (e.g., low stock alerts, demand forecasting)
- Integration with billing or order services


# 👨‍💻 Author
Created with ❤️ by Navdeep Chovatiya.
[![LinkedIn](https://img.shields.io/badge/LinkedIn-Profile-blue?logo=linkedin)](https://www.linkedin.com/in/navdeep-chovatiya-73349222a)












