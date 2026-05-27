INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1802876458514227201', 1123598815738675201, 'i6000externaladd', '', 'menu', '/device/i6000externaladd', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1802876458514227202', '1802876458514227201', 'i6000externaladd_add', '新增', 'add', '/device/i6000externaladd/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1802876458514227203', '1802876458514227201', 'i6000externaladd_edit', '修改', 'edit', '/device/i6000externaladd/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1802876458514227204', '1802876458514227201', 'i6000externaladd_delete', '删除', 'delete', '/api/idevelop-device/i6000externaladd/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1802876458514227205', '1802876458514227201', 'i6000externaladd_view', '查看', 'view', '/device/i6000externaladd/view', 'file-text', 4, 2, 2, 1, NULL, 0);
