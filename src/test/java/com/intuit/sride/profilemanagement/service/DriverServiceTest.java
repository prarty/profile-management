package com.intuit.sride.profilemanagement.service;

import com.intuit.sride.profilemanagement.api.request.DriverProfileUpdateRequest;
import com.intuit.sride.profilemanagement.api.request.DriverRegistrationRequest;
import com.intuit.sride.profilemanagement.api.request.StatusUpdateRequest;
import com.intuit.sride.profilemanagement.api.request.VehicleRequest;
import com.intuit.sride.profilemanagement.exception.BadRequestException;
import com.intuit.sride.profilemanagement.exception.ResourceAlreadyExistsException;
import com.intuit.sride.profilemanagement.exception.UserNotFoundException;
import com.intuit.sride.profilemanagement.model.Driver;
import com.intuit.sride.profilemanagement.model.DriverStatus;
import com.intuit.sride.profilemanagement.model.Vehicle;
import com.intuit.sride.profilemanagement.repository.DriverRepository;
import com.intuit.sride.profilemanagement.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class DriverServiceTest {

    DriverService driverService;

    @Mock
    DriverRegistrationRequest createRequest;

    @Mock
    DriverProfileUpdateRequest updateRequest;

    @Mock
    StatusUpdateRequest statusUpdateRequest;

    @Mock
    ModelMapper mapper;

    @Mock
    DriverRepository driverRepository;
    @Mock
    VehicleRepository vehicleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Driver driver;

    @Mock
    private Vehicle mockedVehicle;
    @Mock
    private VehicleRequest vehicleRequest;

    @BeforeEach
    void setUp() {
        driverService = new DriverService(mapper, driverRepository, vehicleRepository, passwordEncoder);
    }


    @Test
    void shouldThrowExceptionWhenDriverAlreadyExists() {
        when(driverRepository.findByPhoneNumberOrEmail(any(), any())).thenReturn(Optional.of(driver));

        assertThrows(ResourceAlreadyExistsException.class, () -> driverService.register(createRequest));
    }

    @Test
    void shouldThrowExceptionWhenVehicleAlreadyExists() {
        when(driverRepository.findByPhoneNumberOrEmail(any(), any())).thenReturn(Optional.empty());
        when(mapper.map(eq(createRequest), eq(Driver.class))).thenReturn(driver);
        when(createRequest.getVehicle()).thenReturn(vehicleRequest);
        when(mapper.map(eq(vehicleRequest), eq(Vehicle.class))).thenReturn(mockedVehicle);
        when(vehicleRepository.findByRegistrationNumber(any())).thenReturn(Optional.of(mockedVehicle));

        assertThrows(ResourceAlreadyExistsException.class, () -> driverService.register(createRequest));
    }

    @Test
    void shouldRegisterDriverWithoutVehicleDetails() {
        when(driverRepository.findByPhoneNumberOrEmail(any(), any())).thenReturn(Optional.empty());
        when(mapper.map(eq(createRequest), eq(Driver.class))).thenReturn(driver);
        when(driverRepository.saveAndFlush(driver)).thenReturn(driver);
        when(driver.getId()).thenReturn(1L);

        Long driverID = driverService.register(createRequest);

        verify(driverRepository, times(1)).saveAndFlush(driver);
        verify(vehicleRepository, times(0)).saveAndFlush(mockedVehicle);
        assertEquals(1L, driverID);
    }

    @Test
    void shouldRegisterDriverWithVehicleDetails() {
        when(driverRepository.findByPhoneNumberOrEmail(any(), any())).thenReturn(Optional.empty());
        when(mapper.map(eq(createRequest), eq(Driver.class))).thenReturn(driver);
        when(createRequest.getVehicle()).thenReturn(vehicleRequest);
        when(mapper.map(eq(vehicleRequest), eq(Vehicle.class))).thenReturn(mockedVehicle);
        when(vehicleRepository.findByRegistrationNumber(any())).thenReturn(Optional.empty());
        when(driverRepository.saveAndFlush(driver)).thenReturn(driver);
        when(driver.getId()).thenReturn(1L);

        Long driverID = driverService.register(createRequest);

        verify(driverRepository, times(1)).saveAndFlush(driver);
        verify(vehicleRepository, times(1)).saveAndFlush(mockedVehicle);
        assertEquals(1L, driverID);
    }

    @Test
    void shouldThrowExceptionWhenDriverDoesNotExistsForUpdateRequest() {
        when(driverRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> driverService.update(1L, updateRequest));
    }

    @Test
    void shouldUpdateVehicleInfoWhenVehicleAlreadyExistsForUpdateRequest() {
        when(driverRepository.findById(1L)).thenReturn(Optional.of(driver));
        when(mapper.map(eq(vehicleRequest), eq(Vehicle.class))).thenReturn(mockedVehicle);
        when(vehicleRepository.findByRegistrationNumber(any())).thenReturn(Optional.of(mockedVehicle));
        when(driverRepository.saveAndFlush(driver)).thenReturn(driver);
        when(driver.getId()).thenReturn(1L);
        when(updateRequest.getVehicle()).thenReturn(vehicleRequest);

        driverService.update(1L, updateRequest);

        verify(vehicleRepository).saveAndFlush(mockedVehicle);
        verify(driverRepository).saveAndFlush(driver);
    }

    @Test
    void shouldUpdateVehicleInfoWhenVehicleDoesNotExistsForUpdateRequest() {
        when(driverRepository.findById(1L)).thenReturn(Optional.of(driver));
        when(mapper.map(eq(vehicleRequest), eq(Vehicle.class))).thenReturn(mockedVehicle);
        when(vehicleRepository.findByRegistrationNumber(any())).thenReturn(Optional.empty());
        when(driverRepository.saveAndFlush(driver)).thenReturn(driver);
        when(driver.getId()).thenReturn(1L);
        when(updateRequest.getVehicle()).thenReturn(vehicleRequest);

        driverService.update(1L, updateRequest);

        verify(vehicleRepository).saveAndFlush(mockedVehicle);
        verify(driverRepository).saveAndFlush(driver);
    }

    @Test
    void shouldThrowExceptionWhenDriverDoesNotExistsForStatusUpdateRequest() {
        when(driverRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> driverService.updateStatus(1L, statusUpdateRequest));
    }

    @Test
    void shouldThrowExceptionWhenStatusTransitionIsInvalidForStatusUpdateRequest() {
        when(driverRepository.findById(1L)).thenReturn(Optional.of(driver));
        when(driver.getDriverStatus()).thenReturn(DriverStatus.REGISTERED);
        when(statusUpdateRequest.getStatus()).thenReturn(DriverStatus.REGISTERED);

        assertThrows(BadRequestException.class, () -> driverService.updateStatus(1L, statusUpdateRequest));
    }

    @Test
    void shouldUpdateStatusSuccessfullyWhenStatusTransitionIsvalidForStatusUpdateRequest() {
        when(driverRepository.findById(1L)).thenReturn(Optional.of(driver));
        when(driver.getDriverStatus()).thenReturn(DriverStatus.REGISTERED);
        when(driver.getId()).thenReturn(1L);
        when(statusUpdateRequest.getStatus()).thenReturn(DriverStatus.PENDING_VEHICLE_DETAILS);
        when(driverRepository.saveAndFlush(driver)).thenReturn(driver);

        driverService.updateStatus(1L, statusUpdateRequest);

        verify(driverRepository).saveAndFlush(driver);
    }
}