package com.lnsoft.device.api.i6000.wrapper;

import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.support.BaseEntityWrapper;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.BeanUtil;
import com.lnsoft.core.tool.utils.SpringUtil;
import com.lnsoft.system.entity.Dept;
import com.lnsoft.system.feign.IDeptClient;
import com.lnsoft.system.feign.ISysClient;
import com.lnsoft.system.vo.DeptVO;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @Author: xuel
 * @CreateTime: 2024/4/16 11:00
 * @Description: 包装类, 返回视图层所需的字段 DeptWrapper
 */
public class DeptWrapper extends BaseEntityWrapper<Dept, DeptVO> {
	private static IDeptClient deptClient;
	private static ISysClient iSysClient;

	static {
		deptClient = SpringUtil.getBean(IDeptClient.class);
		iSysClient = SpringUtil.getBean(ISysClient.class);
	}

	public static DeptWrapper build() {
		return new DeptWrapper();
	}

	@Override
	public DeptVO entityVO(Dept dept) {
		DeptVO deptVO = BeanUtil.copy(dept, DeptVO.class);
		return deptVO;
	}

	public Dept getUnitDeptCode(Long id) {
		Dept dept = iSysClient.getDept(id);
		if (ObjectUtils.isEmpty(dept)) {
			throw new ServiceException("单位和部门获取错误");
		}
		return dept;
	}

	/**
	 * 根据部门名称获取编码
	 *
	 * @param name
	 * @return
	 */
	public String getDeptCodeByName(String name) {
		R<Dept> data = deptClient.getCodeByName(name, "");
		if (!ObjectUtils.isEmpty(data)) {
			Dept dept = data.getData();
			if (!ObjectUtils.isEmpty(dept)) {
				return String.valueOf(dept.getId());
			}
		}
		return null;
	}

	public String getDeptCodeByNameAndPid(String name, String pid) {
		R<Dept> data = deptClient.getCodeByName(name, pid);
		Dept dept = data.getData();
		if (!ObjectUtils.isEmpty(dept)) {
			return String.valueOf(dept.getId());
		}
		return null;
	}


	/**
	 * 根据部门名称获取 区域编码
	 *
	 * @param name
	 * @return
	 */
	public String getAreaCodeByName(String name) {
		R<Dept> data = deptClient.getCodeByName(name, "");
		Dept dept = data.getData();
		if (!ObjectUtils.isEmpty(dept)) {
			return String.valueOf(dept.getRegionCode());
		}
		return null;
	}

	/**
	 * 获取部门id
	 *
	 * @param name
	 * @return
	 */
	public String getDeptIds(String name) {
		String tenantId = "000000";
		String deptId = iSysClient.getDeptIds(tenantId, name);
		return deptId;
	}

	/**
	 * 根据区域编码获取 市县 信息
	 *
	 * @param regionCode
	 * @return
	 */
	public List<Dept> getByRegionCodeControl(String regionCode) {
		R<List<Dept>> deptListR = deptClient.getByRegionCodeControl(regionCode);
		return deptListR.getData();
	}

}
