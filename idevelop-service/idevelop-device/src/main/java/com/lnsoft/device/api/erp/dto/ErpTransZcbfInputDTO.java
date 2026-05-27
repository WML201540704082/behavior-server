package com.lnsoft.device.api.erp.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @Author: xuel
 * @CreateTime: 2024/3/30 15:12
 * @Description: ErpTransEqunrInputDTO
 */
@XmlRootElement(name = "INPUT")
public class ErpTransZcbfInputDTO {


	private List<ErpTransZcbfItemDTO> equnrItemDTOList;

	public List<ErpTransZcbfItemDTO> getEqunrItemDTOList() {
		return equnrItemDTOList;
	}

	@XmlElement(name = "ITEM")
	public void setEqunrItemDTOList(List<ErpTransZcbfItemDTO> equnrItemDTOList) {
		this.equnrItemDTOList = equnrItemDTOList;
	}

	@Override
	public String toString() {
		return "ErpTransZcbfInputDTO{" +
			"equnrItemDTOList=" + equnrItemDTOList +
			'}';
	}
}
