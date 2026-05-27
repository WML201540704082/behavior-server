INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1760562945767579650', 1123598815738675201, 'devicestoragelist', '', 'menu', '/device/devicestoragelist', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1760562945767579651', '1760562945767579650', 'devicestoragelist_add', '新增', 'add', '/device/devicestoragelist/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1760562945767579652', '1760562945767579650', 'devicestoragelist_edit', '修改', 'edit', '/device/devicestoragelist/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1760562945767579653', '1760562945767579650', 'devicestoragelist_delete', '删除', 'delete', '/api/idevelop-device/devicestoragelist/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1760562945767579654', '1760562945767579650', 'devicestoragelist_view', '查看', 'view', '/device/devicestoragelist/view', 'file-text', 4, 2, 2, 1, NULL, 0);
