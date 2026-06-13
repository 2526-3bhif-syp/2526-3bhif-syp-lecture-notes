package org.acme.vehicle.openapi;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

@QuarkusTest
class OpenApiSmokeTest {

    @Test
    void openApiDocumentExposesVehicleEndpoints() {
        given()
                .when().get("/q/openapi")
                .then()
                .statusCode(200)
                .body(containsString("/api/vehicles"));
    }

    @Test
    void swaggerUiIsReachable() {
        given()
                .when().get("/q/swagger-ui")
                .then()
                .statusCode(200);
    }
}
