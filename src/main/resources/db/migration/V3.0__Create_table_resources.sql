CREATE TABLE `resources`
(
    `id`            INT(11)      NOT NULL AUTO_INCREMENT,
    `resource_id`   VARCHAR(255) NOT NULL,
    `resource_desc` VARCHAR(255) NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY uni_resources_id (`resource_id`)
);