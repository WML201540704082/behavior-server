/**
 .
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lnsoft.device.api.safeaccess.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lnsoft.core.mp.base.BaseEntity;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * IP地址池实体类
 *
 * @author Idevelop
 * @since 2024-03-08
 */
@Data
@TableName("idevelop_safeaccess_ippool")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SafeaccessIppool对象", description = "IP地址池")
public class SafeaccessIppool extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private static final String TABLENAME = "idevelop_safeaccess_ippool";

	public static String getTablename() {
		return TABLENAME;
	}

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	@TableId(value = "ip_id", type = IdType.ASSIGN_ID)
	private String ipId;
	/**
	 * 单位
	 */
	@ApiModelProperty(value = "单位")
	private String orgNo;
	/**
	 * 所属子网
	 */
	@ApiModelProperty(value = "所属子网")
	private String subnet;
	/**
	 * IP地址
	 */
	@ApiModelProperty(value = "IP地址")
	private String ip;
	/**
	 * 级别
	 */
	@ApiModelProperty(value = "级别 1普通地址 2预留地址 3禁用地址")
	private String ipLevel;
	/**
	 * 使用情况
	 */
	@ApiModelProperty(value = "使用情况 0未分配 1已分配 2网关")
	private String isUsed;
	/**
	 * 序号
	 */
	@ApiModelProperty(value = "序号")
	private BigDecimal orderNo;
	/**
	 * 备用字段1
	 */
	@ApiModelProperty(value = "备用字段1")
	private String bakCol1;
	/**
	 * 备用字段2
	 */
	@ApiModelProperty(value = "备用字段2")
	private String bakCol2;
	/**
	 * 备用字段3
	 */
	@ApiModelProperty(value = "备用字段3")
	private String bakCol3;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty("创建部门")
	private Long createDept;

	@ApiModelProperty(value = "区域编码")
	private String regionCode;

	@ApiModelProperty(value = "部门编码")
	private String deptCode;

}
