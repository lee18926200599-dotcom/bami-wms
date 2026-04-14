/*
 Navicat Premium Data Transfer

 Source Server         : 八米新加坡
 Source Server Type    : MySQL
 Source Server Version : 100624
 Source Host           : 47.236.202.11:3306
 Source Schema         : zzz_bms_bill

 Target Server Type    : MySQL
 Target Server Version : 100624
 File Encoding         : 65001

 Date: 14/04/2026 09:14:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bms_account_bill
-- ----------------------------
DROP TABLE IF EXISTS `bms_account_bill`;
CREATE TABLE `bms_account_bill`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `account_bill_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '记账单号',
  `settlement_bill_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结算单号',
  `account_strategy_id` bigint(20) NOT NULL COMMENT '记账策略ID',
  `account_strategy_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '策略名称',
  `account_strategy_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '策略编码',
  `group_id` bigint(20) NOT NULL COMMENT '集团id',
  `org_id` bigint(20) NOT NULL COMMENT '组织id',
  `org_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组织名称',
  `expense_type` int(11) NOT NULL COMMENT '费用类型',
  `cust_sub_id` bigint(20) NOT NULL COMMENT '计账对象ID',
  `accounting_cycle_type` int(11) NOT NULL COMMENT '记账周期类型',
  `accounting_cycle` int(11) NOT NULL COMMENT '记账周期',
  `cust_sub_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '计费对象名称',
  `account_date_start` date NOT NULL COMMENT '记账开始日期',
  `account_date_end` date NOT NULL COMMENT '记账结束日期',
  `amount` decimal(20, 4) NOT NULL DEFAULT 0.0000 COMMENT '含税金额',
  `untaxed_amount` decimal(20, 4) NOT NULL DEFAULT 0.0000 COMMENT '未税金额',
  `tax_amount` decimal(20, 4) NOT NULL DEFAULT 0.0000 COMMENT '税额',
  `cancel_date` datetime(0) NULL DEFAULT NULL COMMENT '作废日期',
  `cancel_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '作废人',
  `confirm_date` datetime(0) NULL DEFAULT NULL COMMENT '确认日期',
  `confirm_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '确认人',
  `finish_date` datetime(0) NULL DEFAULT NULL COMMENT '对账完成日期',
  `finish_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对账确认人',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `state` int(11) NOT NULL DEFAULT 0 COMMENT '状态',
  `created_by` bigint(20) NOT NULL COMMENT '创建人ID',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `created_date` datetime(0) NOT NULL COMMENT '创建日期',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人id',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted_flag` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除(0:未删除,1:已删除)',
  `extends_field` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '扩展字段(JSON格式)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_settlement_bill_no`(`settlement_bill_no`) USING BTREE,
  INDEX `idx_account_strategy_id`(`account_strategy_id`) USING BTREE,
  INDEX `idx_group_id`(`group_id`) USING BTREE,
  INDEX `idx_org_id`(`org_id`) USING BTREE,
  INDEX `idx_cust_sub_id`(`cust_sub_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '记账单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bms_artificial_account
-- ----------------------------
DROP TABLE IF EXISTS `bms_artificial_account`;
CREATE TABLE `bms_artificial_account`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `artificial_account_config_id` bigint(20) NOT NULL COMMENT '人工账表单id',
  `artificial_account_config_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '人工账表单编码',
  `artificial_account_config_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '人工账表单名称',
  `group_id` bigint(20) NOT NULL COMMENT '所属集团ID',
  `org_id` bigint(20) NOT NULL COMMENT '所属组织ID',
  `org_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所属组织名称',
  `cust_sub_id` bigint(20) NOT NULL COMMENT '所属客商ID',
  `cust_sub_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所属客商名称',
  `expense_bill_id` bigint(20) NULL DEFAULT NULL COMMENT '费用单id',
  `expense_bill_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '费用单号',
  `expense_item_id` bigint(20) NOT NULL COMMENT '费项id',
  `expense_item_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '费项名称',
  `expense_type` int(11) NOT NULL COMMENT '费用类型',
  `artificial_account_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '人工账单号',
  `business_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '业务单号',
  `business_time` datetime(0) NOT NULL COMMENT '业务发生时间',
  `amount` decimal(20, 4) NOT NULL DEFAULT 0.0000 COMMENT '含税金额',
  `untaxed_amount` decimal(20, 4) NOT NULL DEFAULT 0.0000 COMMENT '未税金额',
  `tax_amount` decimal(20, 4) NOT NULL DEFAULT 0.0000 COMMENT '税额',
  `tax_rate` decimal(10, 4) NULL DEFAULT NULL COMMENT '税率',
  `status` int(11) NOT NULL DEFAULT 0 COMMENT '状态',
  `confirm_time` datetime(0) NULL DEFAULT NULL COMMENT '确认时间',
  `confirm_by` bigint(20) NULL DEFAULT NULL COMMENT '确认人ID',
  `confirm_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '确认人名称',
  `invalidate_time` datetime(0) NULL DEFAULT NULL COMMENT '作废时间',
  `invalidate_by` bigint(20) NULL DEFAULT NULL COMMENT '作废人ID',
  `invalidate_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '作废人名称',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted_flag` int(11) NOT NULL DEFAULT 0 COMMENT '删除标识(0:未删除,1:已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_artificial_account_config_id`(`artificial_account_config_id`) USING BTREE,
  INDEX `idx_group_id`(`group_id`) USING BTREE,
  INDEX `idx_org_id`(`org_id`) USING BTREE,
  INDEX `idx_cust_sub_id`(`cust_sub_id`) USING BTREE,
  INDEX `idx_expense_bill_no`(`expense_bill_no`) USING BTREE,
  INDEX `idx_expense_item_id`(`expense_item_id`) USING BTREE,
  INDEX `idx_business_no`(`business_no`) USING BTREE,
  INDEX `idx_business_time`(`business_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '人工账单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bms_artificial_account_config
-- ----------------------------
DROP TABLE IF EXISTS `bms_artificial_account_config`;
CREATE TABLE `bms_artificial_account_config`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `artificial_account_config_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '人工账表单编码',
  `artificial_account_config_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '人工账表单名称',
  `group_id` bigint(20) NOT NULL COMMENT '所属集团ID',
  `org_id` bigint(20) NOT NULL COMMENT '所属组织ID',
  `org_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所属组织名称',
  `physics_table_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '物理表名',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `state` int(11) NOT NULL DEFAULT 0 COMMENT '状态',
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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '人工账单配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bms_artificial_account_config_cust_ref
-- ----------------------------
DROP TABLE IF EXISTS `bms_artificial_account_config_cust_ref`;
CREATE TABLE `bms_artificial_account_config_cust_ref`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `cust_sub_id` bigint(20) NOT NULL COMMENT '所属客商ID',
  `cust_sub_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客商名称',
  `artificial_account_config_id` bigint(20) NOT NULL COMMENT '人工账表单配置id',
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
  INDEX `idx_artificial_account_config_id`(`artificial_account_config_id`) USING BTREE,
  INDEX `idx_group_id`(`group_id`) USING BTREE,
  INDEX `idx_org_id`(`org_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '人工账单配置客商关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bms_artificial_account_config_item
-- ----------------------------
DROP TABLE IF EXISTS `bms_artificial_account_config_item`;
CREATE TABLE `bms_artificial_account_config_item`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `artificial_account_config_id` bigint(20) NOT NULL COMMENT '人工账单表配置ID',
  `artificial_account_field_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字段编码',
  `artificial_account_field_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字段名称',
  `artificial_account_field_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字段类型',
  `artificial_account_field_length` decimal(10, 2) NULL DEFAULT NULL COMMENT '字段长度',
  `dict_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据字典编码',
  `dict_desc` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据字典描述',
  `group_id` bigint(20) NOT NULL COMMENT '所属集团ID',
  `org_id` bigint(20) NOT NULL COMMENT '所属组织ID',
  `org_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所属组织名称',
  `null_flag` int(11) NOT NULL DEFAULT 0 COMMENT '是否为空(0:否,1:是)',
  `billing_dimension_flag` int(11) NOT NULL DEFAULT 0 COMMENT '是否计费维度(0:否,1:是)',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted_flag` int(11) NOT NULL DEFAULT 0 COMMENT '删除标识(0:未删除,1:已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_artificial_account_config_id`(`artificial_account_config_id`) USING BTREE,
  INDEX `idx_group_id`(`group_id`) USING BTREE,
  INDEX `idx_org_id`(`org_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '人工账单表配置扩展字段表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bms_expense_adjust_bill
-- ----------------------------
DROP TABLE IF EXISTS `bms_expense_adjust_bill`;
CREATE TABLE `bms_expense_adjust_bill`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `expense_bill_id` bigint(20) NULL DEFAULT NULL COMMENT '费用单id',
  `expense_bill_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '费用单号',
  `account_bill_id` bigint(20) NULL DEFAULT NULL COMMENT '对账id',
  `account_bill_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对账单号',
  `adjust_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调整单号',
  `expense_item_id` bigint(20) NOT NULL COMMENT '费项id',
  `expense_item_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '费项名称',
  `adjust_object_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调账对象类型',
  `expense_type` int(11) NOT NULL COMMENT '费用类型',
  `group_id` bigint(20) NOT NULL COMMENT '所属集团ID',
  `org_id` bigint(20) NOT NULL COMMENT '所属组织ID',
  `org_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所属组织名称',
  `cust_sub_id` bigint(20) NOT NULL COMMENT '记账对象ID',
  `cust_sub_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '记账对象名称',
  `adjust_amount` decimal(20, 4) NOT NULL DEFAULT 0.0000 COMMENT '含税调账金额',
  `adjust_untaxed_amount` decimal(20, 4) NOT NULL DEFAULT 0.0000 COMMENT '未税调账金额',
  `tax_amount` decimal(20, 4) NOT NULL DEFAULT 0.0000 COMMENT '税额',
  `tax_rate` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '税率',
  `warehouse_id` bigint(20) NULL DEFAULT NULL COMMENT '仓库ID',
  `warehouse_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库名称',
  `state` int(11) NOT NULL DEFAULT 0 COMMENT '启停状态(0:未启用,1:已启用,2:已停用)',
  `confirm_date` datetime(0) NULL DEFAULT NULL COMMENT '确认时间',
  `confirm_by` bigint(20) NULL DEFAULT NULL COMMENT '确认人ID',
  `confirm_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '确认人名称',
  `reason` int(11) NULL DEFAULT NULL COMMENT '调账原因',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted_flag` int(11) NOT NULL DEFAULT 0 COMMENT '删除标识(0:未删除,1:已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_expense_bill_id`(`expense_bill_id`) USING BTREE,
  INDEX `idx_expense_bill_no`(`expense_bill_no`) USING BTREE,
  INDEX `idx_account_bill_id`(`account_bill_id`) USING BTREE,
  INDEX `idx_account_bill_no`(`account_bill_no`) USING BTREE,
  INDEX `idx_expense_item_id`(`expense_item_id`) USING BTREE,
  INDEX `idx_group_id`(`group_id`) USING BTREE,
  INDEX `idx_org_id`(`org_id`) USING BTREE,
  INDEX `idx_cust_sub_id`(`cust_sub_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '费用调整单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bms_expense_bill
-- ----------------------------
DROP TABLE IF EXISTS `bms_expense_bill`;
CREATE TABLE `bms_expense_bill`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `bill_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '费用单编码',
  `expense_type` int(11) NOT NULL COMMENT '费用类型',
  `group_id` bigint(20) NOT NULL COMMENT '所属集团ID',
  `org_id` bigint(20) NOT NULL COMMENT '所属组织ID',
  `org_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所属组织名称',
  `cust_sub_id` bigint(20) NOT NULL COMMENT '客户ID',
  `cust_sub_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户名称',
  `state` int(11) NOT NULL DEFAULT 0 COMMENT '状态',
  `business_time` date NOT NULL COMMENT '业务发生时间',
  `amount` decimal(20, 4) NOT NULL DEFAULT 0.0000 COMMENT '含税金额',
  `untaxed_amount` decimal(20, 4) NOT NULL DEFAULT 0.0000 COMMENT '未税金额',
  `tax_amount` decimal(20, 4) NOT NULL DEFAULT 0.0000 COMMENT '税额',
  `tax_rate` decimal(10, 4) NULL DEFAULT NULL COMMENT '税率',
  `warehouse_id` bigint(20) NULL DEFAULT NULL COMMENT '仓库ID',
  `warehouse_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库名称',
  `expense_item_id` bigint(20) NOT NULL COMMENT '费项ID',
  `billing_source_id` bigint(20) NOT NULL COMMENT '计费源ID',
  `price_table_id` bigint(20) NULL DEFAULT NULL COMMENT '价格表ID',
  `billing_strategy_id` bigint(20) NULL DEFAULT NULL COMMENT '计费策略ID',
  `conbine_code` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '计费策略维度字段值-MD5值',
  `conbine_desc` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '计费策略维度字段值',
  `extends_field` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展字段',
  `source_type` int(11) NOT NULL COMMENT '来源类型',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `cancel_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '作废人',
  `cancel_date` datetime(0) NULL DEFAULT NULL COMMENT '作废时间',
  `account_bill_id` bigint(20) NULL DEFAULT NULL COMMENT '记账单ID',
  `account_bill_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '记账单号',
  `account_start_date` date NULL DEFAULT NULL COMMENT '记账开始日期',
  `account_end_date` date NULL DEFAULT NULL COMMENT '记账结束日期',
  `bill_num` int(11) NOT NULL DEFAULT 1 COMMENT '账单数量',
  `created_by` bigint(20) NOT NULL COMMENT '创建人ID',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人ID',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted_flag` int(11) NOT NULL DEFAULT 0 COMMENT '删除标识(0:未删除,1:已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_group_id`(`group_id`) USING BTREE,
  INDEX `idx_org_id`(`org_id`) USING BTREE,
  INDEX `idx_cust_sub_id`(`cust_sub_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '应收费用单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bms_expense_bill_ref
-- ----------------------------
DROP TABLE IF EXISTS `bms_expense_bill_ref`;
CREATE TABLE `bms_expense_bill_ref`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `bill_id` bigint(20) NOT NULL COMMENT '费用单号ID',
  `bill_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '费用单号',
  `expense_type` int(11) NOT NULL COMMENT '费用类型',
  `group_id` bigint(20) NOT NULL COMMENT '所属集团ID',
  `org_id` bigint(20) NOT NULL COMMENT '所属组织ID',
  `org_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所属组织名称',
  `cust_sub_id` bigint(20) NOT NULL COMMENT '客户ID',
  `cust_sub_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户名称',
  `state` int(11) NOT NULL DEFAULT 0 COMMENT '启停状态(0:未启用,1:已启用,2:已停用)',
  `business_id` bigint(20) NOT NULL COMMENT '业务ID',
  `business_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务单号',
  `business_time` date NOT NULL COMMENT '业务发生时间',
  `amount` decimal(20, 4) NOT NULL DEFAULT 0.0000 COMMENT '含税金额',
  `untaxed_amount` decimal(20, 4) NOT NULL DEFAULT 0.0000 COMMENT '未税金额',
  `tax_amount` decimal(20, 4) NOT NULL DEFAULT 0.0000 COMMENT '税额',
  `tax_rate` decimal(10, 4) NULL DEFAULT NULL COMMENT '税率',
  `warehouse_id` bigint(20) NULL DEFAULT NULL COMMENT '仓库ID',
  `warehouse_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库名称',
  `expense_item_id` bigint(20) NOT NULL COMMENT '费项ID',
  `billing_source_id` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '计费源ID',
  `price_table_id` bigint(20) NULL DEFAULT NULL COMMENT '价格表ID',
  `billing_strategy_id` bigint(20) NULL DEFAULT NULL COMMENT '计费策略ID',
  `source_type` int(11) NOT NULL COMMENT '来源类型',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `created_by` bigint(20) NOT NULL COMMENT '创建人ID',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NOT NULL COMMENT '创建日期',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人id',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted_flag` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除(0:未删除,1:已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_bill_id`(`bill_id`) USING BTREE,
  INDEX `idx_bill_no`(`bill_no`) USING BTREE,
  INDEX `idx_group_id`(`group_id`) USING BTREE,
  INDEX `idx_org_id`(`org_id`) USING BTREE,
  INDEX `idx_cust_sub_id`(`cust_sub_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '费用单业务单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bms_settlement_bill
-- ----------------------------
DROP TABLE IF EXISTS `bms_settlement_bill`;
CREATE TABLE `bms_settlement_bill`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `settlement_bill_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '结算单号',
  `group_id` bigint(20) NOT NULL COMMENT '集团id',
  `org_id` bigint(20) NOT NULL COMMENT '组织id',
  `org_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组织名称',
  `expense_type` int(11) NOT NULL COMMENT '费用类型',
  `cust_sub_id` bigint(20) NOT NULL COMMENT '计账对象ID',
  `cust_sub_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '计费对象名称',
  `accounting_cycle_type` int(11) NOT NULL COMMENT '记账周期类型',
  `accounting_cycle` int(11) NOT NULL COMMENT '记账周期',
  `account_date_start` date NOT NULL COMMENT '记账开始日期',
  `account_date_end` date NOT NULL COMMENT '记账结束日期',
  `amount` decimal(20, 4) NOT NULL DEFAULT 0.0000 COMMENT '含税金额',
  `untaxed_amount` decimal(20, 4) NOT NULL DEFAULT 0.0000 COMMENT '未税金额',
  `tax_amount` decimal(20, 4) NOT NULL DEFAULT 0.0000 COMMENT '税额',
  `confirm_date` datetime(0) NULL DEFAULT NULL COMMENT '确认日期',
  `confirm_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '确认人',
  `extends_feild` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '扩展字段(JSON格式)',
  `account_bill_num` int(11) NOT NULL DEFAULT 0 COMMENT '对账单数',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `state` int(11) NOT NULL DEFAULT 0 COMMENT '状态',
  `created_by` bigint(20) NOT NULL COMMENT '创建人ID',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `created_date` datetime(0) NOT NULL COMMENT '创建日期',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人id',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted_flag` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除(0:未删除,1:已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_group_id`(`group_id`) USING BTREE,
  INDEX `idx_org_id`(`org_id`) USING BTREE,
  INDEX `idx_cust_sub_id`(`cust_sub_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '结算单表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
