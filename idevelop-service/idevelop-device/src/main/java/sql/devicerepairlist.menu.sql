INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1769894320763969538', 1123598815738675201, 'devicerepairlist', '', 'menu', '/device/devicerepairlist', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1769894320763969539', '1769894320763969538', 'devicerepairlist_add', '新增', 'add', '/device/devicerepairlist/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1769894320763969540', '1769894320763969538', 'devicerepairlist_edit', '修改', 'edit', '/device/devicerepairlist/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1769894320763969541', '1769894320763969538', 'devicerepairlist_delete', '删除', 'delete', '/api/idevelop-device/devicerepairlist/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1769894320763969542', '1769894320763969538', 'devicerepairlist_view', '查看', 'view', '/device/devicerepairlist/view', 'file-text', 4, 2, 2, 1, NULL, 0);
