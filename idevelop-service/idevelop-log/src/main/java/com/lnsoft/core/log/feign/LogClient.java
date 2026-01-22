
package com.lnsoft.core.log.feign;

import lombok.AllArgsConstructor;
import com.lnsoft.core.log.feign.ILogClient;
import com.lnsoft.core.log.model.LogApi;
import com.lnsoft.core.log.model.LogUsual;
import com.lnsoft.core.log.model.LogError;
import com.lnsoft.core.log.service.ILogApiService;
import com.lnsoft.core.log.service.ILogUsualService;
import com.lnsoft.core.log.service.ILogErrorService;
import com.lnsoft.core.tool.api.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志服务Feign实现类
 *
 * @author guozhao
 */
@RestController
@AllArgsConstructor
public class LogClient implements ILogClient {

	ILogUsualService usualLogService;

	ILogApiService apiLogService;

	ILogErrorService errorLogService;

	@Override
	@PostMapping(API_PREFIX + "/saveUsualLog")
	public R<Boolean> saveUsualLog(@RequestBody LogUsual log) {
		log.setParams(log.getParams().replace("&amp;", "&"));
		return R.data(usualLogService.save(log));
	}

	@Override
	@PostMapping(API_PREFIX + "/saveApiLog")
	public R<Boolean> saveApiLog(@RequestBody LogApi log) {
		log.setParams(log.getParams().replace("&amp;", "&"));
		return R.data(apiLogService.save(log));
	}

	@Override
	@PostMapping(API_PREFIX + "/saveErrorLog")
	public R<Boolean> saveErrorLog(@RequestBody LogError log) {
		log.setParams(log.getParams().replace("&amp;", "&"));
		return R.data(errorLogService.save(log));
	}
}
