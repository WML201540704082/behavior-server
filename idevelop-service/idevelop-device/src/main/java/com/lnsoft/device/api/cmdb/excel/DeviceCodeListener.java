package com.lnsoft.device.api.cmdb.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.lnsoft.device.api.cmdb.entity.DeviceCodeStencil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DeviceCodeListener extends AnalysisEventListener<DeviceCodeStencil> {
	private List<DeviceCodeStencil> list = new ArrayList<>();
	@Override
	public void invoke(DeviceCodeStencil deviceCodeStencil, AnalysisContext analysisContext) {
		list.add(deviceCodeStencil);
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext analysisContext) {

	}
}
