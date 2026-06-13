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
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/api/vehicles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Vehicles", description = "CRUD operations on vehicles")
public class VehicleResource {

    @Inject
    VehicleRepository repository;

    @GET
    @Operation(summary = "List all vehicles", description = "Returns every vehicle persisted in the database.")
    @APIResponse(responseCode = "200", description = "Array of vehicles (possibly empty)")
    public List<Vehicle> list() {
        return repository.listAll();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get a vehicle by ID", description = "Returns a single vehicle identified by its primary key.")
    @APIResponse(responseCode = "200", description = "Vehicle found")
    @APIResponse(responseCode = "404", description = "No vehicle exists for the given ID")
    public Response get(@PathParam("id") Long id) {
        return repository.findByIdOptional(id)
                .map(vehicle -> Response.ok(vehicle).build())
                .orElseGet(() -> Response.status(Status.NOT_FOUND).build());
    }

    @POST
    @Transactional
    @Operation(summary = "Create a vehicle", description = "Persists a new vehicle and returns the created resource with a Location header.")
    @APIResponse(responseCode = "201", description = "Vehicle created")
    @APIResponse(responseCode = "400", description = "Invalid payload (blank make/model or constructionYear < 1886)")
    public Response create(@Valid Vehicle vehicle) {
        repository.persist(vehicle);
        return Response.created(URI.create("/api/vehicles/" + vehicle.getId()))
                .entity(vehicle)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Update an existing vehicle", description = "Replaces make, model, and construction year of the vehicle with the given ID.")
    @APIResponse(responseCode = "200", description = "Vehicle updated")
    @APIResponse(responseCode = "400", description = "Invalid payload")
    @APIResponse(responseCode = "404", description = "No vehicle exists for the given ID")
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
    @Operation(summary = "Delete a vehicle", description = "Removes the vehicle with the given ID.")
    @APIResponse(responseCode = "204", description = "Vehicle deleted")
    @APIResponse(responseCode = "404", description = "No vehicle exists for the given ID")
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = repository.deleteById(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
}
