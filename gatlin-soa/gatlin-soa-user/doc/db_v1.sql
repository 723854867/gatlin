/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;

USE `gatlin_user`;

/* Create table in target */
CREATE TABLE `user_device`(
	`id` varchar(200) COLLATE utf8_general_ci NOT NULL  , 
	`uid` bigint(20) unsigned NOT NULL  , 
	`type` tinyint(1) unsigned NOT NULL  , 
	`token` varchar(500) COLLATE utf8_general_ci NOT NULL  , 
	`created` int(10) unsigned NOT NULL  , 
	PRIMARY KEY (`id`) , 
	UNIQUE KEY `type、uid`(`uid`,`type`) , 
	UNIQUE KEY `token`(`token`) 
) ENGINE=InnoDB DEFAULT CHARSET='utf8' COLLATE='utf8_general_ci';


/* Create table in target */
CREATE TABLE `user_info`(
	`id` bigint(20) unsigned NOT NULL  , 
	`pwd` varchar(50) COLLATE utf8_general_ci NOT NULL  , 
	`salt` varchar(10) COLLATE utf8_general_ci NOT NULL  , 
	`avatar` varchar(200) COLLATE utf8_general_ci NOT NULL  , 
	`nickname` varchar(20) COLLATE utf8_general_ci NOT NULL  , 
	`created` int(10) unsigned NOT NULL  , 
	`updated` int(10) NULL  , 
	PRIMARY KEY (`id`) 
) ENGINE=InnoDB DEFAULT CHARSET='utf8' COLLATE='utf8_general_ci';


/* Create table in target */
CREATE TABLE `user_invitation`(
	`id` varchar(50) COLLATE utf8_general_ci NOT NULL  , 
	`invitor` bigint(20) unsigned NOT NULL  , 
	`invitee` bigint(20) unsigned NOT NULL  , 
	`created` int(10) unsigned NOT NULL  , 
	PRIMARY KEY (`id`) , 
	UNIQUE KEY `invitor、invitee`(`invitor`,`invitee`) 
) ENGINE=InnoDB DEFAULT CHARSET='utf8' COLLATE='utf8_general_ci';


/* Create table in target */
CREATE TABLE `username`(
	`id` bigint(20) unsigned NOT NULL  auto_increment , 
	`uid` bigint(20) unsigned NOT NULL  , 
	`type` tinyint(1) unsigned NOT NULL  , 
	`username` varchar(20) COLLATE utf8_general_ci NOT NULL  , 
	`created` int(10) unsigned NOT NULL  , 
	PRIMARY KEY (`id`) , 
	UNIQUE KEY `username、type`(`type`,`username`) 
) ENGINE=InnoDB DEFAULT CHARSET='utf8' COLLATE='utf8_general_ci';

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;