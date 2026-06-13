## Context

The Quarkus app uses `quarkus.hibernate-orm.database.generation=drop-and-create`, which recreates the schema on every boot. After recreation, Hibernate optionally runs a SQL load script (`quarkus.hibernate-orm.sql-load-script`) — currently set to `no-file`. The `AI_VEHICLE` table uses `V_*` column prefixes and a Postgres sequence `vehicle_id_seq` (allocation size 1) for primary keys.

Existing tests (`VehicleResourceTest`, `VehicleDatabaseTest`) assume an empty `AI_VEHICLE` table on startup. Production deployments (if any) must not contain demo data.

## Goals / Non-Goals

**Goals:**
- Auto-populate `AI_VEHICLE` with ≥ 7 realistic vehicles on every `dev`-profile boot.
- Use fixed, predictable primary keys (1..7) so docs and demo URLs stay stable.
- Keep production and test profiles untouched.
- Zero new Java code, zero new dependencies.

**Non-Goals:**
- Reseeding between dev tests at runtime (Hibernate handles boot-time only).
- Idempotent seeding under `update` strategy — out of scope while strategy is `drop-and-create`.
- Bean Validation enforcement on seed inserts (SQL bypasses validators; values are hand-picked to satisfy `@NotBlank` + `@Min(1886)`).
- UI / Swagger / pagination changes.

## Decisions

### Decision 1: Hibernate `import.sql` over `@Observes StartupEvent`

Use a SQL load script rather than a Java CDI startup observer.

**Why:** Pure declarative, zero new code, native Hibernate feature. The observer route adds a Java class, requires `@Transactional`, and complicates test isolation.

**Alternatives:**
- `@Observes StartupEvent`: would run through repository + Bean Validation; rejected — adds code, harder to gate per-profile cleanly.
- Flyway/Liquibase: overkill for demo seed; would also need to coexist with `drop-and-create`.

### Decision 2: Per-profile gating via `%dev.` prefix

Three explicit overrides in `application.properties`:

```
%dev.quarkus.hibernate-orm.sql-load-script=import.sql
%prod.quarkus.hibernate-orm.sql-load-script=no-file
%test.quarkus.hibernate-orm.sql-load-script=no-file
```

**Why:** Defense in depth. Explicit `no-file` on `prod` and `test` ensures the default (currently `no-file` at the unprefixed level) cannot be accidentally flipped later and leak demo data.

**Alternatives:**
- Single global `import.sql` — rejected; pollutes production and breaks existing tests.
- Only `%dev.` set, rely on default — rejected; brittle if someone edits the base property.

### Decision 3: Fixed IDs 1..7 with sequence restart at 100

Seed each row with explicit `V_ID = 1..7`, then `ALTER SEQUENCE vehicle_id_seq RESTART WITH 100`.

**Why:** Predictable URLs (`GET /api/vehicles/3` is always the Tesla). Restarting the sequence above the seeded range prevents PK collisions on the first POST.

**Alternatives:**
- `nextval('vehicle_id_seq')` per row — rejected; IDs become 1..7 *only* on first boot, but order/visibility less obvious.
- Restart at 8 — works, but leaves no room if someone adds an 8th seed row later.

### Decision 4: Vehicle selection (validation-safe)

Seven rows covering classic / modern ICE / EV / new:

| V_ID | Make          | Model        | Year |
|------|---------------|--------------|------|
| 1    | Volkswagen    | Golf VII     | 2015 |
| 2    | BMW           | M3 E46       | 2003 |
| 3    | Tesla         | Model 3      | 2022 |
| 4    | Toyota        | Corolla      | 2019 |
| 5    | Mercedes-Benz | W123 240D    | 1981 |
| 6    | Audi          | Quattro      | 1984 |
| 7    | Porsche       | 911 Carrera  | 2024 |

All satisfy `@NotBlank` (non-empty make/model) and `@Min(1886)` (oldest = 1981).

### Decision 5: Single-line INSERTs

Each `INSERT` on one line. Hibernate's `import.sql` parser is line-based by default; multi-line statements require extra config and are fragile.

## Risks / Trade-offs

- **Risk:** Switching `database.generation` from `drop-and-create` to `update` would cause duplicate-key errors on the second boot. → **Mitigation:** Document in `import.sql` header; revisit seed strategy if generation mode changes.
- **Risk:** Bean Validation rules drift (e.g., adding `@Max` on year). Seed data could silently violate new constraints since SQL bypasses validators. → **Mitigation:** Constraint changes to `Vehicle` must include a review of `import.sql`. Note in the entity is unnecessary; tasks.md tracks this.
- **Risk:** Developer runs `mvn quarkus:dev` with a stale Postgres container that has data from a prior `update`-mode run. → **Mitigation:** N/A while strategy is `drop-and-create` — Hibernate drops the table first.
- **Trade-off:** Hard-coded IDs reduce realism (real apps don't ship with `id=1`) but greatly improve teachability for HTL students.

## Migration Plan

1. Add `import.sql`.
2. Update `application.properties` with three profile-prefixed overrides.
3. Run `mvn clean test` — must stay green (test profile disables the script).
4. Run `mvn quarkus:dev` — verify 7 rows present via `GET /api/vehicles`.

Rollback: revert both files; `drop-and-create` clears seed on next boot.

## Open Questions

None.
