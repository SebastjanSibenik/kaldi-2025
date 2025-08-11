# Kaldi Application

## Project Overview
A system with REST API backend (Quarkus) and React frontend for mobile users and operators.

## System Components
### User Roles
- **Mobile Users**:
  - Select chat rooms (Technical, Services, Conversational)
  - Send messages
  - Wait for operator responses

- **Operators** (Web Interface):
  - View pending conversations
  - Claim conversations
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
- Node.js 16+ (for frontend)
- PostgreSQL 12+
- Maven

### Backend Setup
1. Initialize database:
```bash
psql -U postgres -f backend/db/db.init.sql
```

2. Start backend:
```bash
cd backend
mvn clean compile quarkus:dev
```

### Frontend Setup
```bash
cd frontend
npm install
npm start
```

## API Documentation

### Base URL
`http://localhost:8080/api/v1`

### Endpoints

#### Room Management
| Endpoint | Method | Auth | Description |
|----------|--------|------|-------------|
| `/rooms` | GET | No | List all rooms |
| `/rooms/{type}` | GET | No | Get room details |

#### Conversation Management
| Endpoint | Method | Auth | Description |
|----------|--------|------|-------------|
| `/conversations` | GET | Yes | List conversations |
| `/conversations/{uuid}/claim` | POST | Yes | Claim conversation |
| `/conversations/{uuid}/messages` | GET | Yes | Get conversation messages |
| `/conversations/{uuid}/reply` | POST | Yes | Send reply |

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
- `rooms` - Available chat rooms
- `users` - System users
- `conversations` - Active chats
- `messages` - Conversation messages

Initialize with `db.init.sql` script.

## Troubleshooting
- **Port conflicts**: Ensure ports 8080 (backend) and 3000 (frontend) are available
- **Database issues**: Verify PostgreSQL is running and credentials match application.properties
- **Authentication failures**: Check username/password combinations

## Support
For assistance contact:  
**Sebastjan Šibenik**  
📧 sebastjan.sibenik@gmail.com  
📞 +386 40 172 326
s consistent markdown styling with proper headers, code blocks, tables, and JSON examples for easy reading.