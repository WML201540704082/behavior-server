-- Date: 2024-08-09 未完成
-- SQL语句:
CREATE TABLE `idevelop_i6000_enum`
(
    `id`           bigint(40) NOT NULL COMMENT '主键',
    `enum_name`    varchar(30) DEFAULT NULL COMMENT '枚举数据名称',
    `enum_id`      varchar(50) DEFAULT NULL COMMENT '枚举数据编码',
    `enumval_code` varchar(60) DEFAULT NULL COMMENT '枚举项编码',
    `enumval_name` varchar(50) DEFAULT NULL COMMENT '枚举项名称',
    `create_user`  bigint(20) DEFAULT NULL COMMENT '创建人',
    `create_dept`  bigint(20) DEFAULT NULL COMMENT '创建部门',
    `create_time`  datetime    DEFAULT NULL COMMENT '创建时间',
    `status`       int(2) DEFAULT NULL COMMENT '状态',
    `update_user`  bigint(20) DEFAULT NULL COMMENT '修改人',
    `update_time`  datetime    DEFAULT NULL COMMENT '修改时间',
    `is_deleted`   int(2) DEFAULT NULL COMMENT '是否已删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='i6000枚举数据表';



-- Date: 2024-08-09 未完成
-- SQL语句:

ALTER TABLE `idevelop`.`idevelop_zfit_xt_cwzt`
    ADD COLUMN `status` int(2) NULL DEFAULT NULL COMMENT '状态（无用字段）' AFTER `by8`,
    ADD COLUMN `create_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建人' AFTER `status`,
    ADD COLUMN `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间' AFTER `create_user`,
    ADD COLUMN `update_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新人' AFTER `create_time`,
    ADD COLUMN `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间' AFTER `update_user`,
    ADD COLUMN `is_deleted` int(2) NULL DEFAULT NULL COMMENT '是否删除' AFTER `update_time`;


ALTER TABLE `idevelop`.`idevelop_cmdb_dict_ci`
    ADD COLUMN `ci_alias` varchar(50) NULL COMMENT '模型别名' AFTER `ci_icon`;
