# Helpdesk Application (Kaldi Homework)

## Project Description

This project implements a simple REST API backend for a helpdesk system with two types of clients:

- **Mobile users**: select a "room" (Technical, Services, Conversational), send messages, and wait for operator responses.
- **Operators (web browser)**: log in, see pending conversations, claim conversations, and reply to users.

The backend is built with **Quarkus** and uses **HTTP Basic Authentication**.  
The frontend is a **React** application that communicates with the backend API.

---

## Prerequisites

- Java 17+ (for Quarkus backend)  
- Node.js 16+ and npm (for React frontend)  
- PostgreSQL database  

---

## Setup and Run Backend

1. Create and initialize the PostgreSQL database:  
   Run the `db.init.sql` script located in the `/db` folder to create tables and seed data.

2. Set environment variables for database credentials:

   **Linux/macOS:**

   ```bash
   export QUARKUS_DATASOURCE_USERNAME=your_db_user
   export QUARKUS_DATASOURCE_PASSWORD=your_db_password
   ```

**Windows (PowerShell):**

   ```powershell
   setx QUARKUS_DATASOURCE_USERNAME "your_db_user"
   setx QUARKUS_DATASOURCE_PASSWORD "your_db_password"
   ```

*Note: After running `setx`, restart your terminal or IDE for changes to take effect.*

3. Build and run the backend using Maven:

   **Linux/macOS:**

   ```bash
   ./mvnw clean compile quarkus:dev
   ```

   **Windows (Command Prompt or PowerShell):**

   ```cmd
   mvnw.cmd clean compile quarkus:dev
   ```

   Backend will start at [http://localhost:8080](http://localhost:8080)

---

## Setup and Run Frontend

1. Navigate to the frontend folder:

   ```bash
   cd frontend
   ```

2. Install dependencies:

   ```bash
   npm install
   ```

3. Start the React development server:

   ```bash
   npm start
   ```

   Frontend will start at [http://localhost:3000](http://localhost:3000) and communicate with backend on port 8080.

---

## Testing the Application

- **Backend only:**  
  Use tools like Postman or curl to call backend REST endpoints on `http://localhost:8080/api/v1/...`

- **Full application (Frontend + Backend):**  
  Run both backend and frontend as described above.  
  Open your browser at [http://localhost:3000](http://localhost:3000) and interact with the UI.

---

## Notes

- The backend uses HTTP Basic authentication. Use operator credentials from the seeded users in the database to log in via frontend or API.
- The `db.init.sql` script is included in the repo to create and seed the database; this ensures consistent setup for testing.

---

## Contact

For any questions, contact Sebastjan Sibenik.

---

*Good luck and thank you for reviewing my homework!*
```

This should render cleanly on GitHub and be easy to follow. Let me know if you want me to add anything else!