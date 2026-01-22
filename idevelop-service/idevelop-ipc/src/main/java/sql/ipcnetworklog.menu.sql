INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1990329687353245698', 1123598815738675201, 'ipcnetworklog', '', 'menu', '/ipc/ipcnetworklog', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1990329687353245699', '1990329687353245698', 'ipcnetworklog_add', '新增', 'add', '/ipc/ipcnetworklog/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1990329687353245700', '1990329687353245698', 'ipcnetworklog_edit', '修改', 'edit', '/ipc/ipcnetworklog/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1990329687353245701', '1990329687353245698', 'ipcnetworklog_delete', '删除', 'delete', '/api/idevelop-ipc/ipcnetworklog/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1990329687353245702', '1990329687353245698', 'ipcnetworklog_view', '查看', 'view', '/ipc/ipcnetworklog/view', 'file-text', 4, 2, 2, 1, NULL, 0);
