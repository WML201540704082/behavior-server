
package com.lnsoft.system.feign;

import com.lnsoft.core.tool.api.R;
import com.lnsoft.system.entity.Dept;
import com.lnsoft.system.vo.DeptVO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Feign失败配置
 *
 * @author xyz
 */
@Component
public class IDeptClientFallback implements IDeptClient {


	@Override
	public R<List<Dept>> list() {
		return R.fail("获取数据失败");
	}

	@Override
	public R<List<DeptVO>> getTreeList(String parentId) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<List<DeptVO>> getDeptList(String parentId) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<Dept> getByI6000Code(String i6000UnitCode) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<Dept> getCodeByName(String name,String pid) { return R.fail("获取数据失败"); }

	@Override
	public R<List<Dept>> getByRegionCode(String regionCode) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<List<Dept>> getByRegionCodeControl(String regionCode) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<List<Dept>> getByPidAndFullName(String parentId, String fullName) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<Dept> getById(String id) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<List<Dept>> getByI6000Unit(String i6000UnitCode) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<Boolean> update(Dept dept) {
		return R.fail("更新失败");
	}

	@Override
	public R<Dept> detail(Dept dept) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<Map<Long, Dept>> getRegionDetail(Dept dept) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<Dept> getByErpUnitCode(String erpUnitCode) {
		return R.fail("获取数据失败");
	}

}
