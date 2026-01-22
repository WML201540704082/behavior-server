
package com.lnsoft.system.wrapper;

import com.lnsoft.common.constant.CommonConstant;
import com.lnsoft.system.entity.Dict;
import com.lnsoft.system.vo.DictVO;
import com.lnsoft.core.mp.support.BaseEntityWrapper;
import com.lnsoft.core.tool.node.ForestNodeMerger;
import com.lnsoft.core.tool.utils.BeanUtil;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.core.tool.utils.SpringUtil;
import com.lnsoft.system.service.IDictService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author guozhao
 */
public class DictWrapper extends BaseEntityWrapper<Dict, DictVO> {

	private static IDictService dictService;

	static {
		dictService = SpringUtil.getBean(IDictService.class);
	}

	public static DictWrapper build() {
		return new DictWrapper();
	}

	@Override
	public DictVO entityVO(Dict dict) {
		DictVO dictVO = BeanUtil.copy(dict, DictVO.class);
		if (Func.equals(dict.getParentId(), CommonConstant.TOP_PARENT_ID)) {
			dictVO.setParentName(CommonConstant.TOP_PARENT_NAME);
		} else {
			Dict parent = dictService.getById(dict.getParentId());
			dictVO.setParentName(parent.getDictValue());
		}
		return dictVO;
	}

	public List<DictVO> listNodeVO(List<Dict> list) {
		List<DictVO> collect = list.stream().map(dict -> BeanUtil.copy(dict, DictVO.class)).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

}
