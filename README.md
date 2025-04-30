# Spring Boot + React WebApp Template

A modern web application template combining Spring Boot for the backend and React for the frontend. This template provides a solid foundation for building full-stack web applications with a clean separation between frontend and backend.

## Project Overview

This template includes:

- **Backend**: Spring Boot 3.4.5 application with:
  - REST API support
  - JPA for database access
  - Spring Security
  - OpenAPI/Swagger documentation
  - MariaDB database connector
  - Lombok for reducing boilerplate code

- **Frontend**: React 19 application with:
  - TypeScript support
  - Vite as build tool
  - ESLint for code quality
  - Proxy configuration for API calls to backend

## Prerequisites

- Java 21 or higher
- Node.js (latest LTS version recommended)
- npm or yarn
- MariaDB (or another database with appropriate JDBC driver changes)
- IDE (IntelliJ IDEA recommended)

## Setup Instructions

### Clone the Repository

```bash
git clone <repository-url>
cd SpringBootReactJS
```

### Backend Setup

1. Navigate to the backend directory:
   ```bash
   cd backend
   ```

2. Build the project:
   ```bash
   ./mvnw clean install
   ```

3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

The backend will start on http://localhost:8080

### Frontend Setup

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   # or if you use yarn
   yarn
   ```

3. Start the development server:
   ```bash
   npm run dev
   # or with yarn
   yarn dev
   ```

The frontend will start on http://localhost:5173

## Customizing the Project

### Changing the Project Name

#### Backend

1. Update the `pom.xml` file:
   - Change the `groupId` from `org.mosdev.template` to your organization's group ID
   - Change the `artifactId` from `backend` to your preferred name
   - Update the `name` and `description` fields

2. Update the package structure:
   - Rename the package from `org.mosdev.template.backend` to your preferred package name
   - Update all import statements accordingly

3. Update the application properties:
   - In `application.properties`, change `spring.application.name=backend` to your application name

#### Frontend

1. Update the `package.json` file:
   - Change the `name` field from `frontend` to your preferred name

2. Update any references to the project name in the frontend code, particularly in:
   - `App.tsx`
   - Any other components that might reference the project name

### Database Configuration

The default configuration uses MariaDB. To configure your database:

1. Update the database connection properties in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mariadb://localhost:3306/your_database
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

2. If you want to use a different database, update the JDBC driver dependency in `pom.xml` and the corresponding connection URL.

## Running the Application

### Development Mode

Run both the backend and frontend as described in the setup instructions. The frontend will proxy API requests to the backend.

### Production Build

1. Build the frontend:
   ```bash
   cd frontend
   npm run build
   # or with yarn
   yarn build
   ```

2. Copy the contents of the `frontend/dist` directory to the `backend/src/main/resources/static` directory.

3. Build the backend:
   ```bash
   cd backend
   ./mvnw clean package
   ```

4. Run the packaged application:
   ```bash
   java -jar target/backend-0.0.1-SNAPSHOT.war
   ```

## API Documentation

The API documentation is available at:
- Swagger UI: http://localhost:8080/swagger-ui
- OpenAPI JSON: http://localhost:8080/api/api-docs

## Security

The default security configuration uses basic authentication:
- Username: admin
- Password: admin

For production, make sure to change these credentials in the `application.properties` file.

## License

[Add your license information here]

## Contributors

[Add contributor information here]

---

*This README is a template and should be customized for your specific project needs.*