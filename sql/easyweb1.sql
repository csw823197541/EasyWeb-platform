/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50718
Source Host           : 127.0.0.1:3306
Source Database       : easyweb1

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2018-11-10 17:10:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_authority
-- ----------------------------
DROP TABLE IF EXISTS `sys_authority`;
CREATE TABLE `sys_authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `authority_name` varchar(20) NOT NULL COMMENT '权限名称',
  `authority_url` varchar(100) DEFAULT NULL COMMENT '授权url',
  `menu_url` varchar(100) DEFAULT NULL COMMENT '菜单url',
  `menu_type` int(1) NOT NULL DEFAULT '1' COMMENT '0菜单，1按钮',
  `menu_icon` varchar(20) DEFAULT NULL COMMENT '菜单图标',
  `order_number` int(3) NOT NULL DEFAULT '0' COMMENT '排序号',
  `parent_id` int(11) NOT NULL DEFAULT '-1' COMMENT '父id',
  `operator` varchar(50) DEFAULT NULL COMMENT '操作人员',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `authority_name` (`authority_name`),
  UNIQUE KEY `authority_url` (`authority_url`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='权限表';

-- ----------------------------
-- Records of sys_authority
-- ----------------------------
INSERT INTO `sys_authority` VALUES ('1', '系统管理', '', '', '0', null, '1', '-1', null, '2018-09-12 22:38:29', '2018-09-13 18:08:41');
INSERT INTO `sys_authority` VALUES ('2', '用户管理', 'system/user', 'system/user', '0', '', '1', '1', null, '2018-09-13 18:06:04', '2018-09-13 18:08:44');
INSERT INTO `sys_authority` VALUES ('3', '修改用户状态', 'put:/sys/users/state', null, '1', null, '0', '2', 'admin', '2018-09-14 13:32:49', '2018-09-14 13:32:49');
INSERT INTO `sys_authority` VALUES ('4', '权限管理', null, 'system/authority', '0', '', '3', '1', 'admin', '2018-09-14 13:32:49', '2018-09-14 13:32:49');
INSERT INTO `sys_authority` VALUES ('5', '添加权限', 'post:/sys/authorities', null, '1', null, '2', '4', 'admin', '2018-09-14 13:32:49', '2018-09-14 13:32:49');
INSERT INTO `sys_authority` VALUES ('6', '修改权限', 'put:/sys/authorities', null, '1', null, '3', '4', 'admin', '2018-09-14 13:32:49', '2018-09-14 13:32:49');
INSERT INTO `sys_authority` VALUES ('7', '查询所有菜单权限', 'get:/sys/authorities/menuAuth', null, '1', null, '4', '4', 'admin', '2018-09-14 13:32:49', '2018-09-14 13:32:49');
INSERT INTO `sys_authority` VALUES ('8', '修改自己密码', 'put:/sys/users/psw', null, '1', null, '5', '2', 'admin', '2018-09-14 13:32:49', '2018-09-14 13:32:49');
INSERT INTO `sys_authority` VALUES ('9', '获取个人信息', 'get:/sys/users/userInfo', null, '1', null, '6', '2', 'admin', '2018-09-14 13:32:49', '2018-09-14 13:32:49');
INSERT INTO `sys_authority` VALUES ('10', '角色管理', null, 'system/role', '0', '', '2', '1', 'admin', '2018-09-14 13:32:49', '2018-09-14 13:32:49');
INSERT INTO `sys_authority` VALUES ('11', '添加角色', 'post:/sys/roles', null, '1', null, '8', '10', 'admin', '2018-09-14 13:32:49', '2018-09-14 13:32:49');
INSERT INTO `sys_authority` VALUES ('12', '查询所有角色', 'get:/sys/roles', null, '1', null, '9', '10', 'admin', '2018-09-14 13:32:49', '2018-09-14 13:32:49');
INSERT INTO `sys_authority` VALUES ('13', '修改角色', 'put:/sys/roles', null, '1', null, '10', '10', 'admin', '2018-09-14 13:32:49', '2018-09-14 13:32:49');
INSERT INTO `sys_authority` VALUES ('14', '删除角色', 'delete:/sys/roles/{id}', null, '1', null, '11', '10', 'admin', '2018-09-14 13:32:49', '2018-09-14 13:32:49');
INSERT INTO `sys_authority` VALUES ('15', '查询所有用户', 'post:/sys/users/query', null, '1', null, '12', '2', 'admin', '2018-09-14 13:32:49', '2018-09-14 13:32:49');
INSERT INTO `sys_authority` VALUES ('16', '查询所有权限', 'get:/sys/authorities/query', null, '1', null, '13', '4', 'admin', '2018-09-14 13:32:49', '2018-09-14 13:32:49');
INSERT INTO `sys_authority` VALUES ('17', '同步权限', 'post:/sys/authorities/sync', null, '1', null, '14', '4', 'admin', '2018-09-14 13:32:49', '2018-09-14 13:32:49');
INSERT INTO `sys_authority` VALUES ('18', '重置密码', 'put:/sys/users/psw/{id}', null, '1', null, '15', '2', 'admin', '2018-09-14 13:32:49', '2018-09-14 13:32:49');
INSERT INTO `sys_authority` VALUES ('19', '添加用户', 'post:/sys/users', null, '1', null, '16', '2', 'admin', '2018-09-14 13:32:49', '2018-09-14 13:32:49');
INSERT INTO `sys_authority` VALUES ('20', '修改用户', 'put:/sys/users', null, '1', null, '17', '2', 'admin', '2018-09-14 13:32:49', '2018-09-14 13:32:49');
INSERT INTO `sys_authority` VALUES ('21', '查询所有角色（表格）', 'get:/sys/roles/query', null, '1', null, '18', '10', 'admin', '2018-09-14 13:32:49', '2018-09-14 13:32:49');
INSERT INTO `sys_authority` VALUES ('23', '登录日志', null, '', '0', '', '0', '1', 'admin', '2018-09-15 07:47:17', '2018-09-15 07:47:17');
INSERT INTO `sys_authority` VALUES ('24', '角色权限(树)', 'post:/sys/roles/authTree', null, '1', null, '0', '10', 'admin', '2018-09-15 12:05:12', '2018-09-15 12:05:12');
INSERT INTO `sys_authority` VALUES ('25', '绑定权限', 'post:/sys/roles/updateRoleAuth', null, '1', null, '0', '10', 'admin', '2018-09-15 15:11:33', '2018-09-15 15:11:33');

-- ----------------------------
-- Table structure for sys_login_record
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_record`;
CREATE TABLE `sys_login_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(8) NOT NULL COMMENT '用户账号',
  `os_name` varchar(200) DEFAULT NULL COMMENT '操作系统',
  `device` varchar(200) DEFAULT NULL COMMENT '设备名',
  `browser_type` varchar(200) DEFAULT NULL COMMENT '浏览器类型',
  `ip_address` varchar(200) DEFAULT NULL COMMENT 'ip地址',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='登录日志表';

-- ----------------------------
-- Records of sys_login_record
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(20) NOT NULL COMMENT '角色名称',
  `role_type` varchar(20) NOT NULL COMMENT '角色类型',
  `comments` varchar(100) DEFAULT NULL COMMENT '备注',
  `operator` varchar(50) DEFAULT NULL COMMENT '操作人员',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_name` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '系统功能', 'ADMIN', '系统菜单功能', '', '2018-09-12 22:39:44', '2018-09-12 22:39:44');
INSERT INTO `sys_role` VALUES ('2', '角色管理', 'USER', 'test', 'admin', '2018-09-13 16:58:45', '2018-09-13 16:58:45');

-- ----------------------------
-- Table structure for sys_role_authority
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_authority`;
CREATE TABLE `sys_role_authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色权限关联Id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `authority_id` int(11) NOT NULL COMMENT '权限id',
  `operator` varchar(50) NOT NULL COMMENT '操作人员',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `FK_sys_role_permission_role` (`role_id`),
  KEY `FK_sys_role_permission_authority` (`authority_id`),
  CONSTRAINT `FK_sys_role_permission_authority` FOREIGN KEY (`authority_id`) REFERENCES `sys_authority` (`id`),
  CONSTRAINT `FK_sys_role_permission_role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='角色权限关联表';

-- ----------------------------
-- Records of sys_role_authority
-- ----------------------------
INSERT INTO `sys_role_authority` VALUES ('22', '1', '1', 'admin', '2018-09-15 15:06:05', '2018-09-15 15:06:05');
INSERT INTO `sys_role_authority` VALUES ('23', '1', '2', 'admin', '2018-09-15 15:06:05', '2018-09-15 15:06:05');
INSERT INTO `sys_role_authority` VALUES ('24', '1', '18', 'admin', '2018-09-15 15:06:05', '2018-09-15 15:06:05');
INSERT INTO `sys_role_authority` VALUES ('25', '1', '3', 'admin', '2018-09-15 15:06:05', '2018-09-15 15:06:05');
INSERT INTO `sys_role_authority` VALUES ('26', '1', '20', 'admin', '2018-09-15 15:06:05', '2018-09-15 15:06:05');
INSERT INTO `sys_role_authority` VALUES ('27', '1', '8', 'admin', '2018-09-15 15:06:05', '2018-09-15 15:06:05');
INSERT INTO `sys_role_authority` VALUES ('28', '1', '24', 'admin', '2018-09-15 15:06:05', '2018-09-15 15:06:05');
INSERT INTO `sys_role_authority` VALUES ('29', '1', '9', 'admin', '2018-09-15 15:06:05', '2018-09-15 15:06:05');
INSERT INTO `sys_role_authority` VALUES ('30', '1', '10', 'admin', '2018-09-15 15:06:05', '2018-09-15 15:06:05');
INSERT INTO `sys_role_authority` VALUES ('31', '1', '15', 'admin', '2018-09-15 15:06:05', '2018-09-15 15:06:05');
INSERT INTO `sys_role_authority` VALUES ('38', '2', '1', 'test', '2018-09-16 08:18:53', '2018-09-16 08:18:53');
INSERT INTO `sys_role_authority` VALUES ('39', '2', '2', 'test', '2018-09-16 08:18:53', '2018-09-16 08:18:53');
INSERT INTO `sys_role_authority` VALUES ('40', '2', '19', 'test', '2018-09-16 08:18:53', '2018-09-16 08:18:53');
INSERT INTO `sys_role_authority` VALUES ('41', '2', '20', 'test', '2018-09-16 08:18:53', '2018-09-16 08:18:53');
INSERT INTO `sys_role_authority` VALUES ('42', '2', '21', 'test', '2018-09-16 08:18:54', '2018-09-16 08:18:54');
INSERT INTO `sys_role_authority` VALUES ('43', '2', '9', 'test', '2018-09-16 08:18:54', '2018-09-16 08:18:54');
INSERT INTO `sys_role_authority` VALUES ('44', '2', '10', 'test', '2018-09-16 08:18:54', '2018-09-16 08:18:54');
INSERT INTO `sys_role_authority` VALUES ('45', '2', '11', 'test', '2018-09-16 08:18:54', '2018-09-16 08:18:54');
INSERT INTO `sys_role_authority` VALUES ('46', '2', '12', 'test', '2018-09-16 08:18:54', '2018-09-16 08:18:54');
INSERT INTO `sys_role_authority` VALUES ('47', '2', '13', 'test', '2018-09-16 08:18:54', '2018-09-16 08:18:54');
INSERT INTO `sys_role_authority` VALUES ('48', '2', '15', 'test', '2018-09-16 08:18:54', '2018-09-16 08:18:54');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(50) NOT NULL COMMENT '账号',
  `password` varchar(128) NOT NULL COMMENT '密码',
  `nick_name` varchar(50) NOT NULL COMMENT '昵称',
  `avatar` varchar(200) DEFAULT NULL COMMENT '头像',
  `sex` varchar(1) NOT NULL DEFAULT '男' COMMENT '性别',
  `phone` varchar(12) DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `email_verified` int(11) DEFAULT NULL COMMENT '邮箱是否验证，0未验证，1已验证',
  `state` int(1) NOT NULL DEFAULT '0' COMMENT '状态，0正常，1删除，2冻结',
  `operator` varchar(50) DEFAULT NULL COMMENT '操作人员',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_account` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'demo', '{bcrypt}$2a$10$kKqZaoluuMjfvusTuB5Z6e/RPhQgCXtkmBdhGokWPIi0RdhoWxD42', 'demo', null, '男', '13625436602', null, null, '0', null, '2018-09-12 21:36:40', '2018-09-15 17:44:26');
INSERT INTO `sys_user` VALUES ('2', 'admin', '{bcrypt}$2a$10$qrL6p6FKEitfGQnVRa.PPO.PsOJ4Dj9BSjEXll6fnruqBXABb/51O', '管理员', '', '女', '13125062807', 'whvcse@foxmail.com', null, '0', null, '2018-09-12 21:36:40', '2018-09-15 17:44:37');
INSERT INTO `sys_user` VALUES ('3', 'test', '{bcrypt}$2a$10$xTcIkgceIrIA3MX2U.GBFuTteabtl0OziMCrmuHr2NrbjA2MVTfs2', 'test', null, '女', '18217090001', null, null, '0', 'admin', '2018-09-15 22:16:18', '2018-09-15 22:16:18');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户角色关联Id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `operator` varchar(50) NOT NULL COMMENT '操作人员',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `FK_sys_user` (`user_id`),
  KEY `FK_sys_role` (`role_id`),
  CONSTRAINT `FK_sys_role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`),
  CONSTRAINT `FK_sys_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='用户角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '2', '1', 'admin', '2018-09-12 22:41:36', '2018-09-12 22:44:03');
INSERT INTO `sys_user_role` VALUES ('2', '1', '1', 'admin', '2018-09-13 19:07:09', '2018-09-13 19:07:09');
INSERT INTO `sys_user_role` VALUES ('3', '2', '2', '1', '2018-09-13 19:09:09', '2018-09-13 19:09:09');
INSERT INTO `sys_user_role` VALUES ('4', '1', '2', '1', '2018-09-13 19:09:17', '2018-09-13 19:09:24');
INSERT INTO `sys_user_role` VALUES ('7', '3', '2', 'admin', '2018-09-15 23:00:06', '2018-09-15 23:00:06');
