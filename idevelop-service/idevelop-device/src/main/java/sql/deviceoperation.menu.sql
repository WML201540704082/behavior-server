INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765269634129203201', 1123598815738675201, 'deviceoperation', '', 'menu', '/device/deviceoperation', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765269634129203202', '1765269634129203201', 'deviceoperation_add', '新增', 'add', '/device/deviceoperation/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765269634129203203', '1765269634129203201', 'deviceoperation_edit', '修改', 'edit', '/device/deviceoperation/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765269634129203204', '1765269634129203201', 'deviceoperation_delete', '删除', 'delete', '/api/idevelop-device/deviceoperation/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765269634129203205', '1765269634129203201', 'deviceoperation_view', '查看', 'view', '/device/deviceoperation/view', 'file-text', 4, 2, 2, 1, NULL, 0);
