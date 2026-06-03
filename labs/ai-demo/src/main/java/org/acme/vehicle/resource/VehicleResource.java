package org.acme.vehicle.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.net.URI;
import java.util.List;
import org.acme.vehicle.entity.Vehicle;
import org.acme.vehicle.repository.VehicleRepository;

@Path("/api/vehicles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VehicleResource {

    @Inject
    VehicleRepository repository;

    @GET
    public List<Vehicle> list() {
        return repository.listAll();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Long id) {
        return repository.findByIdOptional(id)
                .map(vehicle -> Response.ok(vehicle).build())
                .orElseGet(() -> Response.status(Status.NOT_FOUND).build());
    }

    @POST
    @Transactional
    public Response create(@Valid Vehicle vehicle) {
        repository.persist(vehicle);
        return Response.created(URI.create("/api/vehicles/" + vehicle.getId()))
                .entity(vehicle)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, @Valid Vehicle updatedVehicle) {
        return repository.findByIdOptional(id)
                .map(vehicle -> {
                    vehicle.setMake(updatedVehicle.getMake());
                    vehicle.setModel(updatedVehicle.getModel());
                    vehicle.setConstruction_year(updatedVehicle.getConstruction_year());
                    return Response.ok(vehicle).build();
                })
                .orElseGet(() -> Response.status(Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = repository.deleteById(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
}
