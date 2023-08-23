package com.intuit.sride.profilemanagement.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DriverStatus {
    REGISTERED("REGISTERED"),
    PENDING_VEHICLE_DETAILS("PENDING_VEHICLE_DETAILS"),
    VEHICLE_DETAILS_COMPLETED("VEHICLE_DETAILS_COMPLETED"),
    PENDING_ONBOARDING("PENDING_ONBOARDING"),
    ONBOARDING_COMPLETED("ONBOARDING_COMPLETED"),
    OFFLINE("OFFLINE"),
    ONLINE("ONLINE");

    @JsonValue
    private final String value;

    @Override
    public String toString() {
        return value;
    }
}