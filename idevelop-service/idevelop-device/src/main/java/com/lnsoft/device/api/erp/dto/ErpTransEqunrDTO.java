package com.lnsoft.device.api.erp.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @Author: xuel
 * @CreateTime: 2024/3/30 14:34
 * @Description: ErpTransEqunrDTO 用于存放 工单信息
 */
@XmlRootElement(name = "DATA")
@XmlType(propOrder = {"interfaceName", "xtdocId", "xtdocNo", "input"})
public class ErpTransEqunrDTO {

	private String interfaceName;
	/**
	 * 信通工单ID
	 */
	private String xtdocId;

	/**
	 * 信通工单编码
	 */
	private String xtdocNo;

	private ErpTransEqunrInputDTO input;


	public String getInterfaceName() {
		return interfaceName;
	}

	@XmlElement(name = "INTERFACENAME")
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getXtdocId() {
		return xtdocId;
	}

	@XmlElement(name = "XTDOC_ID")
	public void setXtdocId(String xtdocId) {
		this.xtdocId = xtdocId;
	}

	public String getXtdocNo() {
		return xtdocNo;
	}

	@XmlElement(name = "XTDOC_NO")
	public void setXtdocNo(String xtdocNo) {
		this.xtdocNo = xtdocNo;
	}


	public ErpTransEqunrInputDTO getInput() {
		return input;
	}

	@XmlElement(name = "INPUT")
	public void setInput(ErpTransEqunrInputDTO input) {
		this.input = input;
	}



}
