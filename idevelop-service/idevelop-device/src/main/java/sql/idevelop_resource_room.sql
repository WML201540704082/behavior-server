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

 Date: 21/02/2024 18:03:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for idevelop_resource_room
-- ----------------------------
DROP TABLE IF EXISTS `idevelop_resource_room`;
CREATE TABLE `idevelop_resource_room`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '000000' COMMENT '租户ID',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `create_dept` bigint(20) NULL DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `status` int(2) NULL DEFAULT NULL COMMENT '状态',
  `is_deleted` int(2) NULL DEFAULT NULL COMMENT '是否已删除',
  `room_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '机房名称',
  `room_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '机房编号',
  `global_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '全局名称',
  `abbreviation` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '简称',
  `room_location` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '机房位置',
  `maintenance_unit_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '维护单位',
  `room_area` double(60, 2) NULL DEFAULT NULL COMMENT '机房面积（m²）',
  `room_height` double(60, 2) NULL DEFAULT NULL COMMENT '机房高度（m）',
  `room_width` double(60, 2) NULL DEFAULT NULL COMMENT '机房宽度（m）',
  `room_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '机房类型',
  `is_monitor` int(2) NULL DEFAULT NULL COMMENT '是否监控',
  `room_bearing` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '房间承重',
  `room_depth` double(60, 2) NULL DEFAULT NULL COMMENT '房间进深（m）',
  `heating_method` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '取暖方式',
  `air_method` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '空调方式',
  `routing_method` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '走线方式',
  `maintenance_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '维护人',
  `duty_phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '值班电话',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `room_id`(`room_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1759128646507401219 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '空间资源管理机房表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
