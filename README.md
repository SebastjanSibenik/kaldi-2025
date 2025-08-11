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
```cmd
cd frontend
npm install
npm start
```

## API Documentation

### Base URL
`http://localhost:8080/api/v1`

### Endpoints

#### Room Management
| Endpoint | Method | Auth required | Description |
|----------|--------|---------------|-------------|
| `/rooms` | GET | No            | List all rooms |
| `/rooms/{type}` | GET | No            | Get room details |

#### Conversation Management
| Endpoint | Method | Auth required | Description               |
|----------|--------|---------------|---------------------------|
| `/conversations` | GET | Yes           | List conversations        |
| `/conversations/{uuid}/claim` | POST | Yes           | Claim conversation        |
| `/conversations/{uuid}/messages` | GET | Yes           | Get conversation messages |
| `/conversations/{uuid}/reply` | POST | Yes           | Send message reply        |

#### Requests body


#### Response body



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