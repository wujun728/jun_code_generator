CREATE DATABASE `jeasy` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `jeasy`;
--
-- 用户管理-用户
--
DROP TABLE IF EXISTS `su_user`;
CREATE TABLE `su_user` (
  `id` BIGINT(8) NOT NULL AUTO_INCREMENT COMMENT '主键',

  `name` VARCHAR(32) NOT NULL COMMENT '名称',
  `loginName` VARCHAR(64) NOT NULL COMMENT '登录名',
  `code` VARCHAR(64) DEFAULT '' COMMENT '编码',
  `pwd` VARCHAR(64) NOT NULL COMMENT '密码',
  `salt` VARCHAR(64) DEFAULT '' COMMENT '加密盐',
  `mobile` VARCHAR(11) DEFAULT '' COMMENT '手机号',

  `statusVal` INT(4) DEFAULT 0 COMMENT '用户状态值:1000=启用,1001=停用',
  `statusCode` VARCHAR(16) DEFAULT '' COMMENT '用户状态编码:字典',

  `remark` VARCHAR(64) DEFAULT '' COMMENT '备注',

  `createAt` BIGINT(8) DEFAULT 0 COMMENT '创建时间',
  `createBy` BIGINT(8) DEFAULT 0 COMMENT '创建人ID',
  `createName` VARCHAR(32) DEFAULT '' COMMENT '创建人名称',
  `updateAt` BIGINT(8) DEFAULT 0 COMMENT '更新时间',
  `updateBy` BIGINT(8) DEFAULT 0 COMMENT '更新人ID',
  `updateName` VARCHAR(32) DEFAULT '' COMMENT '更新人名称',
  `isDel` TINYINT(1) DEFAULT 0 COMMENT '是否删除',
  `isTest` TINYINT(1) DEFAULT 0 COMMENT '是否测试',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户';

--
-- 用户管理-用户角色
--
DROP TABLE IF EXISTS `su_user_role`;
CREATE TABLE `su_user_role` (
  `id` BIGINT(8) NOT NULL AUTO_INCREMENT COMMENT '主键',

  `userId` BIGINT(8) NOT NULL COMMENT '用户ID',
  `userName` VARCHAR(32) DEFAULT '' COMMENT '用户名称',
  `userCode` VARCHAR(64) DEFAULT '' COMMENT '用户编码',

  `roleId` BIGINT(8) NOT NULL COMMENT '角色ID',
  `roleName` VARCHAR(32) DEFAULT '' COMMENT '角色名称',
  `roleCode` VARCHAR(64) DEFAULT '' COMMENT '角色编码',

  `createAt` BIGINT(8) DEFAULT 0 COMMENT '创建时间',
  `createBy` BIGINT(8) DEFAULT 0 COMMENT '创建人ID',
  `createName` VARCHAR(32) DEFAULT '' COMMENT '创建人名称',
  `updateAt` BIGINT(8) DEFAULT 0 COMMENT '更新时间',
  `updateBy` BIGINT(8) DEFAULT 0 COMMENT '更新人ID',
  `updateName` VARCHAR(32) DEFAULT '' COMMENT '更新人名称',
  `isDel` TINYINT(1) DEFAULT 0 COMMENT '是否删除',
  `isTest` TINYINT(1) DEFAULT 0 COMMENT '是否测试',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色';

--
-- 用户管理-用户机构
--
DROP TABLE IF EXISTS `su_user_org`;
CREATE TABLE `su_user_org` (
  `id` BIGINT(8) NOT NULL AUTO_INCREMENT COMMENT '主键',

  `userId` BIGINT(8) NOT NULL COMMENT '用户ID',
  `userName` VARCHAR(32) DEFAULT '' COMMENT '用户名称',
  `userCode` VARCHAR(64) DEFAULT '' COMMENT '用户标示',

  `orgId` BIGINT(8) NOT NULL COMMENT '机构ID',
  `orgName` VARCHAR(32) DEFAULT '' COMMENT '机构名称',
  `orgCode` VARCHAR(64) DEFAULT '' COMMENT '机构编码',

  `createAt` BIGINT(8) DEFAULT 0 COMMENT '创建时间',
  `createBy` BIGINT(8) DEFAULT 0 COMMENT '创建人ID',
  `createName` VARCHAR(32) DEFAULT '' COMMENT '创建人名称',
  `updateAt` BIGINT(8) DEFAULT 0 COMMENT '更新时间',
  `updateBy` BIGINT(8) DEFAULT 0 COMMENT '更新人ID',
  `updateName` VARCHAR(32) DEFAULT '' COMMENT '更新人名称',
  `isDel` TINYINT(1) DEFAULT 0 COMMENT '是否删除',
  `isTest` TINYINT(1) DEFAULT 0 COMMENT '是否测试',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户机构';


--
-- 权限管理-角色
--
DROP TABLE IF EXISTS `su_role`;
CREATE TABLE `su_role` (
  `id` BIGINT(8) NOT NULL AUTO_INCREMENT COMMENT '主键',

  `name` VARCHAR(32) NOT NULL COMMENT '名称',
  `code` VARCHAR(64) DEFAULT '' COMMENT '编码',

  `remark` VARCHAR(64) DEFAULT '' COMMENT '备注',

  `createAt` BIGINT(8) DEFAULT 0 COMMENT '创建时间',
  `createBy` BIGINT(8) DEFAULT 0 COMMENT '创建人ID',
  `createName` VARCHAR(32) DEFAULT '' COMMENT '创建人名称',
  `updateAt` BIGINT(8) DEFAULT 0 COMMENT '更新时间',
  `updateBy` BIGINT(8) DEFAULT 0 COMMENT '更新人ID',
  `updateName` VARCHAR(32) DEFAULT '' COMMENT '更新人名称',
  `isDel` TINYINT(1) DEFAULT 0 COMMENT '是否删除',
  `isTest` TINYINT(1) DEFAULT 0 COMMENT '是否测试',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色';

--
-- 权限管理-资源(菜单&操作)
--
DROP TABLE IF EXISTS `su_resource`;
CREATE TABLE `su_resource` (
  `id` BIGINT(8) NOT NULL AUTO_INCREMENT COMMENT '主键',

  `name` VARCHAR(32) NOT NULL COMMENT '名称',
  `code` VARCHAR(32) DEFAULT '' COMMENT '编码',
  `url` VARCHAR(128) DEFAULT '' COMMENT 'URL',
  `icon` VARCHAR(64) DEFAULT '' COMMENT '图标',

  `remark` VARCHAR(64) DEFAULT '' COMMENT '备注/描述',

  `pid` BIGINT(8) DEFAULT 0 COMMENT '父ID',
  `sort` TINYINT(1) DEFAULT 0 COMMENT '排序',
  `isMenu` TINYINT(1) NOT NULL COMMENT '是否菜单:0=否,1=是',
  `isLeaf` TINYINT(1) NOT NULL COMMENT '是否叶子节点:0=否,1=是',

  `createAt` BIGINT(8) DEFAULT 0 COMMENT '创建时间',
  `createBy` BIGINT(8) DEFAULT 0 COMMENT '创建人ID',
  `createName` VARCHAR(32) DEFAULT '' COMMENT '创建人名称',
  `updateAt` BIGINT(8) DEFAULT 0 COMMENT '更新时间',
  `updateBy` BIGINT(8) DEFAULT 0 COMMENT '更新人ID',
  `updateName` VARCHAR(32) DEFAULT '' COMMENT '更新人名称',
  `isDel` TINYINT(1) DEFAULT 0 COMMENT '是否删除',
  `isTest` TINYINT(1) DEFAULT 0 COMMENT '是否测试',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=500 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='菜单';

--
-- 权限管理-角色资源
--
DROP TABLE IF EXISTS `su_role_resource`;
CREATE TABLE `su_role_resource` (
  `id` BIGINT(8) NOT NULL AUTO_INCREMENT COMMENT '主键',

  `roleId` BIGINT(8) NOT NULL COMMENT '角色ID',
  `roleName` VARCHAR(32) DEFAULT '' COMMENT '角色名称',
  `roleCode` VARCHAR(64) DEFAULT '' COMMENT '角色编码',

  `resourceId` BIGINT(8) NOT NULL COMMENT '资源ID',
  `resourceName` VARCHAR(32) DEFAULT '' COMMENT '资源名称',
  `resourceCode` VARCHAR(64) DEFAULT '' COMMENT '资源编码',

  `createAt` BIGINT(8) DEFAULT 0 COMMENT '创建时间',
  `createBy` BIGINT(8) DEFAULT 0 COMMENT '创建人ID',
  `createName` VARCHAR(32) DEFAULT '' COMMENT '创建人名称',
  `updateAt` BIGINT(8) DEFAULT 0 COMMENT '更新时间',
  `updateBy` BIGINT(8) DEFAULT 0 COMMENT '更新人ID',
  `updateName` VARCHAR(32) DEFAULT '' COMMENT '更新人名称',
  `isDel` TINYINT(1) DEFAULT 0 COMMENT '是否删除',
  `isTest` TINYINT(1) DEFAULT 0 COMMENT '是否测试',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=500 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色资源';

--
-- 权限管理-机构
--
DROP TABLE IF EXISTS `su_organization`;
CREATE TABLE `su_organization` (
  `id` BIGINT(8) NOT NULL AUTO_INCREMENT COMMENT '主键',

  `name` VARCHAR(32) NOT NULL COMMENT '名称',
  `code` VARCHAR(64) DEFAULT '' COMMENT '编码',
  `address` VARCHAR(128) DEFAULT '' COMMENT '地址',

  `typeVal` INT(4) DEFAULT 0 COMMENT '机构类型值',
  `typeCode` VARCHAR(16) DEFAULT '' COMMENT '机构类型编码:字典',

  `sort` TINYINT(1) DEFAULT 0 COMMENT '排序',
  `isLeaf` TINYINT(1) NOT NULL COMMENT '是否叶子节点:0=否,1=是',
  `pid` BIGINT(8) DEFAULT 0 COMMENT '父ID',
  `icon` VARCHAR(128) DEFAULT '' COMMENT '图标',

  `remark` VARCHAR(64) DEFAULT '' COMMENT '备注/描述',

  `createAt` BIGINT(8) DEFAULT 0 COMMENT '创建时间',
  `createBy` BIGINT(8) DEFAULT 0 COMMENT '创建人ID',
  `createName` VARCHAR(32) DEFAULT '' COMMENT '创建人名称',
  `updateAt` BIGINT(8) DEFAULT 0 COMMENT '更新时间',
  `updateBy` BIGINT(8) DEFAULT 0 COMMENT '更新人ID',
  `updateName` VARCHAR(32) DEFAULT '' COMMENT '更新人名称',
  `isDel` TINYINT(1) DEFAULT 0 COMMENT '是否删除',
  `isTest` TINYINT(1) DEFAULT 0 COMMENT '是否测试',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='机构';

--
-- 基础数据-日志
--
DROP TABLE IF EXISTS `bd_log`;
CREATE TABLE `bd_log` (
  `id` BIGINT(8) NOT NULL AUTO_INCREMENT COMMENT '主键',

  `tableName` VARCHAR(32) NOT NULL COMMENT '表名称',
  `recordId` BIGINT(8) NOT NULL COMMENT '记录ID',
  `fieldName` VARCHAR(128) DEFAULT '' COMMENT '字段名称',

  `logTypeVal` INT(4) DEFAULT 0 COMMENT '日志类型值',
  `logTypeCode` VARCHAR(16) DEFAULT '' COMMENT '日志类型编码:字典',

  `optTypeVal` INT(4) DEFAULT 0 COMMENT '操作类型值',
  `optTypeCode` VARCHAR(16) DEFAULT '' COMMENT '操作类型编码:字典',
  `optDesc` VARCHAR(128) DEFAULT '' COMMENT '操作类型描述',

  `beforeValue` VARCHAR(128) DEFAULT '' COMMENT '操作前值',
  `afterValue` VARCHAR(128) DEFAULT '' COMMENT '操作后值',

  `remark` VARCHAR(255) DEFAULT '' COMMENT '备注',

  `createAt` BIGINT(8) DEFAULT 0 COMMENT '创建时间',
  `createBy` BIGINT(8) DEFAULT 0 COMMENT '创建人ID',
  `createName` VARCHAR(32) DEFAULT '' COMMENT '创建人名称',
  `updateAt` BIGINT(8) DEFAULT 0 COMMENT '更新时间',
  `updateBy` BIGINT(8) DEFAULT 0 COMMENT '更新人ID',
  `updateName` VARCHAR(32) DEFAULT '' COMMENT '更新人名称',
  `isDel` TINYINT(1) DEFAULT 0 COMMENT '是否删除',
  `isTest` TINYINT(1) DEFAULT 0 COMMENT '是否测试',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='日志';

--
-- 基础数据-字典
--
DROP TABLE IF EXISTS `bd_dictionary`;
CREATE TABLE `bd_dictionary` (
  `id` BIGINT(8) NOT NULL AUTO_INCREMENT COMMENT '主键',

  `name` VARCHAR(32) NOT NULL COMMENT '名称',
  `code` VARCHAR(16) NOT NULL COMMENT '编号',
  `value` INT(4) NOT NULL COMMENT '值',

  `type` VARCHAR(16) DEFAULT '' COMMENT '类型',
  `typeName` VARCHAR(32) DEFAULT '' COMMENT '类型名称',
  `sort` TINYINT(1) DEFAULT 0 COMMENT '排序',

  `pid` BIGINT(8) DEFAULT 0 COMMENT '父ID',
  `pcode` VARCHAR(16) DEFAULT '' COMMENT '父编号',

  `createAt` BIGINT(8) DEFAULT 0 COMMENT '创建时间',
  `createBy` BIGINT(8) DEFAULT 0 COMMENT '创建人ID',
  `createName` VARCHAR(32) DEFAULT '' COMMENT '创建人名称',
  `updateAt` BIGINT(8) DEFAULT 0 COMMENT '更新时间',
  `updateBy` BIGINT(8) DEFAULT 0 COMMENT '更新人ID',
  `updateName` VARCHAR(32) DEFAULT '' COMMENT '更新人名称',
  `isDel` TINYINT(1) DEFAULT 0 COMMENT '是否删除',
  `isTest` TINYINT(1) DEFAULT 0 COMMENT '是否测试',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=500 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='字典';

--
-- 基础数据-文件附件
--
DROP TABLE IF EXISTS `bd_file_attach`;
CREATE TABLE `bd_file_attach` (
  `id` BIGINT(8) NOT NULL AUTO_INCREMENT COMMENT '主键',

  `tableName` VARCHAR(32) NOT NULL COMMENT '表名称',
  `recordId` BIGINT(8) NOT NULL COMMENT '记录ID',

  `name` VARCHAR(64) DEFAULT '' COMMENT '文件原名称',
  `url` VARCHAR(255) DEFAULT '' COMMENT '文件URL',
  `iconUrl` VARCHAR(255) DEFAULT '' COMMENT '文件图标URL',
  `previewUrl` VARCHAR(255) DEFAULT '' COMMENT '文件预览URL',

  `createAt` BIGINT(8) DEFAULT 0 COMMENT '创建时间',
  `createBy` BIGINT(8) DEFAULT 0 COMMENT '创建人ID',
  `createName` VARCHAR(32) DEFAULT '' COMMENT '创建人名称',
  `updateAt` BIGINT(8) DEFAULT 0 COMMENT '更新时间',
  `updateBy` BIGINT(8) DEFAULT 0 COMMENT '更新人ID',
  `updateName` VARCHAR(32) DEFAULT '' COMMENT '更新人名称',
  `isDel` TINYINT(1) DEFAULT 0 COMMENT '是否删除',
  `isTest` TINYINT(1) DEFAULT 0 COMMENT '是否测试',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文件附件';

--
-- 初始化:字典
--
TRUNCATE `bd_dictionary`;
INSERT INTO `bd_dictionary` (`id`, `name`, `code`, `value`, `type`, `typeName`, `sort`, `pid`, `pcode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (1, '启用', 'QY', 1000, 'YHZT', '用户状态', 0, 0, '', 1513563952466, 0, 'SYSTEM', 1513563952466, 0, 'SYSTEM', 0, 0);
INSERT INTO `bd_dictionary` (`id`, `name`, `code`, `value`, `type`, `typeName`, `sort`, `pid`, `pcode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (2, '停用', 'TY', 1001, 'YHZT', '用户状态', 0, 0, '', 1513563952490, 0, 'SYSTEM', 1513563952490, 0, 'SYSTEM', 0, 0);
INSERT INTO `bd_dictionary` (`id`, `name`, `code`, `value`, `type`, `typeName`, `sort`, `pid`, `pcode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (3, '其他', 'QT', 2000, 'JGLX', '机构类型', 0, 0, '', 1513563952491, 0, 'SYSTEM', 1513563952491, 0, 'SYSTEM', 0, 0);
INSERT INTO `bd_dictionary` (`id`, `name`, `code`, `value`, `type`, `typeName`, `sort`, `pid`, `pcode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (4, '登录登出', 'DLDC', 3000, 'RZLX', '日志类型', 0, 0, '', 1513563952491, 0, 'SYSTEM', 1513563952491, 0, 'SYSTEM', 0, 0);
INSERT INTO `bd_dictionary` (`id`, `name`, `code`, `value`, `type`, `typeName`, `sort`, `pid`, `pcode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (5, '登录', 'DL', 3001, 'CZLX', '操作类型', 0, 4, '', 1513563952491, 0, 'SYSTEM', 1513563952491, 0, 'SYSTEM', 0, 0);
INSERT INTO `bd_dictionary` (`id`, `name`, `code`, `value`, `type`, `typeName`, `sort`, `pid`, `pcode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (6, '登出', 'DC', 3002, 'CZLX', '操作类型', 0, 4, '', 1513563952491, 0, 'SYSTEM', 1513563952491, 0, 'SYSTEM', 0, 0);
INSERT INTO `bd_dictionary` (`id`, `name`, `code`, `value`, `type`, `typeName`, `sort`, `pid`, `pcode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (7, '启用停用', 'QYTY', 4000, 'RZLX', '日志类型', 0, 0, '', 1513563952491, 0, 'SYSTEM', 1513563952491, 0, 'SYSTEM', 0, 0);
INSERT INTO `bd_dictionary` (`id`, `name`, `code`, `value`, `type`, `typeName`, `sort`, `pid`, `pcode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (8, '启用', 'QY', 4001, 'CZLX', '操作类型', 0, 7, '', 1513563952492, 0, 'SYSTEM', 1513563952492, 0, 'SYSTEM', 0, 0);
INSERT INTO `bd_dictionary` (`id`, `name`, `code`, `value`, `type`, `typeName`, `sort`, `pid`, `pcode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (9, '停用', 'TY', 4002, 'CZLX', '操作类型', 0, 7, '', 1513563952492, 0, 'SYSTEM', 1513563952492, 0, 'SYSTEM', 0, 0);

--
-- 初始化:菜单资源+角色资源
--
TRUNCATE `su_resource`;
TRUNCATE `su_role_resource`;
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (1, '用户管理', 'YHGL', '', 'ios-people', '', 0, 0, 1, 0, 1513146159132, 0, 'SYSTEM', 1513146159132, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (1, 1, '超级管理员', 'CJGLY', 1, '用户管理', 'YHGL', 1513146159148, 0, 'SYSTEM', 1513146159148, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (2, '人员管理', 'YHGL:RYGL', '/user', 'ios-body', '', 1, 0, 1, 1, 1513146159148, 0, 'SYSTEM', 1513146159148, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (2, 1, '超级管理员', 'CJGLY', 2, '人员管理', 'YHGL:RYGL', 1513146159148, 0, 'SYSTEM', 1513146159148, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (3, '查询', 'YHGL:RYGL:CX', '', '', '', 2, 0, 0, 1, 1513146159149, 0, 'SYSTEM', 1513146159149, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (3, 1, '超级管理员', 'CJGLY', 3, '查询', 'YHGL:RYGL:CX', 1513146159149, 0, 'SYSTEM', 1513146159149, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (4, '查看', 'YHGL:RYGL:CK', '', '', '', 2, 0, 0, 1, 1513146159149, 0, 'SYSTEM', 1513146159149, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (4, 1, '超级管理员', 'CJGLY', 4, '查看', 'YHGL:RYGL:CK', 1513146159149, 0, 'SYSTEM', 1513146159149, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (5, '新增', 'YHGL:RYGL:XZ', '', '', '', 2, 0, 0, 1, 1513146159149, 0, 'SYSTEM', 1513146159149, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (5, 1, '超级管理员', 'CJGLY', 5, '新增', 'YHGL:RYGL:XZ', 1513146159149, 0, 'SYSTEM', 1513146159149, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (6, '编辑', 'YHGL:RYGL:BJ', '', '', '', 2, 0, 0, 1, 1513146159150, 0, 'SYSTEM', 1513146159150, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (6, 1, '超级管理员', 'CJGLY', 6, '编辑', 'YHGL:RYGL:BJ', 1513146159150, 0, 'SYSTEM', 1513146159150, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (7, '删除', 'YHGL:RYGL:SC', '', '', '', 2, 0, 0, 1, 1513146159150, 0, 'SYSTEM', 1513146159150, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (7, 1, '超级管理员', 'CJGLY', 7, '删除', 'YHGL:RYGL:SC', 1513146159150, 0, 'SYSTEM', 1513146159150, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (8, '角色配置', 'YHGL:RYGL:JSPZ', '', '', '', 2, 0, 0, 1, 1513146159150, 0, 'SYSTEM', 1513146159150, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (8, 1, '超级管理员', 'CJGLY', 8, '角色配置', 'YHGL:RYGL:JSPZ', 1513146159151, 0, 'SYSTEM', 1513146159151, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (9, '机构配置', 'YHGL:RYGL:JGPZ', '', '', '', 2, 0, 0, 1, 1513146159151, 0, 'SYSTEM', 1513146159151, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (9, 1, '超级管理员', 'CJGLY', 9, '机构配置', 'YHGL:RYGL:JGPZ', 1513146159151, 0, 'SYSTEM', 1513146159151, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (10, '角色管理', 'YHGL:JSGL', '/role', 'ios-person', '', 1, 0, 1, 1, 1513146159151, 0, 'SYSTEM', 1513146159151, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (10, 1, '超级管理员', 'CJGLY', 10, '角色管理', 'YHGL:JSGL', 1513146159151, 0, 'SYSTEM', 1513146159151, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (11, '查询', 'YHGL:JSGL:CX', '', '', '', 10, 0, 0, 1, 1513146159152, 0, 'SYSTEM', 1513146159152, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (11, 1, '超级管理员', 'CJGLY', 11, '查询', 'YHGL:JSGL:CX', 1513146159152, 0, 'SYSTEM', 1513146159152, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (12, '查看', 'YHGL:JSGL:CK', '', '', '', 10, 0, 0, 1, 1513146159152, 0, 'SYSTEM', 1513146159152, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (12, 1, '超级管理员', 'CJGLY', 12, '查看', 'YHGL:JSGL:CK', 1513146159152, 0, 'SYSTEM', 1513146159152, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (13, '新增', 'YHGL:JSGL:XZ', '', '', '', 10, 0, 0, 1, 1513146159152, 0, 'SYSTEM', 1513146159152, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (13, 1, '超级管理员', 'CJGLY', 13, '新增', 'YHGL:JSGL:XZ', 1513146159153, 0, 'SYSTEM', 1513146159153, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (14, '编辑', 'YHGL:JSGL:BJ', '', '', '', 10, 0, 0, 1, 1513146159153, 0, 'SYSTEM', 1513146159153, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (14, 1, '超级管理员', 'CJGLY', 14, '编辑', 'YHGL:JSGL:BJ', 1513146159153, 0, 'SYSTEM', 1513146159153, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (15, '删除', 'YHGL:JSGL:SC', '', '', '', 10, 0, 0, 1, 1513146159153, 0, 'SYSTEM', 1513146159153, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (15, 1, '超级管理员', 'CJGLY', 15, '删除', 'YHGL:JSGL:SC', 1513146159153, 0, 'SYSTEM', 1513146159153, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (16, '权限配置', 'YHGL:JSGL:QXPZ', '', '', '', 10, 0, 0, 1, 1513146159154, 0, 'SYSTEM', 1513146159154, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (16, 1, '超级管理员', 'CJGLY', 16, '权限配置', 'YHGL:JSGL:QXPZ', 1513146159154, 0, 'SYSTEM', 1513146159154, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (17, '组织机构', 'YHGL:ZZJG', '/organization', 'ios-people', '', 1, 0, 1, 1, 1513146159154, 0, 'SYSTEM', 1513146159154, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (17, 1, '超级管理员', 'CJGLY', 17, '组织机构', 'YHGL:ZZJG', 1513146159154, 0, 'SYSTEM', 1513146159154, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (18, '查询', 'YHGL:ZZJG:CX', '', '', '', 17, 0, 0, 1, 1513146159154, 0, 'SYSTEM', 1513146159154, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (18, 1, '超级管理员', 'CJGLY', 18, '查询', 'YHGL:ZZJG:CX', 1513146159154, 0, 'SYSTEM', 1513146159154, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (19, '查看', 'YHGL:ZZJG:CK', '', '', '', 17, 0, 0, 1, 1513146159155, 0, 'SYSTEM', 1513146159155, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (19, 1, '超级管理员', 'CJGLY', 19, '查看', 'YHGL:ZZJG:CK', 1513146159155, 0, 'SYSTEM', 1513146159155, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (20, '新增', 'YHGL:ZZJG:XZ', '', '', '', 17, 0, 0, 1, 1513146159155, 0, 'SYSTEM', 1513146159155, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (20, 1, '超级管理员', 'CJGLY', 20, '新增', 'YHGL:ZZJG:XZ', 1513146159155, 0, 'SYSTEM', 1513146159155, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (21, '编辑', 'YHGL:ZZJG:BJ', '', '', '', 17, 0, 0, 1, 1513146159155, 0, 'SYSTEM', 1513146159155, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (21, 1, '超级管理员', 'CJGLY', 21, '编辑', 'YHGL:ZZJG:BJ', 1513146159156, 0, 'SYSTEM', 1513146159156, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (22, '删除', 'YHGL:ZZJG:SC', '', '', '', 17, 0, 0, 1, 1513146159156, 0, 'SYSTEM', 1513146159156, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (22, 1, '超级管理员', 'CJGLY', 22, '删除', 'YHGL:ZZJG:SC', 1513146159156, 0, 'SYSTEM', 1513146159156, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (23, '菜单资源', 'YHGL:CDZY', '/resource', 'android-menu', '', 1, 0, 1, 1, 1513146159156, 0, 'SYSTEM', 1513146159156, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (23, 1, '超级管理员', 'CJGLY', 23, '菜单资源', 'YHGL:CDZY', 1513146159156, 0, 'SYSTEM', 1513146159156, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (24, '查询', 'YHGL:CDZY:CX', '', '', '', 23, 0, 0, 1, 1513146159157, 0, 'SYSTEM', 1513146159157, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (24, 1, '超级管理员', 'CJGLY', 24, '查询', 'YHGL:CDZY:CX', 1513146159157, 0, 'SYSTEM', 1513146159157, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (25, '查看', 'YHGL:CDZY:CK', '', '', '', 23, 0, 0, 1, 1513146159157, 0, 'SYSTEM', 1513146159157, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (25, 1, '超级管理员', 'CJGLY', 25, '查看', 'YHGL:CDZY:CK', 1513146159157, 0, 'SYSTEM', 1513146159157, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (26, '新增', 'YHGL:CDZY:XZ', '', '', '', 23, 0, 0, 1, 1513146159157, 0, 'SYSTEM', 1513146159157, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (26, 1, '超级管理员', 'CJGLY', 26, '新增', 'YHGL:CDZY:XZ', 1513146159158, 0, 'SYSTEM', 1513146159158, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (27, '编辑', 'YHGL:CDZY:BJ', '', '', '', 23, 0, 0, 1, 1513146159158, 0, 'SYSTEM', 1513146159158, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (27, 1, '超级管理员', 'CJGLY', 27, '编辑', 'YHGL:CDZY:BJ', 1513146159158, 0, 'SYSTEM', 1513146159158, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (28, '删除', 'YHGL:CDZY:SC', '', '', '', 23, 0, 0, 1, 1513146159158, 0, 'SYSTEM', 1513146159158, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (28, 1, '超级管理员', 'CJGLY', 28, '删除', 'YHGL:CDZY:SC', 1513146159158, 0, 'SYSTEM', 1513146159158, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (29, '基础数据', 'JCSJ', '', 'settings', '', 0, 0, 1, 0, 1513146159158, 0, 'SYSTEM', 1513146159158, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (29, 1, '超级管理员', 'CJGLY', 29, '基础数据', 'JCSJ', 1513146159159, 0, 'SYSTEM', 1513146159159, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (30, '公共码表', 'JCSJ:GGMB', '/dictionary', 'ios-book', '', 29, 0, 1, 1, 1513146159159, 0, 'SYSTEM', 1513146159159, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (30, 1, '超级管理员', 'CJGLY', 30, '公共码表', 'JCSJ:GGMB', 1513146159159, 0, 'SYSTEM', 1513146159159, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (31, '查询', 'JCSJ:GGMB:CX', '', '', '', 30, 0, 0, 1, 1513146159159, 0, 'SYSTEM', 1513146159159, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (31, 1, '超级管理员', 'CJGLY', 31, '查询', 'JCSJ:GGMB:CX', 1513146159160, 0, 'SYSTEM', 1513146159160, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (32, '查看', 'JCSJ:GGMB:CK', '', '', '', 30, 0, 0, 1, 1513146159160, 0, 'SYSTEM', 1513146159160, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (32, 1, '超级管理员', 'CJGLY', 32, '查看', 'JCSJ:GGMB:CK', 1513146159160, 0, 'SYSTEM', 1513146159160, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (33, '新增', 'JCSJ:GGMB:XZ', '', '', '', 30, 0, 0, 1, 1513146159161, 0, 'SYSTEM', 1513146159161, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (33, 1, '超级管理员', 'CJGLY', 33, '新增', 'JCSJ:GGMB:XZ', 1513146159161, 0, 'SYSTEM', 1513146159161, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (34, '编辑', 'JCSJ:GGMB:BJ', '', '', '', 30, 0, 0, 1, 1513146159161, 0, 'SYSTEM', 1513146159161, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (34, 1, '超级管理员', 'CJGLY', 34, '编辑', 'JCSJ:GGMB:BJ', 1513146159161, 0, 'SYSTEM', 1513146159161, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (35, '删除', 'JCSJ:GGMB:SC', '', '', '', 30, 0, 0, 1, 1513146159162, 0, 'SYSTEM', 1513146159162, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (35, 1, '超级管理员', 'CJGLY', 35, '删除', 'JCSJ:GGMB:SC', 1513146159162, 0, 'SYSTEM', 1513146159162, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (36, '文件管理', 'JCSJ:WJGL', '/fileAttach', 'ios-folder', '', 29, 0, 1, 1, 1513146159162, 0, 'SYSTEM', 1513146159162, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (36, 1, '超级管理员', 'CJGLY', 36, '文件管理', 'JCSJ:WJGL', 1513146159162, 0, 'SYSTEM', 1513146159162, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (37, '查询', 'JCSJ:WJGL:CX', '', '', '', 36, 0, 0, 1, 1513146159163, 0, 'SYSTEM', 1513146159163, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (37, 1, '超级管理员', 'CJGLY', 37, '查询', 'JCSJ:WJGL:CX', 1513146159163, 0, 'SYSTEM', 1513146159163, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (38, '查看', 'JCSJ:WJGL:CK', '', '', '', 36, 0, 0, 1, 1513146159163, 0, 'SYSTEM', 1513146159163, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (38, 1, '超级管理员', 'CJGLY', 38, '查看', 'JCSJ:WJGL:CK', 1513146159163, 0, 'SYSTEM', 1513146159163, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (39, '新增', 'JCSJ:WJGL:XZ', '', '', '', 36, 0, 0, 1, 1513146159164, 0, 'SYSTEM', 1513146159164, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (39, 1, '超级管理员', 'CJGLY', 39, '新增', 'JCSJ:WJGL:XZ', 1513146159164, 0, 'SYSTEM', 1513146159164, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (40, '编辑', 'JCSJ:WJGL:BJ', '', '', '', 36, 0, 0, 1, 1513146159164, 0, 'SYSTEM', 1513146159164, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (40, 1, '超级管理员', 'CJGLY', 40, '编辑', 'JCSJ:WJGL:BJ', 1513146159164, 0, 'SYSTEM', 1513146159164, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (41, '删除', 'JCSJ:WJGL:SC', '', '', '', 36, 0, 0, 1, 1513146159165, 0, 'SYSTEM', 1513146159165, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (41, 1, '超级管理员', 'CJGLY', 41, '删除', 'JCSJ:WJGL:SC', 1513146159165, 0, 'SYSTEM', 1513146159165, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (42, '代码平台', 'DMPT', '', 'code-working', '', 0, 0, 1, 0, 1513146159165, 0, 'SYSTEM', 1513146159165, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (42, 1, '超级管理员', 'CJGLY', 42, '代码平台', 'DMPT', 1513146159165, 0, 'SYSTEM', 1513146159165, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (43, '接口文档', 'DMPT:JKWD', '/api', 'usb', '', 42, 0, 1, 1, 1513146159165, 0, 'SYSTEM', 1513146159165, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (43, 1, '超级管理员', 'CJGLY', 43, '接口文档', 'DMPT:JKWD', 1513146159165, 0, 'SYSTEM', 1513146159165, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (44, '查询', 'DMPT:JKWD:CX', '', '', '', 43, 0, 0, 1, 1513146159165, 0, 'SYSTEM', 1513146159165, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (44, 1, '超级管理员', 'CJGLY', 44, '查询', 'DMPT:JKWD:CX', 1513146159166, 0, 'SYSTEM', 1513146159166, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (45, '查看', 'DMPT:JKWD:CK', '', '', '', 43, 0, 0, 1, 1513146159166, 0, 'SYSTEM', 1513146159166, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (45, 1, '超级管理员', 'CJGLY', 45, '查看', 'DMPT:JKWD:CK', 1513146159166, 0, 'SYSTEM', 1513146159166, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (46, '新增', 'DMPT:JKWD:XZ', '', '', '', 43, 0, 0, 1, 1513146159166, 0, 'SYSTEM', 1513146159166, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (46, 1, '超级管理员', 'CJGLY', 46, '新增', 'DMPT:JKWD:XZ', 1513146159167, 0, 'SYSTEM', 1513146159167, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (47, '编辑', 'DMPT:JKWD:BJ', '', '', '', 43, 0, 0, 1, 1513146159167, 0, 'SYSTEM', 1513146159167, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (47, 1, '超级管理员', 'CJGLY', 47, '编辑', 'DMPT:JKWD:BJ', 1513146159167, 0, 'SYSTEM', 1513146159167, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (48, '删除', 'DMPT:JKWD:SC', '', '', '', 43, 0, 0, 1, 1513146159167, 0, 'SYSTEM', 1513146159167, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (48, 1, '超级管理员', 'CJGLY', 48, '删除', 'DMPT:JKWD:SC', 1513146159167, 0, 'SYSTEM', 1513146159167, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (49, '代码生成', 'DMPT:DMSC', '/code', 'code-download', '', 42, 0, 1, 1, 1513146159167, 0, 'SYSTEM', 1513146159167, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (49, 1, '超级管理员', 'CJGLY', 49, '代码生成', 'DMPT:DMSC', 1513146159167, 0, 'SYSTEM', 1513146159167, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (50, '查询', 'DMPT:DMSC:CX', '', '', '', 49, 0, 0, 1, 1513146159168, 0, 'SYSTEM', 1513146159168, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (50, 1, '超级管理员', 'CJGLY', 50, '查询', 'DMPT:DMSC:CX', 1513146159168, 0, 'SYSTEM', 1513146159168, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (51, '查看', 'DMPT:DMSC:CK', '', '', '', 49, 0, 0, 1, 1513146159168, 0, 'SYSTEM', 1513146159168, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (51, 1, '超级管理员', 'CJGLY', 51, '查看', 'DMPT:DMSC:CK', 1513146159168, 0, 'SYSTEM', 1513146159168, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (52, '新增', 'DMPT:DMSC:XZ', '', '', '', 49, 0, 0, 1, 1513146159168, 0, 'SYSTEM', 1513146159168, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (52, 1, '超级管理员', 'CJGLY', 52, '新增', 'DMPT:DMSC:XZ', 1513146159168, 0, 'SYSTEM', 1513146159168, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (53, '编辑', 'DMPT:DMSC:BJ', '', '', '', 49, 0, 0, 1, 1513146159169, 0, 'SYSTEM', 1513146159169, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (53, 1, '超级管理员', 'CJGLY', 53, '编辑', 'DMPT:DMSC:BJ', 1513146159169, 0, 'SYSTEM', 1513146159169, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (54, '删除', 'DMPT:DMSC:SC', '', '', '', 49, 0, 0, 1, 1513146159169, 0, 'SYSTEM', 1513146159169, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (54, 1, '超级管理员', 'CJGLY', 54, '删除', 'DMPT:DMSC:SC', 1513146159169, 0, 'SYSTEM', 1513146159169, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (55, '日志监控', 'RZJK', '', 'monitor', '', 0, 0, 1, 0, 1513146159169, 0, 'SYSTEM', 1513146159169, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (55, 1, '超级管理员', 'CJGLY', 55, '日志监控', 'RZJK', 1513146159169, 0, 'SYSTEM', 1513146159169, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (56, '操作日志', 'RZJK:CZRZ', '/log', 'ios-paper', '', 55, 0, 1, 1, 1513146159169, 0, 'SYSTEM', 1513146159169, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (56, 1, '超级管理员', 'CJGLY', 56, '操作日志', 'RZJK:CZRZ', 1513146159170, 0, 'SYSTEM', 1513146159170, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (57, '查询', 'RZJK:CZRZ:CX', '', '', '', 56, 0, 0, 1, 1513146159170, 0, 'SYSTEM', 1513146159170, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (57, 1, '超级管理员', 'CJGLY', 57, '查询', 'RZJK:CZRZ:CX', 1513146159170, 0, 'SYSTEM', 1513146159170, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (58, '查看', 'RZJK:CZRZ:CK', '', '', '', 56, 0, 0, 1, 1513146159170, 0, 'SYSTEM', 1513146159170, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (58, 1, '超级管理员', 'CJGLY', 58, '查看', 'RZJK:CZRZ:CK', 1513146159170, 0, 'SYSTEM', 1513146159170, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (59, '新增', 'RZJK:CZRZ:XZ', '', '', '', 56, 0, 0, 1, 1513146159170, 0, 'SYSTEM', 1513146159170, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (59, 1, '超级管理员', 'CJGLY', 59, '新增', 'RZJK:CZRZ:XZ', 1513146159171, 0, 'SYSTEM', 1513146159171, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (60, '编辑', 'RZJK:CZRZ:BJ', '', '', '', 56, 0, 0, 1, 1513146159171, 0, 'SYSTEM', 1513146159171, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (60, 1, '超级管理员', 'CJGLY', 60, '编辑', 'RZJK:CZRZ:BJ', 1513146159171, 0, 'SYSTEM', 1513146159171, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (61, '删除', 'RZJK:CZRZ:SC', '', '', '', 56, 0, 0, 1, 1513146159171, 0, 'SYSTEM', 1513146159171, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (61, 1, '超级管理员', 'CJGLY', 61, '删除', 'RZJK:CZRZ:SC', 1513146159171, 0, 'SYSTEM', 1513146159171, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (62, '数据监控', 'RZJK:SJJK', '/druid', 'ios-analytics', '', 55, 0, 1, 1, 1513146159172, 0, 'SYSTEM', 1513146159172, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (62, 1, '超级管理员', 'CJGLY', 62, '数据监控', 'RZJK:SJJK', 1513146159172, 0, 'SYSTEM', 1513146159172, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (63, '查询', 'RZJK:SJJK:CX', '', '', '', 62, 0, 0, 1, 1513146159172, 0, 'SYSTEM', 1513146159172, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (63, 1, '超级管理员', 'CJGLY', 63, '查询', 'RZJK:SJJK:CX', 1513146159172, 0, 'SYSTEM', 1513146159172, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (64, '查看', 'RZJK:SJJK:CK', '', '', '', 62, 0, 0, 1, 1513146159172, 0, 'SYSTEM', 1513146159172, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (64, 1, '超级管理员', 'CJGLY', 64, '查看', 'RZJK:SJJK:CK', 1513146159172, 0, 'SYSTEM', 1513146159172, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (65, '新增', 'RZJK:SJJK:XZ', '', '', '', 62, 0, 0, 1, 1513146159173, 0, 'SYSTEM', 1513146159173, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (65, 1, '超级管理员', 'CJGLY', 65, '新增', 'RZJK:SJJK:XZ', 1513146159173, 0, 'SYSTEM', 1513146159173, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (66, '编辑', 'RZJK:SJJK:BJ', '', '', '', 62, 0, 0, 1, 1513146159173, 0, 'SYSTEM', 1513146159173, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (66, 1, '超级管理员', 'CJGLY', 66, '编辑', 'RZJK:SJJK:BJ', 1513146159173, 0, 'SYSTEM', 1513146159173, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (67, '删除', 'RZJK:SJJK:SC', '', '', '', 62, 0, 0, 1, 1513146159173, 0, 'SYSTEM', 1513146159173, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (67, 1, '超级管理员', 'CJGLY', 67, '删除', 'RZJK:SJJK:SC', 1513146159174, 0, 'SYSTEM', 1513146159174, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (68, '接口监控', 'RZJK:JKJK', '/monitor', 'usb', '', 55, 0, 1, 1, 1513146159174, 0, 'SYSTEM', 1513146159174, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (68, 1, '超级管理员', 'CJGLY', 68, '接口监控', 'RZJK:JKJK', 1513146159174, 0, 'SYSTEM', 1513146159174, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (69, '查询', 'RZJK:JKJK:CX', '', '', '', 68, 0, 0, 1, 1513146159174, 0, 'SYSTEM', 1513146159174, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (69, 1, '超级管理员', 'CJGLY', 69, '查询', 'RZJK:JKJK:CX', 1513146159174, 0, 'SYSTEM', 1513146159174, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (70, '查看', 'RZJK:JKJK:CK', '', '', '', 68, 0, 0, 1, 1513146159174, 0, 'SYSTEM', 1513146159174, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (70, 1, '超级管理员', 'CJGLY', 70, '查看', 'RZJK:JKJK:CK', 1513146159174, 0, 'SYSTEM', 1513146159174, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (71, '新增', 'RZJK:JKJK:XZ', '', '', '', 68, 0, 0, 1, 1513146159175, 0, 'SYSTEM', 1513146159175, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (71, 1, '超级管理员', 'CJGLY', 71, '新增', 'RZJK:JKJK:XZ', 1513146159175, 0, 'SYSTEM', 1513146159175, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (72, '编辑', 'RZJK:JKJK:BJ', '', '', '', 68, 0, 0, 1, 1513146159175, 0, 'SYSTEM', 1513146159175, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (72, 1, '超级管理员', 'CJGLY', 72, '编辑', 'RZJK:JKJK:BJ', 1513146159175, 0, 'SYSTEM', 1513146159175, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_resource` (`id`, `name`, `code`, `url`, `icon`, `remark`, `pid`, `sort`, `isMenu`, `isLeaf`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (73, '删除', 'RZJK:JKJK:SC', '', '', '', 68, 0, 0, 1, 1513146159175, 0, 'SYSTEM', 1513146159175, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_role_resource` (`id`, `roleId`, `roleName`, `roleCode`, `resourceId`, `resourceName`, `resourceCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES (73, 1, '超级管理员', 'CJGLY', 73, '删除', 'RZJK:JKJK:SC', 1513146159175, 0, 'SYSTEM', 1513146159175, 0, 'SYSTEM', 0, 0);

--
-- 初始化:角色
--
TRUNCATE `su_role`;
INSERT INTO `su_role` (`id`, `name`, `code`, `remark`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`)
VALUES (1, '超级管理员', 'CJGLY', '谨慎操作', 1491805287470, 0, 'SYSTEM', 1491805287470, 0, 'SYSTEM', 0, 0);

--
-- 初始化:机构
--
TRUNCATE `su_organization`;
INSERT INTO `su_organization` (`id`, `name`, `code`, `typeVal`, `typeCode`, `sort`, `isLeaf`, `pid`, `remark`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES
  (1, '超级管理组', 'CJGLZ', 2000, 'QT', 0, 1, 0, '谨慎操作',1491805287470, 0, 'SYSTEM', 1491805287470, 0, 'SYSTEM', 0, 0);

--
-- 初始化:用户+用户机构+用户角色
--
TRUNCATE `su_user`;
INSERT INTO `su_user` (`id`, `name`, `loginName`, `code`, `pwd`, `salt`, `mobile`, `statusVal`, `statusCode`,  `remark`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES
  (1, 'admin', 'admin', 'ADMIN', '7d61f71f34b0305aabc5d1cdd9d2a777', 'abc', '13011111111', 1000, 'QY', '谨慎操作', 1491805287470, 0, 'SYSTEM', 1491805287470, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_user` (`id`, `name`, `loginName`, `code`, `pwd`, `salt`, `mobile`, `statusVal`, `statusCode`,  `remark`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES
  (2, 'super_admin', 'super_admin', 'SUPER_ADMIN', '7d61f71f34b0305aabc5d1cdd9d2a777', 'abc', '13022222222', 1000, 'QY', '谨慎操作', 1491805287470, 0, 'SYSTEM', 1491805287470, 0, 'SYSTEM', 0, 0);

TRUNCATE `su_user_org`;
INSERT INTO `su_user_org` (`id`, `userId`, `userName`, `userCode`, `orgId`, `orgName`, `orgCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES
  (1, 1, 'admin', 'ADMIN', 1, '超级管理组', 'CJGLZ', 1491805287470, 0, 'SYSTEM', 1491805287470, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_user_org` (`id`, `userId`, `userName`, `userCode`, `orgId`, `orgName`, `orgCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES
  (2, 2, 'super_admin', 'SUPER_ADMIN', 1, '超级管理组', 'CJGLZ', 1491805287470, 0, 'SYSTEM', 1491805287470, 0, 'SYSTEM', 0, 0);

TRUNCATE `su_user_role`;
INSERT INTO `su_user_role` (`id`, `userId`, `userName`, `userCode`, `roleId`, `roleName`, `roleCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES
  (1, 1, 'admin', 'ADMIN', 1, '超级管理员', 'CJGLY', 1491805287470, 0, 'SYSTEM', 1491805287470, 0, 'SYSTEM', 0, 0);
INSERT INTO `su_user_role` (`id`, `userId`, `userName`, `userCode`, `roleId`, `roleName`, `roleCode`, `createAt`, `createBy`, `createName`, `updateAt`, `updateBy`, `updateName`, `isDel`, `isTest`) VALUES
  (2, 2, 'super_admin', 'SUPER_ADMIN', 1, '超级管理员', 'CJGLY', 1491805287470, 0, 'SYSTEM', 1491805287470, 0, 'SYSTEM', 0, 0);
