-- Date: 2024-07-31 未完成
-- SQL语句:
ALTER TABLE `idevelop_device_operation`
ADD COLUMN `owner_unit_code` varchar(100) NULL COMMENT '产权单位编码' AFTER `operation_num`,
ADD COLUMN `owner_unit` varchar(100) NULL COMMENT '产权单位' AFTER `owner_unit_code`,
ADD COLUMN `property_dept_code` varchar(100) NULL COMMENT '产权部门编码' AFTER `owner_unit`,
ADD COLUMN `property_dept` varchar(100) NULL COMMENT '产权部门' AFTER `property_dept_code`;

ALTER TABLE `idevelop_device_operation_detail`
ADD COLUMN `owner_unit_code` varchar(100) NULL COMMENT '产权单位编码' AFTER `procure_type_code`,
ADD COLUMN `owner_unit` varchar(100) NULL COMMENT '产权单位' AFTER `owner_unit_code`,
ADD COLUMN `property_dept_code` varchar(100) NULL COMMENT '产权部门编码' AFTER `owner_unit`,
ADD COLUMN `property_dept` varchar(100) NULL COMMENT '产权部门' AFTER `property_dept_code`;
