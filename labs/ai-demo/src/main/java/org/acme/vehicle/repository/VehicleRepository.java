package org.acme.vehicle.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.vehicle.entity.Vehicle;

@ApplicationScoped
public class VehicleRepository implements PanacheRepository<Vehicle> {
}
