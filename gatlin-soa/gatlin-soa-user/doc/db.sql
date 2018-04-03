/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;

USE `mysql`;

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

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;