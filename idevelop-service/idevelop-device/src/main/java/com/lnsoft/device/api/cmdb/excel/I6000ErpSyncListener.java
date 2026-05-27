package com.lnsoft.device.api.cmdb.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.lnsoft.device.api.cmdb.entity.I6000ErpImport;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class I6000ErpSyncListener extends AnalysisEventListener<I6000ErpImport> {
	private List<I6000ErpImport> list = new ArrayList<>();
	@Override
	public void invoke(I6000ErpImport i6000ErpImport, AnalysisContext analysisContext) {
		list.add(i6000ErpImport);
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext analysisContext) {

	}
}
