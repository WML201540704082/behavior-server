INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1773906528047738881', 1123598815738675201, 'deviceassetcaching', '', 'menu', '/device/deviceassetcaching', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1773906528047738882', '1773906528047738881', 'deviceassetcaching_add', '新增', 'add', '/device/deviceassetcaching/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1773906528047738883', '1773906528047738881', 'deviceassetcaching_edit', '修改', 'edit', '/device/deviceassetcaching/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1773906528047738884', '1773906528047738881', 'deviceassetcaching_delete', '删除', 'delete', '/api/idevelop-device/deviceassetcaching/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1773906528047738885', '1773906528047738881', 'deviceassetcaching_view', '查看', 'view', '/device/deviceassetcaching/view', 'file-text', 4, 2, 2, 1, NULL, 0);
