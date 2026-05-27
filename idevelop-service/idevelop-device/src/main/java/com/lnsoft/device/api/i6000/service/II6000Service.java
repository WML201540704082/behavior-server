package com.lnsoft.device.api.i6000.service;

import com.alibaba.fastjson.JSONObject;
import com.lnsoft.device.api.i6000.dto.*;
import com.lnsoft.device.api.i6000.entity.I6000External;
import com.lnsoft.device.api.i6000.response.I6000ResultResp;
import com.lnsoft.device.api.i6000.vo.I6000DetailVO;
import com.lnsoft.device.dto.I6000SrynDTO;
import com.lnsoft.device.entity.ProjectManagerDetail;

import java.util.List;
import java.util.Map;

public interface II6000Service {

	/**
	 * 生成I6000认证信息
	 *
	 * @param accessToken
	 * @param publicKey
	 * @return
	 */
	String createAuthInfo(String accessToken, String publicKey);


	/**
	 * 配置类型分类查询接口
	 *
	 * @param ciTypeId
	 * @return
	 */
	List<Map<String, Object>> selectCiType(String ciTypeId);


	/**
	 * 配置类型属性信息查询接口
	 *
	 * @param ciTypeId
	 * @return
	 */
	List<Map<String, Object>> selectCiAttr(String ciTypeId);

	/**
	 * 字典表所有枚举项查询接口
	 *
	 * @return
	 */
	JSONObject selectEnumAll();

	/**
	 * 查询字典表某一枚举项的所有枚举值
	 *
	 * @param enumID
	 * @return
	 */
	JSONObject selectEnumByID(String enumID, String enumValID, String fEnumValID);

	/**
	 * 配置项关联信息查询接口
	 *
	 * @param ciid
	 * @return
	 */
	JSONObject selectRelationByCiid(String ciid);

	/**
	 * 查询全部I6000外部数据视图接口
	 *
	 * @return
	 */
	List<Map<String, Object>> queryOriView();

	/**
	 * 查询指定的外部数据（分页）接口
	 *
	 * @param i6000OriViewDTO
	 * @return
	 */
	List<I6000External> selectOriView(I6000OriViewDTO i6000OriViewDTO);

	/**
	 * 查询指定的外部数据（不分页）接口
	 *
	 * @return
	 */
	Map<String, Object> selectOriViewAll(String oriViewCode);

	/**
	 * 根据ERP资产编码和ERP设备编码, 信通一体化查询I6000系统数据
	 *
	 * @param i6000SrynDTOS
	 * @return
	 */
	Map<String, I6000DetailVO> selectI6000Detail(List<I6000SrynDTO> i6000SrynDTOS);

	/**
	 * 根据ERP资产编码和ERP设备编码, 信通一体化同步I6000系统数据
	 *
	 * @param i6000SyncDetailDTOS
	 * @return
	 */
	List<ProjectManagerDetail> syncI6000Detail(List<I6000SyncDetailDTO> i6000SyncDetailDTOS);


	/**
	 * 配置项查询接口
	 *
	 * @param ciTypeId
	 * @param i6000CiCientityDTO
	 * @return
	 */
	List<Map<String, Object>> selectCiCientity(String ciTypeId, I6000CiCientityDTO i6000CiCientityDTO);

	/**
	 * 功能位置(新增和修改)
	 *
	 * @param i6000FuncDTO
	 * @return
	 */
	Map<String, Object> saveOrUpdateFunc(I6000FuncDTO i6000FuncDTO);

	/**
	 * 功能位置(删除)
	 *
	 * @param objId
	 * @return
	 */
	Map<String, Object> deleteFunc(String objId);

	/**
	 * 获取I6000的仓库信息(T501)
	 *
	 * @return
	 */
	List<I6000RoomDTO> getRoomList();

	/**
	 * 获取I6000的机房信息(T502)
	 *
	 * @return
	 */
	List<I6000WarehouseDTO> getWarehouseList();


	/**
	 * 新增i6000资产台账
	 * 使用方法: 示例数据在 I6000Controller 中
	 * 获取ciTypeId 先通过 Map<String, String> erpI6000MapByCiId = cmdbDictProperties.getErpI6000MapByCiId(cmdbDictProperties.getDeviceType())
	 * 方法获取 Key: 设备类型编码, Value: ciTypeId
	 *
	 * @param ciTypeId  设备类型编码 对应的 dictKeyI6000
	 * @param entityMap key 为 uuid, value 为 数据
	 * @return
	 */
	I6000ResultResp i6000Batchsave(String ciTypeId, Map<String, Map<String, Object>> entityMap);


	/**
	 * 更新i6000资产台账
	 * 使用方法: 示例数据在 I6000Controller 中
	 * value 中必须 包含 CITYPE_ID: ciTypeId
	 * <p>
	 * 获取ciTypeId 先通过 Map<String, String> erpI6000MapByCiId = cmdbDictProperties.getErpI6000MapByCiId(cmdbDictProperties.getDeviceType())
	 * 方法获取 Key: 设备类型编码, Value: ciTypeId
	 *
	 * @param entityMap key 为 uuid, value 为 数据
	 * @return
	 */
	List<I6000ResultResp> i6000Batchupdate(Map<String, Map<String, Object>> entityMap);

	/**
	 * 新增i6000资产台账Copy
	 * 使用方法: 示例数据在 I6000Controller 中
	 * 获取ciTypeId 先通过 Map<String, String> erpI6000MapByCiId = cmdbDictProperties.getErpI6000MapByCiId(cmdbDictProperties.getDeviceType())
	 * 方法获取 Key: 设备类型编码, Value: ciTypeId
	 *
	 * @param ciTypeId  设备类型编码 对应的 dictKeyI6000
	 * @param entityMap key 为 uuid, value 为 数据
	 * @return
	 */
	I6000ResultResp i6000BatchsaveCopy(String ciTypeId, Map<String, Map<String, Object>> entityMap);

	/**
	 * 更新i6000资产台账Copy
	 * 使用方法: 示例数据在 I6000Controller 中
	 * value 中必须 包含 CITYPE_ID: ciTypeId
	 * <p>
	 * 获取ciTypeId 先通过 Map<String, String> erpI6000MapByCiId = cmdbDictProperties.getErpI6000MapByCiId(cmdbDictProperties.getDeviceType())
	 * 方法获取 Key: 设备类型编码, Value: ciTypeId
	 *
	 * @param entityMap key 为 uuid, value 为 数据
	 * @return
	 */
	List<I6000ResultResp> i6000BatchupdateCopy(Map<String, Map<String, Object>> entityMap);

	/**
	 * 新增i6000资产台账(仅限仓库机房使用)
	 * 使用方法: 示例数据在 I6000Controller 中
	 * 获取ciTypeId 先通过 Map<String, String> erpI6000MapByCiId = cmdbDictProperties.getErpI6000MapByCiId(cmdbDictProperties.getDeviceType())
	 * 方法获取 Key: 设备类型编码, Value: ciTypeId
	 *
	 * @param ciTypeId  设备类型编码 对应的 dictKeyI6000
	 * @param entityMap key 为 uuid, value 为 数据
	 * @return
	 */
	I6000ResultResp i6000BatchsaveDirect(String ciTypeId, Map<String, Map<String, Object>> entityMap);

	/**
	 * 更新i6000资产台账(仅限仓库机房使用)
	 * 使用方法: 示例数据在 I6000Controller 中
	 * value 中必须 包含 CITYPE_ID: ciTypeId
	 * <p>
	 * 获取ciTypeId 先通过 Map<String, String> erpI6000MapByCiId = cmdbDictProperties.getErpI6000MapByCiId(cmdbDictProperties.getDeviceType())
	 * 方法获取 Key: 设备类型编码, Value: ciTypeId
	 *
	 * @param entityMap key 为 uuid, value 为 数据
	 * @return
	 */
	List<I6000ResultResp> i6000BatchupdateDirect(Map<String, Map<String, Object>> entityMap);

}

