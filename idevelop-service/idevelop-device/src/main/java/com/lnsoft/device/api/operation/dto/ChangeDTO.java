package com.lnsoft.device.api.operation.dto;

import com.lnsoft.device.api.operation.entity.DeviceChange;
import com.lnsoft.device.api.operation.entity.DeviceChangeList;
import lombok.Data;

import java.util.List;

/**
 * 设备变更参数接收类
 * @author xyzadmin
 */
@Data
public class ChangeDTO extends DeviceChange {
	private List<DeviceChangeList> newChangeDeviceDTOList;
	private List<DeviceChangeList> oldChangeDeviceDTOList;
	private String submitStatus;
}
