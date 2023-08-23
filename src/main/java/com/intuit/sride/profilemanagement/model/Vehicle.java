package com.intuit.sride.profilemanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "vehicle")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    @NotBlank(message = "Registration number can not be null or blank")
    String registrationNumber;

    @Column(columnDefinition = "ENUM('TWO_WHEELER', 'THREE_WHEELER', 'FOUR_WHEELER')")
    @Enumerated(EnumType.STRING)
    VehicleType vehicleType;

    @Column
    @NotNull(message = "passenger capacity can not be null or blank")
    Integer passengerCapacity;

    @Column
    @NotBlank(message = "Make can not be null or blank")
    String make;

    @Column
    @NotBlank(message = "Model can not be null or blank")
    String model;
}
