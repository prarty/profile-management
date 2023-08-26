package com.intuit.sride.profilemanagement.controller;

import com.intuit.sride.profilemanagement.api.request.DriverProfileUpdateRequest;
import com.intuit.sride.profilemanagement.api.request.DriverRegistrationRequest;
import com.intuit.sride.profilemanagement.api.request.LoginRequest;
import com.intuit.sride.profilemanagement.api.request.StatusUpdateRequest;
import com.intuit.sride.profilemanagement.exception.UserNotFoundException;
import com.intuit.sride.profilemanagement.service.AuthService;
import com.intuit.sride.profilemanagement.service.DriverService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/driver/")
public class DriverAuthController {

    final DriverService driverService;
    final AuthService authService;
    final AuthenticationManager authenticationManager;

    public DriverAuthController(DriverService driverService, AuthService authService, AuthenticationManager authenticationManager) {
        this.driverService = driverService;
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(path = "register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody DriverRegistrationRequest request) {
        Long id = driverService.register(request);
        if (id == null) {
          return new ResponseEntity<>("Can not register the driver", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Driver registered successfully with id " + id, HttpStatus.CREATED);
    }

    @PostMapping(path = "login")
    public String login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authenticationResponse = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        Long driverId = driverService.getDriverId(loginRequest.getUsername());
        if (authenticationResponse.isAuthenticated()) {
            return authService.generateToken(loginRequest.getUsername(), driverId);
        } else {
            throw new UserNotFoundException("User does not exist");
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<String> update(@Valid @RequestBody DriverProfileUpdateRequest request, @PathVariable("id") Long id) {
        Long driverId = driverService.update(id, request);
        return new ResponseEntity<>("Successfully updated driver with id " + driverId, HttpStatus.NO_CONTENT);
    }

    @PatchMapping("{id}/update-status")
    public ResponseEntity<String> updateStatus(@Valid @RequestBody StatusUpdateRequest request, @PathVariable("id") Long id) {
        Long driverId = driverService.updateStatus(id, request);
        return new ResponseEntity<>("Status Updated Successfully for driver id " + driverId, HttpStatus.NO_CONTENT);
    }
}
