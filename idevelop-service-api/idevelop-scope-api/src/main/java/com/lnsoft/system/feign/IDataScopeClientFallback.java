
package com.lnsoft.system.feign;

import com.lnsoft.core.datascope.model.DataScopeModel;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * IDataScopeClientFallback
 *
 * @author guozhao
 */
@Component
public class IDataScopeClientFallback implements IDataScopeClient {
	@Override
	public DataScopeModel getDataScopeByMapper(String mapperId, String roleId) {
		return null;
	}

	@Override
	public DataScopeModel getDataScopeByCode(String code) {
		return null;
	}

	@Override
	public List<Long> getDeptAncestors(Long deptId) {
		return null;
	}
}
