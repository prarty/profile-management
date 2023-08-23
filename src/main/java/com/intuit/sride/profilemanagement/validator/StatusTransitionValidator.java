package com.intuit.sride.profilemanagement.validator;

import com.intuit.sride.profilemanagement.model.DriverStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.intuit.sride.profilemanagement.model.DriverStatus.*;

@Component
public class StatusTransitionValidator {

    private static final Map<DriverStatus, DriverStatus[]> VALID_TRANSITIONS = new HashMap<>();
    static {
        VALID_TRANSITIONS.put(REGISTERED, new DriverStatus[] { PENDING_VEHICLE_DETAILS, VEHICLE_DETAILS_COMPLETED });
        VALID_TRANSITIONS.put(PENDING_VEHICLE_DETAILS, new DriverStatus[] { VEHICLE_DETAILS_COMPLETED });
        VALID_TRANSITIONS.put(VEHICLE_DETAILS_COMPLETED, new DriverStatus[] { PENDING_ONBOARDING });
        VALID_TRANSITIONS.put(PENDING_ONBOARDING, new DriverStatus[] { ONBOARDING_COMPLETED });
        VALID_TRANSITIONS.put(ONBOARDING_COMPLETED, new DriverStatus[] { OFFLINE, ONLINE });
        VALID_TRANSITIONS.put(OFFLINE, new DriverStatus[] { ONLINE });
        VALID_TRANSITIONS.put(ONLINE, new DriverStatus[] { OFFLINE });
    }

    public static boolean isStatusTransitionValid(DriverStatus fromStatus, DriverStatus toStatus) {
        DriverStatus[] validNextStatuses = VALID_TRANSITIONS.get(fromStatus);
        return validNextStatuses != null && containsStatus(validNextStatuses, toStatus);
    }

    private static boolean containsStatus(DriverStatus[] statuses, DriverStatus status) {
        for (DriverStatus validStatus : statuses) {
            if (validStatus == status) {
                return true;
            }
        }
        return false;
    }
}