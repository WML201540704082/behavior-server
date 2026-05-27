INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1764836719625400322', 1123598815738675201, 'warehouse', '', 'menu', '/device/warehouse', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1764836719625400323', '1764836719625400322', 'warehouse_add', '新增', 'add', '/device/warehouse/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1764836719625400324', '1764836719625400322', 'warehouse_edit', '修改', 'edit', '/device/warehouse/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1764836719625400325', '1764836719625400322', 'warehouse_delete', '删除', 'delete', '/api/idevelop-device/warehouse/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1764836719625400326', '1764836719625400322', 'warehouse_view', '查看', 'view', '/device/warehouse/view', 'file-text', 4, 2, 2, 1, NULL, 0);
