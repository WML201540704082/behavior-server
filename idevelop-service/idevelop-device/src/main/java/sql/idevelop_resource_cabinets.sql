/*
 Navicat Premium Data Transfer

 Source Server         : idevelop
 Source Server Type    : MySQL
 Source Server Version : 50738
 Source Host           : rm-wk47jx45tm6cu5958.mysql.rds.ops-devcloud.sd.sgcc.com.cn:14306
 Source Schema         : idevelop

 Target Server Type    : MySQL
 Target Server Version : 50738
 File Encoding         : 65001

 Date: 21/02/2024 18:03:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for idevelop_resource_cabinets
-- ----------------------------
DROP TABLE IF EXISTS `idevelop_resource_cabinets`;
CREATE TABLE `idevelop_resource_cabinets`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '000000' COMMENT '租户ID',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `create_dept` bigint(20) NULL DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `status` int(2) NULL DEFAULT NULL COMMENT '状态',
  `is_deleted` int(2) NULL DEFAULT NULL COMMENT '是否已删除',
  `cabinets_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '机柜名称',
  `cabinets_id` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '机柜编号',
  `global_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '全局名称',
  `abbreviation` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '简称',
  `belong_room` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属机房',
  `cabinets_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '机柜类型',
  `produce_factory` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生产厂家',
  `use_date` date NULL DEFAULT NULL COMMENT '投运日期',
  `cabinets_width` int(255) NULL DEFAULT NULL COMMENT '机柜宽度（mm）',
  `cabinets_height` int(255) NULL DEFAULT NULL COMMENT '机柜高度（mm）',
  `is_sort` int(2) NULL DEFAULT NULL COMMENT '是否正序',
  `maintenance_unit` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '维护单位',
  `capacity` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '容量（U）',
  `device_model` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备型号',
  `cabinets_depth` int(255) NULL DEFAULT NULL COMMENT '机柜深度（mm）',
  `return_date` date NULL DEFAULT NULL COMMENT '退运日期',
  `maintenance_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '维护人',
  `room_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '机房id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `机房id`(`room_id`) USING BTREE,
  INDEX `cabinets_id`(`cabinets_id`) USING BTREE,
  CONSTRAINT `机房id` FOREIGN KEY (`room_id`) REFERENCES `idevelop_resource_room` (`room_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1759128646507401219 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '空间资源管理机柜表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
