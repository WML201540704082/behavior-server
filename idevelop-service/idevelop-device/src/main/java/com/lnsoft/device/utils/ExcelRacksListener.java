package com.lnsoft.device.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.lnsoft.device.api.asset.dto.ExportRacks;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @ClassName: ExcelListener
 * @description:
 * @author: xyz
 * @create: 2024-03-02
 **/
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ExcelRacksListener extends AnalysisEventListener<ExportRacks> {

	private List<ExportRacks> dataList;

	public ExcelRacksListener(List<ExportRacks> dataList) {
		this.dataList = dataList;
	}


	@Override
	public void invoke(ExportRacks exportRacks, AnalysisContext analysisContext) {
		dataList.add(exportRacks);
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext analysisContext) {
		// todo
	}
}
