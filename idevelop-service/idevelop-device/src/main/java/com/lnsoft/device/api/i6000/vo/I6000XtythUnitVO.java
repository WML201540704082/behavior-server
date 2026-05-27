package com.lnsoft.device.api.i6000.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: xuel
 * @CreateTime: 2024/4/13 15:22
 * @Description: I6000XtythUnitVO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class I6000XtythUnitVO implements Serializable {

	private String code;

	private String name;

	private Integer unitSort = 1;

}
