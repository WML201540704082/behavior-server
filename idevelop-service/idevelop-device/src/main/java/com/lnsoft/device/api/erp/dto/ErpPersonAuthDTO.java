
package com.lnsoft.device.api.erp.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @Author: xuel
 * @CreateTime: 2024/4/11 15:28
 * @Description: ErpPersonAuth
 */

@XmlRootElement(name = "DATA")
@XmlType(propOrder = {"interfaceName", "erpPersonAuthInputDTO"})
public class ErpPersonAuthDTO {

	private String interfaceName;

	private ErpPersonAuthInputDTO erpPersonAuthInputDTO;

	@XmlElement(name = "INTERFACENAME")
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	@XmlElement(name = "INPUT")
	public void setErpPersonAuthInputDTO(ErpPersonAuthInputDTO erpPersonAuthInputDTO) {
		this.erpPersonAuthInputDTO = erpPersonAuthInputDTO;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public ErpPersonAuthInputDTO getErpPersonAuthInputDTO() {
		return erpPersonAuthInputDTO;
	}

	@Override
	public String toString() {
		return "ErpPersonAuthDTO{" +
			"interfaceName='" + interfaceName + '\'' +
			", erpPersonAuthInputDTO=" + erpPersonAuthInputDTO +
			'}';
	}
}
