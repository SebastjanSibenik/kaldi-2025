# Kaldi Application

## Project Overview
This project was created as part of the Kaldi 2025 technical homework assignment.
It provides a simple REST API backend designed to support two types of clients.
Additionally, a basic frontend was quickly developed to facilitate easier testing.

## Clients
- **Mobile Users** (Mobile client):
  - Select chat rooms (Technical, Services, Conversational)
  - Send messages
  - Wait for operator responses

- **Operators** (Web client):
  - View pending conversations
  - Claim pending conversations
  - Reply to users

## Technical Stack
| Component       | Technology       |
|----------------|-----------------|
| Backend        | Quarkus (Java)  |
| Frontend       | React           |
| Authentication | HTTP Basic Auth |
| Database       | PostgreSQL      |

## Installation Guide

### Prerequisites
- Java 17+
- Node.js 16+ (Optional for frontend usage)
- PostgreSQL 12+
- Maven
- The kaldi-2025-main folder from GitHub

### Backend Setup
0. Open a command prompt in the `/kaldi-2025-main` folder
1. Initialize database:
```cmd
psql -U <your_username> -d postgres -c "DROP DATABASE IF EXISTS kaldi;"
psql -U <your_username> -d postgres -c "CREATE DATABASE kaldi;"
psql -U <your_username> -d kaldi -f backend/db/init.sql
```
- You will be prompted to enter your PostgreSQL password.

2. Start backend:
```cmd
cd backend
mvn clean compile quarkus:dev
```
3. Backend server now runs at `http://localhost:8080`

### Frontend Setup
0. Open another command prompt in the `/kaldi-2025-main` folder
1. Install and start NPM
```cmd
cd frontend
npm install
npm start
```
2. Frontend now listens at `http://localhost:3000/`

## API Documentation

### Base Backend URL
`http://localhost:8080/api/v1`

### Base Frontend URL
`http://localhost:3000/`

### Endpoints

#### Room
| Endpoint | Method | Auth required | Description |
|----------|--------|---------------|-------------|
| `/rooms` | GET | No            | List all rooms |
| `/rooms/{type}` | GET | No            | Get room details |

Sure! Here's a cleaned up, well-formatted, and pretty version of that Markdown section for you:

#### Conversation

| Endpoint                         | Method | Auth required | Description               |
|----------------------------------|--------|---------------|---------------------------|
| `/conversations`                 | GET    | Yes           | List conversations        |
| `/conversations/{uuid}/claim`    | POST   | Yes           | Claim conversation        |
| `/conversations/{uuid}/messages` | GET  | Yes           | Get conversation messages |
| `/conversations/{uuid}/reply`    | POST   | Yes           | Send message reply        |
| `/conversations/start`           | POST   | No            | Initiates conversation    |


#### Request Body Examples

**POST /conversations/start request example**

```json
{
  "content": "Hello there!",
  "sender": {
    "username": "Obi-Wan Kenobi",
    "role": "USER"
  },
  "roomType": "SERVICES"
}
```

**POST /conversations/{uuid}/reply request example**

```json
{
  "content": "Yo",
  "sender": {
    "username": "Obi-Wan Kenobi",
    "role": "USER"
  }
}
```

---
#### Response Body Examples

**GET /rooms response example**

```json
[
  {
    "name": "TECHNICAL",
    "description": "Technical Room",
    "roomType": "TECHNICAL"
  },
  {
    "name": "SERVICES",
    "description": "Service requests Room",
    "roomType": "SERVICES"
  },
  {
    "name": "CONVERSATIONAL",
    "description": "General Chat Room",
    "roomType": "CONVERSATIONAL"
  }
]
```

**GET /rooms/type response example**

```json
{
  "name": "SERVICES",
  "description": "Service requests Room",
  "roomType": "SERVICES"
}
```

---

**POST /conversations/start response example**

```json
{
  "content": "Hello there!",
  "sender": {
    "username": "Mr. Bean",
    "role": "USER"
  },
  "conversation": {
    "uuid": "<uuid>",
    "operator": null,
    "roomType": "SERVICES",
    "status": "PENDING"
  }
}
```

**POST /conversations/{uuid}/reply response example**

```json
{
  "content": "Yo",
  "sender": {
    "username": "Mr. Bean",
    "role": "USER"
  },
  "conversation": {
    "uuid": "<uuid>",
    "operator": {
      "username": "Obi-Wan Kenobi",
      "role": "USER"
    },
    "roomType": "SERVICES",
    "status": "PENDING"
  }
}
```

---

**GET /conversations response example**

```json
[
  {
    "uuid": "f0bf12e9-12f5-4931-99c7-d05aaecb7d07",
    "operator": {
      "role": "USER",
      "username": "Obi-Wan Kenobi"
    },
    "status": "PENDING",
    "roomType": "SERVICES"
  }
]
```
---

**POST /conversations/{uuid}/claim response example**

```json
{
  "content": "Hello there!",
  "sender": {
    "username": "Obi-Wan",
    "role": "USER"
  },
  "roomType": "TECHNICAL"
}
```
---

**GET /conversations/{uuid}/messages response example**

```json
[
  {
    "content": "Hello there!",
    "sender": {
      "role": "USER",
      "username": "Obi-Wan Kenobi"
    },
    "conversation": {
      "uuid": "<uuid>",
      "operator": {
        "username": "Test Operator",
        "role": "OPERATOR"
      },
      "roomType": "SERVICES",
      "status": "PENDING"
    }
  },
  {
    "content": "Yo",
    "sender": {
      "role": "USER",
      "username": "Obi-Wan Kenobi"
    },
    "conversation": {
      "uuid": "<uuid>",
      "operator": null,
      "status": "PENDING",
      "roomType": "SERVICES"
    }
  }
]
```
---

## Authentication
Pre-configured operator accounts:
| Username | Password |
|----------|----------|
| admin1   | admin1   |
| admin2   | admin2   |
| admin3   | admin3   |
| admin4   | admin4   |

Use HTTP Basic Authentication for protected endpoints.

## Database Schema
Key tables:
- `rooms`
- `users`
- `conversations`
- `messages`

Initialize with `db/init.sql` script.

## Troubleshooting
- **Port conflicts**: Ensure ports 8080 (backend) and 3000 (frontend) are available
- **Database issues**: Verify PostgreSQL is running and credentials match
- **Authentication failures**: Check username/password combinations

## Support
For assistance contact:  
**Sebastjan Šibenik**  
sebastjan.sibenik@gmail.com  
+386 40 172 326