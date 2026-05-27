package com.lnsoft.device.api.i6000.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: xuel
 * @CreateTime: 2024/4/12 14:20
 * @Description: I6000UnitDeptVO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class I6000UnitDeptVO implements Serializable {

	private String code;

	private String name;

	private Integer unitSort = 1;

	private List<Children> childrenList;

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Children{

		private String code;

		private String name;

		private Integer deptSort = 1;
	}
}
