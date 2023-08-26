package com.intuit.sride.profilemanagement.service;

import com.intuit.sride.profilemanagement.api.request.DriverProfileUpdateRequest;
import com.intuit.sride.profilemanagement.api.request.DriverRegistrationRequest;
import com.intuit.sride.profilemanagement.api.request.StatusUpdateRequest;
import com.intuit.sride.profilemanagement.api.request.VehicleRequest;
import com.intuit.sride.profilemanagement.exception.BadRequestException;
import com.intuit.sride.profilemanagement.exception.ResourceAlreadyExistsException;
import com.intuit.sride.profilemanagement.exception.UserNotFoundException;
import com.intuit.sride.profilemanagement.model.*;
import com.intuit.sride.profilemanagement.repository.DriverRepository;
import com.intuit.sride.profilemanagement.repository.UserRepository;
import com.intuit.sride.profilemanagement.repository.VehicleRepository;
import com.intuit.sride.profilemanagement.validator.StatusTransitionValidator;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Log4j2
@Service
@Transactional
public class DriverService {

    private final ModelMapper modelMapper;
    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public DriverService(ModelMapper modelMapper, DriverRepository driverRepository, VehicleRepository vehicleRepository, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.driverRepository = driverRepository;
        this.vehicleRepository = vehicleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public Long register(DriverRegistrationRequest request) {
        log.info("Registering driver");

        if (isDriverAlreadyRegistered(request)) {
            log.error("Driver already exists");
            throw new ResourceAlreadyExistsException("Driver already exists, Please Login");
        }

        Driver currentDriver = modelMapper.map(request, Driver.class);

        updateVehicleDetails(request.getVehicle(), currentDriver, false);
        currentDriver.setPassword(passwordEncoder.encode(request.getPassword()));

        Driver driver = driverRepository.saveAndFlush(currentDriver);
        Long driverID = driver.getId();
        User user = createDriverUser(driver);
        userRepository.save(user);
        log.info("Registered driver with id: {} successfully", driverID);
        return driverID;
    }

    private User createDriverUser(Driver driver) {
        Resource driverResources = Resource.builder()
                .resourceId("/driver/**")
                .description("driver resources")
                .build();

        Permission driverPermission = Permission.builder()
                .resources(Set.of(driverResources))
                .description("Driver Permission")
                .name("driver basic permission")
                .build();

        Role driverRole = Role.builder()
                .name("Driver")
                .enabled(Boolean.TRUE)
                .description("Role for driver")
                .applicationName("Driver-Svc")
                .permissions(driverPermission)
                .startDate(LocalDate.now())
                .build();

        return User.builder()
                .id(driver.getId())
                .email(driver.getEmail())
                .creationDate(LocalDate.now())
                .role(driverRole)
                .terminationDate(null)
                .enabled(true)
                .build();
    }

    public Long update(Long id, DriverProfileUpdateRequest request) {
        log.info("Updating driver details with id: {}", id);

        Optional<Driver> driver = driverRepository.findById(id);
        validateExistingDriver(driver);

        Driver currentDriver = driver.get();
        updateVehicleDetails(request.getVehicle(), currentDriver, true);
        Driver savedDriver = driverRepository.saveAndFlush(currentDriver);
        log.info("Updated driver details with id: {} successfully", savedDriver.getId());
        return savedDriver.getId();
    }

    public Long updateStatus(Long id, StatusUpdateRequest request) {
        log.info("Updating driver status with id: {} and status: {}", id, request.getStatus());

        Optional<Driver> savedDriver = driverRepository.findById(id);
        validateExistingDriver(savedDriver);

        Driver currentDriver = savedDriver.get();
        boolean isTransitionValid = StatusTransitionValidator.isStatusTransitionValid(currentDriver.getDriverStatus(), request.getStatus());
        if (!isTransitionValid) {
            log.error("Invalid status transition for driver: {} from status: {} to status: {}", id, currentDriver.getDriverStatus(), request.getStatus());
            throw new BadRequestException("Invalid status transition");
        }
        currentDriver.setDriverStatus(request.getStatus());
        Driver driver = driverRepository.saveAndFlush(currentDriver);
        log.info("Updated driver status with id: {} and status: {} successfully", id, request.getStatus());
        return driver.getId();
    }

    private void updateVehicleDetails(VehicleRequest vehicleRequest, Driver currentDriver, boolean isUpdate) {
        log.info("Updating vehicle details for driver: {}", currentDriver.getId());

        if (vehicleRequest != null) {
            Vehicle vehicle = modelMapper.map(vehicleRequest, Vehicle.class);
            if (isVehicleAlreadyRegistered(vehicle)) {
                if (!isUpdate) {
                    log.error("Vehicle already exists with id: {}", vehicle.getId());
                    throw new ResourceAlreadyExistsException("Vehicle already present");
                } else {
                    currentDriver.setVehicle(null);
                }
            }
            Vehicle savedVehicle = vehicleRepository.saveAndFlush(vehicle);
            currentDriver.setVehicle(savedVehicle);
            StatusTransitionValidator.isStatusTransitionValid(currentDriver.getDriverStatus(), DriverStatus.VEHICLE_DETAILS_COMPLETED);
            currentDriver.setDriverStatus(DriverStatus.VEHICLE_DETAILS_COMPLETED);
        } else {
            StatusTransitionValidator.isStatusTransitionValid(currentDriver.getDriverStatus(), DriverStatus.PENDING_VEHICLE_DETAILS);
            currentDriver.setDriverStatus(DriverStatus.PENDING_VEHICLE_DETAILS);
        }
        log.info("Updated vehicle details successfully");
    }

    private boolean isDriverAlreadyRegistered(DriverRegistrationRequest request) {
        Optional<Driver> existingDriver = driverRepository.findByPhoneNumberOrEmail(request.getPhoneNumber(), request.getEmail());
        return existingDriver.isPresent();
    }

    private boolean isVehicleAlreadyRegistered(Vehicle vehicle) {
        Optional<Vehicle> savedVehicle = vehicleRepository.findByRegistrationNumber(vehicle.getRegistrationNumber());
        return savedVehicle.isPresent();
    }

    private static void validateExistingDriver(Optional<Driver> currentDriver) {
        if (currentDriver.isEmpty()) {
            log.error("Driver does not exists");
            throw new UserNotFoundException("Driver does not exists");
        }
    }

    public Long getDriverId(String username) {
        Driver driver = driverRepository.findByEmail(username).orElseThrow();
        return driver.getId();
    }
}
