package com.intuit.sride.profilemanagement.api.request;

import com.intuit.sride.profilemanagement.model.Vehicle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DriverProfileUpdateRequest {

    VehicleRequest vehicle;

    AccountDetailsRequest accountDetails;
}
