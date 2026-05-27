CREATE TABLE `idevelop_device_storage` (
`id` BIGINT ( 20 ) NOT NULL AUTO_INCREMENT COMMENT '主键',
`tenant_id` VARCHAR ( 12 ) DEFAULT '000000' COMMENT '租户ID',
`serial_number` VARCHAR ( 50 ) NOT NULL COMMENT '入库单号',
`device_source` INT ( 1 ) DEFAULT NULL COMMENT '设备来源；0统一纳管，1非统一纳管',
`wbs_project` VARCHAR ( 120 ) DEFAULT NULL COMMENT 'WBS项目',
`wbs_element` VARCHAR ( 120 ) DEFAULT NULL COMMENT 'WBS元素',
`device_category` VARCHAR ( 60 ) DEFAULT NULL COMMENT '设备分类',
`device_type` VARCHAR ( 60 ) DEFAULT NULL COMMENT '设备类型',
`warehouse` VARCHAR ( 60 ) DEFAULT NULL COMMENT '所在仓库',
`device_num` INT ( 4 ) DEFAULT NULL COMMENT '入库数量',
`fun_location` VARCHAR ( 120 ) DEFAULT NULL COMMENT '功能位置',
`oprt_dept` VARCHAR ( 60 ) DEFAULT NULL COMMENT '运行单位',
`receiver` VARCHAR ( 30 ) DEFAULT NULL COMMENT '受理人',
`storage_time` datetime DEFAULT NULL COMMENT '入库时间',
`use_keep_person` VARCHAR ( 30 ) DEFAULT NULL COMMENT '使用保管人',
`voltage_level` VARCHAR ( 30 ) DEFAULT NULL COMMENT '电压等级',
`procure_date` date DEFAULT NULL COMMENT '采购日期',
`owner_unit` VARCHAR ( 30 ) DEFAULT NULL COMMENT '产权单位',
`property_dept` VARCHAR ( 30 ) DEFAULT NULL COMMENT '产权部门',
`use_keep_dept` VARCHAR ( 60 ) DEFAULT NULL COMMENT '使用保管部门',
`entity_keep_dept` VARCHAR ( 60 ) DEFAULT NULL COMMENT '实物保管部门',
`factory_area` VARCHAR ( 120 ) DEFAULT NULL COMMENT '工厂区域',
`maintenance_factory` VARCHAR ( 120 ) DEFAULT NULL COMMENT '维护工厂',
`line_station` VARCHAR ( 120 ) DEFAULT NULL COMMENT '线站标识(设备建档数据来源)',
`nameplate_no` VARCHAR ( 120 ) DEFAULT NULL COMMENT '铭牌号',
`is_to_i6000` TINYINT ( 1 ) DEFAULT '1' COMMENT '是否同步I6000：0否1是',
`is_to_erp` TINYINT ( 1 ) DEFAULT '1' COMMENT '是否同步ERP(是否立即转资)：0否1是',
`is_usable` TINYINT ( 1 ) DEFAULT '1' COMMENT '是否可用：0否1是',
`is_temp` INT ( 1 ) DEFAULT NULL COMMENT '是否暂存：0否1是',
`is_deleted` INT ( 1 ) DEFAULT '0' COMMENT '是否已删除：0否1是',
`status` INT ( 1 ) DEFAULT NULL COMMENT '工单状态；0为入库，1已入库',
`remark` VARCHAR ( 512 ) DEFAULT NULL COMMENT '备注',
`create_user` BIGINT ( 20 ) DEFAULT NULL COMMENT '创建人',
`create_dept` BIGINT ( 20 ) DEFAULT NULL COMMENT '创建部门',
`create_time` datetime DEFAULT NULL COMMENT '创建时间',
`update_user` BIGINT ( 20 ) DEFAULT NULL COMMENT '修改人',
`update_time` datetime DEFAULT NULL COMMENT '修改时间',
`attach_id` BIGINT ( 20 ) DEFAULT NULL COMMENT '附件表id',
PRIMARY KEY ( `id` ) USING BTREE
) ENGINE = INNODB AUTO_INCREMENT=100  DEFAULT CHARSET = utf8mb4 COMMENT = '设备入库表';
CREATE TABLE `idevelop_device_storage_list` (
`id` BIGINT ( 20 ) NOT NULL AUTO_INCREMENT COMMENT '主键',
`storage_id` BIGINT ( 20 ) NOT NULL COMMENT '入库ID',
`device_code` VARCHAR ( 128 ) DEFAULT NULL COMMENT '设备编码',
`erp_asset_code` VARCHAR ( 128 ) DEFAULT NULL COMMENT 'erp资产编码',
`device_name` VARCHAR ( 128 ) DEFAULT NULL COMMENT '设备名称',
`device_status` INT ( 1 ) DEFAULT NULL COMMENT '设备状态',
`device_asset_info` TINYTEXT COMMENT '资产信息',
`device_hardware_info` TINYTEXT COMMENT '硬件配置信息',
`tenant_id` VARCHAR ( 12 ) DEFAULT '000000' COMMENT '租户ID',
`create_user` BIGINT ( 20 ) DEFAULT NULL COMMENT '创建人',
`create_dept` BIGINT ( 20 ) DEFAULT NULL COMMENT '创建部门',
`create_time` datetime DEFAULT NULL COMMENT '创建时间',
`update_user` BIGINT ( 20 ) DEFAULT NULL COMMENT '修改人',
`update_time` datetime DEFAULT NULL COMMENT '修改时间',
`status` INT ( 1 ) DEFAULT NULL COMMENT '状态',
`is_deleted` INT ( 1 ) DEFAULT '0' COMMENT '是否已删除0否1是',
`remark` VARCHAR ( 512 ) DEFAULT NULL COMMENT '备注',
PRIMARY KEY ( `id` ) USING BTREE,
KEY `storage_id` ( `storage_id` ) USING BTREE
) ENGINE = INNODB AUTO_INCREMENT=100 DEFAULT CHARSET = utf8mb4 COMMENT = '设备入库明细表';
CREATE TABLE `idevelop_device_storage_template` (
`id` BIGINT ( 20 ) NOT NULL AUTO_INCREMENT COMMENT '主键',
`tenant_id` VARCHAR ( 12 ) DEFAULT '000000' COMMENT '租户ID',
`create_user` BIGINT ( 20 ) DEFAULT NULL COMMENT '创建人',
`create_dept` BIGINT ( 20 ) DEFAULT NULL COMMENT '创建部门',
`create_time` datetime DEFAULT NULL COMMENT '创建时间',
`status` INT ( 1 ) DEFAULT NULL COMMENT '状态',
`is_deleted` INT ( 1 ) DEFAULT '0' COMMENT '是否已删除 0否1是',
`device_category` VARCHAR ( 60 ) DEFAULT NULL COMMENT '设备分类',
`device_type` VARCHAR ( 60 ) DEFAULT NULL COMMENT '设备类型',
`file_type` VARCHAR ( 50 ) DEFAULT NULL COMMENT '文件类型，用于指定文件下载流格式mime',
`file_name` VARCHAR ( 512 ) DEFAULT NULL COMMENT '附件名称，原始文件名称',
`file_path` VARCHAR ( 512 ) DEFAULT NULL COMMENT '附件地址',
`file_path_mobile` VARCHAR ( 512 ) DEFAULT NULL COMMENT '附件手机端访问地址',
`file_size` BIGINT ( 10 ) DEFAULT NULL COMMENT '文件大小(kb)',
`remark` VARCHAR ( 256 ) DEFAULT NULL COMMENT '备注',
PRIMARY KEY ( `id` ) USING BTREE
) ENGINE = INNODB AUTO_INCREMENT=100 DEFAULT CHARSET = utf8mb4 COMMENT = '设备入库导入模板表';

/*
2024-3-1 增加保存uuid、配置项id
 */
ALTER TABLE idevelop_device_storage_list ADD `uuid` VARCHAR ( 64 ) DEFAULT NULL COMMENT 'uuid';
ALTER TABLE idevelop_device_storage_list ADD `ci_entity_id` VARCHAR ( 64 ) DEFAULT NULL COMMENT '配置项id';
ALTER TABLE idevelop_device_storage_list ADD `is_to_cmdb` int(1) DEFAULT '0' COMMENT '新增cmdb台账 0否1是';

