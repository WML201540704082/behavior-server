INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765995509556641793', 1123598815738675201, 'safeaccessippool', '', 'menu', '/device/safeaccessippool', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765995509556641794', '1765995509556641793', 'safeaccessippool_add', '新增', 'add', '/device/safeaccessippool/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765995509556641795', '1765995509556641793', 'safeaccessippool_edit', '修改', 'edit', '/device/safeaccessippool/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765995509556641796', '1765995509556641793', 'safeaccessippool_delete', '删除', 'delete', '/api/idevelop-device/safeaccessippool/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1765995509556641797', '1765995509556641793', 'safeaccessippool_view', '查看', 'view', '/device/safeaccessippool/view', 'file-text', 4, 2, 2, 1, NULL, 0);
