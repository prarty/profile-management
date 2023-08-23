package com.intuit.sride.profilemanagement.controller;

import com.intuit.sride.profilemanagement.api.request.DriverProfileUpdateRequest;
import com.intuit.sride.profilemanagement.api.request.DriverRegistrationRequest;
import com.intuit.sride.profilemanagement.api.request.StatusUpdateRequest;
import com.intuit.sride.profilemanagement.exception.ResourceAlreadyExistsException;
import com.intuit.sride.profilemanagement.service.AuthService;
import com.intuit.sride.profilemanagement.service.DriverService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;


@ExtendWith(MockitoExtension.class)
class DriverAuthControllerTest {

    @Mock
    DriverRegistrationRequest driverRegistrationRequest;

    @Mock
    DriverProfileUpdateRequest driverProfileUpdateRequest;

    @Mock
    StatusUpdateRequest statusUpdateRequest;

    @Mock
    DriverService driverService;

    @Mock
    AuthService authService;

    @Mock
    AuthenticationManager authenticationManager;

    DriverAuthController driverAuthController;

    @BeforeEach
    void setUp() {
        driverAuthController = new DriverAuthController(driverService, authService, authenticationManager);
    }

    @Test
    void shouldReturnBadRequestResponseWhenDriverRegistrationFailed() {
        when(driverService.register(driverRegistrationRequest)).thenReturn(null);

        ResponseEntity<String> response = driverAuthController.registerUser(driverRegistrationRequest);

        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertEquals("Can not register the driver", response.getBody());
    }

    @Test
    void shouldReturnCreatedResponseWhenDriverRegistrationIsSuccess() {
        when(driverService.register(driverRegistrationRequest)).thenReturn(1L);

        ResponseEntity<String> response = driverAuthController.registerUser(driverRegistrationRequest);

        assertEquals(CREATED, response.getStatusCode());
        assertEquals("Driver registered successfully with id 1", response.getBody());
    }

    @Test
    void shouldReturnNoContentResponseWhenDriverUpdateIsSuccess() {
        when(driverService.update(1L, driverProfileUpdateRequest)).thenReturn(1L);

        ResponseEntity<String> response = driverAuthController.update(driverProfileUpdateRequest, 1L);

        assertEquals(NO_CONTENT, response.getStatusCode());
        assertEquals("Successfully updated driver with id 1", response.getBody());
    }

    @Test
    void shouldReturnNoContentResponseWhenDriverStatusUpdateIsSuccess() {
        when(driverService.updateStatus(1L, statusUpdateRequest)).thenReturn(1L);

        ResponseEntity<String> response = driverAuthController.updateStatus(statusUpdateRequest, 1L);

        assertEquals(NO_CONTENT, response.getStatusCode());
        assertEquals("Status Updated Successfully for driver id 1", response.getBody());
    }
}