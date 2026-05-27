INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765269634661879809', 1123598815738675201, 'deviceoperationdetail', '', 'menu', '/device/deviceoperationdetail', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765269634661879810', '1765269634661879809', 'deviceoperationdetail_add', '新增', 'add', '/device/deviceoperationdetail/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765269634661879811', '1765269634661879809', 'deviceoperationdetail_edit', '修改', 'edit', '/device/deviceoperationdetail/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765269634661879812', '1765269634661879809', 'deviceoperationdetail_delete', '删除', 'delete', '/api/idevelop-device/deviceoperationdetail/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765269634661879813', '1765269634661879809', 'deviceoperationdetail_view', '查看', 'view', '/device/deviceoperationdetail/view', 'file-text', 4, 2, 2, 1, NULL, 0);
