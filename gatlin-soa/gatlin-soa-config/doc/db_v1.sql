/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;

USE `gatlin_config`;

/* Create table in target */
CREATE TABLE `cfg_api`(
	`path` varchar(50) COLLATE utf8_general_ci NOT NULL  , 
	`desc` varchar(200) COLLATE utf8_general_ci NOT NULL  , 
	`login` tinyint(1) unsigned NOT NULL  , 
	`device_mod` tinyint(3) unsigned NOT NULL  , 
	`serial` tinyint(1) unsigned NOT NULL  , 
	`lock_timeout` int(10) unsigned NOT NULL  , 
	`storage_type` varchar(20) COLLATE utf8_general_ci NOT NULL  , 
	`created` int(10) unsigned NOT NULL  , 
	`updated` int(10) unsigned NOT NULL  , 
	PRIMARY KEY (`path`) 
) ENGINE=InnoDB DEFAULT CHARSET='utf8' COLLATE='utf8_general_ci';


/* Create table in target */
CREATE TABLE `cfg_global`(
	`key` varchar(20) COLLATE utf8_general_ci NOT NULL  , 
	`value` varchar(500) COLLATE utf8_general_ci NOT NULL  , 
	`desc` varchar(200) COLLATE utf8_general_ci NOT NULL  , 
	`type` tinyint(3) unsigned NOT NULL  , 
	`visible` tinyint(1) unsigned NOT NULL  COMMENT '是否前端可见' , 
	`created` int(10) unsigned NOT NULL  , 
	`updated` int(10) unsigned NOT NULL  , 
	PRIMARY KEY (`key`) 
) ENGINE=InnoDB DEFAULT CHARSET='utf8' COLLATE='utf8_general_ci';