INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1766382988484624385', 1123598815738675201, 'safeaccessuseraccess', '', 'menu', '/device/safeaccessuseraccess', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1766382988484624386', '1766382988484624385', 'safeaccessuseraccess_add', '新增', 'add', '/device/safeaccessuseraccess/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1766382988484624387', '1766382988484624385', 'safeaccessuseraccess_edit', '修改', 'edit', '/device/safeaccessuseraccess/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1766382988484624388', '1766382988484624385', 'safeaccessuseraccess_delete', '删除', 'delete', '/api/idevelop-device/safeaccessuseraccess/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1766382988484624389', '1766382988484624385', 'safeaccessuseraccess_view', '查看', 'view', '/device/safeaccessuseraccess/view', 'file-text', 4, 2, 2, 1, NULL, 0);
