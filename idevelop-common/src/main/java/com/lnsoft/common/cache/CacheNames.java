
package com.lnsoft.common.cache;

/**
 * 缓存名
 *
 * @author guozhao
 */
public interface CacheNames {


	/**
	 * 国密sm2秘钥对 redis key
	 */
	String SM2_KEY = "idevelop:sm2_keys:";

	String NOTICE_ONE = "notice:one";

	String DICT_VALUE = "dict:value";
	String DICT_LIST = "dict:list";

	String CAPTCHA_KEY = "idevelop:auth::captcha:";
	String CAPTCHA_USER = "idevelop:user::captcha:";
	String GENERATE_NAMEBER_KEY = "idevelop:device:generate::number:";
	String GENERATE_CODE_KEY = "idevelop:device:generate::code:";
	String GENERATE_WAREHOUSE_KEY = "idevelop:device:generate::warehouse:";
	String LOCK = "_LOCK";

	// CMDB 其他信息 缓存Key
	String GENERATE_CI_OTHER_KEY = "idevelop:cmdb:ci::other:";
	String GENERATE_ATTR_BUSINESS_KEY = "idevelop:cmdb:attr::business:";
	String GENERATE_ATTR_IT_KEY = "idevelop:cmdb:attr::it:";

	String DEVICE_RECORD_ERP_NUMBER = "idevelop:device:record:erp::number:";
	// 生成申请单号
	String DEVICE_APPLY_NUMBER = "idevelop:device:apply::number:";
	// 生成出库单号
	String DEVICE_OUTBOUND_NUMBER = "idevelop:device:outbound::number:";
	// 生成投运单号
	String DEVICE_OPERATION_NUMBER = "idevelop:device:operation::number:";
	// 生成认证用户
	String OPERATION_AUTH_USER = "idevelop:device:auth::user:";
	// 生成隐患单号
	String CREATE_HIDDEN_NUMBER = "idevelop:hidden:generate::number:";

	// 定时任务
	String GENERATE_CMDB_TASK_USE_AGE = "idevelop:cmdb:task::useage:";
	String GENERATE_CMDB_TASK_ASSETS = "idevelop:cmdb:task::assets:";
	String GENERATE_CMDB_TASK_FIXEDVALUE = "idevelop:cmdb:task::fixedvalue:";
	String GENERATE_CMDB_TASK_UNITDEPT = "idevelop:cmdb:task::unitdept:";

	String GENERATE_CMDB_TASK_UNIT = "idevelop:cmdb:task::unit:";
	String GENERATE_CMDB_TASK_MAKER = "idevelop:cmdb:task::maker:";
	String GENERATE_CMDB_TASK_I6000 = "idevelop:i6000:task::refresh:";
	String GENERATE_CMDB_TASK_EXTRACTINFO = "idevelop:cmdb:task::extractinfo:";
	String GENERATE_CMDB_TASK_MIGRATEDATA = "idevelop:cmdb:task::migratedata:";
	String GENERATE_CMDB_TASK_MIGRATEDATAONE = "idevelop:cmdb:task::migratedataone:";
	String GENERATE_CMDB_TASK_ERP = "idevelop:cmdb:task::erp:";
	String GENERATE_CMDB_TASK_SUPPLEERPINFO = "idevelop:cmdb:task::suppleerpinfo:";
	String GENERATE_CMDB_I6000_TASK_ABUTMENT_LAST = "idevelop:cmdb:task::cmdbi6000abutmentLast:";
	String GENERATE_CMDB_I6000_TASK_ABUTMENT_NOW = "idevelop:cmdb:task::cmdbi6000abutmentNow:";

	/**
	 * 设备标准全称
	 */
	String DEVICE_FULL_NAME = "idevelop:device:full::name:";
	/**
	 * 设备名称
	 */
	String DEVICE_NAME = "idevelop:device::name:";
	/**
	 * cmdb字典 入库管理用
	 */
	String CMDB_DICT_STORAGE = "idevelop:device:storage:cmdb:dict:";


	/**
	 * cmdb字典 数据治理导入用
	 */
	String IMPORT_DICT_STORAGE = "idevelop:device:storage:import:dict:";

	String IMPORT_DICT_STORAGE_DEPT = "idevelop:device:storage:export:dept:";

	String IMPORT_DICT_STORAGE_BRAND = "idevelop:device:storage:import:dict:brand";
	String IMPORT_DICT_STORAGE_SERIES = "idevelop:device:storage:import:dict:series";
	String IMPORT_DICT_STORAGE_MODEL = "idevelop:device:storage:import:dict:model";

	// cmdb和i6000 枚举数据
	String GENERATE_DICT_CMDB_I6000 = "idevelop:device:cmdbi6000::dict:";

	//数据治理统计数据缓存
	String GOVERNANCE_SITUATION = "idevelop:device:governance";

	/**
	 *
	 */
	String DATA_TASK = "idevelop:data:task:";

	/**
	 * 获取T10101
	 */
	String CMDB_DICT_STORAGE_TXXXX = "idevelop:device:storage:data:txxxx:";

	String CHECK_TASK_END = "idevelop:check:device:task::end";

	String CHECK_TASK_NETWORK = "idevelop:check:device:task::network";

	String SEND_MSG = "idevelop:device:send::msg:";

	// 隐患管理相关
	String HIDDEN_PROBLEM = "idevelop:hidden:problem::code:";
	String HIDDEN_ORDER = "idevelop:hidden:order::code:";

}
