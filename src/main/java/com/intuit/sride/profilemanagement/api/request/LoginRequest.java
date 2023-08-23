package com.intuit.sride.profilemanagement.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "Username cannot be null or blank")
    String username;

    @NotBlank(message = "Password cannot be null or blank")
    String password;
}
