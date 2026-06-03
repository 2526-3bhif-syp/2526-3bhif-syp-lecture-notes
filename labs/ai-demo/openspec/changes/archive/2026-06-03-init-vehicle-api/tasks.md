## 1. Project Setup

- [x] 1.1 Initialize Quarkus application structure using Maven
- [x] 1.2 Add required dependencies for Hibernate ORM, PostgreSQL JDBC driver, JAX-RS (Resteasy Reactive JSON-B or Jackson), Jakarta Validation, AssertJ Core (3.27.7), and AssertJ-DB (3.0.2) in pom.xml
- [x] 1.3 Create `docker/docker-compose.yaml` configured with PostgreSQL 18, database name `db`, username `app`, and password `app`
- [x] 1.4 Configure `src/main/resources/application.properties` with database connection settings and Hibernate ORM configuration

## 2. Persistence Layer

- [x] 2.1 Implement `Vehicle` JPA entity with custom annotations (table `AI_VEHICLE`, primary key sequence `vehicle_id_seq`, columns `V_ID`, `V_MAKE`, `V_MODEL`, `V_CONSTRUCTION_YEAR`)
- [x] 2.2 Implement `VehicleRepository` extending `PanacheRepository<Vehicle>`

## 3. REST API Implementation

- [x] 3.1 Create `VehicleResource` under path `/api/vehicles` exposing CRUD endpoints
- [x] 3.2 Implement POST endpoint to create vehicles with validation constraints
- [x] 3.3 Implement GET endpoints for listing all vehicles and retrieving a single vehicle by ID
- [x] 3.4 Implement PUT endpoint to update existing vehicles with validation constraints
- [x] 3.5 Implement DELETE endpoint to remove vehicles by ID

## 4. Test Suite Implementation

- [x] 4.1 Write API integration tests using RestAssured and AssertJ Core to verify HTTP status codes, JSON responses, and validation constraints
- [x] 4.2 Write database verification tests using AssertJ-DB to assert correct database state during operations
