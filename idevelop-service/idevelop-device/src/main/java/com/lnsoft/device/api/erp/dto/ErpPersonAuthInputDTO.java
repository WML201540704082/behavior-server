
package com.lnsoft.device.api.erp.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @Author: xuel
 * @CreateTime: 2024/4/11 15:28
 * @Description: ErpPersonAuth
 */

@XmlRootElement(name = "INPUT")
@XmlType(propOrder = {"kostl", "wfId", "nodeId"})
public class ErpPersonAuthInputDTO {


	/**
	 * 成本中心(使用保管部门和实物保管部门)
	 */
	private String kostl;

	/**
	 * 工作流程编码 == 451
	 */
	private String wfId;

	/**
	 * BPM审批节点编码 == 26
	 */
	private String nodeId;

	@XmlElement(name = "KOSTL")
	public void setKostl(String kostl) {
		this.kostl = kostl;
	}
	@XmlElement(name = "WFID")
	public void setWfId(String wfId) {
		this.wfId = wfId;
	}
	@XmlElement(name = "NODEID")
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getKostl() {
		return kostl;
	}

	public String getWfId() {
		return wfId;
	}

	public String getNodeId() {
		return nodeId;
	}


	@Override
	public String toString() {
		return "ErpPersonAuthInputDTO{" +
			"kostl='" + kostl + '\'' +
			", wfId='" + wfId + '\'' +
			", nodeId='" + nodeId + '\'' +
			'}';
	}
}
