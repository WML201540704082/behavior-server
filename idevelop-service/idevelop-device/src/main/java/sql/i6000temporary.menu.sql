INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1781526348108181505', 1123598815738675201, 'i6000temporary', '', 'menu', '/device/i6000temporary', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1781526348108181506', '1781526348108181505', 'i6000temporary_add', '新增', 'add', '/device/i6000temporary/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1781526348108181507', '1781526348108181505', 'i6000temporary_edit', '修改', 'edit', '/device/i6000temporary/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1781526348108181508', '1781526348108181505', 'i6000temporary_delete', '删除', 'delete', '/api/idevelop-device/i6000temporary/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1781526348108181509', '1781526348108181505', 'i6000temporary_view', '查看', 'view', '/device/i6000temporary/view', 'file-text', 4, 2, 2, 1, NULL, 0);
