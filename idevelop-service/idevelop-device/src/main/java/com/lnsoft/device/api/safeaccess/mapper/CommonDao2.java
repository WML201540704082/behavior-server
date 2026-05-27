package com.lnsoft.device.api.safeaccess.mapper;

import com.lnsoft.cmdb.entity.HardwareBasic;
import com.lnsoft.device.api.safeaccess.entity.SysTRadiusConnection;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Component
public interface CommonDao2 {

	List<Map<String, String>> getIpsCanUse(Map<String, String> map);


	//根据类型编号查询设备分类边哈
	//List<String> queryCode(String code);


	int updateDevHardwareBasic(Map<String, String> map);


	void updateDevTHardwareBasicByDeviceNo(Map<String, String> map);


	List<String> findApplyJobTypeById(String id);


	void updateDevTHardwareUse(Map<String, String> map);


	void updateDevTHardwareContract(Map<String, String> map);


	List<Map<String, String>> findWorkType();


	int deleteDevTHardWare(String value);


	int deleteHardWareByCode(String value);


	void updateDevTHardwareUseByDeviceNo(Map<String, String> map);


	String getSwModelByTypeId(String swModel);


	List<String> queryCodeClass(String typeCode);


	int insertHardwareBasic(Map<String, String> map);


	List<Map<String, String>> findCategoryMap();


	Map<String, String> findHardwareUse(String deviceNumber);


	Map<String, String> findHardwareContract(String deviceNumber);

//	HardwareContract selectHardwareContract(@Param("deviceNumber") String deviceNumber);
//
//	HardwareBasic selectHardware(@Param("deviceNo") String deviceNo);


	void insertHardwareContract(Map<String, String> map);


	void insertHardwareUse(Map<String, String> map);

	void insertHardwareService(Map<String, String> map);

	void insertHardwareRecord(Map<String, String> map);

	void insertHardwareApp(Map<String, String> map);


	int deleteHardwareBasic(String deviceNo);


	int deleteHardwareContract(String deviceNo);


	int deleteHardwareUse(String deviceNo);

	/*void deleteHardwareService(String deviceNo);

	void deleteHardwareRecord(String deviceNo);

	void deleteHardwareApp(String deviceNo);*/


	List<SysTRadiusConnection> fingMySqlSource(String orgNo);


	String getCodeClass(String typeCode);

	/**
	 * 根据设备编码清空台账使用人信息
	 *
	 * @param deviceNo deviceNo
	 * @return int
	 */
	public int getRidOfMiChargeUser(@Param("deviceNo") String deviceNo);


}
