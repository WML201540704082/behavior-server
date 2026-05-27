package com.lnsoft.device.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.lnsoft.device.api.warehouse.dto.DeviceScrapListDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @ClassName: ExcelListener
 * @author cwb
 * @since 2024-03-18
 */
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ScrapDeviceListener extends AnalysisEventListener<DeviceScrapListDTO> {

	private List<DeviceScrapListDTO> dataList;

	public ScrapDeviceListener(List<DeviceScrapListDTO> dataList) {
		this.dataList = dataList;
	}


	@Override
	public void invoke(DeviceScrapListDTO deviceTransferDeviceDTO, AnalysisContext analysisContext) {
		dataList.add(deviceTransferDeviceDTO);
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext analysisContext) {
		// todo
	}
}
