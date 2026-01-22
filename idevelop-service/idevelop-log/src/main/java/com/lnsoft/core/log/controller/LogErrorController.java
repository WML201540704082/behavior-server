
package com.lnsoft.core.log.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lnsoft.core.log.model.LogError;
import com.lnsoft.core.log.model.LogErrorVo;
import com.lnsoft.core.log.service.ILogErrorService;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.secure.annotation.PreAuth;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.BeanUtil;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.core.tool.utils.StringPool;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 控制器
 *
 * @author guozhao
 * @since 2018-09-26
 */
@RestController
@AllArgsConstructor
@RequestMapping("/error")
public class LogErrorController {

	private ILogErrorService errorLogService;

	/**
	 * 查询单条
	 */
	@GetMapping("/detail")
	@PreAuth("hasPerm('system:common:all')")
	public R<LogError> detail(LogError logError) {
		return R.data(errorLogService.getOne(Condition.getQueryWrapper(logError)));
	}

	/**
	 * 查询多条(分页)
	 */
	@GetMapping("/list")
	@PreAuth("hasPerm('system:common:all')")
	public R<IPage<LogErrorVo>> list(@ApiIgnore @RequestParam Map<String, Object> logError, Query query) {
		query.setAscs("create_time");
		query.setDescs(StringPool.EMPTY);
		IPage<LogError> pages = errorLogService.page(Condition.getPage(query), Condition.getQueryWrapper(logError, LogError.class));
		List<LogErrorVo> records = pages.getRecords().stream().map(logApi -> {
			LogErrorVo vo = BeanUtil.copy(logApi, LogErrorVo.class);
			vo.setStrId(Func.toStr(logApi.getId()));
			return vo;
		}).collect(Collectors.toList());
		IPage<LogErrorVo> pageVo = new Page<>(pages.getCurrent(), pages.getSize(), pages.getTotal());
		pageVo.setRecords(records);
		return R.data(pageVo);
	}

}
