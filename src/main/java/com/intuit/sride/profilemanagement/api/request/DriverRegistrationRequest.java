package com.intuit.sride.profilemanagement.api.request;

import com.intuit.sride.profilemanagement.model.Vehicle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DriverRegistrationRequest {
    @NotBlank(message = "First Name can not be null or blank")
    String firstName;

    @NotBlank(message = "Last Name can not be null or blank")
    String lastName;
    @NotBlank(message = "Phone number can not be null or blank")
    @Size(min = 10, max = 10)
    String phoneNumber;

    @NotBlank(message = "Email can not be null or blank")
    String email;

    @NotBlank(message = "Password can not be null or blank")
    String password;

    VehicleRequest vehicle;

    AccountDetailsRequest accountDetailsRequest;
}
