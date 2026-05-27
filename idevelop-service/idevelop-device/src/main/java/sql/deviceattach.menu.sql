INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1760868268002074626', 1123598815738675201, 'deviceattach', '', 'menu', '/device/deviceattach', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1760868268002074627', '1760868268002074626', 'deviceattach_add', '新增', 'add', '/device/deviceattach/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1760868268002074628', '1760868268002074626', 'deviceattach_edit', '修改', 'edit', '/device/deviceattach/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1760868268002074629', '1760868268002074626', 'deviceattach_delete', '删除', 'delete', '/api/idevelop-device/deviceattach/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1760868268002074630', '1760868268002074626', 'deviceattach_view', '查看', 'view', '/device/deviceattach/view', 'file-text', 4, 2, 2, 1, NULL, 0);
