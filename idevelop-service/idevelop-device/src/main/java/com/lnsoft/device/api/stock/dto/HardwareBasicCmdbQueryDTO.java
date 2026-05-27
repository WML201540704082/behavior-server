package com.lnsoft.device.api.stock.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lnsoft.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 数据治理查询类
 */
@Data
public class HardwareBasicCmdbQueryDTO  extends BaseEntity {

	private static final long serialVersionUID = 1L;

	//私有标识 1
	private String bs;
	// 文件id
	private String fileId;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * area 区域
	 */
	@ApiModelProperty(value = "区域")
	private String area;
	/**
	 * 设备分类
	 */
	@ApiModelProperty(value = "设备分类")
	private String deviceCategory;
	/**
	 * 设备类型
	 */
	@ApiModelProperty(value = "设备类型")
	private String deviceType;

	/**
	 * 导出 id列表
	 */
	private List<Long> idList;



}
