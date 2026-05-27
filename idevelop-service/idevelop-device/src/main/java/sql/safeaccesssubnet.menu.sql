INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765989999944073217', 1123598815738675201, 'safeaccesssubnet', '', 'menu', '/device/safeaccesssubnet', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765989999944073218', '1765989999944073217', 'safeaccesssubnet_add', '新增', 'add', '/device/safeaccesssubnet/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765989999944073219', '1765989999944073217', 'safeaccesssubnet_edit', '修改', 'edit', '/device/safeaccesssubnet/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765989999944073220', '1765989999944073217', 'safeaccesssubnet_delete', '删除', 'delete', '/api/idevelop-device/safeaccesssubnet/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765989999944073221', '1765989999944073217', 'safeaccesssubnet_view', '查看', 'view', '/device/safeaccesssubnet/view', 'file-text', 4, 2, 2, 1, NULL, 0);
