AirTribe Course Management - Spring Boot (Gradle)

This project has been migrated to use Gradle as the build tool.

Features:
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Simple REST endpoints for Courses and Students

Quick start (Gradle)
1. Create a Postgres database named `airtribe_db` and set credentials in `src/main/resources/application.properties`.
2. Build and run with Gradle:

    gradle test
    gradle bootRun

   If you don't have Gradle installed, create the Gradle wrapper locally (once):

    gradle wrapper

   Then use the wrapper (Windows):

    gradlew.bat test
    gradlew.bat bootRun

3. API endpoints:
- GET /api/courses
- GET /api/courses/{id}
- POST /api/courses
- PUT /api/courses/{id}
- DELETE /api/courses/{id}

- GET /api/students
- GET /api/students/{id}
- POST /api/students
- PUT /api/students/{id}
- DELETE /api/students/{id}

Notes
- Adjust Java/Spring Boot versions in `build.gradle` if needed.
- The project uses `spring.jpa.hibernate.ddl-auto=update` for convenience; consider migrations for production.
- MapStruct requires annotation processing during the build; Gradle config includes the annotationProcessor setting.
- Integration tests use Testcontainers and require Docker.
- The old `pom.xml` has been deprecated and is no longer used; you can delete it if you prefer.
