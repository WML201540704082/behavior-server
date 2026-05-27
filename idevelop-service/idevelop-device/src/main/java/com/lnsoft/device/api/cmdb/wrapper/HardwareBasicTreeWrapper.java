package com.lnsoft.device.api.cmdb.wrapper;

import com.alibaba.fastjson.JSONObject;
import com.lnsoft.cmdb.entity.FeignCmdbCiListattr;
import com.lnsoft.cmdb.entity.FeignCmdbCiListglobalattr;
import com.lnsoft.device.entity.HardwareBasicTree;
import com.lnsoft.cmdb.feign.ICmdbClient;
import com.lnsoft.device.vo.HardwareBasicTreeVO;
import com.lnsoft.core.mp.support.BaseEntityWrapper;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.BeanUtil;
import com.lnsoft.core.tool.utils.SpringUtil;

/**
 * @Author: xuel
 * @CreateTime: 2024/2/22 13:45
 * @Description: 包装类,返回视图层所需的字段 HardwareBasicTreeWrapper
 */
public class HardwareBasicTreeWrapper extends BaseEntityWrapper<HardwareBasicTree, HardwareBasicTreeVO> {

	private static ICmdbClient iCmdbClient;

	static {
		iCmdbClient = SpringUtil.getBean(ICmdbClient.class);
	}

	public static HardwareBasicTreeWrapper build() {
		return new HardwareBasicTreeWrapper();
	}

	@Override
	public HardwareBasicTreeVO entityVO(HardwareBasicTree hardwareBasicTree) {
		HardwareBasicTreeVO hardwareBasicTreeVO = BeanUtil.copy(hardwareBasicTree, HardwareBasicTreeVO.class);
		return hardwareBasicTreeVO;
	}

	public JSONObject resourcetypeTree(String keyword) {
		R<JSONObject> resourcetypedTree = iCmdbClient.feignResourcetypeTree(keyword);
		return resourcetypedTree.getData();
	}

	public JSONObject feignCiListattrBy(FeignCmdbCiListattr feignCmdbCiListattr) {
		R<JSONObject> feignCiListattr = iCmdbClient.feignCiListattrBy(feignCmdbCiListattr);
		return feignCiListattr.getData();
	}

	public JSONObject feignCiListglobalattr(FeignCmdbCiListglobalattr feignCmdbCiListglobalattr) {
		R<JSONObject> feignCiListglobalattr = iCmdbClient.feignCiListglobalattr(feignCmdbCiListglobalattr);
		return feignCiListglobalattr.getData();
	}


}
