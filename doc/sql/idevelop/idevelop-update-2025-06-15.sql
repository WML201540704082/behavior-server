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

 Date: 30/04/2025 15:38:41
*/
CREATE TABLE `idevelop_i6000_import_log`
(
    `id`          varchar(50) NOT NULL COMMENT '主键ID',
    `import_uuid` varchar(50) DEFAULT NULL COMMENT '本次导入UUID',
    `device_code` varchar(50) DEFAULT NULL COMMENT '设备编码',
    `device_info` longtext COMMENT '导入情况',
    `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
    `create_time` datetime    DEFAULT NULL COMMENT '创建时间',
    `update_user` bigint(20) DEFAULT NULL COMMENT '修改人',
    `update_time` datetime    DEFAULT NULL COMMENT '修改时间',
    `status`      int(2) DEFAULT NULL COMMENT '状态',
    `is_deleted`  int(2) DEFAULT '0' COMMENT '是否已删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员批量导入I6000数据记录表';


ALTER TABLE `idevelop`.`idevelop_device_change_list`
    ADD COLUMN `fun_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '功能位置' AFTER `is_deleted`,
ADD COLUMN `fun_location_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '功能位置编码' AFTER `fun_location`,
ADD COLUMN `device_change_type_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备变动方式编码' AFTER `fun_location_code`,
ADD COLUMN `device_change_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备变动方式' AFTER `device_change_type_code`,
ADD COLUMN `device_add_type_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备增加方式编码' AFTER `device_change_type`,
ADD COLUMN `device_add_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备增加方式' AFTER `device_add_type_code`,
ADD COLUMN `voltage_level` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电压等级' AFTER `device_add_type`,
ADD COLUMN `voltage_level_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电压等级编码' AFTER `voltage_level`,
ADD COLUMN `real_manage_dept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '实物管理部门编码' AFTER `voltage_level_code`,
ADD COLUMN `entity_management_dept_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '实物管理部门' AFTER `real_manage_dept`,
ADD COLUMN `use_keep_dept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '使用保管部门编码' AFTER `entity_management_dept_name`,
ADD COLUMN `use_keep_dept_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '使用保管部门' AFTER `use_keep_dept`,
ADD COLUMN `factory_area` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工厂区域' AFTER `use_keep_dept_name`,
ADD COLUMN `factory_area_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工厂区域编码' AFTER `factory_area`;
ADD COLUMN `maker` varchar(255) NULL COMMENT '制造商' AFTER `device_status`,
ADD COLUMN `maker_code` varchar(255) NULL COMMENT '制造商编码' AFTER `maker`,
ADD COLUMN `brand_code` varchar(255) NULL COMMENT '品牌编码' AFTER `brand`,
ADD COLUMN `series_code` varchar(255) NULL COMMENT '系列编码' AFTER `series`,
ADD COLUMN `device_model_code` varchar(255) NULL COMMENT '型号编码' AFTER `device_model`;
ADD COLUMN `measure_unit` varchar(255) NULL COMMENT '计量单位' AFTER `factory_area_code`;
