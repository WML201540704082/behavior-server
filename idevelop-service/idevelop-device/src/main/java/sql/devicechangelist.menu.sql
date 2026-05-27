INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765561359449890817', 1123598815738675201, 'devicechangelist', '', 'menu', '/device/devicechangelist', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765561359449890818', '1765561359449890817', 'devicechangelist_add', '新增', 'add', '/device/devicechangelist/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765561359449890819', '1765561359449890817', 'devicechangelist_edit', '修改', 'edit', '/device/devicechangelist/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765561359449890820', '1765561359449890817', 'devicechangelist_delete', '删除', 'delete', '/api/idevelop-device/devicechangelist/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765561359449890821', '1765561359449890817', 'devicechangelist_view', '查看', 'view', '/device/devicechangelist/view', 'file-text', 4, 2, 2, 1, NULL, 0);
