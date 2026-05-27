INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1775115214051196929', 1123598815738675201, 'h3capplication', '', 'menu', '/data/h3capplication', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1775115214051196930', '1775115214051196929', 'h3capplication_add', '新增', 'add', '/data/h3capplication/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1775115214051196931', '1775115214051196929', 'h3capplication_edit', '修改', 'edit', '/data/h3capplication/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1775115214051196932', '1775115214051196929', 'h3capplication_delete', '删除', 'delete', '/api/idevelop-data/h3capplication/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1775115214051196933', '1775115214051196929', 'h3capplication_view', '查看', 'view', '/data/h3capplication/view', 'file-text', 4, 2, 2, 1, NULL, 0);
