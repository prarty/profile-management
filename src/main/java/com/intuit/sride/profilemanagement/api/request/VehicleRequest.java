package com.intuit.sride.profilemanagement.api.request;

import com.intuit.sride.profilemanagement.model.VehicleType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleRequest {
    @NotBlank(message = "Vehicle type can not be null or blank")
    VehicleType vehicleType;

    @NotBlank(message = "passenger capacity can not be null or blank")
    String passengerCapacity;

    @NotBlank(message = "Make can not be null or blank")
    String make;

    @NotBlank(message = "Model can not be null or blank")
    String model;

    @NotBlank(message = "Registration number can not be null or blank")
    String registrationNumber;
}