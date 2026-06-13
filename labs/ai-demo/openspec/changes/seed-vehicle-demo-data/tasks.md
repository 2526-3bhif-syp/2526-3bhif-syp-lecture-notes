## 1. Seed Script

- [x] 1.1 Create `src/main/resources/import.sql` with 7 single-line `INSERT INTO AI_VEHICLE (V_ID, V_MAKE, V_MODEL, V_CONSTRUCTION_YEAR) VALUES (...)` statements using the table from `design.md` Decision 4
- [x] 1.2 Append `ALTER SEQUENCE vehicle_id_seq RESTART WITH 100;` as the final statement
- [x] 1.3 Verify every seed row satisfies `@NotBlank` on `V_MAKE` / `V_MODEL` and `V_CONSTRUCTION_YEAR >= 1886`

## 2. Profile Configuration

- [x] 2.1 Add `%dev.quarkus.hibernate-orm.sql-load-script=import.sql` to `src/main/resources/application.properties`
- [x] 2.2 Add `%prod.quarkus.hibernate-orm.sql-load-script=no-file`
- [x] 2.3 Add `%test.quarkus.hibernate-orm.sql-load-script=no-file`
- [x] 2.4 Leave the unprefixed `quarkus.hibernate-orm.sql-load-script=no-file` as the safe default

## 3. Verification — Tests

- [x] 3.1 Start Postgres: `docker compose -f docker/docker-compose.yaml up -d`
- [x] 3.2 Run `mvn clean test` — all existing tests in `VehicleResourceTest` and `VehicleDatabaseTest` MUST pass with no modifications
- [x] 3.3 Confirm test profile did not seed (test assertions on empty initial state still hold)

## 4. Verification — Dev Run

- [ ] 4.1 Run `mvn quarkus:dev`
- [ ] 4.2 `curl http://localhost:8080/api/vehicles` — expect JSON array with ≥ 7 vehicles
- [ ] 4.3 `curl http://localhost:8080/api/vehicles/1` — expect HTTP 200, `make = Volkswagen`, `model = Golf VII`, `constructionYear = 2015`
- [ ] 4.4 POST a new vehicle and verify the returned `id` is ≥ 100 (sequence advanced)
- [ ] 4.5 Restart `quarkus:dev` and verify the table is reseeded (still 7 base rows + any newly created ones are gone because `drop-and-create`)
