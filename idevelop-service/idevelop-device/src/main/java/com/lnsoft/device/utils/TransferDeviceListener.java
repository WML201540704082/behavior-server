package com.lnsoft.device.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.lnsoft.device.api.warehouse.dto.DeviceTransferDeviceDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @ClassName: ExcelListener
 * @description: 设备转资 导入
 * @author: hujia
 * @create: 2024-03-04
 **/
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TransferDeviceListener extends AnalysisEventListener<DeviceTransferDeviceDTO> {

	private List<DeviceTransferDeviceDTO> dataList;

	public TransferDeviceListener(List<DeviceTransferDeviceDTO> dataList) {
		this.dataList = dataList;
	}


	@Override
	public void invoke(DeviceTransferDeviceDTO deviceTransferDeviceDTO, AnalysisContext analysisContext) {
		dataList.add(deviceTransferDeviceDTO);
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext analysisContext) {
		// todo
	}
}
