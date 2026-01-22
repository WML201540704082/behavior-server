
package com.lnsoft.system.feign;


import lombok.AllArgsConstructor;
import com.lnsoft.system.entity.Dict;
import com.lnsoft.system.service.IDictService;
import com.lnsoft.core.tool.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


/**
 * 字典服务Feign实现类
 *
 * @author guozhao
 */
@ApiIgnore
@RestController
@AllArgsConstructor
public class DictClient implements IDictClient {

	IDictService service;

	@Override
	@GetMapping(API_PREFIX + "/getValue")
	public R<String> getValue(String code, Integer dictKey) {

		return R.data(service.getValue(code, dictKey));
	}
	@Override
	@GetMapping(API_PREFIX + "/getKey")
	public R<String> getKey(String code, String value) {
		return R.data(service.getKey(code, value));
	}

	@Override
	@GetMapping(API_PREFIX + "/getList")
	public R<List<Dict>> getList(String code) {
		return R.data(service.getList(code));
	}

}
