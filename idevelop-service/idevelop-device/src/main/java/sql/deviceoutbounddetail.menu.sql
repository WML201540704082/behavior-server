INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765269633470697473', 1123598815738675201, 'deviceoutbounddetail', '', 'menu', '/device/deviceoutbounddetail', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765269633470697474', '1765269633470697473', 'deviceoutbounddetail_add', '新增', 'add', '/device/deviceoutbounddetail/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765269633470697475', '1765269633470697473', 'deviceoutbounddetail_edit', '修改', 'edit', '/device/deviceoutbounddetail/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765269633470697476', '1765269633470697473', 'deviceoutbounddetail_delete', '删除', 'delete', '/api/idevelop-device/deviceoutbounddetail/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765269633470697477', '1765269633470697473', 'deviceoutbounddetail_view', '查看', 'view', '/device/deviceoutbounddetail/view', 'file-text', 4, 2, 2, 1, NULL, 0);
