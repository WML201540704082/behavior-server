package com.lnsoft.device.api.asset.service.impl;

import com.lnsoft.cmdb.entity.FeignCiCientity;
import com.lnsoft.cmdb.feign.ICmdbClient;
import com.lnsoft.common.enums.hussar.OldDeviceAgeEnum;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.asset.dto.OverdueAssetSearchAgeDTO;
import com.lnsoft.device.api.asset.dto.OverdueAssetSearchDTO;
import com.lnsoft.device.api.asset.dto.OverdueAssetSearchListDTO;
import com.lnsoft.device.api.asset.service.IDeviceOperationAgeConfigService;
import com.lnsoft.device.api.asset.service.IDeviceOverdueService;
import com.lnsoft.device.api.asset.vo.BeOverdueAssetsVo;
import com.lnsoft.device.api.asset.vo.OverdueAssetVO;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.eums.Expression;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.device.vo.CiCientitySearchVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xyzadmin
 */
@Service
public class DeviceOverdueServiceImpl implements IDeviceOverdueService {
	@Resource
	private ICmdbService cmdbService;
	@Resource
	private ICmdbClient cmdbClient;
	@Resource
	private CmdbCientityProperties cmdbCientityProperties;
	@Resource
	private IDeviceOperationAgeConfigService ageConfigService;

	@Override
	public List<OverdueAssetVO> overdueDeviceStatistics(OverdueAssetSearchListDTO overdueAssetSearchListDTO) {
		IdevelopUser user = SecureUtil.getUser();
		//获取分类数据
		R<List<Map<String, Object>>> categoryList = cmdbClient.feignGetCiCientityDictList(1097745625841664L);
		List<Map<String, Object>> categoryListData = categoryList.getData();
		Query query = new Query();
		query.setCurrent(1);
		query.setSize(10);
		ArrayList<OverdueAssetVO> overdueAssetVOS = new ArrayList<>();
		//循环cmdb接口
		categoryListData.forEach((category) -> {
			OverdueAssetSearchDTO overdueAssetSearchDTO = new OverdueAssetSearchDTO();
			CiCientitySearchVO searchVO2 = CiCientitySearchVO.builder().expression(Expression.LIKE).attrName(CmdbAttrConstant.AREA).attrValue(user.getRegionCode()).build();
			overdueAssetSearchDTO.setDeviceCategoryCode(String.valueOf(category.get("dictKey")));
			List<CiCientitySearchVO> searchVOS = CiCientitySearchVO.convertCiCientitySearchVO(overdueAssetSearchDTO);
			CiCientitySearchVO searchVO = CiCientitySearchVO.builder().expression(Expression.EQUAL).attrName(CmdbAttrConstant.OLD_MARK).attrValue("1").build();
			searchVOS.add(searchVO);
			searchVOS.add(searchVO2);
			FeignCiCientity jsonObject = cmdbService.getCiCientityList(searchVOS, query);
			OverdueAssetVO assetVO = new OverdueAssetVO();
			assetVO.setDeviceCategory(String.valueOf(category.get("dictValue")));
			assetVO.setDeviceCategoryCode(String.valueOf(category.get("dictKey")));
			assetVO.setDevSize(jsonObject.getTotal());
			overdueAssetVOS.add(assetVO);
		});
		return overdueAssetVOS;
	}

	@Override
	public List<OverdueAssetVO> oldDeviceAgeStatistics(OverdueAssetVO overdueAssetVO) {
		IdevelopUser user = SecureUtil.getUser();
		// 获取投运年限列表
		R<List<Map<String, Object>>> useAgeList = cmdbClient.feignGetCiCientityDictList(1102238379737088L);
		List<Map<String, Object>> useAgeListData = useAgeList.getData();
		// 过滤条件大于4年
		List<Map<String, Object>> ageList = useAgeListData.stream().filter((s) ->
			Integer.parseInt(String.valueOf(s.get("remarkTemp"))) >= OldDeviceAgeEnum.AGE.getAge()
		).collect(Collectors.toList());
		Query query = new Query();
		query.setCurrent(1);
		query.setSize(10);
		ArrayList<OverdueAssetVO> overdueAssetVOS = new ArrayList<>();
		Integer up10 = 0;
		// 循环cmdb
		for (Map<String, Object> age : ageList) {
			OverdueAssetSearchAgeDTO assetVO = new OverdueAssetSearchAgeDTO();
			assetVO.setDeviceCategoryCode(overdueAssetVO.getDeviceCategoryCode());
			List<CiCientitySearchVO> searchVOS = CiCientitySearchVO.convertCiCientitySearchVO(assetVO);
			String year = String.valueOf(age.get("remarkTemp"));
			//组合条件
			CiCientitySearchVO searchVO2 = CiCientitySearchVO.builder().expression(Expression.LIKE).attrName(CmdbAttrConstant.AREA).attrValue(user.getRegionCode()).build();
			CiCientitySearchVO searchVO1 = CiCientitySearchVO.builder().expression(Expression.BETWEEN).attrName(CmdbAttrConstant.USE_AGE).attrValue(year + "~" + year).build();
			CiCientitySearchVO searchVO = CiCientitySearchVO.builder().expression(Expression.EQUAL).attrName(CmdbAttrConstant.OLD_MARK).attrValue("1").build();
			searchVOS.add(searchVO);
			searchVOS.add(searchVO2);
			searchVOS.add(searchVO1);
			FeignCiCientity jsonObject = cmdbService.getCiCientityList(searchVOS, query);
			if (Integer.valueOf(year) >= 10) {
				Integer total = jsonObject.getTotal();
				up10 += total;
			} else {
				OverdueAssetVO overdueAssetData = new OverdueAssetVO();
				overdueAssetData.setUseAge(String.valueOf(age.get("dictValue")));
				overdueAssetData.setDevSize(jsonObject.getTotal());
				overdueAssetVOS.add(overdueAssetData);
			}
		}
		OverdueAssetVO assetVO = new OverdueAssetVO();
		assetVO.setUseAge("10年及以上");
		assetVO.setDevSize(up10);
		overdueAssetVOS.add(assetVO);
		return overdueAssetVOS;
	}

	@Override
	public FeignCiCientity getList(BeOverdueAssetsVo beOverdueVo, Query query) {
		IdevelopUser user = SecureUtil.getUser();
		OverdueAssetSearchListDTO searchListDTO = new OverdueAssetSearchListDTO();
		searchListDTO.setDeviceCategoryCode(beOverdueVo.getDeviceCategoryCode());
		searchListDTO.setDeviceTypeCode(beOverdueVo.getDeviceTypeCode());
		searchListDTO.setDeviceSourceCode(beOverdueVo.getDeviceSource());
		searchListDTO.setDeviceStatusCode(beOverdueVo.getDeviceStatus());
		searchListDTO.setReceiveUnitCode(beOverdueVo.getReceiveUnit());
		searchListDTO.setSn(beOverdueVo.getSn());
		searchListDTO.setUseAge(beOverdueVo.getUseAge());
		CiCientitySearchVO searchVO1 = CiCientitySearchVO.builder().expression(Expression.EQUAL).attrName(CmdbAttrConstant.OLD_MARK).attrValue("1").build();
		CiCientitySearchVO searchVO2 = CiCientitySearchVO.builder().expression(Expression.LIKE).attrName(CmdbAttrConstant.AREA).attrValue(user.getRegionCode()).build();

		try {
			List<CiCientitySearchVO> ciCientitySearchVOS = CiCientitySearchVO.convertCiCientitySearchVO(searchListDTO);
			ciCientitySearchVOS.add(searchVO1);
			ciCientitySearchVOS.add(searchVO2);
			FeignCiCientity jsonObject = cmdbService.getCiCientityListByClaccify(ciCientitySearchVOS, query);
			List<Map<String, Object>> data = jsonObject.getData();
			data.forEach((device) -> {
				Object deviceTypeCode = device.get(CmdbAttrConstant.DEVICE_TYPE_CODE);
				String age = ageConfigService.getOneByDeviceType(String.valueOf(deviceTypeCode));
				device.put("age", age);
			});
			return jsonObject;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
