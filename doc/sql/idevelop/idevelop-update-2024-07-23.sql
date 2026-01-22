-- ---------------------------------------------------------------------------------------------------------------------
-- Date: 2024-07-23 已完成
-- SQL语句:
ALTER TABLE `idevelop`.`idevelop_approve_record` ADD COLUMN `approve_status` TINYINT ( 1 ) NULL DEFAULT 0 COMMENT '审批状态 0: 同意 1: 驳回' AFTER `status`;
ALTER TABLE `idevelop`.`idevelop_approve_record` ADD COLUMN `filing_code` VARCHAR ( 50 ) NULL COMMENT '工单编号' AFTER `filing_no`;


DROP TABLE IF EXISTS `idevelop_verify_config`;
CREATE TABLE `idevelop_verify_config`
(
    `id`                bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `verify_key`        varchar(48)  DEFAULT NULL COMMENT 'cmdb字段key值',
    `verify_value`      varchar(1)   DEFAULT NULL COMMENT '1true，其他值false',
    `verify_type`       varchar(1)   DEFAULT NULL COMMENT '类型：1主机设备，2网络设备，3安全设备，4存储设备，5终端设备，0公共配置',
    `warning_type`      varchar(48)  DEFAULT NULL COMMENT '告警类型',
    `warning_level`     varchar(48)  DEFAULT NULL COMMENT '告警等级',
    `main_warning_sort` int(6)       DEFAULT NULL COMMENT '告警等级优先级（数字小的优先级高）',
    `is_deleted`        varchar(1)   DEFAULT '0' COMMENT '0有效',
    `warning_reason`    varchar(255) DEFAULT NULL COMMENT '告警原因',
    `warning_detail`    varchar(255) DEFAULT NULL COMMENT '告警详情',
    PRIMARY KEY (`id`) USING BTREE
) AUTO_INCREMENT = 77 COMMENT = '数据变更是否需要人工确认配置、异动告警配置表';


INSERT INTO `idevelop_verify_config`
VALUES (1, 'memoryCardSize', '0', '1', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (2, 'cpuModel', '0', '1', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (3, 'powerInfo', '0', '1', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (4, 'cpuInfo', '0', '1', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (5, 'hardDiskCapability', '0', '1', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (6, 'cpuCoreSize', '0', '1', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (7, 'OSTypeCode', '0', '1', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (8, 'hddNum', '0', '1', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (9, 'OSVersion', '0', '1', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (10, 'sn', '0', '1', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (11, 'brand', '0', '1', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (12, 'hbaCardWWN', '0', '1', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (13, 'IP', '0', '1', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (14, 'hbaCardSize', '0', '1', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (15, 'cpuClockSpeed', '0', '1', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (16, 'powerModel', '0', '1', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (17, 'MAC', '0', '1', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (18, 'OSInfo', '0', '1', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (19, 'cpuBrand', '0', '1', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (20, 'series', '0', '1', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (21, 'cpuSize', '0', '1', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (22, 'deviceModel', '0', '1', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (23, 'cpuFrequecy', '0', '1', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (24, 'cpuArchCode', '0', '1', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (25, 'memSize', '0', '1', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (26, 'hardDiskTypeCode', '0', '1', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (27, 'series', '0', '2', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (28, 'IP', '0', '2', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (29, 'netPortNum', '0', '2', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (30, 'deviceModel', '0', '2', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (31, 'sn', '0', '2', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (32, 'brand', '0', '2', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (33, 'MAC', '0', '2', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (34, 'series', '0', '3', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (35, 'IP', '0', '3', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (36, 'deviceModel', '0', '3', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (37, 'sn', '0', '3', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (38, 'brand', '0', '3', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (39, 'MAC', '0', '3', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (40, 'ratedPower', '0', '4', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (41, 'series', '0', '4', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (42, 'IP', '0', '4', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (43, 'deviceModel', '0', '4', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (44, 'storageCapacity', '0', '4', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (45, 'sn', '0', '4', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (46, 'brand', '0', '4', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (47, 'MAC', '0', '4', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (48, 'memSize', '0', '4', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (49, 'antiVirusModel', '0', '5', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (50, 'IP', '1', '5', 'IP/MAC变更', '紧急', 1004, '0', 'IP发生改变', 'IP由%s变为%s，请确认是否私改IP');
INSERT INTO `idevelop_verify_config`
VALUES (51, 'cpuModel', '0', '5', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (52, 'antiVirusLibrary', '1', '5', '基线异常', '次要', 1014, '0', '病毒库更新时间改变', '由%s变为%s');
INSERT INTO `idevelop_verify_config`
VALUES (53, 'virusSacnTime', '1', '5', '基线异常', '次要', 1016, '0', '漏洞扫描时间改变', '由%s变为%s');
INSERT INTO `idevelop_verify_config`
VALUES (54, 'cpuClockSpeed', '0', '5', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (55, 'lastActiveDate', '0', '5', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (56, 'MAC', '1', '5', 'IP/MAC变更', '紧急', 1002, '0', 'MAC发生改变', 'MAC由%s变为%s');
INSERT INTO `idevelop_verify_config`
VALUES (57, 'hardDiskCapability', '1', '5', '硬件变化', '次要', 1006, '0', '硬盘容量改变', '由%s变为%s');
INSERT INTO `idevelop_verify_config`
VALUES (58, 'cpuBrand', '0', '5', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (59, 'series', '0', '5', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (60, 'OSTypeCode', '1', '5', '硬件变化', '次要', 1012, '0', '操作系统类型改变', '由%s变为%s');
INSERT INTO `idevelop_verify_config`
VALUES (61, 'OSVersion', '1', '5', '硬件变化', '次要', 1010, '0', '操作系统版本号改变', '由%s变为%s');
INSERT INTO `idevelop_verify_config`
VALUES (62, 'antiVirusProduct', '0', '5', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (63, 'deviceModel', '0', '5', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (64, 'sn', '0', '5', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (65, 'cpuFrequecy', '0', '5', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (66, 'brand', '0', '5', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (67, 'memSize', '1', '5', '硬件变化', '次要', 1008, '0', '内存大小改变', '由%s变为%s');
INSERT INTO `idevelop_verify_config`
VALUES (68, 'hardDiskTypeCode', '0', '5', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (69, 'defaultValue', '0', '6', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (70, 'cpuArchCode', '0', '5', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (71, 'NEW_DEVICE_WARNING', '1', '0', '新发现设备', '紧急', 1000, '0', '该设备IP与MAC在台账中无数据',
        '在网IP为%s与MAC为%s未匹配到台账，发现新设备');
INSERT INTO `idevelop_verify_config`
VALUES (72, 'dataSource', '0', '5', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (73, 'OSIssueVersion', '0', '5', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (74, 'collectTime', '0', '5', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (75, 'isITAICode', '0', '5', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config`
VALUES (76, 'factoryDate', '0', '5', NULL, NULL, NULL, '0', NULL, NULL);
