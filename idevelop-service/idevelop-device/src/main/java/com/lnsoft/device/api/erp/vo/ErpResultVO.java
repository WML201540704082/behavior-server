package com.lnsoft.device.api.erp.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author: xuel
 * @CreateTime: 2024/3/27 9:56
 * @Description: ErpResultVO
 */
@Data
public class ErpResultVO implements Serializable {

	private String code;

	private List<Map<String, String>> result;
}
