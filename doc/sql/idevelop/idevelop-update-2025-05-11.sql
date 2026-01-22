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
INSERT INTO `idevelop`.`idevelop_schedule_job`(`job_id`, `bean_name`, `params`, `cron_expression`, `status`, `remark`,
                                               `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`,
                                               `update_time`, `is_deleted`, `type`)
VALUES (74520, 'cmdbSuppleERPInfo',
        '{     \"beanName\": \"cmdbSuppleERPInfo\",     \"jobId\": 74476,     \"requestSize\": 1000,     \"assetCodeErp\": \"200305119835\",     \"area\": \"3717\" }',
        '0 0 12 * * ?', 1, '台账数据补充ERP信息', '000000', NULL, '2025-05-10 18:21:53', NULL, NULL, NULL, 0,
        '数据贯通');


CREATE TABLE `idevelop_cmdb_entity_export`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`        varchar(50)  DEFAULT NULL COMMENT '属性名称',
    `value`       bigint(50) DEFAULT NULL COMMENT '属性ID',
    `sort`        int(10) DEFAULT NULL COMMENT '排序',
    `remark`      varchar(255) DEFAULT NULL COMMENT '说明',
    `is_deleted`  tinyint(1) DEFAULT '0' COMMENT '是否删除, 0:否;1:是',
    `tenant_id`   varchar(12)  DEFAULT NULL COMMENT '租户ID',
    `create_user` bigint(24) DEFAULT NULL COMMENT '创建人',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `update_user` bigint(24) DEFAULT NULL COMMENT '修改人',
    `update_time` datetime     DEFAULT NULL COMMENT '修改时间',
    `status`      tinyint(2) DEFAULT NULL COMMENT '状态',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='台账导出属性表格';


INSERT INTO `idevelop`.`idevelop_cmdb_ui`(`id`, `name`, `value`, `remark`, `type`, `is_deleted`, `tenant_id`,
                                          `create_user`, `create_dept`, `create_time`, `update_user`, `update_time`,
                                          `status`)
VALUES (54, 'SG_CM_BUS', 1110695581384704, '资产模型ID', 2, 0, NULL, 1123598821738675201, NULL, '2025-05-11 12:17:33',
        1123598821738675201, '2025-05-11 12:17:33', 1);

