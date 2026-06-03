## ADDED Requirements

### Requirement: Create a Vehicle
The system SHALL allow creating a new vehicle record by sending a POST request to `/api/vehicles` with a JSON payload.

#### Scenario: Successful vehicle creation
- **WHEN** a POST request is sent to `/api/vehicles` with a valid JSON payload containing make, model, and constructionYear
- **THEN** the system SHALL persist the vehicle, return HTTP 201 (Created), and return the created vehicle JSON (with generated ID) and a Location header

#### Scenario: Create vehicle validation failure
- **WHEN** a POST request is sent to `/api/vehicles` with invalid input data (e.g. blank make, blank model, or constructionYear before 1886)
- **THEN** the system SHALL return HTTP 400 (Bad Request)

### Requirement: Get a Vehicle
The system SHALL allow retrieving a single vehicle by its ID via a GET request to `/api/vehicles/{id}`.

#### Scenario: Retrieve existing vehicle
- **WHEN** a GET request is sent to `/api/vehicles/{id}` for an existing ID
- **THEN** the system SHALL return HTTP 200 (OK) with the matching vehicle's JSON details

#### Scenario: Retrieve non-existent vehicle
- **WHEN** a GET request is sent to `/api/vehicles/{id}` for a non-existent ID
- **THEN** the system SHALL return HTTP 404 (Not Found)

### Requirement: List Vehicles
The system SHALL allow listing all persisted vehicles via a GET request to `/api/vehicles`.

#### Scenario: List all vehicles
- **WHEN** a GET request is sent to `/api/vehicles`
- **THEN** the system SHALL return HTTP 200 (OK) with a JSON array of all vehicles

### Requirement: Update a Vehicle
The system SHALL allow updating an existing vehicle's attributes by sending a PUT request to `/api/vehicles/{id}` with a JSON payload.

#### Scenario: Successful vehicle update
- **WHEN** a PUT request is sent to `/api/vehicles/{id}` with an existing ID and a valid JSON payload
- **THEN** the system SHALL update the vehicle details and return HTTP 200 (OK) with the updated vehicle JSON

#### Scenario: Update non-existent vehicle
- **WHEN** a PUT request is sent to `/api/vehicles/{id}` with a non-existent ID
- **THEN** the system SHALL return HTTP 404 (Not Found)

#### Scenario: Update validation failure
- **WHEN** a PUT request is sent to `/api/vehicles/{id}` with an existing ID but invalid payload (e.g. blank model)
- **THEN** the system SHALL return HTTP 400 (Bad Request)

### Requirement: Delete a Vehicle
The system SHALL allow deleting a vehicle by its ID via a DELETE request to `/api/vehicles/{id}`.

#### Scenario: Successful vehicle deletion
- **WHEN** a DELETE request is sent to `/api/vehicles/{id}` for an existing ID
- **THEN** the system SHALL delete the vehicle from database and return HTTP 204 (No Content)

#### Scenario: Delete non-existent vehicle
- **WHEN** a DELETE request is sent to `/api/vehicles/{id}` for a non-existent ID
- **THEN** the system SHALL return HTTP 404 (Not Found)
