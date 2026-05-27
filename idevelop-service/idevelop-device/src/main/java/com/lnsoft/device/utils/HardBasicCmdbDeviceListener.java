package com.lnsoft.device.utils;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.lnsoft.core.tool.utils.CollectionUtil;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.device.annotation.ExcelImportValid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName: HardBasicCmdbDeviceListener
 * @description: cmdb数据 导入程序
 **/
@AllArgsConstructor
public class HardBasicCmdbDeviceListener extends AnalysisEventListener {

	private static final Logger log = LoggerFactory.getLogger(HardBasicCmdbDeviceListener.class);

	private List<Map<String, Object>> dataList;


	@Override
	public void invoke(Object o, AnalysisContext analysisContext) {
		log.info("数据对象：{}", o);
		List<String> error = ExcelImportValid.valid(o);
		Map<String, Object> map = BeanUtil.beanToMap(o, false, true);
		//导入的时候 获取 解析信息
		if (CollectionUtil.isNotEmpty(error)) {
			String errorStr = error.stream().collect(Collectors.joining(","));
			map.put("exceptionField", errorStr + ",");
		}
		dataList.add(map);
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext analysisContext) {

	}
}
