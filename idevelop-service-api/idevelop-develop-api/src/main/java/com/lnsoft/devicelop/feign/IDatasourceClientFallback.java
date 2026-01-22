package com.lnsoft.devicelop.feign;

import com.lnsoft.core.tool.api.R;
import com.lnsoft.devicelop.entity.DatasourceLop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class IDatasourceClientFallback implements FallbackFactory<IDatasourceClient> {

	private static final Logger log = LoggerFactory.getLogger(IDatasourceClientFallback.class);

	@Override
	public IDatasourceClient create(Throwable cause) {
		return new IDatasourceClient() {

			@Override
			public R<List<DatasourceLop>> getTianQingSourceList() {
				return R.data(new ArrayList<>());
			}
		};
	}
}
