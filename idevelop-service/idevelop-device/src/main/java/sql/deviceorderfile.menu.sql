INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765269207207776258', 1123598815738675201, 'deviceorderfile', '', 'menu', '/device/deviceorderfile', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765269207207776259', '1765269207207776258', 'deviceorderfile_add', '新增', 'add', '/device/deviceorderfile/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765269207207776260', '1765269207207776258', 'deviceorderfile_edit', '修改', 'edit', '/device/deviceorderfile/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765269207207776261', '1765269207207776258', 'deviceorderfile_delete', '删除', 'delete', '/api/idevelop-device/deviceorderfile/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765269207207776262', '1765269207207776258', 'deviceorderfile_view', '查看', 'view', '/device/deviceorderfile/view', 'file-text', 4, 2, 2, 1, NULL, 0);
