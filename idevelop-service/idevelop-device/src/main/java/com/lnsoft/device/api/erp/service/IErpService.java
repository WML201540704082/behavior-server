package com.lnsoft.device.api.erp.service;

import com.lnsoft.device.api.asset.dto.ProjectManagerDTO;
import com.lnsoft.device.api.asset.entity.ProjectManager;
import com.lnsoft.device.api.erp.dto.ErpTranstplnrDTO;
import com.lnsoft.device.api.erp.entity.ErpPersonAuth;
import com.lnsoft.device.api.erp.entity.ErpTransEqunr;
import com.lnsoft.device.api.erp.entity.ErpTransZcbf;
import com.lnsoft.device.api.erp.entity.ErpUpdateAnlnr;
import com.lnsoft.device.api.erp.response.ErpPersonAuthResp;
import com.lnsoft.device.api.erp.response.ErpTransEqunrResp;
import com.lnsoft.device.api.erp.response.ErpTransZcbfResp;

import java.util.List;
import java.util.Map;

public interface IErpService {

	/**
	 * 成本中心基础数据(实物保管部门,使用保管部门)
	 *
	 * @param maintainCode
	 * @return
	 */
	List<Map<String, String>> getKostl(String maintainCode);

	/**
	 * 功能位置主数据接口
	 *
	 * @param erpTranstplnrDTO
	 * @return
	 */
	Map<String, String> transTplnr(ErpTranstplnrDTO erpTranstplnrDTO);

	/**
	 * 获取WBS基础数据
	 *
	 * @param projectManagerDTO
	 * @return
	 */
	List<ProjectManager> getWbs(ProjectManagerDTO projectManagerDTO);

	/**
	 * 设备台账主数据同步接口
	 *
	 * @param erpTransEqunr
	 * @return
	 */
	ErpTransEqunrResp transEqunr(ErpTransEqunr erpTransEqunr);


	/**
	 * 资产报废集成接口
	 *
	 * @param erpTransZcbf
	 * @return
	 */
	ErpTransZcbfResp transZcbf(ErpTransZcbf erpTransZcbf);

	/**
	 * 获取人员权限
	 *
	 * @param erpPersonAuth
	 * @return
	 */
	ErpPersonAuthResp personAuth(ErpPersonAuth erpPersonAuth);


	/**
	 * ERP调用修改ERP资产编码 erp => xtyth
	 *
	 * @param erpUpdateAnlnr
	 * @return
	 */
	String updateAnlnr(ErpUpdateAnlnr erpUpdateAnlnr);
}
