package com.lnsoft.devicelop.feign;

import com.lnsoft.core.tool.api.R;
import com.lnsoft.devicelop.entity.DatasourceLop;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author zhangs
 */
@FeignClient(
	value = "idevelop-develop",
	fallbackFactory = IDatasourceClientFallback.class
)
public interface IDatasourceClient {

	String API_PREFIX = "/devicelop";

	/**
	 * 获取天擎数据源配置
	 *
	 * @return R
	 */
	@PostMapping(API_PREFIX + "/getTianQingSourceList")
	R<List<DatasourceLop>> getTianQingSourceList();

}
