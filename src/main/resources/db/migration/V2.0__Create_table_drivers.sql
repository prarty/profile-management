CREATE TABLE `drivers`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT,
    `first_name`    varchar(255) NOT NULL,
    `last_name`     varchar(255) NOT NULL,
    `phone_number`  varchar(10)  NOT NULL,
    `email`         varchar(255) NOT NULL,
    `password`      varchar(255) NOT NULL,
    `driver_status` ENUM('REGISTERED', 'PENDING_VEHICLE_DETAILS', 'VEHICLE_DETAILS_COMPLETED', 'PENDING_ONBOARDING', 'ONBOARDING_COMPLETED', 'OFFLINE', 'ONLINE') DEFAULT 'REGISTERED',
    `vehicle_id`    bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_vehicle_id` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicles` (`id`),
    UNIQUE INDEX `uni_drivers_phone_num` (`phone_number` ASC) VISIBLE,
    UNIQUE INDEX `uni_drivers_email` (`email` ASC) VISIBLE
);