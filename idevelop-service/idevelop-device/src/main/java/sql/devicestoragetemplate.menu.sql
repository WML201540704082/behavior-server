INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1760563013841133570', 1123598815738675201, 'devicestoragetemplate', '', 'menu', '/device/devicestoragetemplate', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1760563013841133571', '1760563013841133570', 'devicestoragetemplate_add', '新增', 'add', '/device/devicestoragetemplate/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1760563013841133572', '1760563013841133570', 'devicestoragetemplate_edit', '修改', 'edit', '/device/devicestoragetemplate/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1760563013841133573', '1760563013841133570', 'devicestoragetemplate_delete', '删除', 'delete', '/api/idevelop-device/devicestoragetemplate/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1760563013841133574', '1760563013841133570', 'devicestoragetemplate_view', '查看', 'view', '/device/devicestoragetemplate/view', 'file-text', 4, 2, 2, 1, NULL, 0);
