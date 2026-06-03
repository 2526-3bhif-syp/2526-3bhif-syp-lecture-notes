## Why

This change introduces a RESTful CRUD API and database persistence for a "Vehicle" entity in a Quarkus application. It sets up the database environment using PostgreSQL in a Docker container and establishes the base repository and API endpoints.

## What Changes

- Add a docker-compose setup to run PostgreSQL 18 in a local Docker container for persistence.
- Implement the `Vehicle` entity class with specified table and column mappings.
- Implement `VehicleRepository` following the repository pattern for data access.
- Implement a RESTful HTTP endpoint under `/api/vehicles` for full CRUD operations using JSON.
- Add input validation for POST and PUT requests.
- Add integration and database tests using AssertJ, AssertJ-DB, and RestAssured.

## Capabilities

### New Capabilities

- `vehicle-api`: RESTful API endpoints and persistence layer for managing vehicle records.

### Modified Capabilities

## Impact

- Adds new dependencies: `assertj-core` (3.27.7), `assertj-db` (3.0.2), and PostgreSQL JDBC driver/Hibernate ORM.
- Defines a new database schema containing the `AI_VEHICLE` table and `vehicle_id_seq` sequence.
- Sets up a containerized local development database.
