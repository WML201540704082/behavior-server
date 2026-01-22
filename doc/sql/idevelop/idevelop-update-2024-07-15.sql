-- ---------------------------------------------------------------------------------------------------------------------
-- Date: 2024-07-15 已完成
-- SQL语句:
CREATE TABLE `idevelop_device_old_model_config` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `tenant_id` varchar(12) DEFAULT '000000' COMMENT '租户ID',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_dept` bigint(20) DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `status` int(1) DEFAULT NULL COMMENT '状态',
  `is_deleted` int(1) DEFAULT '0' COMMENT '是否已删除 0否1是',
  `config_type` varchar(60) DEFAULT NULL COMMENT '配置类型',
  `config_type_code` varchar(255) DEFAULT NULL COMMENT '配置类型代号',
  `config_item` varchar(60) DEFAULT NULL COMMENT '配置项',
  `value` varchar(60) DEFAULT NULL COMMENT '取值',
  `device_category` bigint(120) DEFAULT NULL COMMENT '设备分类',
  `device_type` bigint(120) DEFAULT NULL COMMENT '设备类型',
  `device_category_name` varchar(120) DEFAULT NULL COMMENT '设备分类名称',
  `decice_type_name` varchar(120) DEFAULT NULL COMMENT '设备类型名称',
  `update_user` varchar(100) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='老旧设备打分模型配置表';
-- ---------------------------------------------------------------------------------------------------------------------
-- Date: 2024-07-15 已完成
-- SQL语句:
INSERT INTO `idevelop_device_old_model_config` VALUES (1, '000000', -1, NULL, NULL, -1, 0, '全局配置', 'Z', '考核周期', '2022', -1, -1, '', '', '-1', NULL, '');
INSERT INTO `idevelop_device_old_model_config` VALUES (2, '000000', -1, NULL, NULL, -1, 0, '全局配置', 'Z', '数据源', '0', -1, -1, '', '', '-1', NULL, '');
INSERT INTO `idevelop_device_old_model_config` VALUES (3, '000000', -1, NULL, NULL, -1, 0, '机房级别', 'A', '市公司核心机房', '6', -1, -1, '', '', '-1', NULL, '');
INSERT INTO `idevelop_device_old_model_config` VALUES (4, '000000', -1, NULL, NULL, -1, 0, '机房级别', 'A', '分中心/县公司核心机房', '4', -1, -1, '', '', '-1', NULL, '');
INSERT INTO `idevelop_device_old_model_config` VALUES (5, '000000', -1, NULL, NULL, -1, 0, '机房级别', 'A', '供电所机房', '2', -1, -1, '', '', '-1', NULL, '');
INSERT INTO `idevelop_device_old_model_config` VALUES (6, '000000', -1, NULL, NULL, -1, 0, '机房级别', 'A', '楼层配线间', '1', -1, -1, '', '', '-1', NULL, '');
INSERT INTO `idevelop_device_old_model_config` VALUES (7, '000000', -1, NULL, NULL, -1, 0, '机房功能', 'B', '信息机房', '1.2', -1, -1, '', '', '-1', NULL, '');
INSERT INTO `idevelop_device_old_model_config` VALUES (8, '000000', -1, NULL, NULL, -1, 0, '机房功能', 'B', '信息通信机房', '0.8', -1, -1, '', '', '-1', NULL, '');
INSERT INTO `idevelop_device_old_model_config` VALUES (9, '000000', -1, NULL, NULL, -1, 0, '机房功能', 'B', '与其他专业合用机房', '0.5', -1, -1, '', '', '-1', NULL, '');
INSERT INTO `idevelop_device_old_model_config` VALUES (10, '000000', -1, NULL, NULL, -1, 0, '设备状态', 'S', '库存备用', '3', -1, -1, '', '', '-1', NULL, '');
INSERT INTO `idevelop_device_old_model_config` VALUES (11, '000000', -1, NULL, NULL, -1, 0, '设备状态', 'S', '在运', '1', -1, -1, '', '', '-1', NULL, '');
INSERT INTO `idevelop_device_old_model_config` VALUES (12, '000000', -1, NULL, NULL, -1, 0, '设备状态', 'S', '退运在库', '3', -1, -1, '', '', '-1', NULL, '');
INSERT INTO `idevelop_device_old_model_config` VALUES (13, '000000', -1, NULL, NULL, -1, 0, '设备状态', 'S', '待报废', '3', -1, -1, '', '', '-1', NULL, '');
INSERT INTO `idevelop_device_old_model_config` VALUES (14, '000000', -1, NULL, NULL, -1, 0, '设备状态', 'S', '已报废', '3', -1, -1, '', '', '-1', NULL, '');
INSERT INTO `idevelop_device_old_model_config` VALUES (15, '000000', -1, NULL, NULL, -1, 0, '超龄时间', 'T', '超龄(T&gt;0)', '2', -1, -1, '', '', '-1', NULL, '');
INSERT INTO `idevelop_device_old_model_config` VALUES (16, '000000', -1, NULL, NULL, -1, 0, '超龄时间', 'T', '未超龄(T≤0)', '1', -1, -1, '', '', '-1', NULL, '');
INSERT INTO `idevelop_device_old_model_config` VALUES (17, '000000', -1, NULL, NULL, -1, 0, '服务风险', 'E', '近三年故障次数', '1', -1, -1, '', '', '-1', NULL, '');
INSERT INTO `idevelop_device_old_model_config` VALUES (18, '000000', -1, NULL, NULL, -1, 0, '服务风险', 'E', '近三年隐患次数', '0.2', -1, -1, '', '', '-1', NULL, '');
INSERT INTO `idevelop_device_old_model_config` VALUES (19, '000000', -1, NULL, NULL, -1, 0, '运维情况', 'C', '有', '2', -1, -1, '', '', '-1', NULL, '');
INSERT INTO `idevelop_device_old_model_config` VALUES (20, '000000', -1, NULL, NULL, -1, 0, '运维情况', 'C', '无', '3', -1, -1, '', '', '-1', NULL, '');
INSERT INTO `idevelop_device_old_model_config` VALUES (21, '000000', -1, NULL, NULL, -1, 0, '设备产权和运维部门', 'D', '运维部门和产权部门均为数字化管理部门', '4', -1, -1, '', '', '-1', NULL, '');
INSERT INTO `idevelop_device_old_model_config` VALUES (22, '000000', -1, NULL, NULL, -1, 0, '设备产权和运维部门', 'D', '产权部门非数字化管理部门', '2', -1, -1, '', '', '-1', NULL, '');
INSERT INTO `idevelop_device_old_model_config` VALUES (23, '000000', -1, NULL, NULL, -1, 0, '设备产权和运维部门', 'D', '均非数字化管理部门', '1', -1, -1, '', '', '-1', NULL, '');
INSERT INTO `idevelop_device_old_model_config` VALUES (24, '000000', -1, NULL, NULL, -1, 0, '运行情况自评价', 'F', '需要', '1', NULL, NULL, '', '', '-1', NULL, '');
INSERT INTO `idevelop_device_old_model_config` VALUES (25, '000000', -1, NULL, NULL, -1, 0, '运行情况自评价', 'F', '不需要', '0', NULL, NULL, '', '', '-1', NULL, '');
INSERT INTO `idevelop_device_old_model_config` VALUES (26, '000000', -1, NULL, NULL, -1, 0, 'n-1满足情况', 'H', '市公司核心机房/县公司核心机房，去除F为1的设备后，剩余设备不满足n-1', '6', NULL, 1135308252184578, '', '空调', '-1', NULL, '');
INSERT INTO `idevelop_device_old_model_config` VALUES (27, '000000', -1, NULL, NULL, -1, 0, 'n-1满足情况', 'H', '市公司核心机房/县公司核心机房，去除F为1的设备后，满足n-1但n=2', '4', NULL, 1135308252184578, '', '空调', '-1', NULL, '');
INSERT INTO `idevelop_device_old_model_config` VALUES (28, '000000', -1, NULL, NULL, -1, 0, 'n-1满足情况', 'H', '市公司核心机房/县公司核心机房，去除F为1的设备后，满足n-1且n>2', '2', NULL, 1135308252184578, '', '空调', '-1', NULL, '');
INSERT INTO `idevelop_device_old_model_config` VALUES (30, '000000', -1, NULL, NULL, -1, 0, 'n-1满足情况', 'H', '市公司核心机房/县公司核心机房，去除F为1的设备后，剩余设备不满足n-1', '6', NULL, 1135308252184587, '', 'UPS', '-1', NULL, '');
INSERT INTO `idevelop_device_old_model_config` VALUES (31, '000000', -1, NULL, NULL, -1, 0, 'n-1满足情况', 'H', '市公司核心机房/县公司核心机房，去除F为1的设备后，满足n-1但n=2', '4', NULL, 1135308252184587, '', 'UPS', '-1', NULL, '');
INSERT INTO `idevelop_device_old_model_config` VALUES (32, '000000', -1, NULL, NULL, -1, 0, 'n-1满足情况', 'H', '市公司核心机房/县公司核心机房，去除F为1的设备后，满足n-1且n>2', '2', NULL, 1135308252184587, '', 'UPS', '-1', NULL, '');
INSERT INTO `idevelop_device_old_model_config` VALUES (33, '000000', -1, NULL, NULL, -1, 0, 'n-1满足情况', 'H', '市公司核心机房/县公司核心机房，去除F为1的设备后，剩余设备不满足n-1', '6', NULL, 1135308252184582, '', '蓄电池', '-1', NULL, '');
INSERT INTO `idevelop_device_old_model_config` VALUES (34, '000000', -1, NULL, NULL, -1, 0, 'n-1满足情况', 'H', '市公司核心机房/县公司核心机房，去除F为1的设备后，满足n-1但n=2', '4', NULL, 1135308252184582, '', '蓄电池', '-1', NULL, '');
INSERT INTO `idevelop_device_old_model_config` VALUES (35, '000000', -1, NULL, NULL, -1, 0, 'n-1满足情况', 'H', '市公司核心机房/县公司核心机房，去除F为1的设备后，满足n-1且n>2', '2', NULL, 1135308252184582, '', '蓄电池', '-1', NULL, '');
INSERT INTO `idevelop_device_old_model_config` VALUES (36, '000000', -1, NULL, NULL, -1, 0, 'n-1满足情况', 'H', '其它', '1', NULL, NULL, '', '其他', '-1', NULL, '');

SET FOREIGN_KEY_CHECKS = 1;
-- ---------------------------------------------------------------------------------------------------------------------
-- Date: 2024-07-15 已完成
-- SQL语句:
CREATE TABLE `idevelop_device_old_list` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `tenant_id` varchar(12) DEFAULT '000000' COMMENT '租户ID',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_dept` bigint(20) DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `status` int(1) DEFAULT NULL COMMENT '状态 0-未上报 1-待审批 2-审批通过 3-审批驳回',
  `is_deleted` int(1) DEFAULT '0' COMMENT '是否已删除 0否1是',
  `score` double(100,2) DEFAULT NULL COMMENT '分数',
  `device_name` varchar(50) DEFAULT NULL COMMENT '设备名称',
  `device_code` varchar(120) DEFAULT NULL COMMENT '设备编码',
  `room_function` int(2) DEFAULT NULL COMMENT '机房功能 4-信息机房  5-信息通信机房  6-与其他专业合用机房',
  `device_status` varchar(60) DEFAULT NULL COMMENT '设备状态',
  `device_category` varchar(120) DEFAULT NULL COMMENT '设备分类',
  `device_type` varchar(120) DEFAULT NULL COMMENT '设备类型',
  `device_category_name` varchar(120) DEFAULT NULL COMMENT '设备分类名称',
  `device_type_name` varchar(120) DEFAULT NULL COMMENT '设备类型名称',
  `update_user` varchar(60) DEFAULT NULL COMMENT '自评修改人',
  `update_time` datetime DEFAULT NULL COMMENT '自评修改时间',
  `owner_unit` varchar(255) DEFAULT NULL COMMENT '产权单位',
  `owner_unit_code` varchar(255) DEFAULT NULL COMMENT '产权单位编码',
  `property_dept` varchar(255) DEFAULT NULL COMMENT '产权部门',
  `property_dept_code` varchar(255) DEFAULT NULL COMMENT '产权部门编码',
  `operation_unit` varchar(255) DEFAULT NULL COMMENT '运维单位',
  `operation_unit_code` varchar(255) DEFAULT NULL COMMENT '运维单位编码',
  `operation_dept` varchar(255) DEFAULT NULL COMMENT '运维部门',
  `operation_dept_code` varchar(255) DEFAULT NULL COMMENT '运维部门编码',
  `operation_condition` int(1) DEFAULT NULL COMMENT '运维情况 7 无  8 有',
  `oprt_date_first` datetime DEFAULT NULL COMMENT '首次投运日期',
  `fault_count` int(255) DEFAULT NULL COMMENT '近三年故障次数',
  `fault_detail` varchar(255) DEFAULT NULL COMMENT '近三年故障情况',
  `hidden_count` int(255) DEFAULT NULL COMMENT '近三年隐患个数',
  `hidden_detail` varchar(255) DEFAULT NULL COMMENT '近三年隐患情况',
  `is_change` int(2) DEFAULT NULL COMMENT '自评是否更换  9-需要 10-不需要',
  `change_user` varchar(60) DEFAULT NULL COMMENT '自评修改人',
  `change_reason` varchar(2048) DEFAULT NULL COMMENT '自评修改原因',
  `is_project` int(1) DEFAULT NULL COMMENT '是否完成立项',
  `project_time` datetime DEFAULT NULL COMMENT '立项更换时间',
  `room_code` varchar(255) DEFAULT NULL COMMENT '机房编码',
  `room_type` varchar(255) DEFAULT NULL COMMENT '机房类型',
  `region_code` varchar(255) DEFAULT NULL COMMENT '区域编码',
  `room_level_score` double(20,2) DEFAULT NULL COMMENT '机房级别得分',
  `room_function_score` double(20,2) DEFAULT NULL COMMENT '机房功能得分',
  `over_age_score` double(20,2) DEFAULT NULL COMMENT '超龄时间得分',
  `device_status_score` double(20,2) DEFAULT NULL COMMENT '设备状态得分',
  `maintenance_score` double(20,2) DEFAULT NULL COMMENT '维保情况得分',
  `dept_score` double(20,2) DEFAULT NULL COMMENT '设备归属部门得分',
  `service_risk_score` double(20,2) DEFAULT NULL COMMENT '服务风险得分',
  `apprise_own_score` double(20,2) DEFAULT NULL COMMENT '运行情况自评得分',
  `customize_score` double(20,2) DEFAULT NULL COMMENT 'n-1满足情况得分',
  `over_age` double(20,2) DEFAULT NULL COMMENT '超龄时间',
  `device_count` int(50) DEFAULT NULL COMMENT '设备总数',
  `review_library_mark` int(2) DEFAULT NULL COMMENT '评审库标识    1-评审库',
  `approval_opinion` text COMMENT '评审意见',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='老旧设备表\r\n';
-- ---------------------------------------------------------------------------------------------------------------------
-- Date: 2024-07-15 已完成
-- SQL语句:
CREATE TABLE `idevelop_device_old_file` (
  `id` varchar(20) NOT NULL COMMENT '主键id',
  `device_code` varchar(120) DEFAULT NULL COMMENT '设备编码',
  `file_url` varchar(255) DEFAULT NULL COMMENT '附件url',
  `file_name` varchar(255) DEFAULT NULL COMMENT '附件名',
  `file_type` varchar(20) DEFAULT NULL COMMENT '附件类型',
  `tenant_id` varchar(12) DEFAULT '000000' COMMENT '租户ID',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(2) DEFAULT NULL COMMENT '是否已删除',
  `status` tinyint(2) DEFAULT NULL COMMENT '无用字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备工单附件';

-- ---------------------------------------------------------------------------------------------------------------------
-- Date: 2024-07-15 已完成
-- SQL语句:
ALTER TABLE `idevelop`.`idevelop_resource_room`
ADD COLUMN `room_function` int(2) NULL COMMENT '机房功能 4-信息机房  5-信息通信机房  6-与其他专业合用机房' AFTER `room_type`;

-- ---------------------------------------------------------------------------------------------------------------------
-- Date: 2024-07-15 已完成
-- SQL语句:
ALTER TABLE `idevelop`.`idevelop_device_repair_list`
ADD COLUMN `user_tel` varchar(60) NULL COMMENT '使用人联系方式' AFTER `use_user`;


-- ---------------------------------------------------------------------------------------------------------------------
-- Date: 2024-07-15 已完成
-- SQL语句:
INSERT INTO `idevelop`.`idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`, `keep_alive`) VALUES (1803248194405408769, 1759825352884785153, '/assets/oldEquipment', '老旧设备库', 'menu', '/assets/oldEquipment', 'iconfont iconicon_work', 5, 1, 1, 1, '', 0, 0);
INSERT INTO `idevelop`.`idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`, `keep_alive`) VALUES (1803250750644953089, 1803248194405408769, 'oldEquipmentlConfuguration', '老旧模型配置', 'menu', '/assets/oldEquipmentlConfuguration', 'iconfont iconicon_dispose', 3, 1, 1, 1, '', 0, 0);
INSERT INTO `idevelop`.`idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`, `keep_alive`) VALUES (1804029689331150850, 1803248194405408769, 'oldEquipmentLibrary', '老旧设备自评库', 'menu', '/assets/oldEquipmentLibrary', 'iconfont iconicon_addresslist', 0, 1, 1, 1, '', 0, 0);
INSERT INTO `idevelop`.`idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`, `keep_alive`) VALUES (1805530981673324545, 1803248194405408769, 'oldEquipmentAudit', '老旧设备评审库', 'menu', '/assets/oldEquipmentAudit', 'iconfont iconicon_compile', 2, 1, 1, 1, '', 0, 0);
-- ---------------------------------------------------------------------------------------------------------------------
-- Date: 2024-07-17 未完成
-- SQL语句:
ALTER TABLE `idevelop`.`idevelop_device_old_list`
ADD COLUMN `score_cycle` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '打分周期' AFTER `approval_opinion`;
