INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1762360486713122817', 1123598815738675201, 'devicetransferdetail', '', 'menu', '/device/devicetransferdetail', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1762360486713122818', '1762360486713122817', 'devicetransferdetail_add', '新增', 'add', '/device/devicetransferdetail/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1762360486713122819', '1762360486713122817', 'devicetransferdetail_edit', '修改', 'edit', '/device/devicetransferdetail/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1762360486713122820', '1762360486713122817', 'devicetransferdetail_delete', '删除', 'delete', '/api/idevelop-device/devicetransferdetail/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1762360486713122821', '1762360486713122817', 'devicetransferdetail_view', '查看', 'view', '/device/devicetransferdetail/view', 'file-text', 4, 2, 2, 1, NULL, 0);
