INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1909063538515464194', 1123598815738675201, 'standardmodellibrary', '', 'menu', '/system/standardmodellibrary', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1909063538515464195', '1909063538515464194', 'standardmodellibrary_add', '新增', 'add', '/system/standardmodellibrary/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1909063538515464196', '1909063538515464194', 'standardmodellibrary_edit', '修改', 'edit', '/system/standardmodellibrary/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1909063538515464197', '1909063538515464194', 'standardmodellibrary_delete', '删除', 'delete', '/api/idevelop-system/standardmodellibrary/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1909063538515464198', '1909063538515464194', 'standardmodellibrary_view', '查看', 'view', '/system/standardmodellibrary/view', 'file-text', 4, 2, 2, 1, NULL, 0);
