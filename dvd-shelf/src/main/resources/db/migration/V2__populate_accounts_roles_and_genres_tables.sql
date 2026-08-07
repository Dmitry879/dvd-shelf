INSERT INTO `dvd_shelf`.`roles` (`name`) VALUES ('Admin');
INSERT INTO `dvd_shelf`.`roles` (`name`) VALUES ('Employee');
INSERT INTO `dvd_shelf`.`roles` (`name`) VALUES ('Customer');

INSERT INTO `dvd_shelf`.`accounts` (`username`, `password`, `email`, `role_id`)
VALUES ('admin', '$2a$10$GTR25G6DbK63q4NxdQ/YkO3Upvnau1Z.qxfDYC38HOWGLt0xLOEYO', 'admin@dvdshelf.com', '1');

INSERT INTO `dvd_shelf`.`genres` (`name`) VALUES ('Action');
INSERT INTO `dvd_shelf`.`genres` (`name`) VALUES ('Comedy');
INSERT INTO `dvd_shelf`.`genres` (`name`) VALUES ('Science Fiction');
INSERT INTO `dvd_shelf`.`genres` (`name`) VALUES ('Thriller');
INSERT INTO `dvd_shelf`.`genres` (`name`) VALUES ('Horror');
INSERT INTO `dvd_shelf`.`genres` (`name`) VALUES ('Drama');
INSERT INTO `dvd_shelf`.`genres` (`name`) VALUES ('Fantasy');
INSERT INTO `dvd_shelf`.`genres` (`name`) VALUES ('Adventure');