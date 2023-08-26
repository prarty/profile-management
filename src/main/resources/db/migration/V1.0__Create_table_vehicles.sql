CREATE TABLE `vehicles`
(
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `registration_number` varchar(50) NOT NULL,
    `vehicle_type`        ENUM('TWO_WHEELER', 'THREE_WHEELER', 'FOUR_WHEELER')  NOT NULL,
    `passenger_capacity`  INTEGER NOT NULL,
    `make`               varchar(255) NOT NULL,
    `model`              varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uni_vehicles_regn_number` (`registration_number` ASC) VISIBLE
);