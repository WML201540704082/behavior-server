INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1769891832127479810', 1123598815738675201, 'devicerepair', '', 'menu', '/device/devicerepair', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1769891832127479811', '1769891832127479810', 'devicerepair_add', '新增', 'add', '/device/devicerepair/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1769891832127479812', '1769891832127479810', 'devicerepair_edit', '修改', 'edit', '/device/devicerepair/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1769891832127479813', '1769891832127479810', 'devicerepair_delete', '删除', 'delete', '/api/idevelop-device/devicerepair/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1769891832127479814', '1769891832127479810', 'devicerepair_view', '查看', 'view', '/device/devicerepair/view', 'file-text', 4, 2, 2, 1, NULL, 0);
