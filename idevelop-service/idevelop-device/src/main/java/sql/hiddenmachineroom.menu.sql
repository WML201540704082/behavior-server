INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('2020735798596157442', 1123598815738675201, 'hiddenmachineroom', '', 'menu', '/hidden/hiddenmachineroom', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('2020735798596157443', '2020735798596157442', 'hiddenmachineroom_add', '新增', 'add', '/hidden/hiddenmachineroom/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('2020735798596157444', '2020735798596157442', 'hiddenmachineroom_edit', '修改', 'edit', '/hidden/hiddenmachineroom/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('2020735798596157445', '2020735798596157442', 'hiddenmachineroom_delete', '删除', 'delete', '/api/idevelop-hidden/hiddenmachineroom/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('2020735798596157446', '2020735798596157442', 'hiddenmachineroom_view', '查看', 'view', '/hidden/hiddenmachineroom/view', 'file-text', 4, 2, 2, 1, NULL, 0);
