package com.lnsoft.device.api.asset.wrapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lnsoft.cmdb.entity.FeignCmdbCientityGet;
import com.lnsoft.cmdb.entity.HardwareBasic;
import com.lnsoft.cmdb.feign.ICmdbClient;
import com.lnsoft.cmdb.vo.HardwareBasicVO;
import com.lnsoft.core.mp.support.BaseEntityWrapper;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.BeanUtil;
import com.lnsoft.core.tool.utils.SpringUtil;
import com.lnsoft.data.dto.WarningListDTO;
import com.lnsoft.data.entity.DevelopWarning;
import com.lnsoft.data.feign.IDataClient;
import com.lnsoft.system.entity.Dept;
import com.lnsoft.system.feign.IDeptClient;
import com.lnsoft.system.feign.IRegionClient;
import com.lnsoft.system.vo.DeptVO;
import com.lnsoft.system.vo.RegionVO;

import java.util.List;
import java.util.Map;

/**
 * @Author: xuel
 * @CreateTime: 2024/2/22 13:45
 * @Description: 包装类, 返回视图层所需的字段 HardwareBasicWrapper
 */
public class HardwareBasicWrapper extends BaseEntityWrapper<HardwareBasic, HardwareBasicVO> {

	private static ICmdbClient iCmdbClient;
	private static IRegionClient iRegionClient;
	private static IDeptClient iDeptClient;
	private static IDataClient iDataClient;

	static {
		iCmdbClient = SpringUtil.getBean(ICmdbClient.class);
		iRegionClient = SpringUtil.getBean(IRegionClient.class);
		iDeptClient = SpringUtil.getBean(IDeptClient.class);
		iDataClient = SpringUtil.getBean(IDataClient.class);
	}

	public static HardwareBasicWrapper build() {
		return new HardwareBasicWrapper();
	}

	@Override
	public HardwareBasicVO entityVO(HardwareBasic hardwareBasic) {
		HardwareBasicVO hardwareBasicVO = BeanUtil.copy(hardwareBasic, HardwareBasicVO.class);
		return hardwareBasicVO;
	}

	public JSONObject cientitySearch(HardwareBasic hardwareBasic) {
		R<JSONObject> responseEntity = iCmdbClient.feignHardwareBasicList(hardwareBasic);
		return responseEntity.getData();
	}

	public JSONObject cientityGet(FeignCmdbCientityGet feignCmdbCientityGet) {
		R<JSONObject> responseEntity = iCmdbClient.feignCientityGet(feignCmdbCientityGet);
		return responseEntity.getData();
	}

	public List<RegionVO> lazyTree(String parentCode) {
		R<List<RegionVO>> listR = iRegionClient.lazyTree(parentCode);
		List<RegionVO> regionVOS = listR.getData();
		return regionVOS;
	}

	public List<DeptVO> getTreeList(String parentId){
		R<List<DeptVO>> treeList = iDeptClient.getTreeList(parentId);
		return treeList.getData();
	}


	public Page<DevelopWarning> warningList(WarningListDTO warningListDTO){
		R<Page<DevelopWarning>> pageR = iDataClient.warningList(warningListDTO);
		return pageR.getData();
	}

	public Map<String, Integer> warningStatistics(DevelopWarning developWarning) {
		R<Map<String, Integer>> result = iDataClient.warningStatistics(developWarning);
		return result.getData();
	}

	public Map<Long, Dept> getRegionDetail(Dept dept) {
		R<Map<Long, Dept>> result = iDeptClient.getRegionDetail(dept);
		return result.getData();
	}

	public Dept getDeptById(String id) {
		R<Dept> result = iDeptClient.getById(id);
		return result.getData();
	}
}
