
ALTER TABLE `idevelop`.`idevelop_device_change_list`
ADD COLUMN `cpu_brand_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'cpu品牌编码' AFTER `is_deleted`,
ADD COLUMN `cpu_brand` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'cpu品牌' AFTER `cpu_brand_code`,
ADD COLUMN `cpu_model` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'cpu型号' AFTER `cpu_brand`;
