INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1800730801076346882', 1123598815738675201, 'checktaskremind', '', 'menu', '/device/checktaskremind', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1800730801076346883', '1800730801076346882', 'checktaskremind_add', '新增', 'add', '/device/checktaskremind/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1800730801076346884', '1800730801076346882', 'checktaskremind_edit', '修改', 'edit', '/device/checktaskremind/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1800730801076346885', '1800730801076346882', 'checktaskremind_delete', '删除', 'delete', '/api/idevelop-device/checktaskremind/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1800730801076346886', '1800730801076346882', 'checktaskremind_view', '查看', 'view', '/device/checktaskremind/view', 'file-text', 4, 2, 2, 1, NULL, 0);
