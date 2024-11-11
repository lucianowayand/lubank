# Lubank - Bank App Api

## Lubank - Bank API
Lubank is a Spring Boot-based API for handling basic banking operations. The project uses Java 17 and PostgreSQL.

## Build & Run
### Prerequisites
* Java 17 or higher
* Gradle
* PostgreSQL

### Installation (Local)
1. Clone the repository:
```shell
git clone https://github.com/lucianowayand/lubank.git
cd lubank
```

2. Create a .env file in the project root using the provided .env.example:
```shell
cp .env.example .env
```
3. Edit .env with your PostgreSQL credentials and secret key:
```env
# PostgreSQL database credentials
DATABASE_URL=jdbc:postgresql://localhost:5432/lubank
DATABASE_USER=your_db_user
DATABASE_PASSWORD=your_db_password

# JWT Encryption key, must be 256-bits
SECRET_KEY=your_secret_key
```

4. Build the project:
```shell
./gradlew build
```

5. Run the application:
```shell
./gradlew bootRun
```
The API will be available at http://localhost:8080.

### Installation (Docker)
To make it easier to run the API, you can use Docker.
1. Build the Docker image:
```shell
docker build -t lubank:latest .
```

2. Run the Docker container:
```shell
docker run -p 8080:8080 lubank:latest
```
The .env file will be automatically copied during the Docker build step, so make sure it is present in the root directory.


## Dependencies
The project uses several key dependencies:

* Spring Boot Starter Web: Provides core features for building a REST API.
* Spring Boot Starter Data JPA: Integrates with JPA for database operations.
* Spring Boot Starter Data REST: Exposes JPA entities as RESTful endpoints automatically.
* Spring Boot DevTools: Enables hot reloading for quicker development.
* PostgreSQL Driver: JDBC driver for PostgreSQL connection.
* Flyway: Manages database migrations.
* Dotenv Java: Loads environment variables from a .env file.
* OAuth2 Resource Server: Secures the API with JWT-based authentication.
* Lombok: Reduces boilerplate code with annotations (e.g., @Data, @Builder).

## Database Migrations
The project uses Flyway for handling database schema migrations. To create a new migration, use the provided shell script create_migration.sh:

```shell
./create_migration.sh migration_name
```

This script generates a new migration file in the src/main/resources/db/migration/ folder, named with a timestamp for unique versioning. Example:

```shell
./create_migration.sh AddUsersTable
```

Output:

```bash
Migration file created: src/main/resources/db/migration/V1699372841__AddUsersTable.sql
```

The application will check for pending migrations on start and run the necessary migrations.
