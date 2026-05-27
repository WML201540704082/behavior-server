package com.lnsoft.device.api.warehouse.service;

import com.lnsoft.device.api.warehouse.dto.DSwitcherSyncDTO;
import com.lnsoft.device.api.warehouse.dto.SwitcherDeviceListDTO;
import com.lnsoft.device.api.warehouse.entity.DeviceSdnUserAccess;

import java.util.List;

public interface IDSwitcherSyncService {


	/**
	 * 设备投运推送数据同步服务
	 *
	 * @param switcherDeviceListDTOList 投运设备信息
	 * @param region                    区域编码
	 * @param switcherType              推送数据同步服务数据来源 0 设备投运  1 设备退运 2 子网管理
	 */
	void insertDSwitcherSync(List<SwitcherDeviceListDTO> switcherDeviceListDTOList, String region, String switcherType) throws Exception;

	/**
	 * 按区域组装同步数据
	 *
	 * @param switcherDeviceListDTOList
	 * @param regionCode
	 * @param switcherType
	 * @return
	 */
	List<DSwitcherSyncDTO> getDSwitcherSyncList(List<SwitcherDeviceListDTO> switcherDeviceListDTOList, String regionCode, String switcherType);

	/**
	 * 数据组装好之后请求接口
	 *
	 * @param dSwitcherSyncList
	 * @param switcherType
	 * @throws Exception
	 */
	void run(List<DSwitcherSyncDTO> dSwitcherSyncList, String switcherType) throws Exception;

	/**
	 * 同步SDN
	 *
	 * @param deviceSdnUserAccessList 同步数据
	 */
	void deviceSdnUserAccess(List<DeviceSdnUserAccess> deviceSdnUserAccessList) throws Exception;
}
