INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1839558013120561153', 1123598815738675201, 'reminduser', '', 'menu', '/system/reminduser', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1839558013120561154', '1839558013120561153', 'reminduser_add', '新增', 'add', '/system/reminduser/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1839558013120561155', '1839558013120561153', 'reminduser_edit', '修改', 'edit', '/system/reminduser/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1839558013120561156', '1839558013120561153', 'reminduser_delete', '删除', 'delete', '/api/idevelop-system/reminduser/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1839558013120561157', '1839558013120561153', 'reminduser_view', '查看', 'view', '/system/reminduser/view', 'file-text', 4, 2, 2, 1, NULL, 0);
