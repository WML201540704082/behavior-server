INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765632119170367490', 1123598815738675201, 'safeaccessswitche', '', 'menu', '/device/safeaccessswitche', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765632119170367491', '1765632119170367490', 'safeaccessswitche_add', '新增', 'add', '/device/safeaccessswitche/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765632119170367492', '1765632119170367490', 'safeaccessswitche_edit', '修改', 'edit', '/device/safeaccessswitche/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765632119170367493', '1765632119170367490', 'safeaccessswitche_delete', '删除', 'delete', '/api/idevelop-device/safeaccessswitche/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765632119170367494', '1765632119170367490', 'safeaccessswitche_view', '查看', 'view', '/device/safeaccessswitche/view', 'file-text', 4, 2, 2, 1, NULL, 0);
