package com.lnsoft.device.api.warehouse.entity;

import com.lnsoft.device.api.safeaccess.entity.SdnQingDaoNetWork;
import com.lnsoft.device.api.warehouse.vo.DeviceStorageSdnVO;
import com.lnsoft.device.dto.DeviceRepairDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceSdnQingDao implements Serializable {
	private static final long serialVersionUID = 413102691569977782L;
	/**
	 * 入库数据
	 */
	private List<DeviceStorageSdnVO> sparesInDTOList;
	/**
	 * 出库数据
	 *
	 */
	private List<DeviceStorageSdnVO> sparesOutDTOList;
	/**
	 * 报修数据
	 */
	private List<DeviceRepairDTO> repairDTOList;
	/**
	 * 出入网数据
	 */
	private List<SdnQingDaoNetWork> accessNetworkDTOList;
	/**
	 * 判断标识 0-入库 1-出库 2-报修 3-出入网
	 */
	private String flag;
}
