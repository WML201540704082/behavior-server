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
CREATE TABLE `idevelop_graph_signature`
(
    `id`                   varchar(50) NOT NULL COMMENT '主键id',
    `region_code`          varchar(30)  DEFAULT NULL COMMENT '区域编码',
    `region_name`          varchar(30)  DEFAULT NULL COMMENT '区域名称',
    `region_ip`            varchar(10)  DEFAULT NULL COMMENT '区域IP',
    `region_port`          varchar(20)  DEFAULT NULL COMMENT '区域端口号',
    `region_asset_oid`     varchar(20)  DEFAULT NULL COMMENT '区域组织id(oid)',
    `region_asset_id`      varchar(20)  DEFAULT NULL COMMENT '区域资产id(id)',
    `region_app_key`       varchar(20)  DEFAULT NULL COMMENT '区域AppKey',
    `region_app_secret`    varchar(20)  DEFAULT NULL COMMENT '区域AppSecret',
    `region_signature`     varchar(255) DEFAULT NULL COMMENT '区域Signature',
    `region_authorization` varchar(255) DEFAULT NULL COMMENT '区域Authorization',
    `remark`               varchar(255) DEFAULT NULL COMMENT '数据权限备注',
    `create_user`          bigint(20) DEFAULT NULL COMMENT '创建人',
    `create_time`          datetime     DEFAULT NULL COMMENT '创建时间',
    `update_user`          bigint(20) DEFAULT NULL COMMENT '修改人',
    `update_time`          datetime     DEFAULT NULL COMMENT '修改时间',
    `status`               int(2) DEFAULT NULL COMMENT '状态',
    `is_deleted`           int(2) DEFAULT NULL COMMENT '是否已删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='各地市信创天擎详情信息';


ALTER TABLE `idevelop`.`idevelop_user`
    ADD COLUMN `is_login` int(6) UNSIGNED NULL DEFAULT 0 COMMENT '是否是第一次登录' AFTER `ext`;
