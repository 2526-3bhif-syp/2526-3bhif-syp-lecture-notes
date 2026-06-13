## Context

Quarkus ships first-class OpenAPI + Swagger UI support via the `quarkus-smallrye-openapi` extension (SmallRye OpenAPI implementation of Eclipse MicroProfile OpenAPI). With the extension on the classpath:

- An OpenAPI 3.x document is generated from JAX-RS endpoints automatically at build time.
- Swagger UI is served at `/q/swagger-ui` (Quarkus default) — **only in `dev` and `test` profiles** unless `quarkus.swagger-ui.always-include=true` is set.
- The raw spec is served at `/q/openapi` (YAML default; `Accept: application/json` or `?format=json` for JSON).

The current `VehicleResource` uses standard JAX-RS annotations only; method return types include both raw `List<Vehicle>` and `Response`, which limits what auto-generation can infer about response status codes.

## Goals / Non-Goals

**Goals:**
- One-time setup: extension + minimal config + annotations.
- Swagger UI works in `prod` too (HTL demo deployments — students should be able to explore the API regardless of profile).
- Accurate response codes per endpoint: 200, 201, 204, 400, 404 wherever applicable.
- Schema annotations on `Vehicle` make field constraints (`@NotBlank`, `@Min`) visible in the spec.

**Non-Goals:**
- Authentication / security schemes — API is open in this lab.
- Versioned API contracts (`/v1`, `/v2`) — out of scope.
- Custom Swagger UI theme or CSS overrides.
- Generating clients from the OpenAPI spec.
- Hosting the OpenAPI YAML in a repo `docs/` folder.

## Decisions

### Decision 1: Use `quarkus-smallrye-openapi`

Use the official Quarkus extension. Single dependency provides both the OpenAPI generator and the Swagger UI bundle.

**Why:** Native integration, zero configuration to get started, version-aligned with the Quarkus platform BOM (3.36.0), no transitive servlet container needed.

**Alternatives:**
- `springdoc-openapi` — rejected; Spring-only.
- Hand-written OpenAPI YAML — rejected; drifts from code immediately.
- `quarkus-smallrye-openapi` + separate redoc/elements UI — rejected; Swagger UI is sufficient and bundled.

### Decision 2: Enable Swagger UI in all profiles

Set `quarkus.swagger-ui.always-include=true`.

**Why:** HTL lab context — students/instructors run the demo in different profiles and benefit from exploring the API from any environment. Security is not a concern: there is no auth, no PII, no production deployment.

**Alternatives:**
- Default (dev/test only) — rejected; would force students into curl/HTTP files when running a packaged jar.
- Gate via `%dev.` + `%test.` only — rejected; same reason.

### Decision 3: OpenAPI metadata via `application.properties`

Set `mp.openapi.extensions.smallrye.info.*` (or the Quarkus shortcuts `quarkus.smallrye-openapi.info-*`) for title, version, description, contact, license.

**Why:** Keeps metadata declarative and version-controlled in one place. Avoids a Java `OASFilter` class for trivial info.

### Decision 4: Annotation strategy

Annotate the resource methods and DTO sparingly but consistently:

- `@Tag(name = "Vehicles", description = "CRUD operations on vehicles")` on the class.
- `@Operation(summary = "...", description = "...")` on each method.
- `@APIResponse(responseCode = "...", description = "...")` for each documented status code.
- `@Schema(example = "...", description = "...")` on `Vehicle` fields to surface examples and constraints in the UI.
- Method return type stays as-is — annotations carry the spec; we do not refactor `Response` returns to typed responses.

**Why:** Reading the auto-generated spec without annotations gives generic, sometimes incorrect status codes (e.g., POST showing only 200). Annotations fix this without changing runtime behavior.

### Decision 5: Smoke test only, not a contract test

Add a single rest-assured test that `/q/openapi` returns 200 and contains the expected paths (`/api/vehicles`, `/api/vehicles/{id}`). Do **not** snapshot-test the full OpenAPI document.

**Why:** Snapshot tests on auto-generated specs are brittle (whitespace, field order, version bumps). One smoke check provides 90% of the value at 5% of the maintenance cost.

## Risks / Trade-offs

- **Risk:** `always-include=true` exposes Swagger UI in any future production deploy that this project gets reused for. → **Mitigation:** Note this in the proposal and `application.properties` comment; future deployments can flip it.
- **Risk:** Annotation drift — endpoints change, annotations don't. → **Mitigation:** Smoke test catches missing paths; annotations are reviewed alongside endpoint changes (manual discipline; no automation).
- **Trade-off:** Annotations add visual noise to `VehicleResource`. Acceptable for the teaching benefit of a clean Swagger UI.
- **Trade-off:** SmallRye OpenAPI extension adds a few MB and several seconds to build time. Acceptable.

## Migration Plan

1. Add extension to `pom.xml`.
2. Add OpenAPI properties + `always-include=true` to `application.properties`.
3. Add annotations to `VehicleResource` and `Vehicle`.
4. Add the smoke test.
5. Run `mvn clean test` — green.
6. Run `mvn quarkus:dev` — verify `/q/swagger-ui` renders and `/q/openapi` returns a non-empty spec.

Rollback: revert all files; remove the dependency.

## Open Questions

None.
