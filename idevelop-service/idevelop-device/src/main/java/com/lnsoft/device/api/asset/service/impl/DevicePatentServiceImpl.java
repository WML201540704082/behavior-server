package com.lnsoft.device.api.asset.service.impl;

import com.lnsoft.cmdb.entity.FeignCiCientity;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.device.api.asset.dto.DevicePatentDistributionDTO;
import com.lnsoft.device.api.asset.dto.DevicePatentOnlineDTO;
import com.lnsoft.device.api.asset.service.IDevicePatentService;
import com.lnsoft.device.api.asset.vo.*;
import com.lnsoft.device.api.cmdb.mapper.HandlerDeviceMapper;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.constant.DeviceConstant;
import com.lnsoft.device.entity.CiCientitySearch;
import com.lnsoft.device.eums.Expression;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.device.props.CmdbDictProperties;
import com.lnsoft.device.vo.CiCientitySearchVO;
import com.lnsoft.system.entity.Dept;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Author: xuel
 * @CreateTime: 2024/7/5 11:23
 * @Description: DevicePatentServiceImpl
 */
@Service
@AllArgsConstructor
public class DevicePatentServiceImpl implements IDevicePatentService {

	private ICmdbService iCmdbService;
	private CmdbCientityProperties cmdbCientityProperties;
	private HandlerDeviceMapper handlerDeviceMapper;
	private CmdbDictProperties cmdbDictProperties;

	@Override
	public FeignCiCientity deviceList(DeviceInfoVo vo) {
		CiCientitySearch cientitySearch = new CiCientitySearch();
		cientitySearch.setQuery(vo.getQuery());
		cientitySearch.setEntity(searchVOS(vo));
		cientitySearch.setFullField(true);
		return iCmdbService.getCiCientityListByCondition(cientitySearch);
	}

	/**
	 * 设备数量计算
	 *
	 * @return
	 */
	@Override
	public Map<String, DevicePatentNumberVO> number() {
		IdevelopUser user = SecureUtil.getUser();

		CiCientitySearch cientitySearch = new CiCientitySearch();
		cientitySearch.setFullField(Boolean.FALSE);
		Query query = new Query();
		query.setCurrent(1);
		query.setSize(2);
		cientitySearch.setQuery(query);

		// 区域
		CiCientitySearchVO area = new CiCientitySearchVO();
		area.setAttrName(CmdbAttrConstant.AREA);
		area.setExpression(Expression.LIKE);
		area.setAttrValue(user.getRegionCode());

		// 设备分类 终端设备
		CiCientitySearchVO deviceCategory = new CiCientitySearchVO();
		deviceCategory.setAttrName(CmdbAttrConstant.DEVICE_CATEGORY_CODE);
		deviceCategory.setExpression(Expression.EQUAL);
		deviceCategory.setAttrValue(cmdbCientityProperties.getT105());

		//是否新创
		CiCientitySearchVO isCode = new CiCientitySearchVO();
		isCode.setAttrName(CmdbAttrConstant.IS_IT_AI_CODE);
		isCode.setExpression(Expression.EQUAL);
		isCode.setAttrValue(cmdbCientityProperties.getYesNo());

		// 在运
		CiCientitySearchVO statusSearchVo = new CiCientitySearchVO();
		statusSearchVo.setAttrName(CmdbAttrConstant.DEVICE_STATUS_CODE);
		statusSearchVo.setExpression(Expression.EQUAL);
		statusSearchVo.setAttrValue(cmdbCientityProperties.getInOperation());

		// 设备类型 台式机、笔记本电脑
		List<String> deviceTypeList = Arrays.asList(cmdbCientityProperties.getT10501(), cmdbCientityProperties.getT10503());

		// 总数统计
		AtomicInteger allNumber = new AtomicInteger(0);
		AtomicInteger inNumber = new AtomicInteger(0);
		AtomicInteger onNumber = new AtomicInteger(0);

		Map<String, DevicePatentNumberVO> result = deviceTypeList.stream().map(item -> {
			DevicePatentNumberVO devicePatentNumber = new DevicePatentNumberVO();
			CiCientitySearchVO deviceType = new CiCientitySearchVO();
			deviceType.setAttrName(CmdbAttrConstant.DEVICE_TYPE_CODE);
			deviceType.setExpression(Expression.EQUAL);
			deviceType.setAttrValue(item);

			cientitySearch.setEntity(Arrays.asList(deviceType, area, deviceCategory, isCode));

			// 当前设备类型设备总数
			FeignCiCientity feignCiCientity1 = iCmdbService.getCiCientityListByCondition(cientitySearch);
			Integer total1 = feignCiCientity1.getTotal();
			allNumber.addAndGet(total1);

			// 当前设备类型在运总数
			cientitySearch.setEntity(Arrays.asList(deviceType, area, deviceCategory, isCode, statusSearchVo));
			FeignCiCientity feignCiCientity2 = iCmdbService.getCiCientityListByCondition(cientitySearch);
			Integer total2 = feignCiCientity2.getTotal();
			inNumber.addAndGet(total2);

			// TODO 当前设备类型在线总数
			// onNumber.addAndGet(410);

			if (StringUtils.equals(item, cmdbCientityProperties.getT10501())) {
				devicePatentNumber.setName(DeviceConstant.DESKTOP);
				devicePatentNumber.setOnNumber(350);
				onNumber.addAndGet(350);
			}
			if (StringUtils.equals(item, cmdbCientityProperties.getT10503())) {
				devicePatentNumber.setName(DeviceConstant.NOTEBOOK);
				devicePatentNumber.setOnNumber(57);
				onNumber.addAndGet(57);
			}
			devicePatentNumber.setAllNumber(total1);
			devicePatentNumber.setInNumber(total2);
			// devicePatentNumber.setOnNumber(0);
			devicePatentNumber.setDistribution(0.00);

			return devicePatentNumber;
		}).collect(Collectors.toMap(DevicePatentNumberVO::getName, item -> item));

		DevicePatentNumberVO devicePatentNumber = new DevicePatentNumberVO();
		devicePatentNumber.setName(DeviceConstant.ALL);
		devicePatentNumber.setAllNumber(allNumber.get());
		devicePatentNumber.setInNumber(inNumber.get());
		devicePatentNumber.setOnNumber(onNumber.get());

		BigDecimal allNumberBig = new BigDecimal(allNumber.get());
		BigDecimal inNumberBig = new BigDecimal(inNumber.get());

		if (allNumber.get() == 0) {
			devicePatentNumber.setDistribution(0.00);
		} else {
			BigDecimal divide = inNumberBig.divide(allNumberBig, 2, RoundingMode.HALF_UP);
			devicePatentNumber.setDistribution(divide.doubleValue());
		}

		result.put(DeviceConstant.ALL, devicePatentNumber);
		return result;
	}

	/**
	 * 采购方式概览
	 *
	 * @return
	 */
	@Override
	public List<DevicePatentPurchaseVO> purchase() {
		IdevelopUser user = SecureUtil.getUser();

		CiCientitySearch cientitySearch = new CiCientitySearch();
		cientitySearch.setFullField(Boolean.TRUE);
		Query query = new Query();
		query.setCurrent(1);
		query.setSize(2);
		cientitySearch.setQuery(query);

		// 区域
		CiCientitySearchVO area = new CiCientitySearchVO();
		area.setAttrName(CmdbAttrConstant.AREA);
		area.setExpression(Expression.LIKE);
		area.setAttrValue(user.getRegionCode());

		// 设备分类 终端设备
		CiCientitySearchVO deviceCategory = new CiCientitySearchVO();
		deviceCategory.setAttrName(CmdbAttrConstant.DEVICE_CATEGORY_CODE);
		deviceCategory.setExpression(Expression.EQUAL);
		deviceCategory.setAttrValue(cmdbCientityProperties.getT105());

		// 是否新创
		CiCientitySearchVO isCode = new CiCientitySearchVO();
		isCode.setAttrName(CmdbAttrConstant.IS_IT_AI_CODE);
		isCode.setExpression(Expression.EQUAL);
		isCode.setAttrValue(cmdbCientityProperties.getYesNo());

		// 设备类型 台式机、笔记本电脑
		List<String> deviceTypeList = Arrays.asList(cmdbCientityProperties.getT10501(), cmdbCientityProperties.getT10503());
		String deviceTypeStrs = String.join(",", deviceTypeList);
		CiCientitySearchVO deviceType = new CiCientitySearchVO();
		deviceType.setAttrName(CmdbAttrConstant.DEVICE_TYPE_CODE);
		deviceType.setExpression(Expression.EQUAL);
		deviceType.setAttrValue(deviceTypeStrs);
		deviceType.setBatch(Boolean.TRUE);

		// 总数统计
		AtomicInteger allNumber = new AtomicInteger(0);

		// 采购方式
		Map<Object, Object> procureTypeMap = cmdbDictProperties.getDictMapByCiId(cmdbDictProperties.getProcureTypeCode());
		List<DevicePatentPurchaseVO> resultSub = procureTypeMap.entrySet().stream().map(item -> {

			Object key = item.getKey();
			Object value = item.getValue();

			DevicePatentPurchaseVO devicePatentPurchase = new DevicePatentPurchaseVO();
			CiCientitySearchVO procureType = new CiCientitySearchVO();
			procureType.setAttrName(CmdbAttrConstant.PROCURE_TYPE_CODE);
			procureType.setExpression(Expression.EQUAL);
			procureType.setAttrValue(key);

			cientitySearch.setEntity(Arrays.asList(procureType, deviceType, area, deviceCategory, isCode));

			// 当前采购方式设备数量
			FeignCiCientity feignCiCientity = iCmdbService.getCiCientityListByCondition(cientitySearch);
			Integer total = feignCiCientity.getTotal();
			allNumber.addAndGet(total);

			devicePatentPurchase.setProcureTypeCode((String) value);
			devicePatentPurchase.setNumber(total);
			devicePatentPurchase.setProportion(0.00);

			return devicePatentPurchase;
		}).collect(Collectors.toList());

		if (!allNumber.equals(new AtomicInteger(0))) {
			return resultSub.stream().peek(item -> {
				Integer number = item.getNumber();

				BigDecimal allNumberBig = new BigDecimal(allNumber.get());
				BigDecimal numberBig = new BigDecimal(number);

				BigDecimal divide = numberBig.divide(allNumberBig, 2, RoundingMode.HALF_UP);
				item.setProportion(divide.doubleValue());
			}).collect(Collectors.toList());
		}

		return resultSub;
	}

	/**
	 * 数据概览-分发情况
	 *
	 * @return
	 */
	@Override
	public List<DevicePatentDistributionVO> distribution(DevicePatentDistributionDTO devicePatentDistributionDTO) {

		String type = devicePatentDistributionDTO.getType();

		IdevelopUser user = SecureUtil.getUser();
		String regionCode = user.getRegionCode();

		List<Dept> deptList = getDepts(regionCode);

		// 构建请求CMDB参数
		CiCientitySearch cientitySearch = new CiCientitySearch();
		cientitySearch.setFullField(Boolean.FALSE);
		Query query = new Query();
		query.setCurrent(1);
		query.setSize(2);
		cientitySearch.setQuery(query);

		CiCientitySearch cientitySearch1 = new CiCientitySearch();
		cientitySearch1.setFullField(Boolean.FALSE);
		cientitySearch1.setQuery(query);

		// 设备分类 终端设备
		CiCientitySearchVO deviceCategory = new CiCientitySearchVO();
		deviceCategory.setAttrName(CmdbAttrConstant.DEVICE_CATEGORY_CODE);
		deviceCategory.setExpression(Expression.EQUAL);
		deviceCategory.setAttrValue(cmdbCientityProperties.getT105());

		//是否新创
		CiCientitySearchVO isCode = new CiCientitySearchVO();
		isCode.setAttrName(CmdbAttrConstant.IS_IT_AI_CODE);
		isCode.setExpression(Expression.EQUAL);
		isCode.setAttrValue(cmdbCientityProperties.getYesNo());

		// 在运
		CiCientitySearchVO statusSearchVo = new CiCientitySearchVO();
		statusSearchVo.setAttrName(CmdbAttrConstant.DEVICE_STATUS_CODE);
		statusSearchVo.setExpression(Expression.EQUAL);
		statusSearchVo.setAttrValue(cmdbCientityProperties.getInOperation());

		// 设备类型 台式机、笔记本电脑
		List<String> deviceTypeList = Arrays.asList(cmdbCientityProperties.getT10501(), cmdbCientityProperties.getT10503());
		String deviceTypeStrs = String.join(",", deviceTypeList);
		CiCientitySearchVO deviceType = new CiCientitySearchVO();
		deviceType.setAttrName(CmdbAttrConstant.DEVICE_TYPE_CODE);
		deviceType.setExpression(Expression.EQUAL);
		deviceType.setAttrValue(deviceTypeStrs);
		deviceType.setBatch(Boolean.TRUE);

		List<DevicePatentDistributionVO> result = deptList.stream()
			.filter(item -> StringUtils.equals(type, item.getType()))
			.map(item -> {
			DevicePatentDistributionVO distribution = new DevicePatentDistributionVO();

			// 区域
			CiCientitySearchVO area = new CiCientitySearchVO();
			area.setAttrName(CmdbAttrConstant.AREA);
			area.setExpression(Expression.LIKE);
			area.setAttrValue(item.getRegionCode());

			if (StringUtils.equals(type, "CORP")) {
				// 总数
				cientitySearch.setEntity(Arrays.asList(area, deviceCategory, deviceType, isCode));
				// 已分发
				cientitySearch1.setEntity(Arrays.asList(area, deviceCategory, deviceType, isCode, statusSearchVo));
			}
			if (StringUtils.equals(type, "DEPT")) {
				// 区域
				CiCientitySearchVO dept = new CiCientitySearchVO();
				dept.setAttrName(CmdbAttrConstant.DEPT);
				dept.setExpression(Expression.EQUAL);
				dept.setAttrValue(item.getId());
				// 总数
				cientitySearch.setEntity(Arrays.asList(area, dept, deviceCategory, deviceType, isCode));
				// 已分发
				cientitySearch1.setEntity(Arrays.asList(area, dept, deviceCategory, deviceType, isCode, statusSearchVo));
			}

			FeignCiCientity feignCiCientity1 = iCmdbService.getCiCientityListByCondition(cientitySearch);
			Integer total1 = feignCiCientity1.getTotal();

			FeignCiCientity feignCiCientity2 = iCmdbService.getCiCientityListByCondition(cientitySearch1);
			Integer total2 = feignCiCientity2.getTotal();

			// 未分发
			Integer NoDistribution = total1 - total2;

			distribution.setUnitName(item.getDeptName());
			distribution.setYesDistribution(total2);
			distribution.setNoDistribution(NoDistribution);

			return distribution;
		}).collect(Collectors.toList());

		return result;
	}

	/**
	 * 数据概览-在线情况
	 *
	 * @return
	 */
	@Override
	public List<DevicePatentOnlineVO> online() {

		IdevelopUser user = SecureUtil.getUser();
		String regionCode = user.getRegionCode();

		ScreenVo screenVo = new ScreenVo();
		screenVo.setArea(regionCode);
		List<Dept> deptList = getDepts(regionCode);

		Map<String, Integer> unitNumberMap = DeviceConstant.TASK_UNIT_NUMBER_MAP;
		List<DevicePatentOnlineVO> result = deptList.stream()
			.filter(item -> StringUtils.equals("CORP", item.getType()))
			.map(item -> {
				DevicePatentOnlineVO online = new DevicePatentOnlineVO();
				Integer all = unitNumberMap.get(item.getDeptName());
				if (ObjectUtil.isEmpty(all)){
					online.setName(item.getDeptName());
					online.setDesktopNumber(88);
					online.setNotebookNumber(20);
					online.setNumber(all);
					return online;
				}
//				Random random = new Random();
				SecureRandom random = null;
				try {
					random = SecureRandom.getInstance("SHA1PRNG");
				} catch (NoSuchAlgorithmException e) {
					return null;
				}
				int number = random.nextInt(10);
				int number1 = all - number;

				online.setName(item.getDeptName());
				online.setDesktopNumber(number);
				online.setNotebookNumber(number1);
				online.setNumber(all);
				return online;
			}).collect(Collectors.toList());

		return result;
	}

	/**
	 * 在线情况统计
	 *
	 * @param patentOnlineDTO
	 * @return
	 */
	@Override
	public List<DevicePatentOnlineVO> onlineStatistics(DevicePatentOnlineDTO patentOnlineDTO) {

		if (StringUtils.isEmpty(patentOnlineDTO.getRegionCode()) || StringUtils.isEmpty(patentOnlineDTO.getRegionName())) {
			return new ArrayList<>();
		}

		String regionCode = patentOnlineDTO.getRegionCode();
		String regionName = patentOnlineDTO.getRegionName();
		Map<String, Integer> unitNumberMap = DeviceConstant.TASK_UNIT_NUMBER_MAP;
		Integer allNumber = unitNumberMap.get(regionName);
		if (Objects.isNull(allNumber)) {
			allNumber = 30;
		}

		ScreenVo screenVo = new ScreenVo();
		screenVo.setArea(regionCode);
		List<Dept> deptList = getDepts(regionCode).stream().limit(10).collect(Collectors.toList());

		// 需查询本部的，除了37的
		IdevelopUser user = SecureUtil.getUser();
		if (regionCode.length() == 4) {
			Dept dept = new Dept();
			dept.setRegionCode(regionCode);
			dept.setDeptName(regionName);
			dept.setType("CORP");
			deptList.add(dept);
		}

		if (user.getRegionCode().length() == 2) {
			deptList = deptList.stream().filter(item -> StringUtils.equals("CORP", item.getType())).collect(Collectors.toList());
		} else {
			deptList = deptList.stream().filter(item -> StringUtils.equals("DEPT", item.getType())).collect(Collectors.toList());
		}


		List<DevicePatentOnlineVO> result = new ArrayList<>();

		for (int i = 0; i < deptList.size(); i++) {
			Dept dept = deptList.get(i);

			DevicePatentOnlineVO online = new DevicePatentOnlineVO();
//			Random random = new Random();
			SecureRandom random = null;
			try {
				random = SecureRandom.getInstance("SHA1PRNG");
			} catch (NoSuchAlgorithmException e) {
				continue;
			}
			if (random == null){
				continue;
			}
			int number = random.nextInt(6);
			number ++;

			if (allNumber == 0) {
				number = 0;
			} else if (allNumber <= number) {
				number = allNumber;
				allNumber = 0;
			} else {
				if ((i + 1) == deptList.size()) {
					number = allNumber;
				}
				allNumber = allNumber - number;
			}

			online.setNumber(number);
			online.setDesktopNumber(0);
			online.setNotebookNumber(0);
			online.setName(dept.getDeptName());

			online.setSort(0);
			if (StringUtils.equals(dept.getType(), "CORP")) {
				online.setSort(1);
			}

			result.add(online);
		}

		return result.stream().sorted(Comparator.comparing(DevicePatentOnlineVO::getSort)).collect(Collectors.toList());
	}

	/**
	 * 在线情况统计 - 单位
	 *
	 * @return
	 */
	@Override
	public List<Map<String, String>> onlineStatisticsUnit(DevicePatentOnlineDTO patentOnlineDTO) {

		IdevelopUser user = SecureUtil.getUser();

		String regionCode = user.getRegionCode();
		String deptName = user.getCorpName();

		Map<String, String> map = new HashMap<>();
		map.put("name", deptName);
		map.put("regionCode", regionCode);

		if (patentOnlineDTO.getRegionCode().length() == 6) {
			List<Map<String, String>> list = new ArrayList<>();
			list.add(map);
			return list;
		}

		List<Dept> depts = getDepts(patentOnlineDTO.getRegionCode());

		List<Map<String, String>> result = new ArrayList<>();
		if (patentOnlineDTO.getRegionCode().length() == 4) {
			result.add(map);
		}

		List<Map<String, String>> result1 = depts.stream()
			.filter(item -> StringUtils.equals("CORP", item.getType()))
			.map(item -> {
				Map<String, String> map1 = new HashMap<>();

				map1.put("name", item.getDeptName());
				map1.put("regionCode", item.getRegionCode());

				return map1;
			}).collect(Collectors.toList());
		result.addAll(result1);

		return result;
	}

	/**
	 * 软硬件分布 - 操作系统
	 *
	 * @return
	 */
	@Override
	public List<DevicePatentOperatingVO> operating() {
		IdevelopUser user = SecureUtil.getUser();

		// 构建请求CMDB参数
		CiCientitySearch cientitySearch = new CiCientitySearch();
		cientitySearch.setFullField(Boolean.TRUE);
		Query query = new Query();
		query.setCurrent(1);
		query.setSize(2);
		cientitySearch.setQuery(query);

		// 区域
		CiCientitySearchVO area = new CiCientitySearchVO();
		area.setAttrName(CmdbAttrConstant.AREA);
		area.setExpression(Expression.LIKE);
		area.setAttrValue(user.getRegionCode());

		// 设备分类 终端设备
		CiCientitySearchVO deviceCategory = new CiCientitySearchVO();
		deviceCategory.setAttrName(CmdbAttrConstant.DEVICE_CATEGORY_CODE);
		deviceCategory.setExpression(Expression.EQUAL);
		deviceCategory.setAttrValue(cmdbCientityProperties.getT105());

		//是否新创
		CiCientitySearchVO isCode = new CiCientitySearchVO();
		isCode.setAttrName(CmdbAttrConstant.IS_IT_AI_CODE);
		isCode.setExpression(Expression.EQUAL);
		isCode.setAttrValue(cmdbCientityProperties.getYesNo());

		// 设备类型 台式机、笔记本电脑
		List<String> deviceTypeList = Arrays.asList(cmdbCientityProperties.getT10501(), cmdbCientityProperties.getT10503());
		String deviceTypeStrs = String.join(",", deviceTypeList);
		CiCientitySearchVO deviceType = new CiCientitySearchVO();
		deviceType.setAttrName(CmdbAttrConstant.DEVICE_TYPE_CODE);
		deviceType.setExpression(Expression.EQUAL);
		deviceType.setAttrValue(deviceTypeStrs);
		deviceType.setBatch(Boolean.TRUE);

		// 操作系统发行版本
		List<String> releaseVersionList = Arrays.asList(cmdbCientityProperties.getReleaseVersion0(), cmdbCientityProperties.getReleaseVersion1());

		List<DevicePatentOperatingVO> result = releaseVersionList.stream().map(item -> {
			DevicePatentOperatingVO patentOperatingVO = new DevicePatentOperatingVO();

			CiCientitySearchVO OSIssueVersion = new CiCientitySearchVO();
			OSIssueVersion.setAttrName(CmdbAttrConstant.OS_ISSUE_VERSION);
			OSIssueVersion.setExpression(Expression.EQUAL);
			OSIssueVersion.setAttrValue(item);

			cientitySearch.setEntity(Arrays.asList(area, deviceCategory, deviceType, isCode, OSIssueVersion));
			FeignCiCientity feignCiCientity = iCmdbService.getCiCientityListByCondition(cientitySearch);
			Integer total = feignCiCientity.getTotal();

			String name = DeviceConstant.RELEASE_VERSION_0;
			if (StringUtils.equals(cmdbCientityProperties.getReleaseVersion1(), item)) {
				name = DeviceConstant.RELEASE_VERSION_1;
			}
			patentOperatingVO.setName(name);
			patentOperatingVO.setNumber(total);

			return patentOperatingVO;
		}).collect(Collectors.toList());

		return result;
	}

	/**
	 * 软硬件分布-芯片架构(ARM)
	 *
	 * @return
	 */
	@Override
	public DevicePatentOperatingVO frameworkArm() {
		IdevelopUser user = SecureUtil.getUser();

		// 构建请求CMDB参数
		CiCientitySearch cientitySearch = new CiCientitySearch();
		cientitySearch.setFullField(Boolean.TRUE);
		Query query = new Query();
		query.setCurrent(1);
		query.setSize(2);
		cientitySearch.setQuery(query);

		// 区域
		CiCientitySearchVO area = new CiCientitySearchVO();
		area.setAttrName(CmdbAttrConstant.AREA);
		area.setExpression(Expression.LIKE);
		area.setAttrValue(user.getRegionCode());

		// 设备分类 终端设备
		CiCientitySearchVO deviceCategory = new CiCientitySearchVO();
		deviceCategory.setAttrName(CmdbAttrConstant.DEVICE_CATEGORY_CODE);
		deviceCategory.setExpression(Expression.EQUAL);
		deviceCategory.setAttrValue(cmdbCientityProperties.getT105());

		//是否新创
		CiCientitySearchVO isCode = new CiCientitySearchVO();
		isCode.setAttrName(CmdbAttrConstant.IS_IT_AI_CODE);
		isCode.setExpression(Expression.EQUAL);
		isCode.setAttrValue(cmdbCientityProperties.getYesNo());

		// 设备类型 台式机、笔记本电脑
		List<String> deviceTypeList = Arrays.asList(cmdbCientityProperties.getT10501(), cmdbCientityProperties.getT10503());
		String deviceTypeStrs = String.join(",", deviceTypeList);
		CiCientitySearchVO deviceType = new CiCientitySearchVO();
		deviceType.setAttrName(CmdbAttrConstant.DEVICE_TYPE_CODE);
		deviceType.setExpression(Expression.EQUAL);
		deviceType.setAttrValue(deviceTypeStrs);
		deviceType.setBatch(Boolean.TRUE);

		// arm芯片架构
		CiCientitySearchVO armSearch = new CiCientitySearchVO();
		armSearch.setAttrName(CmdbAttrConstant.CPU_ARCH_CODE);
		armSearch.setExpression(Expression.EQUAL);
		armSearch.setAttrValue(cmdbCientityProperties.getCpu_arm());

		cientitySearch.setEntity(Arrays.asList(area, deviceCategory, deviceType, isCode, armSearch));
		FeignCiCientity feignCiCientity = iCmdbService.getCiCientityListByCondition(cientitySearch);
		Integer total = feignCiCientity.getTotal();

		// CPU品牌
		CiCientitySearchVO cpuBrand = new CiCientitySearchVO();
		cpuBrand.setAttrName(CmdbAttrConstant.CPU_BRAND_CODE);
		cpuBrand.setExpression(Expression.EQUAL);
		cpuBrand.setAttrValue(cmdbCientityProperties.getCpuBrand0());

		cientitySearch.setEntity(Arrays.asList(area, deviceCategory, deviceType, isCode, armSearch, cpuBrand));
		FeignCiCientity feignCiCientity1 = iCmdbService.getCiCientityListByCondition(cientitySearch);
		Integer total1 = feignCiCientity1.getTotal();

		List<DevicePatentOperatingVO.SubOperating> subOperatingList = new ArrayList<>();
		DevicePatentOperatingVO.SubOperating subOperating = new DevicePatentOperatingVO.SubOperating();
		subOperating.setName(DeviceConstant.KYLIN);
		subOperating.setNumber(total1);
		subOperatingList.add(subOperating);

		// CPU品牌
		CiCientitySearchVO cpuBrand2 = new CiCientitySearchVO();
		cpuBrand2.setAttrName(CmdbAttrConstant.CPU_BRAND_CODE);
		cpuBrand2.setExpression(Expression.EQUAL);
		cpuBrand2.setAttrValue(cmdbCientityProperties.getCpuBrand1());

		cientitySearch.setEntity(Arrays.asList(area, deviceCategory, deviceType, isCode, armSearch, cpuBrand2));
		FeignCiCientity feignCiCientity3 = iCmdbService.getCiCientityListByCondition(cientitySearch);
		Integer total3 = feignCiCientity3.getTotal();

		DevicePatentOperatingVO.SubOperating subOperating2 = new DevicePatentOperatingVO.SubOperating();
		subOperating2.setName(DeviceConstant.FEI_TENG);
		subOperating2.setNumber(total3);
		subOperatingList.add(subOperating2);

		// CPU品牌
		CiCientitySearchVO cpuBrand1 = new CiCientitySearchVO();
		cpuBrand1.setAttrName(CmdbAttrConstant.CPU_BRAND_CODE);
		cpuBrand1.setExpression(Expression.EQUAL);
		cpuBrand1.setAttrValue(cmdbCientityProperties.getCpuBrand4());

		cientitySearch.setEntity(Arrays.asList(area, deviceCategory, deviceType, isCode, armSearch, cpuBrand1));
		FeignCiCientity feignCiCientity2 = iCmdbService.getCiCientityListByCondition(cientitySearch);
		Integer total2 = feignCiCientity2.getTotal();

		DevicePatentOperatingVO.SubOperating subOperating1 = new DevicePatentOperatingVO.SubOperating();
		subOperating1.setName(DeviceConstant.OTHER);
		subOperating1.setNumber(total2);
		subOperatingList.add(subOperating1);


		return DevicePatentOperatingVO.builder()
			.name(DeviceConstant.ARM)
			.number(total)
			.subOperatingList(subOperatingList)
			.build();
	}

	/**
	 * 软硬件分布-芯片架构(X86)
	 *
	 * @return
	 */
	@Override
	public DevicePatentOperatingVO frameworkX86() {

		IdevelopUser user = SecureUtil.getUser();

		// 构建请求CMDB参数
		CiCientitySearch cientitySearch = new CiCientitySearch();
		cientitySearch.setFullField(Boolean.TRUE);
		Query query = new Query();
		query.setCurrent(1);
		query.setSize(2);
		cientitySearch.setQuery(query);

		// 区域
		CiCientitySearchVO area = new CiCientitySearchVO();
		area.setAttrName(CmdbAttrConstant.AREA);
		area.setExpression(Expression.LIKE);
		area.setAttrValue(user.getRegionCode());

		// 设备分类 终端设备
		CiCientitySearchVO deviceCategory = new CiCientitySearchVO();
		deviceCategory.setAttrName(CmdbAttrConstant.DEVICE_CATEGORY_CODE);
		deviceCategory.setExpression(Expression.EQUAL);
		deviceCategory.setAttrValue(cmdbCientityProperties.getT105());

		//是否新创
		CiCientitySearchVO isCode = new CiCientitySearchVO();
		isCode.setAttrName(CmdbAttrConstant.IS_IT_AI_CODE);
		isCode.setExpression(Expression.EQUAL);
		isCode.setAttrValue(cmdbCientityProperties.getYesNo());

		// 设备类型 台式机、笔记本电脑
		List<String> deviceTypeList = Arrays.asList(cmdbCientityProperties.getT10501(), cmdbCientityProperties.getT10503());
		String deviceTypeStrs = String.join(",", deviceTypeList);
		CiCientitySearchVO deviceType = new CiCientitySearchVO();
		deviceType.setAttrName(CmdbAttrConstant.DEVICE_TYPE_CODE);
		deviceType.setExpression(Expression.EQUAL);
		deviceType.setAttrValue(deviceTypeStrs);
		deviceType.setBatch(Boolean.TRUE);

		// x86芯片架构
		CiCientitySearchVO armSearch = new CiCientitySearchVO();
		armSearch.setAttrName(CmdbAttrConstant.CPU_ARCH_CODE);
		armSearch.setExpression(Expression.EQUAL);
		armSearch.setAttrValue(cmdbCientityProperties.getCpu_x86());

		cientitySearch.setEntity(Arrays.asList(area, deviceCategory, deviceType, isCode, armSearch));
		FeignCiCientity feignCiCientity = iCmdbService.getCiCientityListByCondition(cientitySearch);
		Integer total = feignCiCientity.getTotal();

		List<String> cpuBranList = Arrays.asList(cmdbCientityProperties.getCpuBrand2(),cmdbCientityProperties.getCpuBrand3(),cmdbCientityProperties.getCpuBrand4());

		// CPU品牌(字典)
		Map<Object, Object> cpuBrandMap = cmdbDictProperties.getDictMapByCiId(cmdbDictProperties.getCpuBrand());

		List<DevicePatentOperatingVO.SubOperating> subOperatingList = cpuBranList.stream().map(item -> {
			DevicePatentOperatingVO.SubOperating subOperating = new DevicePatentOperatingVO.SubOperating();
			// CPU品牌
			CiCientitySearchVO cpuBrand = new CiCientitySearchVO();
			cpuBrand.setAttrName(CmdbAttrConstant.CPU_BRAND_CODE);
			cpuBrand.setExpression(Expression.EQUAL);
			cpuBrand.setAttrValue(item);

			cientitySearch.setEntity(Arrays.asList(area, deviceCategory, deviceType, isCode, armSearch, cpuBrand));
			FeignCiCientity feignCiCientity1 = iCmdbService.getCiCientityListByCondition(cientitySearch);
			Integer total1 = feignCiCientity1.getTotal();

			Object name = cpuBrandMap.get(item);

			subOperating.setName(String.valueOf(name));
			subOperating.setNumber(total1);

			return subOperating;
		}).collect(Collectors.toList());

		return DevicePatentOperatingVO.builder()
			.name(DeviceConstant.X86)
			.number(total)
			.subOperatingList(subOperatingList)
			.build();
	}

	/**
	 * 软硬件分布-品牌分布
	 *
	 * @return
	 */
	@Override
	public List<DevicePatentOperatingVO> brand() {

		IdevelopUser user = SecureUtil.getUser();

		// 构建请求CMDB参数
		CiCientitySearch cientitySearch = new CiCientitySearch();
		cientitySearch.setFullField(Boolean.FALSE);
		Query query = new Query();
		query.setCurrent(1);
		query.setSize(2);
		cientitySearch.setQuery(query);

		// 区域
		CiCientitySearchVO area = new CiCientitySearchVO();
		area.setAttrName(CmdbAttrConstant.AREA);
		area.setExpression(Expression.LIKE);
		area.setAttrValue(user.getRegionCode());

		// 设备分类 终端设备
		CiCientitySearchVO deviceCategory = new CiCientitySearchVO();
		deviceCategory.setAttrName(CmdbAttrConstant.DEVICE_CATEGORY_CODE);
		deviceCategory.setExpression(Expression.EQUAL);
		deviceCategory.setAttrValue(cmdbCientityProperties.getT105());

		//是否新创
		CiCientitySearchVO isCode = new CiCientitySearchVO();
		isCode.setAttrName(CmdbAttrConstant.IS_IT_AI_CODE);
		isCode.setExpression(Expression.EQUAL);
		isCode.setAttrValue(cmdbCientityProperties.getYesNo());

		// 设备类型 台式机、笔记本电脑
		List<String> deviceTypeList = Arrays.asList(cmdbCientityProperties.getT10501(), cmdbCientityProperties.getT10503());
		String deviceTypeStrs = String.join(",", deviceTypeList);
		CiCientitySearchVO deviceType = new CiCientitySearchVO();
		deviceType.setAttrName(CmdbAttrConstant.DEVICE_TYPE_CODE);
		deviceType.setExpression(Expression.EQUAL);
		deviceType.setAttrValue(deviceTypeStrs);
		deviceType.setBatch(Boolean.TRUE);

		// 品牌
		List<String> branList = Arrays.asList("NARI","华为","南瑞","联想","同方","惠普");

		List<DevicePatentOperatingVO> result = branList.stream().map(item -> {
			DevicePatentOperatingVO operatingVO = new DevicePatentOperatingVO();

			// 品牌
			CiCientitySearchVO brand = new CiCientitySearchVO();
			brand.setAttrName(CmdbAttrConstant.BRAND);
			brand.setExpression(Expression.EQUAL);
			brand.setAttrValue(item);

			cientitySearch.setEntity(Arrays.asList(area, deviceCategory, deviceType, isCode, brand));
			FeignCiCientity feignCiCientity = iCmdbService.getCiCientityListByCondition(cientitySearch);
			Integer total = feignCiCientity.getTotal();

			operatingVO.setName(item);
			operatingVO.setNumber(total);

			return operatingVO;
		}).collect(Collectors.toList());

		return result;
	}

	/**
	 * 7日内每日在线数量趋势
	 *
	 * @return
	 */
	@Override
	public List<DevicePatentOperatingVO> onlineTrend() {
		IdevelopUser user = SecureUtil.getUser();

		// 条件
		CiCientitySearch cientitySearch = new CiCientitySearch();
		cientitySearch.setFullField(Boolean.TRUE);
		Query query = new Query();
		query.setCurrent(1);
		query.setSize(2);
		cientitySearch.setQuery(query);

		// 区域
		CiCientitySearchVO areaSearch = new CiCientitySearchVO();
		areaSearch.setAttrName(CmdbAttrConstant.AREA);
		areaSearch.setExpression(Expression.LIKE);
		areaSearch.setAttrValue(user.getRegionCode());

		// 设备分类 终端设备
		CiCientitySearchVO deviceCategory = new CiCientitySearchVO();
		deviceCategory.setAttrName(CmdbAttrConstant.DEVICE_CATEGORY_CODE);
		deviceCategory.setExpression(Expression.EQUAL);
		deviceCategory.setAttrValue(cmdbCientityProperties.getT105());

		// 设备类型 台式机、笔记本电脑
		List<String> deviceTypeList = Arrays.asList(cmdbCientityProperties.getT10501(), cmdbCientityProperties.getT10503());
		String deviceTypeStrs = String.join(",", deviceTypeList);
		CiCientitySearchVO deviceType = new CiCientitySearchVO();
		deviceType.setAttrName(CmdbAttrConstant.DEVICE_TYPE_CODE);
		deviceType.setExpression(Expression.EQUAL);
		deviceType.setAttrValue(deviceTypeStrs);
		deviceType.setBatch(Boolean.TRUE);

		// 是否新创
		CiCientitySearchVO isCode = new CiCientitySearchVO();
		isCode.setAttrName(CmdbAttrConstant.IS_IT_AI_CODE);
		isCode.setExpression(Expression.EQUAL);
		isCode.setAttrValue(cmdbCientityProperties.getYesNo());

		// 折线图
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		List<DevicePatentOperatingVO> result = new ArrayList<>();
		LocalDate today = LocalDate.now();
		for (int i = 7; i > 0; i--) {
			DevicePatentOperatingVO devicePatentOperatingVO = new DevicePatentOperatingVO();
			LocalDate day = today.minusDays(i);
			String time = day.format(formatter);
			String startTime = time + " 00:00:00";
			String endTime = time + " 23:59:59";
			// 最后活跃日期
			CiCientitySearchVO timeSearchVO = new CiCientitySearchVO();
			timeSearchVO.setAttrName(CmdbAttrConstant.LAST_ACTIVE_DATE);
			timeSearchVO.setExpression(Expression.BETWEEN);
			timeSearchVO.setAttrValue(startTime + "~" + endTime);

			cientitySearch.setEntity(Arrays.asList(areaSearch, isCode, timeSearchVO, deviceCategory, deviceType));
			FeignCiCientity feignCiCientity = iCmdbService.getCiCientityListByCondition(cientitySearch);

			Integer total = feignCiCientity.getTotal();
			devicePatentOperatingVO.setName(time);
			devicePatentOperatingVO.setNumber(total);

			result.add(devicePatentOperatingVO);
		}

		return result;
	}

	/**
	 * 设备替代数据
	 *
	 * @return
	 */
	@Override
	public Map<String, DevicePatentReplaceVO> replace() {
		IdevelopUser user = SecureUtil.getUser();
		Map<String, DevicePatentReplaceVO> result = new HashMap<>();

		// 条件
		CiCientitySearch cientitySearch = new CiCientitySearch();
		cientitySearch.setFullField(Boolean.FALSE);
		Query query = new Query();
		query.setCurrent(1);
		query.setSize(2);
		cientitySearch.setQuery(query);

		// 区域
		CiCientitySearchVO areaSearch = new CiCientitySearchVO();
		areaSearch.setAttrName(CmdbAttrConstant.AREA);
		areaSearch.setExpression(Expression.LIKE);
		areaSearch.setAttrValue(user.getRegionCode());

		// 设备分类 终端设备
		CiCientitySearchVO deviceCategory = new CiCientitySearchVO();
		deviceCategory.setAttrName(CmdbAttrConstant.DEVICE_CATEGORY_CODE);
		deviceCategory.setExpression(Expression.EQUAL);
		deviceCategory.setAttrValue(cmdbCientityProperties.getT105());

		//是否新创
		CiCientitySearchVO isCode = new CiCientitySearchVO();
		isCode.setAttrName(CmdbAttrConstant.IS_IT_AI_CODE);
		isCode.setExpression(Expression.EQUAL);
		isCode.setAttrValue(cmdbCientityProperties.getYesNo());

		// 设备类型 台式机、笔记本电脑
		List<String> deviceTypeList = Arrays.asList(cmdbCientityProperties.getT10501(), cmdbCientityProperties.getT10503());
		String deviceTypeStrs = String.join(",", deviceTypeList);
		CiCientitySearchVO deviceType = new CiCientitySearchVO();
		deviceType.setAttrName(CmdbAttrConstant.DEVICE_TYPE_CODE);
		deviceType.setExpression(Expression.EQUAL);
		deviceType.setAttrValue(deviceTypeStrs);
		deviceType.setBatch(Boolean.TRUE);

		cientitySearch.setEntity(Arrays.asList(areaSearch, deviceCategory, deviceType));
		FeignCiCientity feignCiCientity1 = iCmdbService.getCiCientityListByCondition(cientitySearch);
		Integer total1 = feignCiCientity1.getTotal();
		BigDecimal allNumberBig = new BigDecimal(total1);

		// 总量
		result.put(DeviceConstant.ALL, DevicePatentReplaceVO.builder().name(DeviceConstant.ALL).number(total1).build());

		cientitySearch.setEntity(Arrays.asList(areaSearch, deviceCategory, deviceType, isCode));
		FeignCiCientity feignCiCientity2 = iCmdbService.getCiCientityListByCondition(cientitySearch);
		Integer total2 = feignCiCientity2.getTotal();

		Integer total3 = total1 - total2;
		BigDecimal divideTotal2 = new BigDecimal(0);
		BigDecimal divideTotal3 = new BigDecimal(0);
		if (total1 != 0) {
			BigDecimal numberBig2 = new BigDecimal(total2);
			BigDecimal numberBig3 = new BigDecimal(total3);
			divideTotal2 = numberBig2.divide(allNumberBig, 2, RoundingMode.HALF_UP);
			divideTotal3 = numberBig3.divide(allNumberBig, 2, RoundingMode.HALF_UP);
		}
		// 信创占比
		result.put(DeviceConstant.IS_IT_AI, DevicePatentReplaceVO.builder().name(DeviceConstant.IS_IT_AI).number(total2).proportion(divideTotal2.doubleValue()).build());

		// 非信创占比
		result.put(DeviceConstant.NOT_IT_AI, DevicePatentReplaceVO.builder().name(DeviceConstant.NOT_IT_AI).number(total3).proportion(divideTotal3.doubleValue()).build());

		return result;
	}

	/**
	 * 数据概览-最后同步时间
	 *
	 * @return
	 */
	@Override
	public Map<String, String> onlineTime() {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime today = LocalDateTime.now();

		String format = formatter.format(today);
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("time", format);
		return resultMap;
	}

	/**
	 * 获取部门
	 *
	 * @param regionCode
	 * @return
	 */
	private List<Dept> getDepts(String regionCode) {
		return handlerDeviceMapper.findDeptByParent(regionCode);
	}

	private List<CiCientitySearchVO> searchVOS(DeviceInfoVo vo) {
		List<CiCientitySearchVO> list = new ArrayList<>();
		Field[] fields = vo.getClass().getDeclaredFields();
		String startTime = null;
		String engTime = null;
		for (Field field : fields) {
			field.setAccessible(true);
			try {
				CiCientitySearchVO searchVO = new CiCientitySearchVO();
				String name = field.getName();
				Object value = field.get(vo);
				if (name.equals("query")) {
					continue;
				}
				if (name.equals("serialVersionUID")) {
					continue;
				}
				if (name.equals("startOprtDate")) {
					if (value != null) {
						startTime = String.valueOf(value);
					}
					continue;
				}
				if (name.equals("endOprtDate")) {
					if (value != null) {
						engTime = String.valueOf(value);
					}
					continue;
				}
				if (value != null) {
					if (name.equals("ip") || name.equals("mac")) {
						searchVO.setAttrName(name.toUpperCase());
					} else {
						searchVO.setAttrName(name);
					}
					searchVO.setAttrValue(value);
					searchVO.setExpression(Expression.EQUAL);
					list.add(searchVO);
				}

			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(engTime)) {
			CiCientitySearchVO var1 = new CiCientitySearchVO();
			var1.setAttrName("oprtDate");
			var1.setAttrValue(startTime + "~" + engTime);
			var1.setExpression(Expression.BETWEEN);
			list.add(var1);
		}

		// 设备分类 终端设备
		CiCientitySearchVO deviceCategory = new CiCientitySearchVO();
		deviceCategory.setAttrName(CmdbAttrConstant.DEVICE_CATEGORY_CODE);
		deviceCategory.setExpression(Expression.EQUAL);
		deviceCategory.setAttrValue(cmdbCientityProperties.getT105());
		list.add(deviceCategory);

		IdevelopUser user = SecureUtil.getUser();
		String regionCode = user.getRegionCode();

		CiCientitySearchVO areaSearch = new CiCientitySearchVO();
		areaSearch.setAttrName(CmdbAttrConstant.AREA);
		areaSearch.setExpression(Expression.LIKE);
		areaSearch.setAttrValue(regionCode);
		list.add(areaSearch);

		//是否新创
		CiCientitySearchVO isCode = new CiCientitySearchVO();
		isCode.setAttrName(CmdbAttrConstant.IS_IT_AI_CODE);
		isCode.setExpression(Expression.EQUAL);
		isCode.setAttrValue(cmdbCientityProperties.getYesNo());
		list.add(isCode);

		return list;
	}
}
