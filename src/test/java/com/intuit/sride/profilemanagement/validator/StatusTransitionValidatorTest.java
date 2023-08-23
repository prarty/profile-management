package com.intuit.sride.profilemanagement.validator;

import com.intuit.sride.profilemanagement.model.DriverStatus;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StatusTransitionValidatorTest {

    @ParameterizedTest(name = "invalidStatusTransition(from=\"{0}\", to=\"{1}\")")
    @CsvSource(value = {
            "REGISTERED, REGISTERED",
            "REGISTERED, PENDING_ONBOARDING",
            "REGISTERED, ONBOARDING_COMPLETED",
            "REGISTERED, OFFLINE",
            "REGISTERED, ONLINE",
            "PENDING_VEHICLE_DETAILS, PENDING_VEHICLE_DETAILS",
            "PENDING_VEHICLE_DETAILS, REGISTERED",
            "PENDING_VEHICLE_DETAILS, PENDING_ONBOARDING",
            "PENDING_VEHICLE_DETAILS, ONBOARDING_COMPLETED",
            "PENDING_VEHICLE_DETAILS, OFFLINE",
            "PENDING_VEHICLE_DETAILS, ONLINE",
            "VEHICLE_DETAILS_COMPLETED, PENDING_VEHICLE_DETAILS",
            "VEHICLE_DETAILS_COMPLETED, VEHICLE_DETAILS_COMPLETED",
            "VEHICLE_DETAILS_COMPLETED, REGISTERED",
            "VEHICLE_DETAILS_COMPLETED, ONBOARDING_COMPLETED",
            "VEHICLE_DETAILS_COMPLETED, OFFLINE",
            "VEHICLE_DETAILS_COMPLETED, ONLINE",
            "PENDING_ONBOARDING, PENDING_ONBOARDING",
            "PENDING_ONBOARDING, REGISTERED",
            "PENDING_ONBOARDING, PENDING_VEHICLE_DETAILS",
            "PENDING_ONBOARDING, VEHICLE_DETAILS_COMPLETED",
            "PENDING_ONBOARDING, OFFLINE",
            "PENDING_ONBOARDING, ONLINE",
            "ONBOARDING_COMPLETED, ONBOARDING_COMPLETED",
            "ONBOARDING_COMPLETED, REGISTERED",
            "ONBOARDING_COMPLETED, PENDING_VEHICLE_DETAILS",
            "ONBOARDING_COMPLETED, VEHICLE_DETAILS_COMPLETED",
            "OFFLINE, OFFLINE",
            "OFFLINE, REGISTERED",
            "OFFLINE, PENDING_VEHICLE_DETAILS",
            "OFFLINE, VEHICLE_DETAILS_COMPLETED",
            "OFFLINE, PENDING_ONBOARDING",
            "OFFLINE, ONBOARDING_COMPLETED",
            "ONLINE, ONLINE",
            "ONLINE, REGISTERED",
            "ONLINE, PENDING_VEHICLE_DETAILS",
            "ONLINE, VEHICLE_DETAILS_COMPLETED",
            "ONLINE, PENDING_ONBOARDING",
            "ONLINE, ONBOARDING_COMPLETED"
    })
    void invalidStatusTransition(DriverStatus from, DriverStatus to) {
        boolean isValid = StatusTransitionValidator.isStatusTransitionValid(from, to);

        assertFalse(isValid);
    }

    @ParameterizedTest(name = "validStatusTransition(from=\"{0}\", to=\"{1}\")")
    @CsvSource(value = {
            "REGISTERED, PENDING_VEHICLE_DETAILS",
            "REGISTERED, VEHICLE_DETAILS_COMPLETED",
            "PENDING_VEHICLE_DETAILS, VEHICLE_DETAILS_COMPLETED",
            "PENDING_ONBOARDING, ONBOARDING_COMPLETED",
            "ONBOARDING_COMPLETED, OFFLINE",
            "ONBOARDING_COMPLETED, ONLINE",
            "OFFLINE, ONLINE",
            "ONLINE, OFFLINE",
    })
    void validStatusTransition(DriverStatus from, DriverStatus to) {
        boolean isValid = StatusTransitionValidator.isStatusTransitionValid(from, to);

        assertTrue(isValid);
    }
}