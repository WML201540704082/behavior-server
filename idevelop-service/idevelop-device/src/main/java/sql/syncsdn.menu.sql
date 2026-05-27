INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('2016067702806523906', 1123598815738675201, 'syncsdn', '', 'menu', '/device/syncsdn', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('2016067702806523907', '2016067702806523906', 'syncsdn_add', '新增', 'add', '/device/syncsdn/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('2016067702806523908', '2016067702806523906', 'syncsdn_edit', '修改', 'edit', '/device/syncsdn/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('2016067702806523909', '2016067702806523906', 'syncsdn_delete', '删除', 'delete', '/api/idevelop-device/syncsdn/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('2016067702806523910', '2016067702806523906', 'syncsdn_view', '查看', 'view', '/device/syncsdn/view', 'file-text', 4, 2, 2, 1, NULL, 0);
