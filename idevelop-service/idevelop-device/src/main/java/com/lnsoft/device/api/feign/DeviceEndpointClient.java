package com.lnsoft.device.api.feign;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.cmdb.entity.CmdbCiAttr;
import com.lnsoft.cmdb.entity.FeignCiCientity;
import com.lnsoft.cmdb.entity.FeignCmdbCientityGet;
import com.lnsoft.cmdb.entity.FeignCmdbDictCientitySearch;
import com.lnsoft.cmdb.feign.ICmdbClient;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.api.ResultCode;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.core.tool.utils.BeanUtil;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.device.api.cmdb.service.*;
import com.lnsoft.common.enums.hussar.DeviceChangeSubmitEnum;
import com.lnsoft.device.api.operation.service.IDeviceRepairService;
import com.lnsoft.device.api.res.enums.AttrMappingType;
import com.lnsoft.device.api.warehouse.service.IDeviceReturnedService;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.constant.DeviceConstant;
import com.lnsoft.device.dto.*;
import com.lnsoft.device.entity.*;
import com.lnsoft.device.eums.TransactionActionType;
import com.lnsoft.device.feign.IDeviceEndpointClient;
import com.lnsoft.device.props.CmdbDictProperties;
import com.lnsoft.device.utils.OrderNumberUtil;
import com.lnsoft.device.vo.*;
import com.lnsoft.endpoint.fegin.IEndpointClient;
import com.lnsoft.system.entity.Dept;
import com.lnsoft.system.feign.IDeptClient;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xyzadmin
 */
@ApiIgnore
@RestController
public class DeviceEndpointClient implements IDeviceEndpointClient {

	@Resource
	private ICmdbDictCiService cmdbDictCiService;
	@Resource
	private ICmdbService cmdbService;
	@Resource
	private IDeviceRepairService deviceRepairService;
	@Resource
	private IDeviceReturnedService deviceReturnedService;
	@Resource
	private ICmdbCiAttrGradeService cmdbCiAttrGradeService;
	@Resource
	private ICmdbCiAttrService cmdbCiAttrService;
	@Resource
	private IHardwareBasicTreeService hardwareBasicTreeService;
	@Resource
	private ICmdbResourcecenterTypeCiService cmdbResourcecenterTypeCiService;
	@Resource
	private IEndpointClient endpointClient;
	@Resource
	private ICmdbClient cmdbClient;
	@Resource
	private CmdbDictProperties cmdbDictProperties;
	@Resource
	private IDeptClient deptClient;
	@Resource
	private OrderNumberUtil orderNumberUtil;
	/**
	 * 获取所有字典模型id
	 *
	 * @return
	 */
	@Override
	@PostMapping(API_PREFIX + "/enum/dict")
	public R<List<EndpointCmdbDictAllVO>> searchEnumAll(EndpointCmdbDictAllDTO endpointCmdbDictAllDTO) {

		CmdbDictCi cmdbDictCi = new CmdbDictCi();
		QueryWrapper<CmdbDictCi> queryWrapper = Condition.getQueryWrapper(cmdbDictCi);
		Query query = new Query();
		query.setSize(endpointCmdbDictAllDTO.getSize());
		query.setCurrent(endpointCmdbDictAllDTO.getCurrent());

		IPage<CmdbDictCi> pages = cmdbDictCiService.page(Condition.getPage(query), queryWrapper);
		List<CmdbDictCi> cmdbDictCiList = pages.getRecords();
		if (CollectionUtils.isEmpty(cmdbDictCiList)) {
			return R.data(new ArrayList<>());
		}
		List<EndpointCmdbDictAllVO> response = cmdbDictCiList.stream().map(entity -> {
			EndpointCmdbDictAllVO endpointCmdbDictAllVO = new EndpointCmdbDictAllVO();
			endpointCmdbDictAllVO.setEnumCiId(entity.getCiId());
			endpointCmdbDictAllVO.setEnumCiName(entity.getCiName());
			endpointCmdbDictAllVO.setEnumCiLabel(entity.getCiLabel());
			endpointCmdbDictAllVO.setEnumCiAlias(entity.getCiAlias());

			if (entity.getIsExistCascade() == 1) {
				endpointCmdbDictAllVO.setParentCiEnumId(entity.getSelectCiId());
				endpointCmdbDictAllVO.setParentCiEnumName(entity.getSelectCiName());
				endpointCmdbDictAllVO.setParentCiEnumLabel(entity.getCascadeCiInfo());
			}

			return endpointCmdbDictAllVO;
		}).collect(Collectors.toList());

		return R.data(response);
	}

	@Override
	@PostMapping(API_PREFIX + "/all/ciId")
	public R<CmdbDeviceCategoryVO> searchCiType(CmdbDeviceCategoryDTO cmdbDeviceCategoryDTO) {

		Long categoryId = cmdbDeviceCategoryDTO.getCategoryId();
		Long typeId = cmdbDeviceCategoryDTO.getTypeId();

		HardwareBasicTree hardwareBasicTree = new HardwareBasicTree();
		hardwareBasicTree.setDeviceClaccify(categoryId.toString());
		if (Objects.nonNull(typeId)) {
			hardwareBasicTree.setDeviceType(typeId.toString());
		}

		QueryWrapper<HardwareBasicTree> queryWrapper = Condition.getQueryWrapper(hardwareBasicTree);

		if (Objects.isNull(typeId)) {
			queryWrapper.lambda().isNull(HardwareBasicTree::getDeviceType);
		}

		HardwareBasicTree hardwareBasicTreeDetail = hardwareBasicTreeService.getOne(queryWrapper);

		if (Objects.isNull(hardwareBasicTreeDetail)) {
			return R.data(new CmdbDeviceCategoryVO());
		}

		CmdbDeviceCategoryVO cmdbDeviceCategoryVO = new CmdbDeviceCategoryVO();
		cmdbDeviceCategoryVO.setCiId(hardwareBasicTreeDetail.getCiId());
		cmdbDeviceCategoryVO.setCiName(hardwareBasicTreeDetail.getCiName());
		cmdbDeviceCategoryVO.setCiLabel(hardwareBasicTreeDetail.getCiLabel());

		return R.data(cmdbDeviceCategoryVO);
	}

	@Override
	@PostMapping(API_PREFIX + "/all/attr")
	public R<List<CmdbCiAttrFegin>> searchAttr(EndpointSearchAttrDTO endpointSearchAttrDTO) {

		Long ciId = endpointSearchAttrDTO.getCiId();
		HardwareBasicTree hardwareBasicTree = new HardwareBasicTree();
		hardwareBasicTree.setCiId(endpointSearchAttrDTO.getCiId());

		QueryWrapper<HardwareBasicTree> hardwareBasicTreeQueryWrapper = Condition.getQueryWrapper(hardwareBasicTree);

		HardwareBasicTree hardwareBasicTreeDetail = hardwareBasicTreeService.getOne(hardwareBasicTreeQueryWrapper);

		if (ObjectUtils.isEmpty(hardwareBasicTreeDetail)) {
			throw new RuntimeException("未查询到模型ID为: " + ciId + " 的模型, 请查询有效模型ID");
		}

		CmdbCiAttrGrade cmdbCiAttrGrade = new CmdbCiAttrGrade();

		QueryWrapper<CmdbCiAttrGrade> queryWrapper = Condition.getQueryWrapper(cmdbCiAttrGrade);
		queryWrapper.lambda().like(CmdbCiAttrGrade::getAttrCiId, ciId + "-")
			.orderByDesc(CmdbCiAttrGrade::getId);

		List<CmdbCiAttrGrade> cmdbCiAttrGradeList = cmdbCiAttrGradeService.list(queryWrapper);

		if (CollectionUtils.isEmpty(cmdbCiAttrGradeList)) {

			Long parentCiId = hardwareBasicTreeDetail.getParentCiId();
			QueryWrapper<CmdbCiAttrGrade> queryWrapper1 = Condition.getQueryWrapper(cmdbCiAttrGrade);
			queryWrapper1.lambda().like(CmdbCiAttrGrade::getAttrCiId, parentCiId + "-")
				.orderByDesc(CmdbCiAttrGrade::getId);
			cmdbCiAttrGradeList = cmdbCiAttrGradeService.list(queryWrapper);
		}

		if (CollectionUtils.isEmpty(cmdbCiAttrGradeList)) {
			throw new RuntimeException("未查询到模型ID为: " + ciId + " 的模型属性, 请查询有效模型ID");
		}

		Map<String, String> ciGroupNameMap = DeviceConstant.CI_GROUP_CODE_MAP;
		Map<String, Integer> ciGroupSortMap = DeviceConstant.CI_GROUP_SORT_MAP;
		Map<String, List<CmdbCiAttrGrade>> cmdbCiAttrGradeMap = cmdbCiAttrGradeList.stream().collect(Collectors.groupingBy(CmdbCiAttrGrade::getGroupName));
		List<CmdbCiAttrFegin> result = cmdbCiAttrGradeMap.entrySet().stream().map(entry -> {

			String key = entry.getKey();
			List<CmdbCiAttrGrade> value = entry.getValue();

			CmdbCiAttrFegin cmdbCiAttrFegin = new CmdbCiAttrFegin();
			cmdbCiAttrFegin.setGroupName(key);

			String groupCode = ciGroupNameMap.get(key);
			cmdbCiAttrFegin.setGroupCode(groupCode);
			Integer groupSort = ciGroupSortMap.get(key);
			cmdbCiAttrFegin.setGroupSort(groupSort);
			List<CmdbCiAttrFegin.CmdbCiAttrUp> cmdbCiAttrUpList = new ArrayList<>();
			for (CmdbCiAttrGrade ciAttrGrade : value) {
				CmdbCiAttrFegin.CmdbCiAttrUp cmdbCiAttrUp = new CmdbCiAttrFegin.CmdbCiAttrUp();
				cmdbCiAttrUp.setAttrId(ciAttrGrade.getId());
				cmdbCiAttrUp.setAttrCode(ciAttrGrade.getName());
				cmdbCiAttrUp.setAttrLabel(ciAttrGrade.getLabel());
				cmdbCiAttrUp.setAttrType(ciAttrGrade.getType());
				cmdbCiAttrUp.setAttrTypeText(ciAttrGrade.getTypeText());
				cmdbCiAttrUp.setTargetCiId(ciAttrGrade.getTargetCiId());
				String name = ciAttrGrade.getName();
				R<String> alias = endpointClient.getNameAlias(name);
				if (ObjectUtil.isNotEmpty(alias.getData())){
					cmdbCiAttrUp.setNameAlias(alias.getData());
				}
				cmdbCiAttrUpList.add(cmdbCiAttrUp);
			}
//			List<CmdbCiAttrFegin.CmdbCiAttrUp> cmdbCiAttrUpList = value.stream().map(entryValue -> {
////				CmdbCiAttrFegin.CmdbCiAttrUp cmdbCiAttrUp = new CmdbCiAttrFegin.CmdbCiAttrUp();
////				cmdbCiAttrUp.setAttrId(entryValue.getId());
////				cmdbCiAttrUp.setAttrCode(entryValue.getName());
////				cmdbCiAttrUp.setAttrLabel(entryValue.getLabel());
////				cmdbCiAttrUp.setAttrType(entryValue.getType());
////				cmdbCiAttrUp.setAttrTypeText(entryValue.getTypeText());
////				cmdbCiAttrUp.setTargetCiId(entryValue.getTargetCiId());
////				String name = entryValue.getName();
////				String nameAlias = NameAliasConfiguration.getNameAlias(name);
////				cmdbCiAttrUp.setNameAlias(nameAlias);
////				return cmdbCiAttrUp;
////			}).collect(Collectors.toList());
			cmdbCiAttrFegin.setAttrData(cmdbCiAttrUpList);

			return cmdbCiAttrFegin;
		}).collect(Collectors.toList());

		List<CmdbCiAttrFegin> resultSort = result.stream().sorted(Comparator.comparing(CmdbCiAttrFegin::getGroupSort)).collect(Collectors.toList());

		return R.data(resultSort);
	}

	@Override
	@PostMapping(API_PREFIX + "/ledger")
	public R<FeignCiCientityVO> getDeviceList(CiCientitySearch cientitySearch) {
		FeignCiCientity feignCiCientity = cmdbService.getCiCientityListByCondition(cientitySearch);
		FeignCiCientityVO feignCiCientityVO = Convert.convert(new TypeReference<FeignCiCientityVO>() {
		}, feignCiCientity);

		return R.data(feignCiCientityVO);
	}

	@Override
	@GetMapping(API_PREFIX + "/getByCiId")
	public R<HardwareBasicTree> getByCiId(Long ciId) {
		HardwareBasicTree hardwareBasicTree = new HardwareBasicTree();
		hardwareBasicTree.setCiId(ciId);
		hardwareBasicTree.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);

		QueryWrapper<HardwareBasicTree> queryWrapper = Condition.getQueryWrapper(hardwareBasicTree);
		HardwareBasicTree result = hardwareBasicTreeService.getOne(queryWrapper);
		return R.data(result);
	}

	@Override
	@PostMapping(API_PREFIX + "/cientityDetail")
	public R<Map<String, Object>> cientityDetail(FeignCmdbCientityDetailDTO feignCmdbCientityDetailDTO) {
		FeignCmdbCientityGet feignCmdbCientityGet = new FeignCmdbCientityGet();
		BeanUtil.copy(feignCmdbCientityDetailDTO, feignCmdbCientityGet);
		return R.data(cmdbService.getCientityDetail(feignCmdbCientityGet));
	}

	@Override
	@PostMapping(API_PREFIX + "/repair")
	public R submit(@Valid DeviceRepairDTO deviceRepairDTO,@RequestHeader(name = "Idevelop-Auth") String token) throws Exception {
		deviceRepairDTO.setSubmitType(DeviceChangeSubmitEnum.WAIT_APPROVAL.getCode());
		return deviceRepairService.submit(deviceRepairDTO);
	}

	@Override
	@PostMapping(API_PREFIX + "/returned")
	public R<DeviceReturnedVO> deviceReturnedSubmit(DeviceReturnedDTO dto) throws Exception {
		return deviceReturnedService.deviceReturnedSubmit(dto);
	}

	@Override
	@GetMapping(API_PREFIX + "/asset/stand/ciid")
	public R<Map<String, CmdbCiAttrVO>> getAssetStandCiId(Long ciId, Boolean isCommonCi) {

		if (isCommonCi) {
			ciId = cmdbResourcecenterTypeCiService.getAssetStandCiId();
			CmdbCiAttr cmdbCiAttr = new CmdbCiAttr();
			cmdbCiAttr.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
			QueryWrapper<CmdbCiAttr> queryWrapper = Condition.getQueryWrapper(cmdbCiAttr);
			queryWrapper.lambda().like(CmdbCiAttr::getAttrCiId, ciId + "-")
				.orderByDesc(CmdbCiAttr::getAttrId);
			List<CmdbCiAttr> cmdbCiAttrList = cmdbCiAttrService.list(queryWrapper);

			Map<String, CmdbCiAttrVO> map = new HashMap<>();
			for (CmdbCiAttr ciAttr : cmdbCiAttrList) {
				CmdbCiAttrVO cmdbCiAttrVO = new CmdbCiAttrVO();
				cmdbCiAttrVO.setAttrId(ciAttr.getAttrId());
				cmdbCiAttrVO.setTargetCiId(ciAttr.getTargetCiId());
				cmdbCiAttrVO.setAttrType(ciAttr.getAttrType());
				map.put(ciAttr.getAttrName(), cmdbCiAttrVO);
			}
			return R.data(map);
		}

		CmdbCiAttrGrade cmdbCiAttrGrade = new CmdbCiAttrGrade();
		QueryWrapper<CmdbCiAttrGrade> queryWrapper = Condition.getQueryWrapper(cmdbCiAttrGrade);
		queryWrapper.lambda().like(CmdbCiAttrGrade::getAttrCiId, ciId + "-")
			.orderByDesc(CmdbCiAttrGrade::getId);
		List<CmdbCiAttrGrade> cmdbCiAttrGradeList = cmdbCiAttrGradeService.list(queryWrapper);
		Map<String, CmdbCiAttrVO> map = new HashMap<>();
		for (CmdbCiAttrGrade ciAttrGrade : cmdbCiAttrGradeList) {
			CmdbCiAttrVO cmdbCiAttrVO = new CmdbCiAttrVO();
			cmdbCiAttrVO.setAttrId(ciAttrGrade.getId());
			cmdbCiAttrVO.setTargetCiId(ciAttrGrade.getTargetCiId());
			cmdbCiAttrVO.setAttrType(ciAttrGrade.getType());
			map.put(ciAttrGrade.getName(), cmdbCiAttrVO);
		}
		return R.data(map);

	}

	@Override
	@PostMapping(API_PREFIX + "/ledger/add")
	public R<Map<String, Object>> cientityBatchsave(CmdbLedgerDTO cmdbLedgerDTO) {
		Long ciId = cmdbLedgerDTO.getCiId();
		Map<String, Map<String, Object>> entity = cmdbLedgerDTO.getEntityInsert();
		TransactionActionType actionType = cmdbLedgerDTO.getActionType();
		Set<String> keySet = entity.keySet();
		for (String key : keySet) {
			Map<String, Object> stringObjectMap = entity.get(key);
			// 处理品牌系列型号
			//制造商
			if (stringObjectMap.containsKey(CmdbAttrConstant.MAKER) && !stringObjectMap.containsKey(CmdbAttrConstant.MAKER_CODE)){
				String maker = String.valueOf(stringObjectMap.get(CmdbAttrConstant.MAKER));
				//根据名称获取编码
				FeignCmdbDictCientitySearch search = new FeignCmdbDictCientitySearch();
				search.setCiId(cmdbDictProperties.getMaker());
				search.setCurrentPage(1);
				search.setPageSize(10);
				search.setDictValue(maker);
				R<FeignCiCientity> feignCiCientityR = cmdbClient.feignGetCiEntityDictPage(search);
				if (feignCiCientityR.getCode()== ResultCode.SUCCESS.getCode() && ObjectUtil.isNotEmpty(feignCiCientityR.getData().getData())){
					String dictKey = String.valueOf(feignCiCientityR.getData().getData().get(0).get("dictKey"));
					stringObjectMap.put(CmdbAttrConstant.MAKER_CODE,dictKey);
				}
			}else if (!stringObjectMap.containsKey(CmdbAttrConstant.MAKER) && stringObjectMap.containsKey(CmdbAttrConstant.MAKER_CODE)){
				String makerCode = String.valueOf(stringObjectMap.get(CmdbAttrConstant.MAKER_CODE));
				//根据编码获取名称
				FeignCmdbDictCientitySearch search = new FeignCmdbDictCientitySearch();
				search.setCiId(cmdbDictProperties.getMaker());
				search.setCurrentPage(1);
				search.setPageSize(10);
				search.setDictKey(makerCode);
				R<FeignCiCientity> feignCiCientityR = cmdbClient.feignGetCiEntityDictPage(search);
				if (feignCiCientityR.getCode()== ResultCode.SUCCESS.getCode() && ObjectUtil.isNotEmpty(feignCiCientityR.getData().getData())){
					String dictValue = String.valueOf(feignCiCientityR.getData().getData().get(0).get("dictValue"));
					stringObjectMap.put(CmdbAttrConstant.MAKER,dictValue);
				}
			}
			//品牌
			if (stringObjectMap.containsKey(CmdbAttrConstant.BRAND) && !stringObjectMap.containsKey(CmdbAttrConstant.BRAND_CODE)){
				String brand = String.valueOf(stringObjectMap.get(CmdbAttrConstant.BRAND));
				//根据名称获取编码
				FeignCmdbDictCientitySearch search = new FeignCmdbDictCientitySearch();
				search.setCiId(cmdbDictProperties.getBrand());
				search.setCurrentPage(1);
				search.setPageSize(10);
				search.setDictValue(brand);
				R<FeignCiCientity> feignCiCientityR = cmdbClient.feignGetCiEntityDictPage(search);
				if (feignCiCientityR.getCode()== ResultCode.SUCCESS.getCode() && ObjectUtil.isNotEmpty(feignCiCientityR.getData().getData())){
					String dictKey = String.valueOf(feignCiCientityR.getData().getData().get(0).get("dictKey"));
					stringObjectMap.put(CmdbAttrConstant.BRAND_CODE,dictKey);
				}
			}else if (!stringObjectMap.containsKey(CmdbAttrConstant.BRAND) && stringObjectMap.containsKey(CmdbAttrConstant.BRAND_CODE)){
				String brandCode = String.valueOf(stringObjectMap.get(CmdbAttrConstant.BRAND_CODE));
				//根据编码获取名称
				FeignCmdbDictCientitySearch search = new FeignCmdbDictCientitySearch();
				search.setCiId(cmdbDictProperties.getBrand());
				search.setCurrentPage(1);
				search.setPageSize(10);
				search.setDictKey(brandCode);
				R<FeignCiCientity> feignCiCientityR = cmdbClient.feignGetCiEntityDictPage(search);
				if (feignCiCientityR.getCode()== ResultCode.SUCCESS.getCode() && ObjectUtil.isNotEmpty(feignCiCientityR.getData().getData())){
					String dictValue = String.valueOf(feignCiCientityR.getData().getData().get(0).get("dictValue"));
					stringObjectMap.put(CmdbAttrConstant.BRAND,dictValue);
				}
			}
			//系列
			if (stringObjectMap.containsKey(CmdbAttrConstant.SERIES) && !stringObjectMap.containsKey(CmdbAttrConstant.SERIES_CODE)){
				String series = String.valueOf(stringObjectMap.get(CmdbAttrConstant.SERIES));
				//根据名称获取编码
				FeignCmdbDictCientitySearch search = new FeignCmdbDictCientitySearch();
				search.setCiId(cmdbDictProperties.getSeries());
				search.setCurrentPage(1);
				search.setPageSize(10);
				search.setDictValue(series);
				R<FeignCiCientity> feignCiCientityR = cmdbClient.feignGetCiEntityDictPage(search);
				if (feignCiCientityR.getCode()== ResultCode.SUCCESS.getCode() && ObjectUtil.isNotEmpty(feignCiCientityR.getData().getData())){
					String dictKey = String.valueOf(feignCiCientityR.getData().getData().get(0).get("dictKey"));
					stringObjectMap.put(CmdbAttrConstant.SERIES_CODE,dictKey);
				}
			}else if (!stringObjectMap.containsKey(CmdbAttrConstant.SERIES) && stringObjectMap.containsKey(CmdbAttrConstant.SERIES_CODE)){
				String seriesCode = String.valueOf(stringObjectMap.get(CmdbAttrConstant.SERIES_CODE));
				//根据编码获取名称
				FeignCmdbDictCientitySearch search = new FeignCmdbDictCientitySearch();
				search.setCiId(cmdbDictProperties.getSeries());
				search.setCurrentPage(1);
				search.setPageSize(10);
				search.setDictKey(seriesCode);
				R<FeignCiCientity> feignCiCientityR = cmdbClient.feignGetCiEntityDictPage(search);
				if (feignCiCientityR.getCode()== ResultCode.SUCCESS.getCode() && ObjectUtil.isNotEmpty(feignCiCientityR.getData().getData())){
					String dictValue = String.valueOf(feignCiCientityR.getData().getData().get(0).get("dictValue"));
					stringObjectMap.put(CmdbAttrConstant.SERIES,dictValue);
				}
			}
			//型号
			if (stringObjectMap.containsKey(CmdbAttrConstant.DEVICE_MODEL) && !stringObjectMap.containsKey(CmdbAttrConstant.DEVICE_MODEL_CODE)){
				String deviceModel = String.valueOf(stringObjectMap.get(CmdbAttrConstant.DEVICE_MODEL));
				//根据名称获取编码
				FeignCmdbDictCientitySearch search = new FeignCmdbDictCientitySearch();
				search.setCiId(cmdbDictProperties.getModel());
				search.setCurrentPage(1);
				search.setPageSize(10);
				search.setDictValue(deviceModel);
				R<FeignCiCientity> feignCiCientityR = cmdbClient.feignGetCiEntityDictPage(search);
				if (feignCiCientityR.getCode()== ResultCode.SUCCESS.getCode() && ObjectUtil.isNotEmpty(feignCiCientityR.getData().getData())){
					String dictKey = String.valueOf(feignCiCientityR.getData().getData().get(0).get("dictKey"));
					stringObjectMap.put(CmdbAttrConstant.DEVICE_MODEL_CODE,dictKey);
				}
			}else if (!stringObjectMap.containsKey(CmdbAttrConstant.DEVICE_MODEL) && stringObjectMap.containsKey(CmdbAttrConstant.DEVICE_MODEL_CODE)){
				String deviceModelCode = String.valueOf(stringObjectMap.get(CmdbAttrConstant.DEVICE_MODEL_CODE));
				//根据编码获取名称
				FeignCmdbDictCientitySearch search = new FeignCmdbDictCientitySearch();
				search.setCiId(cmdbDictProperties.getModel());
				search.setCurrentPage(1);
				search.setPageSize(10);
				search.setDictKey(deviceModelCode);
				R<FeignCiCientity> feignCiCientityR = cmdbClient.feignGetCiEntityDictPage(search);
				if (feignCiCientityR.getCode()== ResultCode.SUCCESS.getCode() && ObjectUtil.isNotEmpty(feignCiCientityR.getData().getData())){
					String dictValue = String.valueOf(feignCiCientityR.getData().getData().get(0).get("dictValue"));
					stringObjectMap.put(CmdbAttrConstant.DEVICE_MODEL,dictValue);
				}
			}
			//产权单位编码和名称
			if (!stringObjectMap.containsKey(CmdbAttrConstant.OWNER_UNIT) || !stringObjectMap.containsKey(CmdbAttrConstant.OWNER_UNIT_CODE)){
				String area = String.valueOf(stringObjectMap.get(CmdbAttrConstant.AREA));
				Dept dept = new Dept();
				dept.setRegionCode(area);
				R<Dept> deptR = deptClient.detail(dept);
				if (deptR.getCode() == ResultCode.SUCCESS.getCode()){
					String ownerUnitCode = String.valueOf(deptR.getData().getId());
					String ownerUnit = String.valueOf(deptR.getData().getFullName());
					stringObjectMap.put(CmdbAttrConstant.OWNER_UNIT,ownerUnit);
					stringObjectMap.put(CmdbAttrConstant.OWNER_UNIT_CODE,ownerUnitCode);
				}
			}
			//数据来源字段
			stringObjectMap.put(CmdbAttrConstant.DATA_SOURCE,"动环");
			//设备编码
			if (!stringObjectMap.containsKey(CmdbAttrConstant.DEVICE_CODE)){
				stringObjectMap.put(CmdbAttrConstant.DEVICE_CODE,orderNumberUtil.generateCode(String.valueOf(stringObjectMap.get(CmdbAttrConstant.DEVICE_TYPE_CODE)),String.valueOf(stringObjectMap.get(CmdbAttrConstant.AREA))));
			}
		}
		Map<String, Object> resultMap = cmdbService.cientityBatchsave(ciId, entity, actionType);
		return R.data(resultMap);
	}

	@Override
	@PostMapping(API_PREFIX + "/ledger/update")
	public R<Map<String, Object>> cientityBatchupdate(CmdbLedgerDTO cmdbLedgerDTO) {
		Map<Long, Map<String, Object>> entityUpdate = cmdbLedgerDTO.getEntityUpdate();
		Set<Long> keySet = entityUpdate.keySet();
		for (Long aLong : keySet) {
			Map<String, Object> stringObjectMap = entityUpdate.get(aLong);
			//如果包含品牌系类型号，设备分类设备类型，产权单位，
			if (stringObjectMap.containsKey(CmdbAttrConstant.MAKER) && !stringObjectMap.containsKey(CmdbAttrConstant.MAKER_CODE)){
				String maker = String.valueOf(stringObjectMap.get(CmdbAttrConstant.MAKER));
				//根据名称获取编码
				FeignCmdbDictCientitySearch search = new FeignCmdbDictCientitySearch();
				search.setCiId(cmdbDictProperties.getMaker());
				search.setCurrentPage(1);
				search.setPageSize(10);
				search.setDictValue(maker);
				R<FeignCiCientity> feignCiCientityR = cmdbClient.feignGetCiEntityDictPage(search);
				if (feignCiCientityR.getCode()== ResultCode.SUCCESS.getCode() && ObjectUtil.isNotEmpty(feignCiCientityR.getData().getData())){
					String dictKey = String.valueOf(feignCiCientityR.getData().getData().get(0).get("dictKey"));
					stringObjectMap.put(CmdbAttrConstant.MAKER_CODE,dictKey);
				}
			}else if (!stringObjectMap.containsKey(CmdbAttrConstant.MAKER) && stringObjectMap.containsKey(CmdbAttrConstant.MAKER_CODE)){
				String makerCode = String.valueOf(stringObjectMap.get(CmdbAttrConstant.MAKER_CODE));
				//根据编码获取名称
				FeignCmdbDictCientitySearch search = new FeignCmdbDictCientitySearch();
				search.setCiId(cmdbDictProperties.getMaker());
				search.setCurrentPage(1);
				search.setPageSize(10);
				search.setDictKey(makerCode);
				R<FeignCiCientity> feignCiCientityR = cmdbClient.feignGetCiEntityDictPage(search);
				if (feignCiCientityR.getCode()== ResultCode.SUCCESS.getCode() && ObjectUtil.isNotEmpty(feignCiCientityR.getData().getData())){
					String dictValue = String.valueOf(feignCiCientityR.getData().getData().get(0).get("dictValue"));
					stringObjectMap.put(CmdbAttrConstant.MAKER,dictValue);
				}
			}
			//品牌
			if (stringObjectMap.containsKey(CmdbAttrConstant.BRAND) && !stringObjectMap.containsKey(CmdbAttrConstant.BRAND_CODE)){
				String brand = String.valueOf(stringObjectMap.get(CmdbAttrConstant.BRAND));
				//根据名称获取编码
				FeignCmdbDictCientitySearch search = new FeignCmdbDictCientitySearch();
				search.setCiId(cmdbDictProperties.getBrand());
				search.setCurrentPage(1);
				search.setPageSize(10);
				search.setDictValue(brand);
				R<FeignCiCientity> feignCiCientityR = cmdbClient.feignGetCiEntityDictPage(search);
				if (feignCiCientityR.getCode()== ResultCode.SUCCESS.getCode() && ObjectUtil.isNotEmpty(feignCiCientityR.getData().getData())){
					String dictKey = String.valueOf(feignCiCientityR.getData().getData().get(0).get("dictKey"));
					stringObjectMap.put(CmdbAttrConstant.BRAND_CODE,dictKey);
				}
			}else if (!stringObjectMap.containsKey(CmdbAttrConstant.BRAND) && stringObjectMap.containsKey(CmdbAttrConstant.BRAND_CODE)){
				String brandCode = String.valueOf(stringObjectMap.get(CmdbAttrConstant.BRAND_CODE));
				//根据编码获取名称
				FeignCmdbDictCientitySearch search = new FeignCmdbDictCientitySearch();
				search.setCiId(cmdbDictProperties.getBrand());
				search.setCurrentPage(1);
				search.setPageSize(10);
				search.setDictKey(brandCode);
				R<FeignCiCientity> feignCiCientityR = cmdbClient.feignGetCiEntityDictPage(search);
				if (feignCiCientityR.getCode()== ResultCode.SUCCESS.getCode() && ObjectUtil.isNotEmpty(feignCiCientityR.getData().getData())){
					String dictValue = String.valueOf(feignCiCientityR.getData().getData().get(0).get("dictValue"));
					stringObjectMap.put(CmdbAttrConstant.BRAND,dictValue);
				}
			}
			//系列
			if (stringObjectMap.containsKey(CmdbAttrConstant.SERIES) && !stringObjectMap.containsKey(CmdbAttrConstant.SERIES_CODE)){
				String series = String.valueOf(stringObjectMap.get(CmdbAttrConstant.SERIES));
				//根据名称获取编码
				FeignCmdbDictCientitySearch search = new FeignCmdbDictCientitySearch();
				search.setCiId(cmdbDictProperties.getSeries());
				search.setCurrentPage(1);
				search.setPageSize(10);
				search.setDictValue(series);
				R<FeignCiCientity> feignCiCientityR = cmdbClient.feignGetCiEntityDictPage(search);
				if (feignCiCientityR.getCode()== ResultCode.SUCCESS.getCode() && ObjectUtil.isNotEmpty(feignCiCientityR.getData().getData())){
					String dictKey = String.valueOf(feignCiCientityR.getData().getData().get(0).get("dictKey"));
					stringObjectMap.put(CmdbAttrConstant.SERIES_CODE,dictKey);
				}
			}else if (!stringObjectMap.containsKey(CmdbAttrConstant.SERIES) && stringObjectMap.containsKey(CmdbAttrConstant.SERIES_CODE)){
				String seriesCode = String.valueOf(stringObjectMap.get(CmdbAttrConstant.SERIES_CODE));
				//根据编码获取名称
				FeignCmdbDictCientitySearch search = new FeignCmdbDictCientitySearch();
				search.setCiId(cmdbDictProperties.getSeries());
				search.setCurrentPage(1);
				search.setPageSize(10);
				search.setDictKey(seriesCode);
				R<FeignCiCientity> feignCiCientityR = cmdbClient.feignGetCiEntityDictPage(search);
				if (feignCiCientityR.getCode()== ResultCode.SUCCESS.getCode() && ObjectUtil.isNotEmpty(feignCiCientityR.getData().getData())){
					String dictValue = String.valueOf(feignCiCientityR.getData().getData().get(0).get("dictValue"));
					stringObjectMap.put(CmdbAttrConstant.SERIES,dictValue);
				}
			}
			//型号
			if (stringObjectMap.containsKey(CmdbAttrConstant.DEVICE_MODEL) && !stringObjectMap.containsKey(CmdbAttrConstant.DEVICE_MODEL_CODE)){
				String deviceModel = String.valueOf(stringObjectMap.get(CmdbAttrConstant.DEVICE_MODEL));
				//根据名称获取编码
				FeignCmdbDictCientitySearch search = new FeignCmdbDictCientitySearch();
				search.setCiId(cmdbDictProperties.getModel());
				search.setCurrentPage(1);
				search.setPageSize(10);
				search.setDictValue(deviceModel);
				R<FeignCiCientity> feignCiCientityR = cmdbClient.feignGetCiEntityDictPage(search);
				if (feignCiCientityR.getCode()== ResultCode.SUCCESS.getCode() && ObjectUtil.isNotEmpty(feignCiCientityR.getData().getData())){
					String dictKey = String.valueOf(feignCiCientityR.getData().getData().get(0).get("dictKey"));
					stringObjectMap.put(CmdbAttrConstant.DEVICE_MODEL_CODE,dictKey);
				}
			}else if (!stringObjectMap.containsKey(CmdbAttrConstant.DEVICE_MODEL) && stringObjectMap.containsKey(CmdbAttrConstant.DEVICE_MODEL_CODE)){
				String deviceModelCode = String.valueOf(stringObjectMap.get(CmdbAttrConstant.DEVICE_MODEL_CODE));
				//根据编码获取名称
				FeignCmdbDictCientitySearch search = new FeignCmdbDictCientitySearch();
				search.setCiId(cmdbDictProperties.getModel());
				search.setCurrentPage(1);
				search.setPageSize(10);
				search.setDictKey(deviceModelCode);
				R<FeignCiCientity> feignCiCientityR = cmdbClient.feignGetCiEntityDictPage(search);
				if (feignCiCientityR.getCode()== ResultCode.SUCCESS.getCode() && ObjectUtil.isNotEmpty(feignCiCientityR.getData().getData())){
					String dictValue = String.valueOf(feignCiCientityR.getData().getData().get(0).get("dictValue"));
					stringObjectMap.put(CmdbAttrConstant.DEVICE_MODEL,dictValue);
				}
			}
		}
		TransactionActionType actionType = cmdbLedgerDTO.getActionType();
		Map<String, Object> resultMap = cmdbService.cientityBatchupdate(entityUpdate, actionType);
		if (resultMap.containsKey("transactionGroupId")){
			resultMap.remove("transactionGroupId");
		}
		return R.data(resultMap);
	}

	@Override
	@PostMapping(API_PREFIX + "/order/status")
	public JSONObject orderStatus(JSONObject jsonObject) {
		JSONObject result = new JSONObject();
		if ("repair".equals(jsonObject.get("workOrderType"))){
			LambdaQueryWrapper<DeviceRepair> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(DeviceRepair::getFilingNo,jsonObject.get("filingNo"));
			DeviceRepair repair = deviceRepairService.getOne(queryWrapper);
			String jsonString = JSONObject.toJSONString(repair);
			result = JSONObject.parseObject(jsonString);
		}else if ("returned".equals(jsonObject.get("workOrderType"))){
			LambdaQueryWrapper<DeviceReturned> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(DeviceReturned::getFilingNo,jsonObject.get("filingNo"));
			DeviceReturned returned = deviceReturnedService.getOne(queryWrapper);
			String jsonString = JSONObject.toJSONString(returned);
			result = JSONObject.parseObject(jsonString);
		}
		return result;
	}

	@Override
	@GetMapping(API_PREFIX + "/cmdb/attr")
	public Map<Object, Object> selectCiAttrMap(Long ciId) {
		Map<Object, Object> objectObjectMap = cmdbCiAttrService.selectCiAttrMap(ciId, AttrMappingType.ALL);
		return objectObjectMap;
	}

}
