# Continuation Prompt: Quarkus Vehicle API Project

This document outlines the current state and structure of the Quarkus Vehicle API project to help you resume development seamlessly.

## Project Context & Architecture

- **Framework:** Quarkus
- **Java Version:** 21+ (configured release 21)
- **Database:** PostgreSQL 18
  - **Docker Setup:** Run via `docker compose -f docker/docker-compose.yaml up -d`
  - **Database name:** `db`
  - **Username/Password:** `app` / `app`
- **Data Access:** Repository Pattern (explicitly avoiding Active Record / PanacheEntity).
- **Custom Mapping Constraints:**
  - Table name: `AI_VEHICLE`
  - Primary Key: Generated via sequence `vehicle_id_seq`
  - Columns: Prefixed with `V_` (`V_ID`, `V_MAKE`, `V_MODEL`, `V_CONSTRUCTION_YEAR`)
- **Testing Tools:**
  - `rest-assured` for endpoint tests.
  - `assertj-core` (3.27.7) for API assertions.
  - `assertj-db` (3.0.2) for database assertions.
    - *Note on AssertJ-DB 3.0.2:* Public constructors for `Table` and `Request` have been removed. Use the fluent builder instead:
      ```java
      AssertDbConnection connection = AssertDbConnectionFactory.of(dataSource).create();
      Table table = connection.table("AI_VEHICLE").build();
      ```

## File Map

- **Entity Class:** [Vehicle.java](file:///Users/stuetz/SynologyDrive/htl/skripten/pre.syp.itp.3jg/2526.3bhif.syp/2526-3bhif-syp-lecture-notes/labs/ai-demo/src/main/java/org/acme/vehicle/entity/Vehicle.java)
- **Repository Class:** [VehicleRepository.java](file:///Users/stuetz/SynologyDrive/htl/skripten/pre.syp.itp.3jg/2526.3bhif.syp/2526-3bhif-syp-lecture-notes/labs/ai-demo/src/main/java/org/acme/vehicle/repository/VehicleRepository.java)
- **Resource Class:** [VehicleResource.java](file:///Users/stuetz/SynologyDrive/htl/skripten/pre.syp.itp.3jg/2526.3bhif.syp/2526-3bhif-syp-lecture-notes/labs/ai-demo/src/main/java/org/acme/vehicle/resource/VehicleResource.java)
- **docker-compose.yaml:** [docker-compose.yaml](file:///Users/stuetz/SynologyDrive/htl/skripten/pre.syp.itp.3jg/2526.3bhif.syp/2526-3bhif-syp-lecture-notes/labs/ai-demo/docker/docker-compose.yaml)
- **application.properties:** [application.properties](file:///Users/stuetz/SynologyDrive/htl/skripten/pre.syp.itp.3jg/2526.3bhif.syp/2526-3bhif-syp-lecture-notes/labs/ai-demo/src/main/resources/application.properties)
- **API Tests:** [VehicleResourceTest.java](file:///Users/stuetz/SynologyDrive/htl/skripten/pre.syp.itp.3jg/2526.3bhif.syp/2526-3bhif-syp-lecture-notes/labs/ai-demo/src/test/java/org/acme/vehicle/resource/VehicleResourceTest.java)
- **Database Tests:** [VehicleDatabaseTest.java](file:///Users/stuetz/SynologyDrive/htl/skripten/pre.syp.itp.3jg/2526.3bhif.syp/2526-3bhif-syp-lecture-notes/labs/ai-demo/src/test/java/org/acme/vehicle/repository/VehicleDatabaseTest.java)

## What Was Completed

1. Bootstrapped Quarkus with Panache, Jackson, Validator, and Postgres dependencies.
2. Formulated and synced the `vehicle-api` capability specification.
3. Created the Docker Compose PostgreSQL 18 config.
4. Mapped the `Vehicle` entity according to exact column name prefixes.
5. Implemented JAX-RS CRUD endpoints with validations under `/api/vehicles`.
6. Verified integration tests and database state assertions compile and validate successfully.

## Next Steps

1. Start the database locally (`docker compose -f docker/docker-compose.yaml up -d`).
2. Run the application or tests (`mvn clean test`).
3. Formulate a new OpenSpec change if additional functionality is required (e.g., adding user management, Swagger UI support, pagination, etc.).
