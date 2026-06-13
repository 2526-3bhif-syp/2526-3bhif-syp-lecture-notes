## Why

Students and developers need a discoverable, interactive way to explore the Vehicle API endpoints without crafting curl commands by hand. Swagger UI provides a browser-based playground and an OpenAPI 3.x machine-readable contract — both standard expectations for any modern REST service and a strong teaching aid for HTL students learning HTTP/REST.

## What Changes

- Add the `quarkus-smallrye-openapi` extension to `pom.xml`.
- Enable Swagger UI to remain accessible in **all** profiles (not only dev) by setting `quarkus.swagger-ui.always-include=true`. (Default behavior: dev-only.)
- Customize OpenAPI metadata (title, version, description) for the Vehicle API.
- Annotate `VehicleResource` and `Vehicle` with OpenAPI annotations (`@Tag`, `@Operation`, `@APIResponse`, `@Schema`) so the generated docs describe parameters, request bodies, and response codes accurately.
- Document the Swagger UI URL and OpenAPI JSON URL in the project's continuation notes (no new docs file).

## Capabilities

### New Capabilities
- `api-documentation`: Provides an OpenAPI 3 contract and an interactive Swagger UI for the Vehicle REST API.

### Modified Capabilities
<!-- None. Vehicle API endpoints, request/response shapes, and validation behavior are unchanged. -->

## Impact

- **Code:** `VehicleResource.java` and `Vehicle.java` gain OpenAPI annotations (non-behavioral).
- **Dependencies:** New extension `io.quarkus:quarkus-smallrye-openapi`.
- **Config:** New properties in `application.properties` for OpenAPI metadata and `always-include`.
- **Tests:** Existing tests remain valid. One small smoke test added to confirm `/q/openapi` returns 200 and `/q/swagger-ui` is reachable.
- **Endpoints exposed:** `/q/openapi` (YAML by default, `?format=json` for JSON) and `/q/swagger-ui`.
