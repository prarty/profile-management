CREATE TABLE IF NOT EXISTS `permission_resources`
(
    `permission_id` INT(11) NOT NULL,
    `resource_id`   INT(11) NOT NULL,
    PRIMARY KEY (`permission_id`, `resource_id`),
    FOREIGN key (`permission_id`) REFERENCES permissions (`id`),
    FOREIGN key (`resource_id`) REFERENCES resources (`id`)
) ;
