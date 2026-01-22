-- ---------------------------------------------------------------------------------------------------------------------
-- Date:
-- SQL语句:
-- ---------------------------------------------------------------------------------------------------------------------


-- ---------------------------------------------------------------------------------------------------------------------
-- Date: 2024-05-31
-- SQL语句:

CREATE TABLE `idevelop_verify_config`
(
    `key`        varchar(48) DEFAULT NULL COMMENT 'cmdb字段key值',
    `value`      varchar(1)  DEFAULT NULL COMMENT '1true，其他值false',
    `type`       varchar(1)  DEFAULT NULL COMMENT '类型：1主机设备，2网络设备，3安全设备，4存储设备，5终端设备，6 key=defaultValue保存默认值',
    `is_deleted` varchar(1)  DEFAULT '0' COMMENT '0有效'
) COMMENT='数据变更是否需要人工确认配置表';


INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('memoryCardSize', '0', '1');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('cpuModel', '0', '1');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('powerInfo', '0', '1');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('cpuInfo', '0', '1');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('hardDiskCapability', '1', '1');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('cpuCoreSize', '1', '1');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('OSTypeCode', '1', '1');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('hddNum', '0', '1');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('OSVersion', '0', '1');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('sn', '0', '1');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('brand', '0', '1');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('hbaCardWWN', '0', '1');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('IP', '1', '1');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('hbaCardSize', '0', '1');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('cpuClockSpeed', '1', '1');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('powerModel', '0', '1');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('MAC', '1', '1');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('OSInfo', '1', '1');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('cpuBrand', '0', '1');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('series', '0', '1');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('cpuSize', '1', '1');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('deviceModel', '0', '1');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('cpuFrequecy', '1', '1');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('cpuArchCode', '1', '1');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('memSize', '1', '1');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('hardDiskTypeCode', '1', '1');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('series', '0', '2');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('IP', '1', '2');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('netPortNum', '0', '2');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('deviceModel', '0', '2');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('sn', '0', '2');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('brand', '0', '2');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('MAC', '1', '2');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('series', '0', '3');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('IP', '1', '3');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('deviceModel', '0', '3');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('sn', '0', '3');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('brand', '0', '3');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('MAC', '1', '3');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('ratedPower', '0', '4');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('series', '0', '4');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('IP', '1', '4');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('deviceModel', '0', '4');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('storageCapacity', '1', '4');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('sn', '0', '4');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('brand', '0', '4');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('MAC', '1', '4');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('memSize', '1', '4');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('antiVirusModel', '0', '5');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('IP', '1', '5');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('cpuModel', '0', '5');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('antiVirusLibrary', '0', '5');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('virusSacnTime', '0', '5');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('cpuClockSpeed', '1', '5');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('lastActiveDate', '1', '5');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('MAC', '1', '5');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('hardDiskCapability', '1', '5');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('cpuBrand', '0', '5');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('series', '0', '5');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('OSTypeCode', '1', '5');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('OSVersion', '0', '5');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('antiVirusProduct', '0', '5');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('deviceModel', '0', '5');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('sn', '0', '5');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('cpuFrequecy', '1', '5');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('brand', '0', '5');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('memSize', '1', '5');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('hardDiskTypeCode', '1', '5');
INSERT INTO `idevelop_verify_config` (`key`, `value`, `type`)
VALUES ('defaultValue', '1', '6');

-- ---------------------------------------------------------------------------------------------------------------------
-- Date: 2024-06-05
-- SQL语句:
ALTER TABLE `idevelop`.`idevelop_device_scrap_list`
    CHANGE COLUMN `account_code_erp` `device_code_erp` varchar (50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ERP台账编码' AFTER `asset_code_erp`;

INSERT INTO `idevelop`.`idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`,
                                       `action`, `is_open`, `remark`, `is_deleted`, `keep_alive`)
VALUES (1797797072819109889, 1752941559413792770, 'erp', 'ERP信息管理', 'menu', '/data/erp',
        'iconfont iconicon_coinpurse_line', 11, 1, 1, 1, 'erp信息管理', 0, 0);

CREATE TABLE `idevelop_cientity_ls`
(
    `id`              int(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
    `cientity_id`     bigint(50) DEFAULT NULL COMMENT '配置项id',
    `cientity_uuid`   varchar(50)  DEFAULT NULL COMMENT '配置项uuid',
    `cientity_ci`     bigint(50) DEFAULT NULL COMMENT '配置项模型id',
    `device_area`     varchar(10)  DEFAULT NULL COMMENT '设备区域',
    `device_code`     varchar(255) DEFAULT NULL COMMENT '设备编码',
    `device_category` bigint(50) DEFAULT NULL COMMENT '设备分类编码',
    `device_type`     bigint(50) DEFAULT NULL COMMENT '设备类型编码',
    `device_status`   bigint(50) DEFAULT NULL COMMENT '设备状态编码',
    `is_deleted`      tinyint(1) DEFAULT '0' COMMENT '是否删除, 0:否;1:是',
    `tenant_id`       varchar(12)  DEFAULT NULL COMMENT '租户ID',
    `create_user`     bigint(24) DEFAULT NULL COMMENT '创建人',
    `create_dept`     bigint(24) DEFAULT NULL COMMENT '创建部门',
    `create_time`     datetime     DEFAULT NULL COMMENT '创建时间',
    `update_user`     bigint(24) DEFAULT NULL COMMENT '修改人',
    `update_time`     datetime     DEFAULT NULL COMMENT '修改时间',
    `status`          tinyint(2) DEFAULT NULL COMMENT '状态',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;



-- ---------------------------------------------------------------------------------------------------------------------
-- Date: 2024-06-23
-- SQL语句:
select `value`
from idevelop_verify_config
WHERE `type` = '5'
  and `key` = 'lastActiveDate';

update `idevelop_verify_config`
set `value` = '0'
WHERE `type` = '5'
  and `key` = 'lastActiveDate';

-- ---------------------------------------------------------------------------------------------------------------------
-- Date: 2024-06-23
-- SQL语句:

CREATE TABLE `idevelop_h3c_path_config`
(
    `region_name` varchar(32)  DEFAULT NULL COMMENT '区域名称',
    `region_code` varchar(6)   DEFAULT NULL COMMENT '区域编码',
    `h3c_path`    varchar(128) DEFAULT NULL COMMENT 'H3C U-Centor 地址',
    `h3c_user`    varchar(32)  DEFAULT NULL COMMENT 'H3C U-Centor 账号',
    `h3c_pwd`     varchar(32) COMMENT 'H3C U-Centor 密码',
    `is_deleted`  varchar(1)   DEFAULT '0' COMMENT '0有效'
) COMMENT ='配置ucenter区域、接口地址、账号、密码';






-- ---------------------------------------------------------------------------------------------------------------------
-- Date: 2024-06-19
-- SQL语句:
CREATE TABLE `idevelop_warning` (
                                    `id` varchar(20) NOT NULL COMMENT 'id',
                                    `warning_number` varchar(50) DEFAULT NULL COMMENT '告警编码',
                                    `warning_source` varchar(255) DEFAULT NULL COMMENT '告警来源',
                                    `warning_device` varchar(100) DEFAULT NULL COMMENT '告警设备',
                                    `warning_level` varchar(10) DEFAULT NULL COMMENT '告警等级',
                                    `warning_type` varchar(50) DEFAULT NULL COMMENT '告警类型',
                                    `warning_reason` varchar(255) DEFAULT NULL COMMENT '告警原因',
                                    `warning_detail` varchar(255) DEFAULT NULL COMMENT '告警详情',
                                    `warning_gather_time` datetime DEFAULT NULL COMMENT '告警采集时间',
                                    `warning_begin_time` datetime DEFAULT NULL COMMENT '告警开始时间',
                                    `warning_end_time` datetime DEFAULT NULL COMMENT '告警结束时间',
                                    `warning_category` varchar(50) DEFAULT NULL COMMENT '告警设备分类id',
                                    `warning_category_name` varchar(50) DEFAULT NULL COMMENT '告警设备分类名称',
                                    `warning_device_type` varchar(50) DEFAULT NULL COMMENT '告警设备类型id',
                                    `warning_device_type_name` varchar(50) DEFAULT NULL COMMENT '告警设备类型名称',
                                    `warning_count` int(2) DEFAULT NULL COMMENT '告警次数',
                                    `warning_sort` tinyint(10) DEFAULT '0' COMMENT '告警排序',
                                    `warning_is_main` tinyint(1) DEFAULT '0' COMMENT '是否是主告警信息  0 是 1 否',
                                    `warning_show` tinyint(1) DEFAULT '0' COMMENT '存在子告警，列表显示 + 号 0 不显示 1 显示',
                                    `receive_unit` varchar(50) DEFAULT NULL COMMENT '领用单位id',
                                    `receive_unit_name` varchar(100) DEFAULT NULL COMMENT '领用单位名称',
                                    `receive_duty_dept` varchar(50) DEFAULT NULL COMMENT '领用部门',
                                    `receive_duty_dept_name` varchar(100) DEFAULT NULL COMMENT '领用部门名称',
                                    `receive_use_name` varchar(50) DEFAULT NULL COMMENT '领用责任人',
                                    `receive_use_phone` varchar(11) DEFAULT NULL COMMENT '领用责任人联系方式',
                                    `user_name` varchar(50) DEFAULT NULL COMMENT '使用人',
                                    `user_phone` varchar(11) DEFAULT NULL COMMENT '使用人联系方式',
                                    `confirm_status` tinyint(1) DEFAULT '0' COMMENT '告警确认状态 0 未确认 1 已确认 2 已忽略',
                                    `dispose_status` tinyint(1) DEFAULT '0' COMMENT '告警处置状态 0 未处置 1 已处置 2 已忽略',
                                    `dispose_user` varchar(20) DEFAULT NULL COMMENT '告警处置人',
                                    `dispose_phone` varchar(11) DEFAULT NULL COMMENT '告警处置人联系方式',
                                    `dispose_dept` varchar(50) DEFAULT NULL COMMENT '告警处置人所在部门',
                                    `dispose_time` datetime DEFAULT NULL COMMENT '告警处置时间',
                                    `dispose_result` varchar(255) DEFAULT NULL COMMENT '告警处置结果',
                                    `dispose_order` varchar(50) DEFAULT NULL COMMENT '告警处置关联工单编号',
                                    `dispose_order_type` tinyint(2) DEFAULT NULL COMMENT '告警处置关联工单类型 0 设备变更 1 设备投运',
                                    `dispose_device_code` varchar(50) DEFAULT NULL COMMENT '设备投运关联设备编码',
                                    `device_id` varchar(100) DEFAULT NULL COMMENT '台账设备id',
                                    `device_uuid` varchar(100) DEFAULT NULL COMMENT '台账设备UUID',
                                    `device_cid` varchar(100) DEFAULT NULL COMMENT '台账设备CID',
                                    `device_code` varchar(100) DEFAULT NULL COMMENT '台账设备编码',
                                    `device_ip` varchar(20) DEFAULT NULL COMMENT '台账设备ip',
                                    `device_mac` varchar(20) DEFAULT NULL COMMENT '台账设备mac',
                                    `device_factory_number` varchar(50) DEFAULT NULL COMMENT '台账设备出厂序列号',
                                    `device_factory_date` date DEFAULT NULL COMMENT '台账设备出厂日期',
                                    `device_hard_disk_size` double DEFAULT NULL COMMENT '硬盘容量（GB）',
                                    `device_memory_size` double DEFAULT NULL COMMENT '内存大小（GB）',
                                    `device_system_version` varchar(64) DEFAULT NULL COMMENT '操作系统版本号',
                                    `device_system_type` varchar(20) DEFAULT NULL COMMENT '操作系统类型',
                                    `device_last_time` datetime DEFAULT NULL COMMENT '设备最后一次活跃时间',
                                    `device_library_time` datetime DEFAULT NULL COMMENT '病毒库更新时间',
                                    `gather_ip` varchar(20) DEFAULT NULL COMMENT '采集台账设备ip',
                                    `gather_mac` varchar(20) DEFAULT NULL COMMENT '采集台账设备mac',
                                    `gather_factory_number` varchar(50) DEFAULT NULL COMMENT '采集台账设备出厂序列号',
                                    `gather_factory_date` date DEFAULT NULL COMMENT '采集台账设备出厂日期',
                                    `gather_hard_disk_size` double DEFAULT NULL COMMENT '采集硬盘容量（GB）',
                                    `gather_memory_size` double DEFAULT NULL COMMENT '采集内存大小（GB）',
                                    `gather_system_version` varchar(64) DEFAULT NULL COMMENT '采集操作系统版本号',
                                    `gather_system_type` varchar(20) DEFAULT NULL COMMENT '采集操作系统类型',
                                    `gather_last_time` datetime DEFAULT NULL COMMENT '采集最后一次活跃时间',
                                    `gather_library_time` datetime DEFAULT NULL COMMENT '采集病毒库更新时间',
                                    `history_time` varchar(255) DEFAULT NULL COMMENT '同一类型告警，不同时间组装，通过英文逗号隔开',
                                    `region_code` varchar(50) DEFAULT NULL COMMENT '设备所在区域编码',
                                    `create_user` varchar(50) DEFAULT NULL COMMENT '告警信息创建人',
                                    `create_time` datetime DEFAULT NULL COMMENT '告警信息创建时间',
                                    `update_user` varchar(50) DEFAULT NULL COMMENT '告警信息处理更新人',
                                    `update_time` datetime DEFAULT NULL COMMENT '告警信息操作更新时间',
                                    `status` tinyint(1) DEFAULT '0' COMMENT '无效字段（适配框架创建）',
                                    `is_deleted` tinyint(1) DEFAULT '0' COMMENT '无效字段（适配框架创建）',
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备异动告警';


CREATE TABLE `idevelop_device_data_based_template`
(
    `id`          bigint(20) NOT NULL COMMENT '主键',
    `file_type`   varchar(50)  DEFAULT NULL COMMENT '文件类型',
    `file_name`   varchar(512) DEFAULT NULL COMMENT '文件名称',
    `file_path`   varchar(512) DEFAULT NULL COMMENT '文件地址',
    `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `is_deleted`  int(2) DEFAULT '0' COMMENT '是否已删除',
    `create_dept` bigint(20) DEFAULT NULL COMMENT '创建部门',
    `status`      int(2) DEFAULT NULL COMMENT '状态',
    `tenant_id`   varchar(12)  DEFAULT '000000' COMMENT '租户ID',
    `remark`      varchar(256) DEFAULT NULL COMMENT '备注',
    `update_user` bigint(20) DEFAULT NULL COMMENT '更新人',
    `update_time` datetime     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备-数据治理模板表';


-- ---------------------------------------------------------------------------------------------------------------------
-- Date: 2024-06-20 已完成
-- SQL语句:
INSERT INTO `idevelop`.`idevelop_schedule_job`(`job_id`, `bean_name`, `params`, `cron_expression`, `status`, `remark`,
                                               `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`,
                                               `update_time`, `is_deleted`, `type`)
VALUES (74483, 'assetRefresh', '', '0 0 0 * * ?', 1, '空间资源管理刷新单位名称', '000000', NULL, '2024-06-20 16:16:44',
        NULL, NULL, NULL, 0, '其他');

INSERT INTO `idevelop`.`idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`,
                                       `action`, `is_open`, `remark`, `is_deleted`, `keep_alive`)
VALUES (1798961528076439554, 1759825352884785153, 'itaiDevice', '信创设备管理', 'menu', '/assets/itaiDevice',
        'iconfont iconicon_airplay', 4, 1, 1, 1, '', 0, 1);

-- ---------------------------------------------------------------------------------------------------------------------
-- Date: 2024-06-21 已完成
-- SQL语句:
insert into idevelop_verify_config (`key`, `value`, `type`, `is_deleted`)
values ("cpuArchCode", "0", "5", "0");

-- ---------------------------------------------------------------------------------------------------------------------
-- Date: 2024-06-25 已完成
-- SQL语句:
insert into idevelop_verify_config (`key`, `value`, `type`, `is_deleted`)
values ("factoryDate", "0", "5", "0");

CREATE TABLE `idevelop_cientity_ls`
(
    `id`                     int(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
    `cientity_id`            bigint(50) DEFAULT NULL COMMENT '配置项id',
    `cientity_uuid`          varchar(50)   DEFAULT NULL COMMENT '配置项uuid',
    `cientity_ci`            bigint(50) DEFAULT NULL COMMENT '配置项模型id',
    `device_area`            varchar(10)   DEFAULT NULL COMMENT '设备区域',
    `device_dept`            varchar(50)   DEFAULT NULL COMMENT '设备部门',
    `device_owner_unit`      varchar(50)   DEFAULT NULL COMMENT '设备产权单位',
    `device_owner_unit_code` varchar(50)   DEFAULT NULL COMMENT '设备产权单位编码',
    `device_owner_dept`      varchar(50)   DEFAULT NULL COMMENT '设备产权部门',
    `device_owner_dept_code` varchar(50)   DEFAULT NULL COMMENT '设备产权部门编码',
    `device_code`            varchar(255)  DEFAULT NULL COMMENT '设备编码',
    `device_category`        bigint(50) DEFAULT NULL COMMENT '设备分类编码',
    `device_type`            bigint(50) DEFAULT NULL COMMENT '设备类型编码',
    `device_status`          bigint(50) DEFAULT NULL COMMENT '设备状态编码',
    `is_deleted`             tinyint(1) DEFAULT '0' COMMENT '是否删除, 0:否;1:是',
    `tenant_id`              varchar(12)   DEFAULT NULL COMMENT '租户ID',
    `create_user`            bigint(24) DEFAULT NULL COMMENT '创建人',
    `create_dept`            bigint(24) DEFAULT NULL COMMENT '创建部门',
    `create_time`            datetime      DEFAULT NULL COMMENT '创建时间',
    `update_user`            bigint(24) DEFAULT NULL COMMENT '修改人',
    `update_time`            datetime      DEFAULT NULL COMMENT '修改时间',
    `status`                 tinyint(2) DEFAULT NULL COMMENT '状态',
    `expand`                 varchar(1024) DEFAULT NULL COMMENT '拓展字段',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3244553 DEFAULT CHARSET=utf8mb4 COMMENT='CMBD配置项简易表';

-- ---------------------------------------------------------------------------------------------------------------------
-- Date: 2024-07-02 已完成
-- SQL语句:
CREATE TABLE `idevelop_cmdb_dict_ci`
(
    `ci_id`            bigint(24) NOT NULL COMMENT '模型ID',
    `ci_name`          varchar(48)  NOT NULL COMMENT '模型英文名',
    `ci_label`         varchar(255) NOT NULL COMMENT '模型中文名',
    `ci_icon`          varchar(50)  NOT NULL COMMENT '模型图标',
    `ci_type_id`       bigint(24) DEFAULT NULL COMMENT '类型ID ecmdb_ci_type',
    `cascade_ci_info`  varchar(48) DEFAULT NULL COMMENT '级联模型信息',
    `is_exist_cascade` tinyint(1) DEFAULT '0' COMMENT '是否需要级联: 0: 不需要; 1: 需要',
    `is_deleted`       tinyint(1) DEFAULT '0' COMMENT '是否删除, 0:否;1:是',
    `tenant_id`        varchar(12) DEFAULT NULL COMMENT '租户ID',
    `create_user`      bigint(24) DEFAULT NULL COMMENT '创建人',
    `create_dept`      bigint(24) DEFAULT NULL COMMENT '创建部门',
    `create_time`      datetime    DEFAULT NULL COMMENT '创建时间',
    `update_user`      bigint(24) DEFAULT NULL COMMENT '修改人',
    `update_time`      datetime    DEFAULT NULL COMMENT '修改时间',
    `status`           tinyint(2) DEFAULT NULL COMMENT '状态',
    PRIMARY KEY (`ci_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='cmdb字典模型管理';

ALTER TABLE `idevelop`.`idevelop_cmdb_ui`
    MODIFY COLUMN `name` varchar (50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性/配置项/模型/字典模型key' AFTER `id`,
    MODIFY COLUMN `value` bigint(50) NULL DEFAULT NULL COMMENT '属性/配置项/模型/字典模型value' AFTER `name`,
    MODIFY COLUMN `type` tinyint(2) NULL DEFAULT 0 COMMENT '类型 0: 属性key, 1: 配置项key, 2: 模型key 3: 字典模型Key' AFTER `remark`;

-- ---------------------------------------------------------------------------------------------------------------------
-- Date: 2024-07-05 已完成
-- SQL语句:

CREATE TABLE `idevelop_brand_series_model`
(
    `brand` varchar(64)  DEFAULT NULL COMMENT '品牌',
    `series` varchar(128)   DEFAULT NULL COMMENT '系列',
    `device_model`    varchar(128) DEFAULT NULL COMMENT '型号',
    `maker`    varchar(128) DEFAULT NULL COMMENT '制造商',
    `maker_id`     varchar(64) COMMENT '制造商ID',
    `brand_id`    varchar(64)  DEFAULT NULL COMMENT '品牌ID',
    `series_id`     varchar(64) COMMENT '系列ID',
    `device_model_id`    varchar(64)  DEFAULT NULL COMMENT '型号ID',
    `brand_series_model`     varchar(255) COMMENT '字典品牌系列型号',
    `data_status`     varchar(1) DEFAULT '1'  COMMENT '0有效，1需要确认品牌系列型号',
    `is_deleted`  tinyint(1)   DEFAULT 0 COMMENT '0有效'
) COMMENT ='字典品牌系列型号';

CREATE TABLE `idevelop_brand_tianqing`
(
    `brand` varchar(64)  DEFAULT NULL COMMENT '品牌',
    `series` varchar(128)   DEFAULT NULL COMMENT '系列',
    `device_model`    varchar(128) DEFAULT NULL COMMENT '型号',
    `maker`    varchar(128) DEFAULT NULL COMMENT '制造商',
    `maker_id`     varchar(64) COMMENT '制造商ID',
    `brand_id`    varchar(64)  DEFAULT NULL COMMENT '品牌ID',
    `series_id`     varchar(64) COMMENT '系列ID',
    `device_model_id`    varchar(64)  DEFAULT NULL COMMENT '型号ID',
    `brand_series_model`     varchar(255) COMMENT '天擎抽取的数据，品牌系列型号',
    `data_status`     varchar(1) DEFAULT '1'  COMMENT '0有效，1需要确认品牌系列型号',
    `is_deleted`  tinyint(1)   DEFAULT 0 COMMENT '0有效'
) COMMENT ='天擎抽取的数据，品牌系列型号';

CREATE TABLE `idevelop_brand_tianqing_backup` like `idevelop_brand_tianqing`;
ALTER TABLE `idevelop_brand_tianqing_backup` COMMENT = 'idevelop_brand_tianqing表的备份表';

CREATE TABLE `idevelop_brand_xinchuang` like `idevelop_brand_tianqing`;
ALTER TABLE `idevelop_brand_xinchuang` COMMENT = '信创设备品牌系列型号';


insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('英特尔','LunaPier','LunaPier','英特尔 LunaPier');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('微星','MS-7D82','MS-7D82','微星 MS-7D82');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('微星','MS-7B97','MS-7B97','微星 MS-7B97');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('微星','MS-7A15','MS-7A15','微星 MS-7A15');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('微星','MS-7996','MS-7996','微星 MS-7996');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('微星','MS-7721','MS-7721','微星 MS-7721');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('微星','MS-7529','MS-7529','微星 MS-7529');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('清华同方','H81M-CT','H81M-CT','清华同方 H81M-CT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('清华同方','H61H2-CM','H61H2-CM','清华同方 H61H2-CM');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('清华同方','A5000','All in One','清华同方 A5000 All in One');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('七彩虹','H310M-T','H310M-T PRO','七彩虹 H310M-T PRO');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('七彩虹','CH510M-K','CH510M-K M.2','七彩虹 CH510M-K M.2');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('七彩虹','C.H110M-VH','C.H110M-VH PRO','七彩虹 C.H110M-VH PRO');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('七彩虹','C.H110M-K','C.H110M-K D3 PRO','七彩虹 C.H110M-K D3 PRO');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('铭瑄','MS-TZZ','H510M-F','铭瑄 MS-TZZ H510M-F');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('梅捷','SY-KL','H310CM-V3H V2.0','梅捷 SY-KL H310CM-V3H V2.0');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','T4988D-00','联想 扬天T4988D-00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','T4988D','联想 扬天T4988D');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','T4900v-00','联想 扬天T4900v-00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','T4900D','联想 扬天T4900D');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','T2900V','联想 扬天T2900V');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','T2900D','联想 扬天T2900D');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','R4918d','联想 扬天R4918d');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','R4910d','联想 扬天R4910d');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','R4900d','联想 扬天R4900d');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','M6881n-00','联想 扬天M6881n-00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','M6880s-00','联想 扬天M6880s-00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','M6602D','联想 扬天M6602D');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','M6600N','联想 扬天M6600N');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','M4680N','联想 扬天M4680N');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','M4662n-00','联想 扬天M4662n-00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','M4632D','联想 扬天M4632D');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','M4630s-00','联想 扬天M4630s-00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','M4602D','联想 扬天M4602D');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','M4600n-10','联想 扬天M4600n-10');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','M4600n-00','联想 扬天M4600n-00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','M3311D','联想 扬天M3311D');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','M3310D','联想 扬天M3310D');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','M2631n-00','联想 扬天M2631n-00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','M2622N','联想 扬天M2622N');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','M2620n-00','联想 扬天M2620n-00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','M2620D','联想 扬天M2620D');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','M2613D','联想 扬天M2613D');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','M2612D','联想 扬天M2612D');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','M2610n-33','联想 扬天M2610n-33');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','M2610n-10','联想 扬天M2610n-10');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','M2610n-00','联想 扬天M2610n-00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','M2610D-10','联想 扬天M2610D-10');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','M2200r-00','联想 扬天M2200r-00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','A8000k-11','联想 扬天A8000k-11');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','A4600T','联想 扬天A4600T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','A4600R','联想 扬天A4600R');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','A4600k-00','联想 扬天A4600k-00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','A4600K','联想 扬天A4600K');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','A2600T','联想 扬天A2600T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','M7360','联想 启天M7360');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','M7330','联想 启天M7330');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','M730E','联想 启天M730E');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','M7300','联想 启天M7300');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','M7160','联想 启天M7160');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','M715E','联想 启天M715E');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','M7150','联想 启天M7150');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','M7130','联想 启天M7130');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','M7120','联想 启天M7120');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','M6900','联想 启天M6900');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','M6400-N000','联想 启天M6400-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','M6300','联想 启天M6300');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','M4550-N000','联想 启天M4550-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','M4500-N000','联想 启天M4500-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','M4390','联想 启天M4390');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','M4380','联想 启天M4380');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','M436E','联想 启天M436E');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','M4360-N088','联想 启天M4360-N088');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','M4360-N000','联想 启天M4360-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','M4360','联想 启天M4360');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','M435E','联想 启天M435E');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','M4350-N099','联想 启天M4350-N099');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','M4350-N000','联想 启天M4350-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','M4350-B102','联想 启天M4350-B102');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','M4350','联想 启天M4350');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','M4340','联想 启天M4340');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','M4330','联想 启天M4330');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','M4300','联想 启天M4300');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','B4550-B302','联想 启天B4550-B302');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','B4550-B102','联想 启天B4550-B102');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','B4550-B031','联想 启天B4550-B031');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','B4550-B002','联想 启天B4550-B002');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','B4360-N001','联想 启天B4360-N001');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','B4360-B063','联想 启天B4360-B063');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','B4360-B015','联想 启天B4360-B015');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','B4360-B003','联想 启天B4360-B003');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','家悦','家悦I','联想 家悦I');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','家悦','家悦E','联想 家悦E');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','XINYUANMENG','F358','联想 XINYUANMENGF358');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','XINYUANMENG','F2995','联想 XINYUANMENGF2995');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','XINYUANMENG','F2993','联想 XINYUANMENGF2993');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkSystem','ST558 -[7Y16CTO1WW]- Tower','联想 ThinkSystem ST558 -[7Y16CTO1WW]- Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkPad','T420','联想 ThinkPad T420');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M920t','联想 ThinkCentre-M920t');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','ThinkCentre','联想 ThinkCentre');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','启天 M6900','联想 ThinkCentre 启天M6900');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M8000T','联想 ThinkCentre ThinkCentre M8000T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','neo S500IRB','联想 ThinkCentre neo S500IRB');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','neo P600-01IAB','联想 ThinkCentre neo P600-01IAB');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','neo P600-00IAB','联想 ThinkCentre neo P600-00IAB');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M950t-N000','联想 ThinkCentre M950t-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M950t-D213','联想 ThinkCentre M950t-D213');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M930t-N000','联想 ThinkCentre M930t-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M930t','联想 ThinkCentre M930t');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M920z-N000 All in One','联想 ThinkCentre M920z-N000 All in One');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M920t-N000','联想 ThinkCentre M920t-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M920t-D403','联想 ThinkCentre M920t-D403');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M920t-D252','联想 ThinkCentre M920t-D252');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M920t-D232','联想 ThinkCentre M920t-D232');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M920t-D225','联想 ThinkCentre M920t-D225');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M920t-D003','联想 ThinkCentre M920t-D003');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M920t','联想 ThinkCentre M920t');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M910z-N000 All in One','联想 ThinkCentre M910z-N000 All in One');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M910t-N000','联想 ThinkCentre M910t-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M910t-N000','联想 ThinkCentre M910t-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M910s-N000','联想 ThinkCentre M910s-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M90','联想 ThinkCentre M90');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M8600t-N000','联想 ThinkCentre M8600t-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M8600t-D184','联想 ThinkCentre M8600t-D184');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M8600t-D065','联想 ThinkCentre M8600t-D065');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M830z-N000 All in One','联想 ThinkCentre M830z-N000 All in One');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M818z-N000 All in One','联想 ThinkCentre M818z-N000 All in One');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M810z-N000 All in One','联想 ThinkCentre M810z-N000 All in One');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M750t-E035','联想 ThinkCentre M750t-E035');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M750t-D220','联想 ThinkCentre M750t-D220');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M737t-D215','联想 ThinkCentre M737t-D215');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M730t-N000','联想 ThinkCentre M730t-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M730q-N000 未知','联想 ThinkCentre M730q-N000 未知');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M730e-A012','联想 ThinkCentre M730e-A012');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M720t-N000','联想 ThinkCentre M720t-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M720t-D265','联想 ThinkCentre M720t-D265');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M720q-N000 未知','联想 ThinkCentre M720q-N000 未知');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M720e-A010','联想 ThinkCentre M720e-A010');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M710t-D372','联想 ThinkCentre M710t-D372');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M710e-N030','联想 ThinkCentre M710e-N030');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M70a Gen 3-N000 All in One','联想 ThinkCentre M70a Gen 3-N000 All in One');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M6280T','联想 ThinkCentre M6280T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M6200T','联想 ThinkCentre M6200T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M6106T','联想 ThinkCentre M6106T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M6000T','联想 ThinkCentre M6000T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M4500k-N000','联想 ThinkCentre M4500k-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M4350t-N070','联想 ThinkCentre M4350t-N070');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M4350t-N000','联想 ThinkCentre M4350t-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M4350S','联想 ThinkCentre M4350S');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M3500q-N000','联想 ThinkCentre M3500q-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','E97','联想 ThinkCentre E97');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','E96x','联想 ThinkCentre E96x');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','E77s','联想 ThinkCentre E77s');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','E77','联想 ThinkCentre E77');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','E76p','联想 ThinkCentre E76p');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','E75','联想 ThinkCentre E75');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','E74','联想 ThinkCentre E74');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','E73s','联想 ThinkCentre E73s');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','E73','联想 ThinkCentre E73');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','E700IAB','联想 ThinkCentre E700IAB');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','M4500','联想 Product 启天 M4500');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M920t-N000','联想 M920t-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','M760A242800X','M760A242800X','联想 M760A242800X');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','Lenovo','V110-15ISK 80TL','联想 Lenovo V110-15ISK 80TL');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','S710','联想 Lenovo Product 扬天S710');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','ThinkCentre','联想 Lenovo Product ThinkCentre');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','ThinkCentre','联想 Lenovo Product ThinkCebtre');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','TC-M1A','TC-M1A','联想 Lenovo Product TC-M1A__');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','TC-M16','TC-M16','联想 Lenovo Product TC-M16__');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','R9000','R9000-25ICZ','联想 Lenovo Product R9000-25ICZ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QianTian','M428','联想 Lenovo Product QianTianM428');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','NA21737218','NA21737218','联想 Lenovo Product NA21737218');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M940t','联想 Lenovo Product M940t');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','m920t','联想 Lenovo Product m920t');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M920','联想 Lenovo Product M920');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M910T','联想 Lenovo Product M910T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M910','联想 Lenovo Product M910');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M9010T','联想 Lenovo Product M9010T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M8600t-N000','联想 Lenovo Product M8600t-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M8500t','联想 Lenovo Product M8500t');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','m8500s','联想 Lenovo Product m8500s');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M706BRSQ','联想 Lenovo Product M706BRSQ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','M4900','联想 Lenovo Product M4900');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','M4360','联想 Lenovo Product M4360');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','M4350','联想 Lenovo Product M4350');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','All','in One','联想 Lenovo Product All in One');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M910T','联想 Lenovo Product 910T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90M2A02SCD','90M2A02SCD','联想 Lenovo Product 90M2A02SCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','Lenovo','ideapad Y700-14ISK','联想 Lenovo ideapad Y700-14ISK');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','Lenovo','H5055 90BG003VCD','联想 Lenovo H5055 90BG003VCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','Lenovo','H5050 90B700KJCD','联想 Lenovo H5050 90B700KJCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','Lenovo','H5050 90B700H1CD','联想 Lenovo H5050 90B700H1CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','Lenovo','H3060 90D9005NCD','联想 Lenovo H3060 90D9005NCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','Lenovo','H3050 90B900A1CD','联想 Lenovo H3050 90B900A1CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','Lenovo','G50500I 90AXCTO1WW','联想 Lenovo G50500I 90AXCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','Lenovo','G5000','联想 Lenovo G5000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','Lenovo','ECI-521 11AR52TCCN','联想 Lenovo ECI-521 11AR52TCCN');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','Lenovo','D7070 90CQZ1CRCN','联想 Lenovo D7070 90CQZ1CRCN');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','Lenovo','D5055 90CF0001CD','联想 Lenovo D5055 90CF0001CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','Lenovo','D5050 90CH001TCD','联想 Lenovo D5050 90CH001TCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','Lenovo','D5050 90CH001LCD','联想 Lenovo D5050 90CH001LCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','Lenovo','D5050 90CH001KCD','联想 Lenovo D5050 90CH001KCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','Lenovo','D5050 90CH001HCD','联想 Lenovo D5050 90CH001HCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','Lenovo','D5050 90CH001FCD','联想 Lenovo D5050 90CH001FCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','Lenovo','D5050 90CH0014CD','联想 Lenovo D5050 90CH0014CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','Lenovo','D5050 90CH000MCD','联想 Lenovo D5050 90CH000MCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','Lenovo','D5050 90CH000CCD','联想 Lenovo D5050 90CH000CCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','Lenovo','C460 All in One','联想 Lenovo C460 All in One');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','Lenovo','3000 870020z','联想 Lenovo 3000 870020z');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','F0F60007CD','F0F60007CD All in One','联想 F0F60007CD All in One');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','F0E8003MCD','F0E8003MCD All in One','联想 F0E8003MCD All in One');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','F0DJ00CVCD','F0DJ00CVCD All in One','联想 F0DJ00CVCD All in One');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','F0CT0003CP','F0CT0003CP All in One','联想 F0CT0003CP All in One');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','F0CE0012CD','F0CE0012CD All in One','联想 F0CE0012CD All in One');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','F0C3002LCP','F0C3002LCP All in One','联想 F0C3002LCP All in One');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','F0BY00J5CD','F0BY00J5CD All in One','联想 F0BY00J5CD All in One');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','F0BHCTO1WW','F0BHCTO1WW All in One','联想 F0BHCTO1WW All in One');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','A6800','联想 A6800');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90WE0006CD','90WE0006CD','联想 90WE0006CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90WE0001CD','90WE0001CD','联想 90WE0001CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90WB0008CD','90WB0008CD','联想 90WB0008CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90VWCTO1WW','90VWCTO1WW','联想 90VWCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90VWA00QCD','90VWA00QCD','联想 90VWA00QCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90VWA00GCD','90VWA00GCD','联想 90VWA00GCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90VU000QCD','90VU000QCD','联想 90VU000QCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90VU0007CD','90VU0007CD','联想 90VU0007CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90VT007UCD','90VT007UCD','联想 90VT007UCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90TDCTO1WW','90TDCTO1WW','联想 90TDCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90TBCTO1WW','90TBCTO1WW','联想 90TBCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90TBA050CD','90TBA050CD','联想 90TBA050CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90TACTO1WW','90TACTO1WW','联想 90TACTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90TAA0B8CD','90TAA0B8CD','联想 90TAA0B8CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90TAA0ARCD','90TAA0ARCD','联想 90TAA0ARCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90TAA07GCD','90TAA07GCD','联想 90TAA07GCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90TAA07FCD','90TAA07FCD','联想 90TAA07FCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90TAA06KCD','90TAA06KCD','联想 90TAA06KCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90TAA06D00','90TAA06D00','联想 90TAA06D00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SL0020CD','90SL0020CD','联想 90SL0020CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SL000GCD','90SL000GCD','联想 90SL000GCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SHCTO1WW','90SHCTO1WW','联想 90SHCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SFCTO1WW','90SFCTO1WW','联想 90SFCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SFA07SCD','90SFA07SCD','联想 90SFA07SCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SFA07BCD','90SFA07BCD','联想 90SFA07BCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SFA05UCD','90SFA05UCD','联想 90SFA05UCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SFA05TCD','90SFA05TCD','联想 90SFA05TCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SFA05SCD','90SFA05SCD','联想 90SFA05SCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SFA02DCD','90SFA02DCD','联想 90SFA02DCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SDA02HCD','90SDA02HCD','联想 90SDA02HCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SDA01TCD','90SDA01TCD','联想 90SDA01TCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SB001BCD','90SB001BCD','联想 90SB001BCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SB000UCD','90SB000UCD','联想 90SB000UCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SB000KCD','90SB000KCD','联想 90SB000KCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SB0007CD','90SB0007CD','联想 90SB0007CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90RQ0006CD','90RQ0006CD','联想 90RQ0006CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90RN000HCD','90RN000HCD','联想 90RN000HCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90RM001KCD','90RM001KCD','联想 90RM001KCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90RM000UCD','90RM000UCD','联想 90RM000UCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90RM0005CD','90RM0005CD','联想 90RM0005CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90RK000CCD','90RK000CCD','联想 90RK000CCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90QKA036CD','90QKA036CD','联想 90QKA036CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90NYCTO1WW','90NYCTO1WW','联想 90NYCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90NB0032CD','90NB0032CD','联想 90NB0032CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90MU000DCD','90MU000DCD','联想 90MU000DCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90MQCTO1WW','90MQCTO1WW','联想 90MQCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90MNA0N9CD','90MNA0N9CD','联想 90MNA0N9CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90MD002CCD','90MD002CCD','联想 90MD002CCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90MD001CCD','90MD001CCD','联想 90MD001CCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90MCCTO1WW','90MCCTO1WW','联想 90MCCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90MB001NCD','90MB001NCD','联想 90MB001NCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90MB001KCD','90MB001KCD','联想 90MB001KCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90M3A001CD','90M3A001CD','联想 90M3A001CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90M2CTO1WW','90M2CTO1WW','联想 90M2CTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90M2A0A6CD','90M2A0A6CD','联想 90M2A0A6CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90M2A09VCD','90M2A09VCD','联想 90M2A09VCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90M2A04XCD','90M2A04XCD','联想 90M2A04XCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90M2A04WCD','90M2A04WCD','联想 90M2A04WCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90M2A02SCD','90M2A02SCD','联想 90M2A02SCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90LX000FCD','90LX000FCD','联想 90LX000FCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90LU0023CD','90LU0023CD','联想 90LU0023CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90LU000GCD','90LU000GCD','联想 90LU000GCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90L0S02J00','90L0S02J00','联想 90L0S02J00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90L0CTO1WW','90L0CTO1WW','联想 90L0CTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90KSCTO1WW','90KSCTO1WW','联想 90KSCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90KQS0G200','90KQS0G200','联想 90KQS0G200');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90KQCTO1WW','90KQCTO1WW','联想 90KQCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90KQA0M3CD','90KQA0M3CD','联想 90KQA0M3CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90KDCTO1WW','90KDCTO1WW','联想 90KDCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90K7CTO1WW','90K7CTO1WW','联想 90K7CTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90K7A00DCD','90K7A00DCD','联想 90K7A00DCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90JB0019CP','90JB0019CP','联想 90JB0019CP');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90J8CTO1WW','90J8CTO1WW','联想 90J8CTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90J3001BCD','90J3001BCD','联想 90J3001BCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90J30017CD','90J30017CD','联想 90J30017CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90HKCTO1WW','90HKCTO1WW','联想 90HKCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90HKA0GCCD','90HKA0GCCD','联想 90HKA0GCCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90HKA0GBCD','90HKA0GBCD','联想 90HKA0GBCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90HKA03YCD','90HKA03YCD','联想 90HKA03YCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90HJCTO1WW','90HJCTO1WW','联想 90HJCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90HJA0YFCD','90HJA0YFCD','联想 90HJA0YFCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90HJA0JSCD','90HJA0JSCD','联想 90HJA0JSCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90HHCTO1WW','90HHCTO1WW','联想 90HHCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GV0014CD','90GV0014CD','联想 90GV0014CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GQZ3ZMCN','90GQZ3ZMCN','联想 90GQZ3ZMCN');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GKCTO1WW','90GKCTO1WW','联想 90GKCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GK001DCD','90GK001DCD','联想 90GK001DCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GH000LCD','90GH000LCD','联想 90GH000LCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GGCTO1WW','90GGCTO1WW','联想 90GGCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GG001VCD','90GG001VCD','联想 90GG001VCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GG001SCD','90GG001SCD','联想 90GG001SCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GG001PCD','90GG001PCD','联想 90GG001PCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GG001JCD','90GG001JCD','联想 90GG001JCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GG001DCD','90GG001DCD','联想 90GG001DCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GG001BCD','90GG001BCD','联想 90GG001BCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GG0015CD','90GG0015CD','联想 90GG0015CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GG000SCD','90GG000SCD','联想 90GG000SCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GG000MCD','90GG000MCD','联想 90GG000MCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GF0004CD','90GF0004CD','联想 90GF0004CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GE000GCD','90GE000GCD','联想 90GE000GCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GE0007CD','90GE0007CD','联想 90GE0007CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GD0003CD','90GD0003CD','联想 90GD0003CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GB003RCD','90GB003RCD','联想 90GB003RCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90G2A010CD','90G2A010CD','联想 90G2A010CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90G2A00YCD','90G2A00YCD','联想 90G2A00YCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90G2A00WCD','90G2A00WCD','联想 90G2A00WCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90G2A00UCD','90G2A00UCD','联想 90G2A00UCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90G2A009CD','90G2A009CD','联想 90G2A009CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90G2A008CD','90G2A008CD','联想 90G2A008CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90G1CTO1WW','90G1CTO1WW','联想 90G1CTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90G1A0VKCD','90G1A0VKCD','联想 90G1A0VKCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90G1A0VBCD','90G1A0VBCD','联想 90G1A0VBCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90G1A0PHCD','90G1A0PHCD','联想 90G1A0PHCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90G1A0PCCD','90G1A0PCCD','联想 90G1A0PCCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90G1A0MFCD','90G1A0MFCD','联想 90G1A0MFCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90G1A0LTCD','90G1A0LTCD','联想 90G1A0LTCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90G1A0LFCD','90G1A0LFCD','联想 90G1A0LFCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90G1A09KCD','90G1A09KCD','联想 90G1A09KCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90G0CTO1WW','90G0CTO1WW','联想 90G0CTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90G0A0TPCD','90G0A0TPCD','联想 90G0A0TPCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90G0A0M2CD','90G0A0M2CD','联想 90G0A0M2CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90FYS04700','90FYS04700','联想 90FYS04700');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90FYCTO1WW','90FYCTO1WW','联想 90FYCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90FS000JCD','90FS000JCD','联想 90FS000JCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90FS0009CD','90FS0009CD','联想 90FS0009CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90FS0007CD','90FS0007CD','联想 90FS0007CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90FQCTO1WW','90FQCTO1WW','联想 90FQCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90FJ001TCD','90FJ001TCD','联想 90FJ001TCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90FJ000BCD','90FJ000BCD','联想 90FJ000BCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90FJ0007CD','90FJ0007CD','联想 90FJ0007CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90FF001QCD','90FF001QCD','联想 90FF001QCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90F9CTO1WW','90F9CTO1WW','联想 90F9CTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90F5001RCN','90F5001RCN','联想 90F5001RCN');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90F5000BCN','90F5000BCN','联想 90F5000BCN');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90F50008CN','90F50008CN','联想 90F50008CN');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90F30002CN','90F30002CN','联想 90F30002CN');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90ETCTO1WW','90ETCTO1WW','联想 90ETCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90ESCTO1WW','90ESCTO1WW','联想 90ESCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90ES001CCN','90ES001CCN','联想 90ES001CCN');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90ES0019CN','90ES0019CN','联想 90ES0019CN');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90ES000VCN','90ES000VCN','联想 90ES000VCN');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90ES000UCN','90ES000UCN','联想 90ES000UCN');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90EACTO1WW','90EACTO1WW','联想 90EACTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90E9000TCD','90E9000TCD','联想 90E9000TCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90E9000BCD','90E9000BCD','联想 90E9000BCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90E90007CD','90E90007CD','联想 90E90007CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90E8CTO1WW','90E8CTO1WW','联想 90E8CTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90E80038CD','90E80038CD','联想 90E80038CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90E80035CD','90E80035CD','联想 90E80035CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90E80032CD','90E80032CD','联想 90E80032CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90E80031CD','90E80031CD','联想 90E80031CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90E8002LCD','90E8002LCD','联想 90E8002LCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90E8001WCD','90E8001WCD','联想 90E8001WCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90E8001VCD','90E8001VCD','联想 90E8001VCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90E8000WCD','90E8000WCD','联想 90E8000WCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90E8000MCD','90E8000MCD','联想 90E8000MCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90E8000KCD','90E8000KCD','联想 90E8000KCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90E6000KCD','90E6000KCD','联想 90E6000KCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90E5000YCD','90E5000YCD','联想 90E5000YCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90E5000PCD','90E5000PCD','联想 90E5000PCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90DWCTO1WW','90DWCTO1WW','联想 90DWCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90DWA00LCD','90DWA00LCD','联想 90DWA00LCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90DWA005CD','90DWA005CD','联想 90DWA005CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90DTA01CCD','90DTA01CCD','联想 90DTA01CCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90DTA01BCD','90DTA01BCD','联想 90DTA01BCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90DTA00SCD','90DTA00SCD','联想 90DTA00SCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90DSCTO1WW','90DSCTO1WW','联想 90DSCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90DSA0GQCD','90DSA0GQCD','联想 90DSA0GQCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90DSA0GECD','90DSA0GECD','联想 90DSA0GECD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90DG0033CP','90DG0033CP','联想 90DG0033CP');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90DCCTO1WW','90DCCTO1WW','联想 90DCCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90D8A00800','90D8A00800','联想 90D8A00800');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90D7A02400','90D7A02400','联想 90D7A02400');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90D7A01Q00','90D7A01Q00','联想 90D7A01Q00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90D7A01C00','90D7A01C00','联想 90D7A01C00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90D7A01800','90D7A01800','联想 90D7A01800');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90D7A01300','90D7A01300','联想 90D7A01300');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90D7A00Y00','90D7A00Y00','联想 90D7A00Y00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90D7A00W00','90D7A00W00','联想 90D7A00W00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90D7A00V00','90D7A00V00','联想 90D7A00V00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90D7A00P00','90D7A00P00','联想 90D7A00P00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90D7A00600','90D7A00600','联想 90D7A00600');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90D7A00100','90D7A00100','联想 90D7A00100');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90D4CTO1WW','90D4CTO1WW','联想 90D4CTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90D4A027CN','90D4A027CN','联想 90D4A027CN');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90D0CTO1WW','90D0CTO1WW','联想 90D0CTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90D0A007CN','90D0A007CN','联想 90D0A007CN');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90D0A005CN','90D0A005CN','联想 90D0A005CN');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90CYCTO1WW','90CYCTO1WW','联想 90CYCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90CYA04V00','90CYA04V00','联想 90CYA04V00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90CYA04U00','90CYA04U00','联想 90CYA04U00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90CYA03500','90CYA03500','联想 90CYA03500');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90CXCTO1WW','90CXCTO1WW','联想 90CXCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90CXA07S00','90CXA07S00','联想 90CXA07S00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90CXA07H00','90CXA07H00','联想 90CXA07H00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90CWCTO1WW','90CWCTO1WW','联想 90CWCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90CC000HCD','90CC000HCD','联想 90CC000HCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90BYCTO1WW','90BYCTO1WW','联想 90BYCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90BYA01C00','90BYA01C00','联想 90BYA01C00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','80HTCTO1WW','80HTCTO1WW','联想 80HTCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','43512M2 Tower','联想 43512M2 Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','30GUA0JBCW','30GUA0JBCW','联想 30GUA0JBCW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','30FNA1V6CW','30FNA1V6CW','联想 30FNA1V6CW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','30E4A3VSCW','30E4A3VSCW','联想 30E4A3VSCW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','30DLA0EMCW','30DLA0EMCW','联想 30DLA0EMCW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','30DJS9L300','30DJS9L300','联想 30DJS9L300');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','30DJA3F4CW','30DJA3F4CW','联想 30DJA3F4CW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','30DBA44FCW','30DBA44FCW','联想 30DBA44FCW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','30D3A00ACW','30D3A00ACW','联想 30D3A00ACW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','30D0A5SVCW','30D0A5SVCW','联想 30D0A5SVCW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','30D0A4QPCW','30D0A4QPCW','联想 30D0A4QPCW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','30D0A0GFCW','30D0A0GFCW','联想 30D0A0GFCW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','30BYABP5CW','30BYABP5CW','联想 30BYABP5CW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','30BYA9WMCW','30BYA9WMCW','联想 30BYA9WMCW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','30BGA0S500','30BGA0S500','联想 30BGA0S500');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','30BGA02G00','30BGA02G00','联想 30BGA02G00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','30ASA174CW','30ASA174CW Tower','联想 30ASA174CW Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','30ASA0UACW','30ASA0UACW Tower','联想 30ASA0UACW Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','21J70009CD','21J70009CD','联想 21J70009CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','21A2','21A2','联想 21A2');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','20W1A12BCD','20W1A12BCD','联想 20W1A12BCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','20TD001TCD','20TD001TCD','联想 20TD001TCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','20S0A04RCD','20S0A04RCD','联想 20S0A04RCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','20RAA035CD','20RAA035CD','联想 20RAA035CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','20KNA04VCD','20KNA04VCD','联想 20KNA04VCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','12BEA021CD','12BEA021CD','联想 12BEA021CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','12B4A01VCD','12B4A01VCD','联想 12B4A01VCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','11SKCTO1WW','11SKCTO1WW','联想 11SKCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','11KMA07NCD','11KMA07NCD','联想 11KMA07NCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','11CWCTO1WW','11CWCTO1WW','联想 11CWCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10SMCTO1WW','10SMCTO1WW','联想 10SMCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('浪潮','NP5540M3','NP5540M3','浪潮 NP5540M3');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('浪潮','NF5270M5','NF5270M5 Rack Mount Chassis','浪潮 NF5270M5 Rack Mount Chassis');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('技嘉','Z97-HD3','Z97-HD3','技嘉 Z97-HD3');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('技嘉','H81M-S1','H81M-S1','技嘉 H81M-S1');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('技嘉','H81M-DS2','H81M-DS2','技嘉 H81M-DS2');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('技嘉','H81M-D2','H81M-D2','技嘉 H81M-D2');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('技嘉','H510M','H510M H','技嘉 H510M H');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('技嘉','H310M','H310M S2 2.0','技嘉 H310M S2 2.0');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('技嘉','H110M','H110M-S2','技嘉 H110M-S2');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('技嘉','B85M','B85M-D3V-A-SI','技嘉 B85M-D3V-A-SI');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('技嘉','B85M','B85M-D3V','技嘉 B85M-D3V');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('技嘉','B85M','B85M-D2V-SI','技嘉 B85M-D2V-SI');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('技嘉','A320M','A320M-S2H','技嘉 A320M-S2H');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','康柏Pro','6305 MT Mini Tower','惠普 康柏Pro 6305 MT Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','康柏Pro','6300 MT Mini Tower','惠普 康柏Pro 6300 MT Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','康柏Presario','CQ3000 Series','惠普 康柏Presario CQ3000 Series');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','康柏Elite','8380 MT Mini Tower','惠普 康柏Elite 8380 MT Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','康柏Elite','8300 SFF Low Profile','惠普 康柏Elite 8300 SFF Low Profile');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','康柏Elite','8300 MT Mini Tower','惠普 康柏Elite 8300 MT Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','康柏Elite','8300 MT Low Profile','惠普 康柏Elite 8300 MT Low Profile');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','康柏','康柏dx2390 Microtower','惠普 康柏dx2390 Microtower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','康柏','康柏dc7700 Small Form Factor Low Profile','惠普 康柏dc7700 Small Form Factor Low Profile');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','康柏','康柏dc5750 Small Form Factor Low Profile','惠普 康柏dc5750 Small Form Factor Low Profile');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','康柏Elite','康柏8200 Elite MT PC Mini Tower','惠普 康柏8200 Elite MT PC Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','康柏Elite','康柏8200 Elite MT PC Mini Tower','惠普 康柏8200 Elite CMT PC Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','康柏Elite','康柏8180 Elite CMT PC Mini Tower','惠普 康柏8180 Elite CMT PC Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','康柏Elite','康柏8100 Elite CMT PC Mini Tower','惠普 康柏8100 Elite CMT PC Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','康柏Elite','康柏8000 Elite CMT PC Mini Tower','惠普 康柏8000 Elite CMT PC Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','康柏Pro','康柏6280 Pro MT PC Mini Tower','惠普 康柏6280 Pro MT PC Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','康柏Pro','康柏6200 Pro SFF PC Mini Tower','惠普 康柏6200 Pro SFF PC Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','康柏Pro','康柏6200 Pro MT PC Mini Tower','惠普 康柏6200 Pro MT PC Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','康柏Pro','康柏6080 Pro MT PC Mini Tower','惠普 康柏6080 Pro MT PC Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','康柏Pro','康柏6000 Pro SFF PC Low Profile','惠普 康柏6000 Pro SFF PC Low Profile');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','康柏Pro','康柏6000 Pro MT PC Mini Tower','惠普 康柏6000 Pro MT PC Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','康柏Pro','康柏4000 Pro SFF PC Low Profile','惠普 康柏4000 Pro SFF PC Low Profile');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','康柏Elite','康柏 Elite 8300 SFF Low Profile','惠普 惠普 康柏 Elite 8300 SFF Low Profile');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','康柏Elite','康柏 Elite 8300 MT Mini Tower','惠普 惠普 康柏 Elite 8300 MT Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','SLIC','SLIC-CPC','惠普 SLIC-CPC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','SLIC','SLIC-BPC Mini Tower','惠普 SLIC-BPC Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','SLIC','SLIC-BPC Low Profile','惠普 SLIC-BPC Low Profile');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','ProDesk','400 G4 SFF','惠普 ProDesk 400 G4 SFF');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','Pro','Tower 288 G9 PCI Desktop PC','惠普 Pro Tower 288 G9 PCI Desktop PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','p6','p6-1451cx','惠普 p6-1451cx');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','p6','p6-1399cx','惠普 p6-1399cx');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','p6','p6-1389cx','惠普 p6-1389cx');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HPDESK800','HPDESK800','惠普 HPDESK800');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','HP Z640 Workstation Mini Tower','惠普 HP Z640 Workstation Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','HP Z620 Workstation Mini Tower','惠普 HP Z620 Workstation Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','HP Z440 Workstation Mini Tower','惠普 HP Z440 Workstation Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','HP Z400 Workstation Mini Tower','惠普 HP Z400 Workstation Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','HP Z230 Tower Workstation','惠普 HP Z230 Tower Workstation');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','HP Z230 SFF Workstation Low Profile','惠普 HP Z230 SFF Workstation Low Profile');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','HP Z200 Workstation Mini Tower','惠普 HP Z200 Workstation Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','prodesk','hp prodesk 680 g2','惠普 hp prodesk 680 g2');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','ProDesk','HP ProDesk 600 G1 TWR Mini Tower','惠普 HP ProDesk 600 G1 TWR Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','ProDesk','HP ProDesk 600 G1 SFF Low Profile','惠普 HP ProDesk 600 G1 SFF Low Profile');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','ProDesk','HP ProDesk 490 G2 MT','惠普 HP ProDesk 490 G2 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','ProDesk','HP ProDesk 480 G2 MT','惠普 HP ProDesk 480 G2 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','ProDesk','HP ProDesk 480 G2 MT Mini Tower','惠普 HP ProDesk 480 G2 MT Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','ProDesk','HP ProDesk 480 G2 MT (TPM) Mini Tower','惠普 HP ProDesk 480 G2 MT (TPM) Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','ProDesk','HP ProDesk 400 G2 MT','惠普 HP ProDesk 400 G2 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','ProDesk','HP ProDesk 400 G1 MT Mini Tower','惠普 HP ProDesk 400 G1 MT Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','Pro','HP Pro 3381 MT','惠普 HP Pro 3381 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','Pro','HP Pro 3380 MT','惠普 HP Pro 3380 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','Pro','HP Pro 3348 MT','惠普 HP Pro 3348 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','Pro','HP Pro 3340 MT','惠普 HP Pro 3340 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','Pro','HP Pro 3335 MT','惠普 HP Pro 3335 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','Pro','HP Pro 3330 SFF','惠普 HP Pro 3330 SFF');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','Pro','HP Pro 3330 MT','惠普 HP Pro 3330 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','Pro','HP Pro 3300 Series MT','惠普 HP Pro 3300 Series MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','Pro','HP Pro 3000/3080','惠普 HP Pro 3000/3080');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','Pro','HP Pro 3000 Microtower PC','惠普 HP Pro 3000 Microtower PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','Pro','HP Pro 2080 Microtower PC','惠普 HP Pro 2080 Microtower PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','Pro','HP Pro 2000 Microtower PC','惠普 HP Pro 2000 Microtower PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','EliteDesk','HP EliteDesk 880 G1 TWR Mini Tower','惠普 HP EliteDesk 880 G1 TWR Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','EliteDesk','HP EliteDesk 800 G1 TWR Mini Tower','惠普 HP EliteDesk 800 G1 TWR Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','EliteDesk','HP EliteDesk 800 G1 SFF Low Profile','惠普 HP EliteDesk 800 G1 SFF Low Profile');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','康柏','dx2355 MT(WM253PA)','惠普 HP dx2355 MT(WM253PA)');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','康柏','dx2310 MT(VD378PA)','惠普 HP dx2310 MT(VD378PA)');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','800 G1 Low Profile','惠普 HP 800 G1 Low Profile');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','康柏','6300 Mini Tower','惠普 HP 6300 Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','280 Pro G1 MT','惠普 HP 280 Pro G1 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','280 G1 MT','惠普 HP 280 G1 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','EliteDesk','705 G4 SFF','惠普 EliteDesk 705 G4 SFF');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','C8N26AV','C8N26AV Low Profile','惠普 C8N26AV Low Profile');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','Boma','Boma','惠普 Boma');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','EliteDesk','705 G4','惠普 705 G4');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','500-371cn','惠普 500-371cn');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','288 Pro G3 MT','惠普 288 Pro G3 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','280 Pro G3 MT','惠普 280 Pro G3 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','251-028cn','惠普 251-028cn');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','251-011cn','惠普 251-011cn');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','pavilion','21-a211cx','惠普 21-a211cx');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('华为','RH2288','V3 Main Server Chassis','华为 RH2288 V3 Main Server Chassis');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('华为','PUBZ','PUBZ-W','华为 PUBZ-W');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('华为','DQF','DQF-XX All in One','华为 DQF-XX All in One');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('华为','B730','B730-W','华为 B730-W');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('华为','B530','B530-W','华为 B530-W');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('华为','B530E','B530E-W','华为 B530E-W');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('华硕','V230IC','V230IC-DDR4 All in One','华硕 V230IC-DDR4 All in One');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('华硕','ASUSPRO','D640MB_D640MB','华硕 ASUSPRO D640MB_D640MB');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('华硕','ASUS','EXPERTCENTER D700TA_D700TA','华硕 ASUS EXPERTCENTER D700TA_D700TA');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('华硕','All','Series All Series','华硕 All Series');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('宏碁','Veriton','D730','宏碁 Veriton D730');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('宏碁','Shangqi','X4270','宏碁 Shangqi X4270');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('海尔','HaierComputer','HaierComputer','海尔 HaierComputer');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('方正','Founder','Founder PC','方正 Founder PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','XPS','8500','戴尔 XPS 8500');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','A180','戴尔 Vostro A180');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3910','戴尔 Vostro 3910');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3905','戴尔 Vostro 3905');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3902','戴尔 Vostro 3902');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3901','戴尔 Vostro 3901');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3900','戴尔 Vostro 3900');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3890-China HDD Protection','戴尔 Vostro 3890-China HDD Protection');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3888-China HDD Protection','戴尔 Vostro 3888-China HDD Protection');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3881','戴尔 Vostro 3881');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3800','戴尔 Vostro 3800');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3710','戴尔 Vostro 3710');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3690','戴尔 Vostro 3690');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3671-China HDD Protection','戴尔 Vostro 3671-China HDD Protection');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3671','戴尔 Vostro 3671');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3670-China HDD Protection','戴尔 Vostro 3670-China HDD Protection');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3670','戴尔 Vostro 3670');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3669','戴尔 Vostro 3669');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3668','戴尔 Vostro 3668');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3667','戴尔 Vostro 3667');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3660','戴尔 Vostro 3660');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3650','戴尔 Vostro 3650');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3470','戴尔 Vostro 3470');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3268','戴尔 Vostro 3268');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','270s','戴尔 Vostro 270s');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','270','戴尔 Vostro 270');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','260s','戴尔 Vostro 260s');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','260','戴尔 Vostro 260');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','230','戴尔 Vostro 230');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','220 Series','戴尔 Vostro 220 Series');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Precision','WorkStation T3500 Tower','戴尔 Precision WorkStation T3500 Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Precision','T1650 Mini Tower','戴尔 Precision T1650 Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Precision','3660','戴尔 Precision 3660');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Precision','3650 Tower','戴尔 Precision 3650 Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Precision','3640 Tower','戴尔 Precision 3640 Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','PowerEdge','T30 Mini Tower','戴尔 PowerEdge T30 Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','PowerEdge','R740 Rack Mount Chassis','戴尔 PowerEdge R740 Rack Mount Chassis');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','PowerEdge','R430 Rack Mount Chassis','戴尔 PowerEdge R430 Rack Mount Chassis');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','990 Mini Tower','戴尔 OptiPlex 990 Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','980 Mini Tower','戴尔 OptiPlex 980 Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','9020 Space-saving','戴尔 OptiPlex 9020 Space-saving');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','9020 Mini Tower','戴尔 OptiPlex 9020 Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','790 Mini Tower','戴尔 OptiPlex 790 Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','780 Mini Tower','戴尔 OptiPlex 780 Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','760 Mini Tower','戴尔 OptiPlex 760 Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','755 Mini Tower','戴尔 OptiPlex 755 Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','7090','戴尔 OptiPlex 7090');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','7080','戴尔 OptiPlex 7080');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','7070-China HDD Protection','戴尔 OptiPlex 7070-China HDD Protection');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','7070','戴尔 OptiPlex 7070');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','7060','戴尔 OptiPlex 7060');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','7050','戴尔 OptiPlex 7050');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','7040','戴尔 OptiPlex 7040');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','7020 Mini Tower','戴尔 OptiPlex 7020 Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','7010','戴尔 OptiPlex 7010');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','7010 Mini Tower','戴尔 OptiPlex 7010 Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','7000','戴尔 OptiPlex 7000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','5250 AIO All in One','戴尔 OptiPlex 5250 AIO All in One');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','5080','戴尔 OptiPlex 5080');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','5070','戴尔 OptiPlex 5070');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','5050','戴尔 OptiPlex 5050');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','5040','戴尔 OptiPlex 5040');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','390 Mini Tower','戴尔 OptiPlex 390 Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','380 Mini Tower','戴尔 OptiPlex 380 Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','360 Mini Tower','戴尔 OptiPlex 360 Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','330 Mini Tower','戴尔 OptiPlex 330 Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','3240 AIO All in One','戴尔 OptiPlex 3240 AIO All in One');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','3080','戴尔 OptiPlex 3080');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','3070-China HDD Protection','戴尔 OptiPlex 3070-China HDD Protection');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','3060','戴尔 OptiPlex 3060');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','3050','戴尔 OptiPlex 3050');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','3046','戴尔 OptiPlex 3046');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','3020 Space-saving','戴尔 OptiPlex 3020 Space-saving');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','3020 Mini Tower','戴尔 OptiPlex 3020 Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','3010 Mini Tower','戴尔 OptiPlex 3010 Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','210L Mini Tower','戴尔 OptiPlex 210L Mini Tower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Inspiron','660s','戴尔 Inspiron 660s');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Inspiron','660','戴尔 Inspiron 660');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Inspiron','570','戴尔 Inspiron 570');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Inspiron','560s','戴尔 Inspiron 560s');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Inspiron','3910','戴尔 Inspiron 3910');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Inspiron','3668','戴尔 Inspiron 3668');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Inspiron','3650','戴尔 Inspiron 3650');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Inspiron','3647','戴尔 Inspiron 3647');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Dimension','2010','戴尔 Dimension 2010');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','ChengMing','3991-China HDD Protection','戴尔 ChengMing 3991-China HDD Protection');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','ChengMing','3991','戴尔 ChengMing 3991');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','ChengMing','3990-China HDD Protection','戴尔 ChengMing 3990-China HDD Protection');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','ChengMing','3988','戴尔 ChengMing 3988');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','ChengMing','3967','戴尔 ChengMing 3967');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('昂达','9D4-VH-D','9D4-VH-D','昂达 9D4-VH-D');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ZHAOYANG','E47','ZHAOYANG E47    ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','W4020v-00','YangTianW4020v-00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','T4988D','YangTianT4988D');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YANGTIAN','T4970D','YANGTIANT4970D');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','T4900v-00','YangTianT4900v-00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YANGTIAN','T4900D-00','YANGTIANT4900D-00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','T4900D','YangTianT4900D');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','T2900V','YangTianT2900V');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','T2900D','YangTianT2900D');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YANGTIAN','S710','YANGTIANS710');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YANGTIAN','S700','YANGTIANS700');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','R6900d','YangTianR6900d');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','R4960d','YangTianR4960d');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','R4916d','YangTianR4916d');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','R4900d','YangTianR4900d');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','M7100N','YangTianM7100N');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','M6881n-00','YangTianM6881n-00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','M6880n-34','YangTianM6880n-34');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','M6650N','YangTianM6650N');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','M6602D','YangTianM6602D');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','M6600N','YangTianM6600N');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','M6600D','YangTianM6600D');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','M4800n-11','YangTianM4800n-11');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','M4800n-00','YangTianM4800n-00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','M4680N','YangTianM4680N');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','M4680D','YangTianM4680D');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','M4662n-00','YangTianM4662n-00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','M4660D','YangTianM4660D');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','M4630n-34','YangTianM4630n-34');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','M4630n-00','YangTianM4630n-00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','M4630D','YangTianM4630D');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','M4620D','YangTianM4620D');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','M4600n-00','YangTianM4600n-00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YANGTIAN','M4600D-10','YANGTIANM4600D-10');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','M4600D','YangTianM4600D');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','M4200r-00','YangTianM4200r-00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','M3311D','YangTianM3311D');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','M2620D','YangTianM2620D');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','M2613D','YangTianM2613D');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','M2612D','YangTianM2612D');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','M2610n-00','YangTianM2610n-00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','M2015N','YangTianM2015N');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','A8800k-22','YangTianA8800k-22');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','A8800k-00','YangTianA8800k-00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','A8000T','YangTianA8000T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','A6800R','YangTianA6800R');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','A6800q-00','YangTianA6800q-00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','A6000k-00','YangTianA6000k-00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','A4602T','YangTianA4602T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','A4602k-00','YangTianA4602k-00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','A4600T','YangTianA4600T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','A4600k-33','YangTianA4600k-33');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','A4600k-22','YangTianA4600k-22');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','A4600k-00','YangTianA4600k-00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','YangTian','A4600K','YangTianA4600K');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','XINYUANMENG','F2905','XINYUANMENGF2905');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','5890-China HDD Protection','Vostro 5890-China HDD Protection');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','5880-China HDD Protection','Vostro 5880-China HDD Protection');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','5880','Vostro 5880');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','5090-China HDD Protection','Vostro 5090-China HDD Protection');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','470','Vostro 470');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','430','Vostro 430');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3910-China HDD Protection','Vostro 3910-China HDD Protection');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3910','Vostro 3910');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3905','Vostro 3905');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3902','Vostro 3902  ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3901','Vostro 3901');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3900','Vostro 3900  ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3890-China HDD Protection','Vostro 3890-China HDD Protection');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3890','Vostro 3890');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3888-China HDD Protection','Vostro 3888-China HDD Protection');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3881','Vostro 3881');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3710','Vostro 3710');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3690','Vostro 3690');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3681','Vostro 3681');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3671-China HDD Protection','Vostro 3671-China HDD Protection');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3671','Vostro 3671');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3670-China HDD Protection','Vostro 3670-China HDD Protection');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3670','Vostro 3670');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3669','Vostro 3669');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3668','Vostro 3668');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3667','Vostro 3667');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3660','Vostro 3660');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3650','Vostro 3650  ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3471','Vostro 3471');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3470','Vostro 3470');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3268','Vostro 3268');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','3030S','Vostro 3030S');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','270s','Vostro 270s  ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','270','Vostro 270  ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','260s','Vostro 260s  ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','260','Vostro 260   ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','230','Vostro 230                   ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','220s Series','Vostro 220s Series');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Vostro','220 Series','Vostro 220 Series ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('VMware20,1','VMware20,1','VMware20,1','VMware20,1');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('VirtualBox','VirtualBox','VirtualBox','VirtualBox');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Veriton','Veriton','D730','Veriton D730');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('V241IC-R','V241IC-R','V241IC-R','V241IC-R');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('V241ICR','V241ICR','V241ICR','V241ICR');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('英特尔','Thurley','Thurley','Thurley');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M920t-N000','ThinkCentreM920t-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','THINKCENTRE','M910TN000','THINKCENTREM910TN000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M8500t','ThinkCentreM8500t');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M9350z-N000','ThinkCentre M9350z-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M930t Q470 17','ThinkCentre M930t Q470 17');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M930t','ThinkCentre M930t');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M920t','ThinkCentre M920t');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M9000Z','ThinkCentre M9000Z');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M8500t-N085','ThinkCentre M8500t-N085');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M8500t-N000','ThinkCentre M8500t-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M8500t-D124','ThinkCentre M8500t-D124');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M8500T','ThinkCentre M8500T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M8500s-N000','ThinkCentre M8500s-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M8480T','ThinkCentre M8480T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M8400t-N060','ThinkCentre M8400t-N060');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M8400t-N000','ThinkCentre M8400t-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M8400t-D009','ThinkCentre M8400t-D009');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M8400T','ThinkCentre M8400T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M8380T','ThinkCentre M8380T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M8300T','ThinkCentre M8300T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M8200T','ThinkCentre M8200T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M8000T','ThinkCentre M8000T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M7200z-N000','ThinkCentre M7200z-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M6500t-D204','ThinkCentre M6500t-D204');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M6500t-D203','ThinkCentre M6500t-D203');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M6480T','ThinkCentre M6480T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M6300T','ThinkCentre M6300T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M6299T','ThinkCentre M6299T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M6290T','ThinkCentre M6290T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M6280T','ThinkCentre M6280T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M6250T','ThinkCentre M6250T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M6200T','ThinkCentre M6200T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M6100T','ThinkCentre M6100T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M4390T','ThinkCentre M4390T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M4350t-N070','ThinkCentre M4350t-N070');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M4350t-N000','ThinkCentre M4350t-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M4350T','ThinkCentre M4350T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M4350q-N000','ThinkCentre M4350q-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M4300T','ThinkCentre M4300T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M4000T','ThinkCentre M4000T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','Thincentre','m8500s','Thincentre m8500s');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('宏基','Shangqi','X4270','Shangqi X4270');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('宏基','Shangqi','N320','Shangqi N320');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','M7370','QiTianM7370');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','M7360','QiTianM7360');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','M7350','QiTianM7350');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','M730E','QiTianM730E');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','M7300','QiTianM7300');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','M729E','QiTianM729E');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','M7180','QiTianM7180');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','M7170','QiTianM7170');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','M7160','QiTianM7160');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','M715E','QiTianM715E');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','M7150','QiTianM7150');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','M7100','QiTianM7100');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QITIAN','M6960','QITIANM6960');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','M6900','QiTianM6900');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','M6500-N000','QiTianM6500-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','M6400-N000','QiTianM6400-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','M5900-N000','QiTianM5900-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','M5660','QiTianM5660');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','M510-N000','QiTianM510-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','M4550-N000','QiTianM4550-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','M4500-N000','QiTianM4500-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','M4500-D158','QiTianM4500-D158');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QITIAN','M4500','QITIANM4500');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','M4380','QiTianM4380');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','M4362','QiTianM4362');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','M4360-N000','QiTianM4360-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','M4360-D007','QiTianM4360-D007');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','M4360','QiTianM4360');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','M435E','QiTianM435E');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','M4350-N002','QiTianM4350-N002');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','M4350-N000','QiTianM4350-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QITIAN','M4350N000','QITIANM4350N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','M4350','QiTianM4350');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','M4340','QiTianM4340');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','M4330','QiTianM4330');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','M4320','QiTianM4320');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','M4300','QiTianM4300');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','M Q45','QiTianM Q45');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QITIAN','M','QITIANM');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','B4550-N001','QiTianB4550-N001');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','B4550-B420','QiTianB4550-B420');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','B4550-B323','QiTianB4550-B323');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','B4550-B321','QiTianB4550-B321');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','B4360-N001','QiTianB4360-N001');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','B4360-B063','QiTianB4360-B063');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','B4360-B015','QiTianB4360-B015');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QITIAN','B4360-B003','QITIANB4360-B003');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','QiTian','A7200-N000','QiTianA7200-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Precision','WorkStation T5500','Precision WorkStation T5500  ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Precision','WorkStation 380','Precision WorkStation 380    ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Precision','Tower 7810','Precision Tower 7810');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Precision','T1600','Precision T1600');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','Pavilion','p6-1295cn','p6-1295cn');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','Tower Plus 7010','OptiPlex Tower Plus 7010');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','Tower 7010','OptiPlex Tower 7010');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','990','OptiPlex 990');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','980','OptiPlex 980                 ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','9020','OptiPlex 9020');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','9010','OptiPlex 9010');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','790','OptiPlex 790');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','780','OptiPlex 780                 ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','755','OptiPlex 755                 ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','7080','OptiPlex 7080');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','7060','OptiPlex 7060');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','7050','OptiPlex 7050');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','7040','OptiPlex 7040');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','7020','OptiPlex 7020');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','7010','OptiPlex 7010');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','7000','OptiPlex 7000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','5080','OptiPlex 5080');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','5070','OptiPlex 5070');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','5050','OptiPlex 5050');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','5040','OptiPlex 5040');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','5000','OptiPlex 5000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','390','OptiPlex 390');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','380','OptiPlex 380                 ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','360','OptiPlex 360                 ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','3090','OptiPlex 3090');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','3080','OptiPlex 3080');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','3060','OptiPlex 3060');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','3050','OptiPlex 3050');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','3046','OptiPlex 3046');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','3020','OptiPlex 3020');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','OptiPlex','3010','OptiPlex 3010');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('NARI','NPCH201','NPCH201','NPCH201');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('浪潮','NP5570M4','NP5570M4','NP5570M4');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('浪潮','NP5540M3','NP5540M3','NP5540M3');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('浪潮','NP3020M2','NP3020M2','NP3020M2');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('NARI','NPCH201','NPCH201','NARI NPCH201');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('NARI','NPCH201','NPCH201','NARI NPCH201');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('NARI','nDesktop','HG201','NARI nDesktop HG201');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('NARI','nDesktop','HG201','NARI nDesktop HG201');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','MX8900','MX8900','MX8900');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('MSI','MS-7850','MS-7850','MSI MS-7850');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('MSI','MS-HDZ','H81M-V3H M.2','MS-HDZ H81M-V3H M.2');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('MSI','MS-Challenger','H610M-D','MS-Challenger H610M-D');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('MSI','MS-7E06','MS-7E06','MS-7E06');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('MSI','MS-7E05','MS-7E05','MS-7E05');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('MSI','MS-7D48','MS-7D48','MS-7D48');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('MSI','MS-7D45','MS-7D45','MS-7D45');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('MSI','MS-7D42','MS-7D42','MS-7D42');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('MSI','MS-7D22','MS-7D22','MS-7D22');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('MSI','MS-7C88','MS-7C88','MS-7C88');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('MSI','MS-7C31','MS-7C31','MS-7C31');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('MSI','MS-7C10','MS-7C10','MS-7C10');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('MSI','MS-7B23','MS-7B23','MS-7B23');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('MSI','MS-7A74','MS-7A74','MS-7A74');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('MSI','MS-7996','MS-7996','MS-7996');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('MSI','MS-7817','MS-7817','MS-7817');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('MSI','MS-7721','MS-7721','MS-7721');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('MSI','MS-7E05','MS-7E05','Micro-Star International Co., Ltd. MS-7E05');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('MSI','MS-7D82','MS-7D82','Micro-Star International Co., Ltd. MS-7D82');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('MSI','MS-7C96','MS-7C96','Micro-Star International Co., Ltd. MS-7C96');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('MSI','MS-7B97','MS-7B97','Micro-Star International Co., Ltd. MS-7B97');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M930T-N000','M930T-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M910t-N000','M910t-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M910T M910T','M910T M910T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M910T','M910T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M901t','M901t');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M8600T','M8600T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M8500T-N000','M8500T-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M8500t','M8500t');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M8400T','M8400T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','M8300T','M8300T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','M760A2221050','M760A2221050','M760A2221050');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','M760A031004X','M760A031004X','M760A031004X');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','扬天','M4662n','M4662n');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','M4650','M4650');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','ThinkCentre','m4350t','m4350t');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','启天','M415','M415');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','YangTian','W4060V','LENOVO YangTianW4060V');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','YangTian','T4950D','LENOVO YangTianT4950D');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','YangTian','T4900v-00','LENOVO YangTianT4900v-00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','YangTian','T4900D','LENOVO YangTianT4900D');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','YANGTIAN','S500','LENOVO YANGTIANS500');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','YangTian','M6000n-00','LENOVO YangTianM6000n-00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','YangTian','M4630n-22','LENOVO YangTianM4630n-22');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','YangTian','M4600D','LENOVO YangTianM4600D');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','YangTian','M4200r-00','LENOVO YangTianM4200r-00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','YangTian','M2613D','LENOVO YangTianM2613D');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','YangTian','M2612D','LENOVO YangTianM2612D');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','YangTian','A4600R','LENOVO YangTianA4600R');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','XINYUANMENG','F2997','LENOVO XINYUANMENGF2997');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','ThinkCentre','M910t','LENOVO ThinkCentreM910t');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','THINKCENTRE','M920T','LENOVO THINKCENTRE M920T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','ThinkCentre','M8500t-N000','LENOVO ThinkCentre M8500t-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','ThinkCentre','M8500t-D428','LENOVO ThinkCentre M8500t-D428');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','ThinkCentre','M8500t-D224','LENOVO ThinkCentre M8500t-D224');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','ThinkCentre','M8500t','LENOVO ThinkCentre M8500t');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','ThinkCentre','M8500s-N000','LENOVO ThinkCentre M8500s-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','ThinkCentre','M8480T','LENOVO ThinkCentre M8480T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','ThinkCentre','M8450T','LENOVO ThinkCentre M8450T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','ThinkCentre','M8400T-N000','LENOVO ThinkCentre M8400T-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','ThinkCentre','M8400T','LENOVO ThinkCentre M8400T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','ThinkCentre','M8300T','LENOVO ThinkCentre M8300T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','ThinkCentre','M6300T','LENOVO ThinkCentre M6300T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','ThinkCentre','M6106T','LENOVO ThinkCentre M6106T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','ThinkCentre','M6100T','LENOVO ThinkCentre M6100T');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','ThinkCentre','M4350S','LENOVO ThinkCentre M4350S');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','TC-M1U','TC-M1U','LENOVO TC-M1U__');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','SA68011226','SA68011226','LENOVO SA68011226');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','QiTian','M7330','LENOVO QiTianM7330');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','QiTian','M730E','LENOVO QiTianM730E');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','QiTian','M7150','LENOVO QiTianM7150');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','QiTian','M6900','LENOVO QiTianM6900');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','QiTian','M6400-N000','LENOVO QiTianM6400-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','QiTian','M4550-N000','LENOVO QiTianM4550-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','QiTian','M4390','LENOVO QiTianM4390');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','QITIAN','M4360-N000','LENOVO QITIANM4360-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','QiTian','M4360','LENOVO QiTianM4360');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','QiTian','M4350-N099','LENOVO QiTianM4350-N099');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','QiTian','M4350-N000','LENOVO QiTianM4350-N000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','QiTian','M4350-B102','LENOVO QiTianM4350-B102');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','QiTian','M4350','LENOVO QiTianM4350');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','QiTian','M4330','LENOVO QiTianM4330');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','QiTian','M4300','LENOVO QiTianM4300');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','QiTian','B4360-B102','LENOVO QiTianB4360-B102');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','QiTian','B4360-B015','LENOVO QiTianB4360-B015');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','ThinkCentre','M910t','LENOVO M910t');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','ThinkCentre','M8500s','LENOVO M8500s');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','ThinkCentre','M8480t','LENOVO M8480t');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','IdeaCentre','K320','LENOVO IdeaCentre K320');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','FFFFFFFFFF','FFFFFFFFFF','LENOVO FFFFFFFFFF');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','F0GH00VCCD','F0GH00VCCD','LENOVO F0GH00VCCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','F0CU001JCP','F0CU001JCP','LENOVO F0CU001JCP');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90WEA01KCD','90WEA01KCD','LENOVO 90WEA01KCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90WE0007CD','90WE0007CD','LENOVO 90WE0007CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90WE0006CD','90WE0006CD','LENOVO 90WE0006CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90WD0006CD','90WD0006CD','LENOVO 90WD0006CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90W20047CP','90W20047CP','LENOVO 90W20047CP');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90VWCTO1WW','90VWCTO1WW','LENOVO 90VWCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90VWA00GCD','90VWA00GCD','LENOVO 90VWA00GCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90VT004JCD','90VT004JCD','LENOVO 90VT004JCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90U30004CD','90U30004CD','LENOVO 90U30004CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90TDCTO1WW','90TDCTO1WW','LENOVO 90TDCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90TC0002CD','90TC0002CD','LENOVO 90TC0002CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90TACTO1WW','90TACTO1WW','LENOVO 90TACTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90TAA07GCD','90TAA07GCD','LENOVO 90TAA07GCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90TAA06MCD','90TAA06MCD','LENOVO 90TAA06MCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90TAA06D00','90TAA06D00','LENOVO 90TAA06D00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90T1002YCP','90T1002YCP','LENOVO 90T1002YCP');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90SM00BMCD','90SM00BMCD','LENOVO 90SM00BMCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90SM0005CD','90SM0005CD','LENOVO 90SM0005CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90SM0000CD','90SM0000CD','LENOVO 90SM0000CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90SL0007CD','90SL0007CD','LENOVO 90SL0007CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90SHCTO1WW','90SHCTO1WW','LENOVO 90SHCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90SFCTO1WW','90SFCTO1WW','LENOVO 90SFCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90SFA05SCD','90SFA05SCD','LENOVO 90SFA05SCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90SFA020CD','90SFA020CD','LENOVO 90SFA020CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90SEA013CD','90SEA013CD','LENOVO 90SEA013CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90SDA02FCD','90SDA02FCD','LENOVO 90SDA02FCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90SB001JCD','90SB001JCD','LENOVO 90SB001JCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90SB0017CD','90SB0017CD','LENOVO 90SB0017CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90SB0013CD','90SB0013CD','LENOVO 90SB0013CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90SB000KCD','90SB000KCD','LENOVO 90SB000KCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90SB000HCD','90SB000HCD','LENOVO 90SB000HCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90SB0009CD','90SB0009CD','LENOVO 90SB0009CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90SB0007CD','90SB0007CD','LENOVO 90SB0007CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90RM0022CD','90RM0022CD','LENOVO 90RM0022CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90QKA03CCD','90QKA03CCD','LENOVO 90QKA03CCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90NYCTO1WW','90NYCTO1WW','LENOVO 90NYCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90NB0031CD','90NB0031CD','LENOVO 90NB0031CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90NB001SCD','90NB001SCD','LENOVO 90NB001SCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90MCCTO1WW','90MCCTO1WW','LENOVO 90MCCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90MB0021CD','90MB0021CD','LENOVO 90MB0021CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90M2CTO1WW','90M2CTO1WW','LENOVO 90M2CTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90M2CT01WW','90M2CT01WW','LENOVO 90M2CT01WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90M2A13CCD','90M2A13CCD','LENOVO 90M2A13CCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90M2A02QCD','90M2A02QCD','LENOVO 90M2A02QCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90LX000FCD','90LX000FCD','LENOVO 90LX000FCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90LU0023CD','90LU0023CD','LENOVO 90LU0023CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90LG000PCD','90LG000PCD','LENOVO 90LG000PCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90KQS0G200','90KQS0G200','LENOVO 90KQS0G200');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90KQCTO1WW','90KQCTO1WW','LENOVO 90KQCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90K7CTO1WW','90K7CTO1WW','LENOVO 90K7CTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90J8CTO1WW','90J8CTO1WW','LENOVO 90J8CTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90J3001MCD','90J3001MCD','LENOVO 90J3001MCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90J3001BCD','90J3001BCD','LENOVO 90J3001BCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90J30008CD','90J30008CD','LENOVO 90J30008CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90HV0018CD','90HV0018CD','LENOVO 90HV0018CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90HKCTO1WW','90HKCTO1WW','LENOVO 90HKCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90HJCTO1WW','90HJCTO1WW','LENOVO 90HJCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90HJA0Q1CD','90HJA0Q1CD','LENOVO 90HJA0Q1CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90HJA0JSCD','90HJA0JSCD','LENOVO 90HJA0JSCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90H0001WCD','90H0001WCD','LENOVO 90H0001WCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90GQZ3ZMCN','90GQZ3ZMCN','LENOVO 90GQZ3ZMCN');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90GQCTO1WW','90GQCTO1WW','LENOVO 90GQCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90GKCTO1WW','90GKCTO1WW','LENOVO 90GKCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90GH0016CD','90GH0016CD','LENOVO 90GH0016CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90GG001VCD','90GG001VCD','LENOVO 90GG001VCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90GG001PCD','90GG001PCD','LENOVO 90GG001PCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90GG001JCD','90GG001JCD','LENOVO 90GG001JCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90GG001BCD','90GG001BCD','LENOVO 90GG001BCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90GF0004CD','90GF0004CD','LENOVO 90GF0004CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90G2A00YCD','90G2A00YCD','LENOVO 90G2A00YCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90G1CTO1WW','90G1CTO1WW','LENOVO 90G1CTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90G1A0MFCD','90G1A0MFCD','LENOVO 90G1A0MFCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90G0CTO1WW','90G0CTO1WW','LENOVO 90G0CTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90G0A0TPCD','90G0A0TPCD','LENOVO 90G0A0TPCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90FJ0007CD','90FJ0007CD','LENOVO 90FJ0007CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90F50008CN','90F50008CN','LENOVO 90F50008CN');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90F3000CCN','90F3000CCN','LENOVO 90F3000CCN');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90ETCTO1WW','90ETCTO1WW','LENOVO 90ETCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90EACTO1WW','90EACTO1WW','LENOVO 90EACTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90E90013CD','90E90013CD','LENOVO 90E90013CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90E8CTO1WW','90E8CTO1WW','LENOVO 90E8CTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90E80038CD','90E80038CD','LENOVO 90E80038CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90E80036CD','90E80036CD','LENOVO 90E80036CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90E80035CD','90E80035CD','LENOVO 90E80035CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90E80032CD','90E80032CD','LENOVO 90E80032CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90E8002LCD','90E8002LCD','LENOVO 90E8002LCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90E80023CD','90E80023CD','LENOVO 90E80023CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90E8000MCD','90E8000MCD','LENOVO 90E8000MCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90E50023CD','90E50023CD','LENOVO 90E50023CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90DWCTO1WW','90DWCTO1WW','LENOVO 90DWCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90DWA05MCD','90DWA05MCD','LENOVO 90DWA05MCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90DSA0GECD','90DSA0GECD','LENOVO 90DSA0GECD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90DCCTO1WW','90DCCTO1WW','LENOVO 90DCCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90D7A02000','90D7A02000','LENOVO 90D7A02000');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90D7A00V00','90D7A00V00','LENOVO 90D7A00V00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90D4A027CN','90D4A027CN','LENOVO 90D4A027CN');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90CYCTO1WW','90CYCTO1WW','LENOVO 90CYCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90CWCTO1WW','90CWCTO1WW','LENOVO 90CWCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90C10023CD','90C10023CD','LENOVO 90C10023CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90BYA01C00','90BYA01C00','LENOVO 90BYA01C00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','90B70073CD','90B70073CD','LENOVO 90B70073CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','82XF','82XF','LENOVO 82XF');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','82Q0','82Q0','LENOVO 82Q0');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','82GM','82GM','LENOVO 82GM');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','81QM','81QM','LENOVO 81QM');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','3690AN1','3690AN1','LENOVO 3690AN1');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','3598A7U','3598A7U','LENOVO 3598A7U');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','30E4A51NCW','30E4A51NCW','LENOVO 30E4A51NCW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','30E4A3VSCW','30E4A3VSCW','LENOVO 30E4A3VSCW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','30DJS9L300','30DJS9L300','LENOVO 30DJS9L300');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','30DJA3F4CW','30DJA3F4CW','LENOVO 30DJA3F4CW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','30D0A0GFCW','30D0A0GFCW','LENOVO 30D0A0GFCW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','30B3000UCW','30B3000UCW','LENOVO 30B3000UCW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','30B2S00G00','30B2S00G00','LENOVO 30B2S00G00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','30A3S00200','30A3S00200','LENOVO 30A3S00200');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','25017928','25017928','LENOVO 25017928');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','20S1S8VE00','20S1S8VE00','LENOVO 20S1S8VE00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','12J8CTO1WW','12J8CTO1WW','LENOVO 12J8CTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','12GJA01YCD','12GJA01YCD','LENOVO 12GJA01YCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','12A4A0CXCD','12A4A0CXCD','LENOVO 12A4A0CXCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','12A40001CD','12A40001CD','LENOVO 12A40001CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','11VRCTO1WW','11VRCTO1WW','LENOVO 11VRCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','11SKS04M00','11SKS04M00','LENOVO 11SKS04M00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','11SKS02600','11SKS02600','LENOVO 11SKS02600');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','11SKS00N00','11SKS00N00','LENOVO 11SKS00N00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','11SKS00M00','11SKS00M00','LENOVO 11SKS00M00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','11SKCTO1WW','11SKCTO1WW','LENOVO 11SKCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','11SGA0H3CD','11SGA0H3CD','LENOVO 11SGA0H3CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','11SBA0UCD','11SBA0UCD','LENOVO 11SBA0UCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','11SBA0UACD','11SBA0UACD','LENOVO 11SBA0UACD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','11SBA00BCD','11SBA00BCD','LENOVO 11SBA00BCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','11DXCTO1WW','11DXCTO1WW','LENOVO 11DXCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','11DFCTO1WW','11DFCTO1WW','LENOVO 11DFCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','11CWCTO1WW','11CWCTO1WW','LENOVO 11CWCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','11CWCT01WW','11CWCT01WW','LENOVO 11CWCT01WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','11CMCTO1WW','11CMCTO1WW','LENOVO 11CMCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','11BRA20XCD','11BRA20XCD','LENOVO 11BRA20XCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','11BRA001CD','11BRA001CD','LENOVO 11BRA001CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','11BAA22HCD','11BAA22HCD','LENOVO 11BAA22HCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','10TDCTO1WW','10TDCTO1WW','LENOVO 10TDCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','10SWCTO1WW','10SWCTO1WW','LENOVO 10SWCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','10SWA03FCD','10SWA03FCD','LENOVO 10SWA03FCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','10SMS12K00','10SMS12K00','LENOVO 10SMS12K00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','10SMS02400','10SMS02400','LENOVO 10SMS02400');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','10SMCTO1WW','10SMCTO1WW','LENOVO 10SMCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','10SMA0BXCD','10SMA0BXCD','LENOVO 10SMA0BXCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','10SMA0BGCD','10SMA0BGCD','LENOVO 10SMA0BGCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','10SMA0A6CD','10SMA0A6CD','LENOVO 10SMA0A6CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','10SMA033CD','10SMA033CD','LENOVO 10SMA033CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','10RJA004CD','10RJA004CD','LENOVO 10RJA004CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','10QEA000CD','10QEA000CD','LENOVO 10QEA000CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','10QE0007CD','10QE0007CD','LENOVO 10QE0007CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','10Q2CTO1WW','10Q2CTO1WW','LENOVO 10Q2CTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','10NVCTO1WW','10NVCTO1WW','LENOVO 10NVCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','10NBCTO1WW','10NBCTO1WW','LENOVO 10NBCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','10N9CTO1WW','10N9CTO1WW','LENOVO 10N9CTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','10N9A00HCD','10N9A00HCD','LENOVO 10N9A00HCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','10KS000MCD','10KS000MCD','LENOVO 10KS000MCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','10KS000KCD','10KS000KCD','LENOVO 10KS000KCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','10KS000CCD','10KS000CCD','LENOVO 10KS000CCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','10G6CTO1WW','10G6CTO1WW','LENOVO 10G6CTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','10G4CTO1WW','10G4CTO1WW','LENOVO 10G4CTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','10G4A01X00','10G4A01X00','LENOVO 10G4A01X00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','10C0A034CD','10C0A034CD','LENOVO 10C0A034CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','10C0A02SCD','10C0A02SCD','LENOVO 10C0A02SCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','10C0A02RCD','10C0A02RCD','LENOVO 10C0A02RCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','10C0A021CD','10C0A021CD','LENOVO 10C0A021CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','10C0A01VCD','10C0A01VCD','LENOVO 10C0A01VCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','10C0A009CD','10C0A009CD','LENOVO 10C0A009CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','10C000F1CD','10C000F1CD','LENOVO 10C000F1CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','ZHAOYANG','K27','LENOVO                           ZHAOYANG K27    ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('LENOVO','Lecoo','Lecoo','Lecoo  ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('华硕','K401UQ','K401UQ','K401UQ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','JiaYue','E152Z','JiaYueE152Z');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','JIAYUEE','JIAYUEE','JIAYUEE');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Inspur','NF5280M6','NF5280M6','Inspur NF5280M6');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Inspiron','620s','Inspiron 620s');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Inspiron','580s','Inspiron 580s');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Inspiron','560','Inspiron 560  ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Inspiron','3910','Inspiron 3910');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Inspiron','3880','Inspiron 3880');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Inspiron','3847','Inspiron 3847');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Inspiron','3668','Inspiron 3668');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Inspiron','3650','Inspiron 3650');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','Inspiron','3647','Inspiron 3647');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','IdeaCentre','K330','IdeaCentre K330');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HUAWEI','YTG-XXX','YTG-XXX','HUAWEI YTG-XXX');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HUAWEI','PUC-WXXXX','PUC-WXXXX','HUAWEI PUC-WXXXX');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HUANANZHI','A_M_I_','A_M_I_','HUANANZHI A_M_I_');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Workstation','Zhan 86 Pro G1 MT','HP Zhan 86 Pro G1 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Z840','Z840 Workstation','HP Z840 Workstation');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Z8','Z8 G4 Workstation','HP Z8 G4 Workstation');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Z620','Z620 Workstation','HP Z620 Workstation');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Z600','Z600 Workstation','HP Z600 Workstation');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Z440','Z440 Workstation','HP Z440 Workstation');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Z420','Z420 Workstation','HP Z420 Workstation');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Z400','Z400 Workstation','HP Z400 Workstation');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Z240','Z240 Tower Workstation','HP Z240 Tower Workstation');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Z240','Z240 SFF Workstation','HP Z240 SFF Workstation');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Z230','Z230 SFF Workstation','HP Z230 SFF Workstation');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Z210','Z210 Workstation','HP Z210 Workstation');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Z2','Z2 Tower G9 Workstation Desktop PC','HP Z2 Tower G9 Workstation Desktop PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Z2','Z2 Tower G4 Workstation','HP Z2 Tower G4 Workstation');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Z2','Z2 Tower G4 Workstation','HP Z2 Tower G4 Workstation');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','TPE01-3000i','HP TPE01-3000i');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','ProLiant','DL388p Gen8','HP ProLiant DL388p Gen8');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','ProDesk','680 G4 MT','HP ProDesk 680 G4 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','ProDesk','680 G3 PCI MT','HP ProDesk 680 G3 PCI MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','ProDesk','680 G2 MT','HP ProDesk 680 G2 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','ProDesk','680 G2 MT','HP ProDesk 680 G2 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','ProDesk','600 G3 SFF','HP ProDesk 600 G3 SFF');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','ProDesk','600 G3 SFF Low Profile','HP ProDesk 600 G3 SFF Low Profile');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','ProDesk','600 G3 SFF','HP ProDesk 600 G3 SFF');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','ProDesk','600 G3 PCI MT','HP ProDesk 600 G3 PCI MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','ProDesk','600 G2','HP ProDesk 600 G2');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','ProDesk','600 G2 SFF Low Profile','HP ProDesk 600 G2 SFF Low Profile');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','ProDesk','600 G2 MT','HP ProDesk 600 G2 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','ProDesk','600 G1 SFF','HP ProDesk 600 G1 SFF');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','ProDesk','490 G3 MT Business PC','HP ProDesk 490 G3 MT Business PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','ProDesk','480 G7 PCI Microtower PC','HP ProDesk 480 G7 PCI Microtower PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','ProDesk','480 G6 MT','HP ProDesk 480 G6 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','ProDesk','480 G4 PCI MT','HP ProDesk 480 G4 PCI MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','ProDesk','400 G4 SFF','HP ProDesk 400 G4 SFF');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','ProDesk','400 G4 SFF Low Profile','HP ProDesk 400 G4 SFF Low Profile');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','ProDesk','400 G4 MT','HP ProDesk 400 G4 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Pro','Tower 288 G9 PCI Desktop PC','HP Pro Tower 288 G9 PCI Desktop PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Pro','3380 MT','HP Pro 3380 MT ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Pro','3330 MT','HP Pro 3330 MT ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Pro','3005 Microtower PC','HP Pro 3005 Microtower PC ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Pro','3000 Microtower PC','HP Pro 3000 Microtower PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Pro','2000/2080','HP Pro 2000/2080');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Pavilion','Desktop PC 570-p0xx','HP Pavilion Desktop PC 570-p0xx');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','N01-F250rcn','HP N01-F250rcn	HP N01-F250rcn','HP N01-F250rcn');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','ZHAN 99 Pro G2 Microtower PC','HP HP ZHAN 99 Pro G2 Microtower PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','ZHAN 99 Pro G1 MT','HP HP ZHAN 99 Pro G1 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','Z8G4','HP HP Z8G4');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','Z8 G4 Workstation','HP HP Z8 G4 Workstation');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','Z8 G4 RCTO BASE MODEL WORKSTATION','HP HP Z8 G4 RCTO BASE MODEL WORKSTATION');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','Z2 Tower G9 Workstation Desktop PC','HP HP Z2 Tower G9 Workstation Desktop PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','Z2 Tower G4 Workstation','HP HP Z2 Tower G4 Workstation');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','ProDesk 680 G6 PCI Microtower PC','HP HP ProDesk 680 G6 PCI Microtower PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','ProDesk 680 G3 PCI MT','HP HP ProDesk 680 G3 PCI MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','ProDesk 680 G2 MT','HP HP ProDesk 680 G2 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','ProDesk 600 G4 SFF','HP HP ProDesk 600 G4 SFF');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','ProDesk 600 G3 SFF','HP HP ProDesk 600 G3 SFF');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','ProDesk 600 G3 PCI MT','HP HP ProDesk 600 G3 PCI MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','ProDesk 600 G2 MT','HP HP ProDesk 600 G2 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','ProDesk 480 G7 PCI Microtower PC','HP HP ProDesk 480 G7 PCI Microtower PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','ProDesk 480 G6 MT','HP HP ProDesk 480 G6 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','ProDesk 480 G4 PCI MT','HP HP ProDesk 480 G4 PCI MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','ProDesk 400 G4 SFF','HP HP ProDesk 400 G4 SFF ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','ProDesk 400 G3 SFF','HP HP ProDesk 400 G3 SFF');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','Pro Tower 288 G9 PCI Desktop PC','HP HP Pro Tower 288 G9 PCI Desktop PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','Pro Tower 280 G9 PCI Desktop PC','HP HP Pro Tower 280 G9 PCI Desktop PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','ENVY TE01-3xxx','HP HP ENVY TE01-3xxx');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','EliteSesk 800 G3 TWR','HP HP EliteSesk 800 G3 TWR');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','EliteDesk 880 G6 Tower PC','HP HP EliteDesk 880 G6 Tower PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','EliteDesk 880 G5 TWR','HP HP EliteDesk 880 G5 TWR');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','EliteDesk 880 G3 TWR','HP HP EliteDesk 880 G3 TWR');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','EliteDesk 880 G2 TWR','HP HP EliteDesk 880 G2 TWR');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','EliteDesk 800 G6 Tower PC','HP HP EliteDesk 800 G6 Tower PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','EliteDesk 800 G5 TWR','HP HP EliteDesk 800 G5 TWR');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','EliteDesk 800 G4 WKS TWR','HP HP EliteDesk 800 G4 WKS TWR');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','EliteDesk 800 G4 TWR','HP HP EliteDesk 800 G4 TWR');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','EliteDesk 800 G3 TWR','HP HP EliteDesk 800 G3 TWR');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','EliteDesk 800 G3 SFF','HP HP EliteDesk 800 G3 SFF');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','EliteDesk 800 G2 TWR','HP HP EliteDesk 800 G2 TWR');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','EliteDesk 800 G2 SFF','HP HP EliteDesk 800 G2 SFF');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','EliteDesk 800 G2 DM 65W','HP HP EliteDesk 800 G2 DM 65W');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','EliteDesk 705 G4 SFF','HP HP EliteDesk 705 G4 SFF');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','EliteDesk 705 G3 SFF','HP HP EliteDesk 705 G3 SFF');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','Elite Tower 880 G9 Desktop PC','HP HP Elite Tower 880 G9 Desktop PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','Elite Tower 680 G9 Desktop PC','HP HP Elite Tower 680 G9 Desktop PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','Desktop Pro PCI MT','HP HP Desktop Pro PCI MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','Desktop M01-F2xxx','HP HP Desktop M01-F2xxx');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','880 G6','HP HP 880 G6');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','hp','800 g3','HP hp 800 g3');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','705 G3 Small Form Factor PC','HP HP 705 G3 Small Form Factor PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','600 G3','HP HP 600 G3');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','288 Pro G8 Microtower PC','HP HP 288 Pro G8 Microtower PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','288 Pro G6 Microtower PC','HP HP 288 Pro G6 Microtower PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','288 Pro G5 MT Business PC','HP HP 288 Pro G5 MT Business PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','288 Pro G4 MT Business PC','HP HP 288 Pro G4 MT Business PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','288 Pro G3 MT','HP HP 288 Pro G3 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','288 Pro G2 MT','HP HP 288 Pro G2 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','282 Pro G3 MT','HP HP 282 Pro G3 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','280 Pro G3 MT','HP HP 280 Pro G3 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','280 Pro G2 MT (Legacy)','HP HP 280 Pro G2 MT (Legacy)');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','280 Pro G2 MT','HP HP 280 Pro G2 MT ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','280 Pro G2 MT','HP HP 280 Pro G2 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','280 G1','HP HP 280 G1');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','218 Pro G5 MT','HP HP 218 Pro G5 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','EliteDesk','705 G4','HP EliteDesk705 G4');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','ELITEDESK','880 G6','HP ELITEDESK 880 G6');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','EliteDesk','880 G6 Tower PC','HP EliteDesk 880 G6 Tower PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','EliteDesk','880 G6 Tower PC','HP EliteDesk 880 G6 Tower PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','EliteDesk','880 G5 TWR','HP EliteDesk 880 G5 TWR');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','EliteDesk','880 G5 TWR','HP EliteDesk 880 G5 TWR');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','EliteDesk','880 G3 TWR','HP EliteDesk 880 G3 TWR');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','EliteDesk','880 G3 TWR','HP EliteDesk 880 G3 TWR');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','EliteDesk','880 G2 TWR','HP EliteDesk 880 G2 TWR');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','EliteDesk','880 G2 TWR','HP EliteDesk 880 G2 TWR');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','EliteDesk','880 G1 TWR','HP EliteDesk 880 G1 TWR');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','EliteDesk','800 G5 TWR','HP EliteDesk 800 G5 TWR');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','EliteDesk','800 G5 TWR','HP EliteDesk 800 G5 TWR');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','EliteDesk','800 G4 WKS TWR','HP EliteDesk 800 G4 WKS TWR');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','EliteDesk','800 G4 TWR','HP EliteDesk 800 G4 TWR');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','EliteDesk','800 G4 TWR','HP EliteDesk 800 G4 TWR');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','EliteDesk','800 G3 TWR','HP EliteDesk 800 G3 TWR');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','EliteDesk','800 G3 TWR','HP EliteDesk 800 G3 TWR');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','EliteDesk','800 G3 SFF','HP EliteDesk 800 G3 SFF');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','EliteDesk','800 G3 SFF Low Profile Desktop','HP EliteDesk 800 G3 SFF Low Profile Desktop');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','EliteDesk','800 G2 TWR','HP EliteDesk 800 G2 TWR');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','EliteDesk','800 G2 SFF Low Profile Desktop','HP EliteDesk 800 G2 SFF Low Profile Desktop');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','EliteDesk','800 G2 SFF','HP EliteDesk 800 G2 SFF');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','EliteDesk','800 G2 DM 65W Space-saving','HP EliteDesk 800 G2 DM 65W Space-saving');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','EliteDesk','800 G1 TWR','HP EliteDesk 800 G1 TWR');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','EliteDesk','800 G1 SFF','HP EliteDesk 800 G1 SFF');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','EliteDesk','705 G4 SFF','HP EliteDesk 705 G4 SFF');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','EliteDesk','705 G4 SFF','HP EliteDesk 705 G4 SFF');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','EliteDesk','705 G4 MT','HP EliteDesk 705 G4 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','EliteDesk','705 G4','HP EliteDesk 705 G4');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','EliteDesk','705 G3 SFF Low Profile Desktop','HP EliteDesk 705 G3 SFF Low Profile Desktop');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','EliteDesk','705 G3 SFF','HP EliteDesk 705 G3 SFF');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Elite','Tower 680 G9 Desktop PC','HP Elite Tower 680 G9 Desktop PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Elite','Tower 680 G9 Desktop PC','HP Elite Tower 680 G9 Desktop PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Compaq','dx2710 MT(NA296PA)','HP dx2710 MT(NA296PA)');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Desktop','Pro PCI MT','HP Desktop Pro PCI MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Desktop','Pro G6 Microtower PC','HP Desktop Pro G6 Microtower PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Desktop','M01-F2xxx','HP Desktop M01-F2xxx');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Compaq','Elite 8300 MT','HP Compaq Elite 8300 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Compaq','dx7510 MT','HP Compaq dx7510 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Compaq','8180 Elite CMT PC','HP Compaq 8180 Elite CMT PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Compaq','8000 Elite SFF PC','HP Compaq 8000 Elite SFF PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','805D','805D','HP 805D____');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','EliteDesk','800 G4','HP 800 G4');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','EliteDesk','800 G3 SFF','HP 800 G3 SFF');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','EliteDesk','800 G3 Low Profile Desktop','HP 800 G3 Low Profile Desktop');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','EliteDesk','800 G2 Low Profile Desktop','HP 800 G2 Low Profile Desktop');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','EliteDesk','600 G3','HP 600 G3');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','HP','290 G3 Small Form Factor PC','HP 290 G3 Small Form Factor PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Pro','288 Pro G8 Microtower PC','HP 288 Pro G8 Microtower PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Pro','288 Pro G6 Microtower PC','HP 288 Pro G6 Microtower PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Pro','288 Pro G6 Microtower PC','HP 288 Pro G6 Microtower PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Pro','288 Pro G5 MT Business PC','HP 288 Pro G5 MT Business PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Pro','288 Pro G4 MT Business PC','HP 288 Pro G4 MT Business PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Pro','288 Pro G3 MT','HP 288 Pro G3 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Pro','288 Pro G3 MT Business PC','HP 288 Pro G3 MT Business PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Pro','288 Pro G2 MT','HP 288 Pro G2 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Pro','285 Pro G8 Microtower PC','HP 285 Pro G8 Microtower PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Pro','285 Pro G2 MT','HP 285 Pro G2 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Pro','282 Pro G4 MT Business PC','HP 282 Pro G4 MT Business PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Pro','282 PRO G4 MICROTOWER PC','HP 282 PRO G4 MICROTOWER PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Pro','282 Pro G3 MT','HP 282 Pro G3 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Pro','280 Pro G4 SFF Business PC','HP 280 Pro G4 SFF Business PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Pro','280 Pro G3 MT','HP 280 Pro G3 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Pro','280 Pro G2 MT','HP 280 Pro G2 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Pro','280 Pro G2 MT (Legacy)','HP 280 Pro G2 MT (Legacy)');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Pro','280 Pro G2 MT (Legacy)','HP 280 Pro G2 MT (Legacy)');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Pro','280 Pro G2 MT','HP 280 Pro G2 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Pro','280 Pro G1 MT','HP 280 Pro G1 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Pro','280 G1 MT','HP 280 G1 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('HP','Pro','218 Pro G5 MT','HP 218 Pro G5 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','SLIC-CPC','Hewlett-Packard SLIC-CPC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','SLIC-BPC','Hewlett-Packard SLIC-BPC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','PPPPP-CCC#MMMMMMMM','Hewlett-Packard PPPPP-CCC#MMMMMMMM');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','p7-1005cx','Hewlett-Packard p7-1005cx');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','p6-1451cx','Hewlett-Packard p6-1451cx');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','p6-1450cx','Hewlett-Packard p6-1450cx');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','p6-1401cx','Hewlett-Packard p6-1401cx');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','p6-1181cx','Hewlett-Packard p6-1181cx');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','HP-G2035cx','Hewlett-Packard HP-G2035cx');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','Z640 Workstation','Hewlett-Packard HP Z640 Workstation');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','Z620 Workstation','Hewlett-Packard HP Z620 Workstation');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','Z600 Workstation','Hewlett-Packard HP Z600 Workstation');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','Z440 Workstation','Hewlett-Packard HP Z440 Workstation');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','Z420 Workstation','Hewlett-Packard HP Z420 Workstation');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','Z400 Workstation','Hewlett-Packard HP Z400 Workstation');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','Z230 Tower Workstation','Hewlett-Packard HP Z230 Tower Workstation');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','Z230 SFF Workstation','Hewlett-Packard HP Z230 SFF Workstation');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','Z210 Workstation','Hewlett-Packard HP Z210 Workstation');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','Z200 Workstation','Hewlett-Packard HP Z200 Workstation');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','xw6600 Workstation','Hewlett-Packard HP xw6600 Workstation');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','ProDesk 680 G1 TWR','Hewlett-Packard HP ProDesk 680 G1 TWR');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','ProDesk 600 G1 SFF','Hewlett-Packard HP ProDesk 600 G1 SFF');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','ProDesk 490 G2 MT','Hewlett-Packard HP ProDesk 490 G2 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','ProDesk 480 G2 MT (TPM)','Hewlett-Packard HP ProDesk 480 G2 MT (TPM)');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','ProDesk 480 G2 MT','Hewlett-Packard HP ProDesk 480 G2 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','ProDesk 400 G2 MT','Hewlett-Packard HP ProDesk 400 G2 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','Pro 3381 MT','Hewlett-Packard HP Pro 3381 MT ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','Pro 3380 MT','Hewlett-Packard HP Pro 3380 MT ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','Pro 3348 MT','Hewlett-Packard HP Pro 3348 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','Pro 3340 MT','Hewlett-Packard HP Pro 3340 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','Pro 3335 MT','Hewlett-Packard HP Pro 3335 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','Pro 3330 MT','Hewlett-Packard HP Pro 3330 MT ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','Pro 3330 MT','Hewlett-Packard HP Pro 3330 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','Pro 3300 Series MT','Hewlett-Packard HP Pro 3300 Series MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','Pro 3005/3085','Hewlett-Packard HP Pro 3005/3085');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','Pro 3000 Microtower PC','Hewlett-Packard HP Pro 3000 Microtower PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','Pro 2000 Microtower PC','Hewlett-Packard HP Pro 2000 Microtower PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','EliteDesk 880 G1 TWR','Hewlett-Packard HP EliteDesk 880 G1 TWR');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','EliteDesk 800 G1 TWR','Hewlett-Packard HP EliteDesk 800 G1 TWR');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','EliteDesk 800 G1 SFF','Hewlett-Packard HP EliteDesk 800 G1 SFF');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','dx2318 MT(KQ862AV)','Hewlett-Packard HP dx2318 MT(KQ862AV)');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','dx2310 MT(VD378PA)','Hewlett-Packard HP dx2310 MT(VD378PA)');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','Compaq','Pro 6305 MT','Hewlett-Packard HP Compaq Pro 6305 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','Compaq','Pro 6300 SFF','Hewlett-Packard HP Compaq Pro 6300 SFF');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','Compaq','Pro 6300 MT','Hewlett-Packard HP Compaq Pro 6300 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','Compaq','Elite 8300 SFF','Hewlett-Packard HP Compaq Elite 8300 SFF');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','Compaq','Elite 8300 MT','Hewlett-Packard HP Compaq Elite 8300 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','Compaq','dx7510 MT','Hewlett-Packard HP Compaq dx7510 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','Compaq','dx2390 Microtower','Hewlett-Packard HP Compaq dx2390 Microtower');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','Compaq','8180 Elite CMT PC','Hewlett-Packard HP Compaq 8180 Elite CMT PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','Compaq','8000 Elite CMT PC','Hewlett-Packard HP Compaq 8000 Elite CMT PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','Compaq','6280 Pro MT PC','Hewlett-Packard HP Compaq 6280 Pro MT PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','Compaq','6200 Pro SFF PC','Hewlett-Packard HP Compaq 6200 Pro SFF PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','Compaq','6200 Pro MT PC','Hewlett-Packard HP Compaq 6200 Pro MT PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','Compaq','6080 Pro MT PC','Hewlett-Packard HP Compaq 6080 Pro MT PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','Compaq','6000 Pro MT PC','Hewlett-Packard HP Compaq 6000 Pro MT PC');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','800 G1','Hewlett-Packard HP 800 G1');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','280 Pro G1 MT','Hewlett-Packard HP 280 Pro G1 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','280 G1 MT','Hewlett-Packard HP 280 G1 MT');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','Compaq','Presario CQ3000 Series','Hewlett-Packard Compaq Presario CQ3000 Series');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','CNG1519W4Y','Hewlett-Packard CNG1519W4Y');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','A6T27PA','Hewlett-Packard A6T27PA');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('惠普','HP','4cv3233528','Hewlett-Packard 4cv3233528');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','G3','G3 3500','G3 3500');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','Vostro','Vostro 3910','Dell Inc. Vostro 3910');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','Vostro','Vostro 3902','Dell Inc. Vostro 3902  ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','Vostro','Vostro 3901','Dell Inc. Vostro 3901');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','Vostro','Vostro 3900','Dell Inc. Vostro 3900  ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','Vostro','Vostro 3890-China HDD Protection','Dell Inc. Vostro 3890-China HDD Protection');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','Vostro','Vostro 3888-China HDD Protection','Dell Inc. Vostro 3888-China HDD Protection');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','Vostro','Vostro 3881','Dell Inc. Vostro 3881');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','Vostro','Vostro 3710','Dell Inc. Vostro 3710');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','Vostro','Vostro 3690','Dell Inc. Vostro 3690');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','Vostro','Vostro 3681','Dell Inc. Vostro 3681');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','Vostro','Vostro 3671-China HDD Protection','Dell Inc. Vostro 3671-China HDD Protection');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','Vostro','Vostro 3671','Dell Inc. Vostro 3671');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','Vostro','Vostro 3669','Dell Inc. Vostro 3669');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','Vostro','Vostro 3668','Dell Inc. Vostro 3668');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','Vostro','Vostro 3660','Dell Inc. Vostro 3660');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','Vostro','Vostro 3653','Dell Inc. Vostro 3653  ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','Vostro','Vostro 3650','Dell Inc. Vostro 3650  ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','Vostro','Vostro 3471','Dell Inc. Vostro 3471');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','Vostro','Vostro 3268','Dell Inc. Vostro 3268');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','Vostro','Vostro 3020 SFF','Dell Inc. Vostro 3020 SFF');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','Vostro','Vostro 270s','Dell Inc. Vostro 270s  ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','Vostro','Vostro 270','Dell Inc. Vostro 270  ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','Vostro','Vostro 260s','Dell Inc. Vostro 260s  ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','Vostro','Vostro 260','Dell Inc. Vostro 260   ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','Vostro','Vostro 220s Series','Dell Inc. Vostro 220s Series');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','Vostro','Vostro 220 Series','Dell Inc. Vostro 220 Series ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','Precision','Precision WorkStation T5500','Dell Inc. Precision WorkStation T5500  ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','Precision','Precision Tower 5810','Dell Inc. Precision Tower 5810');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','Precision','Precision 3660','Dell Inc. Precision 3660');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','PowerEdge','PowerEdge T30','Dell Inc. PowerEdge T30');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','OptiPlex','OptiPlex 990','Dell Inc. OptiPlex 990');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','OptiPlex','OptiPlex 9020','Dell Inc. OptiPlex 9020');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','OptiPlex','OptiPlex 790','Dell Inc. OptiPlex 790');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','OptiPlex','OptiPlex 780','Dell Inc. OptiPlex 780                 ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','OptiPlex','OptiPlex 760','Dell Inc. OptiPlex 760                 ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','OptiPlex','OptiPlex 7060','Dell Inc. OptiPlex 7060');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','OptiPlex','OptiPlex 7050','Dell Inc. OptiPlex 7050');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','OptiPlex','OptiPlex 7040','Dell Inc. OptiPlex 7040');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','OptiPlex','OptiPlex 7020','Dell Inc. OptiPlex 7020');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','OptiPlex','OptiPlex 7010','Dell Inc. OptiPlex 7010');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','OptiPlex','OptiPlex 390','Dell Inc. OptiPlex 390');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','OptiPlex','OptiPlex 380','Dell Inc. OptiPlex 380                 ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','OptiPlex','OptiPlex 360','Dell Inc. OptiPlex 360                 ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','OptiPlex','OptiPlex 3060','Dell Inc. OptiPlex 3060');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','OptiPlex','OptiPlex 3050','Dell Inc. OptiPlex 3050');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','OptiPlex','OptiPlex 3046','Dell Inc. OptiPlex 3046');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','OptiPlex','OptiPlex 3020','Dell Inc. OptiPlex 3020');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','OptiPlex','OptiPlex 3010','Dell Inc. OptiPlex 3010');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','Inspiron','Inspiron One 2330','Dell Inc. Inspiron One 2330');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','Inspiron','Inspiron 580s','Dell Inc. Inspiron 580s');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','Inspiron','Inspiron 560','Dell Inc. Inspiron 560  ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','Inspiron','Inspiron 3910','Dell Inc. Inspiron 3910');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','Inspiron','Inspiron 3020 S','Dell Inc. Inspiron 3020 S');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','Dimension','Dimension 2010','Dell Inc. Dimension 2010');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','ChengMing','ChengMing 3991-China HDD Protection','Dell Inc. ChengMing 3991-China HDD Protection');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','ChengMing','ChengMing 3967','Dell Inc. ChengMing 3967');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','ChengMing','ChengMing 3900-China HDD Protection','Dell Inc. ChengMing 3900-China HDD Protection');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','OptiPlex','OptiPlex GX620','Dell Inc.                 OptiPlex GX620               ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Dell','DM051','DM051','Dell Inc.                 Dell DM051                   ');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','ChengMing','ChengMing 3991-China HDD Protection','ChengMing 3991-China HDD Protection');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','ChengMing','ChengMing 3991','ChengMing 3991');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','ChengMing','ChengMing 3990-China HDD Protection','ChengMing 3990-China HDD Protection');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','ChengMing','ChengMing 3977','ChengMing 3977');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','ChengMing','ChengMing 3967','ChengMing 3967');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('戴尔','ChengMing','ChengMing 3901','ChengMing 3901');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('ASUS','All','Series','ASUS All Series');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Acer','Aspire','E5-572G','Acer Aspire E5-572G');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('Acer','Veriton','D730','Acer                   Veriton D730');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90WE0001CD','90WE0001CD','90WE0001CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90WD0006CD','90WD0006CD','90WD0006CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90VWCTO1WW','90VWCTO1WW','90VWCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90VWA01PCD','90VWA01PCD','90VWA01PCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90VWA01NCD','90VWA01NCD','90VWA01NCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90VWA00QCD','90VWA00QCD','90VWA00QCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90VWA00PCD','90VWA00PCD','90VWA00PCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90VVCTO1WW','90VVCTO1WW','90VVCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90VU0011CD','90VU0011CD','90VU0011CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90VU000SCD','90VU000SCD','90VU000SCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90VU000MCD','90VU000MCD','90VU000MCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90VU000KCD','90VU000KCD','90VU000KCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90VU0002CD','90VU0002CD','90VU0002CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90VT0054CD','90VT0054CD','90VT0054CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90VT0029CD','90VT0029CD','90VT0029CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90VK004BCD','90VK004BCD','90VK004BCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90VK0033CD','90VK0033CD','90VK0033CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90TDCTO1WW','90TDCTO1WW','90TDCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90TC0016CD','90TC0016CD','90TC0016CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90TBCTO1WW','90TBCTO1WW','90TBCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90TBA050CD','90TBA050CD','90TBA050CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90TBA00V00','90TBA00V00','90TBA00V00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90TBA00U00','90TBA00U00','90TBA00U00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90TAS0FY00','90TAS0FY00','90TAS0FY00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90TACTO1WW','90TACTO1WW','90TACTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90TAA0BKCD','90TAA0BKCD','90TAA0BKCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90TAA088CD','90TAA088CD','90TAA088CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90TAA07HCD','90TAA07HCD','90TAA07HCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90TAA07GCD','90TAA07GCD','90TAA07GCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90TAA06MCD','90TAA06MCD','90TAA06MCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90TAA06KCD','90TAA06KCD','90TAA06KCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90TAA05VCD','90TAA05VCD','90TAA05VCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90TAA05LCD','90TAA05LCD','90TAA05LCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90TAA03300','90TAA03300','90TAA03300');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90TAA021CD','90TAA021CD','90TAA021CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90T3008KCD','90T3008KCD','90T3008KCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SM009GCD','90SM009GCD','90SM009GCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SM0071CD','90SM0071CD','90SM0071CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SM003TCD','90SM003TCD','90SM003TCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SM0005CD','90SM0005CD','90SM0005CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SL0020CD','90SL0020CD','90SL0020CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SL000GCD','90SL000GCD','90SL000GCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SHCTO1WW','90SHCTO1WW','90SHCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SFCTO1WW','90SFCTO1WW','90SFCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SFA05UCD','90SFA05UCD','90SFA05UCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SFA05SCD','90SFA05SCD','90SFA05SCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SFA05QCD','90SFA05QCD','90SFA05QCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SFA02JCD','90SFA02JCD','90SFA02JCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SFA020CD','90SFA020CD','90SFA020CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SFA00UCD','90SFA00UCD','90SFA00UCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SECTO1WW','90SECTO1WW','90SECTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SEA05NCD','90SEA05NCD','90SEA05NCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SDCTO1WW','90SDCTO1WW','90SDCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SDA02HCD','90SDA02HCD','90SDA02HCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SB0012CD','90SB0012CD','90SB0012CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SB000UCD','90SB000UCD','90SB000UCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SB000KCD','90SB000KCD','90SB000KCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90SB0007CD','90SB0007CD','90SB0007CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90S0000ACD','90S0000ACD','90S0000ACD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90S00004CD','90S00004CD','90S00004CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90RQCTO1WW','90RQCTO1WW','90RQCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90RM001NCD','90RM001NCD','90RM001NCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90RM0013CD','90RM0013CD','90RM0013CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90RJ00DXCD','90RJ00DXCD','90RJ00DXCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90RJ0099CD','90RJ0099CD','90RJ0099CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90QKA015CD','90QKA015CD','90QKA015CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90NYCTO1WW','90NYCTO1WW','90NYCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90NB001SCD','90NB001SCD','90NB001SCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90NA000VCD','90NA000VCD','90NA000VCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90MV00FRCD','90MV00FRCD','90MV00FRCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90MV00C0CD','90MV00C0CD','90MV00C0CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90MU0008CD','90MU0008CD','90MU0008CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90MQCTO1WW','90MQCTO1WW','90MQCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90MNA0EECD','90MNA0EECD','90MNA0EECD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90MNA0EACD','90MNA0EACD','90MNA0EACD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90MN000CCD','90MN000CCD','90MN000CCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90MG0008CD','90MG0008CD','90MG0008CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90MCCTO1WW','90MCCTO1WW','90MCCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90MB001NCD','90MB001NCD','90MB001NCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90MB001KCD','90MB001KCD','90MB001KCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90M3A008CD','90M3A008CD','90M3A008CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90M3A007CD','90M3A007CD','90M3A007CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90M2CTO1WW','90M2CTO1WW','90M2CTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90M2A144CD','90M2A144CD','90M2A144CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90M2A122CD','90M2A122CD','90M2A122CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90M2A0A6CD','90M2A0A6CD','90M2A0A6CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90M2A0A4CD','90M2A0A4CD','90M2A0A4CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90M2A09VCD','90M2A09VCD','90M2A09VCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90M2A02SCD','90M2A02SCD','90M2A02SCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90M2A02RCD','90M2A02RCD','90M2A02RCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90LX000FCD','90LX000FCD','90LX000FCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90LV0014CD','90LV0014CD','90LV0014CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90L0S02J00','90L0S02J00','90L0S02J00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90L0CTO1WW','90L0CTO1WW','90L0CTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90KX0004CP','90KX0004CP','90KX0004CP');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90KVCTO1WW','90KVCTO1WW','90KVCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90KSCTO1WW','90KSCTO1WW','90KSCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90KQS0DW00','90KQS0DW00','90KQS0DW00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90KQCTO1WW','90KQCTO1WW','90KQCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90KQA02200','90KQA02200','90KQA02200');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90KDCTO1WW','90KDCTO1WW','90KDCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90K7A09HCD','90K7A09HCD','90K7A09HCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90JJ000ACP','90JJ000ACP','90JJ000ACP');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90J8CTO1WW','90J8CTO1WW','90J8CTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90J3001MCD','90J3001MCD','90J3001MCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90J30007CD','90J30007CD','90J30007CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90HU00E3CD','90HU00E3CD','90HU00E3CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90HT000LCD','90HT000LCD','90HT000LCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90HMA01GCD','90HMA01GCD','90HMA01GCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90HLA00WCD','90HLA00WCD','90HLA00WCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90HKCTO1WW','90HKCTO1WW','90HKCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90HJCTO1WW','90HJCTO1WW','90HJCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90HJA0YJCD','90HJA0YJCD','90HJA0YJCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90HJA0YFCD','90HJA0YFCD','90HJA0YFCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90HJA0X5CD','90HJA0X5CD','90HJA0X5CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90HJA0ULCD','90HJA0ULCD','90HJA0ULCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90HJA0U5CD','90HJA0U5CD','90HJA0U5CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90HJA0GHCD','90HJA0GHCD','90HJA0GHCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90HHCTO1WW','90HHCTO1WW','90HHCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90H80009CP','90H80009CP','90H80009CP');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90H00043CD','90H00043CD','90H00043CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90H0001WCD','90H0001WCD','90H0001WCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GV002ACD','90GV002ACD','90GV002ACD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GV001PCD','90GV001PCD','90GV001PCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GV001ECD','90GV001ECD','90GV001ECD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GQZ3ZMCN','90GQZ3ZMCN','90GQZ3ZMCN');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GQCTO1WW','90GQCTO1WW','90GQCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GL0028CD','90GL0028CD','90GL0028CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GKCTO1WW','90GKCTO1WW','90GKCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GH001GCD','90GH001GCD','90GH001GCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GH000LCD','90GH000LCD','90GH000LCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GGCTO1WW','90GGCTO1WW','90GGCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GG0015CD','90GG0015CD','90GG0015CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GG000UCD','90GG000UCD','90GG000UCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GG000TCD','90GG000TCD','90GG000TCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GG000SCD','90GG000SCD','90GG000SCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GG000QCD','90GG000QCD','90GG000QCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GG000MCD','90GG000MCD','90GG000MCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GG000HCD','90GG000HCD','90GG000HCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GE0008CD','90GE0008CD','90GE0008CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GE0007CD','90GE0007CD','90GE0007CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90GB003RCD','90GB003RCD','90GB003RCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90G9003LCD','90G9003LCD','90G9003LCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90G2A011CD','90G2A011CD','90G2A011CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90G2A00UCD','90G2A00UCD','90G2A00UCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90G2A00FCD','90G2A00FCD','90G2A00FCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90G1CTO1WW','90G1CTO1WW','90G1CTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90G1A0RBCD','90G1A0RBCD','90G1A0RBCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90G1A0LVCD','90G1A0LVCD','90G1A0LVCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90G1A0CBCD','90G1A0CBCD','90G1A0CBCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90G1A09KCD','90G1A09KCD','90G1A09KCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90G0CTO1WW','90G0CTO1WW','90G0CTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90G0A142CD','90G0A142CD','90G0A142CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90FYCTO1WW','90FYCTO1WW','90FYCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90FSCTO1WW','90FSCTO1WW','90FSCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90FQCTO1WW','90FQCTO1WW','90FQCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90FF001RCD','90FF001RCD','90FF001RCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90FF001QCD','90FF001QCD','90FF001QCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90FF0013CD','90FF0013CD','90FF0013CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90F9CTO1WW','90F9CTO1WW','90F9CTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90F5001UCN','90F5001UCN','90F5001UCN');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90F5001TCN','90F5001TCN','90F5001TCN');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90F5001NCN','90F5001NCN','90F5001NCN');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90F5000ACN','90F5000ACN','90F5000ACN');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90F30002CN','90F30002CN','90F30002CN');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90ETCTO1WW','90ETCTO1WW','90ETCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90ET0014CN','90ET0014CN','90ET0014CN');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90ESCTO1WW','90ESCTO1WW','90ESCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90ES0019CN','90ES0019CN','90ES0019CN');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90ES000WCN','90ES000WCN','90ES000WCN');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90ES000VCN','90ES000VCN','90ES000VCN');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90ES000BCN','90ES000BCN','90ES000BCN');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90EACTO1WW','90EACTO1WW','90EACTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90EA001TCD','90EA001TCD','90EA001TCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90EA001NCD','90EA001NCD','90EA001NCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90E9001DCD','90E9001DCD','90E9001DCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90E90011CD','90E90011CD','90E90011CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90E9000BCD','90E9000BCD','90E9000BCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90E8CTO1WW','90E8CTO1WW','90E8CTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90E80032CD','90E80032CD','90E80032CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90E80030CD','90E80030CD','90E80030CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90E8002YCD','90E8002YCD','90E8002YCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90E8002PCD','90E8002PCD','90E8002PCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90E8002LCD','90E8002LCD','90E8002LCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90E80023CD','90E80023CD','90E80023CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90E8001WCD','90E8001WCD','90E8001WCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90E8001VCD','90E8001VCD','90E8001VCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90E80011CD','90E80011CD','90E80011CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90E8000SCD','90E8000SCD','90E8000SCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90E8000KCD','90E8000KCD','90E8000KCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90E5000YCD','90E5000YCD','90E5000YCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90E5000PCD','90E5000PCD','90E5000PCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90E5000ECD','90E5000ECD','90E5000ECD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90DWCTO1WW','90DWCTO1WW','90DWCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90DWA05ACD','90DWA05ACD','90DWA05ACD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90DTA00SCD','90DTA00SCD','90DTA00SCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90DTA00QCD','90DTA00QCD','90DTA00QCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90DTA006CD','90DTA006CD','90DTA006CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90DSCTO1WW','90DSCTO1WW','90DSCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90DCCTO1WW','90DCCTO1WW','90DCCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90DA00DGCD','90DA00DGCD','90DA00DGCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90DA00DECD','90DA00DECD','90DA00DECD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90D9005LCD','90D9005LCD','90D9005LCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90D8A00A00','90D8A00A00','90D8A00A00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90D7A01Q00','90D7A01Q00','90D7A01Q00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90D7A01200','90D7A01200','90D7A01200');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90D7A01100','90D7A01100','90D7A01100');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90D7A00Y00','90D7A00Y00','90D7A00Y00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90D7A00W00','90D7A00W00','90D7A00W00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90D7A00P00','90D7A00P00','90D7A00P00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90D7A00600','90D7A00600','90D7A00600');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90D4CTO1WW','90D4CTO1WW','90D4CTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90D4A027CN','90D4A027CN','90D4A027CN');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90D0CTO1WW','90D0CTO1WW','90D0CTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90CYCTO1WW','90CYCTO1WW','90CYCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90CYA05200','90CYA05200','90CYA05200');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90CYA03H00','90CYA03H00','90CYA03H00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90CYA03900','90CYA03900','90CYA03900');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90CYA03500','90CYA03500','90CYA03500');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90CXS03Q00','90CXS03Q00','90CXS03Q00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90CXCTO1WW','90CXCTO1WW','90CXCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90CXA07H00','90CXA07H00','90CXA07H00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90CWCTO1WW','90CWCTO1WW','90CWCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90CH001LCD','90CH001LCD','90CH001LCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90CH001JCD','90CH001JCD','90CH001JCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90CH0007CD','90CH0007CD','90CH0007CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90CC0011CD','90CC0011CD','90CC0011CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90CC000FCD','90CC000FCD','90CC000FCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90BYCTO1WW','90BYCTO1WW','90BYCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90BYA00KCN','90BYA00KCN','90BYA00KCN');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90B900C0CD','90B900C0CD','90B900C0CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90B90078CD','90B90078CD','90B90078CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90B70085CD','90B70085CD','90B70085CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90B70042CD','90B70042CD','90B70042CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90B70026CD','90B70026CD','90B70026CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90AXCTO1WW','90AXCTO1WW','90AXCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90AX000HCD','90AX000HCD','90AX000HCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','90AX000ACD','90AX000ACD','90AX000ACD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','70Q3R004CN','70Q3R004CN','70Q3R004CN');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','70Q30001CN','70Q30001CN','70Q30001CN');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','30FNA4NBCW','30FNA4NBCW','30FNA4NBCW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','30FNA2V5CW','30FNA2V5CW','30FNA2V5CW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','30D0A4RKCW','30D0A4RKCW','30D0A4RKCW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','30D0A1GVCW','30D0A1GVCW','30D0A1GVCW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','30BYABP5CW','30BYABP5CW','30BYABP5CW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','30BYA9WMCW','30BYA9WMCW','30BYA9WMCW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','30BGA02G00','30BGA02G00','30BGA02G00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','30BBA04XCW','30BBA04XCW','30BBA04XCW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','30B3000UCW','30B3000UCW','30B3000UCW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','30AGA22K00','30AGA22K00','30AGA22K00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','30A6S26Q00','30A6S26Q00','30A6S26Q00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','20U2A006CD','20U2A006CD','20U2A006CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','20S0A0EECD','20S0A0EECD','20S0A0EECD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','12S9A00HCD','12S9A00HCD','12S9A00HCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','12J8S00Q00','12J8S00Q00','12J8S00Q00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','12J8CTO1WW','12J8CTO1WW','12J8CTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','12GJA024CD','12GJA024CD','12GJA024CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','12GJA01YCD','12GJA01YCD','12GJA01YCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','12B4A074CD','12B4A074CD','12B4A074CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','12A4A170CD','12A4A170CD','12A4A170CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','12A4A0YNCD','12A4A0YNCD','12A4A0YNCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','12A4A0UXCD','12A4A0UXCD','12A4A0UXCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','12A4A0CVCD','12A4A0CVCD','12A4A0CVCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','12A4A07WCD','12A4A07WCD','12A4A07WCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','12A40003CD','12A40003CD','12A40003CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','11VRCTO1WW','11VRCTO1WW','11VRCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','11T1A00CCD','11T1A00CCD','11T1A00CCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','11SKS04N00','11SKS04N00','11SKS04N00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','11SKS04M00','11SKS04M00','11SKS04M00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','11SKS02600','11SKS02600','11SKS02600');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','11SKS00N00','11SKS00N00','11SKS00N00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','11SKS00M00','11SKS00M00','11SKS00M00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','11SKCTO1WW','11SKCTO1WW','11SKCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','11SKA026CD','11SKA026CD','11SKA026CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','11SKA01JCD','11SKA01JCD','11SKA01JCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','11S8A00NCD','11S8A00NCD','11S8A00NCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','11QFA08QCD','11QFA08QCD','11QFA08QCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','11MBA00GCD','11MBA00GCD','11MBA00GCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','11KMA0C200','11KMA0C200','11KMA0C200');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','11KMA051CD','11KMA051CD','11KMA051CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','11EBA2X8CD','11EBA2X8CD','11EBA2X8CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','11EBA0TMCD','11EBA0TMCD','11EBA0TMCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','11DXCTO1WW','11DXCTO1WW','11DXCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','11CWCTO1WW','11CWCTO1WW','11CWCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','11CWCT01WW','11CWCT01WW','11CWCT01WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','11CWA03RCD','11CWA03RCD','11CWA03RCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','11BRA0LKCD','11BRA0LKCD','11BRA0LKCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','11BFA00HCD','11BFA00HCD','11BFA00HCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','11BBA002CD','11BBA002CD','11BBA002CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','11AR52AFCN','11AR52AFCN','11AR52AFCN');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10TDCTO1WW','10TDCTO1WW','10TDCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10SWCTO1WW','10SWCTO1WW','10SWCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10SWA0BLCD','10SWA0BLCD','10SWA0BLCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10SWA0BKCD','10SWA0BKCD','10SWA0BKCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10SWA04YCD','10SWA04YCD','10SWA04YCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10SWA03FCD','10SWA03FCD','10SWA03FCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10SWA03DCD','10SWA03DCD','10SWA03DCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10SMS12K00','10SMS12K00','10SMS12K00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10SMCTO1WW','10SMCTO1WW','10SMCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10SMCT01WW','10SMCT01WW','10SMCT01WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10SMA0BLCD','10SMA0BLCD','10SMA0BLCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10SMA0BGCD','10SMA0BGCD','10SMA0BGCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10SMA034CD','10SMA034CD','10SMA034CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10SMA033CD','10SMA033CD','10SMA033CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10SMA02NCD','10SMA02NCD','10SMA02NCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10SMA00DCD','10SMA00DCD','10SMA00DCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10S9CTO1WW','10S9CTO1WW','10S9CTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10RJA00LCD','10RJA00LCD','10RJA00LCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10RJA004CD','10RJA004CD','10RJA004CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10RJ0011CD','10RJ0011CD','10RJ0011CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10R9CTO1WW','10R9CTO1WW','10R9CTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10QF0005CD','10QF0005CD','10QF0005CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10QEA006CD','10QEA006CD','10QEA006CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10QEA002CD','10QEA002CD','10QEA002CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10NVCTO1WW','10NVCTO1WW','10NVCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10NBCTO1WW','10NBCTO1WW','10NBCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10NBA0C5CD','10NBA0C5CD','10NBA0C5CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10NACTO1WW','10NACTO1WW','10NACTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10N9S0XQ00','10N9S0XQ00','10N9S0XQ00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10N9CTO1WW','10N9CTO1WW','10N9CTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10N9CT01WW','10N9CT01WW','10N9CT01WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10MLS0XY06','10MLS0XY06','10MLS0XY06');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10KTA004CD','10KTA004CD','10KTA004CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10KT000CCD','10KT000CCD','10KT000CCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10KSA008CD','10KSA008CD','10KSA008CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10KSA007CD','10KSA007CD','10KSA007CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10KS000KCD','10KS000KCD','10KS000KCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10KS000FCD','10KS000FCD','10KS000FCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10GYCTO1WW','10GYCTO1WW','10GYCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10GNCTO1WW','10GNCTO1WW','10GNCTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10G7CTO1WW','10G7CTO1WW','10G7CTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10G4CTO1WW','10G4CTO1WW','10G4CTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10G4A01X00','10G4A01X00','10G4A01X00');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10ENA01VCD','10ENA01VCD','10ENA01VCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10ENA01TCD','10ENA01TCD','10ENA01TCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10ENA00VCD','10ENA00VCD','10ENA00VCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10EN001ACD','10EN001ACD','10EN001ACD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10E4CTO1WW','10E4CTO1WW','10E4CTO1WW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10C1A00KCV','10C1A00KCV','10C1A00KCV');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10C0S00400','10C0S00400','10C0S00400');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10C0A035CD','10C0A035CD','10C0A035CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10C0A02SCD','10C0A02SCD','10C0A02SCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10C0A02RCD','10C0A02RCD','10C0A02RCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10C0A02QCD','10C0A02QCD','10C0A02QCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10C0A021CD','10C0A021CD','10C0A021CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10C0A01WCD','10C0A01WCD','10C0A01WCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10C0A01VCD','10C0A01VCD','10C0A01VCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10C0A01TCD','10C0A01TCD','10C0A01TCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10C0A01HCD','10C0A01HCD','10C0A01HCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10C0A01DCD','10C0A01DCD','10C0A01DCD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10C0A014CD','10C0A014CD','10C0A014CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10C00055CD','10C00055CD','10C00055CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10C00023CW','10C00023CW','10C00023CW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10C00020CW','10C00020CW','10C00020CW');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10C00005CD','10C00005CD','10C00005CD');
insert into `idevelop_brand_tianqing` (`brand`,`series`,`device_model`,`brand_series_model`) values ('联想','10AHS0G300','10AHS0G300','10AHS0G300');



