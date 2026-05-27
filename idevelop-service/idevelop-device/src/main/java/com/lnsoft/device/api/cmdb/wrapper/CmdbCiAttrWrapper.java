package com.lnsoft.device.api.cmdb.wrapper;

import com.alibaba.fastjson.JSONObject;
import com.lnsoft.cmdb.entity.*;
import com.lnsoft.cmdb.feign.ICmdbClient;
import com.lnsoft.cmdb.vo.CmdbCiAttrVO;
import com.lnsoft.core.mp.support.BaseEntityWrapper;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.BeanUtil;
import com.lnsoft.core.tool.utils.SpringUtil;

import java.util.List;
import java.util.Map;

/**
 * @Author: xuel
 * @CreateTime: 2024/2/22 13:45
 * @Description: 包装类, 返回视图层所需的字段 HardwareBasicWrapper
 */
public class CmdbCiAttrWrapper extends BaseEntityWrapper<CmdbCiAttr, CmdbCiAttrVO> {

	private static ICmdbClient iCmdbClient;

	static {
		iCmdbClient = SpringUtil.getBean(ICmdbClient.class);
	}

	public static CmdbCiAttrWrapper build() {
		return new CmdbCiAttrWrapper();
	}

	@Override
	public CmdbCiAttrVO entityVO(CmdbCiAttr cmdbCiAttr) {
		CmdbCiAttrVO cmdbCiAttrVO = BeanUtil.copy(cmdbCiAttr, CmdbCiAttrVO.class);
		return cmdbCiAttrVO;
	}


	public R<JSONObject> cientityValidate(FeignCmdbCientityValidate cmdbCientityValidateVO) {
		R<JSONObject> responseEntity = iCmdbClient.feignCientityValidate(cmdbCientityValidateVO);
		return responseEntity;
	}

	public R<JSONObject> cientityXtythBatchsave(FeignCmdbCientityBatchsave cmdbCientityBatchsaveVO) {
		R<JSONObject> responseEntity = iCmdbClient.feignCientityXtythBatchsave(cmdbCientityBatchsaveVO);
		return responseEntity;
	}

	public R<JSONObject> cientityBatchsave(FeignCmdbCientityBatchsave cmdbCientityBatchsaveVO) {
		R<JSONObject> responseEntity = iCmdbClient.feignCientityBatchsave(cmdbCientityBatchsaveVO);
		return responseEntity;
	}

	public R<FeignCiCientity> getCiCientityListPage(FeignCmdbCientitySearch feignCmdbCientitySearch) {
		R<FeignCiCientity> responseEntity = iCmdbClient.feignGetCiCientityListPage(feignCmdbCientitySearch);
		return responseEntity;
	}

	public R<List<Map<String, Object>>> getCiCientityDictList(Long ciId) {
		R<List<Map<String, Object>>> responseEntity = iCmdbClient.feignGetCiCientityDictList(ciId);
		return responseEntity;
	}

	public R<List<Map<String, Object>>> feignGetCiEntityDictListByPid(Long ciId, Long pid) {
		R<List<Map<String, Object>>> responseEntity = iCmdbClient.feignGetCiEntityDictListByPid(ciId, pid);
		return responseEntity;
	}

	public R<JSONObject> feignCientityGet(FeignCmdbCientityGet feignCmdbCientityGet) {
		R<JSONObject> responseEntity = iCmdbClient.feignCientityGet(feignCmdbCientityGet);
		return responseEntity;
	}

	public Map<String, Object> feignCientityDelete(Long id, String description) {
		R<JSONObject> responseEntity = iCmdbClient.feignCientityDelete(id, description);
		JSONObject data = responseEntity.getData();
		Map<String, Object> innerMap = data.getInnerMap();
		return innerMap;
	}

	public R<Map<String, Object>> feignCientityBatchDelete(FeignCmdbCientityBatchDelete feignCmdbCientityBatchDelete) {
		R<Map<String, Object>> responseEntity = iCmdbClient.feignCientityBatchDelete(feignCmdbCientityBatchDelete);
		return responseEntity;
	}

	public Map<String, Object> feignCientityBatchupdate(FeignCmdbCientityBatchupdate feignCmdbCientityBatchupdate) {
		R<Map<String, Object>> responseEntity = iCmdbClient.feignCientityBatchupdate(feignCmdbCientityBatchupdate);
		return responseEntity.getData();
	}


	public List<Map<String, Object>> feignGetCiEntityDictListById(FeignCmdbDictCientitySearch feignCmdbDictCientitySearch) {
		R<List<Map<String, Object>>> responseEntity = iCmdbClient.feignGetCiEntityDictListById(feignCmdbDictCientitySearch);
		return responseEntity.getData();
	}

	public FeignCiCientity feignGetCiEntityDictPage(FeignCmdbDictCientitySearch feignCmdbDictCientitySearch) {
		R<FeignCiCientity> responseEntity = iCmdbClient.feignGetCiEntityDictPage(feignCmdbDictCientitySearch);
		return responseEntity.getData();
	}

	public Map<String, Object> feignCientityDetailById(Long ciId, Long ciEntityId){
		R<Map<String, Object>> mapR = iCmdbClient.feignCientityDetailById(ciId, ciEntityId);
		return mapR.getData();
	}
}
