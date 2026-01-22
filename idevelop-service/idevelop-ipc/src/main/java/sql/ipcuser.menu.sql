INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1990329817573801986', 1123598815738675201, 'ipcuser', '', 'menu', '/ipc/ipcuser', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1990329817573801987', '1990329817573801986', 'ipcuser_add', '新增', 'add', '/ipc/ipcuser/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1990329817573801988', '1990329817573801986', 'ipcuser_edit', '修改', 'edit', '/ipc/ipcuser/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1990329817573801989', '1990329817573801986', 'ipcuser_delete', '删除', 'delete', '/api/idevelop-ipc/ipcuser/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1990329817573801990', '1990329817573801986', 'ipcuser_view', '查看', 'view', '/ipc/ipcuser/view', 'file-text', 4, 2, 2, 1, NULL, 0);
