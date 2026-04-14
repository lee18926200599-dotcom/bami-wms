/*
 Navicat Premium Data Transfer

 Source Server         : 八米新加坡
 Source Server Type    : MySQL
 Source Server Version : 100624
 Source Host           : 47.236.202.11:3306
 Source Schema         : zzz_bms_base

 Target Server Type    : MySQL
 Target Server Version : 100624
 File Encoding         : 65001

 Date: 14/04/2026 09:13:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bms_account_strategy
-- ----------------------------
DROP TABLE IF EXISTS `bms_account_strategy`;
CREATE TABLE `bms_account_strategy`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `account_strategy_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '策略名称',
  `account_strategy_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '策略编码',
  `group_id` bigint(20) NOT NULL COMMENT '集团id',
  `org_id` bigint(20) NOT NULL COMMENT '组织id',
  `org_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组织名称',
  `expense_type` int(11) NOT NULL COMMENT '费用类型',
  `cust_sub_id` bigint(20) NOT NULL COMMENT '计账对象',
  `cust_sub_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '计费对象名称',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `accounting_cycle_type` int(11) NOT NULL COMMENT '记账周期类型',
  `accounting_cycle` int(11) NOT NULL COMMENT '记账周期',
  `accounting_start_date` date NOT NULL COMMENT '记账开始日期',
  `outgoing_type` int(11) NOT NULL COMMENT '出账类型',
  `outgoing_cycle` int(11) NOT NULL COMMENT '出账单日',
  `execute_record_end_date` date NULL DEFAULT NULL COMMENT '执行记录结束日期',
  `execute_record_account_period` int(11) NULL DEFAULT NULL COMMENT '执行记录账期',
  `state` int(11) NOT NULL DEFAULT 0 COMMENT '状态',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted_flag` int(11) NOT NULL DEFAULT 0 COMMENT '删除标识(0:未删除,1:已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_account_strategy_no`(`account_strategy_no`) USING BTREE,
  INDEX `idx_group_id`(`group_id`) USING BTREE,
  INDEX `idx_org_id`(`org_id`) USING BTREE,
  INDEX `idx_cust_sub_id`(`cust_sub_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '计账策略表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bms_account_strategy_execute_record
-- ----------------------------
DROP TABLE IF EXISTS `bms_account_strategy_execute_record`;
CREATE TABLE `bms_account_strategy_execute_record`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `account_strategy_id` bigint(20) NOT NULL COMMENT '策略ID',
  `account_strategy_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '策略编码',
  `account_strategy_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '策略名称',
  `group_id` bigint(20) NOT NULL COMMENT '集团id',
  `org_id` bigint(20) NOT NULL COMMENT '组织id',
  `org_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组织名称',
  `expense_type` int(11) NOT NULL COMMENT '费用类型',
  `cust_sub_id` bigint(20) NOT NULL COMMENT '计账对象',
  `cust_sub_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '计费对象名称',
  `account_period` int(11) NOT NULL COMMENT '账期',
  `account_period_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账期名称',
  `accounting_cycle_type` int(11) NOT NULL COMMENT '记账周期类型',
  `accounting_cycle` int(11) NOT NULL COMMENT '记账周期',
  `accounting_start_date` date NOT NULL COMMENT '记账开始日期',
  `accounting_end_date` date NOT NULL COMMENT '记账截止日期',
  `payment_date` date NULL DEFAULT NULL COMMENT '支付日期',
  `account_bill_no` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '记账单号集合（逗号分隔）',
  `execute_start_time` datetime(0) NULL DEFAULT NULL COMMENT '执行开始时间',
  `execute_end_time` datetime(0) NULL DEFAULT NULL COMMENT '执行结束时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `reason` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '失败原因',
  `state` int(11) NOT NULL DEFAULT 0 COMMENT '状态',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted_flag` int(11) NOT NULL DEFAULT 0 COMMENT '删除标识(0:未删除,1:已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_account_strategy_id`(`account_strategy_id`) USING BTREE,
  INDEX `idx_account_strategy_no`(`account_strategy_no`) USING BTREE,
  INDEX `idx_group_id`(`group_id`) USING BTREE,
  INDEX `idx_org_id`(`org_id`) USING BTREE,
  INDEX `idx_cust_sub_id`(`cust_sub_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '记账执行跟踪表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bms_account_strategy_expense_item
-- ----------------------------
DROP TABLE IF EXISTS `bms_account_strategy_expense_item`;
CREATE TABLE `bms_account_strategy_expense_item`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `account_strategy_id` bigint(20) NOT NULL COMMENT '记账策略ID',
  `account_strategy_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '记账策略编码',
  `group_id` bigint(20) NOT NULL COMMENT '集团id',
  `org_id` bigint(20) NOT NULL COMMENT '组织id',
  `org_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组织名称',
  `cust_sub_id` bigint(20) NOT NULL COMMENT '计账对象ID',
  `expense_item_id` bigint(20) NOT NULL COMMENT '费项id',
  `expense_item_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '费项名称',
  `expense_type` int(11) NOT NULL COMMENT '费用类型',
  `billing_source_id` bigint(20) NOT NULL COMMENT '计费源id',
  `billing_source_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '计费源名称',
  `state` int(11) NOT NULL DEFAULT 0 COMMENT '状态(0:启用,1:停用)',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted_flag` int(11) NOT NULL DEFAULT 0 COMMENT '删除标识(0:未删除,1:已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_account_strategy_id`(`account_strategy_id`) USING BTREE,
  INDEX `idx_account_strategy_no`(`account_strategy_no`) USING BTREE,
  INDEX `idx_group_id`(`group_id`) USING BTREE,
  INDEX `idx_org_id`(`org_id`) USING BTREE,
  INDEX `idx_cust_sub_id`(`cust_sub_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '记账费项表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bms_biilling_strategy_exception
-- ----------------------------
DROP TABLE IF EXISTS `bms_biilling_strategy_exception`;
CREATE TABLE `bms_biilling_strategy_exception`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `strategy_id` bigint(20) NOT NULL COMMENT '策略id',
  `group_id` bigint(20) NOT NULL COMMENT '集团id',
  `org_id` bigint(20) NOT NULL COMMENT '组织id',
  `org_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组织名称',
  `business_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务单号',
  `strategy_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '策略编码',
  `strategy_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '策略名称',
  `strategy_version` double NOT NULL COMMENT '策略版本',
  `expense_type` int(11) NOT NULL COMMENT '费用类型',
  `cust_sub_id` bigint(20) NOT NULL COMMENT '客商id',
  `business_time` date NOT NULL COMMENT '业务发生时间',
  `excute_time` datetime(0) NOT NULL COMMENT '执行时间',
  `reason` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '异常原因',
  `state` int(11) NOT NULL DEFAULT 0 COMMENT '状态',
  `created_by` bigint(20) NOT NULL COMMENT '创建人ID',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `created_date` datetime(0) NOT NULL COMMENT '创建日期',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人id',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted_flag` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除(0:未删除,1:已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_strategy_id`(`strategy_id`) USING BTREE,
  INDEX `idx_group_id`(`group_id`) USING BTREE,
  INDEX `idx_org_id`(`org_id`) USING BTREE,
  INDEX `idx_cust_sub_id`(`cust_sub_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '计费策略异常记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bms_billing_rule
-- ----------------------------
DROP TABLE IF EXISTS `bms_billing_rule`;
CREATE TABLE `bms_billing_rule`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `rule_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '规则编码',
  `rule_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '规则名称',
  `group_id` bigint(20) NOT NULL COMMENT '集团id',
  `org_id` bigint(20) NOT NULL COMMENT '组织id',
  `org_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组织名称',
  `cust_sub_id` bigint(20) NOT NULL COMMENT '客商id',
  `cust_sub_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客商名称',
  `expense_type` int(11) NOT NULL COMMENT '费用类型',
  `purchase_sales_type` int(11) NOT NULL COMMENT '采销类型',
  `price_config_id` bigint(20) NOT NULL COMMENT '价格配置模板id',
  `price_config_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '价格配置模板名称',
  `expense_item_id` bigint(20) NOT NULL COMMENT '费项id',
  `expense_item_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '费项名称',
  `tax_rate` double NULL DEFAULT NULL COMMENT '税率',
  `state` int(11) NOT NULL DEFAULT 0 COMMENT '状态(0:启用,1:停用)',
  `rule_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '计费规则',
  `rule_content_json` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '计费规则JSON格式',
  `script_json` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '脚本JSON',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted_flag` int(11) NOT NULL DEFAULT 0 COMMENT '删除标识(0:未删除,1:已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_rule_no`(`rule_no`) USING BTREE,
  INDEX `idx_group_id`(`group_id`) USING BTREE,
  INDEX `idx_org_id`(`org_id`) USING BTREE,
  INDEX `idx_cust_sub_id`(`cust_sub_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '计费规则表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bms_billing_source
-- ----------------------------
DROP TABLE IF EXISTS `bms_billing_source`;
CREATE TABLE `bms_billing_source`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` bigint(20) NOT NULL COMMENT '集团id',
  `group_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '集团名称',
  `org_id` bigint(20) NOT NULL COMMENT '所属组织id',
  `org_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所属组织名称',
  `billing_source_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '计费源编码',
  `billing_source_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '计费源名称',
  `state` int(11) NOT NULL DEFAULT 0 COMMENT '状态',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `version` int(11) NOT NULL DEFAULT 1 COMMENT '版本号',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted_flag` int(11) NOT NULL DEFAULT 0 COMMENT '删除标识(0:未删除,1:已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_group_id`(`group_id`) USING BTREE,
  INDEX `idx_org_id`(`org_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '计费源主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bms_billing_source_org_allocate
-- ----------------------------
DROP TABLE IF EXISTS `bms_billing_source_org_allocate`;
CREATE TABLE `bms_billing_source_org_allocate`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` bigint(20) NOT NULL COMMENT '集团id',
  `group_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '集团名称',
  `billing_source_id` bigint(20) NOT NULL COMMENT '计费源id',
  `org_id` bigint(20) NOT NULL COMMENT '组织id',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted_flag` int(11) NOT NULL DEFAULT 0 COMMENT '删除标识(0:未删除,1:已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_group_id`(`group_id`) USING BTREE,
  INDEX `idx_billing_source_id`(`billing_source_id`) USING BTREE,
  INDEX `idx_org_id`(`org_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '计费源组织分配表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bms_billing_source_table_detail
-- ----------------------------
DROP TABLE IF EXISTS `bms_billing_source_table_detail`;
CREATE TABLE `bms_billing_source_table_detail`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` bigint(20) NOT NULL COMMENT '集团id',
  `group_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '集团名称',
  `org_id` bigint(20) NOT NULL COMMENT '组织id',
  `org_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组织名称',
  `field_id` bigint(20) NOT NULL COMMENT '字段id',
  `field_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字段名称',
  `query_flag` int(11) NOT NULL DEFAULT 0 COMMENT '是否查询条件(0:否,1:是)',
  `query_type` int(11) NULL DEFAULT NULL COMMENT '查询方式',
  `billing_source_table_id` bigint(20) NOT NULL COMMENT '数据源关联表id',
  `data_description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据项描述',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `version` int(11) NOT NULL DEFAULT 1 COMMENT '版本号',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted_flag` int(11) NOT NULL DEFAULT 0 COMMENT '删除标识(0:未删除,1:已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_group_id`(`group_id`) USING BTREE,
  INDEX `idx_org_id`(`org_id`) USING BTREE,
  INDEX `idx_billing_source_table_id`(`billing_source_table_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '计费源明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bms_billing_source_table_ref
-- ----------------------------
DROP TABLE IF EXISTS `bms_billing_source_table_ref`;
CREATE TABLE `bms_billing_source_table_ref`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` bigint(20) NOT NULL COMMENT '集团id',
  `group_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '集团名称',
  `org_id` bigint(20) NOT NULL COMMENT '所属组织id',
  `org_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所属组织名称',
  `table_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '表名称',
  `table_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表名描述',
  `physics_table_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '物理表名称',
  `physics_table_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '物理表名描述',
  `table_type` int(11) NOT NULL COMMENT '表类型(1:主表,2:明细表)',
  `billing_source_id` bigint(20) NOT NULL COMMENT '计费源id',
  `version` int(11) NOT NULL DEFAULT 1 COMMENT '版本号',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted_flag` int(11) NOT NULL DEFAULT 0 COMMENT '删除标识(0:未删除,1:已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_group_id`(`group_id`) USING BTREE,
  INDEX `idx_org_id`(`org_id`) USING BTREE,
  INDEX `idx_billing_source_id`(`billing_source_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '计费源物理表关系中间表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bms_billing_strategy
-- ----------------------------
DROP TABLE IF EXISTS `bms_billing_strategy`;
CREATE TABLE `bms_billing_strategy`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `strategy_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '策略名称',
  `strategy_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '策略编码',
  `group_id` bigint(20) NOT NULL COMMENT '集团id',
  `org_id` bigint(20) NOT NULL COMMENT '组织id',
  `org_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组织名称',
  `expense_type` int(11) NOT NULL COMMENT '费用类型',
  `cust_sub_id` bigint(20) NOT NULL COMMENT '计费对象ID',
  `cust_sub_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '计费对象名称',
  `expense_item_id` bigint(20) NOT NULL COMMENT '费项id',
  `expense_item_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '费项名称',
  `billing_source_id` bigint(20) NOT NULL COMMENT '计费源ID',
  `billing_source_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '计费源名称',
  `price_config_id` bigint(20) NOT NULL COMMENT '价格配置id',
  `billing_rule_id` bigint(20) NOT NULL COMMENT '计费规则id',
  `state` int(11) NOT NULL DEFAULT 0 COMMENT '状态(0:草稿,1:启用,2:停用)',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `version` double NOT NULL DEFAULT 1 COMMENT '版本号',
  `current_version_flag` int(11) NOT NULL DEFAULT 1 COMMENT '是否当前版本(0:历史版本,1:当前版本)',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted_flag` int(11) NOT NULL DEFAULT 0 COMMENT '删除标识(0:未删除,1:已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_strategy_no_version`(`strategy_no`, `version`) USING BTREE,
  INDEX `idx_group_id`(`group_id`) USING BTREE,
  INDEX `idx_org_id`(`org_id`) USING BTREE,
  INDEX `idx_cust_sub_id`(`cust_sub_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '计费策略表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bms_expense_item
-- ----------------------------
DROP TABLE IF EXISTS `bms_expense_item`;
CREATE TABLE `bms_expense_item`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` bigint(20) NOT NULL COMMENT '所属集团ID',
  `org_id` bigint(20) NOT NULL COMMENT '所属组织ID',
  `group_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '集团名称',
  `org_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组织名称',
  `expense_item_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '费项编码',
  `expense_item_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '费项名称',
  `expense_type` int(11) NOT NULL COMMENT '费用类型',
  `tax_rate` decimal(10, 2) NULL DEFAULT NULL COMMENT '税率',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `state` int(11) NOT NULL DEFAULT 0 COMMENT '启停状态(0:未启用,1:已启用,2:已停用)',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted_flag` int(11) NOT NULL DEFAULT 0 COMMENT '删除标识(0:未删除,1:已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_group_id`(`group_id`) USING BTREE,
  INDEX `idx_org_id`(`org_id`) USING BTREE,
  INDEX `idx_expense_type`(`expense_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '费项表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bms_expense_item_cust_ref
-- ----------------------------
DROP TABLE IF EXISTS `bms_expense_item_cust_ref`;
CREATE TABLE `bms_expense_item_cust_ref`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `cust_sub_id` bigint(20) NOT NULL COMMENT '所属客商ID',
  `cust_sub_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客商名称',
  `expense_item_id` bigint(20) NOT NULL COMMENT '费项ID',
  `group_id` bigint(20) NOT NULL COMMENT '所属集团ID',
  `org_id` bigint(20) NOT NULL COMMENT '所属组织ID',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted_flag` int(11) NOT NULL DEFAULT 0 COMMENT '删除标识(0:未删除,1:已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_cust_sub_id`(`cust_sub_id`) USING BTREE,
  INDEX `idx_expense_item_id`(`expense_item_id`) USING BTREE,
  INDEX `idx_org_id`(`org_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '费项客商关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bms_keyboard_dict
-- ----------------------------
DROP TABLE IF EXISTS `bms_keyboard_dict`;
CREATE TABLE `bms_keyboard_dict`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `keyboard_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '键盘名称',
  `keyboard_value` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '键盘值',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted_flag` int(11) NOT NULL DEFAULT 0 COMMENT '删除标识(0:未删除,1:已删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '键盘字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bms_price_strategy
-- ----------------------------
DROP TABLE IF EXISTS `bms_price_strategy`;
CREATE TABLE `bms_price_strategy`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `strategy_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '策略名称',
  `strategy_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '策略编码',
  `group_id` bigint(20) NOT NULL COMMENT '集团id',
  `org_id` bigint(20) NOT NULL COMMENT '组织id',
  `expense_type` int(11) NOT NULL COMMENT '费用类型',
  `cust_sub_id` bigint(20) NOT NULL COMMENT '计费对象ID',
  `expense_item_id` bigint(20) NOT NULL COMMENT '费项id',
  `billing_source_id` bigint(20) NOT NULL COMMENT '计费源ID',
  `price_config_id` bigint(20) NOT NULL COMMENT '价格配置id',
  `billing_rule_id` bigint(20) NOT NULL COMMENT '计费规则id',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted_flag` int(11) NOT NULL DEFAULT 0 COMMENT '删除标识(0:未删除,1:已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_strategy_no`(`strategy_no`) USING BTREE,
  INDEX `idx_group_id`(`group_id`) USING BTREE,
  INDEX `idx_org_id`(`org_id`) USING BTREE,
  INDEX `idx_expense_type`(`expense_type`) USING BTREE,
  INDEX `idx_cust_sub_id`(`cust_sub_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '计费策略表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bms_price_table
-- ----------------------------
DROP TABLE IF EXISTS `bms_price_table`;
CREATE TABLE `bms_price_table`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `price_table_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '价格表编码',
  `price_table_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '价格表名称',
  `group_id` bigint(20) NOT NULL COMMENT '集团id',
  `group_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '集团名称',
  `org_id` bigint(20) NOT NULL COMMENT '所属组织id',
  `org_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所属组织名称',
  `expense_type` int(11) NOT NULL COMMENT '费用类型',
  `expense_item_id` bigint(20) NOT NULL COMMENT '费项id',
  `expense_item_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '费项名称',
  `cust_sub_id` bigint(20) NOT NULL COMMENT '所属客商ID',
  `cust_sub_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所属客商名称',
  `purchase_sales_type` int(11) NOT NULL COMMENT '采销类型',
  `price_config_id` bigint(20) NOT NULL COMMENT '价格表配置id',
  `price_config_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '价格表配置名称',
  `tax_included_flag` int(11) NOT NULL DEFAULT 0 COMMENT '是否含税(0:不含税,1:含税)',
  `effective_date_start` date NOT NULL COMMENT '生效时间开始',
  `effective_date_end` date NOT NULL COMMENT '生效时间结束',
  `state` int(11) NOT NULL DEFAULT 0 COMMENT '状态(0:草稿,1:启用,2:停用)',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `version` double NOT NULL DEFAULT 1 COMMENT '版本号',
  `current_flag` int(11) NOT NULL DEFAULT 1 COMMENT '是否当前版本(0:历史版本,1:当前版本)',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted_flag` int(11) NOT NULL DEFAULT 0 COMMENT '删除标识(0:未删除,1:已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_group_id`(`group_id`) USING BTREE,
  INDEX `idx_org_id`(`org_id`) USING BTREE,
  INDEX `idx_expense_type`(`expense_type`) USING BTREE,
  INDEX `idx_expense_item_id`(`expense_item_id`) USING BTREE,
  INDEX `idx_cust_sub_id`(`cust_sub_id`) USING BTREE,
  INDEX `idx_price_config_id`(`price_config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '计费价格表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bms_price_table_config
-- ----------------------------
DROP TABLE IF EXISTS `bms_price_table_config`;
CREATE TABLE `bms_price_table_config`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `config_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '配置编码',
  `config_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '配置名称',
  `purchase_sales_type` int(11) NOT NULL COMMENT '采销类型',
  `group_id` bigint(20) NOT NULL COMMENT '所属集团ID',
  `org_id` bigint(20) NOT NULL COMMENT '所属组织ID',
  `group_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '集团名称',
  `org_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组织名称',
  `state` int(11) NOT NULL DEFAULT 0 COMMENT '启停状态(0:未启用,1:已启用,2:已停用)',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted_flag` int(11) NOT NULL DEFAULT 0 COMMENT '删除标识(0:未删除,1:已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_config_code`(`config_code`) USING BTREE,
  INDEX `idx_group_id`(`group_id`) USING BTREE,
  INDEX `idx_org_id`(`org_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '价格表配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bms_price_table_config_billing_item
-- ----------------------------
DROP TABLE IF EXISTS `bms_price_table_config_billing_item`;
CREATE TABLE `bms_price_table_config_billing_item`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `price_table_config_id` bigint(20) NOT NULL COMMENT '价格表配置ID',
  `billing_item_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '计费元素编码',
  `billing_item_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '计费元素名称',
  `group_id` bigint(20) NOT NULL COMMENT '所属集团ID',
  `org_id` bigint(20) NOT NULL COMMENT '所属组织ID',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `is_default` int(11) NOT NULL DEFAULT 0 COMMENT '是否默认(0:否,1:是)',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted_flag` int(11) NOT NULL DEFAULT 0 COMMENT '删除标识(0:未删除,1:已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_price_table_config_id`(`price_table_config_id`) USING BTREE,
  INDEX `idx_group_id`(`group_id`) USING BTREE,
  INDEX `idx_org_id`(`org_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '计费元素' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bms_price_table_config_cust_ref
-- ----------------------------
DROP TABLE IF EXISTS `bms_price_table_config_cust_ref`;
CREATE TABLE `bms_price_table_config_cust_ref`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `cust_sub_id` bigint(20) NOT NULL COMMENT '所属客商ID',
  `cust_sub_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客商名称',
  `price_table_config_id` bigint(20) NOT NULL COMMENT '价格表配置ID',
  `group_id` bigint(20) NOT NULL COMMENT '所属集团ID',
  `org_id` bigint(20) NOT NULL COMMENT '所属组织ID',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted_flag` int(11) NOT NULL DEFAULT 0 COMMENT '删除标识(0:未删除,1:已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_cust_sub_id`(`cust_sub_id`) USING BTREE,
  INDEX `idx_price_table_config_id`(`price_table_config_id`) USING BTREE,
  INDEX `idx_group_id`(`group_id`) USING BTREE,
  INDEX `idx_org_id`(`org_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '价格表配置客商关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bms_price_table_config_item
-- ----------------------------
DROP TABLE IF EXISTS `bms_price_table_config_item`;
CREATE TABLE `bms_price_table_config_item`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `price_table_config_id` bigint(20) NOT NULL COMMENT '价格表配置ID',
  `price_table_item_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '价格表单项编码',
  `price_table_item_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '价格表单项名称',
  `price_table_item_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据类型',
  `price_table_item_length` decimal(10, 2) NULL DEFAULT NULL COMMENT '数据长度',
  `dict_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据字典编码',
  `group_id` bigint(20) NOT NULL COMMENT '所属集团ID',
  `org_id` bigint(20) NOT NULL COMMENT '所属组织ID',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted_flag` int(11) NOT NULL DEFAULT 0 COMMENT '删除标识(0:未删除,1:已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_price_table_config_id`(`price_table_config_id`) USING BTREE,
  INDEX `idx_group_id`(`group_id`) USING BTREE,
  INDEX `idx_org_id`(`org_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '价格表单项配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bms_price_table_detail
-- ----------------------------
DROP TABLE IF EXISTS `bms_price_table_detail`;
CREATE TABLE `bms_price_table_detail`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` bigint(20) NOT NULL COMMENT '集团id',
  `group_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '集团名称',
  `org_id` bigint(20) NOT NULL COMMENT '所属组织id',
  `org_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所属组织名称',
  `price_table_id` bigint(20) NOT NULL COMMENT '价格表id',
  `price_table_detail_group` bigint(20) NOT NULL COMMENT '价格管理明细id',
  `price_config_item_id` bigint(20) NOT NULL COMMENT '价格表配置项id',
  `price_config_item_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '价格表配置项编码',
  `price_config_item_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '价格表配置项类型',
  `price_config_item_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '价格表配置项名称',
  `item_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '配置项值',
  `item_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置项值名称',
  `extra_attr` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '拓展字段(JSON格式)',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted_flag` int(11) NOT NULL DEFAULT 0 COMMENT '删除标识(0:未删除,1:已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_group_id`(`group_id`) USING BTREE,
  INDEX `idx_org_id`(`org_id`) USING BTREE,
  INDEX `idx_price_table_id`(`price_table_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '计费价格表明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bms_strategy_expense_dimension
-- ----------------------------
DROP TABLE IF EXISTS `bms_strategy_expense_dimension`;
CREATE TABLE `bms_strategy_expense_dimension`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `strategy_id` bigint(20) NOT NULL COMMENT '策略id',
  `strategy_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '策略编码',
  `strategy_type` int(11) NOT NULL COMMENT '策略类型(1:计费策略,2:记账策略)',
  `group_id` bigint(20) NOT NULL COMMENT '集团id',
  `org_id` bigint(20) NOT NULL COMMENT '组织id',
  `org_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组织名称',
  `billing_field` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '计费元素字段',
  `billing_field_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '计费元素字段名称',
  `billing_dimension_field_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '计费维度字段名称',
  `value_table_feild` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '取值表字段',
  `state` int(11) NOT NULL DEFAULT 0 COMMENT '状态',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted_flag` int(11) NOT NULL DEFAULT 0 COMMENT '删除标识(0:未删除,1:已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_strategy_id`(`strategy_id`) USING BTREE,
  INDEX `idx_strategy_no`(`strategy_no`) USING BTREE,
  INDEX `idx_strategy_type`(`strategy_type`) USING BTREE,
  INDEX `idx_group_id`(`group_id`) USING BTREE,
  INDEX `idx_org_id`(`org_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '策略维度表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bms_strategy_price
-- ----------------------------
DROP TABLE IF EXISTS `bms_strategy_price`;
CREATE TABLE `bms_strategy_price`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `strategy_id` bigint(20) NOT NULL COMMENT '计费策略id',
  `group_id` bigint(20) NOT NULL COMMENT '集团id',
  `org_id` bigint(20) NOT NULL COMMENT '组织id',
  `price_table_id` bigint(20) NOT NULL COMMENT '价格表id',
  `price_table_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '价格配置名称',
  `effective_date_start` date NOT NULL COMMENT '生效日期开始',
  `effective_date_end` date NOT NULL COMMENT '生效日期结束',
  `sort_num` int(11) NOT NULL DEFAULT 0 COMMENT '顺序',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted_flag` int(11) NOT NULL DEFAULT 0 COMMENT '删除标识(0:未删除,1:已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_strategy_id`(`strategy_id`) USING BTREE,
  INDEX `idx_group_id`(`group_id`) USING BTREE,
  INDEX `idx_org_id`(`org_id`) USING BTREE,
  INDEX `idx_price_table_id`(`price_table_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '计费策略价格表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bms_strategy_screen_condition
-- ----------------------------
DROP TABLE IF EXISTS `bms_strategy_screen_condition`;
CREATE TABLE `bms_strategy_screen_condition`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `strategy_id` bigint(20) NOT NULL COMMENT '策略id',
  `group_id` bigint(20) NOT NULL COMMENT '集团id',
  `org_id` bigint(20) NOT NULL COMMENT '组织id',
  `billing_field` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '匹配元素',
  `billing_field_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '匹配元素名称',
  `value_table_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '取值表名称',
  `value_table_type` int(11) NULL DEFAULT NULL COMMENT '取值表类型',
  `value_table_feild` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '取值表字段',
  `match_method` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '匹配方式',
  `match_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '匹配值',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted_flag` int(11) NOT NULL DEFAULT 0 COMMENT '删除标识(0:未删除,1:已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_strategy_id`(`strategy_id`) USING BTREE,
  INDEX `idx_group_id`(`group_id`) USING BTREE,
  INDEX `idx_org_id`(`org_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '计费策略筛选条件表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
