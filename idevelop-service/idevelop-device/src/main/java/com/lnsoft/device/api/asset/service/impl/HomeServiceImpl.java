package com.lnsoft.device.api.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lnsoft.cmdb.entity.FeignCiCientity;
import com.lnsoft.cmdb.feign.ICmdbClient;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.data.feign.IDeviceWarningClient;
import com.lnsoft.data.vo.WainingDetailVO;
import com.lnsoft.data.vo.WarningCountVO;
import com.lnsoft.device.api.asset.entity.DeviceOldList;
import com.lnsoft.device.api.asset.eums.OldTrendEnum;
import com.lnsoft.device.api.asset.service.IDeviceOldListService;
import com.lnsoft.device.api.asset.service.IHomeService;
import com.lnsoft.device.api.asset.vo.*;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.constant.DeviceConstant;
import com.lnsoft.device.entity.CiCientitySearch;
import com.lnsoft.device.eums.Expression;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.device.props.CmdbDictProperties;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.vo.CiCientitySearchVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HomeServiceImpl implements IHomeService {
	@Resource
	private ICmdbService cmdbService;
	@Resource
	private CmdbCientityProperties cmdbCientityProperties;
	@Resource
	private CmdbDictProperties cmdbDictProperties;
	@Resource
	private IDeviceWarningClient deviceWarningClient;
	@Resource
	private IDeviceOldListService deviceOldListService;
	@Resource
	private ICmdbClient cmdbClient;

	@Override
	public R<DeviceCount> deviceCount() {
		DeviceCount deviceCount = new DeviceCount();
		Query query = new Query();
		query.setCurrent(1);
		query.setSize(10);
		//设备总数
		ArrayList<CiCientitySearchVO> ciCientitySearchVOS = new ArrayList<>();
		FeignCiCientity jsonObject = cmdbService.getCiCientityListByClaccify(ciCientitySearchVOS, query);
		deviceCount.setDeviceCount(jsonObject.getTotal());
		//在运设备
		ArrayList<CiCientitySearchVO> ciCientitySearchVOS1 = new ArrayList<>();
		CiCientitySearchVO searchVO1 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_STATUS_CODE).attrValue(cmdbCientityProperties.getInOperation()).expression(Expression.EQUAL).build();
		ciCientitySearchVOS1.add(searchVO1);
		FeignCiCientity jsonObject1 = cmdbService.getCiCientityListByClaccify(ciCientitySearchVOS1, query);
		deviceCount.setOperationCount(jsonObject1.getTotal());
		//退运在库设备
		ArrayList<CiCientitySearchVO> ciCientitySearchVOS2 = new ArrayList<>();
		CiCientitySearchVO searchVO2 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_STATUS_CODE).attrValue(cmdbCientityProperties.getReturnWarehouse()).expression(Expression.EQUAL).build();
		ciCientitySearchVOS2.add(searchVO2);
		FeignCiCientity jsonObject2 = cmdbService.getCiCientityListByClaccify(ciCientitySearchVOS2, query);
		deviceCount.setReturnedCount(jsonObject2.getTotal());
		//资产总数
		ArrayList<CiCientitySearchVO> ciCientitySearchVOS3 = new ArrayList<>();
		CiCientitySearchVO searchVO3 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_SOURCE_CODE).attrValue(cmdbCientityProperties.getDeviceSource()).expression(Expression.EQUAL).build();
		ciCientitySearchVOS3.add(searchVO3);
		FeignCiCientity jsonObject3 = cmdbService.getCiCientityListByClaccify(ciCientitySearchVOS3, query);
		deviceCount.setAssetCount(jsonObject3.getTotal());
		//已转资数
		ArrayList<CiCientitySearchVO> ciCientitySearchVOS4 = new ArrayList<>();
		CiCientitySearchVO searchVO4 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.ERP_TRANSFER_STATUS).attrValue(cmdbCientityProperties.getErpTransferStatus1()).expression(Expression.EQUAL).build();
		ciCientitySearchVOS4.add(searchVO4);
		FeignCiCientity jsonObject4 = cmdbService.getCiCientityListByClaccify(ciCientitySearchVOS4, query);
		deviceCount.setTransferCount(jsonObject4.getTotal());
		//待转资数
		ArrayList<CiCientitySearchVO> ciCientitySearchVOS5 = new ArrayList<>();
		CiCientitySearchVO searchVO5 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.ERP_TRANSFER_STATUS).attrValue(cmdbCientityProperties.getErpTransferStatus2()).expression(Expression.EQUAL).build();
		ciCientitySearchVOS5.add(searchVO5);
		FeignCiCientity jsonObject5 = cmdbService.getCiCientityListByClaccify(ciCientitySearchVOS5, query);
		deviceCount.setNoTransferCount(jsonObject5.getTotal());
		return R.data(deviceCount);
	}

	@Override
	public R<PatentDeviceCount> distributeCount() {
		PatentDeviceCount patentDeviceCount = new PatentDeviceCount();
		Query query = new Query();
		query.setCurrent(1);
		query.setSize(10);
		ArrayList<CiCientitySearchVO> ciCientitySearchVOS1 = new ArrayList<>();
		//信创总数
		CiCientitySearchVO searchVO1 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.IS_IT_AI_CODE).attrValue(cmdbCientityProperties.getYesNo()).expression(Expression.EQUAL).build();
		ciCientitySearchVOS1.add(searchVO1);
		FeignCiCientity jsonObject1 = cmdbService.getCiCientityListByClaccify(ciCientitySearchVOS1, query);
		patentDeviceCount.setPatentCount(jsonObject1.getTotal());
		//已分发数
		CiCientitySearchVO searchVO2 = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_STATUS_CODE).attrValue(cmdbCientityProperties.getInOperation()).expression(Expression.EQUAL).build();
		ciCientitySearchVOS1.add(searchVO2);
		FeignCiCientity jsonObject2 = cmdbService.getCiCientityListByClaccify(ciCientitySearchVOS1, query);
		patentDeviceCount.setDistributeCount(jsonObject2.getTotal());
		//分发进度
		Double result = (jsonObject2.getTotal().doubleValue() / jsonObject1.getTotal().doubleValue()) * 100;
		DecimalFormat decimalFormat = new DecimalFormat("#.00");
		Double format = Double.parseDouble(decimalFormat.format(result));
		patentDeviceCount.setDistributionProgress(format);
		//信创替代率
		//设备总数
		ArrayList<CiCientitySearchVO> ciCientitySearchVOS = new ArrayList<>();
		FeignCiCientity jsonObject = cmdbService.getCiCientityListByClaccify(ciCientitySearchVOS, query);
		Double patentProbability = jsonObject1.getTotal().doubleValue() / jsonObject.getTotal().doubleValue() * 100;
		Double patentPro = Double.parseDouble(decimalFormat.format(patentProbability));
		patentDeviceCount.setPatentProbability(patentPro);
		return R.data(patentDeviceCount);
	}

	@Override
	public R<DevicePatentOperatingVO> frameworkArm() {
		// 构建请求CMDB参数
		CiCientitySearch cientitySearch = new CiCientitySearch();
		cientitySearch.setFullField(Boolean.TRUE);
		Query query = new Query();
		query.setCurrent(1);
		query.setSize(2);
		cientitySearch.setQuery(query);

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

		cientitySearch.setEntity(Arrays.asList(deviceCategory, deviceType, isCode, armSearch));
		FeignCiCientity feignCiCientity = cmdbService.getCiCientityListByCondition(cientitySearch);
		Integer total = feignCiCientity.getTotal();

		// CPU品牌
		CiCientitySearchVO cpuBrand = new CiCientitySearchVO();
		cpuBrand.setAttrName(CmdbAttrConstant.CPU_BRAND_CODE);
		cpuBrand.setExpression(Expression.EQUAL);
		cpuBrand.setAttrValue(cmdbCientityProperties.getCpuBrand0());

		cientitySearch.setEntity(Arrays.asList(deviceCategory, deviceType, isCode, armSearch, cpuBrand));
		FeignCiCientity feignCiCientity1 = cmdbService.getCiCientityListByCondition(cientitySearch);
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

		cientitySearch.setEntity(Arrays.asList(deviceCategory, deviceType, isCode, armSearch, cpuBrand2));
		FeignCiCientity feignCiCientity3 = cmdbService.getCiCientityListByCondition(cientitySearch);
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

		cientitySearch.setEntity(Arrays.asList(deviceCategory, deviceType, isCode, armSearch, cpuBrand1));
		FeignCiCientity feignCiCientity2 = cmdbService.getCiCientityListByCondition(cientitySearch);
		Integer total2 = feignCiCientity2.getTotal();

		DevicePatentOperatingVO.SubOperating subOperating1 = new DevicePatentOperatingVO.SubOperating();
		subOperating1.setName(DeviceConstant.OTHER);
		subOperating1.setNumber(total2);
		subOperatingList.add(subOperating1);
		return R.data(DevicePatentOperatingVO.builder()
			.name(DeviceConstant.ARM)
			.number(total)
			.subOperatingList(subOperatingList)
			.build());
	}

	@Override
	public R<DevicePatentOperatingVO> frameworkX86() {

		// 构建请求CMDB参数
		CiCientitySearch cientitySearch = new CiCientitySearch();
		cientitySearch.setFullField(Boolean.TRUE);
		Query query = new Query();
		query.setCurrent(1);
		query.setSize(2);
		cientitySearch.setQuery(query);

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

		cientitySearch.setEntity(Arrays.asList(deviceCategory, deviceType, isCode, armSearch));
		FeignCiCientity feignCiCientity = cmdbService.getCiCientityListByCondition(cientitySearch);
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

			cientitySearch.setEntity(Arrays.asList( deviceCategory, deviceType, isCode, armSearch, cpuBrand));
			FeignCiCientity feignCiCientity1 = cmdbService.getCiCientityListByCondition(cientitySearch);
			Integer total1 = feignCiCientity1.getTotal();

			Object name = cpuBrandMap.get(item);

			subOperating.setName(String.valueOf(name));
			subOperating.setNumber(total1);

			return subOperating;
		}).collect(Collectors.toList());

		return R.data(DevicePatentOperatingVO.builder()
			.name(DeviceConstant.X86)
			.number(total)
			.subOperatingList(subOperatingList)
			.build());
	}

	@Override
	public R<WarningCountVO> warningCount() {
		R<WarningCountVO> warningCountR = deviceWarningClient.warningCount();
		WarningCountVO data = warningCountR.getData();
		return R.data(data);
	}

	@Override
	public R<List<WainingDetailVO>> warningDetail() {
		return deviceWarningClient.warningDetail();
	}

	@Override
	public R<List<OldAgeVO>> oldAge() {
		String ups = cmdbCientityProperties.getT10901();
		String battery = cmdbCientityProperties.getT10902();
		String air = cmdbCientityProperties.getT10903();
		ArrayList<String> list = new ArrayList<>();
		list.add(ups);
		list.add(battery);
		list.add(air);
		ArrayList<OldAgeVO> oldAgeVOS = new ArrayList<>();
		for (String type : list) {
			OldAgeVO oldAgeVO = new OldAgeVO();
			if (type.equals(ups)){
				oldAgeVO.setDeviceType("UPS");
			}else if (type.equals(battery)){
				oldAgeVO.setDeviceType("空调");
			}else if (type.equals(air)){
				oldAgeVO.setDeviceType("蓄电池");
			}
			//五年以内
			LambdaQueryWrapper<DeviceOldList> queryWrapper01 = new LambdaQueryWrapper<>();
			queryWrapper01.eq(DeviceOldList::getDeviceType,type).ge(DeviceOldList::getOverAge,-5).le(DeviceOldList::getOverAge,0);
			List<DeviceOldList> result01 = deviceOldListService.list(queryWrapper01);
			//6-8年
			LambdaQueryWrapper<DeviceOldList> queryWrapper02 = new LambdaQueryWrapper<>();
			queryWrapper02.eq(DeviceOldList::getDeviceType,type).le(DeviceOldList::getOverAge,-6).ge(DeviceOldList::getOverAge,-8);
			List<DeviceOldList> result02 = deviceOldListService.list(queryWrapper02);
			//8-10年
			LambdaQueryWrapper<DeviceOldList> queryWrapper03 = new LambdaQueryWrapper<>();
			queryWrapper03.eq(DeviceOldList::getDeviceType,type).le(DeviceOldList::getOverAge,-8).ge(DeviceOldList::getOverAge,-10);
			List<DeviceOldList> result03 = deviceOldListService.list(queryWrapper03);
			//10年以上
			LambdaQueryWrapper<DeviceOldList> queryWrapper04 = new LambdaQueryWrapper<>();
			queryWrapper04.eq(DeviceOldList::getDeviceType,type).lt(DeviceOldList::getOverAge,-10);
			List<DeviceOldList> result04 = deviceOldListService.list(queryWrapper04);
			ArrayList<Integer> strings = new ArrayList<>();
			strings.add(result01.size());
			strings.add(result02.size());
			strings.add(result03.size());
			strings.add(result04.size());
			oldAgeVO.setValueList(strings);
			oldAgeVOS.add(oldAgeVO);
		}
		return R.data(oldAgeVOS);
	}

	@Override
	public R<OldTrend> oldTrend() {
		OldTrendEnum[] values = OldTrendEnum.values();
		//获取分类数据
		R<List<Map<String, Object>>> categoryList = cmdbClient.feignGetCiCientityDictList(cmdbDictProperties.getDeviceClaccify());
		List<Map<String, Object>> categoryListData = categoryList.getData();
		List<String> yearList = new ArrayList<>();
		OldTrend oldTrend = new OldTrend();
		Map<String, List<Integer>> map = new HashMap<>();
		for (Map<String, Object> category : categoryListData) {
			List<Integer> integers = new ArrayList<>();
			for (OldTrendEnum value : values) {
				LambdaQueryWrapper<DeviceOldList> queryWrapper = new LambdaQueryWrapper<>();
				queryWrapper.eq(DeviceOldList::getScoreCycle, value.getCode()).eq(DeviceOldList::getDeviceCategory,category.get("dictKey"));
				List<DeviceOldList> result = deviceOldListService.list(queryWrapper);
				integers.add(result.size());
			}
			map.put(String.valueOf(category.get("dictValue")),integers);
		}
		for (OldTrendEnum value : values) {
			yearList.add(value.getMessage());
		}
		oldTrend.setDataMap(map);
		oldTrend.setYear(yearList);
		return R.data(oldTrend);
	}

	@Override
	public OnlineVO online(String date) {
		ArrayList<String> strings = new ArrayList<>();
		ArrayList<Integer> integers = new ArrayList<>();
		strings.add("第一周");
		strings.add("第二周");
		strings.add("第三周");
		strings.add("第四周");
		if (date.equals("3")){
			integers.add(55);
			integers.add(124);
			integers.add(95);
			integers.add(231);
		}else if (date.equals("4")){
			integers.add(211);
			integers.add(71);
			integers.add(36);
			integers.add(151);
		}else if (date.equals("5")){
			integers.add(123);
			integers.add(321);
			integers.add(240);
			integers.add(222);
		}else if (date.equals("6")){
			integers.add(153);
			integers.add(33);
			integers.add(220);
			integers.add(220);
		}else if (date.equals("7")){
			integers.add(35);
			integers.add(48);
			integers.add(86);
			integers.add(151);
		}else {
			integers.add(224);
			integers.add(224);
			integers.add(114);
			integers.add(99);
		}
		OnlineVO onlineVO = new OnlineVO();
		onlineVO.setNumber(integers);
		onlineVO.setTime(strings);
		return onlineVO;
	}

}
