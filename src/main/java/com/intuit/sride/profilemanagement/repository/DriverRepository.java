package com.intuit.sride.profilemanagement.repository;

import com.intuit.sride.profilemanagement.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    Optional<Driver> findByPhoneNumberOrEmail(String phoneNumber, String email);

    Optional<Driver> findByEmail(String username);
}
