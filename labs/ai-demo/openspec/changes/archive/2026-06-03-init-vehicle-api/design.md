## Context

The application is a new Quarkus-based microservice that needs to manage `Vehicle` records. Data persistence will be handled via PostgreSQL 18 running locally in a Docker container.

## Goals / Non-Goals

**Goals:**
- Provide standard REST CRUD operations under `/api/vehicles`.
- Structure data access using the Repository Pattern with Hibernate ORM (Panache).
- Map the Java class and its fields to custom table and column names (`AI_VEHICLE`, `V_ID`, `V_MAKE`, `V_MODEL`, `V_CONSTRUCTION_YEAR`).
- Generate primary keys using the `vehicle_id_seq` sequence.
- Support validation (blank checks, construction year boundaries).
- Implement database and integration tests using AssertJ, AssertJ-DB, and RestAssured.

**Non-Goals:**
- Authentication or role-based access control.
- Pagination or advanced filtering of vehicles (simple list retrieval is sufficient).
- Frontend UI components.

## Decisions

### Decision 1: Database and Access Pattern
- **Choice**: PostgreSQL 18 with Hibernate ORM using `PanacheRepository<Vehicle>`.
- **Rationale**: The requirements specify PostgreSQL 18, a docker-compose setup, and the Repository pattern (explicitly avoiding the Active Record pattern).

### Decision 2: Custom Mapping & Sequence Generator
- **Choice**: Map entity to table name `AI_VEHICLE` (annotated with `@Table(name = "AI_VEHICLE")` instead of `@Column` for the class table) and column prefixes (`V_ID`, `V_MAKE`, `V_MODEL`, `V_CONSTRUCTION_YEAR`). The ID generation will use `GenerationType.SEQUENCE` pointing to sequence generator name `vehicle_seq` mapped to the database sequence `vehicle_id_seq`.
- **Rationale**: Strict alignment with naming constraints provided in requirements.

### Decision 3: Local Dev Infrastructure
- **Choice**: A custom `docker-compose.yaml` under `docker/` directory configuration containing PostgreSQL 18 setup (db name `db`, user `app`, password `app`).
- **Rationale**: Mandated by constraints.

### Decision 4: Test Infrastructure
- **Choice**: QuarkusTest, RestAssured, AssertJ Core (version 3.27.7) for API assertions, and AssertJ-DB (version 3.0.2) for direct database assertions.
- **Rationale**: Mandated by constraints.

## Risks / Trade-offs

- **[Risk] Docker Environment Availability** → Local environment must have Docker running to execute integration tests and the dev profile properly.
  - *Mitigation*: Clearly document Docker prerequisites in instructions, and use Quarkus Dev Services or docker-compose integration.
- **[Risk] Database Initialization Timing** → Quarkus dev mode starting before PostgreSQL is ready in Docker.
  - *Mitigation*: Configure appropriate health checks in docker-compose.
