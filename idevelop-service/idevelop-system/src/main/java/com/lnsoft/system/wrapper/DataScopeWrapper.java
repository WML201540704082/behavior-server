
package com.lnsoft.system.wrapper;

import com.lnsoft.system.vo.DataScopeVO;
import com.lnsoft.core.mp.support.BaseEntityWrapper;
import com.lnsoft.core.tool.utils.BeanUtil;
import com.lnsoft.core.tool.utils.SpringUtil;
import com.lnsoft.system.entity.DataScope;
import com.lnsoft.system.service.IDictService;

import java.util.Objects;


/**
 * 包装类,返回视图层所需的字段
 *
 * @author guozhao
 */
public class DataScopeWrapper extends BaseEntityWrapper<DataScope, DataScopeVO> {

	private static IDictService dictService;

	static {
		dictService = SpringUtil.getBean(IDictService.class);
	}

	public static DataScopeWrapper build() {
		return new DataScopeWrapper();
	}

	@Override
	public DataScopeVO entityVO(DataScope dataScope) {
		DataScopeVO dataScopeVO = Objects.requireNonNull(BeanUtil.copy(dataScope, DataScopeVO.class));
		String scopeTypeName = dictService.getValue("data_scope_type", dataScope.getScopeType());
		dataScopeVO.setScopeTypeName(scopeTypeName);
		return dataScopeVO;
	}

}
