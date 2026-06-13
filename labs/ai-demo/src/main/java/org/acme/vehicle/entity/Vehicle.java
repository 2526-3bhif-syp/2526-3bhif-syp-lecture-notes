package org.acme.vehicle.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Entity
@Table(name = "AI_VEHICLE")
@Schema(description = "A vehicle record")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_seq")
    @SequenceGenerator(name = "vehicle_seq", sequenceName = "vehicle_id_seq", allocationSize = 1)
    @Column(name = "V_ID")
    @Schema(readOnly = true, example = "1", description = "Server-generated primary key")
    private Long id;

    @NotBlank(message = "Make must not be blank")
    @Column(name = "V_MAKE", nullable = false)
    @Schema(example = "Volkswagen", description = "Manufacturer of the vehicle", required = true)
    private String make;

    @NotBlank(message = "Model must not be blank")
    @Column(name = "V_MODEL", nullable = false)
    @Schema(example = "Golf VII", description = "Model designation", required = true)
    private String model;

    @NotNull(message = "Construction year must not be null")
    @Min(value = 1886, message = "Construction year must be 1886 or later")
    @Column(name = "V_CONSTRUCTION_YEAR", nullable = false)
    @Schema(example = "2015", description = "Year the vehicle was manufactured (>= 1886)", required = true)
    private Integer construction_year;

    public Vehicle() {
    }

    public Vehicle(String make, String model, Integer construction_year) {
        this.make = make;
        this.model = model;
        this.construction_year = construction_year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getConstruction_year() {
        return construction_year;
    }

    public void setConstruction_year(Integer construction_year) {
        this.construction_year = construction_year;
    }
}
