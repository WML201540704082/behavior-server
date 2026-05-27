package com.lnsoft.device.api.i6000.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lnsoft.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * i6000外部数据实体类
 *
 * @author xyz
 */
@Data
@TableName("idevelop_i6000_external")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "I6000CiAttr对象", description = "i6000模型属性")
public class I6000External extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@ApiModelProperty(value = "id" )
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private String id;
	/**
	 * 外部数据code
	 */
	@ApiModelProperty(value = "外部数据code")
	private String extCode;

	/**
	 * I6000唯一ID
	 */
	@ApiModelProperty(value = "I6000唯一ID")
	private String extId;
	/**
	 * 外部数据值名称
	 */
	@ApiModelProperty(value = "外部数据值名称")
	private String extName;
	/**
	 * 外部数据值父ID
	 */
	@ApiModelProperty(value = "外部数据值父ID")
	private String extPid;
	/**
	 * 外部数据值状态
	 */
	@ApiModelProperty(value = "外部数据值状态")
	private String extState;
	/**
	 * 序号
	 */
	@ApiModelProperty(value = "序号")
	private String rn;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String matchModelId;

	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String mfrHs;

}
