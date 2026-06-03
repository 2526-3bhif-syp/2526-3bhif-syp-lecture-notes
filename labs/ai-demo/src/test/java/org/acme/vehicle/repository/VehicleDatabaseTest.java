package org.acme.vehicle.repository;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import javax.sql.DataSource;
import org.acme.vehicle.entity.Vehicle;
import org.assertj.db.type.AssertDbConnection;
import org.assertj.db.type.AssertDbConnectionFactory;
import org.assertj.db.type.Table;
import static org.assertj.db.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class VehicleDatabaseTest {

    @Inject
    DataSource dataSource;

    @Inject
    VehicleRepository repository;

    @Test
    @Transactional
    public void testDatabaseState() {
        AssertDbConnection connection = AssertDbConnectionFactory.of(dataSource).create();

        // Assert initial state of AI_VEHICLE table
        Table table = connection.table("AI_VEHICLE").build();
        int initialRows = table.getRowsList().size();

        // Create a new vehicle
        Vehicle vehicle = new Vehicle("Audi", "A4", 2021);
        repository.persistAndFlush(vehicle);

        // Reload the table state
        Table updatedTable = connection.table("AI_VEHICLE").build();

        // Assert using assertj-db assertThat
        assertThat(updatedTable)
                .hasNumberOfRows(initialRows + 1)
                .row(initialRows)
                .value("V_MAKE").isEqualTo("Audi")
                .value("V_MODEL").isEqualTo("A4")
                .value("V_CONSTRUCTION_YEAR").isEqualTo(2021);

        // Cleanup
        repository.delete(vehicle);
    }
}
