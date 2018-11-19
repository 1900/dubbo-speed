# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: mysql (MySQL 5.7.18)
# Database: dev
# Generation Time: 2018-10-08 07:08:45 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

# Dump of table sns_operation_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sns_operation_log`;

CREATE TABLE `sns_operation_log` (
  `operation_log_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `log_description` varchar(64) DEFAULT NULL COMMENT '日志描述',
  `action_args` varchar(300) DEFAULT NULL COMMENT '方法参数',
  `user_no` varchar(50) DEFAULT NULL COMMENT '用户主键',
  `class_name` varchar(300) DEFAULT NULL COMMENT '类名称',
  `method_name` varchar(64) DEFAULT NULL COMMENT '方法名称',
  `ip` varchar(32) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `succeed` int(2) DEFAULT NULL COMMENT '是否成功 1:成功 2异常',
  `message` longtext COMMENT '异常堆栈信息',
  PRIMARY KEY (`operation_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志表';


# Dump of table sns_user_thirdparty
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sns_user_thirdparty`;

CREATE TABLE `sns_user_thirdparty` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `uid` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '用户ID',
  `open_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '第三方Id',
  `user_no` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '绑定用户的id',
  `access_token` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '第三方token',
  `provider_type` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '第三方类型 qq:QQ 微信:WX 微博:SINA',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '状态',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '编号',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='第三方用户表';


# Dump of table sys_permission
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_permission`;

CREATE TABLE `sys_permission` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `available` tinyint(1) DEFAULT NULL COMMENT '是否可用',
  `permission_name` varchar(50) DEFAULT NULL COMMENT '权限名称',
  `parent_id` int(10) DEFAULT NULL COMMENT '父权限ID',
  `parent_ids` varchar(255) DEFAULT NULL COMMENT '父权限ID列表',
  `permission_code` varchar(50) DEFAULT NULL COMMENT '权限编码',
  `resource_type` varchar(50) DEFAULT NULL COMMENT '资源类型(directory/menu/button)',
  `url` varchar(50) DEFAULT NULL COMMENT '资源路径',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统权限表';

LOCK TABLES `sys_permission` WRITE;
/*!40000 ALTER TABLE `sys_permission` DISABLE KEYS */;

INSERT INTO `sys_permission` (`id`, `available`, `permission_name`, `parent_id`, `parent_ids`, `permission_code`, `resource_type`, `url`, `update_time`, `create_time`)
VALUES
	(1,1,'系统管理',0,'0','system','directory',NULL,'2018-07-10 11:24:48','2018-07-10 11:24:48'),
	(2,1,'用户管理',1,'0/1','user:view','menu','/user/list','2018-07-10 11:24:48','2018-07-10 11:24:48'),
	(3,1,'用户添加',2,'0/1/2','user:add','button','','2018-07-10 11:25:40','2018-07-10 11:25:40'),
	(4,1,'用户删除',2,'0/1/2','user:del','button','','2018-07-10 11:27:10','2018-07-10 11:27:10'),
	(5,1,'用户编辑',2,'0/1/2','user:edit','button','','2018-07-10 11:27:36','2018-07-10 11:27:36'),
	(6,1,'角色管理',1,'0/1','role:view','menu','/role/list','2018-08-04 09:38:44','2018-08-04 09:38:44'),
	(7,1,'角色添加',6,'0/1/6','role:add','button','','2018-08-04 09:42:05','2018-08-04 09:42:05'),
	(8,1,'角色删除',6,'0/1/6','role:del','button','','2018-08-04 09:43:26','2018-08-04 09:43:26'),
	(9,1,'角色编辑',6,'0/1/6','role:edit','button','','2018-08-04 09:46:01','2018-08-04 09:46:01'),
	(10,1,'权限管理',1,'0/1','permission:view','menu','/permission/list','2018-08-04 09:48:57','2018-08-04 09:48:57'),
	(11,1,'权限添加',10,'0/1/10','permission:add','button','','2018-08-04 09:50:50','2018-08-04 09:50:50'),
	(12,1,'权限删除',10,'0/1/10','permission:del','button','','2018-08-04 09:50:50','2018-08-04 09:50:50'),
	(13,1,'权限编辑',10,'0/1/10','permission:edit','button','','2018-08-23 11:33:34','2018-08-23 11:33:34'),
	(14,1,'测试管理',0,'0','test','directory',NULL,'2018-07-10 11:24:48','2018-07-10 11:24:48'),
	(15,1,'一级菜单',14,'0/14','onemenu:view','menu','/onemenu/list','2018-08-25 14:27:26','2018-07-10 11:24:48'),
	(16,1,'图标管理',1,'0/1','icon:view','menu','/icon/icons','2018-08-23 13:15:51','2018-08-23 13:15:51');

/*!40000 ALTER TABLE `sys_permission` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `permission_ids` varchar(255) DEFAULT NULL COMMENT '权限ID列表',
  `available` tinyint(1) DEFAULT NULL COMMENT '是否可用',
  `role_name` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `role_code` varchar(50) DEFAULT NULL COMMENT '角色编码',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色表';

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;

INSERT INTO `sys_role` (`id`, `permission_ids`, `available`, `role_name`, `role_code`, `update_time`, `create_time`)
VALUES
	(1,',1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,',1,'超级管理员','admin','2018-07-10 11:19:49','2018-07-10 11:19:49'),
	(2,',14,15,',1,'测试员','test','2018-08-25 14:27:51','2018-07-10 11:19:49');

/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_sms_verify
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_sms_verify`;

CREATE TABLE `sys_sms_verify` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `uid` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '用户ID',
  `sms_id` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'sms id',
  `mobile` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '电话号码',
  `sms_type` varchar(1) COLLATE utf8_bin DEFAULT NULL COMMENT '验证码类型（1：登录验证，2：注册验证，3：忘记密码，4：修改账号）',
  `sms_verify` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '验证码',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '状态',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '编号',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='SMS发送记录表';

LOCK TABLES `sys_sms_verify` WRITE;
/*!40000 ALTER TABLE `sys_sms_verify` DISABLE KEYS */;

INSERT INTO `sys_sms_verify` (`id`, `uid`, `sms_id`, `mobile`, `sms_type`, `sms_verify`, `create_by`, `create_date`, `remarks`, `status`, `update_by`, `update_date`)
VALUES
	(X'31303434373837333733363135363434363733',NULL,NULL,X'3133393939393939393939',X'32',X'31373136',NULL,'2018-09-26 11:14:55',NULL,'0',NULL,'2018-09-26 11:14:55');

/*!40000 ALTER TABLE `sys_sms_verify` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '编号',
  `user_no` varchar(255) DEFAULT NULL COMMENT '用户NO',
  `role_id` varchar(10) DEFAULT NULL COMMENT '角色ID',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '名称',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `pass_word` varchar(255) DEFAULT NULL COMMENT '密码',
  `salt` varchar(255) DEFAULT NULL COMMENT '盐值',
  `mobile` varchar(255) DEFAULT NULL COMMENT '电话',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `profile` varchar(1024) DEFAULT NULL COMMENT '头像',
  `source` varchar(255) DEFAULT NULL COMMENT '来源',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '状态',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '编号',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='sns用户表';

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;

/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
