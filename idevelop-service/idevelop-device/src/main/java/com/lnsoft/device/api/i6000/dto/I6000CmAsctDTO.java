package com.lnsoft.device.api.i6000.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: xuel
 * @CreateTime: 2024/5/15 11:09
 * @Description: I6000CmAsctDTO
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class I6000CmAsctDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	// 关联关系类型
	private String relationId;

	// 源模型id
	private String srcCiTypeId;

	// 目标模型id
	private String pageSize;



}
