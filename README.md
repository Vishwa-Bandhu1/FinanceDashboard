# Finance Dashboard Backend

A production-ready Spring Boot backend for a Finance Dashboard.

## Features
- JWT Authentication & Authorization
- RBAC (VIEWER, ANALYST, ADMIN)
- MongoDB Aggregation for Dashboard Analytics
- Full CRUD for Users & Financial Records
- Layered Architecture

## Prerequisites
- Java 17+
- Maven
- MongoDB Atlas Cluster or Local MongoDB

## Getting Started

1. **Configure Environment Variables**
Set `MONGO_URI` and `JWT_SECRET` in your environment, or just edit `application.yml` directly.
```powershell
$env:MONGO_URI="mongodb+srv://<username>:<password>@cluster.mongodb.net/financedb"
$env:JWT_SECRET="YourVeryLongSecretKeyHere..."
```

2. **Run the application**
```powershell
mvn spring-boot:run
```

## Example APIs

### 1. Register a User
`POST /api/auth/register`
```json
{
  "name": "Jane Doe",
  "email": "jane@example.com",
  "password": "password123"
}
```

### 2. Login
`POST /api/auth/login`
```json
{
  "email": "jane@example.com",
  "password": "password123"
}
```

*(Returns a JWT token to be used in the Authorization header: `Bearer {token}`)*

### 3. Get Dashboard Summary
`GET /api/dashboard/summary`
```json
{
    "totalIncome": 5000.0,
    "totalExpense": 1200.0,
    "netBalance": 3800.0
}
```

Enjoy using the Finance Dashboard API!
