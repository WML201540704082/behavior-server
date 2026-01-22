
package com.lnsoft.system.feign;

import com.lnsoft.system.entity.Dict;
import com.lnsoft.core.tool.api.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Feign失败配置
 *
 * @author guozhao
 */
@Component
public class IDictClientFallback implements IDictClient {
	@Override
	public R<String> getValue(String code, Integer dictKey) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<String> getKey(String code, String value) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<List<Dict>> getList(String code) {
		return R.fail("获取数据失败");
	}
}
