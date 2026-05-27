package com.lnsoft.device.api.stock.service;


import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.cmdb.dto.CmdbStockDTO;
import com.lnsoft.device.api.stock.dto.HardwareBasicCmdbQueryDTO;
import com.lnsoft.device.api.stock.dto.SimpleSafeAccessSwitchesDTO;
import com.lnsoft.device.api.stock.vo.ErrorListVo;
import com.lnsoft.device.api.stock.vo.HardwareBasicCmdbDeviceVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 数据治理获取cmdb 数据治理
 */
public interface IHardwareBasicCmdbService {


	/**
	 * 下载指定模板的cmdb数据
	 *
	 * @param hardwareBasicCmdbQuery
	 * @param response
	 */
	void downloadTemplate(HardwareBasicCmdbQueryDTO hardwareBasicCmdbQuery, HttpServletResponse response);

	HardwareBasicCmdbDeviceVO importByExcel(MultipartFile file, String deviceCategory,String isMath);

	/**
	 * 批量保存数据 单次限制10000行
	 *
	 * @param hardwareBasicCmdbDeviceVO
	 * @return
	 */
	String customSaveBatch(HardwareBasicCmdbDeviceVO hardwareBasicCmdbDeviceVO);

	/**
	 * 用于数据治理修改接口
	 *
	 * @param cmdbStockDTO
	 * @return
	 */
	Map<String, Object> cientityBatchupdateStock(CmdbStockDTO cmdbStockDTO);

	Map<String, String> getDeviceTypeRemark();

	void importByExceldownload(HardwareBasicCmdbDeviceVO hardwareBasicCmdbDeviceVO, HttpServletResponse response);

	void downloadError(HardwareBasicCmdbDeviceVO deviceVO, HttpServletResponse response);

	public Map<String, Object> cmdbDataCheckForUpdate(Map<String, Object> map, String deviceCategory);

	/**
	 * 根据cmdb 检查设备数据是否符合规范
	 *
	 * @param cmdb
	 */
	Map<String, Object> cmdbDataCheck(Map<String, Object> cmdb, String deviceCategory);

	/**
	 * 数据治理使用
	 *
	 * @param ip
	 * @return
	 */
	SimpleSafeAccessSwitchesDTO getInfoByIP(String ip);

	void importNew(MultipartFile file, String deviceCategory,String userId);

	R errorList(ErrorListVo errorListVo);

	R importByExcelNew(MultipartFile file, String deviceCategory, String userId);

	R resolver(String fileId);

	void downloadNew(String fileId, HttpServletResponse response);

	R addNums(String id);

	R allNums(String userId);
}
