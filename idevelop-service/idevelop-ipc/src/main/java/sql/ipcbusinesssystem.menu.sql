INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1990327965738254338', 1123598815738675201, 'ipcbusinesssystem', '', 'menu', '/ipc/ipcbusinesssystem', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1990327965738254339', '1990327965738254338', 'ipcbusinesssystem_add', '新增', 'add', '/ipc/ipcbusinesssystem/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1990327965738254340', '1990327965738254338', 'ipcbusinesssystem_edit', '修改', 'edit', '/ipc/ipcbusinesssystem/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1990327965738254341', '1990327965738254338', 'ipcbusinesssystem_delete', '删除', 'delete', '/api/idevelop-ipc/ipcbusinesssystem/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1990327965738254342', '1990327965738254338', 'ipcbusinesssystem_view', '查看', 'view', '/ipc/ipcbusinesssystem/view', 'file-text', 4, 2, 2, 1, NULL, 0);
