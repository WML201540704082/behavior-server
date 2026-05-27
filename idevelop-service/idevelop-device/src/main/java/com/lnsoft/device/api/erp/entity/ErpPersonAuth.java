package com.lnsoft.device.api.erp.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Author: xuel
 * @CreateTime: 2024/4/11 15:28
 * @Description: ErpPersonAuth
 */
public class ErpPersonAuth implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 成本中心(使用保管部门和实物保管部门)
	 */
	private String kostl;

	/**
	 * BPM工作流程编码 == 451
	 */
	private String wfId;

	/**
	 * BPM审批节点编码 == 市公司传10, 县公司传11
	 */
	private String nodeId;


	public String getWfId() {
		if (Objects.isNull(wfId)) {
			return "451";
		}
		return wfId;
	}

	public String getNodeId() {
		if (Objects.isNull(nodeId)) {
			return "11";
		}
		return nodeId;
	}

	public String getKostl() {
		return kostl;
	}

	public void setKostl(String kostl) {
		this.kostl = kostl;
	}

	public void setWfId(String wfId) {
		this.wfId = wfId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	@Override
	public String toString() {
		return "ErpPersonAuth{" +
			"kostl='" + kostl + '\'' +
			", wfId='" + wfId + '\'' +
			", nodeId='" + nodeId + '\'' +
			'}';
	}
}
