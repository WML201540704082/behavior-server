INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1819302149658001410', 1123598815738675201, 'i6000enum', '', 'menu', '/device/i6000enum', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1819302149658001411', '1819302149658001410', 'i6000enum_add', '新增', 'add', '/device/i6000enum/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1819302149658001412', '1819302149658001410', 'i6000enum_edit', '修改', 'edit', '/device/i6000enum/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1819302149658001413', '1819302149658001410', 'i6000enum_delete', '删除', 'delete', '/api/idevelop-device/i6000enum/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1819302149658001414', '1819302149658001410', 'i6000enum_view', '查看', 'view', '/device/i6000enum/view', 'file-text', 4, 2, 2, 1, NULL, 0);
