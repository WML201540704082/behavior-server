ALTER TABLE `idevelop`.`idevelop_resource_room`
    ADD COLUMN `i6000_uuid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联I6000机房uuid' AFTER `is_i6000`,
    ADD COLUMN `i6000_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联I6000机房name' AFTER `i6000_uuid`;

ALTER TABLE `idevelop`.`idevelop_warehouse`
    ADD COLUMN `i6000_uuid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联I6000仓库uuid' AFTER `is_i6000`,
    ADD COLUMN `i6000_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联I6000仓库name' AFTER `i6000_uuid`;


ALTER TABLE `idevelop`.`idevelop_warning`
    ADD COLUMN `warning_is_main_id` varchar(50) NULL COMMENT '主告警ID; warning_is_main=1时必填.' AFTER `is_deleted`;
