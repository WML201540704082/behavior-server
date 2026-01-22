-- Date: 2024-07-25 已完成
-- SQL语句:
ALTER TABLE `idevelop_device_operation_detail`
ADD COLUMN `oprt_date` date NULL COMMENT '投运日期' AFTER `ports_count`;
ALTER TABLE `idevelop_device_operation_detail`
ADD COLUMN `receive_duty_isc_account` varchar(255) NULL COMMENT '责任人ISC账号字段' AFTER `oprt_date`;
