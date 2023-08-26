CREATE TABLE `permissions`
(
    `id`              INT(11)      NOT NULL AUTO_INCREMENT,
    `permission_name` VARCHAR(255) NOT NULL,
    `permission_desc` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY uni_permissions_perm_name (`permission_name`)
);
