INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765269632946409474', 1123598815738675201, 'deviceoutbound', '', 'menu', '/device/deviceoutbound', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765269632946409475', '1765269632946409474', 'deviceoutbound_add', '新增', 'add', '/device/deviceoutbound/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765269632946409476', '1765269632946409474', 'deviceoutbound_edit', '修改', 'edit', '/device/deviceoutbound/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765269632946409477', '1765269632946409474', 'deviceoutbound_delete', '删除', 'delete', '/api/idevelop-device/deviceoutbound/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765269632946409478', '1765269632946409474', 'deviceoutbound_view', '查看', 'view', '/device/deviceoutbound/view', 'file-text', 4, 2, 2, 1, NULL, 0);
