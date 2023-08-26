CREATE TABLE `roles`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT,
    `name`    varchar(255) NOT NULL,
    `description`     varchar(255) NULL,
    `application_name`  varchar(255)  NOT NULL,
    `start_date`         DATE NOT NULL,
    `end_date`      DATE DEFAULT NULL,
    `enabled`    BOOLEAN      NOT NULL,
    `permission_id`    INT(11)  DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_permission_id` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`id`)
);