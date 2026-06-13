## 1. Dependency

- [ ] 1.1 Add `<dependency><groupId>io.quarkus</groupId><artifactId>quarkus-smallrye-openapi</artifactId></dependency>` to `pom.xml`

## 2. Configuration

- [ ] 2.1 Add `quarkus.swagger-ui.always-include=true` to `application.properties`
- [ ] 2.2 Add `quarkus.smallrye-openapi.info-title=Vehicle API` (or `mp.openapi.extensions.smallrye.info.title=...` — use whichever the Quarkus 3.36 docs recommend)
- [ ] 2.3 Add `quarkus.smallrye-openapi.info-version=1.0.0`
- [ ] 2.4 Add `quarkus.smallrye-openapi.info-description=CRUD operations for vehicles (make, model, construction year).`

## 3. Resource Annotations

- [ ] 3.1 Add `@Tag(name = "Vehicles", description = "CRUD operations on vehicles")` to `VehicleResource`
- [ ] 3.2 Add `@Operation(summary, description)` + `@APIResponse(responseCode = "200")` to `list()`
- [ ] 3.3 Add `@Operation` + `@APIResponse(200)` + `@APIResponse(404)` to `get()`
- [ ] 3.4 Add `@Operation` + `@APIResponse(201)` + `@APIResponse(400)` to `create()`
- [ ] 3.5 Add `@Operation` + `@APIResponse(200)` + `@APIResponse(400)` + `@APIResponse(404)` to `update()`
- [ ] 3.6 Add `@Operation` + `@APIResponse(204)` + `@APIResponse(404)` to `delete()`

## 4. Entity Annotations

- [ ] 4.1 Add `@Schema(description = "A vehicle record")` to `Vehicle` class
- [ ] 4.2 Add `@Schema(readOnly = true, example = "1")` to `id` field
- [ ] 4.3 Add `@Schema(example = "Volkswagen", description = "Manufacturer of the vehicle")` to `make`
- [ ] 4.4 Add `@Schema(example = "Golf VII", description = "Model designation")` to `model`
- [ ] 4.5 Add `@Schema(example = "2015", description = "Year the vehicle was manufactured (>= 1886)")` to `construction_year`

## 5. Smoke Test

- [ ] 5.1 Add `OpenApiSmokeTest.java` under `src/test/java/org/acme/vehicle/openapi/` with a single `@QuarkusTest` rest-assured assertion that `GET /q/openapi` returns 200 and the body contains the substring `/api/vehicles`

## 6. Verification

- [ ] 6.1 Run `mvn clean test` — all tests green (existing + new smoke test)
- [ ] 6.2 Run `mvn quarkus:dev`, open `http://localhost:8080/q/swagger-ui` in a browser — confirm UI renders all 5 endpoints with the documented response codes
- [ ] 6.3 Open `http://localhost:8080/q/openapi?format=json` — confirm JSON contains paths and schemas
