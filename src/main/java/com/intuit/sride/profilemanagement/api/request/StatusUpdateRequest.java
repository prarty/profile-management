package com.intuit.sride.profilemanagement.api.request;

import com.intuit.sride.profilemanagement.model.DriverStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StatusUpdateRequest {
    @NotNull(message = "Status cannot be null")
    DriverStatus status;
}
