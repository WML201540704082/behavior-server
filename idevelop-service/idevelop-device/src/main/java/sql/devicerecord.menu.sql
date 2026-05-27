INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1755117405464023042', 1123598815738675201, 'devicerecord', '', 'menu', '/idevelop_device/devicerecord', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1755117405464023043', '1755117405464023042', 'devicerecord_add', '新增', 'add', '/idevelop_device/devicerecord/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1755117405464023044', '1755117405464023042', 'devicerecord_edit', '修改', 'edit', '/idevelop_device/devicerecord/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1755117405464023045', '1755117405464023042', 'devicerecord_delete', '删除', 'delete', '/api/idevelop_device/devicerecord/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `idevelop_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1755117405464023046', '1755117405464023042', 'devicerecord_view', '查看', 'view', '/idevelop_device/devicerecord/view', 'file-text', 4, 2, 2, 1, NULL, 0);
