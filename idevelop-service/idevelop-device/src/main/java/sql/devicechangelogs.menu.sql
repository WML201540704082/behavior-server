INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765561785553428482', 1123598815738675201, 'devicechangelogs', '', 'menu', '/device/devicechangelogs', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765561785553428483', '1765561785553428482', 'devicechangelogs_add', '新增', 'add', '/device/devicechangelogs/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765561785553428484', '1765561785553428482', 'devicechangelogs_edit', '修改', 'edit', '/device/devicechangelogs/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765561785553428485', '1765561785553428482', 'devicechangelogs_delete', '删除', 'delete', '/api/idevelop-device/devicechangelogs/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765561785553428486', '1765561785553428482', 'devicechangelogs_view', '查看', 'view', '/device/devicechangelogs/view', 'file-text', 4, 2, 2, 1, NULL, 0);
