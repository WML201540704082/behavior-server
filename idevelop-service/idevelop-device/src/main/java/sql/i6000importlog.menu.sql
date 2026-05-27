INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1934086461189341185', 1123598815738675201, 'i6000importlog', '', 'menu', '/device/i6000importlog', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1934086461189341186', '1934086461189341185', 'i6000importlog_add', '新增', 'add', '/device/i6000importlog/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1934086461189341187', '1934086461189341185', 'i6000importlog_edit', '修改', 'edit', '/device/i6000importlog/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1934086461189341188', '1934086461189341185', 'i6000importlog_delete', '删除', 'delete', '/api/idevelop-device/i6000importlog/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1934086461189341189', '1934086461189341185', 'i6000importlog_view', '查看', 'view', '/device/i6000importlog/view', 'file-text', 4, 2, 2, 1, NULL, 0);
