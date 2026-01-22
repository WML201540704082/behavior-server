package com.lnsoft.develop.feign;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.develop.entity.Datasource;
import com.lnsoft.develop.mapper.DatasourceMapper;
import com.lnsoft.devicelop.entity.DatasourceLop;
import com.lnsoft.devicelop.feign.IDatasourceClient;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

@ApiIgnore
@RestController
public class DatasourceClient implements IDatasourceClient {

	@Resource
	private DatasourceMapper datasourceMapper;

	/**
	 * 获取天擎数据源配置
	 *
	 * @return R
	 */
	@Override
	public R<List<DatasourceLop>> getTianQingSourceList() {
		List<Datasource> datasourceList = datasourceMapper.selectList(new LambdaQueryWrapper<Datasource>()
			.likeRight(Datasource::getName, "tianqing"));
		return R.data(Convert.convert(new TypeReference<List<DatasourceLop>>() {
		}, datasourceList));
	}
}
