//package com.lnsoft.auth.controller;
//
//import com.google.common.collect.Lists;
//import com.lnsoft.core.isc.domain.IscUser;
//import com.lnsoft.core.log.exception.ServiceException;
//import com.lnsoft.core.tool.api.R;
//import com.lnsoft.core.tool.utils.StringUtil;
//import com.sgcc.isc.core.orm.identity.Department;
//import com.sgcc.isc.core.orm.identity.User;
//import com.sgcc.isc.framework.common.constant.Constants;
//import com.sgcc.isc.framework.common.entity.Paging;
//import com.sgcc.isc.service.adapter.factory.AdapterFactory;
//import com.sgcc.isc.service.adapter.helper.IIdentityService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author guoz on 2024/3/2
// */
//@Slf4j
//@RestController
//@Api(value = "ISC-统一权限服务接口", tags = "ISC")
//public class IscController {
//
//	private IIdentityService identityService = (IIdentityService) AdapterFactory.getIdentityService();
//
//
//	@GetMapping("/isc/iscbaseorg/tree")
//	@ApiOperation(value = "ISC-获取基准组织树接口", notes = "CAS票据:ticket")
//	public R<List<Department>> iscbaseorgTree(@RequestParam("type") String type,
//											  @RequestParam("value") String value) {
//		try {
//			String le = "{\"le\":{\"value\":\"" +
//				type +
//				"\",\"type\":\"1\"},\"optr\":\"=\",\"re\":{\"value\":\"" +
//				value +
//				"\",\"type\":\"1\"},\"type\":\"0\"}";
//			List<Department> list = identityService.getQuoteDepartmentsByConditionAndOrderBy(le, null);
//			return R.data(list);
//		} catch (Exception e) {
//			log.error("获取ISC基准组织失败:{}", e.getMessage(), e);
//			throw new ServiceException("获取ISC基准组织失败");
//		}
//	}
//
//	/**
//	 * 动态查询ISC基准用户信息
//	 * @param queryKey
//	 * @param queryValue
//	 * @return
//	 */
//	@GetMapping("/isc/user")
//	@ApiOperation(value = "动态获取获取基准用户", notes = "基准组织ID:baseorgId")
//	public R<List<IscUser>> user(@RequestParam(value = "queryKey", required = true) String queryKey,
//									   @RequestParam(value = "queryValue", required = true) String queryValue) {
//		String le = "{\"le\":{\"value\":\"" +
//			queryKey +
//			"\",\"type\":\"1\"},\"optr\":\"=\",\"re\":{\"value\":\"" +
//			queryValue +
//			"\",\"type\":\"1\"},\"type\":\"0\"}";
//
//		try {
//			List<User> users = identityService.getUsersByConditionAndOrderBy(le, "");
//			List<IscUser> data = Lists.newArrayList();
//			users.forEach(user -> {
//				IscUser iscUser = new IscUser();
//				StringBuffer organization = new StringBuffer();
//				// 获取用户公司信息
//				try {
//					Department company = identityService.getDepartmentById(user.getBaseOrgId());
//					if(company != null) {
//						organization.append(company.getName());
//					}
//				} catch (Exception e) {
//					log.error("获取公司信息失败:{}", e.getMessage(), e);
//					throw new ServiceException("获取公司信息失败");
//				}
//				// 获取用户部门信息
//				try {
//					String type = "id";
//					String lestr = "{\"le\":{\"value\":\"" +
//						type +
//						"\",\"type\":\"1\"},\"optr\":\"=\",\"re\":{\"value\":\"" +
//						user.getBaseOrgId() +
//						"\",\"type\":\"1\"},\"type\":\"0\"}";
//					List<Department> list = identityService.getQuoteDepartmentsByConditionAndOrderBy(lestr, null);
//					Department department = list.get(0);
//					if(department != null && !department.getName().equals(organization.toString())) {
//						organization.append("/").append(department.getName());
//					}
//				} catch (Exception e) {
//					log.error("获取部门信息失败:{}", e.getMessage(), e);
//					throw new ServiceException("获取部门信息失败");
//				}
//				iscUser.setUser(user);
//				iscUser.setOrganization(organization.toString());
//				data.add(iscUser);
//			});
//
//			return R.data(data);
//		} catch (Exception e) {
//			log.error("获取基准用户失败:{}", e.getMessage(), e);
//			throw new ServiceException("获取基准用户失败");
//		}
//	}
//
//
//}
