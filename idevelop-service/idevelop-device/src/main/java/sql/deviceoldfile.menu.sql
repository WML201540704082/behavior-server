INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1805522703225368577', 1123598815738675201, 'deviceoldfile', '', 'menu', '/device/deviceoldfile', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1805522703225368578', '1805522703225368577', 'deviceoldfile_add', '新增', 'add', '/device/deviceoldfile/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1805522703225368579', '1805522703225368577', 'deviceoldfile_edit', '修改', 'edit', '/device/deviceoldfile/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1805522703225368580', '1805522703225368577', 'deviceoldfile_delete', '删除', 'delete', '/api/idevelop-device/deviceoldfile/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1805522703225368581', '1805522703225368577', 'deviceoldfile_view', '查看', 'view', '/device/deviceoldfile/view', 'file-text', 4, 2, 2, 1, NULL, 0);
