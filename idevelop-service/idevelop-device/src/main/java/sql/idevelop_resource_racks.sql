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

 Date: 21/02/2024 18:03:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for idevelop_resource_racks
-- ----------------------------
DROP TABLE IF EXISTS `idevelop_resource_racks`;
CREATE TABLE `idevelop_resource_racks`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '000000' COMMENT '租户ID',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `create_dept` bigint(20) NULL DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `status` int(2) NULL DEFAULT NULL COMMENT '状态',
  `is_deleted` int(2) NULL DEFAULT NULL COMMENT '是否已删除',
  `racks_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '机架名称',
  `global_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '全局名称',
  `abbreviation` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '简称',
  `belong_racks` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属机柜',
  `produce_factory` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生产厂家',
  `use_date` date NULL DEFAULT NULL COMMENT '投运日期',
  `racks_num` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '机架序号',
  `racks_occupancy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '机架占用',
  `running_status` int(2) NULL DEFAULT NULL COMMENT '运行状态',
  `racks_location` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '机架位置',
  `return_date` date NULL DEFAULT NULL COMMENT '退运日期',
  `maintenance_unti` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '维护单位',
  `maintenance_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '维护人',
  `remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `cabinets_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '机柜id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `机柜id`(`cabinets_id`) USING BTREE,
  CONSTRAINT `机柜id` FOREIGN KEY (`cabinets_id`) REFERENCES `idevelop_resource_cabinets` (`cabinets_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1759128646507401219 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '空间资源管理机架表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
