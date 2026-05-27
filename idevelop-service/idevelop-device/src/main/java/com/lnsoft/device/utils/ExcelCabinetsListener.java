package com.lnsoft.device.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.lnsoft.device.api.asset.dto.ExportCabinets;
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
public class ExcelCabinetsListener extends AnalysisEventListener<ExportCabinets> {

	private List<ExportCabinets> dataList;

	public ExcelCabinetsListener(List<ExportCabinets> dataList) {
		this.dataList = dataList;
	}


	@Override
	public void invoke(ExportCabinets exportCabinets, AnalysisContext analysisContext) {
		dataList.add(exportCabinets);
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext analysisContext) {
		// todo
	}
}
