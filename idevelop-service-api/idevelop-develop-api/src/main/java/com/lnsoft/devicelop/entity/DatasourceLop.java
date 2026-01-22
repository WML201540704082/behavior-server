
package com.lnsoft.devicelop.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 数据源配置表实体类
 *
 * @author guozhao
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatasourceLop implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;
	/**
	 * 驱动类
	 */
	@ApiModelProperty(value = "驱动类")
	private String driverClass;
	/**
	 * 连接地址
	 */
	@ApiModelProperty(value = "连接地址")
	private String url;
	/**
	 * 用户名
	 */
	@ApiModelProperty(value = "用户名")
	private String username;
	/**
	 * 密码
	 */
	@ApiModelProperty(value = "密码")
	private transient String password;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;


}
