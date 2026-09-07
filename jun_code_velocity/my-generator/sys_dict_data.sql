/*
 Navicat Premium Dump SQL

 Source Server         : localhost_3307
 Source Server Type    : MySQL
 Source Server Version : 80012 (8.0.12)
 Source Host           : localhost:3307
 Source Schema         : db_generator

 Target Server Type    : MySQL
 Target Server Version : 80012 (8.0.12)
 File Encoding         : 65001

 Date: 22/06/2026 16:36:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `dict_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典编码',
  `dict_sort` int(4) NULL DEFAULT 0 COMMENT '字典排序',
  `dict_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES ('028B02676BDD44ACB531BC2BBC2AA2B1', 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', '0', 'superAdmin', '2024-07-17 15:19:23', '', NULL, '系统默认是');
INSERT INTO `sys_dict_data` VALUES ('028B02676BDD44ACB531BC2BBC2AA2B2', 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', '0', 'superAdmin', '2024-07-17 15:19:23', '', NULL, '系统默认否');
INSERT INTO `sys_dict_data` VALUES ('028B02676BDD44ACB531BC2BBC2AA2B3', 0, '请假流程', 'leave', 'sys_process_category', NULL, 'default', 'N', '0', 'superAdmin', '2022-12-12 11:17:56', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('028B02676BDD44ACB531BC2BBC2AA2B4', 0, 'OA流程', 'oa', 'sys_process_category', NULL, 'default', 'N', '0', 'superAdmin', '2022-12-12 11:18:08', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('028B02676BDD44ACB531BC2BBC2AA2B5', 0, '任务监听', '1', 'sys_listener_type', NULL, 'default', 'N', '0', 'superAdmin', '2022-12-25 11:47:26', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('028B02676BDD44ACB531BC2BBC2AA2B6', 1, '公司公告', '0', 'sys_notice_type', '', 'default', 'Y', '0', 'superAdmin', '2024-07-17 15:19:23', '', NULL, '通知');
INSERT INTO `sys_dict_data` VALUES ('028B02676BDD44ACB531BC2BBC2AA2B7', 2, '执行监听', '2', 'sys_listener_type', NULL, 'default', 'N', '0', 'superAdmin', '2022-12-25 11:47:37', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('028B02676BDD44ACB531BC2BBC2AA2B8', 0, 'Java类', 'classListener', 'sys_listener_value_type', NULL, 'default', 'N', '0', 'superAdmin', '2022-12-25 11:48:55', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('028B02676BDD44ACB531BC2BBC2AA2B9', 0, '表达式', 'expressionListener', 'sys_listener_value_type', NULL, 'default', 'N', '0', 'superAdmin', '2022-12-25 11:49:05', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('028B02676BDD44ACB531BC2BBC2AA2BA', 2, '已取消', '2', 'rad_order_status', NULL, 'warning', 'N', '0', 'superAdmin', '2024-07-19 17:12:39', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('038B02676BDD44ACB531BC2BBC2AA2B1', 0, '未发布', '0', 'workflow_template_status', NULL, 'default', 'N', '0', 'superAdmin', '2024-08-27 19:34:17', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('038B02676BDD44ACB531BC2BBC2AA2B2', 0, '已发布', '1', 'workflow_template_status', NULL, 'default', 'N', '0', 'superAdmin', '2024-08-27 19:34:40', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('038B02676BDD44ACB531BC2BBC2AA2B3', 0, '已下架', '2', 'workflow_template_status', NULL, 'default', 'N', '0', 'superAdmin', '2024-08-27 19:34:52', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('038B02676BDD44ACB531BC2BBC2AA2B4', 2, '部门公告', '1', 'sys_notice_type', '', 'default', 'N', '0', 'superAdmin', '2024-07-17 15:19:23', '', NULL, '公告');
INSERT INTO `sys_dict_data` VALUES ('038B02676BDD44ACB531BC2BBC2AA2B5', 0, '成功', '1', 'mq_async_status', NULL, 'default', 'N', '0', 'superAdmin', '2024-09-11 16:22:22', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('038B02676BDD44ACB531BC2BBC2AA2B6', 0, '失败', '2', 'mq_async_status', NULL, 'default', 'N', '0', 'superAdmin', '2024-09-11 16:22:29', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('038B02676BDD44ACB531BC2BBC2AA2B7', 0, '未处理', '0', 'mq_async_status', NULL, 'default', 'N', '0', 'superAdmin', '2024-09-11 16:22:58', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('038B02676BDD44ACB531BC2BBC2AA2B8', 1, '草稿', '0', 'sys_notice_status', '', 'info', 'Y', '0', 'superAdmin', '2024-07-17 15:19:23', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES ('038B02676BDD44ACB531BC2BBC2AA2B9', 0, '动态表单（通用）', '1', 'workflow_template_form_type', NULL, 'default', 'N', '0', 'superAdmin', '2024-11-26 14:37:42', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('038B02676BDD44ACB531BC2BBC2AA2BA', 0, '代理表达式', 'delegateExpressionListener', 'sys_listener_value_type', NULL, 'default', 'N', '0', 'superAdmin', '2022-12-25 11:49:16', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('045B02676BDD44ACB531BC2BBC2AA2B1', 0, '8', '8', 'biz_form_field_span', NULL, 'default', 'N', '0', 'superAdmin', '2024-11-26 18:10:26', '', NULL, '按gutter=24布局，占8，即页面1/3');
INSERT INTO `sys_dict_data` VALUES ('045B02676BDD44ACB531BC2BBC2AA2B2', 1, '16', '16', 'biz_form_field_span', NULL, 'default', 'N', '0', 'superAdmin', '2024-11-26 18:10:40', '', NULL, '按gutter=24布局，占16，即页面2/3');
INSERT INTO `sys_dict_data` VALUES ('045B02676BDD44ACB531BC2BBC2AA2B3', 2, '24', '24', 'biz_form_field_span', NULL, 'default', 'N', '0', 'superAdmin', '2024-11-26 18:10:47', '', NULL, '按gutter=24布局，占一整行');
INSERT INTO `sys_dict_data` VALUES ('045B02676BDD44ACB531BC2BBC2AA2B4', 0, 'text（单行文本）', 'text', 'biz_form_field_type', NULL, 'default', 'N', '0', 'superAdmin', '2024-11-26 18:22:38', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('045B02676BDD44ACB531BC2BBC2AA2B5', 1, 'textarea（多行文本）', 'textarea', 'biz_form_field_type', NULL, 'default', 'N', '0', 'superAdmin', '2024-11-26 18:23:07', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('045B02676BDD44ACB531BC2BBC2AA2B6', 3, 'date（日期，yyyy-MM-dd）', 'date', 'biz_form_field_type', NULL, 'default', 'N', '0', 'superAdmin', '2024-11-26 18:23:34', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('045B02676BDD44ACB531BC2BBC2AA2B7', 2, '已发布', '1', 'sys_notice_status', '', 'success', 'N', '0', 'superAdmin', '2024-07-17 15:19:23', '', NULL, '关闭状态');
INSERT INTO `sys_dict_data` VALUES ('045B02676BDD44ACB531BC2BBC2AA2BA', 0, '业务表单（关联业务表）', '2', 'workflow_template_form_type', NULL, 'default', 'N', '0', 'superAdmin', '2024-11-26 14:38:01', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('058B02676BDD44ACB531BC2BBC2AA2B1', 1, '男', '0', 'sys_user_sex', '', '', 'Y', '0', 'superAdmin', '2024-07-17 15:19:22', '', NULL, '性别男');
INSERT INTO `sys_dict_data` VALUES ('058B02676BDD44ACB531BC2BBC2AA2B2', 1, '默认', 'DEFAULT', 'sys_job_group', '', '', 'Y', '0', 'superAdmin', '2024-07-17 15:19:23', '', NULL, '默认分组');
INSERT INTO `sys_dict_data` VALUES ('058B02676BDD44ACB531BC2BBC2AA2B3', 2, '系统', 'SYSTEM', 'sys_job_group', '', '', 'N', '0', 'superAdmin', '2024-07-17 15:19:23', '', NULL, '系统分组');
INSERT INTO `sys_dict_data` VALUES ('058B02676BDD44ACB531BC2BBC2AA2B4', 0, '无效', '0', 'sys_valid_status', NULL, 'danger', 'N', '0', 'superAdmin', '2024-07-19 17:01:41', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('058B02676BDD44ACB531BC2BBC2AA2B5', 1, '有效', '1', 'sys_valid_status', NULL, 'success', 'N', '0', 'superAdmin', '2024-07-19 17:01:55', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('058B02676BDD44ACB531BC2BBC2AA2B6', 0, '否', '0', 'sys_yes_or_no', NULL, 'info', 'N', '0', 'superAdmin', '2024-07-19 17:08:08', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('058B02676BDD44ACB531BC2BBC2AA2B7', 1, '是', '1', 'sys_yes_or_no', NULL, 'primary', 'N', '0', 'superAdmin', '2024-07-19 17:08:15', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('058B02676BDD44ACB531BC2BBC2AA2B8', 0, '未支付', '0', 'rad_order_status', NULL, 'info', 'N', '0', 'superAdmin', '2024-07-19 17:12:12', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('058B02676BDD44ACB531BC2BBC2AA2B9', 1, '已支付', '1', 'rad_order_status', NULL, 'success', 'N', '0', 'superAdmin', '2024-07-19 17:12:25', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('16A609D3170A467C85734847EEF29BE6', 2, '提前10分钟', '2', 'schedule_remind_time', NULL, 'default', 'N', '0', 'superAdmin', '2025-07-03 19:21:36', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('1786AB52B0D64C488FC15E97222D0685', 2, '科室', '2', 'sys_org_type', NULL, 'default', 'N', '0', 'superAdmin', '2025-07-29 17:28:31', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('8BF6892093FA40D0BC754505873BAD60', 3, '班组', '3', 'sys_org_type', NULL, 'default', 'N', '0', 'superAdmin', '2025-07-29 17:28:40', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('A15B02676BDD44ACB531BC2BBC2AA2B1', 3, 'datetime（日期+时间，yyyy-MM-dd HH:mm:ss）', 'datetime', 'biz_form_field_type', NULL, 'default', 'N', '0', 'superAdmin', '2024-11-26 18:25:23', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('A15B02676BDD44ACB531BC2BBC2AA2B2', 4, 'daterange（日期范围）', 'daterange', 'biz_form_field_type', NULL, 'default', 'N', '0', 'superAdmin', '2024-11-26 18:26:22', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('A15B02676BDD44ACB531BC2BBC2AA2B3', 6, 'checkbox（多选框）', 'checkbox', 'biz_form_field_type', NULL, 'default', 'N', '0', 'superAdmin', '2024-11-26 18:31:17', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('A15B02676BDD44ACB531BC2BBC2AA2B4', 5, 'radio（单选）', 'radio', 'biz_form_field_type', NULL, 'default', 'N', '0', 'superAdmin', '2024-11-26 18:32:05', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('A15B02676BDD44ACB531BC2BBC2AA2B5', 8, 'switch（开关）', 'switch', 'biz_form_field_type', NULL, 'default', 'N', '0', 'superAdmin', '2024-11-26 18:35:27', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('A15B02676BDD44ACB531BC2BBC2AA2B6', 7, 'select（下拉选择）', 'select', 'biz_form_field_type', NULL, 'default', 'N', '0', 'superAdmin', '2024-11-26 18:36:34', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('A15B02676BDD44ACB531BC2BBC2AA2B7', 2, 'number（计数器）', 'number', 'biz_form_field_type', NULL, 'default', 'N', '0', 'superAdmin', '2024-11-26 18:37:23', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('A15B02676BDD44ACB531BC2BBC2AA2B8', 9, 'rate（评分）', 'rate', 'biz_form_field_type', NULL, 'default', 'N', '0', 'superAdmin', '2024-11-26 18:40:23', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('A15B02676BDD44ACB531BC2BBC2AA2B9', 10, 'cascader（级联选择）', 'cascader', 'biz_form_field_type', NULL, 'default', 'N', '0', 'superAdmin', '2024-11-26 18:41:12', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('AD2382B11BDD4C758E5A464ADBC1CFB2', 0, '单位', '0', 'sys_org_type', NULL, 'default', 'N', '0', 'superAdmin', '2025-07-29 17:28:13', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('B45B02E56BDD44ACB531BC2BBC2AA2B1', 99, '其他', '0', 'sys_oper_type', '', 'info', 'N', '0', 'superAdmin', '2024-07-17 15:19:23', '', NULL, '其他操作');
INSERT INTO `sys_dict_data` VALUES ('B45B02E56BDD44ACB531BC2BBC2AA2B2', 0, '圆形', 'circle', 'esign_seal_style', NULL, 'default', 'N', '0', 'superAdmin', '2025-03-04 14:51:34', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('B45B02E56BDD44ACB531BC2BBC2AA2B3', 1, '椭圆', 'oval', 'esign_seal_style', NULL, 'default', 'N', '0', 'superAdmin', '2025-03-04 14:51:49', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('B45B02E56BDD44ACB531BC2BBC2AA2B4', 0, '红色', 'red', 'esign_seal_color', NULL, 'danger', 'N', '0', 'superAdmin', '2025-03-04 15:18:03', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('B45B02E56BDD44ACB531BC2BBC2AA2B5', 1, '蓝色', 'blue', 'esign_seal_color', NULL, 'primary', 'N', '0', 'superAdmin', '2025-03-04 15:18:41', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('B45B02E56BDD44ACB531BC2BBC2AA2B6', 1, '新增', '1', 'sys_oper_type', '', 'info', 'N', '0', 'superAdmin', '2024-07-17 15:19:24', '', NULL, '新增操作');
INSERT INTO `sys_dict_data` VALUES ('B45B02E56BDD44ACB531BC2BBC2AA2B7', 0, '公章', '0', 'esign_seal_type', NULL, 'default', 'N', '0', 'superAdmin', '2025-03-04 15:21:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('B45B02E56BDD44ACB531BC2BBC2AA2B8', 1, '合同章', '1', 'esign_seal_type', NULL, 'default', 'N', '0', 'superAdmin', '2025-03-04 15:21:08', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('B45B02E56BDD44ACB531BC2BBC2AA2B9', 2, '发票章', '2', 'esign_seal_type', NULL, 'default', 'N', '0', 'superAdmin', '2025-03-04 15:21:15', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('C25B42E76BDD44ACB531BC2BBC2AA2B1', 2, '女', '1', 'sys_user_sex', '', '', 'N', '0', 'superAdmin', '2024-07-17 15:19:22', '', NULL, '性别女');
INSERT INTO `sys_dict_data` VALUES ('C25B42E76BDD44ACB531BC2BBC2AA2B2', 2, '修改', '2', 'sys_oper_type', '', 'info', 'N', '0', 'superAdmin', '2024-07-17 15:19:24', '', NULL, '修改操作');
INSERT INTO `sys_dict_data` VALUES ('C25B42E76BDD44ACB531BC2BBC2AA2B3', 3, '自定义（有扩展要求，需自行实现）', '3', 'workflow_template_form_type', NULL, 'default', 'N', '0', 'superAdmin', '2025-03-13 19:52:57', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('C25B42E76BDD44ACB531BC2BBC2AA2B4', 0, '指定人员', 'fixed', 'flowable_expression_type', NULL, 'default', 'N', '0', 'superAdmin', '2025-03-27 09:18:37', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('C25B42E76BDD44ACB531BC2BBC2AA2B5', 0, '动态选人', 'dynamic', 'flowable_expression_type', NULL, 'default', 'N', '0', 'superAdmin', '2025-03-27 09:18:52', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('C25B42E76BDD44ACB531BC2BBC2AA2B6', 3, '已下架', '2', 'sys_notice_status', NULL, 'warning', 'N', '0', 'superAdmin', '2025-05-13 08:42:17', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('C25B42E76BDD44ACB531BC2BBC2AA2B7', 1, '短信', '1', 'workflow_message_type', NULL, 'default', 'N', '0', 'superAdmin', '2025-05-14 17:19:20', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('C25B42E76BDD44ACB531BC2BBC2AA2B8', 2, '站内通知', '2', 'workflow_message_type', NULL, 'default', 'N', '0', 'superAdmin', '2025-05-14 17:19:34', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('C25B42E76BDD44ACB531BC2BBC2AA2B9', 1, '流程审批', 'SMS_492490041', 'workflow_message_template_type', NULL, 'default', 'N', '0', 'superAdmin', '2025-05-14 19:42:36', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('C35F42E76BDD44ACB531BC2BBC2AA2B1', 0, '草稿', '0', 'sys_news_status', NULL, 'info', 'N', '0', 'superAdmin', '2025-05-15 08:39:38', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('C35F42E76BDD44ACB531BC2BBC2AA2B2', 3, '删除', '3', 'sys_oper_type', '', 'danger', 'N', '0', 'superAdmin', '2024-07-17 15:19:24', '', NULL, '删除操作');
INSERT INTO `sys_dict_data` VALUES ('C35F42E76BDD44ACB531BC2BBC2AA2B3', 0, '已发布', '1', 'sys_news_status', NULL, 'primary', 'N', '0', 'superAdmin', '2025-05-15 08:39:56', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('C35F42E76BDD44ACB531BC2BBC2AA2B4', 0, '已下架', '2', 'sys_news_status', NULL, 'warning', 'N', '0', 'superAdmin', '2025-05-15 08:40:08', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('C35F42E76BDD44ACB531BC2BBC2AA2B5', 0, '脚本', 'scriptListener', 'sys_listener_value_type', NULL, 'default', 'N', '0', 'superAdmin', '2025-05-16 09:16:30', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('C35F42E76BDD44ACB531BC2BBC2AA2B6', 0, '草稿', '0', 'workflow_biz_status', NULL, 'info', 'N', '0', 'superAdmin', '2025-05-16 11:29:34', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('C35F42E76BDD44ACB531BC2BBC2AA2B7', 0, '审批中', '1', 'workflow_biz_status', NULL, 'primary', 'N', '0', 'superAdmin', '2025-05-16 11:29:46', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('C35F42E76BDD44ACB531BC2BBC2AA2B8', 0, '已办结', '2', 'workflow_biz_status', NULL, 'success', 'N', '0', 'superAdmin', '2025-05-16 11:30:04', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('C35F42E76BDD44ACB531BC2BBC2AA2B9', 0, '已终止', '3', 'workflow_biz_status', NULL, 'danger', 'N', '0', 'superAdmin', '2025-05-16 11:30:31', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('D03F42E76BDD44ACB531BC2BBC2AA2B1', 2, '短信通知', '2', 'schedule_remind_way', NULL, 'default', 'N', '0', 'superAdmin', '2025-05-23 10:33:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('D03F42E76BDD44ACB531BC2BBC2AA2B2', 1, '按天', '1', 'schedule_repeat_way', NULL, 'default', 'N', '0', 'superAdmin', '2025-05-23 10:37:17', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('D03F42E76BDD44ACB531BC2BBC2AA2B3', 2, '按周', '2', 'schedule_repeat_way', NULL, 'default', 'N', '0', 'superAdmin', '2025-05-23 10:37:29', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('D03F42E76BDD44ACB531BC2BBC2AA2B4', 3, '按月', '3', 'schedule_repeat_way', NULL, 'default', 'N', '0', 'superAdmin', '2025-05-23 10:37:36', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('D03F42E76BDD44ACB531BC2BBC2AA2B5', 4, '按年', '4', 'schedule_repeat_way', NULL, 'default', 'N', '0', 'superAdmin', '2025-05-23 10:37:45', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('D03F42E76BDD44ACB531BC2BBC2AA2B6', 1, '全部可见', '1', 't_kbs_topic_visual_scope', NULL, 'default', 'N', '0', 'superAdmin', '2025-05-27 13:36:01', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('D03F42E76BDD44ACB531BC2BBC2AA2B7', 6, '导入', '6', 'sys_oper_type', '', 'warning', 'N', '0', 'superAdmin', '2024-07-17 15:19:24', '', NULL, '导入操作');
INSERT INTO `sys_dict_data` VALUES ('D03F42E76BDD44ACB531BC2BBC2AA2B8', 2, '部分可见', '2', 't_kbs_topic_visual_scope', NULL, 'default', 'N', '0', 'superAdmin', '2025-05-27 13:36:16', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('D03F42E76BDD44ACB531BC2BBC2AA2B9', 3, '仅自己可见', '3', 't_kbs_topic_visual_scope', NULL, 'default', 'N', '0', 'superAdmin', '2025-05-27 13:37:03', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('D2TF22E76BDD44ACB531BC2BBC2AA2B1', 1, '可编辑', '1', 't_kbs_topic_operate_type', NULL, 'default', 'N', '0', 'superAdmin', '2025-05-28 10:37:02', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('D2TF22E76BDD44ACB531BC2BBC2AA2B2', 1, '主题', '1', 't_kbs_favorite_type', NULL, 'default', 'N', '0', 'superAdmin', '2025-05-28 17:27:18', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('D2TF22E76BDD44ACB531BC2BBC2AA2B3', 2, '文档', '2', 't_kbs_favorite_type', NULL, 'default', 'N', '0', 'superAdmin', '2025-05-28 17:27:31', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('D2TF22E76BDD44ACB531BC2BBC2AA2B4', 7, '强退', '7', 'sys_oper_type', '', 'danger', 'N', '0', 'superAdmin', '2024-07-17 15:19:24', '', NULL, '强退操作');
INSERT INTO `sys_dict_data` VALUES ('D2TF22E76BDD44ACB531BC2BBC2AA2B5', 2, '仅查看', '2', 't_kbs_topic_operate_type', NULL, 'default', 'N', '0', 'superAdmin', '2025-06-16 15:53:20', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('D2TF22E76BDD44ACB531BC2BBC2AA2B6', 8, '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', '0', 'superAdmin', '2024-07-17 15:19:24', '', NULL, '生成操作');
INSERT INTO `sys_dict_data` VALUES ('D2TF22E76BDD44ACB531BC2BBC2AA2B7', 9, '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', '0', 'superAdmin', '2024-07-17 15:19:24', '', NULL, '清空操作');
INSERT INTO `sys_dict_data` VALUES ('D2TF22E76BDD44ACB531BC2BBC2AA2B8', 1, '成功', '0', 'sys_common_status', '', 'primary', 'N', '0', 'superAdmin', '2024-07-17 15:19:24', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES ('D4YF42E76BDD44ACB531BC2BBC2AA2B1', 2, '失败', '1', 'sys_common_status', '', 'danger', 'N', '0', 'superAdmin', '2024-07-17 15:19:25', '', NULL, '停用状态');
INSERT INTO `sys_dict_data` VALUES ('D4YF42E76BDD44ACB531BC2BBC2AA2B2', 3, '未知', '2', 'sys_user_sex', '', '', 'N', '0', 'superAdmin', '2024-07-17 15:19:22', '', NULL, '性别未知');
INSERT INTO `sys_dict_data` VALUES ('D4YF42E76BDD44ACB531BC2BBC2AA2B3', 1, '显示', '0', 'sys_show_hide', '', 'primary', 'Y', '0', 'superAdmin', '2024-07-17 15:19:22', '', NULL, '显示菜单');
INSERT INTO `sys_dict_data` VALUES ('D4YF42E76BDD44ACB531BC2BBC2AA2B4', 2, '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', '0', 'superAdmin', '2024-07-17 15:19:22', '', NULL, '隐藏菜单');
INSERT INTO `sys_dict_data` VALUES ('D4YF42E76BDD44ACB531BC2BBC2AA2B5', 1, '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', '0', 'superAdmin', '2024-07-17 15:19:23', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES ('D4YF42E76BDD44ACB531BC2BBC2AA2B6', 2, '停用', '1', 'sys_normal_disable', '', 'danger', 'N', '0', 'superAdmin', '2024-07-17 15:19:23', '', NULL, '停用状态');
INSERT INTO `sys_dict_data` VALUES ('D4YF42E76BDD44ACB531BC2BBC2AA2B7', 1, '正常', '0', 'sys_job_status', '', 'primary', 'Y', '0', 'superAdmin', '2024-07-17 15:19:23', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES ('D4YF42E76BDD44ACB531BC2BBC2AA2B8', 2, '暂停', '1', 'sys_job_status', '', 'danger', 'N', '0', 'superAdmin', '2024-07-17 15:19:23', '', NULL, '停用状态');
INSERT INTO `sys_dict_data` VALUES ('DDDD4802C44C4C89BE07ED01964BECC0', 1, '部门', '1', 'sys_org_type', NULL, 'default', 'N', '0', 'superAdmin', '2025-07-29 17:28:23', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('F20F42E76BDD44ACB531BC2BBC2AA2B1', 3, '提前15分钟', '3', 'schedule_remind_time', NULL, 'default', 'N', '0', 'superAdmin', '2025-05-23 10:01:19', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('F20F42E76BDD44ACB531BC2BBC2AA2B2', 4, '提前30分钟', '4', 'schedule_remind_time', NULL, 'default', 'N', '0', 'superAdmin', '2025-05-23 10:01:29', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('F20F42E76BDD44ACB531BC2BBC2AA2B3', 5, '提前1小时', '5', 'schedule_remind_time', NULL, 'default', 'N', '0', 'superAdmin', '2025-05-23 10:01:51', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('F20F42E76BDD44ACB531BC2BBC2AA2B4', 6, '提前1天', '6', 'schedule_remind_time', NULL, 'default', 'N', '0', 'superAdmin', '2025-05-23 10:02:10', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('F20F42E76BDD44ACB531BC2BBC2AA2B5', 0, '一般的', '0', 'schedule_level', NULL, 'default', 'N', '0', 'superAdmin', '2025-05-23 10:30:04', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('F20F42E76BDD44ACB531BC2BBC2AA2B6', 5, '导出', '5', 'sys_oper_type', '', 'warning', 'N', '0', 'superAdmin', '2024-07-17 15:19:24', '', NULL, '导出操作');
INSERT INTO `sys_dict_data` VALUES ('F20F42E76BDD44ACB531BC2BBC2AA2B7', 1, '重要的', '1', 'schedule_level', NULL, 'default', 'N', '0', 'superAdmin', '2025-05-23 10:30:12', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('F20F42E76BDD44ACB531BC2BBC2AA2B8', 99, '不提醒', '0', 'schedule_remind_way', NULL, 'default', 'N', '0', 'superAdmin', '2025-05-23 10:32:45', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('F20F42E76BDD44ACB531BC2BBC2AA2B9', 1, '系统通知', '1', 'schedule_remind_way', NULL, 'default', 'N', '0', 'superAdmin', '2025-05-23 10:32:53', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('G90F42E76BDD44ACB531BC2BBC2AA2B1', 1, '全部', '0', 't_workflow_entrust_type', NULL, 'default', 'N', '0', 'superAdmin', '2025-05-16 11:37:25', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('G90F42E76BDD44ACB531BC2BBC2AA2B2', 2, '部分', '1', 't_workflow_entrust_type', NULL, 'default', 'N', '0', 'superAdmin', '2025-05-16 11:37:41', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('G90F42E76BDD44ACB531BC2BBC2AA2B3', 1, '禁用', '0', 't_workflow_enable_type', NULL, 'default', 'N', '0', 'superAdmin', '2025-05-16 15:38:01', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('G90F42E76BDD44ACB531BC2BBC2AA2B4', 4, '授权', '4', 'sys_oper_type', '', 'primary', 'N', '0', 'superAdmin', '2024-07-17 15:19:24', '', NULL, '授权操作');
INSERT INTO `sys_dict_data` VALUES ('G90F42E76BDD44ACB531BC2BBC2AA2B5', 2, '启用', '1', 't_workflow_enable_type', NULL, 'default', 'N', '0', 'superAdmin', '2025-05-16 15:38:17', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('G90F42E76BDD44ACB531BC2BBC2AA2B6', 0, '个人提醒', '0', 'schedule_type', NULL, 'default', 'N', '0', 'superAdmin', '2025-05-23 09:55:38', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('G90F42E76BDD44ACB531BC2BBC2AA2B7', 1, '组织参与', '1', 'schedule_type', NULL, 'default', 'N', '0', 'superAdmin', '2025-05-23 09:55:58', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('G90F42E76BDD44ACB531BC2BBC2AA2B8', 99, '不提醒', '0', 'schedule_remind_time', NULL, 'default', 'N', '0', 'superAdmin', '2025-05-23 10:00:54', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES ('G90F42E76BDD44ACB531BC2BBC2AA2B9', 1, '提前5分钟', '1', 'schedule_remind_time', NULL, 'default', 'N', '0', 'superAdmin', '2025-05-23 10:01:06', '', NULL, NULL);

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `dict_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典主键',
  `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典类型',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE INDEX `dict_type`(`dict_type` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES ('DA3AD6F9C5DD4D39B31C67AB411B1110', '启用方式', 't_workflow_enable_type', '0', 'superAdmin', '2025-05-16 11:47:40', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES ('DA3AD6F9C5DD4D39B31C67AB411B1141', '菜单状态', 'sys_show_hide', '0', 'superAdmin', '2024-07-17 15:19:19', '', NULL, '菜单状态列表');
INSERT INTO `sys_dict_type` VALUES ('DA3AD6F9C5DD4D39B31C67AB411B1142', '收藏类型', 't_kbs_favorite_type', '0', 'superAdmin', '2025-05-28 17:26:42', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES ('DA3AD6F9C5DD4D39B31C67AB411B1143', '知识库主题操作类型', 't_kbs_topic_operate_type', '0', 'superAdmin', '2025-05-28 10:36:43', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES ('DA3AD6F9C5DD4D39B31C67AB411B1144', '知识库可见范围', 't_kbs_topic_visual_scope', '0', 'superAdmin', '2025-05-27 13:35:35', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES ('DA3AD6F9C5DD4D39B31C67AB411B1145', '日程重复方式', 'schedule_repeat_way', '0', 'superAdmin', '2025-05-23 10:36:34', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES ('DA3AD6F9C5DD4D39B31C67AB411B1146', '日程提醒方式', 'schedule_remind_way', '0', 'superAdmin', '2025-05-23 10:31:52', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES ('DA3AD6F9C5DD4D39B31C67AB411B1147', '日程重要等级', 'schedule_level', '0', 'superAdmin', '2025-05-23 10:29:18', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES ('DA3AD6F9C5DD4D39B31C67AB411B1148', '日程提醒时间', 'schedule_remind_time', '0', 'superAdmin', '2025-05-23 09:59:08', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES ('DA3AD6F9C5DD4D39B31C67AB411B1149', '日程类型', 'schedule_type', '0', 'superAdmin', '2025-05-23 09:51:19', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES ('DA3AD6F9C5DD4D39B31C67AB411B114F', '机构类型', 'sys_org_type', '0', 'superAdmin', '2025-07-29 17:27:41', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES ('DA4AD6F9C5DD4D39B31C67AB411B1141', '委托方式', 't_workflow_entrust_type', '0', 'superAdmin', '2025-05-16 11:37:09', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES ('DA4AD6F9C5DD4D39B31C67AB411B1142', '流程业务状态', 'workflow_biz_status', '0', 'superAdmin', '2025-05-16 11:28:44', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES ('DA4AD6F9C5DD4D39B31C67AB411B1143', '资讯状态', 'sys_news_status', '0', 'superAdmin', '2025-05-15 08:39:24', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES ('DA4AD6F9C5DD4D39B31C67AB411B1144', '短信消息模板', 'workflow_message_template_type', '0', 'superAdmin', '2025-05-14 19:41:59', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES ('DA4AD6F9C5DD4D39B31C67AB411B1145', '流程消息通知类型', 'workflow_message_type', '0', 'superAdmin', '2025-05-14 17:18:37', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES ('DA4AD6F9C5DD4D39B31C67AB411B1146', '表达式类型', 'flowable_expression_type', '0', 'superAdmin', '2025-03-27 09:17:30', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES ('DA4AD6F9C5DD4D39B31C67AB411B1147', '印章类型', 'esign_seal_type', '0', 'superAdmin', '2025-03-04 15:20:41', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES ('DA4AD6F9C5DD4D39B31C67AB411B1148', '印章颜色', 'esign_seal_color', '0', 'superAdmin', '2025-03-04 15:12:53', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES ('DA4AD6F9C5DD4D39B31C67AB411B1149', '印章样式', 'esign_seal_style', '0', 'superAdmin', '2025-03-04 14:51:12', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES ('DA4AD6F9C5DD4D39B31C67AB411B114F', '操作类型', 'sys_oper_type', '0', 'superAdmin', '2024-07-17 15:19:20', '', NULL, '操作类型列表');
INSERT INTO `sys_dict_type` VALUES ('DA55ADF9C5DD4D39B31C67AB411B114F', '通知类型', 'sys_notice_type', '0', 'superAdmin', '2024-07-17 15:19:20', '', NULL, '通知类型列表');
INSERT INTO `sys_dict_type` VALUES ('DA5AD6F9C5DD4D39B31C67AB411B1142', '业务表单字段类型', 'biz_form_field_type', '0', 'superAdmin', '2024-11-26 18:22:11', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES ('DA5AD6F9C5DD4D39B31C67AB411B1143', '业务表单字段布局占位', 'biz_form_field_span', '0', 'superAdmin', '2024-11-26 18:09:53', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES ('DA5AD6F9C5DD4D39B31C67AB411B1144', '模板表单类型', 'workflow_template_form_type', '0', 'superAdmin', '2024-11-26 14:37:03', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES ('DA5AD6F9C5DD4D39B31C67AB411B1145', '异步任务状态', 'mq_async_status', '0', 'superAdmin', '2024-09-11 16:21:43', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES ('DA5AD6F9C5DD4D39B31C67AB411B1146', '模板发布状态', 'workflow_template_status', '0', 'superAdmin', '2024-08-27 19:33:44', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES ('DA5AD6F9C5DD4D39B31C67AB411B1147', '监听属性', 'sys_listener_event_type', '0', 'superAdmin', '2022-12-18 22:04:29', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES ('DA5AD6F9C5DD4D39B31C67AB411B1148', '监听值类型', 'sys_listener_value_type', '0', 'superAdmin', '2022-12-18 22:03:39', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES ('DA5AD6F9C5DD4D39B31C67AB411B1149', '监听类型', 'sys_listener_type', '0', 'superAdmin', '2022-12-18 22:03:07', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES ('DA5AD6F9C5DD4D39B31C67AB411B114F', '通知状态', 'sys_notice_status', '0', 'superAdmin', '2024-07-17 15:19:20', '', NULL, '通知状态列表');
INSERT INTO `sys_dict_type` VALUES ('DA6AD6F9C5DD4D39B31C67AB411B1141', '流程分类', 'sys_process_category', '0', 'superAdmin', '2022-12-12 11:17:38', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES ('DA6AD6F9C5DD4D39B31C67AB411B1142', '是否', 'sys_yes_or_no', '0', 'superAdmin', '2024-07-19 17:07:50', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES ('DA6AD6F9C5DD4D39B31C67AB411B1143', '有效状态', 'sys_valid_status', '0', 'superAdmin', '2024-07-19 17:01:16', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES ('DA6AD6F9C5DD4D39B31C67AB411B1144', '系统状态', 'sys_common_status', '0', 'superAdmin', '2024-07-17 15:19:20', '', NULL, '登录状态列表');
INSERT INTO `sys_dict_type` VALUES ('DA6AD6F9C5DD4D39B31C67AB411B1145', '用户性别', 'sys_user_sex', '0', 'superAdmin', '2024-07-17 15:19:19', '', NULL, '用户性别列表');
INSERT INTO `sys_dict_type` VALUES ('DA6AD6F9C5DD4D39B31C67AB411B114F', '系统是否', 'sys_yes_no', '0', 'superAdmin', '2024-07-17 15:19:19', '', NULL, '系统是否列表');
INSERT INTO `sys_dict_type` VALUES ('DA7AD6F9C5DD4D39B31C67AB411B114F', '任务分组', 'sys_job_group', '0', 'superAdmin', '2024-07-17 15:19:19', '', NULL, '任务分组列表');
INSERT INTO `sys_dict_type` VALUES ('DA8AD6F9C5DD4D39B31C67AB411B114F', '任务状态', 'sys_job_status', '0', 'superAdmin', '2024-07-17 15:19:19', '', NULL, '任务状态列表');
INSERT INTO `sys_dict_type` VALUES ('DA9AD6F9C5DD4D39B31C67AB411B114F', '系统开关', 'sys_normal_disable', '0', 'superAdmin', '2024-07-17 15:19:19', '', NULL, '系统开关列表');

SET FOREIGN_KEY_CHECKS = 1;
