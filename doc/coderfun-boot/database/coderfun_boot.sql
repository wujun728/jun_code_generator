/*
 Navicat Premium Data Transfer

 Source Server         : dev-mysql5.7
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : 47.98.129.217:3306
 Source Schema         : coderfun_boot

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 03/12/2018 20:17:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cf_boot_permission
-- ----------------------------
DROP TABLE IF EXISTS `cf_boot_permission`;
CREATE TABLE `cf_boot_permission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型:菜单or功能',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `perm_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单编码',
  `icon_cls` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `state` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `order_num` double NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cf_boot_permission
-- ----------------------------
INSERT INTO `cf_boot_permission` VALUES (1, 2, '用户管理', 'menu', '/admin/pages/boot/user.jsp', NULL, 'icon-user', NULL, NULL, 1);
INSERT INTO `cf_boot_permission` VALUES (2, NULL, '系统管理', 'menu', NULL, NULL, 'icon-application_home', NULL, NULL, NULL);
INSERT INTO `cf_boot_permission` VALUES (3, 2, '角色管理', 'menu', '/admin/pages/boot/role.jsp', NULL, 'icon-group', NULL, NULL, 2);
INSERT INTO `cf_boot_permission` VALUES (4, 6, '菜单管理', 'menu', '/admin/pages/boot/menu.jsp', NULL, 'icon-application_side_list', NULL, NULL, 3);
INSERT INTO `cf_boot_permission` VALUES (5, 6, '权限管理', 'menu', '/admin/pages/boot/permission.jsp', NULL, 'icon-2012080412263', NULL, NULL, 4);
INSERT INTO `cf_boot_permission` VALUES (6, NULL, '系统设置', 'menu', NULL, NULL, 'icon-2012080404391', NULL, NULL, NULL);
INSERT INTO `cf_boot_permission` VALUES (15, 6, '数据字典', 'menu', '/admin/pages/dict/dict.jsp', NULL, ' icon-application_view_detail ', NULL, NULL, 5);
INSERT INTO `cf_boot_permission` VALUES (16, 1, '添加', 'operation', NULL, 'boot:user:add', NULL, NULL, NULL, NULL);
INSERT INTO `cf_boot_permission` VALUES (17, 1, '修改资料', 'operation', NULL, 'boot:user:edit', NULL, NULL, NULL, NULL);
INSERT INTO `cf_boot_permission` VALUES (18, 1, '修改密码', 'operation', NULL, 'boot:user:updatePassword', NULL, NULL, NULL, NULL);
INSERT INTO `cf_boot_permission` VALUES (19, 1, '查询', 'operation', NULL, 'boot:user:query', NULL, NULL, NULL, NULL);
INSERT INTO `cf_boot_permission` VALUES (20, 3, '添加', 'operation', NULL, 'boot:role:add', NULL, NULL, NULL, NULL);
INSERT INTO `cf_boot_permission` VALUES (21, 3, '修改', 'operation', NULL, 'boot:role:edit', NULL, NULL, NULL, NULL);
INSERT INTO `cf_boot_permission` VALUES (22, 3, '查询', 'operation', NULL, 'boot:role:query', NULL, NULL, NULL, NULL);
INSERT INTO `cf_boot_permission` VALUES (23, 5, '添加', 'operation', NULL, 'boot:permission:add', NULL, NULL, NULL, NULL);
INSERT INTO `cf_boot_permission` VALUES (24, 5, '修改', 'operation', NULL, 'boot:permission:edit', NULL, NULL, NULL, NULL);
INSERT INTO `cf_boot_permission` VALUES (25, 5, '删除', 'operation', NULL, 'boot:permission:delete', NULL, NULL, NULL, NULL);
INSERT INTO `cf_boot_permission` VALUES (26, 5, '查询', 'operation', NULL, 'boot:permission:query', NULL, NULL, NULL, NULL);
INSERT INTO `cf_boot_permission` VALUES (27, 1, '查询角色', 'operation', NULL, 'boot:userRole:query', NULL, NULL, NULL, NULL);
INSERT INTO `cf_boot_permission` VALUES (28, 1, '修改角色', 'operation', NULL, 'boot:userRole:update', NULL, NULL, NULL, NULL);
INSERT INTO `cf_boot_permission` VALUES (29, 3, '查询权限', 'operation', NULL, 'boot:rolePermissions:query', NULL, NULL, NULL, NULL);
INSERT INTO `cf_boot_permission` VALUES (30, 3, '授权', 'operation', NULL, 'boot:rolePermissions:update', NULL, NULL, NULL, NULL);
INSERT INTO `cf_boot_permission` VALUES (31, 15, '添加', 'operation', NULL, 'common:dict:add', NULL, NULL, NULL, NULL);
INSERT INTO `cf_boot_permission` VALUES (32, 15, '修改', 'operation', NULL, 'common:dict:edit', NULL, NULL, NULL, NULL);
INSERT INTO `cf_boot_permission` VALUES (33, 15, '删除', 'operation', NULL, 'common:dict:delete', NULL, NULL, NULL, NULL);
INSERT INTO `cf_boot_permission` VALUES (34, 15, '查询', 'operation', NULL, 'common:dict:query', NULL, NULL, NULL, NULL);
INSERT INTO `cf_boot_permission` VALUES (35, 2, '登录日志', 'menu', '/admin/pages/log/loginlog.jsp', NULL, 'icon-page_white_go', NULL, NULL, 3);
INSERT INTO `cf_boot_permission` VALUES (36, 2, '系统日志', 'menu', '/admin/pages/log/syslog.jsp', NULL, 'icon-page_white_stack', NULL, NULL, 4);
INSERT INTO `cf_boot_permission` VALUES (37, 35, '查询', 'operation', NULL, 'common:loginlog:query', NULL, NULL, NULL, NULL);
INSERT INTO `cf_boot_permission` VALUES (38, 36, '查询', 'operation', NULL, 'common:syslog:query', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for cf_boot_role
-- ----------------------------
DROP TABLE IF EXISTS `cf_boot_role`;
CREATE TABLE `cf_boot_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `sort` smallint(6) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cf_boot_role
-- ----------------------------
INSERT INTO `cf_boot_role` VALUES (1, '系统维护', 'maintain', NULL, NULL);
INSERT INTO `cf_boot_role` VALUES (2, '编辑', 'editor', NULL, NULL);
INSERT INTO `cf_boot_role` VALUES (3, '系统管理', 'sysmanager', NULL, NULL);

-- ----------------------------
-- Table structure for cf_boot_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `cf_boot_role_permission`;
CREATE TABLE `cf_boot_role_permission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NULL DEFAULT NULL,
  `permission_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 67 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cf_boot_role_permission
-- ----------------------------
INSERT INTO `cf_boot_role_permission` VALUES (1, 1, 2);
INSERT INTO `cf_boot_role_permission` VALUES (4, 1, 4);
INSERT INTO `cf_boot_role_permission` VALUES (5, 1, 5);
INSERT INTO `cf_boot_role_permission` VALUES (7, 3, 2);
INSERT INTO `cf_boot_role_permission` VALUES (9, 1, 3);
INSERT INTO `cf_boot_role_permission` VALUES (14, 3, 1);
INSERT INTO `cf_boot_role_permission` VALUES (16, 1, 6);
INSERT INTO `cf_boot_role_permission` VALUES (18, 3, 16);
INSERT INTO `cf_boot_role_permission` VALUES (19, 3, 17);
INSERT INTO `cf_boot_role_permission` VALUES (20, 3, 18);
INSERT INTO `cf_boot_role_permission` VALUES (21, 3, 19);
INSERT INTO `cf_boot_role_permission` VALUES (22, 3, 27);
INSERT INTO `cf_boot_role_permission` VALUES (23, 3, 28);
INSERT INTO `cf_boot_role_permission` VALUES (27, 1, 20);
INSERT INTO `cf_boot_role_permission` VALUES (28, 1, 21);
INSERT INTO `cf_boot_role_permission` VALUES (29, 1, 22);
INSERT INTO `cf_boot_role_permission` VALUES (30, 1, 29);
INSERT INTO `cf_boot_role_permission` VALUES (31, 1, 30);
INSERT INTO `cf_boot_role_permission` VALUES (32, 1, 23);
INSERT INTO `cf_boot_role_permission` VALUES (33, 1, 24);
INSERT INTO `cf_boot_role_permission` VALUES (34, 1, 25);
INSERT INTO `cf_boot_role_permission` VALUES (35, 1, 26);
INSERT INTO `cf_boot_role_permission` VALUES (36, 1, 15);
INSERT INTO `cf_boot_role_permission` VALUES (37, 1, 31);
INSERT INTO `cf_boot_role_permission` VALUES (38, 1, 32);
INSERT INTO `cf_boot_role_permission` VALUES (39, 1, 33);
INSERT INTO `cf_boot_role_permission` VALUES (40, 1, 34);
INSERT INTO `cf_boot_role_permission` VALUES (41, 1, 19);
INSERT INTO `cf_boot_role_permission` VALUES (42, 1, 1);
INSERT INTO `cf_boot_role_permission` VALUES (43, 1, 27);
INSERT INTO `cf_boot_role_permission` VALUES (44, 1, 28);
INSERT INTO `cf_boot_role_permission` VALUES (53, 3, 3);
INSERT INTO `cf_boot_role_permission` VALUES (54, 3, 20);
INSERT INTO `cf_boot_role_permission` VALUES (55, 3, 21);
INSERT INTO `cf_boot_role_permission` VALUES (56, 3, 22);
INSERT INTO `cf_boot_role_permission` VALUES (57, 3, 29);
INSERT INTO `cf_boot_role_permission` VALUES (58, 3, 30);
INSERT INTO `cf_boot_role_permission` VALUES (59, 1, 35);
INSERT INTO `cf_boot_role_permission` VALUES (60, 1, 36);
INSERT INTO `cf_boot_role_permission` VALUES (61, 3, 35);
INSERT INTO `cf_boot_role_permission` VALUES (62, 3, 36);
INSERT INTO `cf_boot_role_permission` VALUES (63, 3, 37);
INSERT INTO `cf_boot_role_permission` VALUES (64, 3, 38);
INSERT INTO `cf_boot_role_permission` VALUES (65, 1, 37);
INSERT INTO `cf_boot_role_permission` VALUES (66, 1, 38);

-- ----------------------------
-- Table structure for cf_boot_user
-- ----------------------------
DROP TABLE IF EXISTS `cf_boot_user`;
CREATE TABLE `cf_boot_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `salt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `birthday` datetime(0) NULL DEFAULT NULL,
  `gender` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `avatar` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `create_date` datetime(0) NULL DEFAULT NULL,
  `state` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `login_count` int(11) NULL DEFAULT NULL,
  `previous_visit` datetime(0) NULL DEFAULT NULL,
  `last_visit` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_User_LoginName`(`login_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cf_boot_user
-- ----------------------------
INSERT INTO `cf_boot_user` VALUES (1, 'admin', 'admin', 'f889913d92a2f55c61ede94eb60fb18b', '110dcddf827ce06fe49d47f08803b63f', '2018-11-16 00:00:00', 'male', 'klguang@xxx.com', '13000000000', NULL, NULL, 'normal', NULL, NULL, NULL, NULL);
INSERT INTO `cf_boot_user` VALUES (2, 'klguang', 'klguang', '3c0645201f90226cb7d1c708758252f9', 'ed19fbe2e2462ef6e812c23517f946f2', NULL, 'male', NULL, NULL, NULL, NULL, 'normal', NULL, NULL, NULL, NULL);
INSERT INTO `cf_boot_user` VALUES (3, 'Editor', 'Editor', 'a19c2d5475f15cdd7740d8a5644d8afd', 'df13d168b3eaf5fef2fc1819f997e879', NULL, 'male', NULL, NULL, NULL, '2018-11-16 15:02:33', 'normal', NULL, NULL, NULL, NULL);
INSERT INTO `cf_boot_user` VALUES (5, 'test', 'test', 'b67161a99babff94141daf855748d8ef', 'd1ab5cc09e8fbbee3dccd0d4968b65be', NULL, 'male', NULL, NULL, NULL, NULL, 'forbidden', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for cf_boot_user_role
-- ----------------------------
DROP TABLE IF EXISTS `cf_boot_user_role`;
CREATE TABLE `cf_boot_user_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cf_boot_user_role
-- ----------------------------
INSERT INTO `cf_boot_user_role` VALUES (2, 2, 1);
INSERT INTO `cf_boot_user_role` VALUES (3, 3, 2);
INSERT INTO `cf_boot_user_role` VALUES (6, 5, 3);

-- ----------------------------
-- Table structure for cf_common_codeclass
-- ----------------------------
DROP TABLE IF EXISTS `cf_common_codeclass`;
CREATE TABLE `cf_common_codeclass`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '代码',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '值',
  `module_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属模块',
  `is_sys` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否系统内置，0否，1是',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` datetime(0) NULL DEFAULT NULL,
  `remark` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `order_num` double(11, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `code_class_unique`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cf_common_codeclass
-- ----------------------------
INSERT INTO `cf_common_codeclass` VALUES (15, 'module_code', '所属模块', NULL, 'sys', 'yes', '2018-06-13 14:03:25', '2018-11-20 00:36:30', NULL, NULL);
INSERT INTO `cf_common_codeclass` VALUES (16, 'yes_or_no', '是否', NULL, 'sys', 'yes', '2018-06-13 14:03:48', '2018-06-13 14:03:48', NULL, NULL);
INSERT INTO `cf_common_codeclass` VALUES (26, 'gender', '性别', NULL, 'sys', 'yes', '2018-09-29 14:51:01', NULL, NULL, NULL);
INSERT INTO `cf_common_codeclass` VALUES (27, 'user_state', '用户状态', NULL, 'sys', 'yes', '2018-09-30 05:58:54', '2018-09-30 06:14:05', NULL, NULL);
INSERT INTO `cf_common_codeclass` VALUES (28, 'log_module', '日志模块', NULL, 'sys', 'yes', '2018-11-23 23:32:21', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for cf_common_codeitem
-- ----------------------------
DROP TABLE IF EXISTS `cf_common_codeitem`;
CREATE TABLE `cf_common_codeitem`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `class_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属分类',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '代码',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '值',
  `extension` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '扩展，json',
  `pinyin` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '汉语拼音',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` datetime(0) NULL DEFAULT NULL,
  `remark` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `order_num` double(11, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cf_common_codeitem
-- ----------------------------
INSERT INTO `cf_common_codeitem` VALUES (1, 'yes_or_no', 'yes', '是', NULL, '1111', NULL, '2018-05-14 13:44:32', '2018-11-27 22:26:52', NULL, NULL);
INSERT INTO `cf_common_codeitem` VALUES (2, 'yes_or_no', 'no', '否', NULL, NULL, NULL, '2018-05-14 13:44:32', '2018-11-27 15:09:45', NULL, NULL);
INSERT INTO `cf_common_codeitem` VALUES (3, 'module_code', 'sys', '系统', NULL, NULL, NULL, '2018-05-14 14:22:03', '2018-12-01 00:36:33', NULL, NULL);
INSERT INTO `cf_common_codeitem` VALUES (7, 'module_code', 'fieldmeta', '字段元', NULL, NULL, NULL, NULL, '2018-11-20 00:36:34', NULL, NULL);
INSERT INTO `cf_common_codeitem` VALUES (40, 'gender', 'male', '男', NULL, NULL, NULL, '2018-09-29 14:52:49', NULL, NULL, NULL);
INSERT INTO `cf_common_codeitem` VALUES (41, 'gender', 'female', '女', NULL, NULL, NULL, '2018-09-29 14:52:59', '2018-11-27 16:00:13', NULL, NULL);
INSERT INTO `cf_common_codeitem` VALUES (42, 'user_state', 'normal', '正常', NULL, NULL, NULL, '2018-09-30 05:59:27', NULL, NULL, NULL);
INSERT INTO `cf_common_codeitem` VALUES (43, 'user_state', 'forbidden', '禁用', NULL, NULL, NULL, '2018-09-30 05:59:39', '2018-09-30 06:02:47', NULL, NULL);
INSERT INTO `cf_common_codeitem` VALUES (44, 'log_module', 'boot', '基础模块', NULL, NULL, NULL, '2018-11-23 23:32:43', NULL, NULL, NULL);
INSERT INTO `cf_common_codeitem` VALUES (45, 'log_module', 'dict', '字典模块', NULL, NULL, NULL, '2018-11-23 23:32:55', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for cf_common_loginlog
-- ----------------------------
DROP TABLE IF EXISTS `cf_common_loginlog`;
CREATE TABLE `cf_common_loginlog`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `message` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `browser` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `os` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `opip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `optime` datetime(0) NULL DEFAULT NULL,
  `opusername` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `successed` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cf_common_loginlog
-- ----------------------------
INSERT INTO `cf_common_loginlog` VALUES (1, '验证码错误', 'Chrome/70.0.3538.110', 'Windows 10', '0:0:0:0:0:0:0:1', '2018-11-30 23:20:23', 'admin', 'no');
INSERT INTO `cf_common_loginlog` VALUES (2, NULL, 'Chrome/70.0.3538.110', 'Windows 10', '0:0:0:0:0:0:0:1', '2018-11-30 23:20:27', 'admin', 'yes');
INSERT INTO `cf_common_loginlog` VALUES (3, NULL, 'Firefox/63.0', 'Windows 10', '0:0:0:0:0:0:0:1', '2018-12-01 00:22:24', 'admin', 'yes');
INSERT INTO `cf_common_loginlog` VALUES (4, NULL, 'Chrome/70.0.3538.110', 'Windows 10', '0:0:0:0:0:0:0:1', '2018-12-01 00:33:57', 'klguang', 'yes');
INSERT INTO `cf_common_loginlog` VALUES (5, NULL, 'Chrome/70.0.3538.110', 'Windows 10', '0:0:0:0:0:0:0:1', '2018-12-01 00:33:57', 'klguang', 'yes');
INSERT INTO `cf_common_loginlog` VALUES (6, NULL, 'Chrome/70.0.3538.110', 'Windows 10', '0:0:0:0:0:0:0:1', '2018-12-01 01:29:31', 'admin', 'yes');
INSERT INTO `cf_common_loginlog` VALUES (7, NULL, 'Chrome/70.0.3538.110', 'Windows 10', '0:0:0:0:0:0:0:1', '2018-12-01 01:33:00', 'admin', 'yes');
INSERT INTO `cf_common_loginlog` VALUES (8, NULL, 'Chrome/70.0.3538.110', 'Windows 10', '0:0:0:0:0:0:0:1', '2018-12-01 01:33:01', 'admin', 'yes');
INSERT INTO `cf_common_loginlog` VALUES (9, NULL, 'Chrome/70.0.3538.110', 'Windows 10', '0:0:0:0:0:0:0:1', '2018-12-01 01:33:27', 'admin', 'yes');
INSERT INTO `cf_common_loginlog` VALUES (10, '验证码错误', 'Chrome/70.0.3538.110', 'Windows 10', '0:0:0:0:0:0:0:1', '2018-12-01 13:01:32', 'admin', 'no');
INSERT INTO `cf_common_loginlog` VALUES (11, NULL, 'Chrome/70.0.3538.110', 'Windows 10', '0:0:0:0:0:0:0:1', '2018-12-01 13:01:39', 'admin', 'yes');
INSERT INTO `cf_common_loginlog` VALUES (12, NULL, 'Chrome/70.0.3538.110', 'Windows 10', '0:0:0:0:0:0:0:1', '2018-12-01 13:03:59', 'admin', 'yes');
INSERT INTO `cf_common_loginlog` VALUES (13, NULL, 'Chrome/70.0.3538.110', 'Windows 10', '0:0:0:0:0:0:0:1', '2018-12-01 13:05:48', 'admin', 'yes');
INSERT INTO `cf_common_loginlog` VALUES (14, NULL, 'Chrome/70.0.3538.110', 'Windows 10', '0:0:0:0:0:0:0:1', '2018-12-01 13:40:16', 'admin', 'yes');
INSERT INTO `cf_common_loginlog` VALUES (15, NULL, 'Chrome/70.0.3538.110', 'Windows 10', '0:0:0:0:0:0:0:1', '2018-12-01 13:43:18', 'admin', 'yes');
INSERT INTO `cf_common_loginlog` VALUES (16, NULL, 'Chrome/70.0.3538.110', 'Windows 10', '0:0:0:0:0:0:0:1', '2018-12-01 13:43:19', 'admin', 'yes');
INSERT INTO `cf_common_loginlog` VALUES (17, NULL, 'Chrome/70.0.3538.110', 'Windows 10', '0:0:0:0:0:0:0:1', '2018-12-01 13:55:23', 'admin', 'yes');
INSERT INTO `cf_common_loginlog` VALUES (18, NULL, 'Chrome/70.0.3538.110', 'Windows 10', '0:0:0:0:0:0:0:1', '2018-12-01 13:55:24', 'admin', 'yes');
INSERT INTO `cf_common_loginlog` VALUES (19, NULL, 'Chrome/70.0.3538.110', 'Windows 10', '0:0:0:0:0:0:0:1', '2018-12-01 16:26:41', 'admin', 'yes');
INSERT INTO `cf_common_loginlog` VALUES (20, NULL, 'Firefox/63.0', 'Windows 10', '0:0:0:0:0:0:0:1', '2018-12-01 16:34:27', 'klguang', 'yes');
INSERT INTO `cf_common_loginlog` VALUES (21, '密码错误', 'Firefox/63.0', 'Windows 10', '0:0:0:0:0:0:0:1', '2018-12-01 16:36:14', 'test', 'no');
INSERT INTO `cf_common_loginlog` VALUES (22, '密码错误', 'Firefox/63.0', 'Windows 10', '0:0:0:0:0:0:0:1', '2018-12-01 16:36:19', 'test', 'no');
INSERT INTO `cf_common_loginlog` VALUES (23, '密码错误', 'Firefox/63.0', 'Windows 10', '0:0:0:0:0:0:0:1', '2018-12-01 16:36:26', 'test', 'no');
INSERT INTO `cf_common_loginlog` VALUES (24, NULL, 'Firefox/63.0', 'Windows 10', '0:0:0:0:0:0:0:1', '2018-12-01 16:36:39', 'test', 'yes');
INSERT INTO `cf_common_loginlog` VALUES (25, '验证码错误', 'Chrome/70.0.3538.110', 'Windows 10', '0:0:0:0:0:0:0:1', '2018-12-01 16:48:42', 'admin', 'no');
INSERT INTO `cf_common_loginlog` VALUES (26, NULL, 'Chrome/70.0.3538.110', 'Windows 10', '0:0:0:0:0:0:0:1', '2018-12-01 16:48:52', 'admin', 'yes');
INSERT INTO `cf_common_loginlog` VALUES (27, NULL, 'Chrome/70.0.3538.110', 'Windows 10', '0:0:0:0:0:0:0:1', '2018-12-03 17:39:09', 'admin', 'yes');
INSERT INTO `cf_common_loginlog` VALUES (28, NULL, 'Chrome/70.0.3538.110', 'Windows 10', '0:0:0:0:0:0:0:1', '2018-12-03 17:39:09', 'admin', 'yes');
INSERT INTO `cf_common_loginlog` VALUES (29, NULL, 'Chrome/70.0.3538.110', 'Windows 10', '0:0:0:0:0:0:0:1', '2018-12-03 18:04:03', 'admin', 'yes');

-- ----------------------------
-- Table structure for cf_common_syslog
-- ----------------------------
DROP TABLE IF EXISTS `cf_common_syslog`;
CREATE TABLE `cf_common_syslog`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `module_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `execute_time` bigint(20) NULL DEFAULT NULL,
  `successed` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `os` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `browser` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `opip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `optime` datetime(0) NULL DEFAULT NULL,
  `opusername` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cf_common_syslog
-- ----------------------------
INSERT INTO `cf_common_syslog` VALUES (1, '修改权限', 'boot', 'org.coderfun.boot.web.controller.PermissionController.edit()', '{\r\n  \"id\" : 16,\r\n  \"parentId\" : 1,\r\n  \"name\" : \"添加\",\r\n  \"children\" : [ ],\r\n  \"type\" : \"operation\",\r\n  \"permCode\" : \"boot:user:add\",\r\n  \"text\" : \"添加\"\r\n}', 377, 'yes', NULL, 'Windows 10', 'Chrome/70.0.3538.110', '0:0:0:0:0:0:0:1', '2018-12-01 00:05:09', 'admin');
INSERT INTO `cf_common_syslog` VALUES (2, '修改权限', 'boot', 'org.coderfun.boot.web.controller.PermissionController.edit()', '{\r\n  \"id\" : 16,\r\n  \"parentId\" : 1,\r\n  \"name\" : \"添加\",\r\n  \"children\" : [ ],\r\n  \"type\" : \"operation\",\r\n  \"permCode\" : \"boot:user:add\",\r\n  \"description\" : \"111\",\r\n  \"text\" : \"添加\"\r\n}', 459, 'no', '测试事务！', 'Windows 10', 'Chrome/70.0.3538.110', '0:0:0:0:0:0:0:1', '2018-12-01 00:15:33', 'admin');
INSERT INTO `cf_common_syslog` VALUES (3, '修改权限', 'boot', 'org.coderfun.boot.web.controller.PermissionController.edit()', '{\r\n  \"id\" : 19,\r\n  \"parentId\" : 1,\r\n  \"name\" : \"查询\",\r\n  \"children\" : [ ],\r\n  \"type\" : \"operation\",\r\n  \"permCode\" : \"boot:user:query\",\r\n  \"description\" : \"111\",\r\n  \"text\" : \"查询\"\r\n}', 386, 'no', '测试事务！', 'Windows 10', 'Chrome/70.0.3538.110', '0:0:0:0:0:0:0:1', '2018-12-01 00:15:43', 'admin');
INSERT INTO `cf_common_syslog` VALUES (4, '修改权限', 'boot', 'org.coderfun.boot.web.controller.PermissionController.edit()', '{\r\n  \"id\" : 21,\r\n  \"parentId\" : 3,\r\n  \"name\" : \"修改\",\r\n  \"children\" : [ ],\r\n  \"type\" : \"operation\",\r\n  \"permCode\" : \"boot:role:edit\",\r\n  \"text\" : \"修改\"\r\n}', 282, 'no', '测试事务！', 'Windows 10', 'Firefox/63.0', '0:0:0:0:0:0:0:1', '2018-12-01 00:33:21', 'admin');
INSERT INTO `cf_common_syslog` VALUES (5, '修改权限', 'boot', 'org.coderfun.boot.web.controller.PermissionController.edit()', '{\r\n  \"id\" : 29,\r\n  \"parentId\" : 3,\r\n  \"name\" : \"查询权限\",\r\n  \"children\" : [ ],\r\n  \"type\" : \"operation\",\r\n  \"permCode\" : \"boot:rolePermissions:query\",\r\n  \"text\" : \"查询权限\"\r\n}', 273, 'no', '测试事务！', 'Windows 10', 'Chrome/70.0.3538.110', '0:0:0:0:0:0:0:1', '2018-12-01 00:35:09', 'klguang');
INSERT INTO `cf_common_syslog` VALUES (6, '修改字典子类', 'dict', 'org.coderfun.common.dict.controller.admin.CodeItemController.edit()', '{\r\n  \"id\" : 3,\r\n  \"classCode\" : \"module_code\",\r\n  \"code\" : \"sys\",\r\n  \"name\" : \"系统\",\r\n  \"new\" : false\r\n}', 640, 'yes', NULL, 'Windows 10', 'Chrome/70.0.3538.110', '0:0:0:0:0:0:0:1', '2018-12-01 00:36:33', 'klguang');
INSERT INTO `cf_common_syslog` VALUES (7, '添加字典子类', 'dict', 'org.coderfun.common.dict.controller.admin.CodeItemController.add()', '{\r\n  \"classCode\" : \"module_code\",\r\n  \"code\" : \"test\",\r\n  \"name\" : \"111\",\r\n  \"new\" : true\r\n}', 1023, 'yes', NULL, 'Windows 10', 'Chrome/70.0.3538.110', '0:0:0:0:0:0:0:1', '2018-12-01 14:06:12', 'admin');
INSERT INTO `cf_common_syslog` VALUES (8, '删除字典子类', 'dict', 'org.coderfun.common.dict.controller.admin.CodeItemController.delete()', '46', 553, 'yes', NULL, 'Windows 10', 'Chrome/70.0.3538.110', '0:0:0:0:0:0:0:1', '2018-12-01 14:06:40', 'admin');
INSERT INTO `cf_common_syslog` VALUES (9, '修改权限', 'boot', 'org.coderfun.boot.web.controller.PermissionController.edit()', '{\r\n  \"id\" : 16,\r\n  \"parentId\" : 1,\r\n  \"name\" : \"添加\",\r\n  \"children\" : [ ],\r\n  \"type\" : \"operation\",\r\n  \"permCode\" : \"boot:user:add\",\r\n  \"description\" : \"1111\",\r\n  \"text\" : \"添加\"\r\n}', 394, 'no', '测试事务！', 'Windows 10', 'Chrome/70.0.3538.110', '0:0:0:0:0:0:0:1', '2018-12-01 14:07:21', 'admin');
INSERT INTO `cf_common_syslog` VALUES (10, '修改用户', 'boot', 'org.coderfun.boot.web.controller.UserController.edit()', '{\r\n  \"id\" : 5,\r\n  \"name\" : \"test\",\r\n  \"gender\" : \"male\",\r\n  \"state\" : \"forbidden\"\r\n}', 409, 'yes', NULL, 'Windows 10', 'Chrome/70.0.3538.110', '0:0:0:0:0:0:0:1', '2018-12-03 17:40:49', 'admin');
INSERT INTO `cf_common_syslog` VALUES (11, '修改用户', 'boot', 'org.coderfun.boot.web.controller.UserController.edit()', '{\r\n  \"id\" : 5,\r\n  \"name\" : \"test\",\r\n  \"gender\" : \"male\",\r\n  \"state\" : \"forbidden\",\r\n  \"description\" : \"111\"\r\n}', 383, 'yes', NULL, 'Windows 10', 'Chrome/70.0.3538.110', '0:0:0:0:0:0:0:1', '2018-12-03 18:05:59', 'admin');
INSERT INTO `cf_common_syslog` VALUES (12, '修改用户', 'boot', 'org.coderfun.boot.web.controller.UserController.edit()', '{\r\n  \"id\" : 5,\r\n  \"name\" : \"test\",\r\n  \"gender\" : \"male\",\r\n  \"state\" : \"forbidden\"\r\n}', 371, 'yes', NULL, 'Windows 10', 'Chrome/70.0.3538.110', '0:0:0:0:0:0:0:1', '2018-12-03 18:06:04', 'admin');
INSERT INTO `cf_common_syslog` VALUES (13, '修改用户', 'boot', 'org.coderfun.boot.web.controller.UserController.edit()', '{\r\n  \"id\" : 2,\r\n  \"name\" : \"klguang\",\r\n  \"gender\" : \"male\",\r\n  \"state\" : \"normal\"\r\n}', 372, 'yes', NULL, 'Windows 10', 'Chrome/70.0.3538.110', '0:0:0:0:0:0:0:1', '2018-12-03 18:06:08', 'admin');
INSERT INTO `cf_common_syslog` VALUES (14, '修改用户', 'boot', 'org.coderfun.boot.web.controller.UserController.edit()', '{\r\n  \"id\" : 1,\r\n  \"name\" : \"admin\",\r\n  \"birthday\" : \"2018-11-15T16:00:00.000+0000\",\r\n  \"gender\" : \"male\",\r\n  \"email\" : \"klguang@xxx.com\",\r\n  \"phone\" : \"13000000000\",\r\n  \"state\" : \"normal\"\r\n}', 378, 'yes', NULL, 'Windows 10', 'Chrome/70.0.3538.110', '0:0:0:0:0:0:0:1', '2018-12-03 18:06:12', 'admin');

SET FOREIGN_KEY_CHECKS = 1;
