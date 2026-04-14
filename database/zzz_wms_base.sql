/*
 Navicat Premium Data Transfer

 Source Server         : 八米新加坡
 Source Server Type    : MySQL
 Source Server Version : 100624
 Source Host           : 47.236.202.11:3306
 Source Schema         : zzz_wms_base

 Target Server Type    : MySQL
 Target Server Version : 100624
 File Encoding         : 65001

 Date: 14/04/2026 09:16:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for base_area
-- ----------------------------
DROP TABLE IF EXISTS `base_area`;
CREATE TABLE `base_area`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '库区id',
  `service_provider_id` bigint(20) UNSIGNED NOT NULL COMMENT '服务商id',
  `service_provider_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓储服务商名称',
  `warehouse_area_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '库区编码',
  `warehouse_area_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '库区名称',
  `area_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '库区类型',
  `warehouse_id` bigint(20) NOT NULL COMMENT '仓库id',
  `warehouse_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '仓库编号',
  `warehouse_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '仓库名称',
  `purpose_attr` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用途属性',
  `replenish_attr` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '补货属性',
  `return_attr` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退货属性',
  `location_owner_attr` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '货位货主属性',
  `item_storage_attr` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品存放属性',
  `location_storage_attr` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '货位存放属性',
  `goods_attr` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '货物属性',
  `circulation_attr` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流转属性',
  `warehouse_category_id` bigint(20) NULL DEFAULT NULL COMMENT '仓储分类id',
  `warehouse_category_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓储分类名称',
  `temperature_attr` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '温控属性',
  `storage_spec` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '存储规格',
  `item_abc_category` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品ABC品类',
  `belong_floor` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '隶属楼层',
  `area_no` int(11) NULL DEFAULT NULL COMMENT '库区序号',
  `expensive_goods_flag` tinyint(3) UNSIGNED NULL DEFAULT NULL COMMENT '是否贵品',
  `state` tinyint(3) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态（0-已创建，1-启用，2-停用）',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '最后修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `deleted_flag` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除标识（0-未删除，1-已删除）',
  `version` int(10) UNSIGNED NOT NULL DEFAULT 1 COMMENT '不自增，乐观锁版本控制',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_base`(`warehouse_id`, `service_provider_id`) USING BTREE,
  INDEX `idx_area_code`(`warehouse_area_code`) USING BTREE,
  INDEX `idx_state`(`state`) USING BTREE,
  INDEX `idx_attr1`(`purpose_attr`, `circulation_attr`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 581 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '货区' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of base_area
-- ----------------------------
INSERT INTO `base_area` VALUES (580, 18965, NULL, '2414', '2121', 'ITEM_AREA', 80, 'C001', '江汉区一区仓库1', 'SHIP', 'kb', 'KT', 'ONE_BY_ONE', 'ONE_BY_ONE', 'ONE_BY_ONE', 'GOOD', 'TEM_STORAGE', NULL, NULL, NULL, NULL, NULL, NULL, 1241, 0, 1, 139, 'ysadmin', '2026-02-27 12:12:38', 139, 'ysadmin', '2026-02-27 12:13:32', NULL, 0, 0);

-- ----------------------------
-- Table structure for base_box_type
-- ----------------------------
DROP TABLE IF EXISTS `base_box_type`;
CREATE TABLE `base_box_type`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `service_provider_id` bigint(20) NULL DEFAULT NULL COMMENT '仓储服务商id',
  `service_provider_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓储服务商名称',
  `warehouse_id` bigint(20) NOT NULL COMMENT '仓库id',
  `warehouse_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '仓库编号',
  `warehouse_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '仓库名称',
  `owner_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '货主ID',
  `owner_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '货主编码',
  `owner_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '货主名称',
  `box_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '箱型编码',
  `box_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '箱型名称',
  `group_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分组号',
  `reserve_rate` decimal(20, 2) NULL DEFAULT NULL COMMENT '预留系数',
  `box_length` decimal(10, 2) NOT NULL COMMENT '长',
  `box_width` decimal(10, 2) NOT NULL COMMENT '宽',
  `box_height` decimal(10, 2) NOT NULL COMMENT '高',
  `box_volume` decimal(20, 2) NOT NULL COMMENT '体积',
  `box_weight` decimal(10, 2) NULL DEFAULT NULL COMMENT '重量',
  `wph_flag` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否唯品会专用箱型',
  `tk_flag` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否淘客订单专用箱型',
  `state` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '状态（0-已创建，1-启用，2-停用）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `created_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` int(11) NULL DEFAULT 0 COMMENT '乐观锁控制',
  `deleted_flag` int(11) NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_boxcode_whidonid__spid`(`box_code`, `warehouse_id`, `owner_id`, `service_provider_id`) USING BTREE,
  INDEX `idx_boxcode_deletedflag`(`box_code`, `deleted_flag`) USING BTREE,
  INDEX `idx_state`(`state`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 74 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '箱型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_container
-- ----------------------------
DROP TABLE IF EXISTS `base_container`;
CREATE TABLE `base_container`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `service_provider_id` bigint(20) UNSIGNED NOT NULL COMMENT '服务商id',
  `warehouse_id` bigint(20) NOT NULL COMMENT '仓库id',
  `warehouse_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '仓库编号',
  `warehouse_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '仓库名称',
  `container_category` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '容器类别',
  `container_type_id` bigint(20) NOT NULL COMMENT '容器类型id',
  `container_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '容器编码',
  `length` decimal(20, 2) UNSIGNED NULL DEFAULT NULL COMMENT '长',
  `width` decimal(20, 2) UNSIGNED NULL DEFAULT NULL COMMENT '宽',
  `height` decimal(20, 2) UNSIGNED NULL DEFAULT NULL COMMENT '高',
  `volume` decimal(20, 6) UNSIGNED NULL DEFAULT NULL COMMENT '体积',
  `max_load_bear` decimal(20, 3) UNSIGNED NULL DEFAULT NULL COMMENT '最大承重',
  `column_num` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '列数',
  `row_num` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '行数',
  `lattice_flag` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否带格子',
  `lattices_order` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '格号顺序',
  `occupy_state` tinyint(4) NULL DEFAULT 0 COMMENT '占用状态 0-空闲 1-占用\n',
  `occupy_document_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '占用单据类型\n',
  `occupy_document_no` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '占用单据编号\n',
  `state` tinyint(3) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态（0-已创建，1-启用，2-停用）',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `deleted_flag` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除字段(0未删除，1已删除)',
  `version` int(10) UNSIGNED NOT NULL DEFAULT 1 COMMENT '不自增，乐观锁版本控制',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_container_code`(`container_code`) USING BTREE,
  INDEX `idx_base`(`warehouse_id`, `service_provider_id`) USING BTREE,
  INDEX `idx_state`(`state`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 296 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '容器表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_container_type
-- ----------------------------
DROP TABLE IF EXISTS `base_container_type`;
CREATE TABLE `base_container_type`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `service_provider_id` bigint(20) UNSIGNED NOT NULL COMMENT '服务商id',
  `warehouse_id` bigint(20) NOT NULL COMMENT '仓库id',
  `warehouse_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '仓库编号',
  `warehouse_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '仓库名称',
  `container_category` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '容器类别',
  `container_type` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '容器类型',
  `length` decimal(20, 2) UNSIGNED NULL DEFAULT NULL COMMENT '长',
  `width` decimal(20, 2) UNSIGNED NULL DEFAULT NULL COMMENT '宽',
  `height` decimal(20, 2) UNSIGNED NULL DEFAULT NULL COMMENT '高',
  `volume` decimal(20, 6) UNSIGNED NULL DEFAULT NULL COMMENT '体积',
  `max_load_bear` decimal(20, 3) UNSIGNED NULL DEFAULT NULL COMMENT '最大承重',
  `lattice_flag` tinyint(1) NULL DEFAULT 0 COMMENT '是否带格子',
  `column_num` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '列数',
  `row_num` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '行数',
  `lattices_order` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '格号顺序',
  `rule_type` tinyint(4) NULL DEFAULT NULL COMMENT '规则类型（1-数字 2-数字+字母）',
  `state` tinyint(3) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态（0-已创建，1-启用，2-停用）',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  `deleted_flag` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除字段(0未删除，1已删除)',
  `version` int(10) UNSIGNED NOT NULL DEFAULT 1 COMMENT '不自增，乐观锁版本控制',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 75 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '容器类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of base_container_type
-- ----------------------------
INSERT INTO `base_container_type` VALUES (74, 18965, 80, 'C001', '江汉区一区仓库1', 'CONTAINER_TP', '12', 12.00, 12.00, 12.00, 0.001700, 2.000, 0, NULL, NULL, NULL, NULL, 0, NULL, 139, 'ysadmin', '2026-02-27 12:17:30', NULL, 'ysadmin', '2026-02-27 12:17:30', 0, 0);

-- ----------------------------
-- Table structure for base_item
-- ----------------------------
DROP TABLE IF EXISTS `base_item`;
CREATE TABLE `base_item`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `owner_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '货主编码',
  `owner_id` bigint(20) UNSIGNED NOT NULL COMMENT '货主id',
  `owner_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '货主名称',
  `barcode` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品条码',
  `service_provider_id` bigint(20) NULL DEFAULT NULL COMMENT '仓储服务商id',
  `item_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品编码',
  `cargo_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '货号',
  `item_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品名称',
  `short_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品简称',
  `mnemonic_code` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '助记码',
  `ext_item_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上游系统编码',
  `origin` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产地',
  `factory` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生产企业',
  `model` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '型号',
  `capacity` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规格',
  `business_industry` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属经营范围',
  `item_brand` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '品牌',
  `unit_grade` int(11) NULL DEFAULT NULL COMMENT '单位级别',
  `batch_mgmt_flag` int(11) NOT NULL COMMENT '是否管理批次号',
  `picking_area` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '指定拣货位',
  `storage_area` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '指定备货位',
  `picking_area_select_flag` tinyint(1) NULL DEFAULT NULL COMMENT '备货区拣选设置 0:不参与 1：参与',
  `picking_area_settings` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备货区拣选设置',
  `manage_expiry_date_flag` tinyint(1) NULL DEFAULT 0 COMMENT '是否管理保质期（1有，默认0无）',
  `sn_manage_flag` tinyint(1) NULL DEFAULT 0 COMMENT '是否启用SN管理（1有，默认0无）',
  `explosives_flag` tinyint(1) NULL DEFAULT 0 COMMENT '是否易爆品（1有，默认0无）',
  `inflammables_flag` tinyint(1) NULL DEFAULT 0 COMMENT '是否易燃品（1有，默认0无）',
  `dangerous_flag` tinyint(1) NULL DEFAULT 0 COMMENT '是否危险品（1有，默认0无）',
  `drugs_flag` tinyint(1) NULL DEFAULT 0 COMMENT '是否有毒品（1有，默认0无）',
  `contraband_flag` tinyint(1) NULL DEFAULT 0 COMMENT '是否禁航品（1有，默认0无）',
  `fragile_flag` tinyint(1) NULL DEFAULT 0 COMMENT '是否易碎品（1有，默认0无）',
  `shelf_life` bigint(20) NULL DEFAULT NULL COMMENT '保质期',
  `prohibitedcollection_time` bigint(20) NULL DEFAULT NULL COMMENT '禁收剩余时效',
  `advent_time` bigint(20) NULL DEFAULT NULL COMMENT '临期剩余时效',
  `forbid_sale_time` bigint(20) NULL DEFAULT NULL COMMENT '禁售剩余时效',
  `unit_convert` decimal(19, 9) NULL DEFAULT 0.000000000 COMMENT '计量单位转换系数',
  `unit_convert_text` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '整零转换',
  `state` tinyint(3) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态分 已创建（0），停用（1），启用（2）',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `created_by` bigint(20) NOT NULL COMMENT '创建人id',
  `created_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `created_date` datetime(0) NOT NULL COMMENT '创建日期',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人id',
  `modified_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted_flag` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除字段(0未删除，1已删除)',
  `version` int(10) UNSIGNED NOT NULL DEFAULT 1 COMMENT '不自增，乐观锁版本控制',
  `sn_code_rule` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'SN编码规则',
  `sn_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'SN 编码',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_owner_item`(`service_provider_id`, `owner_id`, `item_code`) USING BTREE,
  INDEX `idx_item_code`(`item_code`) USING BTREE,
  INDEX `idx_item_name`(`item_name`) USING BTREE,
  INDEX `idx_item_short_name`(`short_name`) USING BTREE,
  INDEX `idx_item_mnemonic_code`(`mnemonic_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 67970 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_item_image
-- ----------------------------
DROP TABLE IF EXISTS `base_item_image`;
CREATE TABLE `base_item_image`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `item_id` bigint(20) NULL DEFAULT NULL COMMENT '商品主表id',
  `image_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片名称',
  `image_type` int(11) NULL DEFAULT NULL COMMENT '图片类别（1主图 0辅图）',
  `pack_level` int(11) NULL DEFAULT NULL COMMENT '包装层级',
  `image` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品图片',
  `locations` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '点位坐标',
  `check_main` int(11) NULL DEFAULT NULL COMMENT '需设置主图关键区域（1需设置 默认0不需要）',
  `remark` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `owner_code` bigint(20) NULL DEFAULT NULL COMMENT '货主code',
  `state` int(11) NOT NULL COMMENT '状态',
  `created_by` bigint(20) NOT NULL COMMENT '创建人id',
  `created_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `created_date` datetime(0) NOT NULL COMMENT '创建日期',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人id',
  `modified_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted_flag` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除字段(0未删除，1已删除)',
  `version` int(10) UNSIGNED NOT NULL DEFAULT 1 COMMENT '不自增，乐观锁版本控制',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_item_keep_rule
-- ----------------------------
DROP TABLE IF EXISTS `base_item_keep_rule`;
CREATE TABLE `base_item_keep_rule`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `item_id` bigint(20) NOT NULL COMMENT '商品ID',
  `item_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品编码',
  `owner_id` bigint(20) UNSIGNED NOT NULL COMMENT '货主id',
  `owner_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '货主编码',
  `owner_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '货主名称',
  `warehouse_id` bigint(20) NULL DEFAULT NULL COMMENT '仓库id',
  `area_id` bigint(20) NULL DEFAULT NULL COMMENT '库区ID',
  `replenish_min_num` int(11) NULL DEFAULT NULL COMMENT '补货下限',
  `replenish_max_num` int(11) NULL DEFAULT NULL COMMENT '临期剩余时效',
  `state` tinyint(3) UNSIGNED NULL DEFAULT 1 COMMENT '状态（0-已创建，1-启用，2-停用）',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `service_provider_id` bigint(20) NOT NULL COMMENT '仓储服务商id',
  `created_by` bigint(20) NOT NULL COMMENT '创建人id',
  `created_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `created_date` datetime(0) NOT NULL COMMENT '创建日期',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人id',
  `modified_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted_flag` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除字段(0未删除，1已删除)',
  `version` int(10) UNSIGNED NOT NULL DEFAULT 1 COMMENT '不自增，乐观锁版本控制',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46701 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品存放规则表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_item_unit
-- ----------------------------
DROP TABLE IF EXISTS `base_item_unit`;
CREATE TABLE `base_item_unit`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `unit_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '计量代码',
  `unit_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `ext_unit_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `item_id` bigint(20) NULL DEFAULT NULL COMMENT '商品编码',
  `unit_measure` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '计量单位',
  `item_barcode` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品条码',
  `ext_item_barcode` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ext_unit_length` decimal(10, 0) NULL DEFAULT NULL,
  `unit_length` decimal(10, 0) NULL DEFAULT NULL COMMENT '长',
  `ext_unit_width` decimal(10, 0) NULL DEFAULT NULL,
  `unit_width` decimal(10, 0) NULL DEFAULT NULL COMMENT '宽',
  `ext_unit_height` decimal(10, 0) NULL DEFAULT NULL,
  `unit_height` decimal(10, 0) NULL DEFAULT NULL COMMENT '单位高',
  `ext_unit_volume` decimal(10, 0) NULL DEFAULT NULL,
  `unit_volume` decimal(10, 0) NULL DEFAULT NULL COMMENT '单位体积',
  `ext_unit_weight` decimal(10, 3) NULL DEFAULT NULL,
  `unit_weight` decimal(10, 3) NULL DEFAULT NULL COMMENT '单位重量',
  `unit_conversion_rate` decimal(10, 0) NULL DEFAULT NULL COMMENT '单位转换率',
  `pack_level` int(11) NULL DEFAULT NULL COMMENT '包装层级',
  `pack_level_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单位层级名称',
  `state` int(11) NOT NULL COMMENT '状态',
  `created_by` bigint(20) NOT NULL COMMENT '创建人id',
  `created_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `created_date` datetime(0) NOT NULL COMMENT '创建日期',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人id',
  `modified_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted_flag` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除字段(0未删除，1已删除)',
  `version` int(10) UNSIGNED NOT NULL DEFAULT 1 COMMENT '不自增，乐观锁版本控制',
  `unit_net_weight` decimal(10, 2) NULL DEFAULT NULL COMMENT '单位净重',
  `ext_unit_net_weight` decimal(10, 0) NULL DEFAULT NULL,
  `complete_maintenance_flag` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_item_id`(`item_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32114 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '计量单位字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_item_warehouse_info
-- ----------------------------
DROP TABLE IF EXISTS `base_item_warehouse_info`;
CREATE TABLE `base_item_warehouse_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `base_item_id` bigint(20) NOT NULL COMMENT '商品基础表id',
  `service_provider_id` bigint(20) NULL DEFAULT NULL COMMENT '仓储服务商id',
  `item_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品编码',
  `owner_id` bigint(20) NULL DEFAULT NULL COMMENT '货主 id',
  `owner_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '货主编码',
  `item_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品名称',
  `warehouse_id` bigint(20) NULL DEFAULT NULL COMMENT '仓库id',
  `warehouse_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库名称',
  `warehouse_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库编号',
  `item_category_id` bigint(20) NULL DEFAULT NULL COMMENT '商品分类',
  `item_abc_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品ABC分类',
  `coder_num` bigint(20) NULL DEFAULT NULL COMMENT '码盘每层数量',
  `layers_num` bigint(20) NULL DEFAULT NULL COMMENT '层数',
  `coder_rule` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '码盘规则',
  `expensive_flag` tinyint(1) NULL DEFAULT 0 COMMENT '是否贵重品（1有，默认0无）',
  `state` tinyint(3) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态分 已创建（0），停用（1），启用（2）',
  `created_by` bigint(20) NOT NULL COMMENT '创建人id',
  `created_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `created_date` datetime(0) NOT NULL COMMENT '创建日期',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人id',
  `modified_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted_flag` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除字段(0未删除，1已删除)',
  `version` int(10) UNSIGNED NOT NULL DEFAULT 1 COMMENT '不自增，乐观锁版本控制',
  `batch_mgmt_flag` int(11) NULL DEFAULT NULL COMMENT '是否管理生产批次号（1是，0否）',
  `manage_expiry_date_flag` int(11) NULL DEFAULT NULL COMMENT '是否管理保质期（1有，默认0无）',
  `sn_manage_flag` int(11) NULL DEFAULT NULL COMMENT '是否启用SN管理（1有，默认0无）',
  `sn_code_rule` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'SN编码规则',
  `shelf_life` bigint(20) NULL DEFAULT NULL COMMENT '保质期',
  `prohibitedcollection_time` bigint(20) NULL DEFAULT NULL COMMENT '禁收剩余时效',
  `advent_time` bigint(20) NULL DEFAULT NULL COMMENT '临期剩余时效',
  `forbid_sale_time` bigint(20) NULL DEFAULT NULL COMMENT '禁售剩余时效',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_item_code`(`item_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 62058 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_location
-- ----------------------------
DROP TABLE IF EXISTS `base_location`;
CREATE TABLE `base_location`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `service_provider_id` bigint(20) NOT NULL COMMENT '仓储服务商id',
  `warehouse_id` bigint(20) NOT NULL COMMENT '所属仓库',
  `warehouse_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库名称',
  `warehouse_area_id` bigint(20) NOT NULL COMMENT '所属库区',
  `warehouse_area_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '库区名称',
  `warehouse_area_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '库区编码',
  `location_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '货位编码',
  `purpose_attr` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用途属性',
  `rule_id` bigint(20) NULL DEFAULT NULL COMMENT '规则id',
  `rule_area_flag` tinyint(1) NULL DEFAULT NULL COMMENT '是否选择库区为前缀',
  `split_symbol` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规则分割符',
  `rule_values` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规则值',
  `location_order` int(11) NULL DEFAULT NULL COMMENT '货位顺序码',
  `location_state` int(10) UNSIGNED NULL DEFAULT 1 COMMENT '货位状态(0-空位 1-存满 2-未满)',
  `location_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '货位类型',
  `valuable_flag` int(11) NULL DEFAULT 2 COMMENT '是否贵重物品',
  `rel_item_count` int(11) NULL DEFAULT 0 COMMENT '挂靠商品数量',
  `location_length` decimal(19, 2) NULL DEFAULT NULL COMMENT '长度',
  `location_width` decimal(19, 2) NULL DEFAULT NULL COMMENT '宽度',
  `location_height` decimal(19, 2) NULL DEFAULT NULL COMMENT '高度',
  `location_cubage` decimal(19, 4) NULL DEFAULT NULL COMMENT '体积',
  `max_cubage` decimal(19, 3) NULL DEFAULT NULL COMMENT '最大体积',
  `max_weight` decimal(19, 3) NULL DEFAULT NULL COMMENT '最大重量',
  `usable_cubage_proportion` decimal(19, 3) NULL DEFAULT NULL COMMENT '可用容积比例',
  `usable_weight_proportion` decimal(19, 3) NULL DEFAULT NULL COMMENT '可用重量比例',
  `code_type` int(11) NULL DEFAULT 1 COMMENT '货位编码类型 1：系统生成 2：自定义',
  `state` tinyint(3) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态（0-已创建，1-启用，2-停用）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NOT NULL DEFAULT current_timestamp() ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  `deleted_flag` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除字段(0未删除，1已删除)',
  `version` int(10) UNSIGNED NOT NULL DEFAULT 1 COMMENT '不自增，乐观锁版本控制',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_deleted_flag`(`deleted_flag`) USING BTREE,
  INDEX `idx_location_order`(`location_order`) USING BTREE,
  INDEX `idx_location_code_warehouse_id`(`location_code`, `warehouse_id`) USING BTREE,
  INDEX `idx_warehouse_area_id`(`warehouse_area_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23014 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '货位表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of base_location
-- ----------------------------
INSERT INTO `base_location` VALUES (23013, 18965, 80, 'C001', '江汉区一区仓库1', 580, '2121', '2414', '2414-1-1-1-12-1', 'SHIP', NULL, 1, '-', '[{\"code\":\"LOCATION_RULE_CHANNEL\",\"name\":\"通道\",\"ruleCode\":\"CNN\",\"value\":\"1\"},{\"code\":\"LOCATION_RULE_ROW\",\"name\":\"排\",\"ruleCode\":\"CNN\",\"value\":\"1\"},{\"code\":\"LOCATION_RULE_GROUP\",\"name\":\"组\",\"ruleCode\":\"CNN\",\"value\":\"1\"},{\"code\":\"LOCATION_RULE_LATER\",\"name\":\"层\",\"ruleCode\":\"CNN\",\"value\":\"12\"},{\"code\":\"LOCATION_RULE_POSITION\",\"name\":\"位\",\"ruleCode\":\"CNN\",\"value\":\"1\"}]', 1214, 0, 'HL', 2, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1, NULL, 139, 'ysadmin', '2026-02-27 04:14:06', 0, 'sys', '2026-02-27 12:14:06', 0, 0);

-- ----------------------------
-- Table structure for base_location_rule
-- ----------------------------
DROP TABLE IF EXISTS `base_location_rule`;
CREATE TABLE `base_location_rule`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `service_provider_id` bigint(20) UNSIGNED NOT NULL COMMENT '服务商id',
  `warehouse_id` bigint(20) NOT NULL COMMENT '所属仓库',
  `warehouse_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库名称',
  `warehouse_area_id` bigint(20) NOT NULL COMMENT '所属库区',
  `warehouse_area_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '库区名称',
  `warehouse_area_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '库区编码',
  `config_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '规则编码',
  `split_symbol` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分割符号',
  `state` tinyint(3) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态（0-已创建，1-启用，2-停用）',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  `deleted_flag` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除字段(0未删除，1已删除)',
  `version` int(10) UNSIGNED NOT NULL DEFAULT 1 COMMENT '不自增，乐观锁版本控制',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '月台表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_location_rule_detail
-- ----------------------------
DROP TABLE IF EXISTS `base_location_rule_detail`;
CREATE TABLE `base_location_rule_detail`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `service_provider_id` bigint(20) UNSIGNED NOT NULL COMMENT '服务商id',
  `location_rule_id` bigint(20) NOT NULL COMMENT '规则主表id',
  `warehouse_area_id` bigint(20) NOT NULL COMMENT '所属库区',
  `rule_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规则编码',
  `rule_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规则名称',
  `rule_value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '规则值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '货位规则编码' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_owner_area
-- ----------------------------
DROP TABLE IF EXISTS `base_owner_area`;
CREATE TABLE `base_owner_area`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `service_provider_id` bigint(20) UNSIGNED NOT NULL COMMENT '仓储服务商id',
  `warehouse_id` bigint(20) NOT NULL COMMENT '所属仓库',
  `warehouse_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库名称',
  `owner_id` bigint(20) NOT NULL COMMENT '货主ID',
  `owner_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '货主编码',
  `owner_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '货主名称',
  `supplier_id` bigint(20) NULL DEFAULT NULL COMMENT '供应商',
  `supplier_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '供应商编码',
  `supplier_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '供应商名称',
  `area_id` bigint(20) NOT NULL COMMENT '所属库区',
  `area_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '库区编码',
  `area_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '库区名称',
  `location_id` bigint(20) NULL DEFAULT NULL COMMENT '货位id',
  `location_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '货位编码',
  `private_flag` tinyint(4) NULL DEFAULT 0 COMMENT '是否专用（0否，1是）',
  `item_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '货物属性',
  `circulation_attr` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流转属性',
  `purpose_attr` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用途属性',
  `state` tinyint(3) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态（0-已创建，1-启用，2-停用）',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NOT NULL DEFAULT current_timestamp() ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  `deleted_flag` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除字段(0未删除，1已删除)',
  `version` int(10) UNSIGNED NOT NULL DEFAULT 1 COMMENT '不自增，乐观锁版本控制',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_deleted_flag`(`deleted_flag`) USING BTREE,
  INDEX `idx_warehouse_area_id`(`area_id`) USING BTREE,
  INDEX `idx_owner_id_warehouse_id`(`owner_id`, `warehouse_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1194 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '货主库区表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_platform
-- ----------------------------
DROP TABLE IF EXISTS `base_platform`;
CREATE TABLE `base_platform`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `service_provider_id` bigint(20) UNSIGNED NOT NULL COMMENT '服务商id',
  `warehouse_id` bigint(20) NULL DEFAULT NULL COMMENT '仓库id',
  `warehouse_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库名称',
  `platform_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '月台编号',
  `platform_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '月台名称',
  `busi_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务类型',
  `throughput` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '吞吐量',
  `throughput_unit` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '吞吐量单位',
  `share_flag` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否共享',
  `lifting_platform_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否提供升降台',
  `dock_upper` int(11) NULL DEFAULT NULL COMMENT '停靠上限',
  `use_order` int(11) NULL DEFAULT NULL COMMENT '使用顺序',
  `occupy_qty` int(11) NULL DEFAULT NULL COMMENT '占用数量',
  `occupy_status` int(11) NULL DEFAULT NULL COMMENT '占用状态',
  `state` tinyint(3) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态（0-已创建，1-启用，2-停用）',
  `remark` varchar(600) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  `deleted_flag` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除字段(0未删除，1已删除)',
  `version` int(10) UNSIGNED NOT NULL DEFAULT 1 COMMENT '不自增，乐观锁版本控制',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 62 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '月台表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of base_platform
-- ----------------------------
INSERT INTO `base_platform` VALUES (61, 18965, 80, 'C001', '江汉区一区仓库1', 'Y0892828', '123', 'PLATEFORM_BUSI_SHRK', 21, 'PLATEFORM_UNIT_KG', 1, 1, 1321, 132, 0, 0, 0, NULL, 139, 'ysadmin', '2026-02-27 12:54:54', NULL, 'ysadmin', '2026-02-27 12:54:54', 0, 0);

-- ----------------------------
-- Table structure for base_store_print
-- ----------------------------
DROP TABLE IF EXISTS `base_store_print`;
CREATE TABLE `base_store_print`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `config_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置编码',
  `config_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置名称',
  `order_owner_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单货主ID',
  `order_owner_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单货主名称',
  `order_platform_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单来源平台编码',
  `order_platform_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单来源平台名称',
  `from_platform_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '获取渠道编码',
  `from_platform_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '获取渠道名称',
  `from_store_owner_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '渠道货主ID',
  `from_store_owner_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '渠道货主名称',
  `from_store_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '渠道店铺ID',
  `from_store_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '渠道店铺编码',
  `from_store_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '渠道店铺名称',
  `merchant_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '渠道店铺/授权店铺商家编码',
  `warehouse_id` bigint(20) NULL DEFAULT NULL COMMENT '所属仓库',
  `warehouse_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库名称',
  `service_provider_id` bigint(20) NULL DEFAULT NULL COMMENT '仓储服务商ID',
  `service_provider_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓储服务商名称 ',
  `state` tinyint(3) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态（0-已创建，1-启用，2-停用）',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `created_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` int(11) NULL DEFAULT 0 COMMENT '乐观锁控制',
  `deleted_flag` int(11) NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_base`(`warehouse_id`, `service_provider_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 103 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '运单号与面单获取' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_store_print_detail
-- ----------------------------
DROP TABLE IF EXISTS `base_store_print_detail`;
CREATE TABLE `base_store_print_detail`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `config_id` bigint(20) NULL DEFAULT NULL COMMENT '配置项主表ID',
  `config_state` tinyint(4) NULL DEFAULT NULL COMMENT '配置项主表状态（0-已创建，1-启用，2-停用）（冗余便于查询）',
  `order_owner_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单货主ID（冗余便于查询）',
  `order_platform_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单来源平台编码（冗余便于查询）',
  `order_store_id` bigint(20) NULL DEFAULT NULL COMMENT '订单店铺ID',
  `order_store_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单店铺编码',
  `order_store_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单店铺名称',
  `from_platform_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '获取渠道编码（冗余便于查询）',
  `platform_logistics_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '获取渠道的平台承运商编码（冗余便于查询）',
  `from_store_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '渠道店铺ID（冗余便于查询）',
  `from_store_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '渠道店铺编码（冗余便于查询）',
  `from_store_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '渠道店铺名称（冗余便于查询）',
  `merchant_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '渠道店铺/授权店铺商家编码',
  `logistics_id` bigint(20) NULL DEFAULT NULL COMMENT '系统承运商ID',
  `logistics_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '系统承运商编码',
  `logistics_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '系统承运商名称',
  `standard_face_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标准面单模板id/code',
  `standard_face_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标准面单类型',
  `standard_face_type_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标准面单类型名称',
  `standard_face_template_url` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标准面单名称',
  `custom_face_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '自定义面单模板id/code',
  `custom_face_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '自定义面单类型',
  `custom_face_type_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '自定义面单类型名称',
  `custom_template_url` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '自定义模版地址',
  `netsite_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网点ID',
  `netsite_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网点名称',
  `province` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省',
  `province_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省编码',
  `city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市',
  `city_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市编码',
  `area` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区',
  `area_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区编码',
  `town` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '镇',
  `town_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '镇编码',
  `address_detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '详细地址',
  `contact` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `contact_tel` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `product_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品编码',
  `express_pay_method` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '快递费支付方式：1:寄方付 2:收方付 3:第三方付',
  `brand_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '品牌编码，TM对应的SF',
  `settle_account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '月结账号',
  `attr_str1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信店铺id',
  `attr_str2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信电子面单账号id',
  `attr_str3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '京东是否开通送货上门',
  `attr_str4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '扩展字符串4',
  `attr_str5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '扩展字符串5',
  `warehouse_id` bigint(20) NULL DEFAULT NULL COMMENT '所属仓库（冗余便于查询）',
  `warehouse_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库名称',
  `service_provider_id` bigint(20) NULL DEFAULT NULL COMMENT '仓储服务商ID',
  `service_provider_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓储服务商名称 ',
  `state` tinyint(3) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态（0-已创建，1-启用，2-停用）',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `created_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` int(11) NULL DEFAULT 0 COMMENT '乐观锁控制',
  `deleted_flag` int(11) NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_base`(`warehouse_id`, `service_provider_id`) USING BTREE,
  INDEX `idx_order_store_id_platform_code_logistics_code`(`order_store_id`, `order_platform_code`, `logistics_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 236 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '运单号与面单获取明细' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_supplier
-- ----------------------------
DROP TABLE IF EXISTS `base_supplier`;
CREATE TABLE `base_supplier`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `supplier_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '供应商编码',
  `supplier_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '供应商名称',
  `contact` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `contact_mobile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人手机号',
  `contact_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人电话',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `type` int(11) NOT NULL DEFAULT 1 COMMENT '类型 1-供应商,2-客户',
  `state` tinyint(3) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态（0-已创建，1-启用，2-停用）',
  `data_from` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据来源（0:手工录入，1：Excel导入，2：Service接口写入 3外部系统写入）',
  `owner_id` bigint(20) NOT NULL COMMENT '货主id',
  `owner_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '货主名称',
  `warehouse_id` bigint(20) NOT NULL COMMENT '所属仓库',
  `warehouse_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库名称',
  `service_provider_id` bigint(20) NOT NULL COMMENT '仓储服务商id',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `created_date` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
  `modified_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `deleted_flag` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除字段(0未删除，1已删除)',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '不自增，乐观锁版本控制',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_code_name`(`supplier_code`, `supplier_name`) USING BTREE,
  INDEX `idx_type`(`type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '供应商主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_workbench
-- ----------------------------
DROP TABLE IF EXISTS `base_workbench`;
CREATE TABLE `base_workbench`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `service_provider_id` bigint(20) UNSIGNED NOT NULL COMMENT '服务商id',
  `workbench_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '工作台编号',
  `workbench_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '工作台名称',
  `workbench_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '业务类型',
  `warehouse_id` bigint(20) NOT NULL COMMENT '所属仓库',
  `warehouse_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '仓库名称',
  `warehouse_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库编码',
  `bind_flag` tinyint(1) NULL DEFAULT 0 COMMENT '是否绑定',
  `mac_address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'MAC地址',
  `state` tinyint(3) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态（0-已创建，1-启用，2-停用）',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  `deleted_flag` tinyint(3) UNSIGNED NULL DEFAULT 0 COMMENT '逻辑删除字段(0未删除，1已删除)',
  `version` int(10) UNSIGNED NULL DEFAULT 1 COMMENT '不自增，乐观锁版本控制',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 115 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '工作台' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of base_workbench
-- ----------------------------
INSERT INTO `base_workbench` VALUES (114, 18965, '12', '11', NULL, 80, '江汉区一区仓库1', 'C001', 0, NULL, 0, NULL, 139, 'ysadmin', '2026-02-27 13:13:08', NULL, 'ysadmin', '2026-02-27 13:13:08', 0, 0);

-- ----------------------------
-- Table structure for disassembly_team
-- ----------------------------
DROP TABLE IF EXISTS `disassembly_team`;
CREATE TABLE `disassembly_team`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '仓库id',
  `service_provider_id` bigint(20) UNSIGNED NOT NULL COMMENT '服务商id',
  `disassembly_team_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '拆装队编号',
  `disassembly_team_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '拆装队名称',
  `disassembly_team_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '拆装队类型',
  `warehouse_id` bigint(20) NOT NULL COMMENT '仓库id',
  `warehouse_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '仓库编号',
  `warehouse_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '仓库名称',
  `park` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '园区',
  `warehouse_property` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库性质字典code',
  `warehouse_area` int(11) NULL DEFAULT NULL COMMENT '仓库面积',
  `charge_person` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '责任人',
  `charge_tel` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '责任人电话',
  `members` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '成员',
  `province_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省',
  `city_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市',
  `district_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区（县）',
  `town_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '乡（镇）',
  `address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '详细地址',
  `state` tinyint(3) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态（0-已创建，1-启用，2-停用）',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NOT NULL DEFAULT current_timestamp() ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '最后修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `deleted_flag` tinyint(3) UNSIGNED NULL DEFAULT 0 COMMENT '逻辑删除标识（0-未删除，1-已删除）',
  `version` int(10) UNSIGNED NULL DEFAULT 1 COMMENT '不自增，乐观锁版本控制',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '拆装队' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for excel_task
-- ----------------------------
DROP TABLE IF EXISTS `excel_task`;
CREATE TABLE `excel_task`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `service_provider_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '服务商id',
  `type` tinyint(4) NOT NULL COMMENT '类型：1-导入,2-导出',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态：0-初始,1-进行中,2-完成,3-失败',
  `estimate_count` int(11) NOT NULL DEFAULT 0 COMMENT '预估总记录数',
  `total_count` int(11) NOT NULL DEFAULT 0 COMMENT '实际总记录数',
  `success_count` int(11) NOT NULL DEFAULT 0 COMMENT '成功记录数',
  `failed_count` bigint(20) NOT NULL DEFAULT 0 COMMENT '失败记录数',
  `file_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件名',
  `file_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件路径',
  `failed_file_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '失败文件路径',
  `failed_message` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '失败消息',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `business_code` int(11) NULL DEFAULT NULL COMMENT '业务编码',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人名称',
  `created_date` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '最后修改人',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `deleted_flag` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除标识（0-未删除，1-已删除）',
  `warehouse_id` bigint(20) NULL DEFAULT NULL COMMENT '仓库id',
  `warehouse_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库名称',
  `source_file` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2021071621640761346 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '导入导出任务' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of excel_task
-- ----------------------------
INSERT INTO `excel_task` VALUES (2021069971488321538, 18965, 2, 2, 0, 0, 0, 0, '货位库存台账_2021069971488321538.zip', NULL, NULL, NULL, '2026-02-10 11:53:25', '2026-02-10 11:53:25', 22, 0, 'sys', '2026-02-10 11:53:25', NULL, NULL, NULL, NULL, 0, 80, NULL, NULL, NULL);
INSERT INTO `excel_task` VALUES (2021071380589916162, 18965, 2, 2, 0, 0, 0, 0, '仓库可售台账_2021071380589916162.zip', NULL, NULL, NULL, '2026-02-10 11:59:01', '2026-02-10 11:59:01', 20, 0, 'sys', '2026-02-10 11:59:01', NULL, NULL, NULL, NULL, 0, 80, NULL, NULL, NULL);
INSERT INTO `excel_task` VALUES (2021071451402350594, 18965, 2, 2, 0, 0, 0, 0, '货位库存台账_2021071451402350594.zip', NULL, NULL, NULL, '2026-02-10 11:59:18', '2026-02-10 11:59:18', 22, 0, 'sys', '2026-02-10 11:59:18', NULL, NULL, NULL, NULL, 0, 80, NULL, NULL, NULL);
INSERT INTO `excel_task` VALUES (2021071621640761345, 18965, 2, 2, 0, 0, 0, 0, '接单异常_2021071621640761345.zip', NULL, NULL, NULL, '2026-02-10 11:59:58', '2026-02-10 11:59:58', 14, 0, 'sys', '2026-02-10 11:59:58', NULL, NULL, NULL, NULL, 0, 80, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for item_pick_rule
-- ----------------------------
DROP TABLE IF EXISTS `item_pick_rule`;
CREATE TABLE `item_pick_rule`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `service_provider_id` bigint(20) NULL DEFAULT NULL COMMENT '仓储服务商id',
  `warehouse_id` bigint(20) NOT NULL COMMENT '所属仓库',
  `warehouse_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库名称',
  `owner_id` bigint(20) NOT NULL COMMENT '货主id',
  `owner_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '配置编码',
  `owner_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '货主名称',
  `config_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '供应商编码',
  `rule_describo` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规则描述',
  `item_id` bigint(20) NOT NULL COMMENT '商品id',
  `item_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品编码',
  `item_barcode` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品条码',
  `item_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `unit_convert_text` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '整零换算',
  `pick_qty` decimal(19, 0) NULL DEFAULT NULL COMMENT '商品最大单位拣货数量',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `state` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '状态（0-已创建，1-启用，2-停用）',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `created_date` datetime(0) NOT NULL COMMENT '创建时间',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
  `modified_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `deleted_flag` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除字段(0未删除，1已删除)',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` int(10) UNSIGNED NOT NULL DEFAULT 1 COMMENT '不自增，乐观锁版本控制',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品拣选规则' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for video_info
-- ----------------------------
DROP TABLE IF EXISTS `video_info`;
CREATE TABLE `video_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `file_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件名',
  `file_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件下载地址',
  `ship_bill_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '运单号',
  `bill_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单据类型',
  `do_header_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单据编号',
  `operator_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人姓名',
  `station_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工作台',
  `upload_date` timestamp(0) NULL DEFAULT NULL COMMENT '上传时间',
  `warehouse_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓code',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `created_date` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_modified_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `last_modified_date` timestamp(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `deleted_flag` tinyint(3) UNSIGNED NULL DEFAULT 0 COMMENT '逻辑删除标识',
  `version` int(10) UNSIGNED NULL DEFAULT 1 COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_ship_bill_no_warehouse_id`(`ship_bill_no`, `warehouse_code`) USING BTREE,
  INDEX `idx_video_upload_date`(`upload_date`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 905830 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '视频表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for wms_rule
-- ----------------------------
DROP TABLE IF EXISTS `wms_rule`;
CREATE TABLE `wms_rule`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `rule_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '规则类型',
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '编号',
  `warehouse_id` bigint(20) NULL DEFAULT NULL COMMENT '仓库id',
  `warehouse_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库编号',
  `warehouse_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库名称',
  `struct_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单据结构类型',
  `service_provider_id` bigint(20) NULL DEFAULT NULL COMMENT '仓储服务商',
  `service_provider_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓储服务商名称',
  `owner_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '货主编码',
  `owner_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '货主名称',
  `owner_id` int(11) NULL DEFAULT NULL COMMENT '货主id',
  `rule_contontent` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '规则详情',
  `state` int(11) NULL DEFAULT NULL COMMENT '状态',
  `created_by` bigint(20) NOT NULL COMMENT '创建人id',
  `created_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `created_date` datetime(0) NOT NULL COMMENT '创建日期',
  `modified_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人id',
  `modified_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted_flag` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除字段(0未删除，1已删除)',
  `version` int(11) NULL DEFAULT NULL COMMENT '乐观锁字段',
  `pick_up_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `delivery_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `take_down_type` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1322 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '仓储规则表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wms_rule
-- ----------------------------
INSERT INTO `wms_rule` VALUES (1320, 'abcRule', '2602130001', 58, 'C002', 'CQX测试仓库', NULL, 3, NULL, '0', NULL, NULL, '{\"paramValue\":1,\"ruleDescription\":\"是否按商品ABC管理\"}', 0, 4, 'admin', '2026-02-13 14:14:03', NULL, 'admin', '2026-02-13 14:14:03', 0, 0, NULL, NULL, NULL);
INSERT INTO `wms_rule` VALUES (1321, 'abcRule', '2602270001', 80, 'C001', '江汉区一区仓库1', NULL, 18965, NULL, '0', NULL, NULL, '{\"paramValue\":1,\"ruleDescription\":\"Manage by Product ABC Classification\"}', 0, 139, 'ysadmin', '2026-02-27 15:10:27', NULL, 'ysadmin', '2026-02-27 15:10:27', 0, 0, NULL, NULL, NULL);

-- ----------------------------
-- Triggers structure for table video_info
-- ----------------------------
DROP TRIGGER IF EXISTS `tr_vedio_info`;
delimiter ;;
CREATE TRIGGER `tr_vedio_info` BEFORE UPDATE ON `video_info` FOR EACH ROW set new.deleted_flag=0,new.version=1
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
