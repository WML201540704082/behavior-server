CREATE TABLE `llq_favorites_nav` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `url` varchar(500) DEFAULT NULL COMMENT 'url地址',
  `app_name` varchar(255) DEFAULT NULL COMMENT '应用名称',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `dept_id` varchar(32) DEFAULT NULL COMMENT '部门id',
  `ip` varchar(50) DEFAULT NULL COMMENT '终端IP',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  `is_deleted` int(11) DEFAULT NULL COMMENT '是否删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_dept` varchar(32) DEFAULT NULL COMMENT '创建部门',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工控机管控--收藏导航表';