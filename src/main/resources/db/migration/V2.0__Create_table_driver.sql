CREATE TABLE `driver`
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
    CONSTRAINT `fk_vehicle_id` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicle` (`id`),
    ADD UNIQUE INDEX `phone_number_UNIQUE` (`phone_number` ASC) VISIBLE,
    ADD UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE;
);