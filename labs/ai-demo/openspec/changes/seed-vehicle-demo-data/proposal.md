## Why

Developers need realistic sample data in the `AI_VEHICLE` table to manually test the API (curl, browser, IDE HTTP client) without first issuing POST requests after every restart. Since `quarkus.hibernate-orm.database.generation=drop-and-create` wipes the schema on every boot, the seed must repopulate automatically — and only in development, never in production.

## What Changes

- Add a Hibernate SQL load script (`src/main/resources/import.sql`) containing single-line `INSERT` statements for at least 7 realistic vehicles (mix of classic, modern ICE, EV).
- Enable the load script only in the `dev` profile via `%dev.quarkus.hibernate-orm.sql-load-script=import.sql`.
- Explicitly disable the load script in `prod` and `test` profiles to avoid polluting production data and breaking existing integration tests that assume an empty table.
- Use fixed primary key values (`V_ID = 1..7`) for predictable demo URLs (e.g., `GET /api/vehicles/1`).
- Reset `vehicle_id_seq` to `100` after seeding so subsequent POSTs do not collide with seeded IDs.

## Capabilities

### New Capabilities
- `vehicle-demo-data`: Provides automatic seeding of realistic vehicle sample records into the `AI_VEHICLE` table on application start in the `dev` profile only.

### Modified Capabilities
<!-- None. Existing `vehicle-api` requirements remain unchanged; demo data is additive and only affects dev runtime state. -->

## Impact

- **Code:** New file `src/main/resources/import.sql`.
- **Config:** New properties in `src/main/resources/application.properties` (`%dev.`, `%prod.`, `%test.` overrides for `quarkus.hibernate-orm.sql-load-script`).
- **Tests:** Existing `VehicleResourceTest` and `VehicleDatabaseTest` MUST continue to pass — guaranteed by disabling the load script in the `test` profile.
- **DB:** Sequence `vehicle_id_seq` advanced to `100` on dev boot.
- **Dependencies:** None added.
