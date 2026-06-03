package org.acme.vehicle.resource;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.acme.vehicle.entity.Vehicle;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VehicleResourceTest {

    private static Long createdId;

    @Test
    @Order(1)
    public void testCreateVehicle() {
        Vehicle vehicle = new Vehicle("Tesla", "Model S", 2022);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(vehicle)
                .when()
                .post("/api/vehicles")
                .then()
                .statusCode(201)
                .extract()
                .response();

        Vehicle created = response.as(Vehicle.class);
        assertThat(created).isNotNull();
        assertThat(created.getId()).isNotNull();
        assertThat(created.getMake()).isEqualTo("Tesla");
        assertThat(created.getModel()).isEqualTo("Model S");
        assertThat(created.getConstruction_year()).isEqualTo(2022);

        String location = response.header("Location");
        assertThat(location).contains("/api/vehicles/" + created.getId());

        createdId = created.getId();
    }

    @Test
    @Order(2)
    public void testGetVehicle() {
        Response response = given()
                .when()
                .get("/api/vehicles/" + createdId)
                .then()
                .statusCode(200)
                .extract()
                .response();

        Vehicle vehicle = response.as(Vehicle.class);
        assertThat(vehicle).isNotNull();
        assertThat(vehicle.getId()).isEqualTo(createdId);
        assertThat(vehicle.getMake()).isEqualTo("Tesla");
        assertThat(vehicle.getModel()).isEqualTo("Model S");
        assertThat(vehicle.getConstruction_year()).isEqualTo(2022);
    }

    @Test
    @Order(3)
    public void testGetVehicleNotFound() {
        given()
                .when()
                .get("/api/vehicles/9999")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(4)
    public void testListVehicles() {
        Response response = given()
                .when()
                .get("/api/vehicles")
                .then()
                .statusCode(200)
                .extract()
                .response();

        Vehicle[] vehicles = response.as(Vehicle[].class);
        assertThat(vehicles).isNotEmpty();
        assertThat(vehicles[0].getId()).isEqualTo(createdId);
    }

    @Test
    @Order(5)
    public void testUpdateVehicle() {
        Vehicle updated = new Vehicle("Tesla", "Model X", 2023);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(updated)
                .when()
                .put("/api/vehicles/" + createdId)
                .then()
                .statusCode(200)
                .extract()
                .response();

        Vehicle vehicle = response.as(Vehicle.class);
        assertThat(vehicle).isNotNull();
        assertThat(vehicle.getId()).isEqualTo(createdId);
        assertThat(vehicle.getMake()).isEqualTo("Tesla");
        assertThat(vehicle.getModel()).isEqualTo("Model X");
        assertThat(vehicle.getConstruction_year()).isEqualTo(2023);
    }

    @Test
    @Order(6)
    public void testUpdateVehicleNotFound() {
        Vehicle updated = new Vehicle("Tesla", "Model X", 2023);

        given()
                .contentType(ContentType.JSON)
                .body(updated)
                .when()
                .put("/api/vehicles/9999")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(7)
    public void testCreateValidationFail() {
        // Blank make
        Vehicle invalid1 = new Vehicle("", "Model 3", 2021);
        given()
                .contentType(ContentType.JSON)
                .body(invalid1)
                .when()
                .post("/api/vehicles")
                .then()
                .statusCode(400);

        // Invalid year
        Vehicle invalid2 = new Vehicle("Tesla", "Model 3", 1800);
        given()
                .contentType(ContentType.JSON)
                .body(invalid2)
                .when()
                .post("/api/vehicles")
                .then()
                .statusCode(400);
    }

    @Test
    @Order(8)
    public void testDeleteVehicle() {
        given()
                .when()
                .delete("/api/vehicles/" + createdId)
                .then()
                .statusCode(204);

        // Subsequent get should return 404
        given()
                .when()
                .get("/api/vehicles/" + createdId)
                .then()
                .statusCode(404);
    }

    @Test
    @Order(9)
    public void testDeleteVehicleNotFound() {
        given()
                .when()
                .delete("/api/vehicles/9999")
                .then()
                .statusCode(404);
    }
}
