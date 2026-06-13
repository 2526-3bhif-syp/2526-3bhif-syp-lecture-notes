## ADDED Requirements

### Requirement: Auto-seed demo vehicles on dev boot
The system SHALL automatically populate the `AI_VEHICLE` table with at least 7 realistic demo vehicles when the application starts in the `dev` profile.

#### Scenario: Dev startup seeds vehicles
- **WHEN** the application is started with the `dev` Quarkus profile and the database has just been recreated by `drop-and-create`
- **THEN** the `AI_VEHICLE` table SHALL contain at least 7 vehicle rows immediately after startup, and `GET /api/vehicles` SHALL return them as a JSON array

### Requirement: Seed data satisfies API validation
The seeded vehicle rows SHALL conform to the validation rules enforced by the Vehicle API, namely non-blank `V_MAKE`, non-blank `V_MODEL`, and `V_CONSTRUCTION_YEAR` ≥ 1886.

#### Scenario: Every seeded row is API-valid
- **WHEN** the seeded rows are retrieved via `GET /api/vehicles/{id}` for each seeded ID
- **THEN** each response SHALL contain a non-empty `make`, a non-empty `model`, and a `constructionYear` value greater than or equal to 1886

### Requirement: Seeded vehicles cover diverse makes, models, and years
The seed set SHALL include vehicles from different manufacturers, different model designations, and a range of construction years spanning classic, modern, and recent eras.

#### Scenario: Seed diversity
- **WHEN** all seeded vehicles are retrieved via `GET /api/vehicles`
- **THEN** the result SHALL contain at least five distinct `make` values and span a construction year range of at least 30 years

### Requirement: Predictable demo IDs
Seeded vehicles SHALL use fixed primary key values starting at `1` and incrementing by `1`, so demo URLs remain stable across restarts.

#### Scenario: First seeded vehicle has ID 1
- **WHEN** `GET /api/vehicles/1` is called immediately after a dev-profile startup
- **THEN** the system SHALL return HTTP 200 with a vehicle JSON whose `id` is `1`

### Requirement: Sequence advanced beyond seed range
After seeding, the `vehicle_id_seq` sequence SHALL be positioned high enough that new vehicles created via `POST /api/vehicles` do not collide with seeded IDs.

#### Scenario: New POST does not collide with seeded IDs
- **WHEN** a `POST /api/vehicles` request is sent immediately after a dev-profile startup
- **THEN** the response SHALL be HTTP 201 with a generated `id` strictly greater than the highest seeded ID

### Requirement: No seeding in prod or test profiles
The seeding mechanism SHALL be disabled when the application runs under the `prod` or `test` Quarkus profile.

#### Scenario: Test profile leaves table empty
- **WHEN** the existing integration test suite (`VehicleResourceTest`, `VehicleDatabaseTest`) runs under the `test` profile
- **THEN** the `AI_VEHICLE` table SHALL be empty at the start of each test class and all existing tests SHALL continue to pass without modification

#### Scenario: Prod profile leaves table empty
- **WHEN** the application is started with the `prod` Quarkus profile
- **THEN** the `AI_VEHICLE` table SHALL NOT be populated by any seed script
