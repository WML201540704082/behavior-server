INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1784822096161337346', 1123598815738675201, 'deviceinventorylog', '', 'menu', '/device/deviceinventorylog', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1784822096161337347', '1784822096161337346', 'deviceinventorylog_add', '新增', 'add', '/device/deviceinventorylog/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1784822096161337348', '1784822096161337346', 'deviceinventorylog_edit', '修改', 'edit', '/device/deviceinventorylog/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1784822096161337349', '1784822096161337346', 'deviceinventorylog_delete', '删除', 'delete', '/api/idevelop-device/deviceinventorylog/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1784822096161337350', '1784822096161337346', 'deviceinventorylog_view', '查看', 'view', '/device/deviceinventorylog/view', 'file-text', 4, 2, 2, 1, NULL, 0);
