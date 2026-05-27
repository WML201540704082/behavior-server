package com.lnsoft.device.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.lnsoft.device.api.asset.dto.ExportRoom;
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
public class ExcelRoomListener extends AnalysisEventListener<ExportRoom> {

	private List<ExportRoom> dataList;

	public ExcelRoomListener(List<ExportRoom> dataList) {
		this.dataList = dataList;
	}


	@Override
	public void invoke(ExportRoom exportRoom, AnalysisContext analysisContext) {
		dataList.add(exportRoom);
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext analysisContext) {
		// todo
	}
}
