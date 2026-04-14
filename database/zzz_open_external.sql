/*
 Navicat Premium Data Transfer

 Source Server         : 八米新加坡
 Source Server Type    : MySQL
 Source Server Version : 100624
 Source Host           : 47.236.202.11:3306
 Source Schema         : zzz_open_external

 Target Server Type    : MySQL
 Target Server Version : 100624
 File Encoding         : 65001

 Date: 14/04/2026 09:16:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for open_external_order_task
-- ----------------------------
DROP TABLE IF EXISTS `open_external_order_task`;
CREATE TABLE `open_external_order_task`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `upstream_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上游类型',
  `warehouse_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '仓库编码',
  `owner_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '货主编码',
  `task_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '指令类型',
  `bill_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '外部订单类型',
  `business_type` tinyint(4) NULL DEFAULT NULL COMMENT '业务类型',
  `ext_platform_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '平台编码',
  `ext_platform_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '平台订单号',
  `ship_bill_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '运单号',
  `supplier_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '供应商编码',
  `supplier_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '供应商名称',
  `store_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '店铺编码',
  `store_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '店铺名称',
  `delivery_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '承运商编码',
  `task_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '外部订单号',
  `method_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接口类型',
  `task_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '指令信息',
  `task_status` int(11) NOT NULL COMMENT '指令状态',
  `retry_flag` tinyint(4) NULL DEFAULT NULL COMMENT '重试标识 0：不重试 1：重试',
  `retry_count` int(11) NULL DEFAULT NULL COMMENT '重试次数',
  `next_execute_date` datetime(0) NULL DEFAULT NULL COMMENT '下次重试时间',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `created_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `created_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_modify_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后修改人',
  `last_modify_date` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  `version` int(11) NOT NULL COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_bill_no`(`task_no`) USING BTREE,
  INDEX `idx_owner`(`owner_code`) USING BTREE,
  INDEX `idx_warehouse_code`(`warehouse_code`, `task_status`, `business_type`, `created_date`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1273 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '指令表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for open_external_postback_order_task
-- ----------------------------
DROP TABLE IF EXISTS `open_external_postback_order_task`;
CREATE TABLE `open_external_postback_order_task`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `warehouse_code` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '仓库编码',
  `owner_code` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '货主编码',
  `order_type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '外部订单类型',
  `bill_type` int(11) NOT NULL COMMENT '系统单据类型',
  `business_type` tinyint(1) NULL DEFAULT NULL COMMENT '业务类型：1：销售出库 2：退供出库 3：销退 4：入库',
  `task_no` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '外部单据号',
  `wms_order_no` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '系统单号',
  `work_no` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '作业单号',
  `business_no` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '业务单号',
  `ext_platform_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '平台',
  `delivery_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '承运商',
  `ext_platform_no` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '平台单号',
  `ship_bill_no` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '运单号',
  `supplier_code` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '供应商编码',
  `supplier_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '供应商名称',
  `method_type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '接口类型',
  `task_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '指令信息',
  `task_status` int(11) NOT NULL COMMENT '指令状态',
  `retry_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否能重推-0:不能 1：能',
  `retry_count` int(11) NULL DEFAULT NULL COMMENT '重试次数',
  `next_execute_date` datetime(0) NULL DEFAULT NULL COMMENT '下次重试时间',
  `receive_date` datetime(0) NULL DEFAULT NULL COMMENT '接单时间',
  `remark` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '备注',
  `created_by` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `created_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_modify_by` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '最后修改人',
  `last_modify_date` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  `version` int(11) NOT NULL COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 83 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '指令表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
