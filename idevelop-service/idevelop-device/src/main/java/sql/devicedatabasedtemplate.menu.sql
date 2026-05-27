INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1803237738268794881', 1123598815738675201, 'devicedatabasedtemplate', '', 'menu', '/device/devicedatabasedtemplate', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1803237738268794882', '1803237738268794881', 'devicedatabasedtemplate_add', '新增', 'add', '/device/devicedatabasedtemplate/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1803237738268794883', '1803237738268794881', 'devicedatabasedtemplate_edit', '修改', 'edit', '/device/devicedatabasedtemplate/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1803237738268794884', '1803237738268794881', 'devicedatabasedtemplate_delete', '删除', 'delete', '/api/idevelop-device/devicedatabasedtemplate/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1803237738268794885', '1803237738268794881', 'devicedatabasedtemplate_view', '查看', 'view', '/device/devicedatabasedtemplate/view', 'file-text', 4, 2, 2, 1, NULL, 0);
