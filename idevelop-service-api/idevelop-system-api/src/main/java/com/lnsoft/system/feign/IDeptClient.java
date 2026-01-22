
package com.lnsoft.system.feign;

import com.lnsoft.core.launch.constant.AppConstant;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.system.entity.Dept;
import com.lnsoft.system.vo.DeptVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Feign接口类
 *
 * @author xyz
 */
@FeignClient(
	value = AppConstant.APPLICATION_SYSTEM_NAME,
	fallback = IDeptClientFallback.class
)
public interface IDeptClient {

	String API_PREFIX = "/dept";

	/**
	 * 列表
	 */
	@GetMapping(API_PREFIX + "/getList")
	R<List<Dept>> list();

	/**
	 * 根据父id获取子列表
	 */
	@GetMapping(API_PREFIX + "/getTreeList")
	R<List<DeptVO>> getTreeList(@RequestParam("parentId") String parentId);

	/**
	 * 根据父id获取子列表
	 */
	@GetMapping(API_PREFIX + "/getDeptList")
	R<List<DeptVO>> getDeptList(@RequestParam("parentId") String parentId);

	/**
	 * 根据i6000单位编码获取本系统单位编码
	 */
	@GetMapping(API_PREFIX + "/getByI6000Code")
	R<Dept> getByI6000Code(@RequestParam("i6000UnitCode") String i6000UnitCode);

	/**
	 * 根据名称获取部门编码
	 *
	 * @param name
	 * @return
	 */
	@GetMapping(API_PREFIX + "/getCodeByName")
	R<Dept> getCodeByName(@RequestParam("name") String name, @RequestParam("pid") String pid);
	/**
	 * 根据名称获取部门编码
	 *
	 * @param regionCode
	 * @return
	 */
	@GetMapping(API_PREFIX + "/getByRegionCode")
	R<List<Dept>> getByRegionCode(@RequestParam("regionCode") String regionCode);/**
	 * 根据名称获取部门编码
	 *
	 * @param regionCode
	 * @return
	 */
	@GetMapping(API_PREFIX + "/getByRegionCodeControl")
	R<List<Dept>> getByRegionCodeControl(@RequestParam("regionCode") String regionCode);
	/**
	 * 根据父id和fullName获取
	 * @param parentId
	 * @return
	 */
	@GetMapping(API_PREFIX + "/getByPidAndFullName")
	R<List<Dept>> getByPidAndFullName(@RequestParam("parentId") String parentId,@RequestParam("fullName") String fullName);
	/**
	 *
	 */
	@GetMapping(API_PREFIX + "/getById")
	R<Dept> getById(@RequestParam("id") String id);

	/**
	 * 根据i6000单位编码获取本系统部门列表
	 * @param i6000UnitCode
	 * @return
	 */
	@GetMapping(API_PREFIX + "/getByI6000Unit")
	R<List<Dept>> getByI6000Unit(@RequestParam("i6000UnitCode") String i6000UnitCode);

	/**
	 * 更新i6000部门
	 * @param dept
	 * @return
	 */
	@PostMapping(API_PREFIX + "/update")
	R<Boolean> update(@RequestBody Dept dept);

	/**
	 * 获取单位详情
	 * @param dept
	 * @return
	 */
	@PostMapping(API_PREFIX + "/detail")
	R<Dept> detail(@RequestBody Dept dept);

	/**
	 * 获取区域下信息
	 * @param dept
	 * @return
	 */
	@PostMapping(API_PREFIX + "/region/detail")
	R<Map<Long, Dept>> getRegionDetail(@RequestBody Dept dept);

	/**
	 * 根据erp维护工厂编码查询
	 */
	@GetMapping(API_PREFIX + "getByErpUnitCode")
	R<Dept> getByErpUnitCode(@RequestParam("erpUnitCode") String erpUnitCode);

}
