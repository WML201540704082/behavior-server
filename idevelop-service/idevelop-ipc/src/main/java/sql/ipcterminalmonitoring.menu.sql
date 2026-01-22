INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1990329784539463682', 1123598815738675201, 'ipcterminalmonitoring', '', 'menu', '/ipc/ipcterminalmonitoring', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1990329784539463683', '1990329784539463682', 'ipcterminalmonitoring_add', '新增', 'add', '/ipc/ipcterminalmonitoring/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1990329784539463684', '1990329784539463682', 'ipcterminalmonitoring_edit', '修改', 'edit', '/ipc/ipcterminalmonitoring/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1990329784539463685', '1990329784539463682', 'ipcterminalmonitoring_delete', '删除', 'delete', '/api/idevelop-ipc/ipcterminalmonitoring/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1990329784539463686', '1990329784539463682', 'ipcterminalmonitoring_view', '查看', 'view', '/ipc/ipcterminalmonitoring/view', 'file-text', 4, 2, 2, 1, NULL, 0);
