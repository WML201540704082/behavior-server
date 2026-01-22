-- Date: 2024-07-26 已完成
-- SQL语句:
create table `idevelop_verify_config_240726` like `idevelop_verify_config`;

INSERT into `idevelop_verify_config_240726`  SELECT *
from `idevelop_verify_config`;

update `idevelop_verify_config`
set verify_value = '0'
WHERE verify_value = '1';


INSERT INTO `idevelop_verify_config` (`verify_key`, `verify_value`, `verify_type`, `warning_type`, `warning_level`,
                                      `main_warning_sort`, `is_deleted`, `warning_reason`, `warning_detail`)
VALUES ('brandCode', '0', '5', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config` (`verify_key`, `verify_value`, `verify_type`, `warning_type`, `warning_level`,
                                      `main_warning_sort`, `is_deleted`, `warning_reason`, `warning_detail`)
VALUES ('seriesCode', '0', '5', NULL, NULL, NULL, '0', NULL, NULL);
INSERT INTO `idevelop_verify_config` (`verify_key`, `verify_value`, `verify_type`, `warning_type`, `warning_level`,
                                      `main_warning_sort`, `is_deleted`, `warning_reason`, `warning_detail`)
VALUES ('deviceModelCode', '0', '5', NULL, NULL, NULL, '0', NULL, NULL);


