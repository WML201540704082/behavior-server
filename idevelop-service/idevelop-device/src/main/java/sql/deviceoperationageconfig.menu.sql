INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1772461504132882433', 1123598815738675201, 'deviceoperationageconfig', '', 'menu', '/device/deviceoperationageconfig', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1772461504132882434', '1772461504132882433', 'deviceoperationageconfig_add', '新增', 'add', '/device/deviceoperationageconfig/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1772461504132882435', '1772461504132882433', 'deviceoperationageconfig_edit', '修改', 'edit', '/device/deviceoperationageconfig/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1772461504132882436', '1772461504132882433', 'deviceoperationageconfig_delete', '删除', 'delete', '/api/idevelop-device/deviceoperationageconfig/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1772461504132882437', '1772461504132882433', 'deviceoperationageconfig_view', '查看', 'view', '/device/deviceoperationageconfig/view', 'file-text', 4, 2, 2, 1, NULL, 0);
