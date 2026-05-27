package com.lnsoft.device.api.cmdb.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.task.CmdbTask;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * CMDB修复接口 控制器
 *
 * @author xueli
 * @since 2024-04-18
 */

@RestController
@AllArgsConstructor
@RequestMapping("/cmdb/task")
@Api(value = "CMDB任务接口", tags = "CMDB任务接口")
public class CmdbTaskController {


	private CmdbTask cmdbTask;


	/**
	 * 手动触发异步 刷新固定值 任务
	 */
	@PostMapping("/refresh/fixedvalue")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "手动触发异步 刷新固定值 任务")
	public R<Boolean> refreshFixedValue(@RequestBody Map<String, Object> fixedValueMap) {
		// 手动触发异步 刷新固定值 任务
		cmdbTask.refreshFixedValue(fixedValueMap);
		return R.data(Boolean.TRUE);
	}


	/**
	 * 手动触发异步 地市需求字段 任务
	 */
	@PostMapping("/refresh/unitdept")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "手动触发异步 地市需求字段 任务")
	public R<Boolean> refreshUnitDept(@RequestBody List<JSONObject> fixedValueMapList) {
		for (JSONObject fixedValueMap : fixedValueMapList) {
			// 手动触发异步 刷新固定值 任务
			cmdbTask.refreshUnitDept(fixedValueMap);
		}
		return R.data(Boolean.TRUE);
	}
}
