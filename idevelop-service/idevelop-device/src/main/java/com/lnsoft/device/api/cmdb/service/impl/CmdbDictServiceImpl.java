package com.lnsoft.device.api.cmdb.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lnsoft.cmdb.entity.FeignCiCientity;
import com.lnsoft.cmdb.entity.FeignCmdbCientityBatchDelete;
import com.lnsoft.cmdb.entity.FeignCmdbDictCientitySearch;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.device.api.cmdb.service.ICmdbDictCiService;
import com.lnsoft.device.api.cmdb.service.ICmdbDictService;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.api.cmdb.wrapper.CmdbCiAttrWrapper;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.constant.DeviceConstant;
import com.lnsoft.device.dto.CmdbDictDeleteDTO;
import com.lnsoft.device.entity.CmdbDict;
import com.lnsoft.device.entity.CmdbDictCi;
import com.lnsoft.device.eums.TransactionActionType;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.common.utils.UuidUtils;
import com.lnsoft.device.vo.CmdbDictVO;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: xuel
 * @CreateTime: 2024/7/1 16:21
 * @Description: CmdbDictServiceImpl
 */
@Service
@AllArgsConstructor
public class CmdbDictServiceImpl implements ICmdbDictService {

	private static final Logger logger = LoggerFactory.getLogger(CmdbDictServiceImpl.class);

	private ICmdbService cmdbService;
	private ICmdbDictCiService cmdbDictCiService;
	private CmdbCientityProperties cmdbCientityProperties;


	/**
	 * 查询 字典配置项(CMDB)
	 *
	 * @param cmdbDict
	 * @return
	 */
	@Override
	public IPage<CmdbDictVO> cientitySelectDict(CmdbDict cmdbDict) {
		Long ciId = cmdbDict.getCiId();
		if (Objects.isNull(ciId)) {
			throw new RuntimeException("模型ID不能为空");
		}
		FeignCmdbDictCientitySearch cientitySearch = new FeignCmdbDictCientitySearch();
		String cientityId = cmdbDict.getCientityId();
		if (StringUtils.isNotEmpty(cientityId)) {
			cientitySearch.setFilterCiEntityId(Long.valueOf(cientityId));
		}
		cientitySearch.setDictKey(cmdbDict.getDictKey());
		cientitySearch.setDictValue(cmdbDict.getDictValue());

		String dataSourceStr = cmdbDict.getDataSource();
		if (StringUtils.isNotEmpty(dataSourceStr)) {
			cientitySearch.setDataSource(Long.valueOf(dataSourceStr));
		}

		cientitySearch.setCiId(ciId);
		cientitySearch.setCurrentPage(cmdbDict.getCurrentPage());
		cientitySearch.setPageSize(cmdbDict.getPageSize());
		cientitySearch.setPid(cmdbDict.getCientityPid());

		FeignCiCientity feignCiCientity = CmdbCiAttrWrapper.build().feignGetCiEntityDictPage(cientitySearch);
		List<Map<String, Object>> feignCiCientityData = feignCiCientity.getData();
		List<CmdbDictVO> cmdbDictVOS = feignCiCientityData.stream().map(item -> {
			CmdbDictVO cmdbDictVO = new CmdbDictVO();

			cmdbDictVO.setCiId((Long) item.get(CmdbAttrConstant.CI_ID));
			cmdbDictVO.setCientityUuid((String) item.get(CmdbAttrConstant.UUID));
			cmdbDictVO.setCiEntityName((String) item.get(CmdbAttrConstant.CI_NAME));

			cmdbDictVO.setCientityId((String) item.get(CmdbAttrConstant.DICT_KEY));
			cmdbDictVO.setDictKey((String) item.get(CmdbAttrConstant.REMARK_TEMP));
			cmdbDictVO.setDictValue((String) item.get(CmdbAttrConstant.DICT_VALUE));

			Object dataSource = item.get(CmdbAttrConstant.DATA_SOURCE_DICT);
			if (Objects.nonNull(dataSource)) {
				cmdbDictVO.setDataSource(String.valueOf(dataSource));
			}

			Object pid = item.get(CmdbAttrConstant.PID_DICT);
			if (Objects.nonNull(pid)) {
				cmdbDictVO.setCientityPid(String.valueOf(pid));
				List<String> data = new ArrayList<>();
				cmdbDictVO.setCientityPid(String.valueOf(pid));
				CmdbDict dict = new CmdbDict();
				dict.setCiId(ciId);
				dict.setDictKey(String.valueOf(pid));
				dict.setCurrentPage(1);
				dict.setPageSize(10);
				List<String> stringList = search(dict, data);
				if (ObjectUtil.isNotEmpty(getElementSafely(stringList,0))){
					cmdbDictVO.setUpOneCode(stringList.get(0));
				}
				if (ObjectUtil.isNotEmpty(getElementSafely(stringList,1))){
					cmdbDictVO.setUpOneName(stringList.get(1));
				}
				if (ObjectUtil.isNotEmpty(getElementSafely(stringList,2))){
					cmdbDictVO.setUpTwoCode(stringList.get(2));
				}
				if (ObjectUtil.isNotEmpty(getElementSafely(stringList,3))){
					cmdbDictVO.setUpTwoName(stringList.get(3));
				}
				if (ObjectUtil.isNotEmpty(getElementSafely(stringList,4))){
					cmdbDictVO.setUpThreeCode(stringList.get(4));
				}
				if (ObjectUtil.isNotEmpty(getElementSafely(stringList,5))){
					cmdbDictVO.setUpThreeName(stringList.get(5));
				}
			}

			Object pidName = item.get(CmdbAttrConstant.PID_NAME);
			if (Objects.nonNull(pidName)) {
				cmdbDictVO.setCientityPidName((String) pidName);
			}
			return cmdbDictVO;
		}).collect(Collectors.toList());

		Page page = new Page();
		page.setRecords(cmdbDictVOS);
		page.setTotal(feignCiCientity.getTotal());
		return page;
	}
	private String getElementSafely(List<String> list,int index){
		if (index>=0 && index< list.size()){
			return list.get(index);
		}else{
			return null;
		}
	}
	private List<String> search(CmdbDict cmdbDict,List<String> cmdbDictVOList) {
		Long ciIdSearch = 0L;
		if (cmdbDict.getCiId() == 1082608047161344L) {
			ciIdSearch = 1082609011851264L;
		} else if (cmdbDict.getCiId() == 1082609011851264L) {
			ciIdSearch = 1082554947272704L;
		} else if (cmdbDict.getCiId() == 1082554947272704L) {
			ciIdSearch = 1082610161090560L;
		}else {
			return cmdbDictVOList;
		}
		if (Objects.isNull(ciIdSearch)) {
			throw new RuntimeException("模型ID不能为空");
		}
		FeignCmdbDictCientitySearch cientitySearch = new FeignCmdbDictCientitySearch();
		cientitySearch.setFilterCiEntityId(Long.valueOf(cmdbDict.getDictKey()));
		cientitySearch.setCiId(ciIdSearch);
		cientitySearch.setCurrentPage(cmdbDict.getCurrentPage());
		cientitySearch.setPageSize(cmdbDict.getPageSize());
		Map<String, Object> stringObjectMap = CmdbCiAttrWrapper.build().feignCientityDetailById(ciIdSearch, Long.valueOf(cmdbDict.getDictKey()));
		String dictKey = String.valueOf(stringObjectMap.get(CmdbAttrConstant.DICT_KEY));
		String dictValue = String.valueOf(stringObjectMap.get(CmdbAttrConstant.DICT_VALUE));
		//编码
		cmdbDictVOList.add(dictKey);
		//名称
		cmdbDictVOList.add(dictValue);
		Long ciId = ((Long) stringObjectMap.get(CmdbAttrConstant.CI_ID));
		if (Objects.nonNull(stringObjectMap.get(CmdbAttrConstant.PID_DICT))){
			CmdbDict dict = new CmdbDict();
			dict.setPageSize(2);
			dict.setCurrentPage(1);
			dict.setCiId(ciId);
			dict.setDictKey(String.valueOf(stringObjectMap.get(CmdbAttrConstant.PID_DICT)));
			search(dict,cmdbDictVOList);
		}
		return cmdbDictVOList;
	}

	/**
	 * 新增 字典配置项(CMDB)
	 *
	 * @param cmdbDict
	 * @param transactionActionType
	 * @return
	 */
	@Override
	public Map<String, Object> cientityBatchsave(CmdbDict cmdbDict, TransactionActionType transactionActionType) {

		Long ciId = cmdbDict.getCiId();
		if (Objects.isNull(ciId)) {
			throw new RuntimeException("模型ID不能为空!");
		}
		CmdbDictCi cmdbDictCi = new CmdbDictCi();
		cmdbDictCi.setCiId(ciId);
		CmdbDictCi cmdbDictCiOne = cmdbDictCiService.getOne(Condition.getQueryWrapper(cmdbDictCi));
		if (Objects.isNull(cmdbDictCiOne)) {
			throw new RuntimeException("模型不存在,请联系运维人员配置!");
		}

		Map<String, Map<String, Object>> entity = new HashMap<>();
		Map<String, Object> map = new HashMap<>();
		map.put(CmdbAttrConstant.DICT_KEY, cmdbDict.getDictKey());
		map.put(CmdbAttrConstant.REMAKR, cmdbDict.getDictKey());
		map.put(CmdbAttrConstant.DICT_VALUE, cmdbDict.getDictValue());
		map.put(CmdbAttrConstant.SORT, cmdbDict.getSort());
		map.put(CmdbAttrConstant.DICT_KEY_ERP, cmdbDict.getDictKeyErp());
		map.put(CmdbAttrConstant.DICT_VALUE_ERP, cmdbDict.getDictValueErp());
		map.put(CmdbAttrConstant.DICT_KEY_I6000, cmdbDict.getDictKeyI6000());
		map.put(CmdbAttrConstant.DICT_VALUE_I6000, cmdbDict.getDictValueI6000());
		Integer isDeleted = cmdbDict.getIsDeleted();
		map.put(CmdbAttrConstant.IS_DELETED, Objects.nonNull(isDeleted) && isDeleted == 0 ? DeviceConstant.NO : DeviceConstant.YES);
		String pidI6000 = cmdbDict.getPidI6000();
		map.put(CmdbAttrConstant.PID_I6000_DICT, pidI6000);
		map.put(CmdbAttrConstant.DATA_SOURCE_DICT, cmdbDict.getDataSource());
		if (Objects.isNull(cmdbDict.getDataSource())) {
			map.put(CmdbAttrConstant.DATA_SOURCE_DICT, Long.valueOf(cmdbCientityProperties.getDictValueType1()));
		}
		Integer sort = cmdbDict.getSort();
		map.put(CmdbAttrConstant.SORT, sort);
		if (Objects.isNull(sort)) {
			CmdbDict cmdbDictSelect = new CmdbDict();
			cmdbDictSelect.setCiId(ciId);
			cmdbDictSelect.setPageSize(1);
			cmdbDictSelect.setCurrentPage(1);

			IPage<CmdbDictVO> cmdbDictVOIPage = this.cientitySelectDict(cmdbDictSelect);
			long total = cmdbDictVOIPage.getTotal();
			map.put(CmdbAttrConstant.SORT, total + 1);
		}

		String cientityPid = cmdbDict.getCientityPid();
		if (cmdbDictCiOne.getIsExistCascade() == 1) {
			if (StringUtils.isEmpty(cientityPid)) {
				throw new RuntimeException(cmdbDictCiOne.getCiLabel() + " 模型存在级联关系,请增加级联关系信息!");
			} else {
				map.put(CmdbAttrConstant.PID_DICT, cientityPid);
			}
		}
		String uuid = UuidUtils.uuid();
		String cientityUuid = cmdbDict.getCientityUuid();
		if (StringUtils.isNotEmpty(cientityUuid)) {
			uuid = cientityUuid;
		}
		entity.put(uuid, map);
		Map<String, Object> result = cmdbService.cientityBatchsaveDict(ciId, entity, cmdbDictCiOne, TransactionActionType.INSERT);

		return result;
	}

	/**
	 * 修改 字典配置项(CMDB)
	 *
	 * @param cmdbDict
	 * @param transactionActionType
	 * @return
	 */
	@Override
	public Map<String, Object> cientityBatchupdate(CmdbDict cmdbDict, TransactionActionType transactionActionType) {

		Long ciId = cmdbDict.getCiId();
		if (Objects.isNull(ciId)) {
			throw new RuntimeException("模型ID不能为空!");
		}

		String cientityId = cmdbDict.getCientityId();
		if (Objects.isNull(cientityId)) {
			throw new RuntimeException("配置项ID不能为空!");
		}

		String cientityUuid = cmdbDict.getCientityUuid();
		if (StringUtils.isEmpty(cientityUuid)) {
			throw new RuntimeException("配置项UUID不能为空!");
		}

		CmdbDictCi cmdbDictCi = new CmdbDictCi();
		cmdbDictCi.setCiId(ciId);
		CmdbDictCi cmdbDictCiOne = cmdbDictCiService.getOne(Condition.getQueryWrapper(cmdbDictCi));
		if (Objects.isNull(cmdbDictCiOne)) {
			throw new RuntimeException("模型不存在,请联系运维人员配置!");
		}

		Map<Long, Map<String, Object>> entity = new HashMap<>();
		Map<String, Object> map = new HashMap<>();
		map.put(CmdbAttrConstant.DICT_KEY, cmdbDict.getDictKey());
		map.put(CmdbAttrConstant.REMAKR, cmdbDict.getDictKey());
		map.put(CmdbAttrConstant.DICT_VALUE, cmdbDict.getDictValue());
		map.put(CmdbAttrConstant.SORT, cmdbDict.getSort());
		map.put(CmdbAttrConstant.DICT_KEY_ERP, cmdbDict.getDictKeyErp());
		map.put(CmdbAttrConstant.DICT_VALUE_ERP, cmdbDict.getDictValueErp());
		map.put(CmdbAttrConstant.DICT_KEY_I6000, cmdbDict.getDictKeyI6000());
		map.put(CmdbAttrConstant.DICT_VALUE_I6000, cmdbDict.getDictValueI6000());
		Integer isDeleted = cmdbDict.getIsDeleted();
		map.put(CmdbAttrConstant.IS_DELETED, Objects.nonNull(isDeleted) && isDeleted == 0 ? DeviceConstant.NO : DeviceConstant.YES);
		String pidI6000 = cmdbDict.getPidI6000();
		map.put(CmdbAttrConstant.PID_I6000_DICT, pidI6000);
		map.put(CmdbAttrConstant.DATA_SOURCE_DICT, cmdbDict.getDataSource());
		if (Objects.isNull(cmdbDict.getDataSource())) {
			map.put(CmdbAttrConstant.DATA_SOURCE_DICT, Long.valueOf(cmdbCientityProperties.getDictValueType1()));
		}

		String cientityPid = cmdbDict.getCientityPid();
		if (cmdbDictCiOne.getIsExistCascade() == 1) {
			if (StringUtils.isEmpty(cientityPid)) {
				throw new RuntimeException(cmdbDictCiOne.getCiLabel() + " 模型存在级联关系,请增加级联关系信息!");
			} else {
				map.put(CmdbAttrConstant.PID_DICT, cientityPid);
			}
		}

		map.put(CmdbAttrConstant.ID, cientityId);
		map.put(CmdbAttrConstant.CI_ID, ciId);
		map.put(CmdbAttrConstant.UUID, ciId);
		entity.put(Long.valueOf(cientityId), map);
		Map<String, Object> result = cmdbService.cientityBatchupdateDict(entity, cmdbDictCiOne, TransactionActionType.UPDATE);

		return result;
	}

	/**
	 * 删除 字典配置项(CMDB)
	 *
	 * @param cmdbDictDeleteDTOList
	 * @param transactionActionType
	 * @return
	 */
	@Override
	public Boolean cientityDelete(List<CmdbDictDeleteDTO> cmdbDictDeleteDTOList, TransactionActionType transactionActionType) {
		FeignCmdbCientityBatchDelete feignCmdbCientityBatchDelete = new FeignCmdbCientityBatchDelete();

		List<FeignCmdbCientityBatchDelete.CiEntity> ciEntityList = cmdbDictDeleteDTOList.stream().map(item -> {
			FeignCmdbCientityBatchDelete.CiEntity ciEntity = new FeignCmdbCientityBatchDelete.CiEntity();
			ciEntity.setCiEntityId(item.getCiEntityId());
			ciEntity.setCiEntityName(item.getCiEntityName());
			ciEntity.setCiId(item.getCiId());
			return ciEntity;
		}).collect(Collectors.toList());

		feignCmdbCientityBatchDelete.setNeedCommit(Boolean.TRUE);
		feignCmdbCientityBatchDelete.setCiEntityList(ciEntityList);
		feignCmdbCientityBatchDelete.setDescription("删除字典配置项");


		return cmdbService.cientityBatchDelete(feignCmdbCientityBatchDelete);
	}
}
