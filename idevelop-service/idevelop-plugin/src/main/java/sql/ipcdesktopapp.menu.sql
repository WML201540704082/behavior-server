-- 工控机管控--桌面应用维护表
CREATE TABLE `ipc_desktop_app` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `app_name` varchar(255) DEFAULT NULL COMMENT '桌面应用名称',
  `main_file_name` varchar(255) DEFAULT NULL COMMENT '主程序文件名',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工控机管控--桌面应用维护表';

-- 菜单SQL
INSERT INTO `blade_menu` (`id`, `parent_id`, `code`, `name`, `route`, `path`, `source`, `sort`, `category`, `action`, `icon`, `remark`, `is_open`, `component`, `is_route`, `is_cache`, `is_leaf`, `scope`, `status`, `create_time`, `update_time`, `create_user`, `update_user`) VALUES
('1468475242240', '1468475242240', 'ipcdesktopapp', '桌面应用维护', 'ipcdesktopapp', '/ipc/ipcdesktopapp', 'ipc', 1, 2, 0, 'icon-desktop', '', 1, 'ipc/ipcdesktopapp/index', 1, 0, 1, '', 1, NOW(), NOW(), '1', '1');
