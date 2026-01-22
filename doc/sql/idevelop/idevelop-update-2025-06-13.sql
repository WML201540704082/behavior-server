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
