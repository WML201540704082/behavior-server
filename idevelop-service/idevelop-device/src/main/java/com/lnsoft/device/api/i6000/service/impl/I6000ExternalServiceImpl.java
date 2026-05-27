package com.lnsoft.device.api.i6000.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lnsoft.cmdb.entity.FeignCiCientity;
import com.lnsoft.cmdb.entity.FeignCmdbDictCientitySearch;
import com.lnsoft.cmdb.feign.ICmdbClient;
import com.lnsoft.common.cache.CacheNames;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.api.ResultCode;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.core.tool.utils.RedisUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.api.asset.dto.ExportRoom;
import com.lnsoft.device.api.asset.entity.ResourceCabinetsLs;
import com.lnsoft.device.api.asset.entity.ResourceRoom;
import com.lnsoft.device.api.asset.mapper.ResourceCabinetsMapper;
import com.lnsoft.device.api.asset.mapper.ResourceRoomMapper;
import com.lnsoft.device.api.asset.service.IResourceCabinetsService;
import com.lnsoft.device.api.asset.service.IResourceRoomService;
import com.lnsoft.device.api.asset.service.impl.HardwareBasicServiceImpl;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.api.i6000.dto.I6000CiCientityDTO;
import com.lnsoft.device.api.i6000.dto.I6000ExternalDTO;
import com.lnsoft.device.api.i6000.dto.I6000OriViewDTO;
import com.lnsoft.device.api.i6000.entity.I6000External;
import com.lnsoft.device.api.i6000.entity.I6000ExternalAdd;
import com.lnsoft.device.api.i6000.enums.I6000ExternalEnum;
import com.lnsoft.device.api.i6000.mapper.I6000ExternalMapper;
import com.lnsoft.device.api.i6000.service.II6000ExternalAddService;
import com.lnsoft.device.api.i6000.service.II6000ExternalService;
import com.lnsoft.device.api.i6000.service.II6000Service;
import com.lnsoft.device.api.warehouse.entity.Warehouse;
import com.lnsoft.device.api.warehouse.service.IWarehouseService;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.constant.I6000AttrConstant;
import com.lnsoft.device.eums.Expression;
import com.lnsoft.device.eums.TransactionActionType;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.device.props.CmdbDictProperties;
import com.lnsoft.device.utils.OrderNumberUtil;
import com.lnsoft.common.utils.UuidUtils;
import com.lnsoft.device.vo.CiCientitySearchVO;
import com.lnsoft.system.entity.Dept;
import com.lnsoft.system.feign.IDeptClient;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.lnsoft.device.utils.OrderNumberUtil.*;

/**
 * @author xyzadmin
 */
@Service
@AllArgsConstructor
public class I6000ExternalServiceImpl extends BaseServiceImpl<I6000ExternalMapper, I6000External> implements II6000ExternalService {
	private IWarehouseService warehouseService;
	private static final Logger LOGGER = LoggerFactory.getLogger(HardwareBasicServiceImpl.class);
	private IDeptClient deptClient;
	private IResourceCabinetsService resourceCabinetsService;
	private IResourceRoomService roomService;
	private ResourceRoomMapper resourceRoomMapper;
	private RedisUtil redisUtil;
	private ICmdbClient cmdbClient;
	private CmdbDictProperties cmdbDictProperties;
	private II6000Service ii6000Service;
	private II6000ExternalAddService ii6000ExternalAddService;
	private ResourceCabinetsMapper cabinetsMapper;
	private ICmdbService cmdbService;
	private OrderNumberUtil orderNumberUtil;
	private CmdbCientityProperties cmdbCientityProperties;
	private I6000ExternalMapper externalMapper;

	@Override
	public Boolean saveExternal(I6000ExternalDTO i6000ExternalDTO) {
		I6000OriViewDTO i6000OriViewDTO = new I6000OriViewDTO();
		i6000OriViewDTO.setOriViewId(i6000ExternalDTO.getExtCode());
		if (StringUtil.isBlank(i6000ExternalDTO.getPageStart()) || StringUtil.isBlank(i6000ExternalDTO.getPageSize())){
			i6000ExternalDTO.setPageStart("1");
			i6000ExternalDTO.setPageSize("10000");
		}
		i6000OriViewDTO.setPageStart(i6000ExternalDTO.getPageStart());
		i6000OriViewDTO.setPageSize(i6000ExternalDTO.getPageSize());
		List<I6000External> selectOriView = ii6000Service.selectOriView(i6000OriViewDTO);
		try {

			List<I6000External> i6000Externals = new ArrayList<>();
			for (I6000External item : selectOriView) {
				I6000External i6000External = new I6000External();
				i6000External.setExtCode(i6000ExternalDTO.getExtCode());
				i6000External.setExtId(item.getExtId());
				i6000External.setExtName(item.getExtName());
				i6000External.setExtPid(item.getExtPid());
				i6000External.setExtState(item.getExtState());
				i6000External.setMatchModelId(item.getMatchModelId());
				i6000External.setMfrHs(item.getMfrHs());
				i6000External.setRn(item.getRn());
				i6000External.setCreateTime(new Date());
				i6000External.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
				i6000External.setUpdateTime(new Date());
				LambdaQueryWrapper<I6000External> queryWrapper = new LambdaQueryWrapper<>();
				queryWrapper.eq(I6000External::getExtId,item.getExtId());
				I6000External external = this.getOne(queryWrapper);
				if (ObjectUtil.isEmpty(external)){
					baseMapper.insert(i6000External);
				}else {
					LambdaUpdateWrapper<I6000External> updateWrapper = new LambdaUpdateWrapper<>();
					updateWrapper.set(I6000External::getExtCode,i6000External.getExtCode()).set(I6000External::getExtName,i6000External.getExtName())
						.set(I6000External::getExtPid,i6000External.getExtPid()).set(I6000External::getExtState,i6000External.getExtState())
						.set(I6000External::getMatchModelId,i6000External.getMatchModelId()).set(I6000External::getMfrHs,i6000External.getMfrHs())
						.set(I6000External::getRn,i6000External.getRn()).eq(I6000External::getExtId,i6000External.getExtId());
					baseMapper.update(updateWrapper);
				}
			}

			return this.saveOrUpdateBatch(i6000Externals);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public I6000External detail(I6000External i6000External) {
		return baseMapper.selectById(i6000External);
	}


	@Override
	public IPage<I6000External> seleteI6000ExternalPage(IPage<I6000External> page, I6000External i6000External) {

		return page.setRecords(baseMapper.selectExternalPage(page, i6000External));
	}

	@Override
	public void delete(List<Long> idList) {
		baseMapper.deleteExternal(idList);
	}

	@Override
	public Boolean insertExternal(I6000ExternalDTO i6000ExternalDTO) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> ciAttrList = objectMapper.readValue(i6000ExternalDTO.getResultValue(), new com.fasterxml.jackson.core.type.TypeReference<Map<String, Object>>() {
			});
			List<Map<String, Object>> items = (List<Map<String, Object>>) ciAttrList.get("items");
			List<Warehouse> warehouseList = new ArrayList<>();
			items.forEach((item) -> {
				Warehouse warehouse = new Warehouse();
				warehouse.setUuid(String.valueOf(item.get("CI_ID")));
				warehouse.setWarehouseName(String.valueOf(item.get("CI_NAME")));
				R<Dept> data = deptClient.getByI6000Code(String.valueOf(item.get("RUN_CORP_CODE")));
				Dept dept = data.getData();
				if (dept == null) {
					warehouse.setOwnerUnit("");
					warehouse.setOwnerUnitId("");
					warehouse.setRegionCode("");
				} else {
					warehouse.setOwnerUnit(dept.getFullName());
					warehouse.setOwnerUnitId(String.valueOf(dept.getId()));
					warehouse.setRegionCode(dept.getRegionCode());
				}
				warehouse.setWarehouseStatus("1");
				warehouse.setWarehouseId(generateWarehouse());
				warehouse.setCreateTime(new Date());
				warehouse.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
				warehouse.setUpdateTime(new Date());
				warehouse.setChargeUser("张三");
				warehouseList.add(warehouse);
			});
			return warehouseService.saveOrUpdateBatch(warehouseList);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Boolean addExternal(I6000ExternalDTO i6000ExternalDTO) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> ciAttrList = objectMapper.readValue(i6000ExternalDTO.getResultValue(), new com.fasterxml.jackson.core.type.TypeReference<Map<String, Object>>() {
			});
			List<Map<String, Object>> items = (List<Map<String, Object>>) ciAttrList.get("items");
			List<ResourceRoom> roomList = new ArrayList<>();
			for (Map<String, Object> item : items) {
				ResourceRoom room = new ResourceRoom();
				room.setUuid(String.valueOf(item.get("CI_ID")));
				room.setRoomName(String.valueOf(item.get("CI_NAME")));
				room.setGlobalName(String.valueOf(item.get("CI_NAME")));
				room.setAbbreviation(String.valueOf(item.get("CI_NAME")));
				room.setIsMonitor("1");
				room.setRoomFunction("4");
				R<Dept> data = deptClient.getByI6000Code(String.valueOf(item.get("RUN_CORP_CODE")));
				Dept dept = data.getData();
				if (dept == null) {
					room.setMaintenanceUnit("");
					room.setMaintenanceUnitName("");
					room.setRegionCode("");
				} else {
					room.setMaintenanceUnit(String.valueOf(dept.getId()));
					room.setMaintenanceUnitName(dept.getFullName());
					room.setRegionCode(dept.getRegionCode());
				}
				room.setCreateTime(new Date());
				room.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
				room.setUpdateTime(new Date());
				room.setRoomId(generateRoomId());
				room.setRoomType("1");
				room.setType("1");
				roomList.add(room);
				LambdaQueryWrapper<ResourceRoom> wrapper = new LambdaQueryWrapper<>();
				wrapper.eq(ResourceRoom::getUuid, String.valueOf(item.get("CI_ID"))).eq(ResourceRoom::getIsDeleted, IdevelopConstant.DB_NOT_DELETED);
				ResourceRoom resourceRoom = roomService.getOne(wrapper);
				if (ObjectUtil.isNotEmpty(resourceRoom)) {
					LambdaUpdateWrapper<ResourceRoom> updateWrapper = new LambdaUpdateWrapper<>();
					updateWrapper.eq(ResourceRoom::getUuid, String.valueOf(item.get("CI_ID")));
					roomService.update(room, updateWrapper);
				} else {
					roomService.save(room);
				}
			}
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean addCabinets(Map<String, Map<String, Object>> map) {
		map.keySet().forEach((key) -> {
			Map<String, Object> stringObjectMap = map.get(key);
			String resultValue = (String) stringObjectMap.get("resultValue");
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				Map<String, Object> ciAttrList = objectMapper.readValue(resultValue, new com.fasterxml.jackson.core.type.TypeReference<Map<String, Object>>() {
				});
				List<ResourceCabinetsLs> list = new ArrayList<>();
				List<Map<String, Object>> links = (List<Map<String, Object>>) ciAttrList.get("links");
				links.forEach((cabinets) -> {
					ResourceCabinetsLs resourceCabinets = new ResourceCabinetsLs();
					String roomCiId = (String) cabinets.get("SRC_CIID");
					String cabinetsCiId = (String) cabinets.get("TGT_CIID");
					ExportRoom exportRoom = roomService.selectRoomById(roomCiId);
					String roomName = exportRoom.getRoomName();
					resourceCabinets.setBelongRoom(roomName);
					resourceCabinets.setId(cabinetsCiId);
					resourceCabinets.setRoomId(roomCiId);
					list.add(resourceCabinets);
				});
				resourceCabinetsService.updateBatchById(list);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
		return true;
	}

	@Override
	public boolean insertCabinets(I6000ExternalDTO i6000ExternalDTO) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> ciAttrList = objectMapper.readValue(i6000ExternalDTO.getResultValue(), new com.fasterxml.jackson.core.type.TypeReference<Map<String, Object>>() {
			});
			List<Map<String, Object>> items = (List<Map<String, Object>>) ciAttrList.get("items");
			List<ResourceCabinetsLs> cabinetsList = new ArrayList<>();
			items.forEach((item) -> {
				ResourceCabinetsLs cabinets = new ResourceCabinetsLs();
				cabinets.setId(String.valueOf(item.get("CI_ID")));
				cabinets.setCabinetsName(String.valueOf(item.get("CI_NAME")));
				cabinets.setGlobalName(String.valueOf(item.get("CI_NAME")));
				cabinets.setAbbreviation(String.valueOf(item.get("CI_NAME")));
				cabinets.setIsSort("1");
				R<Dept> data = deptClient.getByI6000Code(String.valueOf(item.get("RUN_CORP_CODE")));
				Dept dept = data.getData();
				if (dept == null) {
					cabinets.setMaintenanceUnit("");
					cabinets.setMaintenanceUnitName("");
				} else {
					cabinets.setMaintenanceUnit(String.valueOf(dept.getId()));
					cabinets.setMaintenanceUnitName(dept.getFullName());
				}
				cabinets.setCreateTime(new Date());
				cabinets.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
				cabinets.setUpdateTime(new Date());
//				cabinets.setRoomId(generateRoomId());
				cabinets.setCabinetsType("1");
				cabinets.setType("1");
				cabinets.setCabinetsId(generateRoomId());
				cabinetsList.add(cabinets);
			});
			return resourceCabinetsService.saveOrUpdateBatch(cabinetsList);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<I6000External> checkData(List<I6000External> i6000Externals) {
		String code = i6000Externals.get(0).getExtCode();
		FeignCmdbDictCientitySearch cientitySearch = new FeignCmdbDictCientitySearch();
		if (I6000ExternalEnum.EXT_109.getCode().equals(code)) {
			cientitySearch.setCiId(cmdbDictProperties.getMaker());
		} else if (I6000ExternalEnum.EXT_110.getCode().equals(code)) {
			cientitySearch.setCiId(cmdbDictProperties.getBrand());
		} else if (I6000ExternalEnum.EXT_111.getCode().equals(code)) {
			cientitySearch.setCiId(cmdbDictProperties.getSeries());
		} else if (I6000ExternalEnum.EXT_112.getCode().equals(code)) {
			cientitySearch.setCiId(cmdbDictProperties.getModel());
		}
		cientitySearch.setDictKey(code);
		R<List<Map<String, Object>>> listR = cmdbClient.feignGetCiEntityDictListById(cientitySearch);
		if (ObjectUtil.isNotEmpty(listR.getData())) {
			return null;
		}
		List<Map<String, Object>> data = listR.getData();
		ArrayList<I6000External> I6000ExternalsByCmdb = new ArrayList<>();
		for (Map<String, Object> map : data) {
			I6000External external = new I6000External();
			external.setExtName(String.valueOf(map.get("dictValueI6000")));
			external.setExtId(String.valueOf(map.get("dictKeyI6000")));
			external.setExtPid(ObjectUtil.isNotEmpty(map.get("pidI6000")) ? String.valueOf(map.get("pidI6000")) : "");
			I6000ExternalsByCmdb.add(external);
		}
		ArrayList<I6000External> result = new ArrayList<>();
		result.addAll(i6000Externals);
		if (I6000ExternalEnum.EXT_109.getCode().equals(code)) {
			result.removeIf(a -> I6000ExternalsByCmdb.removeIf(b -> b.getExtCode().equals(a.getExtCode()) &&
				b.getExtName().equals(a.getExtName())));
		} else {
			result.removeIf(a -> I6000ExternalsByCmdb.removeIf(b -> b.getExtCode().equals(a.getExtCode()) &&
				b.getExtPid().equals(a.getExtPid())));
		}
		String jsonString = JSON.toJSONString(result);
		List<I6000ExternalAdd> i6000ExternalAdds = JSON.parseArray(jsonString, I6000ExternalAdd.class);
		//删除上一次对比的数据
		ii6000ExternalAddService.delete(code);
		//新增对比数据结果
		ii6000ExternalAddService.saveBatch(i6000ExternalAdds);
		return result;
	}

	@Override
	public Boolean getI6000AndAdd(I6000OriViewDTO i6000OriViewDTO) {
		List<I6000External> i6000Externals = ii6000Service.selectOriView(i6000OriViewDTO);
		String extCode = i6000OriViewDTO.getOriViewId();
		//删除旧数据
		baseMapper.delete(Wrappers.<I6000External>lambdaQuery().eq(I6000External::getExtCode, extCode));
		// 新增新数据
		return saveBatch(i6000Externals);
	}

	@Override
	public R inCmdb() {
		String attrCode = I6000AttrConstant.CI_NAME + "," +
			I6000AttrConstant.CABINET_VOLUME + "," +
			I6000AttrConstant.BEBER + "," +
			I6000AttrConstant.ABBR_NAME + "," +
			I6000AttrConstant.REMARKS + "," +
			I6000AttrConstant.RELEASE_NO + "," +
			I6000AttrConstant.ENTITY_ID + "," +
			I6000AttrConstant.CYCLE_STATUS + "," +
			I6000AttrConstant.FIRST_RUN_DATE + "," +
			I6000AttrConstant.MADE_COUNTRY + "," +
			I6000AttrConstant.MANUFACTURER + "," +
			I6000AttrConstant.MANUFACTURER_NAME + "," +
			I6000AttrConstant.BRAND + "," +
			I6000AttrConstant.BRAND_NAME + "," +
			I6000AttrConstant.SERIES + "," +
			I6000AttrConstant.SERIES_NAME + "," +
			I6000AttrConstant.MODEL + "," +
			I6000AttrConstant.MODEL_NAME + "," +
			I6000AttrConstant.DEV_SCRAP_DATE + "," +
			I6000AttrConstant.RUN_ENVIRONMENT + "," +
			I6000AttrConstant.OTHER_CORP_FLAG + "," +
			I6000AttrConstant.CABINET_VOLUME + "," +
			I6000AttrConstant.REPAIR_STATE + "," +
			I6000AttrConstant.NETWORK + "," +
			I6000AttrConstant.INSTALSITE + "," +
			I6000AttrConstant.RUN_DATE + "," +
			I6000AttrConstant.APP + "," +
			I6000AttrConstant.MAC_ADDR + "," +
			I6000AttrConstant.IP_ADDR + "," +
			I6000AttrConstant.ROUTING_NODES + "," +
			I6000AttrConstant.CONF_SPEC + "," +
			I6000AttrConstant.RCVD_DATE + "," +
			I6000AttrConstant.RELEASE_DATE + "," +
			I6000AttrConstant.RCVD_USER + "," +
			I6000AttrConstant.RCVD_CORP + "," +
			I6000AttrConstant.PUR_DATE + "," +
			I6000AttrConstant.RUN_DEPT + "," +
			I6000AttrConstant.RUN_DEPT_NAME + "," +
			I6000AttrConstant.RCVD_DEPT + "," +
			I6000AttrConstant.RCVD_DEPT_NAME + "," +
			I6000AttrConstant.RUN_CORP_CODE_NAME + "," +
			I6000AttrConstant.RUN_USER_NAME + "," +
			I6000AttrConstant.RUN_USER_TEL + "," +
			I6000AttrConstant.SRV_COMPANY_NAME + "," +
			I6000AttrConstant.SRV_NO + "," +
			I6000AttrConstant.SRV_REQUIRED + "," +
			I6000AttrConstant.SRV_CONTACT + "," +
			I6000AttrConstant.PUR_NO + "," +
			I6000AttrConstant.PUR_MODE + "," +
			I6000AttrConstant.SUP_TEL + "," +
			I6000AttrConstant.SUP_CONTACT + "," +
			I6000AttrConstant.RETIRE_STORAGE + "," +
			I6000AttrConstant.RETIRE_DATE + "," +
			I6000AttrConstant.ITEM + "," +
			I6000AttrConstant.KEEP_DEPT + "," +
			I6000AttrConstant.KEEP_DEPT_NAME + "," +
			I6000AttrConstant.MANAGE_DEPT + "," +
			I6000AttrConstant.MANAGE_DEPT_NAME + "," +
			I6000AttrConstant.ASSET_CHANGE + "," +
			I6000AttrConstant.INIT_ASSET_VALUE + "," +
			I6000AttrConstant.ASSET_ADD + "," +
			I6000AttrConstant.ERP_ASSET_NO + "," +
			I6000AttrConstant.ERP_LEDGER_NO + "," +
			I6000AttrConstant.OPDEP + "," +
			I6000AttrConstant.OPDEP_NAME + "," +
			I6000AttrConstant.WBS + "," +
			I6000AttrConstant.WBS_NAME + "," +
			I6000AttrConstant.PROP_CORP + "," +
			I6000AttrConstant.PROP_CORP_NAME + "," +
			I6000AttrConstant.FUN_SITE + "," +
			I6000AttrConstant.FUN_SITE_NAME + "," +
			I6000AttrConstant.BDZ + "," +
			I6000AttrConstant.BDZ_NAME + "," +
			I6000AttrConstant.SYNC_ERP_FLAG;
		LambdaQueryWrapper<ResourceCabinetsLs> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ResourceCabinetsLs::getIsDeleted, IdevelopConstant.DB_NOT_DELETED);
		List<ResourceCabinetsLs> cabinetsList = cabinetsMapper.selectList(queryWrapper);
		for (ResourceCabinetsLs cabinets : cabinetsList) {
			//获取i6000机柜数据
			I6000CiCientityDTO i6000CiCientityDTO = new I6000CiCientityDTO();
			i6000CiCientityDTO.setAttrCode(attrCode);
			i6000CiCientityDTO.setPageStart("1");
			i6000CiCientityDTO.setPageSize("10");
			List<I6000CiCientityDTO.Conditions> conditions = new ArrayList<>();
			//查询条件
			I6000CiCientityDTO.Conditions condition = new I6000CiCientityDTO.Conditions();
			condition.setAttrCode(I6000AttrConstant.CI_ID);
			condition.setOperator("=");
			condition.setValue(String.valueOf(cabinets.getId()));
			conditions.add(condition);
			i6000CiCientityDTO.setConditions(conditions);
			LOGGER.info("--------机柜数据导入------  请求I6000参数: i6000CiCientityDTO = {}", i6000CiCientityDTO);
			List<Map<String, Object>> mapList = ii6000Service.selectCiCientity("T10603", i6000CiCientityDTO);
			LOGGER.info("--------机柜数据导入------  返回I6000数据: i6000ResultMap = {}", mapList);
			Map<String, Object> i6000Map = new HashMap<>();
			if (ObjectUtil.isNotEmpty(mapList)) {
				i6000Map = mapList.get(0);
			}
			//组装新增cmdb字段
			Map<Long, Map<String, Object>> map = new HashMap<>();
			Query query = new Query();
			query.setCurrent(1);
			query.setSize(99);
			CiCientitySearchVO searchVO = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.CABINET_CODE).attrValue(cabinets.getId()).expression(Expression.EQUAL).build();
			ArrayList<CiCientitySearchVO> ciCientitySearchVOS = new ArrayList<>();
			ciCientitySearchVOS.add(searchVO);
			FeignCiCientity jsonObject = cmdbService.getCiCientityListByClaccify(ciCientitySearchVOS, query);
			if (ObjectUtil.isEmpty(jsonObject)) {
				continue;
			}
			Map<String, Object> objectMap = jsonObject.getData().get(0);
			Long cmdbId = Long.valueOf(String.valueOf(objectMap.get(CmdbAttrConstant.ID)));
			//参数组装
			Map<String, Object> converMapCmdb = converMapCmdb(objectMap, i6000Map, cabinets);
			map.put(cmdbId, converMapCmdb);
			cmdbService.cientityBatchupdate(map, TransactionActionType.UPDATE);
		}
		return R.success(ResultCode.SUCCESS);
	}

	@Override
	public R inCmdbTest(String id) {
		String attrCode = I6000AttrConstant.CI_NAME + "," +
			I6000AttrConstant.CABINET_VOLUME + "," +
			I6000AttrConstant.BEBER + "," +
			I6000AttrConstant.ABBR_NAME + "," +
			I6000AttrConstant.REMARKS + "," +
			I6000AttrConstant.RELEASE_NO + "," +
			I6000AttrConstant.ENTITY_ID + "," +
			I6000AttrConstant.CYCLE_STATUS + "," +
			I6000AttrConstant.FIRST_RUN_DATE + "," +
			I6000AttrConstant.MADE_COUNTRY + "," +
			I6000AttrConstant.MANUFACTURER + "," +
			I6000AttrConstant.MANUFACTURER_NAME + "," +
			I6000AttrConstant.BRAND + "," +
			I6000AttrConstant.BRAND_NAME + "," +
			I6000AttrConstant.SERIES + "," +
			I6000AttrConstant.SERIES_NAME + "," +
			I6000AttrConstant.MODEL + "," +
			I6000AttrConstant.MODEL_NAME + "," +
			I6000AttrConstant.DEV_SCRAP_DATE + "," +
			I6000AttrConstant.RUN_ENVIRONMENT + "," +
			I6000AttrConstant.OTHER_CORP_FLAG + "," +
			I6000AttrConstant.CABINET_VOLUME + "," +
			I6000AttrConstant.REPAIR_STATE + "," +
			I6000AttrConstant.NETWORK + "," +
			I6000AttrConstant.INSTALSITE + "," +
			I6000AttrConstant.RUN_DATE + "," +
			I6000AttrConstant.APP + "," +
			I6000AttrConstant.MAC_ADDR + "," +
			I6000AttrConstant.IP_ADDR + "," +
			I6000AttrConstant.ROUTING_NODES + "," +
			I6000AttrConstant.CONF_SPEC + "," +
			I6000AttrConstant.RCVD_DATE + "," +
			I6000AttrConstant.RELEASE_DATE + "," +
			I6000AttrConstant.RCVD_USER + "," +
			I6000AttrConstant.RCVD_CORP + "," +
			I6000AttrConstant.PUR_DATE + "," +
			I6000AttrConstant.RUN_DEPT + "," +
			I6000AttrConstant.RUN_DEPT_NAME + "," +
			I6000AttrConstant.RCVD_DEPT + "," +
			I6000AttrConstant.RCVD_DEPT_NAME + "," +
			I6000AttrConstant.RUN_CORP_CODE_NAME + "," +
			I6000AttrConstant.RUN_USER_NAME + "," +
			I6000AttrConstant.RUN_USER_TEL + "," +
			I6000AttrConstant.SRV_COMPANY_NAME + "," +
			I6000AttrConstant.SRV_NO + "," +
			I6000AttrConstant.SRV_REQUIRED + "," +
			I6000AttrConstant.SRV_CONTACT + "," +
			I6000AttrConstant.PUR_NO + "," +
			I6000AttrConstant.PUR_MODE + "," +
			I6000AttrConstant.SUP_TEL + "," +
			I6000AttrConstant.SUP_CONTACT + "," +
			I6000AttrConstant.RETIRE_STORAGE + "," +
			I6000AttrConstant.RETIRE_DATE + "," +
			I6000AttrConstant.ITEM + "," +
			I6000AttrConstant.KEEP_DEPT + "," +
			I6000AttrConstant.KEEP_DEPT_NAME + "," +
			I6000AttrConstant.MANAGE_DEPT + "," +
			I6000AttrConstant.MANAGE_DEPT_NAME + "," +
			I6000AttrConstant.ASSET_CHANGE + "," +
			I6000AttrConstant.INIT_ASSET_VALUE + "," +
			I6000AttrConstant.ASSET_ADD + "," +
			I6000AttrConstant.ERP_ASSET_NO + "," +
			I6000AttrConstant.ERP_LEDGER_NO + "," +
			I6000AttrConstant.OPDEP + "," +
			I6000AttrConstant.OPDEP_NAME + "," +
			I6000AttrConstant.WBS + "," +
			I6000AttrConstant.WBS_NAME + "," +
			I6000AttrConstant.PROP_CORP + "," +
			I6000AttrConstant.PROP_CORP_NAME + "," +
			I6000AttrConstant.FUN_SITE + "," +
			I6000AttrConstant.FUN_SITE_NAME + "," +
			I6000AttrConstant.BDZ + "," +
			I6000AttrConstant.BDZ_NAME + "," +
			I6000AttrConstant.SYNC_ERP_FLAG;
		LambdaQueryWrapper<ResourceCabinetsLs> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ResourceCabinetsLs::getIsDeleted, IdevelopConstant.DB_NOT_DELETED).eq(ResourceCabinetsLs::getId, id);
		ResourceCabinetsLs cabinets = cabinetsMapper.selectOne(queryWrapper);
		//获取i6000机柜数据
		I6000CiCientityDTO i6000CiCientityDTO = new I6000CiCientityDTO();
		i6000CiCientityDTO.setAttrCode(attrCode);
		i6000CiCientityDTO.setPageStart("1");
		i6000CiCientityDTO.setPageSize("10");
		List<I6000CiCientityDTO.Conditions> conditions = new ArrayList<>();
		//查询条件
		I6000CiCientityDTO.Conditions condition = new I6000CiCientityDTO.Conditions();
		condition.setAttrCode(I6000AttrConstant.CI_ID);
		condition.setOperator("=");
		condition.setValue(id);
		conditions.add(condition);
		i6000CiCientityDTO.setConditions(conditions);
		LOGGER.info("--------机柜数据导入------  请求I6000参数: i6000CiCientityDTO = {}", i6000CiCientityDTO);
		List<Map<String, Object>> mapList = ii6000Service.selectCiCientity("T10603", i6000CiCientityDTO);
		LOGGER.info("--------机柜数据导入------  返回I6000数据: i6000ResultMap = {}", mapList);
		Map<String, Object> i6000Map = mapList.get(0);
		//组装新增cmdb字段
		Map<Long, Map<String, Object>> map = new HashMap<>();
		Query query = new Query();
		query.setCurrent(1);
		query.setSize(99);
		CiCientitySearchVO searchVO = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.CABINET_CODE).attrValue(id).expression(Expression.EQUAL).build();
		ArrayList<CiCientitySearchVO> ciCientitySearchVOS = new ArrayList<>();
		ciCientitySearchVOS.add(searchVO);
		FeignCiCientity jsonObject = cmdbService.getCiCientityListByClaccify(ciCientitySearchVOS, query);
		if (ObjectUtil.isEmpty(jsonObject)) {
			return R.fail("查询不到该机柜");
		}
		Map<String, Object> objectMap = jsonObject.getData().get(0);
		//参数组装
		Map<String, Object> converMapCmdb = converMapCmdb(objectMap, i6000Map, cabinets);
		Long cmdbId = Long.valueOf(String.valueOf(objectMap.get(CmdbAttrConstant.ID)));
		map.put(cmdbId, converMapCmdb);
		cmdbService.cientityBatchupdate(map, TransactionActionType.UPDATE);
		return R.success(ResultCode.SUCCESS);
	}

	@Override
	public R inCmdbTry() {
		LambdaQueryWrapper<ResourceCabinetsLs> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ResourceCabinetsLs::getIsDeleted, IdevelopConstant.DB_NOT_DELETED);
		List<ResourceCabinetsLs> cabinetsList = cabinetsMapper.selectList(queryWrapper);
		for (ResourceCabinetsLs cabinets : cabinetsList) {
			//组装新增cmdb字段
			if (!String.valueOf(cabinets.getId()).contains("-")) {
				cabinets.setId(UuidUtils.uuid());
			}
			Map<String, Map<String, Object>> map = new HashMap<>();
			//参数组装
			Map<String, Object> objectHashMap = new HashMap<>();
			ResourceRoom resourceRoom = roomService.getById(cabinets.getRoomId());
			if (ObjectUtil.isNotEmpty(resourceRoom)) {
				objectHashMap.put(CmdbAttrConstant.AREA, StringUtil.isNotBlank(resourceRoom.getRegionCode()) ? resourceRoom.getRegionCode() : "37");
			} else {
				String regionCode = "37";
				String unitCode = cabinets.getMaintenanceUnit();
				R<Dept> deptR = deptClient.getById(unitCode);
				if (ObjectUtil.isNotEmpty(deptR.getData())) {
					regionCode = deptR.getData().getRegionCode();
				}
				objectHashMap.put(CmdbAttrConstant.AREA, regionCode);
			}
			objectHashMap.put(CmdbAttrConstant.CABINET_CODE, cabinets.getId());
			objectHashMap.put(CmdbAttrConstant.CABINET, cabinets.getCabinetsName());
			objectHashMap.put(CmdbAttrConstant.COMPUTER_ROOM, cabinets.getBelongRoom());
			objectHashMap.put(CmdbAttrConstant.COMPUTER_ROOM_CODE, cabinets.getRoomId());
			objectHashMap.put(CmdbAttrConstant.FULL_NAME, cabinets.getCabinetsName());
			objectHashMap.put(CmdbAttrConstant.DEVICE_NAME, cabinets.getCabinetsName());
			objectHashMap.put(CmdbAttrConstant.CABINET_USE_CAPACITY, 0);
			objectHashMap.put(CmdbAttrConstant.OWNER_UNIT_CODE, cabinets.getMaintenanceUnit());
			objectHashMap.put(CmdbAttrConstant.OWNER_UNIT, cabinets.getMaintenanceUnitName());
			objectHashMap.put(CmdbAttrConstant.DEVICE_CODE, orderNumberUtil.generateCode("1135308277350478"));
			objectHashMap.put(CmdbAttrConstant.DEVICE_CATEGORY, "辅助设备");
			objectHashMap.put(CmdbAttrConstant.DEVICE_CATEGORY_CODE, cmdbCientityProperties.getT106());
			objectHashMap.put(CmdbAttrConstant.DEVICE_TYPE, "机柜");
			objectHashMap.put(CmdbAttrConstant.DEVICE_TYPE_CODE, cmdbCientityProperties.getT10603());
			objectHashMap.put(CmdbAttrConstant.IS_GOVERN, cmdbCientityProperties.getGovernNo());
			map.put(UuidUtils.uuid(), objectHashMap);
			Integer count = externalMapper.getCount(cabinets.getId());
			if (count == 0) {
				//新增
				cmdbService.cientityBatchsave(1120991213584384L, map, TransactionActionType.INSERT);
			}
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R refresh(List<String> cabinetsNames) {
		for (String cabinetsName : cabinetsNames) {
			Query query = new Query();
			query.setCurrent(1);
			query.setSize(99);
			CiCientitySearchVO searchVO = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.DEVICE_NAME).attrValue(cabinetsName).expression(Expression.EQUAL).build();
			ArrayList<CiCientitySearchVO> ciCientitySearchVOS = new ArrayList<>();
			ciCientitySearchVOS.add(searchVO);
			FeignCiCientity jsonObject = cmdbService.getCiCientityListByClaccify(ciCientitySearchVOS, query);
			if (ObjectUtil.isNotEmpty(jsonObject) && jsonObject.getTotal() == 2) {
				Map<String, Object> map = jsonObject.getData().get(0);
				Map<String, Object> map1 = jsonObject.getData().get(1);
				String deviceCode = String.valueOf(map.get(CmdbAttrConstant.DEVICE_CODE));
				if (deviceCode.startsWith("37")) {
					Map<Long, Map<String, Object>> mapResult = new HashMap<>();
					for (Map.Entry<String, Object> entry : map.entrySet()) {
						map1.putIfAbsent(entry.getKey(), entry.getValue());
					}
					Long cmdbId = Long.valueOf(String.valueOf(map1.get(CmdbAttrConstant.ID)));
					mapResult.put(cmdbId, map1);
					cmdbService.cientityBatchupdate(mapResult, TransactionActionType.UPDATE);
				} else {
					Map<Long, Map<String, Object>> mapResult = new HashMap<>();
					for (Map.Entry<String, Object> entry : map1.entrySet()) {
						map.putIfAbsent(entry.getKey(), entry.getValue());
					}
					Long cmdbId = Long.valueOf(String.valueOf(map.get(CmdbAttrConstant.ID)));
					mapResult.put(cmdbId, map);
					cmdbService.cientityBatchupdate(mapResult, TransactionActionType.UPDATE);
				}
			}
		}
		return R.success("操作成功");
	}

	@Override
	public R updateRegion(List<String> cabinetCodes) {
		Map<Long, Map<String, Object>> mapResult = new HashMap<>();
		for (String cabinetCode : cabinetCodes) {
			LambdaQueryWrapper<ResourceCabinetsLs> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(ResourceCabinetsLs::getId, cabinetCode);
			ResourceCabinetsLs cabinetsLs = cabinetsMapper.selectOne(queryWrapper);
			String regionCode = "37";
			if (ObjectUtil.isNotEmpty(cabinetsLs)) {
				R<Dept> deptR = deptClient.getById(cabinetsLs.getMaintenanceUnit());
				if (ObjectUtil.isNotEmpty(deptR)) {
					regionCode = deptR.getData().getRegionCode();
				}
			}
			Query query = new Query();
			query.setCurrent(1);
			query.setSize(99);
			CiCientitySearchVO searchVO = CiCientitySearchVO.builder().attrName(CmdbAttrConstant.CABINET_CODE).attrValue(cabinetCode).expression(Expression.EQUAL).build();
			ArrayList<CiCientitySearchVO> ciCientitySearchVOS = new ArrayList<>();
			ciCientitySearchVOS.add(searchVO);
			FeignCiCientity jsonObject = cmdbService.getCiCientityListByClaccify(ciCientitySearchVOS, query);
			if (ObjectUtil.isNotEmpty(jsonObject.getData())) {
				Map<String, Object> cabinetMap = jsonObject.getData().get(0);
				cabinetMap.put(CmdbAttrConstant.AREA, regionCode);
				Long cmdbId = Long.valueOf(String.valueOf(cabinetMap.get(CmdbAttrConstant.ID)));
				mapResult.put(cmdbId, cabinetMap);
			}
			cmdbService.cientityBatchupdate(mapResult, TransactionActionType.UPDATE);
		}
		return R.success(ResultCode.SUCCESS);
	}

	@NotNull
	private Map<String, Object> converMapCmdb(Map<String, Object> hashMap, Map<String, Object> i6000Map, ResourceCabinetsLs cabinets) {
		//区域
		String regionCode = "37";
		String unitCode = cabinets.getMaintenanceUnit();
		R<Dept> deptR = deptClient.getById(unitCode);
		if (ObjectUtil.isNotEmpty(deptR.getData())) {
			regionCode = deptR.getData().getRegionCode();
		}
		hashMap.put(CmdbAttrConstant.AREA, regionCode);
		//部门
		hashMap.put(CmdbAttrConstant.DEPT, "");
		//设备名称  标准全称

		//设备编码

		// 制造国家和地区
		if (i6000Map.containsKey(I6000AttrConstant.MADE_COUNTRY)) {
			Map<String, Object> dictMap = getDictMap(i6000Map, I6000AttrConstant.MADE_COUNTRY, cmdbDictProperties.getCountryArea());
			hashMap.put(CmdbAttrConstant.MAINTENANCE_COUNTRY, dictMap.get(CmdbAttrConstant.DICT_KEY));
		}
		// 制造商
		if (i6000Map.containsKey(I6000AttrConstant.MANUFACTURER)) {
			Map<String, Object> dictMap = getDictMap(i6000Map, I6000AttrConstant.MANUFACTURER, cmdbDictProperties.getMaker());
			hashMap.put(CmdbAttrConstant.MAKER, dictMap.get(CmdbAttrConstant.DICT_VALUE));
			hashMap.put(CmdbAttrConstant.MAKER_CODE, dictMap.get(CmdbAttrConstant.DICT_KEY));
		}
		// 品牌
		if (i6000Map.containsKey(I6000AttrConstant.BRAND)) {
			Map<String, Object> dictMap = getDictMap(i6000Map, I6000AttrConstant.BRAND, cmdbDictProperties.getBrand());
			hashMap.put(CmdbAttrConstant.BRAND_CODE, dictMap.get(CmdbAttrConstant.DICT_KEY));
			hashMap.put(CmdbAttrConstant.BRAND, dictMap.get(CmdbAttrConstant.DICT_VALUE));
		}
		// 序列
		if (i6000Map.containsKey(I6000AttrConstant.SERIES)) {
			Map<String, Object> dictMap = getDictMap(i6000Map, I6000AttrConstant.SERIES, cmdbDictProperties.getSeries());
			hashMap.put(CmdbAttrConstant.SERIES_CODE, dictMap.get(CmdbAttrConstant.DICT_KEY));
			hashMap.put(CmdbAttrConstant.SERIES, dictMap.get(CmdbAttrConstant.DICT_VALUE));
		}
		// 型号MODEL
		if (i6000Map.containsKey(I6000AttrConstant.MODEL)) {
			Map<String, Object> dictMap = getDictMap(i6000Map, I6000AttrConstant.MODEL, cmdbDictProperties.getModel());
			hashMap.put(CmdbAttrConstant.DEVICE_MODEL_CODE, dictMap.get(CmdbAttrConstant.DICT_KEY));
			hashMap.put(CmdbAttrConstant.DEVICE_MODEL, dictMap.get(CmdbAttrConstant.DICT_VALUE));
		}
		//出厂序列号
		if (i6000Map.containsKey(I6000AttrConstant.RELEASE_NO)) {
			hashMap.put(CmdbAttrConstant.SN, i6000Map.get(I6000AttrConstant.RELEASE_NO));
		}
		//投运日期
		if (i6000Map.containsKey(I6000AttrConstant.RUN_DATE)) {
			hashMap.put(CmdbAttrConstant.OPRT_DATE, i6000Map.get(I6000AttrConstant.RUN_DATE));
		}
		//领用单位
		if (i6000Map.containsKey(I6000AttrConstant.RCVD_CORP) && i6000Map.containsKey(I6000AttrConstant.RCVD_CORP_NAME)) {
			hashMap.put(CmdbAttrConstant.RECEIVE_UNIT, i6000Map.get(I6000AttrConstant.RCVD_CORP_NAME));
			hashMap.put(CmdbAttrConstant.RECEIVE_UNIT_CODE, i6000Map.get(I6000AttrConstant.RCVD_CORP));
		}
		//领用部门
		if (i6000Map.containsKey(I6000AttrConstant.RCVD_DEPT) && i6000Map.containsKey(I6000AttrConstant.RCVD_DEPT_NAME)) {
			hashMap.put(CmdbAttrConstant.RECEIVE_DEPT, i6000Map.get(I6000AttrConstant.RCVD_DEPT_NAME));
			hashMap.put(CmdbAttrConstant.RECEIVE_DEPT_CODE, i6000Map.get(I6000AttrConstant.RCVD_DEPT));
		}
		//首次投运日期
		if (i6000Map.containsKey(I6000AttrConstant.FIRST_RUN_DATE)) {
			hashMap.put(CmdbAttrConstant.OPRT_DATE_FIRST, i6000Map.get(I6000AttrConstant.FIRST_RUN_DATE));
		}
		//erp设备台账编码
		if (i6000Map.containsKey(I6000AttrConstant.ERP_LEDGER_NO)) {
			hashMap.put(CmdbAttrConstant.DEVICE_CODE_ERP, i6000Map.get(I6000AttrConstant.ERP_LEDGER_NO));
		}
		//erp资产编码
		if (i6000Map.containsKey(I6000AttrConstant.ERP_ASSET_NO)) {
			hashMap.put(CmdbAttrConstant.ASSET_CODE_ERP, i6000Map.get(I6000AttrConstant.ERP_ASSET_NO));
		}
		//安装地点
		if (i6000Map.containsKey(I6000AttrConstant.INSTALSITE)) {
			hashMap.put(CmdbAttrConstant.INSTALLATION_SITE, i6000Map.get(I6000AttrConstant.INSTALSITE));
		}
		//简称
		if (i6000Map.containsKey(I6000AttrConstant.ABBR_NAME)) {
			hashMap.put(CmdbAttrConstant.SHORT_NAME, i6000Map.get(I6000AttrConstant.ABBR_NAME));
		}
		//机柜容量
		if (i6000Map.containsKey(I6000AttrConstant.CABINET_VOLUME)) {
			hashMap.put(CmdbAttrConstant.CABINET_CAPACITY, i6000Map.get(I6000AttrConstant.CABINET_VOLUME));
			//机柜使用容量
			hashMap.put(CmdbAttrConstant.CABINET_USE_CAPACITY, 0);
		}
		//所属机房

		//ip
		if (i6000Map.containsKey(I6000AttrConstant.IP_ADDR)) {
			hashMap.put(CmdbAttrConstant.IP, i6000Map.get(I6000AttrConstant.IP_ADDR));
		}
		//领用日期
		if (i6000Map.containsKey(I6000AttrConstant.RCVD_DATE)) {
			hashMap.put(CmdbAttrConstant.RECEIVING_DATE, i6000Map.get(I6000AttrConstant.RCVD_DATE));
		}
		//出厂日期
		if (i6000Map.containsKey(I6000AttrConstant.RELEASE_DATE)) {
			hashMap.put(CmdbAttrConstant.FACTORY_DATE, i6000Map.get(I6000AttrConstant.RELEASE_DATE));
		}
		//采购日期
		if (i6000Map.containsKey(I6000AttrConstant.PUR_DATE)) {
			hashMap.put(CmdbAttrConstant.PROCURE_DATE, i6000Map.get(I6000AttrConstant.PUR_DATE));
		}
		//运维部门
		if (i6000Map.containsKey(I6000AttrConstant.RUN_DEPT) && i6000Map.containsKey(I6000AttrConstant.RUN_DEPT_NAME)) {
			hashMap.put(CmdbAttrConstant.OPERATION_DEPT, i6000Map.get(I6000AttrConstant.RUN_DEPT_NAME));
			hashMap.put(CmdbAttrConstant.OPERATION_DEP_CODE, i6000Map.get(I6000AttrConstant.RUN_DEPT));
		}
		//运维单位
		if (i6000Map.containsKey(I6000AttrConstant.RUN_CORP_CODE) && i6000Map.containsKey(I6000AttrConstant.RUN_CORP_CODE_NAME)) {
			hashMap.put(CmdbAttrConstant.OPERATION_UNIT, i6000Map.get(I6000AttrConstant.RUN_CORP_CODE_NAME));
			hashMap.put(CmdbAttrConstant.OPERATION_UNIT_CODE, i6000Map.get(I6000AttrConstant.RUN_CORP_CODE));
		}
		//运维责任人
		if (i6000Map.containsKey(I6000AttrConstant.RUN_USER_NAME)) {
			hashMap.put(CmdbAttrConstant.OPERATION_PERSON, i6000Map.get(I6000AttrConstant.RUN_USER_NAME));
		}
		//运维责任人联系方式
		if (i6000Map.containsKey(I6000AttrConstant.RUN_USER_TEL)) {
			hashMap.put(CmdbAttrConstant.OPERATION_PERSON_TEL, i6000Map.get(I6000AttrConstant.RUN_USER_TEL));
		}
		//服务商名称
		if (i6000Map.containsKey(I6000AttrConstant.SRV_COMPANY_NAME) && i6000Map.containsKey(I6000AttrConstant.SRV_COMPANY)) {
			hashMap.put(CmdbAttrConstant.SERVICE_NAME, i6000Map.get(I6000AttrConstant.SRV_COMPANY));
			hashMap.put(CmdbAttrConstant.SERVICE_FULL_NAME, i6000Map.get(I6000AttrConstant.SRV_COMPANY_NAME));
		}
		//服务合同编号
		if (i6000Map.containsKey(I6000AttrConstant.SRV_NO)) {
			hashMap.put(CmdbAttrConstant.CONTRACT_NO, i6000Map.get(I6000AttrConstant.SRV_NO));
		}
		//服务级别
		if (i6000Map.containsKey(I6000AttrConstant.SRV_REQUIRED)) {
			hashMap.put(CmdbAttrConstant.SERVICE_LEVEL, i6000Map.get(I6000AttrConstant.SRV_REQUIRED));
		}
		//服务商联系人
		if (i6000Map.containsKey(I6000AttrConstant.SRV_CONTACT)) {
			hashMap.put(CmdbAttrConstant.SERVICE_CONTACTS, i6000Map.get(I6000AttrConstant.SRV_CONTACT));
		}
		//采购合同编号
		if (i6000Map.containsKey(I6000AttrConstant.PUR_NO)) {
			hashMap.put(CmdbAttrConstant.PROCURE_CONTRACT_NO, i6000Map.get(I6000AttrConstant.PUR_NO));
		}
		//采购方式
		if (i6000Map.containsKey(I6000AttrConstant.PUR_MODE)) {
			hashMap.put(CmdbAttrConstant.PROCURE_TYPE_CODE, i6000Map.get(I6000AttrConstant.PUR_MODE));
		}
		//供应商联系电话
		if (i6000Map.containsKey(I6000AttrConstant.SUP_TEL)) {
			hashMap.put(CmdbAttrConstant.SUPPLIER_TEL, i6000Map.get(I6000AttrConstant.SUP_TEL));
		}
		//供应商联系人
		if (i6000Map.containsKey(I6000AttrConstant.SUP_CONTACT)) {
			hashMap.put(CmdbAttrConstant.SUPPLIER_CONTACTS, i6000Map.get(I6000AttrConstant.SUP_CONTACT));
		}
		//工厂区域
		if (i6000Map.containsKey(I6000AttrConstant.BEBER)) {
			hashMap.put(CmdbAttrConstant.FACTORY_AREA_CODE, i6000Map.get(I6000AttrConstant.BEBER));
			hashMap.put(CmdbAttrConstant.FACTORY_AREA, i6000Map.get(I6000AttrConstant.BEBER_NAME_99));
		}
		//项目名称
		if (i6000Map.containsKey(I6000AttrConstant.ITEM)) {
			hashMap.put(CmdbAttrConstant.PROJECT_NAME, i6000Map.get(I6000AttrConstant.ITEM));
		}
		//使用保管部门
		if (i6000Map.containsKey(I6000AttrConstant.KEEP_DEPT) && i6000Map.containsKey(I6000AttrConstant.KEEP_DEPT_NAME)) {
			hashMap.put(CmdbAttrConstant.USE_KEEP_DEPT, i6000Map.get(I6000AttrConstant.KEEP_DEPT));
			hashMap.put(CmdbAttrConstant.USE_KEEP_DEPT_NAME, i6000Map.get(I6000AttrConstant.KEEP_DEPT_NAME));
		}
		//实物管理部门
		if (i6000Map.containsKey(I6000AttrConstant.MANAGE_DEPT) && i6000Map.containsKey(I6000AttrConstant.MANAGE_DEPT_NAME)) {
			hashMap.put(CmdbAttrConstant.REAL_MANAGE_DEPT, i6000Map.get(I6000AttrConstant.KEEP_DEPT));
			hashMap.put(CmdbAttrConstant.ENTITY_MANAGEMENT_DEPT_NAME, i6000Map.get(I6000AttrConstant.KEEP_DEPT_NAME));
		}
		// 设备变动方式
		if (i6000Map.containsKey(I6000AttrConstant.ASSET_CHANGE)) {
			Map<String, Object> dictMap = getDictMap(i6000Map, I6000AttrConstant.ASSET_CHANGE, cmdbDictProperties.getDeviceChangeType());
			hashMap.put(CmdbAttrConstant.DEVICE_CHANGE_TYPE_CODE, dictMap.get(CmdbAttrConstant.DICT_KEY));
			hashMap.put(CmdbAttrConstant.DEVICE_CHANGE_TYPE, dictMap.get(CmdbAttrConstant.DICT_VALUE));
		}
		//资产原值
		if (i6000Map.containsKey(I6000AttrConstant.INIT_ASSET_VALUE)) {
			hashMap.put(CmdbAttrConstant.ASSET_ORIGINAL, i6000Map.get(I6000AttrConstant.INIT_ASSET_VALUE));
		}
		// 设备增加方式
		if (i6000Map.containsKey(I6000AttrConstant.ASSET_ADD)) {
			Map<String, Object> dictMap = getDictMap(i6000Map, I6000AttrConstant.ASSET_ADD, cmdbDictProperties.getDeviceAddType());
			hashMap.put(CmdbAttrConstant.DEVICE_ADD_TYPE_CODE, dictMap.get(CmdbAttrConstant.DICT_KEY));
			hashMap.put(CmdbAttrConstant.DEVICE_ADD_TYPE, dictMap.get(CmdbAttrConstant.DICT_VALUE));
		}
		//维护工厂
		if (i6000Map.containsKey(I6000AttrConstant.OPDEP) && i6000Map.containsKey(I6000AttrConstant.OPDEP_NAME)) {
			hashMap.put(CmdbAttrConstant.MAINTENANCE_FACTORY_CODE, i6000Map.get(I6000AttrConstant.OPDEP));
			hashMap.put(CmdbAttrConstant.MAINTENANCE_FACTORY, i6000Map.get(I6000AttrConstant.OPDEP_NAME));
		}
		//wbs元素
		if (i6000Map.containsKey(I6000AttrConstant.WBS)) {
			hashMap.put(CmdbAttrConstant.WBS_ELEMENT, i6000Map.get(I6000AttrConstant.WBS));
		}
		//产权单位
		if (i6000Map.containsKey(I6000AttrConstant.PROP_CORP) && i6000Map.containsKey(I6000AttrConstant.PROP_CORP_NAME)) {
			hashMap.put(CmdbAttrConstant.OWNER_UNIT, i6000Map.get(I6000AttrConstant.PROP_CORP_NAME));
			hashMap.put(CmdbAttrConstant.OWNER_UNIT_CODE, i6000Map.get(I6000AttrConstant.PROP_CORP));
		}
		//功能位置
		if (i6000Map.containsKey(I6000AttrConstant.FUN_SITE) && i6000Map.containsKey(I6000AttrConstant.FUN_SITE_NAME)) {
			hashMap.put(CmdbAttrConstant.FUN_LOCATION_CODE, i6000Map.get(I6000AttrConstant.FUN_SITE));
			hashMap.put(CmdbAttrConstant.FUN_LOCATION, i6000Map.get(I6000AttrConstant.FUN_SITE_NAME));
		}
		//线站标识
		if (i6000Map.containsKey(I6000AttrConstant.BDZ) && i6000Map.containsKey(I6000AttrConstant.BDZ_NAME)) {
			hashMap.put(CmdbAttrConstant.LINE_STATION, i6000Map.get(I6000AttrConstant.BDZ));
			hashMap.put(CmdbAttrConstant.LINE_STATION_SIGN, i6000Map.get(I6000AttrConstant.BDZ_NAME));
		}
		//是否同步给erp
		if (i6000Map.containsKey(I6000AttrConstant.SYNC_ERP_FLAG)) {
			hashMap.put(CmdbAttrConstant.IS_TO_ERP_CODE, cmdbCientityProperties.getYesNo());
		}
		return hashMap;


	}

	private Map<String, Object> getDictMap(Map<String, Object> i6000Map, String i6000Str, Long ciId) {
		FeignCmdbDictCientitySearch cientitySearch = new FeignCmdbDictCientitySearch();
		cientitySearch.setDictKeyI6000(String.valueOf(i6000Map.get(i6000Str)));
		cientitySearch.setCiId(ciId);
		R<List<Map<String, Object>>> dictListR = cmdbClient.feignGetCiEntityDictListById(cientitySearch);
		List<Map<String, Object>> dictList = dictListR.getData();
		if (CollectionUtils.isEmpty(dictList)) {
			return new HashMap<>();
		}
		return dictList.get(0);

	}

	public String generateWarehouse() {

		String regionCode = "370000";

		String format = DateTimeFormatter.ofPattern(YYYY_MM_DD).format(LocalDateTime.now());
		String redisKey = CacheNames.GENERATE_WAREHOUSE_KEY + regionCode + format;

		long increment = redisUtil.incr(redisKey, 1);

		if (increment == 1) {
			redisUtil.expireAt(redisKey, this.getNexDay());
		}

		return WAREHOUSE + regionCode + format + String.format(FORMAT_04, increment);
	}

	private long getNexDay() {
		LocalDateTime tomorrow = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
		return tomorrow.toEpochSecond(ZoneOffset.UTC) * 1000;
	}

	private String generateRoomId() {
//		Random random = new Random();
		SecureRandom random = null;
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		StringBuffer code = new StringBuffer();
		for (int i = 0; i < 10; i++) {
			if (random != null){
				code.append(random.nextInt(10));
			}
		}
		return code.toString();
	}
}
