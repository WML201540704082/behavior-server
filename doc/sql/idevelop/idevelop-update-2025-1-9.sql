INSERT INTO `idevelop`.`idevelop_device_storage_template`(`id`, `tenant_id`, `create_user`, `create_dept`, `create_time`, `status`, `is_deleted`, `device_category`, `device_type`, `file_type`, `file_name`, `file_path`, `file_path_mobile`, `file_size`, `remark`, `update_user`, `update_time`) VALUES (1765198932054789546, '000000', 1123598821738675201, NULL, '2025-01-09 17:02:20', 0, 0, '1118822355763201', '1135308252184578', NULL, '基础设施', 'com.lnsoft.device.api.warehouse.dto.DeviceStorageListImportT109KTDTO', NULL, NULL, NULL, 1123598821738675201, '2025-01-09 17:03:02');
INSERT INTO `idevelop`.`idevelop_device_storage_template`(`id`, `tenant_id`, `create_user`, `create_dept`, `create_time`, `status`, `is_deleted`, `device_category`, `device_type`, `file_type`, `file_name`, `file_path`, `file_path_mobile`, `file_size`, `remark`, `update_user`, `update_time`) VALUES (1765189841851694446, '000000', 1123598821738675201, NULL, '2025-01-09 17:02:20', 0, 0, '1118822355763201', '1135308252184587', NULL, '基础设施', 'com.lnsoft.device.api.warehouse.dto.DeviceStorageListImportT109UPSDTO', NULL, NULL, NULL, 1123598821738675201, '2025-01-09 17:03:02');
INSERT INTO `idevelop`.`idevelop_device_storage_template`(`id`, `tenant_id`, `create_user`, `create_dept`, `create_time`, `status`, `is_deleted`, `device_category`, `device_type`, `file_type`, `file_name`, `file_path`, `file_path_mobile`, `file_size`, `remark`, `update_user`, `update_time`) VALUES (1765641854894614654, '000000', 1123598821738675201, NULL, '2025-01-09 17:02:20', 0, 0, '1118822355763201', '1135308252184582', NULL, '基础设施', 'com.lnsoft.device.api.warehouse.dto.DeviceStorageListImportT109XDCZDTO', NULL, NULL, NULL, 1123598821738675201, '2025-01-09 17:03:02');

ALTER TABLE `idevelop`.`idevelop_device_operation_detail`
ADD COLUMN `voltage_level` varchar(255) NULL COMMENT '电压等级名称' AFTER `property_dept`,
ADD COLUMN `voltage_level_code` varchar(255) NULL COMMENT '电压等级编码' AFTER `voltage_level`;
ADD COLUMN `os_version` varchar(255) NULL COMMENT '操作系统版本号' AFTER `voltage_level_code`;
ADD COLUMN `oprt_date_first` date(0) NULL COMMENT '首次投运日期' AFTER `os_version`;
ADD COLUMN `hard_disk_type` varchar(50) NULL COMMENT '硬盘类型' AFTER `oprt_date_first`;
ADD COLUMN `standby_attr` varchar(50) NULL COMMENT '主备属性' AFTER `hard_disk_type`;
ADD COLUMN `security_boundary` varchar(50) NULL COMMENT '所属安全边界' AFTER `standby_attr`;
ADD COLUMN `mem_size` varchar(50) NULL COMMENT '内存大小' AFTER `security_boundary`,
ADD COLUMN `rated_power` varchar(50) NULL COMMENT '额定功率' AFTER `mem_size`,
ADD COLUMN `power_model` varchar(50) NULL COMMENT '电源模块' AFTER `rated_power`,
ADD COLUMN `is_cloud_mange` varchar(50) NULL COMMENT '是否纳入云管' AFTER `power_model`,
ADD COLUMN `server_use_to_type` varchar(50) NULL COMMENT '主机设备用途类型' AFTER `is_cloud_mange`,
ADD COLUMN `hard_disk_capability` varchar(50) NULL COMMENT '硬盘容量' AFTER `server_use_to_type`;
ADD COLUMN `pdu_rated_power` varchar(50) NULL COMMENT 'pdu额定功率' AFTER `hard_disk_capability`,
ADD COLUMN `pdu_operate_power` varchar(50) NULL COMMENT 'pdu运行功率' AFTER `pdu_rated_power`;
ADD COLUMN `os_issue_version` varchar(50) NULL COMMENT '操作系统发行版本' AFTER `pdu_operate_power`;


CREATE TABLE `idevelop_data_task_log` (
  `id` int(50) NOT NULL AUTO_INCREMENT COMMENT '自增主键id',
  `cmdb_id` bigint(50) DEFAULT NULL,
  `task_name` varchar(255) DEFAULT NULL,
  `success` tinyint(1) DEFAULT NULL COMMENT '是否成功, 0:成功,1失败',
  `message` text,
  `expand` text COMMENT '拓展字段',
  `is_deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除, 0:否;1:是',
  `tenant_id` varchar(12) DEFAULT NULL COMMENT '租户ID',
  `create_user` bigint(24) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` bigint(24) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `status` tinyint(2) DEFAULT NULL COMMENT '状态',
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;


ALTER TABLE `idevelop`.`idevelop_resource_room`
    ADD COLUMN `i6000_uuid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联I6000机房uuid' AFTER `is_i6000`,
ADD COLUMN `i6000_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联I6000机房name' AFTER `i6000_uuid`;
