
package com.lnsoft.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.system.dto.DeptDTO;
import com.lnsoft.system.entity.Dept;
import com.lnsoft.system.vo.DeptVO;

import java.util.List;

/**
 * 服务类
 *
 * @author guozhao
 */
public interface IDeptService extends IService<Dept> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param dept
	 * @return
	 */
	IPage<DeptVO> selectDeptPage(IPage<DeptVO> page, DeptVO dept);

	/**
	 * 树形结构
	 *
	 * @param tenantId
	 * @return
	 */
	List<DeptVO> tree(String tenantId);

	/**
	 * 树形结构
	 *
	 * @param dept
	 * @return
	 */
	List<DeptVO> tree(DeptDTO dept);

	/**
	 * 获取部门ID
	 *
	 * @param tenantId
	 * @param deptNames
	 * @return
	 */
	String getDeptIds(String tenantId, String deptNames);

	/**
	 * 获取部门名
	 *
	 * @param deptIds
	 * @return
	 */
	List<String> getDeptNames(String deptIds);

	/**
	 * 获取公司信息
	 */
	Dept getCorp(Long deptId);

	/**
	 * 获取公司信息
	 */
	Dept getDeptOrGroup(Long deptId);

	/**
	 * 获取公司信息
	 */
	Dept getDeptByGroup(Long deptId);

	/**
	 * 提交
	 *
	 * @param dept
	 * @return
	 */
	boolean submit(Dept dept);

	/**
	 * 查询所属单位
	 *
	 * @return
	 */
	List<Dept> queryDept();

	/**
	 * 获取部门树形结构
	 *
	 * @return R
	 */
	R<DeptVO> treeList();

	/**
	 * 单位懒加载
	 *
	 * @param parentId
	 * @return R
	 */
	List<Dept> getTreeList(String parentId);
	/**
	 * 获取所有单位列表
	 * @param
	 * @return R
	 */
	List<Dept> getAllCorp();


	/**
	 * 刷新ISC单位和部门数据
	 *
	 * @return
	 */
	Boolean refresh(String type);
}
