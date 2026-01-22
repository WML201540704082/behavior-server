
package com.lnsoft.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.system.dto.DeptDTO;
import com.lnsoft.system.vo.DeptVO;
import com.lnsoft.system.entity.Dept;
import com.lnsoft.system.vo.DeptVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Mapper 接口
 *
 * @author guozhao
 */
public interface DeptMapper extends BaseMapper<Dept> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param dept
	 * @return
	 */
	List<DeptVO> selectDeptPage(IPage page, DeptVO dept);

	/**
	 * 获取树形节点
	 *
	 * @param tenantId
	 * @return
	 */
	List<DeptVO> tree(String tenantId);

	/**
	 * 获取部门名
	 *
	 * @param ids
	 * @return
	 */
	List<String> getDeptNames(Long[] ids);

	List<DeptVO> treeByPid(DeptDTO dept);

	Dept selectErpById(Long id);

	Dept selectI6000ById(Long id);
	/**
	 * 获取所有单位列表
	 * @return
	 */
    List<Dept> getAllCorp();


	List<Map<String, String>> getIscDict(@Param("type") String type, @Param("iscDeptId") String iscDeptId, @Param("iscParentId") String iscParentId);
}
