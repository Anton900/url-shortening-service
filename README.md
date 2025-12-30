# URL Shortening Service

A RESTful URL shortening service built with Quarkus that allows users to shorten long URLs and track their usage statistics.

This project idea and instructions are from https://roadmap.sh/projects/url-shortening-service

## Features

- Shorten long URLs into 6-character alphanumeric codes
- Retrieve original URLs from short codes
- Update existing shortened URLs
- Delete shortened URLs
- Track access statistics (view count, timestamps)

## Tech Stack

- **Framework**: Quarkus
- **Language**: Java 21
- **Build Tool**: Maven
- **Storage**: In-memory ConcurrentHashMap (database coming soon)

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.8+

### Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/url-shortening-service.git
cd url-shortening-service
```

2. Build the project:
```bash
./mvnw clean package
```

3. Run the application:
```bash
./mvnw quarkus:dev
```

The service will start on `http://localhost:8080`

## API Endpoints

### 1. Create Shortened URL

**POST** `/shorten`

Create a new shortened URL.

**Request Body:**
```json
{
  "url": "https://www.example.com/very/long/url/path"
}
```

**Response:** `201 Created`
```json
{
  "id": 1,
  "url": "https://www.example.com/very/long/url/path",
  "shortCode": "aBc123",
  "createdAt": "2024-01-15T10:30:00.000+00:00",
  "updatedAt": "2024-01-15T10:30:00.000+00:00"
}
```

**Error Responses:**
- `400 Bad Request` - Invalid URL provided
- `500 Internal Server Error` - Could not generate unique short code

---

### 2. Get Original URL

**GET** `/shorten/{shortCode}`

Retrieve the original URL from a short code.

**Response:** `200 OK`
```json
{
  "id": 1,
  "url": "https://www.example.com/very/long/url/path",
  "shortCode": "aBc123",
  "createdAt": "2024-01-15T10:30:00.000+00:00",
  "updatedAt": "2024-01-15T10:30:00.000+00:00"
}
```

**Error Response:**
- `404 Not Found` - Short code not found

---

### 3. Update Shortened URL

**PUT** `/shorten/{shortCode}`

Update the original URL associated with a short code.

**Request Body:**
```json
{
  "url": "https://www.example.com/updated/url"
}
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "url": "https://www.example.com/updated/url",
  "shortCode": "aBc123",
  "createdAt": "2024-01-15T10:30:00.000+00:00",
  "updatedAt": "2024-01-15T10:35:00.000+00:00"
}
```

**Error Responses:**
- `404 Not Found` - Short code not found
- `400 Bad Request` - Invalid URL provided

---

### 4. Delete Shortened URL

**DELETE** `/shorten/{shortCode}`

Delete a shortened URL.

**Response:** `204 No Content`

**Error Response:**
- `404 Not Found` - Short code not found

---

### 5. Get Statistics

**GET** `/shorten/{shortCode}/stats`

Get usage statistics for a shortened URL.

**Response:** `200 OK`
```json
{
  "id": 1,
  "url": "https://www.example.com/very/long/url/path",
  "shortCode": "aBc123",
  "createdAt": "2024-01-15T10:30:00.000+00:00",
  "updatedAt": "2024-01-15T10:35:00.000+00:00",
  "accessCount": 42
}
```

**Error Response:**
- `404 Not Found` - Short code not found

## Project Structure

```
src/
├── main/java/dev/shorten/
│   ├── model/
│   │   ├── ShortCodeEntity.java          # Entity representing shortened URL
│   │   └── dto/
│   │       ├── CreateShortenRequest.java  # Request DTO
│   │       ├── ShortCodeResponse.java     # Response DTO
│   │       └── ShortCodeStatsResponse.java # Stats response DTO
│   ├── resource/
│   │   └── ShortenResource.java           # REST API endpoints
│   ├── service/
│   │   └── ShortenService.java            # Business logic
│   └── util/
│       ├── ShortenUtil.java               # Short code generation
│       ├── ValidateURLUtil.java           # URL validation
│       └── mapper/
│           └── ShortCodeMapper.java       # Entity-DTO mapping
```