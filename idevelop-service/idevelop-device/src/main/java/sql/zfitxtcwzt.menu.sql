INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1817802987616980994', 1123598815738675201, 'zfitxtcwzt', '', 'menu', '/device/zfitxtcwzt', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1817802987616980995', '1817802987616980994', 'zfitxtcwzt_add', '新增', 'add', '/device/zfitxtcwzt/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1817802987616980996', '1817802987616980994', 'zfitxtcwzt_edit', '修改', 'edit', '/device/zfitxtcwzt/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1817802987616980997', '1817802987616980994', 'zfitxtcwzt_delete', '删除', 'delete', '/api/idevelop-device/zfitxtcwzt/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1817802987616980998', '1817802987616980994', 'zfitxtcwzt_view', '查看', 'view', '/device/zfitxtcwzt/view', 'file-text', 4, 2, 2, 1, NULL, 0);
