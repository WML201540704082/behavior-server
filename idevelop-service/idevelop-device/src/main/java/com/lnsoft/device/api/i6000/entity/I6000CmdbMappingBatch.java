package com.lnsoft.device.api.i6000.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author: xuel
 * @CreateTime: 2024/4/13 14:35
 * @Description: I6000CmdbMappingBatch
 */
@Data
public class I6000CmdbMappingBatch {

	private Long cmdbCiId;
	private String cmdbCiName;

	private String i6000CiId;

	private List<I6000CmdbMapping> i6000CmdbMappingList;
}
