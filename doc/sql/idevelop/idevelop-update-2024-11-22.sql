-- Date: 2024-11-20 已完成
-- SQL语句:

UPDATE `idevelop`.`idevelop_dict` SET `parent_id` = 0, `code` = 'control_question_type', `dict_key` = '-1', `dict_value` = '问题类型', `sort` = 100, `remark` = '', `is_deleted` = 0 WHERE `id` = 1829355617930932225;
UPDATE `idevelop`.`idevelop_dict` SET `parent_id` = 1829355617930932225, `code` = 'control_question_type', `dict_key` = '0', `dict_value` = '系统问题', `sort` = 0, `remark` = '', `is_deleted` = 1 WHERE `id` = 1829355669613146113;
UPDATE `idevelop`.`idevelop_dict` SET `parent_id` = 1829355617930932225, `code` = 'control_question_type', `dict_key` = '1', `dict_value` = '操作系统问题', `sort` = 1, `remark` = '', `is_deleted` = 0 WHERE `id` = 1829355732959719426;
UPDATE `idevelop`.`idevelop_dict` SET `parent_id` = 1829355617930932225, `code` = 'control_question_type', `dict_key` = '2', `dict_value` = '办公软件问题', `sort` = 2, `remark` = '', `is_deleted` = 0 WHERE `id` = 1829355830338875393;
UPDATE `idevelop`.`idevelop_dict` SET `parent_id` = 1829355617930932225, `code` = 'control_question_type', `dict_key` = '3', `dict_value` = '用户体验问题', `sort` = 3, `remark` = '', `is_deleted` = 0 WHERE `id` = 1829355921623707649;
UPDATE `idevelop`.`idevelop_dict` SET `parent_id` = 1829355617930932225, `code` = 'control_question_type', `dict_key` = '4', `dict_value` = '终端性能问题', `sort` = 4, `remark` = '', `is_deleted` = 0 WHERE `id` = 1829355997037293570;
UPDATE `idevelop`.`idevelop_dict` SET `parent_id` = 1829355617930932225, `code` = 'control_question_type', `dict_key` = '5', `dict_value` = '其他', `sort` = 5, `remark` = '', `is_deleted` = 0 WHERE `id` = 1829356041790517250;



INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859767563086712834, 0, 'control_question_device_type', '-1', '终端类型', 104, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859767618032095233, 1859767563086712834, 'control_question_device_type', '1', '笔记本', 1, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859767671341699074, 1859767563086712834, 'control_question_device_type', '2', '台式机', 2, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859771885698666498, 0, 'control_question_brand', '-1', '问题提报-品牌', 105, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859772010244329473, 1859771885698666498, 'control_question_brand', '0', '联想', 1, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859772074383626241, 1859771885698666498, 'control_question_brand', '1', '南瑞', 2, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859772136547405826, 1859771885698666498, 'control_question_brand', '2', '华为', 3, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859772175818674177, 1859771885698666498, 'control_question_brand', '3', '浪潮', 4, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859772223172366338, 1859771885698666498, 'control_question_brand', '4', '国电通', 5, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859772267518742530, 1859771885698666498, 'control_question_brand', '5', '鲁软', 6, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859772656779513857, 0, 'control_question_series', '-1', '问题提报-系列', 106, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859773078835548162, 1859772656779513857, 'control_question_series', '0', '开天', 1, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859773159273910273, 1859772656779513857, 'control_question_series', '1', 'nDestop', 2, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859773207160279042, 1859772656779513857, 'control_question_series', '2', '擎云', 3, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859773288756269057, 1859772656779513857, 'control_question_series', '3', '英政', 4, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859773337242423297, 1859772656779513857, 'control_question_series', '4', '磐石', 5, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859773376928927745, 1859772656779513857, 'control_question_series', '5', '火石', 6, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859773581791318017, 0, 'control_question_model', '-1', '问题提报-型号', 107, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859773677853462530, 1859773581791318017, 'control_question_model', '0', 'M99h Glt', 1, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859773747793481729, 1859773581791318017, 'control_question_model', '1', 'HPCH 201', 2, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859773804831821826, 1859773581791318017, 'control_question_model', '2', 'HG 201', 3, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859773898297692162, 1859773581791318017, 'control_question_model', '3', 'B230', 4, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859773958519508993, 1859773581791318017, 'control_question_model', '4', 'B240', 5, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859774023338283010, 1859773581791318017, 'control_question_model', '5', 'L410', 6, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859774094838583297, 1859773581791318017, 'control_question_model', '6', 'L420', 7, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859774156708761601, 1859773581791318017, 'control_question_model', '7', 'CE520F', 8, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859774246362009601, 1859773581791318017, 'control_question_model', '8', 'RC712Z', 9, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859774364658159618, 1859773581791318017, 'control_question_model', '9', 'TN140A2-SGH', 10, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859774452268781569, 1859773581791318017, 'control_question_model', '10', 'TN140A2-SGM', 11, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859774529825656833, 1859773581791318017, 'control_question_model', '11', 'TN140A2-SGL', 12, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859774743227650050, 1859773581791318017, 'control_question_model', '12', 'TD085A2-SGH', 13, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859774893459230721, 1859773581791318017, 'control_question_model', '13', 'TD085A2-SGM', 14, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859774953823653889, 1859773581791318017, 'control_question_model', '14', 'TD085A2-SGL', 15, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859775048312934401, 1859773581791318017, 'control_question_model', '15', 'TD085A3-SGH', 16, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859775134606544898, 1859773581791318017, 'control_question_model', '16', 'TD085A3-SGM', 17, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859775181465309186, 1859773581791318017, 'control_question_model', '17', 'TD085A3-SGL', 18, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859775880810975233, 1859773581791318017, 'control_question_model', '18', 'TD085A5-SGH', 19, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859775957533184001, 1859773581791318017, 'control_question_model', '19', 'TD085A5-SGM', 20, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859776026848251906, 1859773581791318017, 'control_question_model', '20', 'TD085A5-SGL', 21, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859886520204513282, 0, 'control_question_osType', '-1', '问题提报-操作系统版本', 108, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859886625519292417, 1859886520204513282, 'control_question_osType', '0', '统信系统', 1, '', 0);
INSERT INTO `idevelop`.`idevelop_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`) VALUES (1859886684449263618, 1859886520204513282, 'control_question_osType', '1', '银河麒麟', 2, '', 0);

