INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765268606893821954', 1123598815738675201, 'deviceapply', '', 'menu', '/device/deviceapply', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765268606893821955', '1765268606893821954', 'deviceapply_add', '新增', 'add', '/device/deviceapply/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765268606893821956', '1765268606893821954', 'deviceapply_edit', '修改', 'edit', '/device/deviceapply/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765268606893821957', '1765268606893821954', 'deviceapply_delete', '删除', 'delete', '/api/idevelop-device/deviceapply/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765268606893821958', '1765268606893821954', 'deviceapply_view', '查看', 'view', '/device/deviceapply/view', 'file-text', 4, 2, 2, 1, NULL, 0);
