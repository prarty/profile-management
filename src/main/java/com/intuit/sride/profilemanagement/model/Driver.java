package com.intuit.sride.profilemanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Driver")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First Name can not be null or blank")
    @Column(name = "first_name")
    String firstName;

    @NotBlank(message = "Last Name can not be null or blank")
    @Column(name = "last_name")
    String lastName;

    @NotBlank(message = "Phone Number can not be null or blank")
    @Size(min = 10, max = 10)
    @Column
    String phoneNumber;

    @NotBlank(message = "Email can not be null or blank")
    @Column
    String email;

    @NotBlank(message = "Password can not be null or blank")
    @Column
    String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('REGISTERED', 'PENDING_VEHICLE_DETAILS', 'VEHICLE_DETAILS_COMPLETED', 'PENDING_ONBOARDING', 'ONBOARDING_COMPLETED', 'OFFLINE', 'ONLINE')")
    private DriverStatus driverStatus = DriverStatus.REGISTERED;

    @JoinColumn(name = "vehicle_id")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Vehicle vehicle;
}
