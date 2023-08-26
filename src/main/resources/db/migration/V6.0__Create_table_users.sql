CREATE TABLE `users`
(
    `id`            bigint(20) NOT NULL ,
    `email`         varchar(255) NOT NULL,
    `creation_date`      DATE NOT NULL,
    `termination_date`      DATE NULL,
    `enabled`            BOOLEAN  DEFAULT NULL,
    `role_id`            bigint(20)  NOT NULL,
    PRIMARY KEY (`id`),
--     CONSTRAINT `fk_id` FOREIGN KEY (`id`) REFERENCES `driver` (`id`),
    CONSTRAINT `fk_role_id` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
--     CONSTRAINT `fk_email` FOREIGN KEY (`email`) REFERENCES `drivers` (`email`)
);