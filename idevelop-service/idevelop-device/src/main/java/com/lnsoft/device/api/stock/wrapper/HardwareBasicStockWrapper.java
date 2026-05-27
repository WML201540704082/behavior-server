package com.lnsoft.device.api.stock.wrapper;

import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.BeanUtil;
import com.lnsoft.core.tool.utils.SpringUtil;
import com.lnsoft.data.feign.IDeviceAssetCheckClient;
import com.lnsoft.data.vo.CmdbAttrConstantVO;
import com.lnsoft.data.vo.DeviceAssetCheckVO;

import java.util.Map;

/**
 * @Description: 数据治理-数据校验服务  HardwareBasicCmdbWrapper
 */
public class HardwareBasicStockWrapper {

	private static IDeviceAssetCheckClient iDeviceAssetCheckClient;

	static {
		iDeviceAssetCheckClient = SpringUtil.getBean(IDeviceAssetCheckClient.class);
	}

	public static HardwareBasicStockWrapper build() {
		return new HardwareBasicStockWrapper();
	}


	/**
	 * 校验数据文件
	 * @param device
	 * @return
	 */
	public R<Map<String, Object>> feignDeviceAssetCheck(Map<String, Object> device) {

		//接受map 抓换对象 调用方法
		CmdbAttrConstantVO constantVO = BeanUtil.toBean(device, CmdbAttrConstantVO.class);
		R<Map<String, Object>> results = iDeviceAssetCheckClient.feignDeviceAssetCheck(constantVO);
		return results;
	}


	/**
	 * 校验数据文件 通用
	 * @param device
	 * @return
	 */
	public R<Map<String, Object>> feignDeviceAssetCheckByMap(Map<String, Object> device,String ruleCode) {

		//接受map 抓换对象 调用方法
		DeviceAssetCheckVO constantVO = new DeviceAssetCheckVO();
		constantVO.setDevice(device);
		constantVO.setRuleCode(ruleCode);
		R<Map<String, Object>> results = iDeviceAssetCheckClient.feignDeviceAssetCheckByMap(constantVO);
		return results;
	}



}
