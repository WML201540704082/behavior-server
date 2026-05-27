package com.lnsoft.device.api.erp.service;

import com.lnsoft.device.api.cmdb.entity.DeviceCodeStencil;
import com.lnsoft.device.api.erp.response.ErpTransEqunrResp;
import com.lnsoft.device.api.i6000.dto.I6000EntityIdDTO;
import com.lnsoft.device.entity.ZfitXtCwzt;

import java.util.List;
import java.util.Map;

public interface IErpSyncService {


	/**
	 * 设备台账主数据同步接口(手动维护) xtyth -> erp 同步
	 *
	 * @param map
	 * @return
	 */
	ErpTransEqunrResp manualTransEqunr(Map<String, Object> map, ZfitXtCwzt zfitXtCwzt);

	/**
	 * 根据信通一体化设备编码, 同步Erp系统数据
	 *
	 * @param list
	 * @return
	 */
	String importSyncErpDetail(List<DeviceCodeStencil> list);

	/**
	 * 获取I6000台账 填充信通一体化实物ID
	 *
	 * @param i6000EntityIdDTO
	 * @return
	 */
    String getI6000EntityId(I6000EntityIdDTO i6000EntityIdDTO);
}
