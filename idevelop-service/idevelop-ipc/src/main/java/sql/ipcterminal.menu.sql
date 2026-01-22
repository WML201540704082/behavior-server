INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1990329738590863362', 1123598815738675201, 'ipcterminal', '', 'menu', '/ipc/ipcterminal', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1990329738590863363', '1990329738590863362', 'ipcterminal_add', '新增', 'add', '/ipc/ipcterminal/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1990329738590863364', '1990329738590863362', 'ipcterminal_edit', '修改', 'edit', '/ipc/ipcterminal/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1990329738590863365', '1990329738590863362', 'ipcterminal_delete', '删除', 'delete', '/api/idevelop-ipc/ipcterminal/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1990329738590863366', '1990329738590863362', 'ipcterminal_view', '查看', 'view', '/ipc/ipcterminal/view', 'file-text', 4, 2, 2, 1, NULL, 0);
