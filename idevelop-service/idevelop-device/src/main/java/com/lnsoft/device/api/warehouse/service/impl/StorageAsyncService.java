package com.lnsoft.device.api.warehouse.service.impl;

import com.lnsoft.device.api.erp.service.IErpService;
import com.lnsoft.device.api.i6000.service.II6000Service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @ClassName: StorageAsyncService
 * @description:
 * @author: zhangs
 * @create: 2024-04-01 14:16
 **/
@Service
@AllArgsConstructor
public class StorageAsyncService {

	private II6000Service ii6000Service;
	private IErpService erpService;

	// @Async
	// public Future<String> syncI6000(String deviceType, Map<String, Map<String, Object>> map) {
	// 	I6000ResultResp i6000ResultResp = ii6000Service.i6000Batchsave(deviceType, map);
	// 	return new AsyncResult<>(i6000ResultResp.getSuccessful());
	// }
	//
	// @Async
	// public AsyncResult<ErpTransEqunrResp> syncErp(ErpTransEqunr erpTransEqunr) {
	// 	ErpTransEqunrResp erpTransEqunrResp = erpService.transEqunr(erpTransEqunr);
	// 	return new AsyncResult<>(erpTransEqunrResp);
	// }
}
