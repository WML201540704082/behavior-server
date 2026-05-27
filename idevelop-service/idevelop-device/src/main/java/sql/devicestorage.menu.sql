INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1760562242999361537', 1123598815738675201, 'devicestorage', '', 'menu', '/device/devicestorage', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1760562242999361538', '1760562242999361537', 'devicestorage_add', '新增', 'add', '/device/devicestorage/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1760562242999361539', '1760562242999361537', 'devicestorage_edit', '修改', 'edit', '/device/devicestorage/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1760562242999361540', '1760562242999361537', 'devicestorage_delete', '删除', 'delete', '/api/idevelop-device/devicestorage/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1760562242999361541', '1760562242999361537', 'devicestorage_view', '查看', 'view', '/device/devicestorage/view', 'file-text', 4, 2, 2, 1, NULL, 0);
