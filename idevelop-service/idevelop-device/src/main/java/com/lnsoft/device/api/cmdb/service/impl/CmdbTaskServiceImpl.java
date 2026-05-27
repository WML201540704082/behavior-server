package com.lnsoft.device.api.cmdb.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.lnsoft.cmdb.entity.FeignCiCientity;
import com.lnsoft.cmdb.entity.FeignCmdbCientityBatchupdate;
import com.lnsoft.cmdb.entity.FeignCmdbCientitySearch;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.asset.service.IDeviceOperationAgeConfigService;
import com.lnsoft.device.api.cmdb.mapper.IscDeptMapper;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.api.cmdb.service.ICmdbTaskService;
import com.lnsoft.device.api.cmdb.wrapper.CmdbCiAttrWrapper;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.constant.CmdbCientityConstant;
import com.lnsoft.device.entity.StockDept;
import com.lnsoft.device.entity.StockUnitDept;
import com.lnsoft.device.eums.Expression;
import com.lnsoft.device.eums.TransactionActionType;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.device.props.CmdbDictProperties;
import com.lnsoft.device.vo.CiCientitySearchVO;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: xuel
 * @CreateTime: 2024/3/1 10:04
 * @Description: ICmdbServiceImpl
 */
@Service
@AllArgsConstructor
public class CmdbTaskServiceImpl implements ICmdbTaskService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CmdbTaskServiceImpl.class);

	private IDeviceOperationAgeConfigService ageConfigService;
	private CmdbCientityProperties cmdbCientityProperties;
	private ICmdbService iCmdbService;
	private CmdbDictProperties cmdbDictProperties;
	@Resource
	private IscDeptMapper iscDeptMapper;


	/**
	 * 刷新投运年限 和 维保到期时间
	 *
	 * @param format
	 */
	@Override
	public Boolean refreshUseAge(String format) {

		Query query = new Query();
		Integer size = 10;
		query.setSize(size);
		query.setCurrent(1);
		String operation = cmdbCientityProperties.getCientityId(CmdbCientityConstant.IN_OPERATION);
		String warehouse = cmdbCientityProperties.getCientityId(CmdbCientityConstant.RETURN_WAREHOUSE);

		// 构造查询条件
		List<CiCientitySearchVO> entity = new ArrayList<>();
		CiCientitySearchVO condition1 = CiCientitySearchVO.builder()
			.attrValue(operation + "," + warehouse)
			.attrName(CmdbAttrConstant.DEVICE_STATUS_CODE)
			.expression(Expression.EQUAL).build();
		entity.add(condition1);
		IdevelopUser user = SecureUtil.getUser();
		if (!StringUtils.equals(user.getRegionCode(), "37")) {
			CiCientitySearchVO condition2 = CiCientitySearchVO.builder()
				.attrValue(user.getRegionCode())
				.attrName(CmdbAttrConstant.AREA)
				.expression(Expression.LIKE).build();
			entity.add(condition2);
		}

		FeignCiCientity feignCiCientity = iCmdbService.getCiCientityListByClaccify(entity, query);
		Integer total = feignCiCientity.getTotal();
		size = 1000;
		int count = total / size;
		if (total % size != 0) {
			count++;
		}
		for (int i = 0; i < count; i++) {
			LOGGER.info("开始时间：" + System.currentTimeMillis() + "当前次数:" + i);
			FeignCiCientity feignCiCientitys = iCmdbService.getCiCientityListByClaccify(entity, query);
			List<Map<String, Object>> data = feignCiCientitys.getData();
			Map<Long, Map<String, Object>> updateEntity = new HashMap<>();
			for (Map<String, Object> datum : data) {
				LocalDate now = LocalDate.parse(format);
				Map<String, Object> map = new HashMap<>();
				// 首次投运时间
				Object oprtDateFirst = datum.get("oprtDateFirst");
				if (Objects.nonNull(oprtDateFirst)) {
					String typeCode = String.valueOf(datum.get(CmdbAttrConstant.DEVICE_TYPE_CODE));
					Integer age = Integer.parseInt(ageConfigService.getOneByDeviceType(typeCode));
					LocalDate parse = LocalDate.parse(String.valueOf(oprtDateFirst));
					Period between = Period.between(parse, now);
					int years = between.getYears();
					map.put("useAge", years + 1);
					if (years > age) {
						map.put("oldMark", "1");
					}
				}
				// 售后服务到期时间
				Object afterSaleExpDate = datum.get("afterSaleExpDate");
				if (Objects.nonNull(afterSaleExpDate)) {
					LocalDate parse = LocalDate.parse(String.valueOf(afterSaleExpDate));
					int compare = now.compareTo(parse);
					if (compare <= 0) {
						map.put("afterStatus", "正常");
						map.put("afterStatusCode", 1102248395735041L);
					} else {
						map.put("afterStatus", "过期");
						map.put("afterStatusCode", 1102248202797057L);
					}
				}
				map.put("id", datum.get("id"));
				map.put("uuid", datum.get("uuid"));
				map.put("ciId", datum.get("ciId"));
				updateEntity.put((Long) datum.get("id"), map);
			}
			Map<String, Object> map = iCmdbService.cientityBatchupdate(updateEntity, TransactionActionType.UPDATE);
			LOGGER.info("结束时间：" + System.currentTimeMillis() + "当前次数:" + i);
			Object committed = map.get("committed");
			if (Objects.equals(Boolean.FALSE.toString(), committed)) {
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}

	/**
	 * 刷新转资到期
	 *
	 * @param format
	 */
	@Override
	public Boolean refreshBecomeDueAssets(String format) {
		Query query = new Query();
		query.setSize(999999999);
		query.setCurrent(1);

		List<CiCientitySearchVO> entity = new ArrayList<>();
		entity.add(CiCientitySearchVO.builder()
			.attrValue("1102861334544385")
			.attrName("deviceSourceCode")
			.expression(Expression.EQUAL).build());
		entity.add(CiCientitySearchVO.builder()
			.attrName("deviceCodeErp")
			.expression(Expression.ISNULL).build());
		FeignCiCientity feignCiCientity = iCmdbService.getCiCientityListByClaccify(entity, query);
		List<Map<String, Object>> data = feignCiCientity.getData();
		Map<Long, Map<String, Object>> updateEntity = new HashMap<>();
		for (Map<String, Object> datum : data) {
			LocalDate now = LocalDate.parse(format);
			Map<String, Object> map = new HashMap<>();
			// 入库时间
			Object inWarehouseDate = datum.get("inWarehouseDate");
			LocalDate inWarehouseDateFormat = LocalDate.parse(String.valueOf(inWarehouseDate));
			// 获取半年之后的时间
			LocalDate after = inWarehouseDateFormat.plusMonths(6);
			// 比较获取相差天数
			long daysDiff = ChronoUnit.DAYS.between(now, after);
			map.put("transferDate", daysDiff);
			map.put("id", datum.get("id"));
			map.put("uuid", datum.get("uuid"));
			map.put("ciId", datum.get("ciId"));
			updateEntity.put((Long) datum.get("id"), map);
		}

		Map<String, Object> map = iCmdbService.cientityBatchupdate(updateEntity, TransactionActionType.UPDATE);
		Object committed = map.get("committed");
		if (Objects.equals(Boolean.FALSE.toString(), committed)) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	/**
	 * 手动触发异步 刷新固定值 任务
	 *
	 * @param fixedValueMap
	 * @return
	 */
	@Override
	public Boolean refreshFixedValue(Map<String, Object> fixedValueMap) {
		try {
			// 属性ID
			Long requestAttr = (Long) fixedValueMap.get("requestAttr");
			// 需要修改属性的值
			String requestValue = (String) fixedValueMap.get("requestValue");
			// 需要修改属性的值
			String requestActualValue = (String) fixedValueMap.get("requestActualValue");
			// 属性类型
			String requestType = (String) fixedValueMap.get("requestType");

			// 属性英文名(用于判断)
			String requestName = (String) fixedValueMap.get("requestName");
			// 开始请求的分页
			Integer requestNum = (Integer) fixedValueMap.get("requestNum");
			// 每页请求的数量
			Integer requestSize = (Integer) fixedValueMap.get("requestSize");

			if (Objects.isNull(requestNum)) {
				requestNum = 1;
			}
			if (Objects.isNull(requestSize)) {
				requestSize = 150;
			}

			FeignCmdbCientitySearch feignCmdbCientitySearch = new FeignCmdbCientitySearch();
			feignCmdbCientitySearch.setCurrentPage(1);
			feignCmdbCientitySearch.setPageSize(2);
			feignCmdbCientitySearch.setCiId(1082372687986688L);

			List<FeignCmdbCientitySearch.CiEntitySearchAttr> ciEntitySearchAttrs = new ArrayList<>();
			FeignCmdbCientitySearch.CiEntitySearchAttr ciEntitySearchAttr = new FeignCmdbCientitySearch.CiEntitySearchAttr();
			ciEntitySearchAttr.setAttrId(requestAttr);
			ciEntitySearchAttr.setExpression(Expression.ISNULL.getExpression());
			ciEntitySearchAttrs.add(ciEntitySearchAttr);
			feignCmdbCientitySearch.setAttrFilterList(ciEntitySearchAttrs);

			R<FeignCiCientity> ciCientityListPage = CmdbCiAttrWrapper.build().getCiCientityListPage(feignCmdbCientitySearch);
			Integer total = ciCientityListPage.getData().getTotal();
			LOGGER.error("查询共 " + total + " 条");

			int count = total / requestSize;
			if (total % requestSize != 0) {
				count++;
			}
			LOGGER.error("需要循环 " + count + " 次");

			Integer allNum = 0;
			for (int i = requestNum; i < count + 1; i++) {
				Thread.sleep(500);
				LocalDateTime startTime = LocalDateTime.now();
				LOGGER.info("(总)循环次数: " + i + " 开始时间为: " + startTime);

				FeignCmdbCientitySearch feignCmdbCientitySearch1 = new FeignCmdbCientitySearch();
				feignCmdbCientitySearch1.setCurrentPage(i);
				feignCmdbCientitySearch1.setPageSize(requestSize);
				feignCmdbCientitySearch1.setCiId(1082372687986688L);
				feignCmdbCientitySearch1.setAttrFilterList(ciEntitySearchAttrs);

				R<FeignCiCientity> ciCientityListPage1 = CmdbCiAttrWrapper.build().getCiCientityListPage(feignCmdbCientitySearch1);
				List<Map<String, Object>> data = ciCientityListPage1.getData().getData();
				allNum += data.size();
				LOGGER.info("当前处理数量: " + allNum);

				Map<Long, List<Map<String, Object>>> collect = data.stream()
					.collect(Collectors.groupingBy(item -> (Long) item.get("ciId")));
				Integer foreachNum = 1;
				for (Map.Entry<Long, List<Map<String, Object>>> entry : collect.entrySet()) {

					FeignCmdbCientityBatchupdate feignCmdbCientityBatchupdate = new FeignCmdbCientityBatchupdate();

					feignCmdbCientityBatchupdate.setCiId(entry.getKey());

					int size = 0;
					List<Map<String, Object>> value = entry.getValue();
					if (StringUtils.isNotEmpty(requestName)) {
						List<Long> collect1 = value.stream()
							.filter(item -> Objects.isNull(item.get(requestName)) ||
								(Objects.nonNull(item.get(requestName)) && !StringUtils.equals(item.get(requestName).toString(), requestValue)))
							.map(item -> (Long) item.get("id")).collect(Collectors.toList());
						size = collect1.size();
						feignCmdbCientityBatchupdate.setCiEntityIdList(collect1);
					} else {
						List<Long> collect1 = value.stream().map(item -> (Long) item.get("id")).collect(Collectors.toList());
						size = collect1.size();
						feignCmdbCientityBatchupdate.setCiEntityIdList(collect1);
					}
					LOGGER.error("(内)循环次数: (" + i + ") 内中循环次数: (" + foreachNum + ") ,查询个数为: " + value.size() + " 个,过滤后个数为: " + size + " 个");

					foreachNum++;
					if (size == 0) {
						LOGGER.error("不需要处理!");
						continue;
					}

					Map<String, FeignCmdbCientityBatchupdate.AttrEntityDataValue> attrEntityData = new HashMap<>();
					FeignCmdbCientityBatchupdate.AttrEntityDataValue attrEntityDataValue = new FeignCmdbCientityBatchupdate.AttrEntityDataValue();
					attrEntityDataValue.setValueList(Lists.newArrayList(String.valueOf(requestValue)));
					attrEntityDataValue.setActualValueList(Lists.newArrayList(String.valueOf(requestActualValue)));
					attrEntityDataValue.setType(requestType);

					String requestAttrStr = "attr_" + requestAttr;
					attrEntityData.put(requestAttrStr, attrEntityDataValue);
					feignCmdbCientityBatchupdate.setAttrEntityData(attrEntityData);

					feignCmdbCientityBatchupdate.setNeedCommit(Boolean.TRUE);
					LOGGER.error("请求修改CMDB参数请求参数 " + JSONObject.toJSONString(feignCmdbCientityBatchupdate) + " ,请求参数个数: " + size + " 个");
					Map<String, Object> innerMap = CmdbCiAttrWrapper.build().feignCientityBatchupdate(feignCmdbCientityBatchupdate);
					LOGGER.error("请求修改CMDB参数返回结果 " + JSONObject.toJSONString(innerMap));
				}
				LOGGER.info("循环次数: " + i + " 次, 开始时间为: " + startTime + ", 结束时间为: " + LocalDateTime.now());
			}

			return Boolean.TRUE;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}


	@Override
	public Boolean refreshUnitDept(JSONObject fixedValueMap) {
		try {

			// 总任务名称
			String requestAttr = fixedValueMap.getString("requestAttr");
			// 总任务需要查询的 开始 值
			Integer requestNumAll = fixedValueMap.getInteger("requestNumAll");
			// 总任务需要查询的 结束 值
			Integer requestSizeAll = fixedValueMap.getInteger("requestSizeAll");
			// 任务每次需要处理的数据数量
			Integer requestSize = fixedValueMap.getInteger("requestSize");
			// 是否 只处理 未处理数据
			Integer isSuccess = fixedValueMap.getInteger("isSuccess");


			List<Long> cientityIds = iscDeptMapper.selectCientityId(requestNumAll, requestSizeAll, isSuccess);
			Integer total = cientityIds.size();

			if (Objects.isNull(total)) {
				LOGGER.info("(总)任务: {}, 未查询到数据! 参数为: {}", requestAttr, fixedValueMap);
				return Boolean.TRUE;
			}
			LOGGER.error("(总)任务: {}, 本次查询并需要处理的数据共 {} 条", requestAttr, total);

			if (Objects.isNull(requestSize)) {
				requestSize = 100;
			}

			int totalPage = total / requestSize;
			if (total % requestSize != 0) {
				totalPage++;
			}
			LOGGER.error("需要循环 {} 次", totalPage);


			Integer allNum = 0;
			StockUnitDept unitDept = new StockUnitDept();
			List<StockUnitDept> stockUnitDeptList = iscDeptMapper.selectIscDept(unitDept);
			List<String> iscDeptList = new ArrayList<>();
			for (StockUnitDept stockUnitDept : stockUnitDeptList) {
				iscDeptList.add(stockUnitDept.getIscDeptId());
			}
			Map<String, StockUnitDept> iscDeptMap = stockUnitDeptList.stream().collect(Collectors.toMap(StockUnitDept::getUnicode, item -> item));
			List<StockDept> deptIdList = iscDeptMapper.selectDeptIdByIscId(iscDeptList);
			Map<String, StockDept> deptIdMap = deptIdList.stream().collect(Collectors.toMap(StockDept::getIscDeptId, item -> item));
			Map<Object, Object> deviceStatusMap = cmdbDictProperties.getDictMapByCiId(cmdbDictProperties.getDeviceStatus());
			Map<Object, Object> deviceClaccifyMap = cmdbDictProperties.getDictMapByCiId(cmdbDictProperties.getDeviceClaccify());


			for (int currentPage = 0; currentPage < totalPage; currentPage++) {
				Thread.sleep(200);
				LocalDateTime startTime = LocalDateTime.now();

				int startIndex = currentPage * requestSize;
				int endIndex = Math.min(startIndex + requestSize, cientityIds.size());

				// 先获取数据
				List<Long> cientityIdList = cientityIds.subList(startIndex, endIndex);
				LOGGER.info("(总)任务: {}, 循环次数: {} 次, 开始时间: {} , 当期处理的 cientityId 为: {} ", requestAttr, currentPage, startTime, cientityIdList.get(0));

				if (CollectionUtils.isEmpty(cientityIdList)) {
					continue;
				}
				// 先查询数据
				FeignCmdbCientitySearch feignCmdbCientitySearch = new FeignCmdbCientitySearch();
				feignCmdbCientitySearch.setCurrentPage(1);
				feignCmdbCientitySearch.setPageSize(cientityIdList.size());
				feignCmdbCientitySearch.setCiId(1082372687986688L);
				feignCmdbCientitySearch.setIdList(cientityIdList);
				R<FeignCiCientity> ciCientityListPage = CmdbCiAttrWrapper.build().getCiCientityListPage(feignCmdbCientitySearch);
				List<Map<String, Object>> data = ciCientityListPage.getData().getData();
				allNum += data.size();
				LOGGER.info("当前处理数量: {} 个", allNum);

				// 循环数据
				int needCount = 0;
				int noNeedCount = 0;
				int successCount = 0;
				int failCount = 0;
				for (Map<String, Object> datum : data) {
					Long id = (Long) datum.get(CmdbAttrConstant.ID);
					try {
						String uuid = (String) datum.get(CmdbAttrConstant.UUID);
						Object ciId = datum.get(CmdbAttrConstant.CI_ID);
						// 设备编码
						String deviceCode = (String) datum.get(CmdbAttrConstant.DEVICE_CODE);
						// 设备分类
						Object deviceCategoryCode = datum.get(CmdbAttrConstant.DEVICE_CATEGORY_CODE);
						Long deviceCategory = null;
						if (Objects.nonNull(deviceCategoryCode) && Objects.nonNull(deviceClaccifyMap.get(deviceCategoryCode.toString()))) {
							deviceCategory = (Long) deviceCategoryCode;
						}

						// 设备状态
						Object deviceStatusCode = datum.get(CmdbAttrConstant.DEVICE_STATUS_CODE);
						Long deviceStatus = null;
						if (Objects.nonNull(deviceStatusCode) && Objects.nonNull(deviceStatusMap.get(deviceStatusCode.toString()))) {
							deviceCategory = (Long) deviceStatusCode;
						}

						String unicode = deviceCode.substring(0, 4);
						StockUnitDept stockUnitDept = iscDeptMap.get(unicode);
						if (Objects.isNull(stockUnitDept)) {
							// 1 成功标志,2 失败标志, 3 报错标志, 4 为 不需要处理个数
							iscDeptMapper.updateCientityId(id, 4);
							noNeedCount++;
							continue;
						}
						needCount++;
						// 组装数据
						Map<Long, Map<String, Object>> entityMap = new HashMap<>();
						Map<String, Object> itemMap = new HashMap<>();
						itemMap.put(CmdbAttrConstant.UUID, uuid);
						itemMap.put(CmdbAttrConstant.CI_ID, ciId);
						itemMap.put(CmdbAttrConstant.DEVICE_CODE, deviceCode);

						// 区域
						itemMap.put(CmdbAttrConstant.AREA, stockUnitDept.getRegionCode());

						// 产权单位
						itemMap.put(CmdbAttrConstant.OWNER_UNIT, stockUnitDept.getNewName());
						itemMap.put(CmdbAttrConstant.OWNER_UNIT_CODE, stockUnitDept.getNewId());
						if (Objects.nonNull(deviceStatus) && deviceStatus.equals(1105089449492480L)) {
							// 领用单位
							itemMap.put(CmdbAttrConstant.RECEIVE_UNIT, stockUnitDept.getNewName());
							itemMap.put(CmdbAttrConstant.RECEIVE_UNIT_CODE, stockUnitDept.getNewId());
						}

						// 维护工厂
						itemMap.put(CmdbAttrConstant.MAINTENANCE_FACTORY, stockUnitDept.getErpUnit());
						itemMap.put(CmdbAttrConstant.MAINTENANCE_FACTORY_CODE, stockUnitDept.getErpUnitCode());
						// 运行单位
						itemMap.put(CmdbAttrConstant.OPRT_DEPT, stockUnitDept.getNewName());
						itemMap.put(CmdbAttrConstant.OPRT_DEPT_CODE, stockUnitDept.getNewId());
						// 运维单位
						itemMap.put(CmdbAttrConstant.OPERATION_UNIT, stockUnitDept.getNewName());
						itemMap.put(CmdbAttrConstant.OPERATION_UNIT_CODE, stockUnitDept.getNewId());

						String iscDeptId = stockUnitDept.getIscDeptId();
						if (StringUtils.isNotEmpty(iscDeptId)) {
							StockDept stockDept = deptIdMap.get(stockUnitDept.getIscDeptId());
							// 部门
							itemMap.put(CmdbAttrConstant.DEPT, stockDept.getId());
							// 产权部门
							itemMap.put(CmdbAttrConstant.PROPERTY_DEPT, stockDept.getFullName());
							itemMap.put(CmdbAttrConstant.PROPERTY_DEPT_CODE, stockDept.getId());
							// 运维部门
							itemMap.put(CmdbAttrConstant.OPERATION_DEPT, stockDept.getFullName());
							itemMap.put(CmdbAttrConstant.OPERATION_DEP_CODE, stockDept.getId());
						}

						// 运维责任人
						itemMap.put(CmdbAttrConstant.OPERATION_PERSON, stockUnitDept.getIscUserName());
						// 运维责任人联系方式
						itemMap.put(CmdbAttrConstant.OPERATION_PERSON_TEL, stockUnitDept.getIscUserPhone());
						// 运维责任人身份证
						itemMap.put(CmdbAttrConstant.OPERATION_CHAGE_ACCOUNT, stockUnitDept.getIscUserId());


						// 产权状态(正常)
						itemMap.put(CmdbAttrConstant.OWNER_STATUS, 1131251278086145L);
						// 运维等级(三级)
						itemMap.put(CmdbAttrConstant.OPERATION_LEVEL, 1110112522797057L);
						// 线站标识
						itemMap.put(CmdbAttrConstant.LINE_STATION, "00000000000000000");
						// 线站标识名称
						itemMap.put(CmdbAttrConstant.LINE_STATION_SIGN, "00000000000000000");
						// 设备状态为 已报废
						if (Objects.nonNull(deviceStatus) && deviceStatus.equals(1105092729438209L)) {
							// 报废比例  报废原因
							itemMap.put(CmdbAttrConstant.SCRAP_RATIO, "100%");
							itemMap.put(CmdbAttrConstant.SCRAP_REASON_CODE, 1131283054133250L);
						}

						// 设备分类为 主机设备
						if (Objects.nonNull(deviceCategory) && deviceCategory.equals(1097755012694017L)) {
							// 电压等级
							itemMap.put(CmdbAttrConstant.VOLTAGE_LEVEL_CODE, 1125298478579878L);
							itemMap.put(CmdbAttrConstant.VOLTAGE_LEVEL, "交流220V");

							// 设备状态为 在运
							if (Objects.nonNull(deviceStatus) && deviceStatus.equals(1105089449492480L)) {
								// 是否热备
								itemMap.put(CmdbAttrConstant.IS_HOT_BACKUP, 1104198453493761L);
							}
							// 是否信创设备
							itemMap.put(CmdbAttrConstant.IS_IT_AI_CODE, 1104198587711488L);
							// 是否纳入云管
							itemMap.put(CmdbAttrConstant.IS_CLOUD_MANGE, 1104198587711488L);
						}

						// 设备分类为 终端设备
						if (Objects.nonNull(deviceCategory) && deviceCategory.equals(1097756774301696L)) {
							// 是否信创设备
							itemMap.put(CmdbAttrConstant.IS_IT_AI_CODE, 1104198587711488L);
							// 认证方式
							itemMap.put(CmdbAttrConstant.NETWORK_ACCESS_METHOD, "802.1x");
						}

						// 设备分类为 网络设备
						if (Objects.nonNull(deviceCategory) && deviceCategory.equals(1097756405202944L)) {
							// 认证方式
							itemMap.put(CmdbAttrConstant.NETWORK_ACCESS_METHOD, "MAC认证");
						}

						// 设备分类为 存储设备
						if (Objects.nonNull(deviceCategory) && deviceCategory.equals(1097756019326977L)) {
							// 是否纳入云管
							itemMap.put(CmdbAttrConstant.IS_CLOUD_MANGE, 1104198587711488L);
						}

						// 设备分类为 安全设备
						if (Objects.nonNull(deviceCategory) && deviceCategory.equals(1097756572975105L)) {
							// 设备状态为 在运
							if (Objects.nonNull(deviceStatus) && deviceStatus.equals(1105089449492480L)) {
								// 是否热备
								itemMap.put(CmdbAttrConstant.IS_HOT_BACKUP, 1104198453493761L);
							}
						}

						entityMap.put(id, itemMap);

						// key为 id  value 包括 ciId和uuid
						Map<String, Object> map = iCmdbService.cientityBatchupdate(entityMap, TransactionActionType.UPDATE);
						int status = (int) map.get("Status");
						Thread.sleep(200);
						if (status == 200) {
							successCount++;
							// 1 成功标志
							iscDeptMapper.updateCientityId(id, 1);
						} else {
							// 1 成功标志,2 失败标志
							iscDeptMapper.updateCientityId(id, 2);
							failCount++;
						}
					} catch (Exception e) {
						Thread.sleep(200);
						failCount++;
						LOGGER.error("(总)任务: {}, 循环次数: {} 次, 报错信息: {} ", requestAttr, currentPage, e.toString());
						// 1 成功标志,2 失败标志, 3 报错标志
						iscDeptMapper.updateCientityId(id, 3);
					}
				}
				LocalDateTime endTime = LocalDateTime.now();
				LOGGER.info("(总)任务: {}, 循环次数: {} 次, 开始时间: {}, 结束时间: {}, 需要处理个数: {}, 不需要处理个数: {}, 成功个数: {}, 失败个数: {}",
					requestAttr, currentPage, startTime, endTime, needCount, noNeedCount, successCount, failCount);
			}
			return Boolean.TRUE;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}






}
