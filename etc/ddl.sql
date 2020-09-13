-- --------------------------------------------------------
-- 호스트:                          127.0.0.1
-- 서버 버전:                        8.0.21 - MySQL Community Server - GPL
-- 서버 OS:                        Linux
-- HeidiSQL 버전:                  11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- bootadmin 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `bootauthdemo` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `bootauthdemo`;

-- 테이블 bootauthdemo.authority 구조 내보내기
CREATE TABLE IF NOT EXISTS `authority` (
  `url` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  UNIQUE KEY `ix_auth_username` (`url`,`authority`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 bootauthdemo.authority:~5 rows (대략적) 내보내기
/*!40000 ALTER TABLE `authority` DISABLE KEYS */;
INSERT INTO `authority` (`url`, `authority`) VALUES
	('/menu/**', 'ROLE_ADMIN'),
	('/role/**', 'ROLE_ADMIN'),
	('/test/**', 'ROLE_USER'),
	('/user/**', 'ROLE_ADMIN'),
	('/work*/**', 'ROLE_WORK');
/*!40000 ALTER TABLE `authority` ENABLE KEYS */;

-- 테이블 bootauthdemo.menu 구조 내보내기
CREATE TABLE IF NOT EXISTS `menu` (
  `id` varchar(50) NOT NULL,
  `label` varchar(20) NOT NULL,
  `path` varchar(200) DEFAULT '0',
  `order` smallint DEFAULT '1',
  `level` smallint DEFAULT '1' COMMENT 'level',
  `url` varchar(200) DEFAULT NULL,
  `type` smallint DEFAULT '1' COMMENT 'type',
  `style` varchar(50) DEFAULT NULL COMMENT 'ui style',
  `disabled` smallint DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 bootauthdemo.menu:~8 rows (대략적) 내보내기
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` (`id`, `label`, `path`, `order`, `level`, `url`, `type`, `style`, `disabled`) VALUES
	('group1', '워크그룹1', '0', 10, 1, ' ', 0, NULL, 0),
	('group2', '워크그룹2', '0', 10, 1, ' ', 0, NULL, 0),
	('menu', '메뉴', '0,system', 2, 1, '/menu', 1, NULL, 0),
	('role', '권한', '0,system', 3, 3, '/role', 1, NULL, 0),
	('system', '시스템', '0', 1, 1, '', 1, NULL, 0),
	('test', 'Test1', '0,group1', 10, 2, '/work/test', 0, NULL, 0),
	('user', '사용자', '0,system', 4, 2, '/user', 1, NULL, 0),
	('work2', '일하자2', '0,group2', 10, 2, '/work/work2', 0, NULL, 0);
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;

-- 테이블 bootauthdemo.role 구조 내보내기
CREATE TABLE IF NOT EXISTS `role` (
  `id` varchar(50) NOT NULL,
  `name` varchar(30) NOT NULL COMMENT 'name',
  `disabled` smallint NOT NULL DEFAULT '0',
  `description` varchar(60) DEFAULT NULL COMMENT 'description',
  PRIMARY KEY (`id`),
  UNIQUE KEY `rolename` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='role';

-- 테이블 데이터 bootauthdemo.role:~4 rows (대략적) 내보내기
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` (`id`, `name`, `disabled`, `description`) VALUES
	('1', 'ROLE_ADMIN', 0, 'Admin'),
	('2', 'ROLE_USER', 0, 'User'),
	('3', 'ROLE_WORK', 0, 'Work'),
	('4', 'ROLE_MANAGE', 0, 'Manage');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;

-- 테이블 bootauthdemo.role_menu 구조 내보내기
CREATE TABLE IF NOT EXISTS `role_menu` (
  `role_id` varchar(50) DEFAULT NULL,
  `menu_id` varchar(50) DEFAULT NULL,
  KEY `role_id_rm` (`role_id`),
  KEY `menu_code_rm` (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 bootauthdemo.role_menu:~15 rows (대략적) 내보내기
/*!40000 ALTER TABLE `role_menu` DISABLE KEYS */;
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES
	('ROLE_ADMIN', 'menu'),
	('ROLE_ADMIN', 'role'),
	('ROLE_ADMIN', 'system'),
	('ROLE_ADMIN', 'user'),
	('ROLE_MANAGE', 'group1'),
	('ROLE_MANAGE', 'test'),
	('ROLE_MANAGE', 'system'),
	('ROLE_MANAGE', 'menu'),
	('ROLE_MANAGE', 'role'),
	('ROLE_USER', 'group1'),
	('ROLE_USER', 'test'),
	('ROLE_WORK', 'group1'),
	('ROLE_WORK', 'test'),
	('ROLE_WORK', 'work2'),
	('ROLE_WORK', 'group2');
/*!40000 ALTER TABLE `role_menu` ENABLE KEYS */;

-- 테이블 bootauthdemo.users 구조 내보내기
CREATE TABLE IF NOT EXISTS `users` (
  `id` varchar(50) NOT NULL COMMENT 'id',
  `username` varchar(20) NOT NULL COMMENT 'username',
  `password` varchar(32) NOT NULL COMMENT 'password',
  `email` varchar(60) DEFAULT NULL COMMENT 'email',
  `salt` varchar(50) DEFAULT '0' COMMENT 'salt',
  `disabled` smallint NOT NULL DEFAULT '1' COMMENT '0、  1、 ',
  `createTime` datetime DEFAULT NULL COMMENT 'createTime',
  `lastTime` datetime DEFAULT NULL COMMENT 'lastTime',
  PRIMARY KEY (`id`),
  UNIQUE KEY `loginname` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='사용자';

-- 테이블 데이터 bootauthdemo.users:~4 rows (대략적) 내보내기
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id`, `username`, `password`, `email`, `salt`, `disabled`, `createTime`, `lastTime`) VALUES
	('1', 'admin', '1111', 'admin@abc.com', 'r', 0, '2016-09-27 19:53:20', '2016-09-27 19:53:22'),
	('2', 'james', '1111', 'jame2@abc.com', 'r', 0, '2016-09-27 19:53:20', '2016-09-27 19:53:22'),
	('3', 'user', '1111', 'user@abc.com', 'r', 0, '2016-09-27 19:53:20', '2016-09-27 19:53:22'),
	('4', 'worker1', '1111', 'worker1@abc.com', '&8aSxg|/LI', 0, '2020-09-12 04:48:06', NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

-- 테이블 bootauthdemo.user_roles 구조 내보내기
CREATE TABLE IF NOT EXISTS `user_roles` (
  `username` varchar(50) NOT NULL,
  `roles` varchar(50) NOT NULL,
  UNIQUE KEY `ix_auth_username` (`username`,`roles`) USING BTREE,
  CONSTRAINT `fk_authorities_users` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 bootauthdemo.user_roles:~0 rows (대략적) 내보내기
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` (`username`, `roles`) VALUES
	('admin', 'ROLE_ADMIN'),
	('admin', 'ROLE_MANAGE'),
	('admin', 'ROLE_USER'),
	('admin', 'ROLE_WORK'),
	('james', 'ROLE_MANAGE'),
	('james', 'ROLE_USER'),
	('james', 'ROLE_WORK'),
	('user', 'ROLE_USER'),
	('worker1', 'ROLE_USER'),
	('worker1', 'ROLE_WORK');
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
