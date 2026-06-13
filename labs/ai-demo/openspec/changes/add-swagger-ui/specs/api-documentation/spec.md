## ADDED Requirements

### Requirement: Serve OpenAPI 3 specification
The system SHALL expose a machine-readable OpenAPI 3 specification describing every endpoint of the Vehicle REST API.

#### Scenario: OpenAPI document is reachable
- **WHEN** a `GET /q/openapi` request is sent to the running application
- **THEN** the system SHALL return HTTP 200 with a non-empty OpenAPI 3 document that lists at least the paths `/api/vehicles` and `/api/vehicles/{id}`

#### Scenario: OpenAPI document available as JSON
- **WHEN** a `GET /q/openapi?format=json` request is sent
- **THEN** the system SHALL return HTTP 200 with a valid JSON-formatted OpenAPI 3 document

### Requirement: Serve Swagger UI
The system SHALL serve an interactive Swagger UI that renders the OpenAPI document for browser-based API exploration.

#### Scenario: Swagger UI loads in dev profile
- **WHEN** a `GET /q/swagger-ui` request is sent while the application runs under the `dev` profile
- **THEN** the system SHALL return HTTP 200 with a Swagger UI HTML page

#### Scenario: Swagger UI loads in prod profile
- **WHEN** a `GET /q/swagger-ui` request is sent while the application runs under the `prod` profile
- **THEN** the system SHALL return HTTP 200 with a Swagger UI HTML page

### Requirement: OpenAPI metadata reflects the Vehicle API
The OpenAPI document SHALL contain accurate metadata identifying the API.

#### Scenario: Info block populated
- **WHEN** the OpenAPI document is retrieved
- **THEN** the `info` object SHALL contain a non-empty `title`, a non-empty `version`, and a non-empty `description` that mention vehicles

### Requirement: Endpoint documentation describes responses
The OpenAPI document SHALL describe the expected HTTP status codes for each operation of the Vehicle API, including success codes (200, 201, 204) and applicable error codes (400, 404).

#### Scenario: POST documents 201 and 400
- **WHEN** the OpenAPI document is inspected for `POST /api/vehicles`
- **THEN** it SHALL list at least responses `201` and `400`

#### Scenario: GET by id documents 200 and 404
- **WHEN** the OpenAPI document is inspected for `GET /api/vehicles/{id}`
- **THEN** it SHALL list at least responses `200` and `404`

#### Scenario: DELETE documents 204 and 404
- **WHEN** the OpenAPI document is inspected for `DELETE /api/vehicles/{id}`
- **THEN** it SHALL list at least responses `204` and `404`

### Requirement: Schema describes Vehicle fields
The OpenAPI document SHALL describe the `Vehicle` schema with its fields and their validation constraints.

#### Scenario: Vehicle schema present
- **WHEN** the OpenAPI document is retrieved
- **THEN** the components/schemas section SHALL contain a `Vehicle` (or equivalent) schema with the properties `id`, `make`, `model`, and `construction_year` (or `constructionYear`)
