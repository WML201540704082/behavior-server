INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1762359963641470977', 1123598815738675201, 'devicetransfer', '', 'menu', '/device/devicetransfer', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1762359963641470978', '1762359963641470977', 'devicetransfer_add', '新增', 'add', '/device/devicetransfer/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1762359963641470979', '1762359963641470977', 'devicetransfer_edit', '修改', 'edit', '/device/devicetransfer/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1762359963641470980', '1762359963641470977', 'devicetransfer_delete', '删除', 'delete', '/api/idevelop-device/devicetransfer/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1762359963641470981', '1762359963641470977', 'devicetransfer_view', '查看', 'view', '/device/devicetransfer/view', 'file-text', 4, 2, 2, 1, NULL, 0);
