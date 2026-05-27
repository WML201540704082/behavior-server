package com.lnsoft.device.api.operation.listener;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.lnsoft.device.annotation.ExcelImportValid;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class ChangeListener extends AnalysisEventListener {

	private List<Map<String, Object>> dataList;

	@Override
	public void invoke(Object object, AnalysisContext analysisContext) {
		List<String> error = ExcelImportValid.valid(object);
		Map<String, Object> map = BeanUtil.beanToMap(object, false, false);
		dataList.add(map);
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext analysisContext) {

	}
}
