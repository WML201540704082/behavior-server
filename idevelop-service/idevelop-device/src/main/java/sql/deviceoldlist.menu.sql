INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1803233748970127361', 1123598815738675201, 'deviceoldlist', '', 'menu', '/device/deviceoldlist', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1803233748970127362', '1803233748970127361', 'deviceoldlist_add', '新增', 'add', '/device/deviceoldlist/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1803233748970127363', '1803233748970127361', 'deviceoldlist_edit', '修改', 'edit', '/device/deviceoldlist/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1803233748970127364', '1803233748970127361', 'deviceoldlist_delete', '删除', 'delete', '/api/idevelop-device/deviceoldlist/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1803233748970127365', '1803233748970127361', 'deviceoldlist_view', '查看', 'view', '/device/deviceoldlist/view', 'file-text', 4, 2, 2, 1, NULL, 0);
