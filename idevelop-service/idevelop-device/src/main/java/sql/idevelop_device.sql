CREATE TABLE `idevelop_cmdb_ci_attr`
(
    `attr_id`     bigint(20) NOT NULL COMMENT '属性ID',
    `ci_id`       bigint(20) NOT NULL COMMENT '所属模型ID',
    `attr_name`   varchar(50)  NOT NULL COMMENT '属性英文名',
    `attr_label`  varchar(200) NOT NULL COMMENT '属性中文名',
    `is_deleted`  tinyint(1) DEFAULT '0' COMMENT '是否删除, 0:否;1:是',
    `tenant_id`   varchar(12) DEFAULT NULL COMMENT '租户ID',
    `create_user` bigint(24) DEFAULT NULL COMMENT '创建人',
    `create_dept` bigint(24) DEFAULT NULL COMMENT '创建部门',
    `create_time` datetime    DEFAULT NULL COMMENT '创建时间',
    `update_user` bigint(24) DEFAULT NULL COMMENT '修改人',
    `update_time` datetime    DEFAULT NULL COMMENT '修改时间',
    `status`      tinyint(2) DEFAULT NULL COMMENT '状态',
    PRIMARY KEY (`attr_id`) USING BTREE,
    KEY           `ci_id` (`ci_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='模型属性映射表';

CREATE TABLE `idevelop_hardware_basic_tree`
(
    `ci_id`           bigint(24) NOT NULL COMMENT '模型ID',
    `ci_name`         varchar(48) DEFAULT NULL COMMENT '模型英文名',
    `ci_label`        varchar(255) NOT NULL COMMENT '模型中文名',
    `parent_ci_id`    bigint(24) NOT NULL COMMENT '父模型ID',
    `level`           tinyint(2) NOT NULL COMMENT '当前层级',
    `is_menu`         tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否在菜单中显示',
    `is_deleted`      tinyint(1) DEFAULT '0' COMMENT '是否删除, 0:否;1:是',
    `is_map`          tinyint(1) DEFAULT '0' COMMENT '是否需要属性映射表, 0:否;1:是',
    `device_claccify` varchar(24) DEFAULT NULL COMMENT '设备分类',
    `device_type`     varchar(24) DEFAULT NULL COMMENT '设备类型',
    `tenant_id`       varchar(12) DEFAULT NULL COMMENT '租户ID',
    `create_user`     bigint(24) DEFAULT NULL COMMENT '创建人',
    `create_dept`     bigint(24) DEFAULT NULL COMMENT '创建部门',
    `create_time`     datetime    DEFAULT NULL COMMENT '创建时间',
    `update_user`     bigint(24) DEFAULT NULL COMMENT '修改人',
    `update_time`     datetime    DEFAULT NULL COMMENT '修改时间',
    `status`          tinyint(2) DEFAULT NULL COMMENT '状态',
    PRIMARY KEY (`ci_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='资产台账模型树管理表';
