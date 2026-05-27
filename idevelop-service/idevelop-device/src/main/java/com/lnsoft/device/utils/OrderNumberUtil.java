package com.lnsoft.device.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lnsoft.common.cache.CacheNames;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.CollectionUtil;
import com.lnsoft.core.tool.utils.RedisUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.api.asset.entity.GenerateCode;
import com.lnsoft.device.api.asset.service.IGenerateCodeService;
import com.lnsoft.device.api.cmdb.wrapper.CmdbCiAttrWrapper;
import com.lnsoft.device.api.res.constants.Constants;
import com.lnsoft.common.enums.device.WorkOrderTypeEnum;
import com.lnsoft.device.constant.CmdbCientityConstant;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.device.props.CmdbDictProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: xuel
 * @CreateTime: 2024/2/19 16:13
 * @Description: 获取工单编号和设备编码 OrderNumberUtils
 */
@Component
@AllArgsConstructor
@Slf4j
public class OrderNumberUtil {

	private RedisUtil redisUtil;
	private IGenerateCodeService iGenerateCodeService;

	public static final String YYYY_MM_DD = "yyyyMMdd";
	public static final String WAREHOUSE = "CK";
	public static final String ROOM = "JF";
	public static final String FORMAT_04 = "%04d";
	public static final String FORMAT_03 = "%03d";
	public static final String DEVICE_AUTH_USER = "%05d";
	public static final String REAL_ID = "%018d";
	private static final Integer ORDER_NO_LENGTH = 6;
	private static final String PAD_STR = "0";

	private static Map<Object, Object> DEVICE_TYPE = new HashMap<>();

	private CmdbDictProperties cmdbDictProperties;
	private CmdbCientityProperties ciEntityProperties;

	/**
	 * 获取工单编号
	 * 规则: 工单类型 省 市 县 yyyyMMdd xxxxxx
	 * JD 37 01 01 20240219 000001
	 *
	 * @param typeEnum
	 * @return
	 */
	public String generateNumber(WorkOrderTypeEnum typeEnum) {

		String regionCode = getRegionCode();

		String format = DateTimeFormatter.ofPattern(YYYY_MM_DD).format(LocalDateTime.now());
		String value = typeEnum.getValue();
		String redisKey = CacheNames.GENERATE_NAMEBER_KEY + value + regionCode + format;
		String lockKey = CacheNames.GENERATE_NAMEBER_KEY + value + regionCode + format + CacheNames.LOCK;
		try {
			// 获取锁
			if (!acquireLock(lockKey)) {
				waitBeforeRetry();
				return generateNumber(typeEnum);
			}
			return value + format + regionCode + getIncrement(redisKey, value);
		} finally {
			// 释放锁
			releaseLock(lockKey);
		}
	}


	/**
	 * 获取工单编号 应用于其他模块里面
	 * 规则: 工单类型 省 市 县 yyyyMMdd xxxxxx
	 * JD 37 01 01 20240219 000001
	 *
	 * @param typeEnum
	 * @return
	 */
	public String generateNumber(WorkOrderTypeEnum typeEnum, String region) {

		String regionCode = getRegionCode(region);

		String format = DateTimeFormatter.ofPattern(YYYY_MM_DD).format(LocalDateTime.now());
		String value = typeEnum.getValue();
		String redisKey = CacheNames.GENERATE_NAMEBER_KEY + value + regionCode + format;
		String lockKey = CacheNames.GENERATE_NAMEBER_KEY + value + regionCode + format + CacheNames.LOCK;
		try {
			// 获取锁
			if (!acquireLock(lockKey)) {
				waitBeforeRetry();
				return generateNumber(typeEnum);
			}
			return value + format + regionCode + getIncrement(redisKey, value);
		} finally {
			// 释放锁
			releaseLock(lockKey);
		}
	}

	/**
	 * 定时任务获取工单编号
	 * 规则: 工单类型 省 市 县 yyyyMMdd xxxxxx
	 * JD 37 01 01 20240219 000001
	 *
	 * @param typeEnum
	 * @return
	 */
	public String generateNumberData(WorkOrderTypeEnum typeEnum, String regionCode) {
		String format = DateTimeFormatter.ofPattern(YYYY_MM_DD).format(LocalDateTime.now());
		String value = typeEnum.getValue();
		String redisKey = CacheNames.GENERATE_NAMEBER_KEY + value + regionCode + format;
		String lockKey = CacheNames.GENERATE_NAMEBER_KEY + value + regionCode + format + CacheNames.LOCK;
		try {
			// 获取锁
			if (!acquireLock(lockKey)) {
				waitBeforeRetry();
				return generateNumber(typeEnum);
			}
			return value + format + regionCode + getIncrement(redisKey, typeEnum.getValue());
		} finally {
			// 释放锁
			releaseLock(lockKey);
		}
	}

	/**
	 * 定时任务生成工单编号
	 *
	 * @param typeEnum   工单类型
	 * @param regionCode 区域编码
	 * @return String
	 */
	public String generateNumberByRegionCode(WorkOrderTypeEnum typeEnum, String regionCode) {
		String format = DateTimeFormatter.ofPattern(YYYY_MM_DD).format(LocalDateTime.now());
		String value = typeEnum.getValue();
		String redisKey = CacheNames.GENERATE_NAMEBER_KEY + value + regionCode + format;
		String lockKey = CacheNames.GENERATE_NAMEBER_KEY + value + regionCode + format + CacheNames.LOCK;
		try {
			// 获取锁
			if (!acquireLock(lockKey)) {
				waitBeforeRetry();
				return generateNumber(typeEnum);
			}
			return value + format + regionCode + getIncrement(redisKey, typeEnum.getValue());
		} finally {
			// 释放锁
			releaseLock(lockKey);
		}
	}


	/**
	 * 获取设备编码
	 * 规则: 省 市 县 类型 yyyyMMdd xxxxxx
	 * 37 01 01 XX xx 20240219 000001
	 *
	 * @param type 设备类型
	 * @return
	 */
	public String generateCode(String type) {
		String typeStr;
		Object typeObj = DEVICE_TYPE.get(type);
		if (Objects.isNull(typeObj)) {
			DEVICE_TYPE = cmdbDictProperties.getDictMapByCiIdSpecial(cmdbDictProperties.getDeviceType());
			typeObj = DEVICE_TYPE.get(type);
		}
		if (Objects.isNull(typeObj)) {
			typeStr = type;
		} else {
			typeStr = String.valueOf(typeObj);
		}

		String regionCode = getRegionCode();
		String nowStr = DateTimeFormatter.ofPattern(YYYY_MM_DD).format(LocalDateTime.now());
		String redisKey = CacheNames.GENERATE_CODE_KEY + regionCode + typeStr + nowStr;
		String lockKey = CacheNames.GENERATE_CODE_KEY + regionCode + typeStr + nowStr + CacheNames.LOCK;
		try {
			// 获取锁
			if (!acquireLock(lockKey)) {
				waitBeforeRetry();
				return generateCode(type);
			}
			return regionCode + typeStr + nowStr + getIncrement(redisKey, WorkOrderTypeEnum.GC.getValue());
		} finally {
			// 释放锁
			releaseLock(lockKey);
		}
	}

	/**
	 * 获取设备编码
	 * 规则: 省 市 县 类型 yyyyMMdd xxxxxx
	 * 37 01 01 XX xx 20240219 000001
	 *
	 * @param type 设备类型
	 * @return
	 */
	public String generateCodeData(String type, IdevelopUser user) {
		String typeStr;
		Object typeObj = DEVICE_TYPE.get(type);
		if (Objects.isNull(typeObj)) {
			DEVICE_TYPE = cmdbDictProperties.getDictMapByCiIdSpecial(cmdbDictProperties.getDeviceType());
			typeObj = DEVICE_TYPE.get(type);
		}
		if (Objects.isNull(typeObj)) {
			typeStr = type;
		} else {
			typeStr = String.valueOf(typeObj);
		}
		String regionCode = user.getRegionCode().length() <= 2 ? user.getRegionCode() + "0000" :
			user.getRegionCode().length() > 2 && user.getRegionCode().length() <= 4 ? user.getRegionCode() + "00" : user.getRegionCode();
		String nowStr = DateTimeFormatter.ofPattern(YYYY_MM_DD).format(LocalDateTime.now());
		String redisKey = CacheNames.GENERATE_CODE_KEY + regionCode + typeStr + nowStr;
		String lockKey = CacheNames.GENERATE_CODE_KEY + regionCode + typeStr + nowStr + CacheNames.LOCK;
		try {
			// 获取锁
			if (!acquireLock(lockKey)) {
				waitBeforeRetry();
				return generateCode(type);
			}
			return regionCode + typeStr + nowStr + getIncrement(redisKey, WorkOrderTypeEnum.GC.getValue());
		} finally {
			// 释放锁
			releaseLock(lockKey);
		}
	}

	/**
	 * 获取设备编码
	 * 规则: 省 市 县 类型 yyyyMMdd xxxxxx
	 * 37 01 01 XX xx 20240219 000001
	 *
	 * @param type 设备类型
	 * @return
	 */
	public String generateCode(String type, String regionCode) {
		String typeStr;
		Object typeObj = DEVICE_TYPE.get(type);
		if (Objects.isNull(typeObj)) {
			DEVICE_TYPE = cmdbDictProperties.getDictMapByCiIdSpecial(cmdbDictProperties.getDeviceType());
			typeObj = DEVICE_TYPE.get(type);
		}
		if (Objects.isNull(typeObj)) {
			typeStr = type;
		} else {
			typeStr = String.valueOf(typeObj);
		}

		regionCode = getRegionCode(regionCode);
		String nowStr = DateTimeFormatter.ofPattern(YYYY_MM_DD).format(LocalDateTime.now());
		String redisKey = CacheNames.GENERATE_CODE_KEY + regionCode + typeStr + nowStr;
		String lockKey = CacheNames.GENERATE_CODE_KEY + regionCode + typeStr + nowStr + CacheNames.LOCK;
		try {
			// 获取锁
			if (!acquireLock(lockKey)) {
				waitBeforeRetry();
				return generateCode(type);
			}
			return regionCode + typeStr + nowStr + getIncrement(redisKey, WorkOrderTypeEnum.GC.getValue());
		} finally {
			// 释放锁
			releaseLock(lockKey);
		}
	}

	/**
	 * 获取仓库编码
	 * 规则: CK yyyyMMdd xxxx
	 * CK20240306 0001
	 *
	 * @return
	 */
	public String generateWarehouse() {

		String regionCode = getRegionCode();

		String format = DateTimeFormatter.ofPattern(YYYY_MM_DD).format(LocalDateTime.now());
		String redisKey = CacheNames.GENERATE_WAREHOUSE_KEY + regionCode + format;
		String lockKey = CacheNames.GENERATE_WAREHOUSE_KEY + regionCode + format + CacheNames.LOCK;

		try {
			// 获取锁
			if (!acquireLock(lockKey)) {
				waitBeforeRetry();
				return generateWarehouse();
			}
			return WAREHOUSE + regionCode + format + getIncrement(redisKey, WorkOrderTypeEnum.WH.getValue());
		} finally {
			// 释放锁
			releaseLock(lockKey);
		}

	}

	/**
	 * 获取机房编码
	 * 规则: JF yyyyMMdd xxxx
	 * JF20240306 0001
	 *
	 * @return
	 */
	public String generateRoom() {

		String regionCode = getRegionCode();

		String format = DateTimeFormatter.ofPattern(YYYY_MM_DD).format(LocalDateTime.now());
		String redisKey = CacheNames.GENERATE_WAREHOUSE_KEY + regionCode + format;
		String lockKey = CacheNames.GENERATE_WAREHOUSE_KEY + regionCode + format + CacheNames.LOCK;

		try {
			// 获取锁
			if (!acquireLock(lockKey)) {
				waitBeforeRetry();
				return generateWarehouse();
			}
			return ROOM + regionCode + format + getIncrement(redisKey, WorkOrderTypeEnum.RM.getValue());
		} finally {
			// 释放锁
			releaseLock(lockKey);
		}

	}

	/**
	 * 根据地市和日期生成工单编号方法统一调用 例如 SQ0102202403060001
	 * orderType 工单前缀 例如 SQ
	 * 请求缓存 从CacheNames 中取 例如 idevelop:device:apply::number:
	 *
	 * @return String 根据地市生成的工单编号
	 */
	public String generateOrderNumber(String orderType, String orderRedis) {
		String format = DateTimeFormatter.ofPattern(YYYY_MM_DD).format(LocalDateTime.now());
		IdevelopUser user = SecureUtil.getUser();
		String region = StringUtils.rightPad(user.getRegionCode(), ORDER_NO_LENGTH, PAD_STR);
		String userRegion = region.substring(2);
		String redisKey = orderRedis + format + userRegion;
		String lockKey = orderRedis + format + userRegion + CacheNames.LOCK;
		try {
			// 获取锁
			if (!acquireLock(lockKey)) {
				waitBeforeRetry();
				return generateOrderNumber(orderType, orderRedis);
			}
			return orderType + format + userRegion + getIncrement(redisKey, orderType);
		} finally {
			// 释放锁
			releaseLock(lockKey);
		}
	}

	public String generateOrderNumberData(String orderType, String orderRedis, IdevelopUser user) {
		String format = DateTimeFormatter.ofPattern(YYYY_MM_DD).format(LocalDateTime.now());
		String region = StringUtils.rightPad(user.getRegionCode(), ORDER_NO_LENGTH, PAD_STR);
		String userRegion = region.substring(2);
		String redisKey = orderRedis + format + userRegion;
		String lockKey = orderRedis + format + userRegion + CacheNames.LOCK;
		try {
			// 获取锁
			if (!acquireLock(lockKey)) {
				waitBeforeRetry();
				return generateOrderNumber(orderType, orderRedis);
			}
			return orderType + format + userRegion + getIncrement(redisKey, orderType);
		} finally {
			// 释放锁
			releaseLock(lockKey);
		}
	}

	/**
	 * 设备建档生成ERP编号，自定义测试使用
	 * 请求缓存 从CacheNames 中取 例如 idevelop:device:apply::number:
	 *
	 * @return String 根据地市生成的工单编号
	 */
	public String generateRecordErpNumber(String orderRedis) {
		String format = DateTimeFormatter.ofPattern(YYYY_MM_DD).format(LocalDateTime.now());
		IdevelopUser user = SecureUtil.getUser();
		String region = StringUtils.rightPad(user.getRegionCode(), ORDER_NO_LENGTH, PAD_STR);
		String userRegion = region.substring(2);
		String redisKey = orderRedis + format + userRegion;
		String lockKey = orderRedis + format + userRegion + CacheNames.LOCK;
		try {
			// 获取锁
			if (!acquireLock(lockKey)) {
				waitBeforeRetry();
				return generateRecordErpNumber(orderRedis);
			}
			return Constants.RECORD_ERP_TEST + format + userRegion + getIncrement(redisKey, WorkOrderTypeEnum.ERP.getValue());
		} finally {
			// 释放锁
			releaseLock(lockKey);
		}
	}

	/**
	 * 根据区域生成认证账号，账号规则 区域（六位，不够补零，加上五位递增）
	 * regionCode 区域编码
	 * 请求缓存 从CacheNames 中取 例如 idevelop:device:auth::user:
	 *
	 * @return String 根据地市生成的认账用户账号
	 */
	public String generateDeviceAuthUser(String regionCode, String orderRedis) {
		String region = StringUtils.rightPad(regionCode, ORDER_NO_LENGTH, PAD_STR);
		String redisKey = orderRedis + region;
		String lockKey = orderRedis + region + CacheNames.LOCK;

		try {
			// 获取锁
			if (!acquireLock(lockKey)) {
				waitBeforeRetry();
				return generateDeviceAuthUser(regionCode, orderRedis);
			}
			return regionCode + createDeviceAuthUser(redisKey, WorkOrderTypeEnum.RZ.getValue());
		} finally {
			// 释放锁
			releaseLock(lockKey);
		}
	}


	private String createDeviceAuthUser(String redisKey, String typeEnum) {
		long increment = redisUtil.incr(redisKey, 1);
		log.error("获取序列号:{}", increment);

		if (increment == 1) {
			GenerateCode generateCode = iGenerateCodeService.getOne(new LambdaQueryWrapper<GenerateCode>().eq(GenerateCode::getUniqueKey, redisKey));
			if (Objects.isNull(generateCode)) {
				// 如果数据库为空, 增加DB
				iGenerateCodeService.save(GenerateCode.builder().uniqueKey(redisKey).number(increment).type(typeEnum).build());
				return String.format(DEVICE_AUTH_USER, increment);
			}
			// 如果redis为空, 默认将DB中 版本号 增加到redis中
			increment = generateCode.getNumber() + 1;
			redisUtil.incr(redisKey, generateCode.getNumber());
			iGenerateCodeService.saveOrUpdate(GenerateCode.builder().uniqueKey(redisKey).number(increment).type(typeEnum).build());
			return String.format(DEVICE_AUTH_USER, increment);
		}
		// DB和redis都正常
		iGenerateCodeService.saveOrUpdate(GenerateCode.builder().uniqueKey(redisKey).number(increment).type(typeEnum).build());
		return String.format(DEVICE_AUTH_USER, increment);
	}

	/**
	 * 生成全省 实物ID
	 * 根据区域生成认证账号，账号规则 区域（六位，不够补零，加上五位递增）
	 * regionCode 区域编码
	 * 请求缓存 从CacheNames 中取 例如 idevelop:device:auth::user:
	 *
	 * @return String 根据地市生成的认账用户账号
	 */
	public String generateSerial() {
		String lockKey = CacheNames.REAL_ID_LOCK;
		String redisKey = CacheNames.REAL_ID_LOCK + "1";

		try {
			// 获取锁
			if (!acquireLock(lockKey)) {
				waitBeforeRetry();
				return generateSerial();
			}
			return createSerial(redisKey, WorkOrderTypeEnum.REAL_ID.getValue());
		} finally {
			// 释放锁
			releaseLock(lockKey);
		}
	}


	private String createSerial(String redisKey, String typeEnum) {
		long increment = redisUtil.incr(redisKey, 1);
		log.error("获取序列号:{}", increment);

		if (increment == 1) {
			GenerateCode generateCode = iGenerateCodeService.getOne(new LambdaQueryWrapper<GenerateCode>().eq(GenerateCode::getUniqueKey, redisKey));
			if (Objects.isNull(generateCode)) {
				// 如果数据库为空, 增加DB
				iGenerateCodeService.save(GenerateCode.builder().uniqueKey(redisKey).number(increment).type(typeEnum).build());
				return String.format(REAL_ID, increment);
			}
			// 如果redis为空, 默认将DB中 版本号 增加到redis中
			increment = generateCode.getNumber() + 1;
			redisUtil.incr(redisKey, generateCode.getNumber());
			iGenerateCodeService.saveOrUpdate(GenerateCode.builder().uniqueKey(redisKey).number(increment).type(typeEnum).build());
			return String.format(REAL_ID, increment);
		}
		// DB和redis都正常
		iGenerateCodeService.saveOrUpdate(GenerateCode.builder().uniqueKey(redisKey).number(increment).type(typeEnum).time(LocalDate.now()).build());
		return String.format(REAL_ID, increment);
	}


	/**
	 * 获取下一天凌晨
	 *
	 * @return
	 */
	private long getNexDay() {
		LocalDateTime tomorrow = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
		return tomorrow.toEpochSecond(ZoneOffset.UTC) * 1000;
	}


	/**
	 * 获取用户区域
	 *
	 * @return
	 */
	private static String getRegionCode() {
		IdevelopUser user = SecureUtil.getUser();
		String regionCode = user.getRegionCode();
		if (regionCode.length() <= 2) {
			return regionCode + "0000";
		} else if (regionCode.length() > 2 && regionCode.length() <= 4) {
			return regionCode + "00";
		} else {
			return regionCode;
		}
	}

	/**
	 * 获取用户区域
	 *
	 * @return
	 */
	private static String getRegionCode(String regionCode) {
		if (StringUtils.isEmpty(regionCode)) {
			IdevelopUser user = SecureUtil.getUser();
			regionCode = user.getRegionCode();
		}
		if (regionCode.length() <= 2) {
			return regionCode + "0000";
		} else if (regionCode.length() > 2 && regionCode.length() <= 4) {
			return regionCode + "00";
		} else {
			return regionCode;
		}
	}


	/**
	 * 获取标准全称
	 *
	 * @param type        设备来源
	 * @param element     wbs元素
	 * @param projectName wbs项目名称
	 * @param deviceType  设备类型
	 * @return
	 */
	public String getDeviceFullName(String type, String element, String projectName, String deviceType) {
		int year = Year.now().getValue();
		IdevelopUser user = SecureUtil.getUser();
		String corpName = user.getCorpName();
		String deptName = user.getDeptName();
		String deviceTypeName = getDictValue(cmdbDictProperties.getDeviceType(), deviceType);
		if (ciEntityProperties.getCientityId(CmdbCientityConstant.DEVICE_SOURCE).equals(type)) {
			// 统一纳管：  年度+单位简称+项目名称+设备类型+3位序号
			// 2024-5-29变更为 WBS项目名称+设备类型+3位序号
			String redisKey = CacheNames.DEVICE_FULL_NAME + projectName + deviceType;
			long increment = redisUtil.incr(redisKey, 1);
			if (increment == 1) {
				redisUtil.expireAt(redisKey, this.getLastDay());
			}
			return projectName + deviceTypeName + String.format(FORMAT_03, increment);
		} else {
			// 非统一纳管：年度+单位简称+部门+设备类型+3位序号
			// 2024-5-29变更为 设备编码年度+领用单位简称+"自购"+设备类型+3位序号
			String redisKey = CacheNames.DEVICE_FULL_NAME + year + corpName + "自购" + deviceType;
			long increment = redisUtil.incr(redisKey, 1);
			if (increment == 1) {
				redisUtil.expireAt(redisKey, this.getLastDay());
			}
			return year + corpName + "自购" + deviceTypeName + String.format(FORMAT_03, increment);
		}
	}


	public String getDeviceName(String deviceType) {
		String deviceTypeName = getDictValue(cmdbDictProperties.getDeviceType(), deviceType);
		String redisKey = CacheNames.DEVICE_NAME + deviceType;
		long increment = redisUtil.incr(redisKey, 1);
		if (increment == 1) {
			redisUtil.expireAt(redisKey, this.getLastDay());
		}
		return deviceTypeName + String.format(FORMAT_04, increment);
	}

	private long getLastDay() {
		LocalDateTime lastDay = LocalDateTime.now().with(TemporalAdjusters.lastDayOfYear()).withHour(23).withMinute(59).withSecond(59);
		return lastDay.toEpochSecond(ZoneOffset.UTC) * 1000;
	}

	private String getDictValue(Long ciId, String key) {
		String redisKey = CacheNames.CMDB_DICT_STORAGE + ciId;
		Map<String, String> cache = (Map<String, String>) redisUtil.get(redisKey);
		if (CollectionUtil.isEmpty(cache)) {
			R<List<Map<String, Object>>> dict = CmdbCiAttrWrapper.build().getCiCientityDictList(ciId);
			cache = CmdbDictUtil.getValue(dict);
			if (CollectionUtil.isEmpty(cache)) {
				return null;
			}
			redisUtil.set(redisKey, cache, getNexDay());
		}
		String value = cache.get(key);
		if (StringUtil.isBlank(value)) {
			R<List<Map<String, Object>>> dict = CmdbCiAttrWrapper.build().getCiCientityDictList(ciId);
			cache = CmdbDictUtil.getValue(dict);
			redisUtil.set(redisKey, cache, getNexDay());
		}
		return cache.get(key);
	}

	private String getIncrement(String redisKey, String typeEnum) {
		long increment = redisUtil.incr(redisKey, 1);
		log.error("获取序列号:{}", increment);

		if (increment == 1) {
			redisUtil.expireAt(redisKey, this.getNexDay());
			GenerateCode generateCode = iGenerateCodeService.getOne(new LambdaQueryWrapper<GenerateCode>().eq(GenerateCode::getUniqueKey, redisKey).eq(GenerateCode::getTime, LocalDate.now()));
			if (Objects.isNull(generateCode)) {
				// 如果数据库为空, 增加DB
				iGenerateCodeService.save(GenerateCode.builder().uniqueKey(redisKey).number(increment).type(typeEnum).time(LocalDate.now()).build());
				return String.format(FORMAT_04, increment);
			}
			// 如果redis为空, 默认将DB中 版本号 增加到redis中
			increment = generateCode.getNumber() + 1;
			redisUtil.incr(redisKey, generateCode.getNumber());
			iGenerateCodeService.saveOrUpdate(GenerateCode.builder().uniqueKey(redisKey).number(increment).type(typeEnum).time(LocalDate.now()).build());
			return String.format(FORMAT_04, increment);
		}
		// DB和redis都正常
		iGenerateCodeService.saveOrUpdate(GenerateCode.builder().uniqueKey(redisKey).number(increment).type(typeEnum).time(LocalDate.now()).build());
		return String.format(FORMAT_04, increment);
	}

	private Boolean acquireLock(String lockKey) {
		return redisUtil.set(lockKey, lockKey);
	}

	private void releaseLock(String lockKey) {
		redisUtil.del(lockKey);
	}

	private void waitBeforeRetry() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
