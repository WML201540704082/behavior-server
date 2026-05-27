INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1803240580446961666', 1123598815738675201, 'deviceoldmodelconfig', '', 'menu', '/device/deviceoldmodelconfig', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1803240580446961667', '1803240580446961666', 'deviceoldmodelconfig_add', '新增', 'add', '/device/deviceoldmodelconfig/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1803240580446961668', '1803240580446961666', 'deviceoldmodelconfig_edit', '修改', 'edit', '/device/deviceoldmodelconfig/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1803240580446961669', '1803240580446961666', 'deviceoldmodelconfig_delete', '删除', 'delete', '/api/idevelop-device/deviceoldmodelconfig/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1803240580446961670', '1803240580446961666', 'deviceoldmodelconfig_view', '查看', 'view', '/device/deviceoldmodelconfig/view', 'file-text', 4, 2, 2, 1, NULL, 0);
