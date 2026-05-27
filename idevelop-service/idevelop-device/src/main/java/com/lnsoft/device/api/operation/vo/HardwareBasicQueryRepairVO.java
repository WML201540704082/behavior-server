package com.lnsoft.device.api.operation.vo;

import com.lnsoft.cmdb.vo.HardwareBasicQueryVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xyzadmin
 */
@Data
public class HardwareBasicQueryRepairVO extends HardwareBasicQueryVO {
	/**
	 * 故障类型
	 */
	@ApiModelProperty(value = "故障类型")
	private String repairType;
}
