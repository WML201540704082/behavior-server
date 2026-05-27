INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765269131169239041', 1123598815738675201, 'deviceapplydetail', '', 'menu', '/device/deviceapplydetail', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765269131169239042', '1765269131169239041', 'deviceapplydetail_add', '新增', 'add', '/device/deviceapplydetail/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765269131169239043', '1765269131169239041', 'deviceapplydetail_edit', '修改', 'edit', '/device/deviceapplydetail/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765269131169239044', '1765269131169239041', 'deviceapplydetail_delete', '删除', 'delete', '/api/idevelop-device/deviceapplydetail/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765269131169239045', '1765269131169239041', 'deviceapplydetail_view', '查看', 'view', '/device/deviceapplydetail/view', 'file-text', 4, 2, 2, 1, NULL, 0);
