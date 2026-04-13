-- 创建ipc_ticket表
CREATE TABLE `ipc_ticket` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ticket` varchar(255) NOT NULL COMMENT 'ticket',
  `ip` varchar(50) NOT NULL COMMENT '客户端IP',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `is_deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_ticket` (`ticket`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='存储ticket和ip的表';