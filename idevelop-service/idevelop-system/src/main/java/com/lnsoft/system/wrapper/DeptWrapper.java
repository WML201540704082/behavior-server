
package com.lnsoft.system.wrapper;

import com.lnsoft.common.constant.CommonConstant;
import com.lnsoft.core.mp.support.BaseEntityWrapper;
import com.lnsoft.core.tool.node.ForestNodeMerger;
import com.lnsoft.core.tool.utils.BeanUtil;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.core.tool.utils.SpringUtil;
import com.lnsoft.system.entity.Dept;
import com.lnsoft.system.service.IDeptService;
import com.lnsoft.system.vo.DeptVO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author guozhao
 */
public class DeptWrapper extends BaseEntityWrapper<Dept, DeptVO> {

	private static IDeptService deptService;

	static {
		deptService = SpringUtil.getBean(IDeptService.class);
	}

	public static DeptWrapper build() {
		return new DeptWrapper();
	}

	@Override
	public DeptVO entityVO(Dept dept) {
		DeptVO deptVO = BeanUtil.copy(dept, DeptVO.class);
		if (Func.equals(dept.getParentId(), CommonConstant.TOP_PARENT_ID)) {
			deptVO.setParentName(CommonConstant.TOP_PARENT_NAME);
		} else {
			Dept parent = deptService.getById(dept.getParentId());
			deptVO.setParentName(parent.getDeptName());
		}
		return deptVO;
	}

	public List<DeptVO> listNodeVO(List<Dept> list) {
		List<DeptVO> collect = list.stream().map(dept -> BeanUtil.copy(dept, DeptVO.class)).collect(Collectors.toList());
		Collections.sort(collect,(o1,o2)->{
			//类型一致按照排序
			if( o1.getType().equals(o2.getType())){
				if (o1.getSort() > o2.getSort()) {
					return 1;
				} else {
					return 0;
				}
			}else{
				//类型不一致 部门优先
				if("CORP".equals(o1.getType())){
					return 1;
				}else if("TEAM".equals(o1.getType())){
					return 0;
				}
			}
			return 0;
		});
		return ForestNodeMerger.merge(collect);
	}

}
