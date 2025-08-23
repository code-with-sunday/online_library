# Online Library

An enterprise-level **Online Library Management System** built with Spring Boot. This application allows users to register, log in, manage books, and perform advanced searches. It supports role-based access for librarians and readers.

---

## Table of Contents

- [Features](#features)
- [Endpoints](#endpoints)
- [Request Examples](#request-examples)
- [Screenshots](#screenshots)
- [Technologies](#technologies)
- [Setup](#setup)
- [License](#license)

---

## Features

- User Registration and Login
- Role-based authentication (`READER` and `LIBRARIAN`)
- Add, update, and manage books
- Search books with pagination and filtering
- RESTful API with JSON responses
- JWT Authentication for secure endpoints

---

## Endpoints

| Method | Endpoint | Description | Role |
|--------|---------|-------------|------|
| POST   | `/auth/sign-up` | Register a new user | Public |
| POST   | `/auth/login`   | Authenticate user and receive JWT | Public |
| POST   | `/library/book` | Add a new book | LIBRARIAN |
| PUT    | `/library/book` | Update existing book or create if missing | LIBRARIAN |
| GET    | `/reader/book` | Get all books | READER / LIBRARIAN |
| GET    | `/reader/book/search` | Search books with filters | READER / LIBRARIAN |

---

## Request Examples

### 1. Sign Up
**Endpoint:** `POST /auth/sign-up`
[![Online Library][POSTMAN_VIEW]](sign_up_request.png)

[![Online Library][POSTMAN_VIEW]](sign_up_DB.png)

[![Online Library][POSTMAN_VIEW]](sign_up_logs.png)

[![Online Library][POSTMAN_VIEW]](successful_login.png)

[![Online Library][POSTMAN_VIEW]](search_book.png)

[![Online Library][POSTMAN_VIEW]](Junit_test_login.png)

[![Online Library][POSTMAN_VIEW]](Junit_test_signup.png)

[![Online Library][POSTMAN_VIEW]](getAll_book.png)

[![Online Library][POSTMAN_VIEW]](failed_login.png)

[![Online Library][POSTMAN_VIEW]](create_book.png)









##Body (JSON):
```json
{
  "firstName": "Sunday",
  "lastName": "Peter",
  "email": "sundaypetertest@gmail.com",
  "password": "password123",
  "role": "READER",
  "profileImageUrl": "http://example.com/profile.jpg",
  "enabled": true
}

## 2. Login

**Endpoint:** `POST /auth/login`

**Body (JSON):**
```json
{
  "email": "sundaypetertest@gmail.com",
  "password": "password123"
}
3. Create Book
Endpoint: POST /library/book

Body (JSON):

json
Copy
Edit
{
  "title": "Spring Boot in Action",
  "isbn": "1234567890",
  "revisionNumber": 1,
  "publishedDate": "2024-01-01",
  "publisher": "Tech Press",
  "authors": "Craig Walls",
  "genre": "Programming",
  "coverImageUrl": "http://example.com/cover.jpg",
  "availableCopies": 10
}
4. Update Book
Endpoint: PUT /library/book

Body (JSON):

json
Copy
Edit
{
  "title": "Spring Boot in Action - Updated",
  "isbn": "1234567890",
  "revisionNumber": 2,
  "publishedDate": "2025-01-01",
  "publisher": "Tech Press",
  "authors": "Craig Walls",
  "genre": "Programming",
  "coverImageUrl": "http://example.com/cover-updated.jpg",
  "availableCopies": 15
}

#5. Get All Books
Endpoint: GET /reader/book

6. Search Books
Endpoint: GET /reader/book/search

Query Parameters (optional):

title

isbn

publisher

addedFrom (yyyy-MM-dd)

addedTo (yyyy-MM-dd)

page (integer)

size (integer)

Screenshots
Sign Up

Successful Login

Failed Login

Create Book

Get All Books

Search Book

JUnit Tests

Database Logs & Requests

Technologies
Java 17

Spring Boot 3

Spring Security with JWT

Spring Data JPA (Hibernate)

H2 / MySQL

Maven

Postman for API testing


## 2. Login

**Endpoint:** `POST /auth/login`

**Body (JSON):**
```json
{
  "email": "sundaypetertest@gmail.com",
  "password": "password123"
}
3. Create Book
Endpoint: POST /library/book

Body (JSON):

json
Copy
Edit
{
  "title": "Spring Boot in Action",
  "isbn": "1234567890",
  "revisionNumber": 1,
  "publishedDate": "2024-01-01",
  "publisher": "Tech Press",
  "authors": "Craig Walls",
  "genre": "Programming",
  "coverImageUrl": "http://example.com/cover.jpg",
  "availableCopies": 10
}
4. Update Book
Endpoint: PUT /library/book

Body (JSON):

json
Copy
Edit
{
  "title": "Spring Boot in Action - Updated",
  "isbn": "1234567890",
  "revisionNumber": 2,
  "publishedDate": "2025-01-01",
  "publisher": "Tech Press",
  "authors": "Craig Walls",
  "genre": "Programming",
  "coverImageUrl": "http://example.com/cover-updated.jpg",
  "availableCopies": 15
}

## 5. Get All Books
Endpoint: GET /reader/book

6. Search Books
Endpoint: GET /reader/book/search

Query Parameters (optional):

title

isbn

publisher

addedFrom (yyyy-MM-dd)

addedTo (yyyy-MM-dd)

page (integer)

size (integer)

